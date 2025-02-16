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

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableColumnModel;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class SwingUtils {
    private static final Map<String, Icon> ICON_CACHE = new HashMap<>();

    public static Icon getIcon(String path) {
        if (SwingUtils.ICON_CACHE.containsKey(path)) {
            return SwingUtils.ICON_CACHE.get(path);
        }

        URL resource = SwingUtils.class.getResource(path);

        if (resource == null) {
            System.err.println("Could not find resource: " + path);

            return null;
        }

        Icon icon = new ImageIcon(resource);
        SwingUtils.ICON_CACHE.put(path, icon);

        return icon;
    }

    public static void startWorker(Runnable runnable) {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                runnable.run();

                return null;
            }
        }.execute();
    }

    /**
     * <a href="https://stackoverflow.com/a/27190162/19857533">https://stackoverflow.com/a/27190162/19857533</a>
     * Installs a listener to receive notification when the text of any
     * {@code JTextComponent} is changed. Internally, it installs a
     * {@link DocumentListener} on the text component's {@link Document},
     * and a {@link PropertyChangeListener} on the text component to detect
     * if the {@code Document} itself is replaced.
     *
     * @param text           any text component, such as a {@link JTextField}
     *                       or {@link JTextArea}
     * @param changeListener a listener to receieve {@link ChangeEvent}s
     *                       when the text is changed; the source object for the events
     *                       will be the text component
     * @throws NullPointerException if either parameter is null
     */
    public static void addChangeListener(JTextComponent text, ChangeListener changeListener) {
        DocumentListener documentListener = new DocumentListener() {
            private int lastChange = 0;
            private int lastNotifiedChange = 0;

            @Override
            public void insertUpdate(DocumentEvent e) {
                this.changedUpdate(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                this.changedUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                this.lastChange++;
                SwingUtilities.invokeLater(() -> {
                    if (this.lastNotifiedChange != this.lastChange) {
                        this.lastNotifiedChange = this.lastChange;
                        changeListener.stateChanged(new ChangeEvent(text));
                    }
                });
            }
        };
        text.addPropertyChangeListener("document", (PropertyChangeEvent e) -> {
            Document d1 = (Document) e.getOldValue();

            if (d1 != null) {
                d1.removeDocumentListener(documentListener);
            }

            Document d2 = (Document) e.getNewValue();

            if (d2 != null) {
                d2.addDocumentListener(documentListener);
            }

            documentListener.changedUpdate(null);
        });

        Document d = text.getDocument();

        if (d != null) {
            d.addDocumentListener(documentListener);
        }
    }

    public static Action newAction(ActionListener actionListener) {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionListener.actionPerformed(e);
            }
        };
    }

    public static BufferedImage loadImageFromBase64(String base64) {
        if (base64 == null) {
            return null;
        }

        byte[] decoded = Base64.getMimeDecoder().decode(base64);

        try {
            return ImageIO.read(new ByteArrayInputStream(decoded));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Icon loadIconFromBase64(String base64) {
        return new ImageIcon(SwingUtils.loadImageFromBase64(base64));
    }

    public static BufferedImage getImage(String path) {
        try (InputStream inputStream = Objects.requireNonNull(SwingUtils.class.getResourceAsStream(path))) {
            return ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static BufferedImage loadImageFromFile(Path file) throws IOException {
        return ImageIO.read(file.toFile());
    }

    public static void centerWindow(Window window, int screen) {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] allDevices = env.getScreenDevices();

        if (screen < 0 || screen >= allDevices.length) {
            screen = 0;
        }

        Rectangle bounds = allDevices[screen].getDefaultConfiguration().getBounds();
        window.setLocation(
            ((bounds.width - window.getWidth()) / 2) + bounds.x,
            ((bounds.height - window.getHeight()) / 2) + bounds.y
        );
    }

    public static void removeActionListeners(AbstractButton button) {
        for (ActionListener listener : button.getActionListeners()) {
            button.removeActionListener(listener);
        }
    }

    public static void removeMouseListeners(Component component) {
        for (MouseListener listener : component.getMouseListeners()) {
            component.removeMouseListener(listener);
        }
    }

    public static void setJTableColumnsWidth(JTable table, double... percentages) {
        TableColumnModel columnModel = table.getColumnModel();
        int tablePreferredWidth = table.getWidth();

        double total = 0;
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            total += percentages[i];
        }

        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setPreferredWidth((int) (tablePreferredWidth * (percentages[i] / total)));
        }
    }
}
