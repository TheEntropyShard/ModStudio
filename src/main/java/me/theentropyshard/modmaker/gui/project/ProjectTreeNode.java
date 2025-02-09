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

package me.theentropyshard.modmaker.gui.project;

import javax.swing.tree.DefaultMutableTreeNode;

public class ProjectTreeNode extends DefaultMutableTreeNode {
    private final Type type;
    private final String text;
    private final Object data;

    public ProjectTreeNode(String text, Type type, Object data) {
        super(data);

        this.type = type;
        this.text = text;
        this.data = data;
    }

    public Type getType() {
        return this.type;
    }

    public String getText() {
        return this.text;
    }

    public Object getData() {
        return this.data;
    }

    public enum Type {
        PROJECT,
        CATEGORY,
        FILE
    }
}