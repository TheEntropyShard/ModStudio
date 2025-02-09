package me.theentropyshard.modmaker.cosmic;

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
import java.util.List;

public class Project {
    private final List<Block> blocks;

    public Project() {
        this.blocks = new ArrayList<>();
    }

    public static Project load(Path modDir) throws IOException {
        Project project = new Project();

        if (!Files.exists(modDir)) {
            throw new IOException("Mod at " + modDir + " does not exist!");
        }

        Path blocksDir = modDir.resolve("blocks");

        if (!Files.exists(blocksDir)) {
            throw new IOException("Mod at " + modDir + " does not have blocks!");
        }

        Path modelsDir = modDir.resolve("models");

        if (!Files.exists(modelsDir)) {
            throw new IOException("Mod at " + modDir + " does not have models!");
        }

        Path texturesDir = modDir.resolve("textures");

        if (!Files.exists(modelsDir)) {
            throw new IOException("Mod at " + modDir + " does not have textures!");
        }

        Path blocksTexturesDir = texturesDir.resolve("blocks");

        if (!Files.exists(blocksTexturesDir)) {
            throw new IOException("Mod at " + modDir + " does not have blocks textures!");
        }

        List<Path> blockJsonFiles = FileUtils.list(blocksDir);

        for (Path blockJsonFile : blockJsonFiles) {
            if (!Files.isRegularFile(blockJsonFile)) {
                continue;
            }

            Block block = Json.parse(FileUtils.readUtf8(blockJsonFile), Block.class);

            for (BlockState blockState : block.getBlockStates().values()) {
                String modelJsonPath = blockState.getModelName().split(":")[1];

                Path modelJsonFile = modDir.resolve(modelJsonPath);

                BlockModel blockModel = Json.parse(FileUtils.readUtf8(modelJsonFile), BlockModel.class);

                for (BlockModelTexture modelTexture : blockModel.getTextures().values()) {
                    String fileName = modelTexture.getFileName().split(":")[1];

                    BufferedImage texture;

                    try (InputStream inputStream = Files.newInputStream(modDir.resolve(fileName))) {
                        texture = ImageIO.read(inputStream);
                    }

                    modelTexture.setTexture(texture);
                }
            }
        }

        return project;
    }

    public void addBlock(Block block) {
        this.blocks.add(block);
    }

    public void removeBlock(Block block) {
        this.blocks.remove(block);
    }
}
