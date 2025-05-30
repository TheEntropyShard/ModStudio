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
import me.theentropyshard.modstudio.gui.HtmlLabel;
import me.theentropyshard.modstudio.gui.Icons;
import me.theentropyshard.modstudio.gui.IsometricBlockRenderer;
import me.theentropyshard.modstudio.gui.RenderOptions;
import me.theentropyshard.modstudio.gui.components.AccordionPanel;
import me.theentropyshard.modstudio.gui.utils.MouseListenerBuilder;
import me.theentropyshard.modstudio.gui.utils.Worker;
import me.theentropyshard.modstudio.logging.Log;
import me.theentropyshard.modstudio.project.Project;
import me.theentropyshard.modstudio.project.ProjectManager;
import me.theentropyshard.modstudio.utils.FileUtils;
import me.theentropyshard.modstudio.utils.ImageUtils;
import me.theentropyshard.modstudio.utils.Lazy;
import me.theentropyshard.modstudio.utils.ResourceUtils;
import me.theentropyshard.modstudio.utils.json.Json;
import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Map;

public class BlockEditView extends JPanel {
    private static final Lazy<ImageIcon> NO_TEXTURE_ICON = Lazy.of(() -> {
        BufferedImage noTextureImage = ResourceUtils.loadImage("/no_texture.png");

        RenderOptions options = new RenderOptions().setScale(2);

        return new ImageIcon(
            ImageUtils.fitImageAndResize(
                IsometricBlockRenderer.render(noTextureImage, noTextureImage, noTextureImage, options), 64, 64
            )
        );
    });

    private final JLabel blockStringIdLabel;

    private JFileChooser fileChooser;
    private File lastDir;

    public BlockEditView(Block block) {
        super(new MigLayout("", "[grow]", "[top][]"));

        this.blockStringIdLabel = new HtmlLabel.Builder()
            .bold("String id: ")
            .build();

        JPanel blockStringIdPanel = new JPanel(new BorderLayout());
        blockStringIdPanel.add(this.blockStringIdLabel, BorderLayout.WEST);

        JTextField blockStringIdField = new JTextField(block.getStringId());
        blockStringIdPanel.add(blockStringIdField, BorderLayout.CENTER);

        this.add(blockStringIdPanel, "growx, wrap");

        block.getBlockStates().forEach((blockStringId, blockStateId) -> {
            this.addBlockStateView(block.getStringId(), blockStringId, blockStateId, block);
        });
    }

