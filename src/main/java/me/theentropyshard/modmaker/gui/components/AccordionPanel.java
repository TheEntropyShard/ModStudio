package me.theentropyshard.modmaker.gui.components;

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
        this.label = new JLabel(AccordionPanel.TRIANGLE_DOWN + " " + title);
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
        this.label.setText(String.format("%s %s", this.panel.isVisible() ? AccordionPanel.TRIANGLE_UP :
            AccordionPanel.TRIANGLE_DOWN, this.title));
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