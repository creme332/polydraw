package com.github.creme332.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import com.github.creme332.model.Mode;

public class Toast extends JPanel {
    private JLabel titleLabel;
    private JLabel instructionLabel;

    public Toast() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 80));

        titleLabel = new JLabel();
        titleLabel.putClientProperty("FlatLaf.style", "font: $h3.font");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(new EmptyBorder(10, 10, 10, 10));

        instructionLabel = new JLabel();
        instructionLabel.setForeground(Color.WHITE);
        instructionLabel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);
        textPanel.add(titleLabel);
        textPanel.add(instructionLabel);

        setBackground(new Color(47, 47, 51));
        setBorder(BorderFactory.createLineBorder(new Color(45, 45, 45), 1));

        add(textPanel, BorderLayout.CENTER);
        setVisible(false);
    }

    public String getTitleText() {
        return titleLabel.getText();
    }

    public void setTitleText(String text) {
        titleLabel.setText(text);
    }

    public String getInstructionText() {
        return instructionLabel.getText();
    }

    public void setInstructionText(String text) {
        instructionLabel.setText(text);
    }
}