    private void addBlockStateView(String blockStringId, String blockStateParams, BlockState blockState, Block block) {
        ProjectManager projectManager = ModStudio.getInstance().getProjectManager();
        Project currentProject = projectManager.getCurrentProject();
        JPanel panel = new JPanel(new MigLayout("fill", "[left][fill, center]", "[top]"));
        JLabel iconLabel = new JLabel(BlockEditView.NO_TEXTURE_ICON.get()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(this.getBackground());
                g2d.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 10, 10);

                super.paintComponent(g);

                g2d.setColor(UIManager.getColor("Component.borderColor"));
                g2d.drawRoundRect(0, 0, this.getWidth() - 1, this.getHeight() - 1, 10, 10);
            }
        };
        MouseListener listener = new MouseListenerBuilder()
            .mouseEntered(e -> {
                iconLabel.setBackground(UIManager.getColor("Component.borderColor"));
            })
            .mouseExited(e -> {
                iconLabel.setBackground(UIManager.getColor("Panel.background"));
            })
            .mouseClicked(e -> {
                JPopupMenu popupMenu = new JPopupMenu("Hello");

                JMenuItem changeTopTextureItem = new JMenuItem("Change top texture");
                changeTopTextureItem.addActionListener(ev -> {
                    new Worker<Path, Void>("picking top texture") {
                        @Override
                        protected Path work() throws Exception {
                            if (BlockEditView.this.fileChooser == null) {
                                BlockEditView.this.fileChooser = new JFileChooser();
                                BlockEditView.this.fileChooser.setCurrentDirectory(ModStudio.getInstance().getWorkDir().toFile());
                                BlockEditView.this.fileChooser.setFileFilter(new FileNameExtensionFilter("PNG Images", "png"));
                                BlockEditView.this.fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                            }

                            int option = BlockEditView.this.fileChooser.showOpenDialog(ModStudio.getInstance().getGui().getFrame());

                            if (option == JFileChooser.APPROVE_OPTION) {
                                File file = BlockEditView.this.fileChooser.getSelectedFile();
                                BlockEditView.this.lastDir = BlockEditView.this.fileChooser.getCurrentDirectory();
                                Path path = file.toPath();

                                Project project = ModStudio.getInstance().getProjectManager().getCurrentProject();

                                Path workDir = project.getWorkDir();
                                Path blocksTexturesDir = workDir.resolve("textures").resolve("blocks");
                                FileUtils.createDirectoryIfNotExists(blocksTexturesDir);

                                Files.copy(path, blocksTexturesDir.resolve(blockStringId.split(":")[1] + "_top.png"),
                                    StandardCopyOption.REPLACE_EXISTING);

                                return path;
                            }

                            return null;
                        }

                        @Override
                        protected void finish(Path path) {
                            BufferedImage image;
                            try {
                                image = ImageIO.read(path.toFile());
                            } catch (IOException ex) {
                                Log.error("Could not read image " + path, ex);

                                return;
                            }
                            String blockName = block.getStringId().split(":")[1];
                            BlockModel blockModel = blockState.getBlockModel();
                            Map<String, BlockModelTexture> textures = blockModel.getTextures();
                            if (textures.containsKey("top")) {
                                BlockModelTexture texture = textures.get("top");
                                texture.setFileName(currentProject.getNamespace() + ":" + "textures/blocks/" + blockName + "_top.png");
                                texture.setTexture(image);
                            } else {
                                BlockModelTexture texture = new BlockModelTexture();
                                texture.setTexture(image);
                                texture.setFileName(currentProject.getNamespace() + ":" + "textures/blocks/" + blockName + "_top.png");
                                textures.put("top", texture);
                            }
                            iconLabel.setIcon(BlockEditView.generateBlockIcon(blockState));
                            Path blocksDir = currentProject.getWorkDir().resolve("blocks");
                            try {
                                FileUtils.createDirectoryIfNotExists(blocksDir);
                                FileUtils.writeUtf8(blocksDir.resolve(blockName + ".json"), Json.writePretty(block));
                            } catch (IOException ex) {
                                Log.error("Could not write block " + blockName, ex);

                                return;
                            }

                            Path modelsDir = currentProject.getWorkDir().resolve("models").resolve("blocks");
                            try {
                                FileUtils.createDirectoryIfNotExists(modelsDir);
                                FileUtils.writeUtf8(modelsDir.resolve("model_" + blockName + ".json"), Json.writePretty(blockModel));
                            } catch (IOException ex) {
                                Log.error("Could not write model for " + blockName, ex);

                                return;
                            }
                        }
                    }.execute();
                });
                popupMenu.add(changeTopTextureItem);

                JMenuItem changeSideTextureItem = new JMenuItem("Change side texture");
                changeSideTextureItem.addActionListener(ev -> {
                    new Worker<Path, Void>("picking side texture") {
                        @Override
                        protected Path work() throws Exception {
                            if (BlockEditView.this.fileChooser == null) {
                                BlockEditView.this.fileChooser = new JFileChooser();
                                BlockEditView.this.fileChooser.setCurrentDirectory(ModStudio.getInstance().getWorkDir().toFile());
                                BlockEditView.this.fileChooser.setFileFilter(new FileNameExtensionFilter("PNG Images", "png"));
                                BlockEditView.this.fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                            }

                            int option = BlockEditView.this.fileChooser.showOpenDialog(ModStudio.getInstance().getGui().getFrame());

                            if (option == JFileChooser.APPROVE_OPTION) {
                                File file = BlockEditView.this.fileChooser.getSelectedFile();
                                BlockEditView.this.lastDir = BlockEditView.this.fileChooser.getCurrentDirectory();
                                Path path = file.toPath();

                                Project project = ModStudio.getInstance().getProjectManager().getCurrentProject();

                                Path workDir = project.getWorkDir();
                                Path blocksTexturesDir = workDir.resolve("textures").resolve("blocks");
                                FileUtils.createDirectoryIfNotExists(blocksTexturesDir);

                                Files.copy(path, blocksTexturesDir.resolve(blockStringId.split(":")[1] + "_side.png"),
                                    StandardCopyOption.REPLACE_EXISTING);

                                return path;
                            }

                            return null;
                        }

                        @Override
                        protected void finish(Path path) {
                            BufferedImage image;
                            try {
                                image = ImageIO.read(path.toFile());
                            } catch (IOException ex) {
                                Log.error("Could not read image " + path, ex);

                                return;
                            }
                            String blockName = block.getStringId().split(":")[1];
                            BlockModel blockModel = blockState.getBlockModel();
                            Map<String, BlockModelTexture> textures = blockModel.getTextures();
                            if (textures.containsKey("side")) {
                                BlockModelTexture texture = textures.get("side");
                                texture.setFileName(currentProject.getNamespace() + ":" + "textures/blocks/" + blockName + "_side.png");
                                texture.setTexture(image);
                            } else {
                                BlockModelTexture texture = new BlockModelTexture();
                                texture.setTexture(image);
                                texture.setFileName(currentProject.getNamespace() + ":" + "textures/blocks/" + blockName + "_side.png");
                                textures.put("side", texture);
                            }
                            iconLabel.setIcon(BlockEditView.generateBlockIcon(blockState));


                            Path blocksDir = currentProject.getWorkDir().resolve("blocks");
                            try {
                                FileUtils.createDirectoryIfNotExists(blocksDir);
                                FileUtils.writeUtf8(blocksDir.resolve(blockName + ".json"), Json.writePretty(block));
                            } catch (IOException ex) {
                                Log.error("Could not write block " + blockName, ex);

                                return;
                            }

                            Path modelsDir = currentProject.getWorkDir().resolve("models").resolve("blocks");
                            try {
                                FileUtils.createDirectoryIfNotExists(modelsDir);
                                FileUtils.writeUtf8(modelsDir.resolve("model_" + blockName + ".json"), Json.writePretty(blockModel));
                            } catch (IOException ex) {
                                Log.error("Could not write model for " + blockName, ex);

                                return;
                            }
                        }
                    }.execute();
                });
                popupMenu.add(changeSideTextureItem);

                JMenuItem changeBottomTextureItem = new JMenuItem("Change bottom texture");
                changeBottomTextureItem.addActionListener(ev -> {
                    new Worker<Path, Void>("picking side texture") {
                        @Override
                        protected Path work() throws Exception {
                            if (BlockEditView.this.fileChooser == null) {
                                BlockEditView.this.fileChooser = new JFileChooser();
                                BlockEditView.this.fileChooser.setCurrentDirectory(ModStudio.getInstance().getWorkDir().toFile());
                                BlockEditView.this.fileChooser.setFileFilter(new FileNameExtensionFilter("PNG Images", "png"));
                                BlockEditView.this.fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                            }

                            int option = BlockEditView.this.fileChooser.showOpenDialog(ModStudio.getInstance().getGui().getFrame());

                            if (option == JFileChooser.APPROVE_OPTION) {
                                File file = BlockEditView.this.fileChooser.getSelectedFile();
                                BlockEditView.this.lastDir = BlockEditView.this.fileChooser.getCurrentDirectory();
                                Path path = file.toPath();

                                Project project = ModStudio.getInstance().getProjectManager().getCurrentProject();

                                Path workDir = project.getWorkDir();
                                Path blocksTexturesDir = workDir.resolve("textures").resolve("blocks");
                                FileUtils.createDirectoryIfNotExists(blocksTexturesDir);

                                Files.copy(path, blocksTexturesDir.resolve(blockStringId.split(":")[1] + "_bottom.png"),
                                    StandardCopyOption.REPLACE_EXISTING);

                                return path;
                            }

                            return null;
                        }

                        @Override
                        protected void finish(Path path) {
                            BufferedImage image;
                            try {
                                image = ImageIO.read(path.toFile());
                            } catch (IOException ex) {
                                Log.error("Could not read image " + path, ex);

                                return;
                            }
                            String blockName = block.getStringId().split(":")[1];
                            BlockModel blockModel = blockState.getBlockModel();
                            Map<String, BlockModelTexture> textures = blockModel.getTextures();
                            if (textures.containsKey("bottom")) {
                                BlockModelTexture texture = textures.get("bottom");
                                texture.setFileName(currentProject.getNamespace() + ":" + "textures/blocks/" + blockName + "_bottom.png");
                                texture.setTexture(image);
                            } else {
                                BlockModelTexture texture = new BlockModelTexture();
                                texture.setTexture(image);
                                texture.setFileName(currentProject.getNamespace() + ":" + "textures/blocks/" + blockName + "_bottom.png");
                                textures.put("bottom", texture);
                            }
                            iconLabel.setIcon(BlockEditView.generateBlockIcon(blockState));


                            Path blocksDir = currentProject.getWorkDir().resolve("blocks");
                            try {
                                FileUtils.createDirectoryIfNotExists(blocksDir);
                                FileUtils.writeUtf8(blocksDir.resolve(blockName + ".json"), Json.writePretty(block));
                            } catch (IOException ex) {
                                Log.error("Could not write block " + blockName, ex);

                                return;
                            }

                            Path modelsDir = currentProject.getWorkDir().resolve("models").resolve("blocks");
                            try {
                                FileUtils.createDirectoryIfNotExists(modelsDir);
                                FileUtils.writeUtf8(modelsDir.resolve("model_" + blockName + ".json"), Json.writePretty(blockModel));
                            } catch (IOException ex) {
                                Log.error("Could not write model for " + blockName, ex);

                                return;
                            }
                        }
                    }.execute();
                });
                popupMenu.add(changeBottomTextureItem);

                popupMenu.show(iconLabel, e.getX(), e.getY());
            })
            .build();
        iconLabel.addMouseListener(listener);
        iconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        iconLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.add(iconLabel);

        JPanel blockStateParamsPanel = new JPanel(new MigLayout("fillx, wrap 2, insets 0, gap 5 5", "[][]", "[top]"));

        JLabel langKeyLabel = new JLabel("Lang key: ");
        blockStateParamsPanel.add(langKeyLabel, "aligny baseline");

        JTextField langKeyField = new JTextField(blockState.getLangKey());
        blockStateParamsPanel.add(langKeyField, "growx, pushx");

        JLabel modelNameLabel = new JLabel("Model name: ");
        blockStateParamsPanel.add(modelNameLabel, "aligny baseline");

        JTextField modelNameField = new JTextField(blockState.getModelName());
        blockStateParamsPanel.add(modelNameField, "growx, pushx");

        JLabel swapGroupIdLabel = new JLabel("Swap group id: ");
        blockStateParamsPanel.add(swapGroupIdLabel, "aligny baseline");

        JTextField swapGroupIdField = new JTextField(blockState.getSwapGroupId());
        blockStateParamsPanel.add(swapGroupIdField, "growx, pushx");

        JLabel blockEventsIdLabel = new JLabel("Block events id: ");
        blockStateParamsPanel.add(blockEventsIdLabel, "aligny baseline");

        JTextField blockEventsIdField = new JTextField(blockState.getBlockEventsId());
        blockStateParamsPanel.add(blockEventsIdField, "growx, pushx");

        JCheckBox allowSwappingField = new JCheckBox("Allow swapping");
        allowSwappingField.setSelected(blockState.isAllowSwapping());
        blockStateParamsPanel.add(allowSwappingField, "growx, pushx, span 2");

        JCheckBox isOpaqueField = new JCheckBox("Is opaque");
        isOpaqueField.setSelected(blockState.isOpaque());
        blockStateParamsPanel.add(isOpaqueField, "growx, pushx, span 2");

        JCheckBox canRaycastForBreakField = new JCheckBox("Can raycast for break");
        canRaycastForBreakField.setSelected(blockState.canRaycastForBreak());
        blockStateParamsPanel.add(canRaycastForBreakField, "growx, pushx, span 2");

        JCheckBox canRaycastForPlaceOnField = new JCheckBox("Can raycast for place on");
        canRaycastForPlaceOnField.setSelected(blockState.canRaycastForPlaceOn());
        blockStateParamsPanel.add(canRaycastForPlaceOnField, "growx, pushx, span 2");

        JCheckBox canRaycastForReplaceField = new JCheckBox("Can raycast for replace");
        canRaycastForReplaceField.setSelected(blockState.canRaycastForReplace());
        blockStateParamsPanel.add(canRaycastForReplaceField, "growx, pushx, span 2");

        JCheckBox catalogHiddenField = new JCheckBox("Catalog hidden");
        catalogHiddenField.setSelected(blockState.isCatalogHidden());
        blockStateParamsPanel.add(catalogHiddenField, "growx, pushx, span 2");

        JCheckBox walkThroughField = new JCheckBox("Walk through");
        walkThroughField.setSelected(blockState.isWalkThrough());
        blockStateParamsPanel.add(walkThroughField, "growx, pushx, span 2");

        JCheckBox isFluidField = new JCheckBox("Is fluid");
        isFluidField.setSelected(blockState.isFluid());
        blockStateParamsPanel.add(isFluidField, "growx, pushx, span 2");

        JLabel lightAttenuationLabel = new JLabel("Light attenuation: ");
        blockStateParamsPanel.add(lightAttenuationLabel, "aligny baseline");

        JTextField lightAttenuationField = new JTextField(String.valueOf(blockState.getLightAttenuation()));
        blockStateParamsPanel.add(lightAttenuationField, "growx, pushx");

        JLabel lightLevelRedLabel = new JLabel("Light level red: ");
        blockStateParamsPanel.add(lightLevelRedLabel, "aligny baseline");

        JTextField lightLevelRedField = new JTextField(String.valueOf(blockState.getLightLevelRed()));
        blockStateParamsPanel.add(lightLevelRedField, "growx, pushx");

        JLabel lightLevelGreenLabel = new JLabel("Light level green: ");
        blockStateParamsPanel.add(lightLevelGreenLabel, "aligny baseline");

        JTextField lightLevelGreenField = new JTextField(String.valueOf(blockState.getLightLevelGreen()));
        blockStateParamsPanel.add(lightLevelGreenField, "growx, pushx");

        JLabel lightLevelBlueLabel = new JLabel("Light level blue: ");
        blockStateParamsPanel.add(lightLevelBlueLabel, "aligny baseline");

        JTextField lightLevelBlueField = new JTextField(String.valueOf(blockState.getLightLevelBlue()));
        blockStateParamsPanel.add(lightLevelBlueField, "growx, pushx");

        JLabel frictionLabel = new JLabel("Friction: ");
        blockStateParamsPanel.add(frictionLabel, "aligny baseline");

        JTextField frictionField = new JTextField(String.valueOf(blockState.getFriction()));
        blockStateParamsPanel.add(frictionField, "growx, pushx");

        JLabel bouncinessLabel = new JLabel("Bounciness: ");
        blockStateParamsPanel.add(bouncinessLabel, "aligny baseline");

        JTextField bouncinessField = new JTextField(String.valueOf(blockState.getBounciness()));
        blockStateParamsPanel.add(bouncinessField, "growx, pushx");

        JLabel refractiveIndexLabel = new JLabel("Refractive index: ");
        blockStateParamsPanel.add(refractiveIndexLabel, "aligny baseline");

        JTextField refractiveIndexField = new JTextField(String.valueOf(blockState.getBounciness()));
        blockStateParamsPanel.add(refractiveIndexField, "growx, pushx");

        panel.add(blockStateParamsPanel, "grow, push");

        AccordionPanel accordionPanel = new AccordionPanel(blockStringId + "[" + blockStateParams + "]", panel);
        this.add(accordionPanel, "growx, wrap");
    }

    public static Icon generateBlockIcon(BlockState blockState) {
        BlockModel blockModel = blockState.getBlockModel();

        if (blockModel == null) {
            return Icons.MOD_DEFAULT_TEXTURE;
        }

        Map<String, BlockModelTexture> textures = blockModel.getTextures();

        if (textures == null) {
            return Icons.MOD_DEFAULT_TEXTURE;
        }

        BufferedImage texture = null;

        RenderOptions options = new RenderOptions();
        options.setScale(2);

        if (textures.containsKey("all")) {
            BufferedImage image = textures.get("all").getTexture();

            texture = IsometricBlockRenderer.render(
                image, image, image, options
            );
        } else {
            BufferedImage top = null;
            BufferedImage side = null;

            if (textures.containsKey("top")) {
                top = textures.get("top").getTexture();
            }

            if (textures.containsKey("side")) {
                side = textures.get("side").getTexture();
            }

            if (top != null && side != null) {
                texture = IsometricBlockRenderer.render(
                    top, side, side, options
                );
            }
        }

        return texture == null ? Icons.MOD_DEFAULT_TEXTURE : new ImageIcon(ImageUtils.fitImageAndResize(
            texture, 64, 64
        ));
    }
}
