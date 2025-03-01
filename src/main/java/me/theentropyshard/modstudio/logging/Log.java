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

package me.theentropyshard.modstudio.logging;

import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class Log {
    private static final BlockingQueue<LogEvent> EVENT_QUEUE = new ArrayBlockingQueue<>(128);
    private static final boolean WRAP_ERR = true;

    private static boolean started;

    public static void start() {
        if (Log.started) {
            return;
        }

        new LogQueueProcessor(Log.EVENT_QUEUE).start();

        // System.setOut(new SystemOutInterceptor(System.out, LogLevel.DEBUG));

        if (Log.WRAP_ERR) {
            System.setErr(new SystemOutInterceptor(System.err, LogLevel.ERROR));
        }

        Log.started = true;
    }

    public static void info(String message) {
        Log.EVENT_QUEUE.offer(new LogEvent(LogLevel.INFO, message));
    }

    public static void warn(String message) {
        Log.EVENT_QUEUE.offer(new LogEvent(LogLevel.WARN, message));
    }

    public static void error(String message) {
        Log.EVENT_QUEUE.offer(new LogEvent(LogLevel.ERROR, message));
    }

    public static void debug(String message) {
        Log.EVENT_QUEUE.offer(new LogEvent(LogLevel.DEBUG, message));
    }

    public static void error(String message, Throwable t) {
        Log.error(message);
        Log.error(t);
    }

    public static void error(Throwable t) {
        if (Log.WRAP_ERR) {
            t.printStackTrace();
        } else {
            CharArrayWriter writer = new CharArrayWriter();
            t.printStackTrace(new PrintWriter(writer));
            Log.error(writer.toString());
        }
    }
}