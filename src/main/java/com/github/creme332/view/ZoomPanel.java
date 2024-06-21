package com.github.creme332.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.swing.FontIcon;

public class ZoomPanel extends JPanel {

    private JButton homeButton = new CircularButton();
    private JButton zoomInButton = new CircularButton();
    private JButton zoomOutButton = new CircularButton();
    private JButton fullScreenButton = new CircularButton();

    public ZoomPanel() {
        setOpaque(false);
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // create buttons for zoom panel
        homeButton = createZoomPanelButton(BootstrapIcons.HOUSE);
        zoomInButton = createZoomPanelButton(BootstrapIcons.ZOOM_IN);
        zoomOutButton = createZoomPanelButton(BootstrapIcons.ZOOM_OUT);
        fullScreenButton = createZoomPanelButton(BootstrapIcons.ARROWS_FULLSCREEN);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 0, 0, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;

        this.add(homeButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(zoomInButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(zoomOutButton, gbc);
        gbc.gridy = 3;
        this.add(fullScreenButton, gbc); // Add new button to panel
    }

    private JButton createZoomPanelButton(Ikon ikon) {
        final int ICON_SIZE = 30;
        final int BUTTON_SIZE = 60;
        final Color gray = new Color(116, 116, 116);

        JButton btn = new CircularButton();
        btn.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));

        FontIcon icon = FontIcon.of(ikon, ICON_SIZE);
        icon.setIconColor(gray);
        btn.setIcon(icon);
        return btn;
    }

    public JButton getHomeButton() {
        return homeButton;
    }

    public JButton getZoomInButton() {
        return zoomInButton;
    }

    public JButton getZoomOutButton() {
        return zoomOutButton;
    }

    public JButton getFullScreenButton() {
        return fullScreenButton;
    }
}
