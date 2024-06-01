package com.github.creme332.view;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.swing.FontIcon;

public class SideMenuPanel extends JPanel {
    JLabel label = new JLabel("This is a side menu");
    JButton closeButton = new JButton();

    public SideMenuPanel() {
        add(label);
        this.setBackground(Color.cyan);

        closeButton.setIcon(FontIcon.of(BootstrapIcons.X, 35));
        this.add(closeButton);

    }

    public JButton getCloseButton() {
        return closeButton;
    }
}
