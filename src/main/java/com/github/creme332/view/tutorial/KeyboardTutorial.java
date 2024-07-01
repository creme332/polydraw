package com.github.creme332.view.tutorial;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.BadLocationException;

import com.github.creme332.model.TutorialModel;
import com.github.creme332.utils.exception.InvalidIconSizeException;
import com.github.creme332.utils.exception.InvalidPathException;
import static com.github.creme332.utils.IconLoader.loadSVGIcon;

public class KeyboardTutorial extends TutorialPanel {

    private static final String IMAGE_PATH_PREFIX = "/images/tutorials/getting-started/";
    private static final TutorialModel KEYBOARD_TUTORIAL_MODEL = new TutorialModel("Keyboard Shortcuts");

    public KeyboardTutorial() throws InvalidPathException, InvalidIconSizeException {
        super(KEYBOARD_TUTORIAL_MODEL, loadSVGIcon("/icons/keyboard.svg", TutorialCard.IMAGE_DIMENSION));

        try {
            // Insert text
            doc.insertString(doc.getLength(),
                    "In this tutorial you will learn how to draw a line using DDA or Bresenham line algorithm.\n\n",
                    regular);
            textPane.setPreferredSize(new Dimension(200, 200));
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        createTable();
    }

    private void createTable() {
        // Define the table data with application-specific shortcuts
        String[] columnNames = { "Keyboard Shortcut", "Action" };
        Object[][] data = {
                { "Ctrl + c", "Copy selected shape" },
                { "Ctrl + v", "Paste a previously copied shape" },
                { "Ctrl + z", "Undo" },
                { "Ctrl + Shift + z", "Redo" },
                { "Ctrl + p", "Export canvas" },
                { "Ctrl + +", "Zoom in" },
                { "Ctrl + -", "Zoom out" },
                { "Ctrl + 0", "Zoom 100%" },

                { "Esc", "Go back" },
                { "1", "Select cursor menu" },
                { "2", "Select line menu" },
                { "3", "Select circle menu" },
                { "4", "Select ellipse menu" },
                { "5", "Select polygon menu" },
                { "6", "Select transformations menu" },
                { "7", "Select move menu" },
                { "8", "Select delete menu" },

                { "Alt + h", "Open help center" },
                { "Ctrl + Shift + s", "Toggle sidebar visibility" },

        };

        // Create the table model and set it to be non-editable
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make the cells non-editable
            }
        };

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

        // Add the table to a JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(750, 200));

        // Add the scroll pane to the bodyPanel
        bodyPanel.add(tableHeader, BorderLayout.NORTH);
        bodyPanel.add(scrollPane, BorderLayout.CENTER);
    }

    // Custom renderer to handle multi-line cells
    static class TableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            JLabel label = new JLabel(value.toString());
            label.setVerticalAlignment(javax.swing.SwingConstants.TOP);
            label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            label.setHorizontalAlignment(SwingConstants.LEFT);

            if (column == 0) {
                // use monospace style for keyboard keys
                label.putClientProperty("FlatLaf.style", "font: 105% $monospaced.font");
            } else {
                // Wrap text
                label.setText("<html>" + value.toString().replace("\n", "<br>") + "</html>");
            }

            return label;
        }
    }
}
