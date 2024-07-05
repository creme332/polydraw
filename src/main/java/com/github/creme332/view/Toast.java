package com.github.creme332.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.github.creme332.model.Mode;

import java.awt.*;

public class Toast extends JPanel {
    private JLabel titleLabel;
    private JLabel instructionLabel;

    public Toast(Mode defaultMode) {
        setLayout(new BorderLayout());
        setBackground(new Color(47, 47, 51));

        setBorder(new EmptyBorder(new Insets(10, 20, 20, 20)));
        putClientProperty("FlatLaf.style", "arc: 10");

        titleLabel = new JLabel(defaultMode.getTitle());
        titleLabel.putClientProperty("FlatLaf.style", "font: $large.font");
        titleLabel.setForeground(Color.WHITE);

        instructionLabel = new JLabel(defaultMode.getInstructions());
        instructionLabel.setForeground(Color.WHITE);
        instructionLabel.setBorder(new EmptyBorder(7, 0, 0, 0));

        add(titleLabel, BorderLayout.NORTH);
        add(instructionLabel, BorderLayout.CENTER);
    }

    public void updateInfo(Mode defaultMode) {
        titleLabel.setText(defaultMode.getTitle());
        instructionLabel.setText(defaultMode.getInstructions());
    }
}
