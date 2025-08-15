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

package me.theentropyshard.modstudio.cosmic;

public class Identifier {
    private final String namespace;
    private final String name;

    public Identifier(String namespace, String name) {
        this.namespace = namespace;
        this.name = name;
    }

    public static Identifier of(String serialized) {
        String[] parts = serialized.split(":");

        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid identifier: " + serialized);
        }

        return new Identifier(parts[0], parts[1]);
    }

    @Override
    public String toString() {
        return this.namespace + ":" + this.name;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public String getName() {
        return this.name;
    }
}
