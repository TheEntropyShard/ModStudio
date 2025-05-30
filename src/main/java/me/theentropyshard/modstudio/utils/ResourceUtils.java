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

package me.theentropyshard.modstudio.utils;

import me.theentropyshard.modstudio.gui.view.project.BlockEditView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public final class ResourceUtils {
    public static byte[] readToByteArray(String path) throws IOException {
        return StreamUtils.readToByteArray(ResourceUtils.class.getResourceAsStream(path));
    }

    public static String readToString(String path) throws IOException {
        return StreamUtils.readToString(ResourceUtils.class.getResourceAsStream(path));
    }

    public static BufferedImage loadImage(String path) {
        BufferedImage image;

        try (InputStream inputStream = BlockEditView.class.getResourceAsStream(path)) {
            image = ImageIO.read(Objects.requireNonNull(inputStream));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return image;
    }

    private ResourceUtils() {
        throw new UnsupportedOperationException();
    }
}
