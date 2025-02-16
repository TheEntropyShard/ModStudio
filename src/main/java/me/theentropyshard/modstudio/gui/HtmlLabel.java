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

package me.theentropyshard.modstudio.gui;

import javax.swing.*;

public class HtmlLabel extends JLabel {
    private HtmlLabel(String text) {
        super(text);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private final StringBuilder builder;

        public Builder() {
            this.builder = new StringBuilder();
        }

        public Builder text(String text) {
            this.builder.append(text);

            return this;
        }

        public Builder bold(String text) {
            this.builder.append("<b>").append(text).append("</b>");

            return this;
        }

        public Builder italic(String text) {
            this.builder.append("<i>").append(text).append("</i>");

            return this;
        }

        public Builder underline(String text) {
            this.builder.append("<u>").append(text).append("</u>");

            return this;
        }

        public Builder strikethrough(String text) {
            this.builder.append("<s>").append(text).append("</s>");

            return this;
        }

        public HtmlLabel build() {
            return new HtmlLabel("<html>" + this.builder + "</html>");
        }
    }
}
