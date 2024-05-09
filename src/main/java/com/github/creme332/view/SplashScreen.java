package com.github.creme332.view;

import java.awt.*;
import javax.swing.*;

public class SplashScreen extends JPanel {
    JLabel text = new JLabel("polydraw");

    SplashScreen() {
        setLayout(new GridBagLayout());

        text.putClientProperty("FlatLaf.style", "font: 340% $large.font");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        this.add(text);
    }
}