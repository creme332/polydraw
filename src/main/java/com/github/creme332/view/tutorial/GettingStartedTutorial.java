package com.github.creme332.view.tutorial;

import static com.github.creme332.utils.IconLoader.loadIcon;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.swing.FontIcon;

import com.github.creme332.model.TutorialModel;
import com.github.creme332.utils.IconCellRenderer;
import com.github.creme332.utils.IconLoader;
import com.github.creme332.utils.exception.InvalidIconSizeException;
import com.github.creme332.utils.exception.InvalidPathException;

public class GettingStartedTutorial extends AbstractTutorial {

    private static final TutorialModel GETTING_STARTED_MODEL = new TutorialModel("Getting Started");

    GridBagConstraints gbc = new GridBagConstraints();
    JPanel mainPanel = new JPanel(new GridBagLayout());

    public GettingStartedTutorial() throws InvalidPathException, InvalidIconSizeException {
        super(GETTING_STARTED_MODEL,
                loadIcon("/icons/icosahedron.png",
                        IconLoader.getScaledDimension(new Dimension(512, 512), TutorialCard.IMAGE_DIMENSION)));
        // remove bodyPanel since default layout is not being used
        this.remove(bodyPanel);

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Add Introduction
        JLabel introLabel = new JLabel(
                "<html><p>Welcome to Polydraw! This tutorial will guide you through the various components of the interface to help you get started.</p></html>");
        mainPanel.add(introLabel, gbc);
        gbc.gridy++;

        addMenubarSection();
        addZoomPanelSection();
        addToastSection();

        // Make mainPanel scrollable
        JScrollPane scrollPane = new JScrollPane(mainPanel,
                javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        this.add(scrollPane, BorderLayout.CENTER);
    }

    private void addZoomPanelSection() throws InvalidPathException {
        // Add Zoom Panel section title
        mainPanel.add(createSectionTitle("Zoom Panel"), gbc);
        gbc.gridy++;

        // add image
        JLabel zoomPanelImage = new JLabel(loadIcon(getImagePathPrefix() + "zoom-panel.png"));
        mainPanel.add(zoomPanelImage, gbc);
        gbc.gridy++;

        JTextArea zoomPanelExplanation = createParagraph(
                "The zoom panel is located on the bottom right side of the screen and allows you to control the zoom level of the canvas.");
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
    }

    private void addToastSection() throws InvalidPathException {
        // add title
        mainPanel.add(createSectionTitle("Toast"), gbc);
        gbc.gridy++;

        // add image
        mainPanel.add(new JLabel(loadIcon(getImagePathPrefix() + "toast.png")), gbc);
        gbc.gridy++;

        // add description
        JTextArea toastExplanation = createParagraph(
                "The toast is a temporary panel that appears on the bottom left side of the screen when you select a new drawing mode. It provides quick instructions about the current mode.");
        mainPanel.add(toastExplanation, gbc);
        gbc.gridy++;
    }

    private void addMenubarSection() throws InvalidPathException {
        // section title
        mainPanel.add(createSectionTitle("Menubar"), gbc);
        gbc.gridy++;

        // menubar image
        mainPanel.add(new JLabel(loadIcon(getImagePathPrefix() + "menubar.png")), gbc);
        gbc.gridy++;

        JTextArea buttonExplanation = createParagraph(
                "The menubar contains the commonly used buttons which enable you to quickly change the drawing mode. There are several buttons available in the interface for various actions on the right side of the Menu Bar:");
        mainPanel.add(buttonExplanation, gbc);
        gbc.gridy++;

        JPanel modesTable = createTextTable(new Object[][] {
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
                { "Clip", "Draw clipping region with mouse drag" }
        });
        mainPanel.add(modesTable, gbc);
        gbc.gridy++;

        // Add action buttons as a table
        JPanel actionButtonsTable = createButtonTable(new Object[][] {
                { FontIcon.of(BootstrapIcons.LIST, 40), "Sidebar", "Toggle the sidebar visibility." },
                { FontIcon.of(BootstrapIcons.QUESTION_CIRCLE, 37), "Help", "Open the help documentation." },
                { FontIcon.of(BootstrapIcons.ARROW_COUNTERCLOCKWISE, 40), "Undo", "Undo the last action." },
                { FontIcon.of(BootstrapIcons.ARROW_CLOCKWISE, 40), "Redo", "Redo the last undone action." }
        });
        mainPanel.add(actionButtonsTable, gbc);
        gbc.gridy++;
    }

    private JLabel createSectionTitle(String title) {
        JLabel titleLabel = new JLabel(title);
        titleLabel.setBorder(new EmptyBorder(new Insets(20, 0, 0, 0)));
        titleLabel.putClientProperty("FlatLaf.style", "font: bold $h3.font");
        return titleLabel;
    }

    private JTextArea createParagraph(String content) {
        JTextArea textArea = new JTextArea();
        textArea.setText(content);
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
                    return Icon.class; // Set the column class to Icon
                } else {
                    return String.class;
                }
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make the cells non-editable
            }
        };

        JTable table = new JTable(tableModel);
        table.setRowHeight(50);
        table.setFillsViewportHeight(true);
        table.getColumnModel().getColumn(0).setMaxWidth(60);

        table.getColumnModel().getColumn(0).setCellRenderer(new IconCellRenderer());

        table.setBackground(new Color(245, 245, 245));
        table.setGridColor(new Color(200, 200, 200));

        table.setOpaque(true);
        ((DefaultTableCellRenderer) table.getDefaultRenderer(Object.class)).setOpaque(true);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(table.getTableHeader(), BorderLayout.PAGE_START);
        tablePanel.add(table, BorderLayout.CENTER);
        return tablePanel;
    }

    private JPanel createTextTable(Object[][] data) {
        String[] columnNames = { "Title", "Description" };

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make the cells non-editable
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setFillsViewportHeight(true);

        table.setBackground(new Color(245, 245, 245));
        table.setGridColor(new Color(200, 200, 200));

        table.setOpaque(true);
        ((DefaultTableCellRenderer) table.getDefaultRenderer(Object.class)).setOpaque(true);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(table.getTableHeader(), BorderLayout.PAGE_START);
        tablePanel.add(table, BorderLayout.CENTER);
        return tablePanel;
    }

    @Override
    public String getImagePathPrefix() {
        return "/images/tutorials/getting-started/";
    }
}
