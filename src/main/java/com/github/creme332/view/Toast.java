package com.github.creme332.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.github.creme332.model.Mode;
import com.github.creme332.utils.DesktopApi;
import com.github.creme332.utils.DesktopApi.EnumOS;

import java.awt.*;

public class Toast extends JPanel {
    private JLabel titleLabel;
    private JLabel instructionLabel;

    /**
     * Maximum number of characters on a line before line wrapping occurs.
     */
    private static final int MAX_LINE_SIZE = 30;

    public Toast(Mode defaultMode) {
        setLayout(new BorderLayout());
        setBackground(new Color(47, 47, 51));

        if (DesktopApi.getOs() == EnumOS.LINUX) {
            setPreferredSize(new Dimension(400, 100));
        } else {
            setPreferredSize(new Dimension(400, 120));
        }

        setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
        putClientProperty("FlatLaf.style", "arc: 10");

        titleLabel = new JLabel(defaultMode.getTitle());
        titleLabel.putClientProperty("FlatLaf.style", "font: $h3.font");
        titleLabel.setForeground(Color.WHITE);

        instructionLabel = new JLabel();
        instructionLabel.setForeground(Color.WHITE);
        instructionLabel.setBorder(new EmptyBorder(10, 0, 0, 0));
        setInstructionText(defaultMode.getInstructions());

        add(titleLabel, BorderLayout.NORTH);
        add(instructionLabel, BorderLayout.CENTER);
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
        if (text.length() > MAX_LINE_SIZE) {
            instructionLabel.setText("<html>" + formatHtmlText(text) + "</html>");
        } else {
            instructionLabel.setText(text);
        }
    }

    private String formatHtmlText(String text) {
        StringBuilder htmlText = new StringBuilder();
        int index = 0;
        while (index < text.length()) {
            htmlText.append(text.substring(index, Math.min(index + MAX_LINE_SIZE, text.length())));
            if (index + MAX_LINE_SIZE < text.length()) {
                htmlText.append("<br>");
            }
            index += MAX_LINE_SIZE;
        }
        return htmlText.toString();
    }
}
