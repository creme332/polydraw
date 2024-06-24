package com.github.creme332.view.tutorial;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.github.creme332.model.TutorialModel;

public class KeyboardTutorial extends TutorialPanel {

    public KeyboardTutorial(TutorialModel model, Icon icon) {
        super(model, icon);
        createTable();
    }

    private void createTable() {
        // Define the table data
        String[] columnNames = {"Keyboard shortcut", "Action"};
        Object[][] data = {
            {"Tab", "Go to the next column.\nIf at the end of a row, go to the first cell in the next row."},
            {"Shift+Tab", "Go to the previous column.\nIf at the end of a row, go to the last cell in the previous row."},
            {"Enter", "Go to the next row in the same column."},
            {"Home or Ctrl+Left arrow key", "Go to the first cell in a row."}
        };

        // Create the table model and set it to the table
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);
                if (row == 0) {
                    component.setBackground(Color.LIGHT_GRAY);
                    component.setFont(component.getFont().deriveFont(java.awt.Font.BOLD));
                } else {
                    component.setBackground(Color.WHITE);
                }
                return component;
            }
        };

        // Style the table
        table.setRowHeight(40);
        table.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        table.setShowGrid(true);
        table.setGridColor(Color.BLACK);
        table.setDefaultRenderer(Object.class, new TableCellRenderer());

        // Add the table to the bodyPanel
        bodyPanel.setLayout(new BorderLayout());
        bodyPanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
        bodyPanel.add(table.getTableHeader(), BorderLayout.NORTH);
        bodyPanel.add(table, BorderLayout.CENTER);
    }

    // Custom renderer to handle multi-line cells
    static class TableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            label.setVerticalAlignment(JLabel.TOP);
            label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            return label;
        }
    }
}
