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

import com.formdev.flatlaf.FlatClientProperties;
import me.theentropyshard.modstudio.gui.utils.SimpleDocumentListener;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class BlockNameComponent extends JPanel {
    private final JTextField blockNameField;
    private final JLabel actualBlockNameLabel;

    public BlockNameComponent(Listener listener) {
        super(new MigLayout("fill, insets 5", "[]", "[][]"));

        this.actualBlockNameLabel = new JLabel("Actual name: ");

        this.blockNameField = new JTextField();
        this.blockNameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter a block name");
        this.blockNameField.getDocument().addDocumentListener(new SimpleDocumentListener(() -> {
            String text = this.blockNameField.getText();

            boolean valid;

            if (text.isEmpty()) {
                valid = false;
            } else {
                valid = true;
            }

            listener.onChange(valid);

            this.actualBlockNameLabel.setText("Actual name: " + text.replace(" ", "_"));

            if (valid) {
                this.blockNameField.putClientProperty(FlatClientProperties.OUTLINE, null);
            } else {
                this.blockNameField.putClientProperty(FlatClientProperties.OUTLINE, FlatClientProperties.OUTLINE_ERROR);
            }
        }));

        this.add(this.blockNameField, "wrap, growx, pushx");
        this.add(this.actualBlockNameLabel);
    }

    public String getBlockName() {
        return this.blockNameField.getText().toLowerCase().replace(" ", "_");
    }

    public interface Listener {
        void onChange(boolean valid);
    }
}