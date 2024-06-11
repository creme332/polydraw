package com.github.creme332.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import static com.github.creme332.utils.IconLoader.loadIcon;
import com.github.creme332.utils.exception.InvalidIconSizeException;
import com.github.creme332.utils.exception.InvalidPathException;

public class Toolbar extends JToolBar implements ActionListener {

    static final int THICKNESS_MIN = 1;
    static final int THICKNESS_MAX = 13;
    static final int THICKNESS_INIT = 1;

    /**
     * A slider for line thickness.
     */
    JSlider thicknessSlider = new JSlider(javax.swing.SwingConstants.HORIZONTAL,
            THICKNESS_MIN, THICKNESS_MAX, THICKNESS_INIT);

    private JButton colorBox;

    public Toolbar() throws InvalidIconSizeException, InvalidPathException {

        thicknessSlider.setMajorTickSpacing(10);
        thicknessSlider.setMinorTickSpacing(1);

        // add border
        Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        this.setBorder(border);

        // add a menu
        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu();
        menu.setToolTipText("Set line style");

        menu.setIcon(loadIcon("/icons/solid-line.png", 50));

        JMenuItem menuItem = new JMenuItem("Solid Line",
                loadIcon("/icons/solid-line.png", 50));
        menu.add(menuItem);

        menuItem = new JMenuItem("Dashed Line",
                loadIcon("/icons/dashed-line.png", 50));
        menu.add(menuItem);

        menuItem = new JMenuItem("Dotted Line",
                loadIcon("/icons/dotted-line.png", 50));
        menu.add(menuItem);

        menu.add(thicknessSlider);

        menuBar.add(menu);

        // color picker menu
        JPanel aaa = new JPanel();
        colorBox = new JButton();
        colorBox.setBorderPainted(false);
        colorBox.setBackground(Color.black);
        colorBox.setPreferredSize(new Dimension(50, 50));
        colorBox.setToolTipText("Set color");
        aaa.add(colorBox);
        menuBar.add(aaa);

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

}