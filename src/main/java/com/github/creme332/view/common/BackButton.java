package com.github.creme332.view.common;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.UIManager;

import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.swing.FontIcon;

public class BackButton extends JButton {
    public BackButton() {
        super("Back");
        setOpaque(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);

        FontIcon icon = FontIcon.of(BootstrapIcons.ARROW_LEFT_CIRCLE);
        icon.setIconSize(40);
        setIcon(icon);

        // Add a mouse listener to handle hover effect
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setContentAreaFilled(true);
                setBackground(UIManager.getColor("MenuItem.background"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setContentAreaFilled(false);
            }
        });
    }
}
