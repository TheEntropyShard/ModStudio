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

package me.theentropyshard.modstudio.gui.view.welcome;

import me.theentropyshard.modstudio.gui.Icons;
import me.theentropyshard.modstudio.gui.components.Card;

import javax.swing.*;
import java.awt.*;

public class NewProjectWelcomeButton extends Card {
    private static final int SIDE_SIZE = 64;
    private static final Dimension PREFERRED_SIZE = new Dimension(
        NewProjectWelcomeButton.SIDE_SIZE, NewProjectWelcomeButton.SIDE_SIZE
    );

    public NewProjectWelcomeButton() {
        super(5, 10);

        this.setLayout(new BorderLayout());

        this.add(new JLabel(Icons.get("add").derive(32, 32)), BorderLayout.CENTER);
    }

    @Override
    public Dimension getPreferredSize() {
        return NewProjectWelcomeButton.PREFERRED_SIZE;
    }
}
