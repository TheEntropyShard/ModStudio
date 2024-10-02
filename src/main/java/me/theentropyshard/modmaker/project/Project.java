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

package me.theentropyshard.modmaker.project;

import me.theentropyshard.modmaker.cosmic.block.Block;
import me.theentropyshard.modmaker.utils.FileUtils;
import me.theentropyshard.modmaker.utils.Json;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Project {
    private String name;
    private String namespace;
    private String version;

    private transient List<ProjectBlock> blocks;

    private transient Path workDir;

    public Project() {

    }

    public static Project create(Path workDir, String name, String namespace, String version) throws IOException {
        Project project = new Project();
        project.setName(name);
        project.setNamespace(namespace);
        project.setVersion(version);

        Path projectDir = workDir.resolve(name);
        project.setWorkDir(projectDir);

        FileUtils.createDirectoryIfNotExists(projectDir);

        Path projectFile = projectDir.resolve(ProjectManager.PROJECT_FILE_NAME);
        FileUtils.writeUtf8(projectFile, Json.write(project));

        return project;
    }

    public static Project load(Path projectDir) throws IOException {
        Path projectFile = projectDir.resolve(ProjectManager.PROJECT_FILE_NAME);

        if (!Files.exists(projectFile)) {
            return null;
        }

        if (FileUtils.isEmpty(projectFile)) {
            return null;
        }

        Project project = Json.parse(FileUtils.readUtf8(projectFile), Project.class);
        project.setWorkDir(projectDir);

        return project;
    }

    public void save() throws IOException {
        FileUtils.writeUtf8(this.workDir.resolve(ProjectManager.PROJECT_FILE_NAME), Json.write(this));
    }

    public void loadWorkspace() throws IOException {
        this.blocks = new ArrayList<>();

        Path blocksDir = this.workDir.resolve("blocks");

        if (!Files.exists(blocksDir) || !Files.isDirectory(blocksDir)) {
            return;
        }

        for (Path blockJsonFile : FileUtils.list(blocksDir)) {
            String blockJson = FileUtils.readUtf8(blockJsonFile);

            this.blocks.add(new ProjectBlock(Json.parse(blockJson, Block.class)));
        }
    }

    public List<ProjectBlock> getBlocks() {
        return this.blocks;
    }

    public Path getWorkDir() {
        return this.workDir;
    }

    public void setWorkDir(Path workDir) {
        this.workDir = workDir;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
