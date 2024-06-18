package com.github.creme332.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

import static com.github.creme332.utils.IconLoader.loadIcon;

import com.github.creme332.model.LineType;
import com.github.creme332.utils.exception.InvalidIconSizeException;
import com.github.creme332.utils.exception.InvalidPathException;

public class Toolbar extends JToolBar {

    private static final int THICKNESS_MIN = 1;
    private static final int THICKNESS_MAX = 13;
    private static final int THICKNESS_INIT = 1;
    private static final int ICON_SIZE = 50;

    private JLabel thicknessLabel = new JLabel(String.valueOf(THICKNESS_INIT)); // Line thickness label

    /**
     * A square that displays current fill color.
     */
    private JButton colorBox;

    /**
     * Menu that displays the different line types and line thickness.
     */
    private JMenu lineMenu;

    /**
     * A slider for line thickness.
     */
    JSlider thicknessSlider = new JSlider(javax.swing.SwingConstants.HORIZONTAL,
            THICKNESS_MIN, THICKNESS_MAX, THICKNESS_INIT);

    public Toolbar(LineType defaultLineType, Color defaultColor) throws InvalidIconSizeException, InvalidPathException {
        thicknessSlider.setMajorTickSpacing(10);
        thicknessSlider.setMinorTickSpacing(1);
        thicknessSlider.setPreferredSize(new Dimension(160, 10));

        // Add thickness label to the right of the slider
        JPanel thicknessPanel = new JPanel(new BorderLayout());
        thicknessPanel.setBorder(new EmptyBorder(new Insets(0, 0, 0, 10)));
        thicknessPanel.setOpaque(false);
        thicknessPanel.add(thicknessSlider, BorderLayout.WEST);
        thicknessPanel.add(thicknessLabel, BorderLayout.EAST);

        // add a menu
        JMenuBar menuBar = new JMenuBar();

        lineMenu = new JMenu();
        lineMenu.setToolTipText("Set line style");
        displayLineIcon(defaultLineType);

        // add menu items for the different line types
        for (LineType type : LineType.values()) {
            JMenuItem menuItem = new JMenuItem(type.getDescription(), loadIcon(type.getIconPath(), ICON_SIZE));
            menuItem.setActionCommand(type.getDescription());
            lineMenu.add(menuItem);
        }

        lineMenu.add(thicknessPanel); // Add the panel with the slider and label

        menuBar.add(lineMenu);

        // color picker menu
        JPanel colorPanel = new JPanel();
        colorBox = new JButton();
        colorBox.setBorderPainted(false);
        colorBox.setBackground(defaultColor);
        colorBox.setPreferredSize(new Dimension(ICON_SIZE, ICON_SIZE));
        colorBox.setToolTipText("Set color");
        colorPanel.add(colorBox);
        menuBar.add(colorPanel);

        this.add(menuBar);
    }

    public JSlider getThicknessSlider() {
        return thicknessSlider;
    }

    public JButton getColorBox() {
        return colorBox;
    }

    public JMenu getLineTypeMenu() {
        return lineMenu;
    }

    public void updateThicknessLabel(int thickness) {
        thicknessLabel.setText(String.valueOf(thickness));
    }

    public void displayLineIcon(LineType line) {
        try {
            lineMenu.setIcon(loadIcon(line.getIconPath(), ICON_SIZE));
        } catch (InvalidIconSizeException | InvalidPathException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}