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

package me.theentropyshard.modstudio.gui.view.block;

import me.theentropyshard.modstudio.ModStudio;
import me.theentropyshard.modstudio.gui.utils.SwingUtils;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Optional;

public class AddBlockView extends JPanel {
    private final BlockNameComponent blockNameComponent;
    private final JCheckBox generateSlabsCheckbox;
    private final JCheckBox generateStairsCheckbox;
    private final JButton addButton;
    private final JButton cancelButton;

    private AddBlockInfo addBlockInfo;

    public AddBlockView() {
        super(new MigLayout("fillx, wrap", "[]", "[][][][]"));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBorder(new MatteBorder(1, 0, 0, 0, UIManager.getColor("Component.border")));

        this.addButton = new JButton("Add");
        this.addButton.setEnabled(false);
        bottomPanel.add(this.addButton);

        this.cancelButton = new JButton("Cancel");
        bottomPanel.add(this.cancelButton);

        this.blockNameComponent = new BlockNameComponent(this.addButton::setEnabled);
        this.blockNameComponent.setPreferredSize(new Dimension(320, 0));

        this.add(this.blockNameComponent, "grow");

        this.generateSlabsCheckbox = new JCheckBox("Generate slabs", true);
        this.add(this.generateSlabsCheckbox, "grow");

        this.generateStairsCheckbox = new JCheckBox("Generate stairs", true);
        this.add(this.generateStairsCheckbox, "grow");

        this.add(bottomPanel, "grow");
    }

    private void gatherInfo() {
        this.addBlockInfo = new AddBlockInfo(
            this.blockNameComponent.getBlockName(),
            this.generateSlabsCheckbox.isSelected(),
            this.generateStairsCheckbox.isSelected()
        );
    }

    public static AddBlockView showDialog() {
        AddBlockView view = new AddBlockView();

        JDialog dialog = new JDialog(ModStudio.getInstance().getGui().getFrame(), "Add Block", true);

        view.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape"
        );

        view.getActionMap().put("escape", SwingUtils.newAction(e -> {
            dialog.dispose();
        }));

        dialog.add(view, BorderLayout.CENTER);
        dialog.getRootPane().setDefaultButton(view.getAddButton());
        view.getAddButton().addActionListener(e -> {
            view.gatherInfo();
            dialog.dispose();
        });
        view.getCancelButton().addActionListener(e -> {
            dialog.dispose();
        });
        dialog.pack();
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        SwingUtils.centerWindow(dialog, 0);
        dialog.setVisible(true);

        return view;
    }

    public Optional<AddBlockInfo> getAddBlockInfo() {
        return Optional.ofNullable(this.addBlockInfo);
    }

    public JButton getAddButton() {
        return this.addButton;
    }

    public JButton getCancelButton() {
        return this.cancelButton;
    }
}
