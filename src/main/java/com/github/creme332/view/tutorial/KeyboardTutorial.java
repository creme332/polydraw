package com.github.creme332.view.tutorial;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.github.creme332.model.TutorialModel;
import com.github.creme332.utils.exception.InvalidIconSizeException;
import com.github.creme332.utils.exception.InvalidPathException;
import static com.github.creme332.utils.IconLoader.getScaledDimension;
import static com.github.creme332.utils.IconLoader.loadIcon;

public class KeyboardTutorial extends TutorialPanel {

    private static final String IMAGE_PATH_PREFIX = "/images/tutorials/getting-started/";
    private static final TutorialModel KEYBOARD_TUTORIAL_MODEL = new TutorialModel("Keyboard Shortcuts");

    public KeyboardTutorial() throws InvalidPathException, InvalidIconSizeException {
        super(KEYBOARD_TUTORIAL_MODEL, loadIcon(IMAGE_PATH_PREFIX + "background.png",
                getScaledDimension(new Dimension(753, 453), TutorialCard.IMAGE_DIMENSION)));
        createTable();
    }

    private void createTable() {
        // Define the table data
        String[] columnNames = { "Keyboard shortcut", "Action" };
        Object[][] data = {
                { "Tab", "Go to the next column.\nIf at the end of a row, go to the first cell in the next row." },
                { "Shift+Tab",
                        "Go to the previous column.\nIf at the end of a row, go to the last cell in the previous row." },
                { "Enter", "Go to the next row in the same column." },
                { "Home or Ctrl+Left arrow key", "Go to the first cell in a row." }
        };

        // Create the table model and set it to the table
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model) {

            @Override
            public void doLayout() {
                for (int row = 0; row < getRowCount(); row++) {
                    int rowHeight = getRowHeight();
                    for (int column = 0; column < getColumnCount(); column++) {
                        Component comp = prepareRenderer(getCellRenderer(row, column), row, column);
                        rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
                    }
                    setRowHeight(row, rowHeight);
                }
                super.doLayout();
            }
        };

        // Style the table
        table.setSelectionBackground(new Color(238, 238, 238));
        table.setSelectionForeground(Color.black);
        table.setShowGrid(true);
        table.setDefaultRenderer(Object.class, new TableCellRenderer());
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(new Color(227, 227, 227));

        // Add the table panel to the bodyPanel
        bodyPanel.add(tableHeader, BorderLayout.NORTH);
        bodyPanel.add(table, BorderLayout.CENTER);

    }

    // Custom renderer to handle multi-line cells
    static class TableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                    column);
            label.setVerticalAlignment(JLabel.TOP);
            label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            label.setHorizontalAlignment(SwingConstants.LEFT);
            label.setVerticalAlignment(SwingConstants.TOP);
            // Wrap text
            String text = "<html>" + value.toString().replace("\n", "<br>") + "</html>";
            label.setText(text);

            return label;
        }
    }
}
