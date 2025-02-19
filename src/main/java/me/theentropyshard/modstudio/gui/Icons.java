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

package me.theentropyshard.modstudio.gui;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import me.theentropyshard.modstudio.utils.FlatLafUtils;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class Icons {
    private static final Map<String, FlatSVGIcon> ICONS = new HashMap<>();

    public static final Icon MOD_DEFAULT_TEXTURE = FlatLafUtils.getSvgIcon("/icons/mod_default_icon.svg")
        .derive(64, 64);

    public static void load() {
        for (String name : new String[]{
            "folder", "image", "json", "project", "unknown", "chevronDown", "chevronRight",
            "open_disk", "open_disk_hover", "add"
        }) {
            String path = "/icons/" + name;

            if (Gui.darkTheme) {
                path = path + "_dark";
            }

            path = path + ".svg";

            Icons.ICONS.put(name, FlatLafUtils.getSvgIcon(path));
        }
    }

    static {
        Icons.load();
    }

    public static FlatSVGIcon get(String name) {
        FlatSVGIcon icon = Icons.ICONS.get(name);

        if (icon == null) {
            throw new RuntimeException("Icon with name " + name + " does not exist");
        }

        return icon;
    }
}