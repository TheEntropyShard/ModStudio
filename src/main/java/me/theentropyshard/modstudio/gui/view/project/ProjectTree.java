/*
 * ModStudio - https://github.com/TheEntropyShard/ModStudio
 * Copyright (C) 2024-2025 TheEntropyShard
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package me.theentropyshard.modstudio.gui.view.project;

import me.theentropyshard.modstudio.ModStudio;
import me.theentropyshard.modstudio.cosmic.block.Block;
import me.theentropyshard.modstudio.cosmic.block.BlockState;
import me.theentropyshard.modstudio.cosmic.block.model.BlockModel;
import me.theentropyshard.modstudio.cosmic.block.model.BlockModelTexture;
import me.theentropyshard.modstudio.project.Project;
import me.theentropyshard.modstudio.project.ProjectManager;
import me.theentropyshard.modstudio.utils.FileUtils;
import me.theentropyshard.modstudio.utils.json.Json;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.*;

public class ProjectTree extends JTree {
    private final ProjectTreeNode rootNode;
    private final ProjectTreeNode blocksNode;
    private final Set<NewBlockListener> newBlockListeners;
    private final Set<DeleteBlockListener> deleteBlockListeners;
    private final Set<LeftClickListener> leftClickListeners;

    public ProjectTree(String projectName) {
        this.rootNode = new ProjectTreeNode(projectName, ProjectTreeNode.Type.PROJECT, null);
        this.blocksNode = new ProjectTreeNode("Blocks", ProjectTreeNode.Type.CATEGORY, null);

        this.newBlockListeners = new HashSet<>();
        this.deleteBlockListeners = new HashSet<>();
        this.leftClickListeners = new HashSet<>();

        this.setRowHeight(20);
        this.setShowsRootHandles(true);
        this.setRootVisible(true);
        this.setCellRenderer(new ProjectTreeCellRenderer());

        DefaultTreeModel model = new DefaultTreeModel(this.rootNode);
        this.setModel(model);

        model.insertNodeInto(this.blocksNode, this.rootNode, 0);
        model.nodeStructureChanged(this.rootNode);

        MouseListener ml = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int selRow = ProjectTree.this.getRowForLocation(e.getX(), e.getY());

                TreePath selectedPath = ProjectTree.this.getPathForLocation(e.getX(), e.getY());

                if (selRow != -1 && e.getButton() == MouseEvent.BUTTON1) {
                    for (LeftClickListener listener : ProjectTree.this.leftClickListeners) {
                        listener.onLeftClick(e.getClickCount(), selectedPath);
                    }
                }
            }
        };
        this.addMouseListener(ml);

        JPopupMenu contextMenu = new JPopupMenu();
        JMenuItem addBlockItem = new JMenuItem("Add Block");
        JMenuItem deleteBlockItem = new JMenuItem("Delete Block");

        addBlockItem.addActionListener(e -> {
            TreePath selectedPath = this.getSelectionPath();

            if (selectedPath == null) {
                return;
            }

            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();

            if (selectedNode != this.blocksNode) {
                return;
            }

            String blockName = JOptionPane.showInputDialog(
                ModStudio.getInstance().getGui().getFrame(),
                "Enter a block name"
            );

            if (blockName == null || blockName.isEmpty()) {
                return;
            }

            ProjectManager projectManager = ModStudio.getInstance().getProjectManager();
            Project currentProject = projectManager.getCurrentProject();

            Block block = new Block();
            String namespace = currentProject.getNamespace();
            block.setStringId(namespace + ":" + blockName);

            Map<String, BlockState> blockStateMap = new HashMap<>();

            BlockState blockState = new BlockState();
            blockState.setModelName(namespace + ":" + "models/blocks/model_" + blockName + ".json");
            blockState.setStateGenerators(List.of("base:slabs_seamless_all", "base:stairs_seamless_all"));

            BlockModel blockModel = new BlockModel();
            blockModel.setParent("base:models/blocks/cube.json");
            blockState.setBlockModel(blockModel);

            Map<String, BlockModelTexture> textureMap = new HashMap<>();
            blockModel.setTextures(textureMap);

            blockStateMap.put("default", blockState);

            block.setBlockStates(blockStateMap);

            currentProject.addBlock(block);

            Path blocksDir = currentProject.getWorkDir().resolve("blocks");
            try {
                FileUtils.createDirectoryIfNotExists(blocksDir);
                FileUtils.writeUtf8(blocksDir.resolve(blockName + ".json"), Json.writePretty(block));
            } catch (IOException ex) {
                ex.printStackTrace();

                return;
            }

            Path modelsDir = currentProject.getWorkDir().resolve("models").resolve("blocks");
            try {
                FileUtils.createDirectoryIfNotExists(modelsDir);
                FileUtils.writeUtf8(modelsDir.resolve("model_" + blockName + ".json"), Json.writePretty(blockModel));
            } catch (IOException ex) {
                ex.printStackTrace();

                return;
            }

            Path texturesDir = currentProject.getWorkDir().resolve("textures").resolve("blocks");
            try {
                FileUtils.createDirectoryIfNotExists(texturesDir);
                try (InputStream inputStream = ProjectTree.class.getResourceAsStream("/no_texture.png")) {
                    Files.copy(Objects.requireNonNull(inputStream), texturesDir.resolve(blockName + ".png"));
                }
            } catch (IOException ex) {
                ex.printStackTrace();

                return;
            }

            ProjectTreeNode newNode = new ProjectTreeNode(namespace + ":" + blockName, ProjectTreeNode.Type.FILE, block);
            model.insertNodeInto(newNode, this.blocksNode, /*this.blocksNode.getChildCount()*/0);
            model.nodeStructureChanged(this.blocksNode);
            this.expandPath(new TreePath(this.rootNode.getPath()));
            TreePath path = new TreePath(newNode.getPath());
            this.scrollPathToVisible(path);

            this.newBlockListeners.forEach(listener -> listener.onNewBlock(namespace + ":" + blockName));
        });

        deleteBlockItem.addActionListener(e -> {
            TreePath selectedPath = this.getSelectionPath();

            if (selectedPath == null) {
                return;
            }

            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();

            if (selectedNode.getParent() != this.blocksNode) {
                return;
            }

            model.removeNodeFromParent(selectedNode);

            this.deleteBlockListeners.forEach(listener -> listener.onBlockDeleted(String.valueOf(selectedNode.getUserObject())));
        });

        contextMenu.add(addBlockItem);
        contextMenu.add(deleteBlockItem);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int closestRow = ProjectTree.this.getClosestRowForLocation(e.getX(), e.getY());
                Rectangle closestRowBounds = ProjectTree.this.getRowBounds(closestRow);
                if (e.getY() >= closestRowBounds.getY() &&
                    e.getY() < closestRowBounds.getY() +
                        closestRowBounds.getHeight()) {
                    if (e.getX() > closestRowBounds.getX() &&
                        closestRow < ProjectTree.this.getRowCount()) {

                        ProjectTree.this.setSelectionRow(closestRow);
                    }

                } else {
                    ProjectTree.this.setSelectionRow(-1);
                }

                if (e.isPopupTrigger()) {
                    this.showContextMenu(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    this.showContextMenu(e);
                }
            }

            private void showContextMenu(MouseEvent e) {
                TreePath path = ProjectTree.this.getPathForLocation(e.getX(), e.getY());

                if (path == null) {
                    return;
                }

                ProjectTree.this.setSelectionPath(path);

                contextMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });
    }

    public void addBlock(Block block) {
        ProjectTreeNode newNode = new ProjectTreeNode(block.getStringId(), ProjectTreeNode.Type.FILE, block);
        ((DefaultTreeModel) this.getModel()).insertNodeInto(newNode, this.blocksNode, this.blocksNode.getChildCount());
        this.scrollPathToVisible(new TreePath(newNode.getPath()));
    }

    public interface NewBlockListener {
        void onNewBlock(String stringId);
    }

    public interface DeleteBlockListener {
        void onBlockDeleted(String stringId);
    }

    public interface LeftClickListener {
        void onLeftClick(int clickCount, TreePath treePath);
    }

    public void addNewBlockListener(NewBlockListener newBlockListener) {
        this.newBlockListeners.add(newBlockListener);
    }

    public void addDeleteBlockListener(DeleteBlockListener deleteBlockListener) {
        this.deleteBlockListeners.add(deleteBlockListener);
    }

    public void addLeftClickListener(LeftClickListener leftClickListener) {
        this.leftClickListeners.add(leftClickListener);
    }
}
