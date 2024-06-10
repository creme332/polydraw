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

import com.github.creme332.utils.IconLoader;
import com.github.creme332.utils.exception.InvalidIconSizeException;
import com.github.creme332.utils.exception.InvalidPathException;

public class Toolbar extends JToolBar implements ActionListener {

    static final int FPS_MIN = 1;
    static final int FPS_MAX = 13;
    static final int FPS_INIT = 1; // initial frames per second
    JSlider framesPerSecond = new JSlider(JSlider.HORIZONTAL,
            FPS_MIN, FPS_MAX, FPS_INIT);

    private JButton colorBox;

    public Toolbar() throws InvalidIconSizeException, InvalidPathException {

        framesPerSecond.setMajorTickSpacing(10);
        framesPerSecond.setMinorTickSpacing(1);
        // framesPerSecond.setPaintTicks(true);
        // framesPerSecond.setPaintLabels(true);

        // add border
        Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        this.setBorder(border);

        IconLoader loader = new IconLoader();
        // add a menu
        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu();
        menu.setToolTipText("Set line style");

        menu.setIcon(loader.loadIcon("/icons/solid-line.png", 50));

        JMenuItem menuItem = new JMenuItem("Solid Line",
                loader.loadIcon("/icons/solid-line.png", 50));
        menu.add(menuItem);

        menuItem = new JMenuItem("Dashed Line",
                loader.loadIcon("/icons/dashed-line.png", 50));
        menu.add(menuItem);

        menuItem = new JMenuItem("Dotted Line",
                loader.loadIcon("/icons/dotted-line.png", 50));
        menu.add(menuItem);

        menu.add(framesPerSecond);

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
        System.out.println("Choose color");

        // color chooser Dialog Box
        Color color = JColorChooser.showDialog(this,
                "Select a color", Color.black);

        // set background color of the Container
        if (color != null)
            colorBox.setBackground(color);
    }

}