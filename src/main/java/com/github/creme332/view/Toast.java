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
    private static final int MAX_TITLE_LINE_SIZE = 26;

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

        titleLabel = new JLabel();
        titleLabel.putClientProperty("FlatLaf.style", "font: $h3.font");
        titleLabel.setForeground(Color.WHITE);

        instructionLabel = new JLabel();
        instructionLabel.setForeground(Color.WHITE);
        instructionLabel.setBorder(new EmptyBorder(10, 0, 0, 0));

        setTitleText(defaultMode.getTitle());
        setInstructionText(defaultMode.getInstructions());

        add(titleLabel, BorderLayout.NORTH);
        add(instructionLabel, BorderLayout.CENTER);
        
        adjustSize();
    }

    public String getTitleText() {
        return titleLabel.getText();
    }

    public void setTitleText(String text) {
        if (text.length() > MAX_TITLE_LINE_SIZE) {
            titleLabel.setText("<html>" + formatHtmlText(text, MAX_TITLE_LINE_SIZE) + "</html>");
        } else {
            titleLabel.setText(text);
        }
        adjustSize();
    }

    public String getInstructionText() {
        return instructionLabel.getText();
    }

    public void setInstructionText(String text) {
        if (text.length() > MAX_LINE_SIZE) {
            instructionLabel.setText("<html>" + formatHtmlText(text, MAX_LINE_SIZE) + "</html>");
        } else {
            instructionLabel.setText(text);
        }
        adjustSize();
    }

    private String formatHtmlText(String text, int lineSize) {
        StringBuilder htmlText = new StringBuilder();
        int index = 0;
        while (index < text.length()) {
            htmlText.append(text, index, Math.min(index + lineSize, text.length()));
            if (index + lineSize < text.length()) {
                htmlText.append("<br>");
            }
            index += lineSize;
        }
        return htmlText.toString();
    }

    private void adjustSize() {
        int titleLines = (titleLabel.getText().length() / MAX_TITLE_LINE_SIZE) + 1;
        int instructionLines = (instructionLabel.getText().length() / MAX_LINE_SIZE) + 1;

        int newHeight = 100 + (titleLines - 1) * 20 + (instructionLines - 1) * 20;

        if (newHeight > getPreferredSize().height) {
            setPreferredSize(new Dimension(400, newHeight));
            revalidate();
            repaint();
        }
    }
}
