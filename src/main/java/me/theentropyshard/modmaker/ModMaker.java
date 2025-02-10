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

import me.theentropyshard.modmaker.gui.utils.WindowClosingListener;
import me.theentropyshard.modmaker.project.DummyProject;
import me.theentropyshard.modmaker.project.Project;
import me.theentropyshard.modmaker.gui.Gui;
import me.theentropyshard.modmaker.project.ProjectManager;
import me.theentropyshard.modmaker.utils.FileUtils;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ModMaker {
    private final Args args;

    private final Path workDir;
    private final Path projectsDir;

    private final ProjectManager projectManager;

    private final ExecutorService taskPool;

    private Gui gui;

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

        this.projectManager = new ProjectManager(this.projectsDir);
        try {
            this.projectManager.load();
        } catch (IOException e) {
            System.err.println("Could not load projects");

            e.printStackTrace();

            System.exit(1);
        }

/*
        try {
            Project.load(Paths.get("F:\\Programs\\ModMakerTestDir\\base"));
        } catch (IOException e) {
            e.printStackTrace();
        }
*/

        this.taskPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        try {
            SwingUtilities.invokeAndWait(() -> {
                this.gui = new Gui("ModMaker", false);
                this.gui.getFrame().addWindowListener(new WindowClosingListener(e -> this.shutdown()));
                this.gui.show();
            });
        } catch (InterruptedException e) {
            System.err.println("Could not wait for GUI to show");

            e.printStackTrace();

            this.shutdown(1);
        } catch (InvocationTargetException e) {
            System.err.println("There was an error creating the GUI");

            e.printStackTrace();

            this.shutdown(1);
        }
    }

    private void createDirectories() throws IOException {
        FileUtils.createDirectoryIfNotExists(this.workDir);
        FileUtils.createDirectoryIfNotExists(this.projectsDir);
    }

    public void doTask(Runnable r) {
        this.taskPool.submit(r);
    }

    public synchronized void shutdown() {
        this.shutdown(0);
    }

    public synchronized void shutdown(int code) {
        if (this.shutdown) {
            return;
        }

        this.shutdown = true;

        this.taskPool.shutdown();

        try {
            this.projectManager.getCurrentProject().save();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.exit(code);
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

    public ProjectManager getProjectManager() {
        return this.projectManager;
    }

    public Gui getGui() {
        return this.gui;
    }
}
