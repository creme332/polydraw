package com.github.creme332.view;

import javax.swing.*;
import java.awt.*;

public class Toast extends JPanel {
    private JLabel titleLabel;
    private JLabel instructionLabel;

    public Toast() {
        setLayout(new BorderLayout());
        
        titleLabel = new JLabel();
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);

        instructionLabel = new JLabel();
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        instructionLabel.setForeground(Color.GRAY);
        
        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);
        textPanel.add(titleLabel);
        textPanel.add(instructionLabel);

        setBackground(new Color(45, 45, 45));
        setBorder(BorderFactory.createLineBorder(new Color(45, 45, 45), 1));
        
        add(textPanel, BorderLayout.CENTER);
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
