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

import me.theentropyshard.modmaker.utils.FlatLafUtils;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class Icons {
    private static final Map<String, Icon> ICONS = new HashMap<>();

    public static void load() {
        for (String name : new String[]{"folder", "image", "json", "project"}) {
            Icons.ICONS.put(name, FlatLafUtils.getSvgIcon("/icons/" + name + ".svg"));
        }
    }

    static {
        Icons.load();
    }

    public static Icon get(String name) {
        Icon icon = Icons.ICONS.get(name);

        if (icon == null) {
            throw new RuntimeException("Icon with name " + name + " does not exist");
        }

        return icon;
    }
}
