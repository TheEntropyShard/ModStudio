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

package me.theentropyshard.modmaker.project;

import me.theentropyshard.modmaker.cosmic.block.Block;
import me.theentropyshard.modmaker.cosmic.block.BlockState;
import me.theentropyshard.modmaker.cosmic.block.model.BlockModel;
import me.theentropyshard.modmaker.cosmic.block.model.BlockModelTexture;
import me.theentropyshard.modmaker.utils.FileUtils;
import me.theentropyshard.modmaker.utils.json.Json;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectManager {
    private final Path workDir;

    private final List<Project> projects;
    private final Map<String, Project> projectsByNamespace;

    private Project currentProject;

    public ProjectManager(Path workDir) {
        this.workDir = workDir;

        this.projects = new ArrayList<>();
        this.projectsByNamespace = new HashMap<>();
    }

    public void load() throws IOException {
        List<Path> paths = FileUtils.list(this.workDir);

        for (Path path : paths) {
            if (!Files.isDirectory(path)) {
                continue;
            }

            Path projectFile = path.resolve("project.json");

            if (!Files.exists(projectFile)) {
                continue;
            }

            Project project = Json.parse(FileUtils.readUtf8(projectFile), Project.class);
            project.setWorkDir(path);

            this.projects.add(project);
        }
    }

    public Project loadProject(Project project) throws IOException {
        Path projectDir = project.getWorkDir();

        Path blocksDir = projectDir.resolve("blocks");

        if (!Files.exists(blocksDir)) {
            return project;
        }

        Path modelsDir = projectDir.resolve("models");

        if (!Files.exists(modelsDir)) {
            return project;
        }

        Path texturesDir = projectDir.resolve("textures");

        if (!Files.exists(modelsDir)) {
            return project;
        }

        Path blocksTexturesDir = texturesDir.resolve("blocks");

        if (!Files.exists(blocksTexturesDir)) {
            return project;
        }

        List<Path> blockJsonFiles = FileUtils.list(blocksDir);

        for (Path blockJsonFile : blockJsonFiles) {
            if (!Files.isRegularFile(blockJsonFile)) {
                continue;
            }

            Block block = Json.parse(FileUtils.readUtf8(blockJsonFile), Block.class);

            for (BlockState blockState : block.getBlockStates().values()) {
                String modelJsonPath = blockState.getModelName().split(":")[1];

                Path modelJsonFile = projectDir.resolve(modelJsonPath);

                BlockModel blockModel = Json.parse(FileUtils.readUtf8(modelJsonFile), BlockModel.class);

                Map<String, BlockModelTexture> textures = blockModel.getTextures();

                if (textures == null) {
                    continue;
                }

                for (BlockModelTexture modelTexture : textures.values()) {
                    String fileName = modelTexture.getFileName().split(":")[1];

                    BufferedImage texture;

                    try (InputStream inputStream = Files.newInputStream(projectDir.resolve(fileName))) {
                        texture = ImageIO.read(inputStream);
                    }

                    modelTexture.setTexture(texture);
                }

                blockState.setBlockModel(blockModel);
            }

            project.addBlock(block);
        }

        this.cacheProject(project);

        return project;
    }

    public Project createProject(String name, String namespace, String version) throws IOException {
        Path projectDir = this.workDir.resolve(namespace);
        FileUtils.createDirectoryIfNotExists(projectDir);

        Project project = new Project(name, namespace, version);
        project.setWorkDir(projectDir);
        project.save();

        this.cacheProject(project);

        return project;
    }

    public void deleteProject(String namespace) throws IOException {
        Project project = this.getProjectByNamespace(namespace);

        if (project == null) {
            return;
        }

        FileUtils.delete(project.getWorkDir());

        this.uncacheProject(project);
    }

    public boolean projectExists(String namespace) {
        Path projectDir = this.workDir.resolve(namespace);

        return Files.exists(projectDir) && Files.isDirectory(projectDir);
    }

    private void cacheProject(Project project) {
        if (this.projectsByNamespace.containsKey(project.getName())) {
            return;
        }

        this.projects.add(project);
        this.projectsByNamespace.put(project.getName(), project);
    }

    private void uncacheProject(Project project) {
        if (!this.projectsByNamespace.containsKey(project.getName())) {
            return;
        }

        this.projects.remove(project);
        this.projectsByNamespace.remove(project.getName());
    }

    private void uncacheAll() {
        this.projects.clear();
        this.projectsByNamespace.clear();
    }

    public Project getProjectByNamespace(String namespace) {
        return this.projectsByNamespace.get(namespace);
    }

    public List<Project> getProjects() {
        return this.projects;
    }

    public Project getCurrentProject() {
        return this.currentProject;
    }

    public void setCurrentProject(Project currentProject) {
        this.currentProject = currentProject;
    }
}
