package me.theentropyshard.modstudio.gui.components;

import me.theentropyshard.modstudio.gui.Icons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/*
 * https://github.com/aterai/java-swing-tips/blob/master/AccordionPanel/src/java/example/MainPanel.java
 */
public class AccordionPanel extends JPanel {
    private static final String TRIANGLE_UP = "▲";
    private static final String TRIANGLE_DOWN = "▼";

    private final String title;
    private final JLabel label;
    private final JPanel panel;

    public AccordionPanel(String title, JPanel panel) {
        super(new BorderLayout());
        this.panel = panel;
        this.title = title;
        this.label = new JLabel(title) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.setColor(UIManager.getColor("Component.borderColor"));
                g.drawLine(g.getFontMetrics().stringWidth(getText()) + 28, getHeight() / 2, getWidth(), getHeight() / 2);
            }
        };
        this.label.setIcon(Icons.get("chevronRight"));
        this.label.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                AccordionPanel.this.toggleVisibility();
            }
        });
        this.label.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 2));
        this.add(this.label, BorderLayout.NORTH);
        this.panel.setVisible(false);
        this.panel.setOpaque(true);
        this.add(this.panel);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension d = this.label.getPreferredSize();

        if (this.panel.isVisible()) {
            d.height += this.panel.getPreferredSize().height;
        }

        return d;
    }

    @Override
    public Dimension getMaximumSize() {
        Dimension d = this.getPreferredSize();
        d.width = Short.MAX_VALUE;

        return d;
    }

    protected void toggleVisibility() {
        this.panel.setVisible(!this.panel.isVisible());
        this.label.setIcon(this.panel.isVisible() ? Icons.get("chevronDown") : Icons.get("chevronRight"));
        this.revalidate();
        // fireExpansionEvent();
        EventQueue.invokeLater(() -> this.panel.scrollRectToVisible(this.panel.getBounds()));
    }

    // protected Vector<ExpansionListener> expansionListenerList = new Vector<>();

    // public void addExpansionListener(ExpansionListener listener) {
    //   if (!expansionListenerList.contains(listener)) {
    //     expansionListenerList.add(listener);
    //   }
    // }

    // public void removeExpansionListener(ExpansionListener listener) {
    //   expansionListenerList.remove(listener);
    // }

    // public void fireExpansionEvent() {
    //   Vector list = (Vector) expansionListenerList.clone();
    //   Enumeration enm = list.elements();
    //   ExpansionEvent e = new ExpansionEvent(this);
    //   while (enm.hasMoreElements()) {
    //     ExpansionListener listener = (ExpansionListener) enm.nextElement();
    //     listener.expansionStateChanged(e);
    //   }
    // }
}