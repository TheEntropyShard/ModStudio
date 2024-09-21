/*
 * ModMaker - https://github.com/TheEntropyShard/ModMaker
 * Copyright (C) 2024 TheEntropyShard
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

package me.theentropyshard.modmaker.gui;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.function.Consumer;

public class FormPanel extends JPanel {
    public FormPanel() {
        super(new MigLayout("fillx", "[][grow]", ""));
    }

    public JTextField addFormRow(String label, String defaultValue, Consumer<String> onTextTyped, String placeholder) {
        this.add(new JLabel(label + ":"));

        JTextField textField = new JTextField(defaultValue);
        textField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, placeholder);
        textField.addCaretListener(e -> onTextTyped.accept(textField.getText()));
        this.add(textField, "wrap, growx");

        return textField;
    }
}
