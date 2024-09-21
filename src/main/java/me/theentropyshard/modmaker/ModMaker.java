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

package me.theentropyshard.modmaker;

import me.theentropyshard.modmaker.gui.Gui;
import me.theentropyshard.modmaker.project.ProjectManager;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ModMaker {
    private final Path workDir;
    private final Path projectsDir;

    private final ProjectManager projectManager;

    public ModMaker() {
        instance = this;

        this.workDir = Paths.get(System.getProperty("user.dir"));
        this.projectsDir = this.workDir.resolve("projects");

        this.projectManager = new ProjectManager(this.workDir);

        new Gui();
    }

    private static ModMaker instance;

    public static ModMaker getInstance() {
        return instance;
    }

    public Path getWorkDir() {
        return this.workDir;
    }

    public Path getProjectsDir() {
        return this.projectsDir;
    }

    public ProjectManager getProjectManager() {
        return this.projectManager;
    }
}
