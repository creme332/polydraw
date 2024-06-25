package com.github.creme332.view.tutorial;

import static com.github.creme332.utils.IconLoader.loadIcon;
import static com.github.creme332.utils.IconLoader.getScaledDimension;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.swing.FontIcon;

import com.github.creme332.model.TutorialModel;
import com.github.creme332.utils.IconLoader;
import com.github.creme332.utils.exception.InvalidIconSizeException;
import com.github.creme332.utils.exception.InvalidPathException;

public class GettingStartedTutorial extends TutorialPanel {

    private static final String IMAGE_PATH_PREFIX = "/images/tutorials/getting-started/";
    private static final TutorialModel GETTING_STARTED_MODEL = new TutorialModel("Getting Started");

    public GettingStartedTutorial() throws InvalidPathException, InvalidIconSizeException {
        super(GETTING_STARTED_MODEL, loadIcon(IMAGE_PATH_PREFIX + "background.png",
                getScaledDimension(new Dimension(753, 453), TutorialCard.IMAGE_DIMENSION)));
        // remove bodyPanel since default layout is not being used
        this.remove(bodyPanel);

        // Create a main panel to hold all content with scrollable area
        JPanel mainPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Add Introduction
        JLabel introLabel = new JLabel(
                "<html><h1>Getting Started Tutorial</h1><p>Welcome to Polydraw! This tutorial will guide you through the various components and features of the interface to help you get started.</p></html>");
        mainPanel.add(introLabel, gbc);
        gbc.gridy++;

        // Add ZoomPanel explanation
        JTextArea zoomPanelExplanation = createSectionText("Zoom Panel",
                "The zoom panel is located on the right side of the application and allows you to control the zoom level and view of the canvas. The buttons include:");
        mainPanel.add(zoomPanelExplanation, gbc);
        gbc.gridy++;

        // Add ZoomPanel buttons as a table
        JPanel zoomPanelTable = createButtonTable(new Object[][] {
                { FontIcon.of(BootstrapIcons.HOUSE, 30), "Home", "Reset the view to the home position." },
                { FontIcon.of(BootstrapIcons.ZOOM_IN, 30), "Zoom In", "Zoom into the view." },
                { FontIcon.of(BootstrapIcons.ZOOM_OUT, 30), "Zoom Out", "Zoom out of the view." },
                { FontIcon.of(BootstrapIcons.ARROWS_FULLSCREEN, 30), "Full Screen", "Toggle full-screen mode." }
        });
        mainPanel.add(zoomPanelTable, gbc);
        gbc.gridy++;

        // Add Toast explanation
        JTextArea toastExplanation = createSectionText("Toast Notifications",
                "Toast notifications provide quick information about the current mode or action being performed. The toast includes a title and instructions, helping you understand what is expected at each step.");
        mainPanel.add(toastExplanation, gbc);
        gbc.gridy++;

        // Add Toast explanation as a table
        JPanel toastTable = createTextTable(new Object[][] {
                { "Mode", "Drag or select object" },
                { "Freehand Shape", "Sketch a function or geometric object" },
                { "Line: DDA", "Select two points or positions" },
                { "Line: Bresenham", "Select two points or positions" },
                { "Circle with Center through Point", "Select center point, then point on circle" },
                { "Circle: Center & Radius", "Select center point, then enter radius" },
                { "Ellipse", "Select two foci, then point on ellipse" },
                { "Polygon", "Select all vertices, then first vertex again" },
                { "Regular Polygon", "Select two points, then enter number of vertices" },
                { "Reflect about Line", "Select object to reflect, then line of reflection" },
                { "Reflect about Point", "Select object to reflect, then center point" },
                { "Rotate around Point", "Select object to rotate and center point, then enter angle" },
                { "Zoom In", "Click/tap to zoom (or Mouse Wheel)" },
                { "Zoom Out", "Click/tap to zoom (or Mouse Wheel)" },
                { "Delete", "Select object which should be deleted" },
                { "Move Graphics View", "Drag white background or axis" },
                { "Translation", "Select object to translate" },
                { "Normal Rotation", "Select object to rotate" },
                { "Scaling", "Select object to scale then enter scaling factor" },
                { "Shear", "Select object to translate, then enter scaling factor" },
                { "Clipping", "Draw clipping region with mouse drag" }
        });
        mainPanel.add(toastTable, gbc);
        gbc.gridy++;

        // Add Detailed Button explanations
        JTextArea buttonExplanation = createSectionText("Buttons on the right side",
                "There are several buttons available in the interface for various actions on the right side of the Menu Bar:");
        mainPanel.add(buttonExplanation, gbc);
        gbc.gridy++;

        // Add action buttons as a table
        JPanel actionButtonsTable = createButtonTable(new Object[][] {
                { FontIcon.of(BootstrapIcons.LIST, 40), "Sidebar", "Toggle the sidebar visibility." },
                { FontIcon.of(BootstrapIcons.GRID_3X3, 37), "Guidelines", "Show or hide guidelines." },
                { IconLoader.loadIcon("/icons/axes.png", 40), "Axes", "Show or hide axes." },
                { FontIcon.of(BootstrapIcons.QUESTION_CIRCLE, 37), "Help", "Open the help documentation." },
                { FontIcon.of(BootstrapIcons.CAMERA, 37), "Export", "Export the current view as an image." },
                { FontIcon.of(BootstrapIcons.ARROW_COUNTERCLOCKWISE, 40), "Undo", "Undo the last action." },
                { FontIcon.of(BootstrapIcons.ARROW_CLOCKWISE, 40), "Redo", "Redo the last undone action." }
        });
        mainPanel.add(actionButtonsTable, gbc);

        // Add scrollable area
        JScrollPane scrollPane = new JScrollPane(mainPanel,
                javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        this.add(scrollPane, BorderLayout.CENTER);

    }

    private JTextArea createSectionText(String title, String content) {
        JTextArea textArea = new JTextArea();
        textArea.setText(title + "\n\n" + content);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.setOpaque(false);
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return textArea;
    }

    private JPanel createButtonTable(Object[][] data) {
        String[] columnNames = { "Icon", "Title", "Description" };

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 0) {
                    return Icon.class;
                } else {
                    return String.class;
                }
            }
        };

        JTable table = new JTable(tableModel);
        table.setRowHeight(50);
        table.setFillsViewportHeight(true);
        table.getColumnModel().getColumn(0).setMaxWidth(60);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

        table.setBackground(new Color(245, 245, 245));
        table.setGridColor(new Color(200, 200, 200));

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(table.getTableHeader(), BorderLayout.PAGE_START);
        tablePanel.add(table, BorderLayout.CENTER);
        return tablePanel;
    }

    private JPanel createTextTable(Object[][] data) {
        String[] columnNames = { "Title", "Description" };

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);

        JTable table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFillsViewportHeight(true);

        table.setBackground(new Color(245, 245, 245));
        table.setGridColor(new Color(200, 200, 200));

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(table.getTableHeader(), BorderLayout.PAGE_START);
        tablePanel.add(table, BorderLayout.CENTER);
        return tablePanel;
    }
}
