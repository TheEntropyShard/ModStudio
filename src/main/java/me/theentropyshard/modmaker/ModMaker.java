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

package me.theentropyshard.modmaker;

import me.theentropyshard.modmaker.gui.Gui;
import me.theentropyshard.modmaker.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Path;

public class ModMaker {
    private final Args args;

    private final Path workDir;
    private final Path projectsDir;

    private final Gui gui;

    private boolean shutdown;

    public ModMaker(Args args, Path workDir) {
        ModMaker.instance = this;

        if (args.hasUnknownOptions()) {
            System.out.println("[WARN]: Unknown options: " + args.getUnknownOptions());
        }

        this.args = args;

        this.workDir = workDir;
        this.projectsDir = this.workDir.resolve("projects");

        try {
            this.createDirectories();
        } catch (IOException e) {
            System.err.println("Could not create ModMaker directories");

            e.printStackTrace();

            System.exit(1);
        }

        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));

        this.gui = new Gui();
        this.gui.show();
    }

    private void createDirectories() throws IOException {
        FileUtils.createDirectoryIfNotExists(this.workDir);
        FileUtils.createDirectoryIfNotExists(this.projectsDir);
    }

    public synchronized void shutdown() {
        if (this.shutdown) {
            return;
        }

        this.shutdown = true;
    }

    private static ModMaker instance;

    public static ModMaker getInstance() {
        return ModMaker.instance;
    }

    public Args getArgs() {
        return this.args;
    }

    public Path getWorkDir() {
        return this.workDir;
    }

    public Path getProjectsDir() {
        return this.projectsDir;
    }
}
