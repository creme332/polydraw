package com.github.creme332.view;

import java.awt.*;
import javax.swing.*;

import com.github.creme332.utils.IconLoader;
import com.github.creme332.utils.exception.InvalidPathException;

public class SplashScreen extends JPanel {
    JLabel text = new JLabel("polydraw");

    SplashScreen() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        text.putClientProperty("FlatLaf.style", "font: 260% $large.font");
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(text, gbc);

        JLabel x = new JLabel();
        try {
            x.setIcon(IconLoader.loadIcon("/icons/icosahedron.png"));
        } catch (InvalidPathException e) {
            e.printStackTrace();
            System.exit(0);
        }

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(x, gbc);
    }
}