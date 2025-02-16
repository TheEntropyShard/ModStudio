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

package me.theentropyshard.modstudio.gui.utils;

import javax.swing.*;
import java.util.concurrent.ExecutionException;

public abstract class Worker<T, V> extends SwingWorker<T, V> {
    private final String name;

    public Worker(String name) {
        this.name = name;
    }

    @Override
    protected final T doInBackground() throws Exception {
        try {
            return this.work();
        } catch (Exception e) {
            System.err.println("Exception while " + this.name);

            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void done() {
        T t;

        try {
            t = this.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();

            return;
        }

        if (t == null) {
            System.err.println("Got null value while " + this.name);

            return;
        }

        this.finish(t);
    }

    protected abstract T work() throws Exception;

    protected abstract void finish(T t);
}
