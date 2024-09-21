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

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GridBagFormPanel extends JPanel {
    private final GridBagConstraints gbc;
    private final List<FormRow> formRows;

    public GridBagFormPanel() {
        super(new GridBagLayout());

        this.gbc = new GridBagConstraints();
        this.gbc.insets = new Insets(5, 5, 5, 5);

        this.formRows = new ArrayList<>();
    }

    public void addFormRow(String label) {
        FormRow formRow = new FormRow();

        JLabel labelLabel = new JLabel(label + ":");
        formRow.setLabel(labelLabel);

        JTextField textField = new JTextField();
        formRow.setTextField(textField);

        this.gbc.gridy++;
        this.gbc.weighty = 0;

        this.gbc.gridx++;
        this.gbc.anchor = GridBagConstraints.WEST;
        this.gbc.weightx = 0;
        formRow.setLabelConstraints((GridBagConstraints) this.gbc.clone());

        this.gbc.gridx++;
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.weightx = 1;
        formRow.setFieldConstraints((GridBagConstraints) this.gbc.clone());

        this.gbc.gridx -= 2;

        this.formRows.add(formRow);
    }

    public void finish() {
        for (int i = 0; i < this.formRows.size() - 1; i++) {
            FormRow formRow = this.formRows.get(i);

            this.add(formRow.getLabel(), formRow.getLabelConstraints());
            this.add(formRow.getTextField(), formRow.getFieldConstraints());
        }

        FormRow lastRow = this.formRows.get(this.formRows.size() - 1);
        this.add(lastRow.getLabel(), lastRow.getLabelConstraints());
        this.add(lastRow.getTextField(), lastRow.getFieldConstraints());
    }

    private static final class FormRow {
        private JLabel label;
        private GridBagConstraints labelConstraints;
        private JTextField textField;
        private GridBagConstraints fieldConstraints;

        public FormRow() {

        }

        public JLabel getLabel() {
            return this.label;
        }

        public void setLabel(JLabel label) {
            this.label = label;
        }

        public GridBagConstraints getLabelConstraints() {
            return this.labelConstraints;
        }

        public void setLabelConstraints(GridBagConstraints labelConstraints) {
            this.labelConstraints = labelConstraints;
        }

        public JTextField getTextField() {
            return this.textField;
        }

        public void setTextField(JTextField textField) {
            this.textField = textField;
        }

        public GridBagConstraints getFieldConstraints() {
            return this.fieldConstraints;
        }

        public void setFieldConstraints(GridBagConstraints fieldConstraints) {
            this.fieldConstraints = fieldConstraints;
        }
    }
}
