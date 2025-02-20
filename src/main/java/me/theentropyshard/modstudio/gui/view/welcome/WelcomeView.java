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
        this.line2.setBorder(new EmptyBorder(30, 0, 0, 0));
        this.line2.setFont(this.line2.getFont().deriveFont(13.0f));
        textGbc.gridy++;
        textPanel.add(this.line2, textGbc);

        this.line3 = new JLabel("Open existing project from disk.");
        this.line3.setFont(this.line3.getFont().deriveFont(13.0f));
        textGbc.gridy++;
        textPanel.add(this.line3, textGbc);

        mainGbc.gridy++;
        this.add(textPanel, mainGbc);

        GridBagConstraints buttonsGbc = new GridBagConstraints();
        buttonsGbc.anchor = GridBagConstraints.NORTH;
        buttonsGbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        buttonsPanel.setBorder(new EmptyBorder(30, 0, 0, 0));

        buttonsGbc.gridy++;
        buttonsGbc.gridx++;
        buttonsPanel.add(new WelcomeViewButton("add", "New Project"), buttonsGbc);

        buttonsGbc.gridx++;
        buttonsPanel.add(Box.createHorizontalStrut(50), buttonsGbc);

        buttonsGbc.gridx++;
        buttonsPanel.add(new WelcomeViewButton("open_disk", "Open Project"), buttonsGbc);

        mainGbc.gridy++;
        this.add(buttonsPanel, mainGbc);
    }
}
