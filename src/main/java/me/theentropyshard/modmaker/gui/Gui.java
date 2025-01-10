/*
 * ModMaker - https://github.com/TheEntropyShard/ModMaker
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

package me.theentropyshard.modmaker.gui;

import com.formdev.flatlaf.FlatLaf;
import me.theentropyshard.modmaker.gui.laf.DarkModMakerLaf;
import me.theentropyshard.modmaker.gui.laf.LightModMakerLaf;
import me.theentropyshard.modmaker.gui.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;

public class Gui {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    private final JFrame frame;

    private boolean darkTheme = false;

    public Gui() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);

        FlatLaf.registerCustomDefaultsSource("themes");

        if (this.darkTheme) {
            DarkModMakerLaf.setup();
        } else {
            LightModMakerLaf.setup();
        }

        UIManager.put("SplitPaneDivider.style", "plain");
        UIManager.put("Button.showMnemonics", true);
        UIManager.put("Component.hideMnemonics", false);

        this.frame = new JFrame("ModMaker");
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.getContentPane().setPreferredSize(new Dimension(Gui.WIDTH, Gui.HEIGHT));
        this.frame.pack();

        SwingUtils.centerWindow(this.frame, 0);
    }

    public void show() {
        this.frame.setVisible(true);
    }
}
