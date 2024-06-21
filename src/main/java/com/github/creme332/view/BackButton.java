package com.github.creme332.view;

import javax.swing.JButton;

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
    }
}
