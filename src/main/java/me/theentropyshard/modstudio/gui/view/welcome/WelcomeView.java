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

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class WelcomeView extends JPanel {
    private final JLabel line1;
    private final JLabel line2;
    private final JLabel line3;

    public WelcomeView() {
        super(new GridBagLayout());

        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.anchor = GridBagConstraints.CENTER;
        mainGbc.fill = GridBagConstraints.VERTICAL;

        GridBagConstraints textGbc = new GridBagConstraints();
        textGbc.anchor = GridBagConstraints.SOUTH;
        textGbc.fill = GridBagConstraints.VERTICAL;

        JPanel textPanel = new JPanel(new GridBagLayout());

        this.line1 = new JLabel("<html><b>Welcome to ModStudio</b></html>");
        this.line1.setFont(this.line1.getFont().deriveFont(24.0f));
        textGbc.gridy++;
        textPanel.add(this.line1, textGbc);

        this.line2 = new JLabel("Create a new project to start from scratch.");
        textGbc.gridy++;
        textPanel.add(this.line2, textGbc);

        this.line3 = new JLabel("Open existing project from disk.");
        textGbc.gridy++;
        textPanel.add(this.line3, textGbc);

        mainGbc.gridy++;
        this.add(textPanel, mainGbc);

        GridBagConstraints buttonsGbc = new GridBagConstraints();
        buttonsGbc.anchor = GridBagConstraints.NORTH;
        buttonsGbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        buttonsPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        NewProjectWelcomeButton newProjectButton = new NewProjectWelcomeButton();
        buttonsGbc.gridy++;
        buttonsGbc.gridx++;
        buttonsPanel.add(newProjectButton, buttonsGbc);

        buttonsGbc.gridx++;
        buttonsPanel.add(Box.createHorizontalStrut(10), buttonsGbc);

        buttonsGbc.gridx++;
        buttonsPanel.add(new OpenProjectWelcomeButton(), buttonsGbc);

        mainGbc.gridy++;
        this.add(buttonsPanel, mainGbc);
    }
}
