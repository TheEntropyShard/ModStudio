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

import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;

public class Gui {
    private final AppFrame appFrame;

    public Gui() {
        Gui.prepare();

        this.appFrame = new AppFrame();
    }

    public void show() {
        this.appFrame.setVisible(true);
    }

    private static void prepare() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);

        FlatIntelliJLaf.setup();

        UIManager.getDefaults().put("Button.showMnemonics", true);
        UIManager.getDefaults().put("Component.hideMnemonics", false);
    }

    public AppFrame getAppFrame() {
        return this.appFrame;
    }
}
