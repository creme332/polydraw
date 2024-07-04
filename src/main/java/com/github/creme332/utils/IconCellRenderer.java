package com.github.creme332.utils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

import org.kordamp.ikonli.swing.FontIcon;

/**
 * Used to render an icon inside a table.
 */
public class IconCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        // Use JLabel to handle the icon rendering
        JLabel label = new JLabel();
        if (value instanceof ImageIcon) {
            label.setIcon((ImageIcon) value);
        } else if (value instanceof FontIcon) {
            label.setIcon((FontIcon) value);
        }
        // Center the icon
        label.setHorizontalAlignment(CENTER);
        return label;
    }
}
