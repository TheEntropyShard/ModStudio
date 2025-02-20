package me.theentropyshard.modstudio.gui.view.welcome;

import me.theentropyshard.modstudio.gui.Icons;
import me.theentropyshard.modstudio.gui.components.Card;
import me.theentropyshard.modstudio.gui.utils.MouseClickListener;
import net.miginfocom.swing.MigLayout;

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
        super(new MigLayout("wrap, insets 0, gap 5 5", "[center]", "[center][top]"));

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
        this.add(this.button);

        this.label = new JLabel(text);
        this.label.setHorizontalAlignment(JLabel.CENTER);
        this.label.setBorder(new EmptyBorder(10, 0, 0, 0));
        this.label.setFont(this.label.getFont().deriveFont(13.0f));
        this.add(this.label);
    }

    public void addOnClickListener(Runnable r) {
        this.button.addMouseListener(new MouseClickListener(e -> r.run()));
    }
}
