package com.github.creme332.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import static com.github.creme332.utils.IconLoader.loadIcon;

import com.github.creme332.model.LineType;
import com.github.creme332.utils.exception.InvalidIconSizeException;
import com.github.creme332.utils.exception.InvalidPathException;

public class Toolbar extends JToolBar implements ActionListener {

    static final int THICKNESS_MIN = 1;
    static final int THICKNESS_MAX = 13;
    static final int THICKNESS_INIT = 1;
    
    private JLabel thicknessLabel = new JLabel(String.valueOf(THICKNESS_INIT)); // Line thickness label
    private List<JMenuItem> lineTypeMenuItems = new ArrayList<>();

    /**
     * A slider for line thickness.
     */
    JSlider thicknessSlider = new JSlider(javax.swing.SwingConstants.HORIZONTAL,
            THICKNESS_MIN, THICKNESS_MAX, THICKNESS_INIT);

    private JButton colorBox;

    public Toolbar() throws InvalidIconSizeException, InvalidPathException {

        thicknessSlider.setMajorTickSpacing(10);
        thicknessSlider.setMinorTickSpacing(1);

        // Add thickness label to the right of the slider
        JPanel thicknessPanel = new JPanel();
        thicknessPanel.add(thicknessSlider);
        thicknessPanel.add(thicknessLabel);

        // add a menu
        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu();
        menu.setToolTipText("Set line style");

        menu.setIcon(loadIcon("/icons/solid-line.png", 50));

        lineTypeMenuItems.add(createLineTypeMenuItem("Solid Line", "/icons/solid-line.png"));
        lineTypeMenuItems.add(createLineTypeMenuItem("Dashed Line", "/icons/dashed-line.png"));
        lineTypeMenuItems.add(createLineTypeMenuItem("Dotted Line", "/icons/dotted-line.png"));

        for (JMenuItem item : lineTypeMenuItems) {
            menu.add(item);
        }

        menu.add(thicknessPanel); // Add the panel with the slider and label

        menuBar.add(menu);

        // color picker menu
        JPanel colorPanel = new JPanel();
        colorBox = new JButton();
        colorBox.setBorderPainted(false);
        colorBox.setBackground(Color.black);
        colorBox.setPreferredSize(new Dimension(50, 50));
        colorBox.setToolTipText("Set color");
        colorPanel.add(colorBox);
        menuBar.add(colorPanel);

        colorBox.addActionListener(this);

        this.add(menuBar);
    }

    public void actionPerformed(ActionEvent e) {
        // color chooser Dialog Box
        Color color = JColorChooser.showDialog(this,
                "Select a color", Color.black);

        // set background color of the Container
        if (color != null)
            colorBox.setBackground(color);
    }

        private JMenuItem createLineTypeMenuItem(String name, String iconPath) throws InvalidIconSizeException, InvalidPathException {
        JMenuItem menuItem = new JMenuItem(name, loadIcon(iconPath, 50));
        menuItem.setActionCommand(name);
        return menuItem;
    }

    public JSlider getThicknessSlider() {
        return thicknessSlider;
    }

    public JButton getColorBox() {
        return colorBox;
    }

    public List<JMenuItem> getLineTypeMenuItems() {
        return lineTypeMenuItems;
    }

    public void updateThicknessLabel(int thickness) {
        thicknessLabel.setText(String.valueOf(thickness));
    }

    public void updateLineTypeIcon(LineType lineType) throws InvalidIconSizeException, InvalidPathException {
        String iconPath = "/icons/" + lineType.toString().toLowerCase() + "-line.png";
        JMenu menu = (JMenu) ((JMenuBar) this.getComponent(0)).getComponent(0);
        menu.setIcon(loadIcon(iconPath, 50));
    }
}