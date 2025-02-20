package me.theentropyshard.modstudio.gui.view.welcome;

import me.theentropyshard.modstudio.gui.Icons;
import me.theentropyshard.modstudio.gui.components.Card;
import me.theentropyshard.modstudio.gui.utils.MouseClickListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class WelcomeViewButton extends JPanel {
    private static final int SIDE_SIZE = 64;
    private static final Dimension PREFERRED_SIZE = new Dimension(
        WelcomeViewButton.SIDE_SIZE, WelcomeViewButton.SIDE_SIZE
    );

    private final Card button;
    private final JLabel label;

    public WelcomeViewButton(String icon, String text) {
        super(new BorderLayout());

        this.button = new Card(0, 10) {
            @Override
            public Dimension getMinimumSize() {
                return WelcomeViewButton.PREFERRED_SIZE;
            }

            @Override
            public Dimension getPreferredSize() {
                return WelcomeViewButton.PREFERRED_SIZE;
            }

            @Override
            public Dimension getMaximumSize() {
                return WelcomeViewButton.PREFERRED_SIZE;
            }
        };
        this.button.setLayout(new BorderLayout());
        this.button.add(new JLabel(Icons.get(icon).derive(32, 32)), BorderLayout.CENTER);
        this.add(this.button, BorderLayout.CENTER);

        this.add(Box.createHorizontalStrut(3), BorderLayout.WEST);
        this.add(Box.createHorizontalStrut(3), BorderLayout.EAST);

        this.label = new JLabel(text);
        this.label.setHorizontalAlignment(JLabel.CENTER);
        this.label.setBorder(new EmptyBorder(10, 0, 0, 0));
        this.label.setFont(this.label.getFont().deriveFont(13.0f));
        this.add(this.label, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public void addOnClickListener(Runnable r) {
        this.button.addMouseListener(new MouseClickListener(e -> r.run()));
    }
}
