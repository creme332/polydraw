package com.github.creme332.view;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import com.github.creme332.utils.IconLoader;

public class Toolbar extends JToolBar {

    static final int FPS_MIN = 1;
    static final int FPS_MAX = 13;
    static final int FPS_INIT = 1; // initial frames per second
    JSlider framesPerSecond = new JSlider(JSlider.HORIZONTAL,
            FPS_MIN, FPS_MAX, FPS_INIT);

    public Toolbar() throws Exception {

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

        menu = new JMenu();
        menu.setToolTipText("Set color");

        menu.setIcon(loader.loadIcon("/icons/solid-line.png", 50));

        menuItem = new JMenuItem("Solid Line",
                loader.loadIcon("/icons/solid-line.png", 50));
        menu.add(menuItem);

        menuItem = new JMenuItem("Dashed Line",
                loader.loadIcon("/icons/dashed-line.png", 50));
        menu.add(menuItem);

        menuItem = new JMenuItem("Dotted Line",
                loader.loadIcon("/icons/dotted-line.png", 50));
        menu.add(menuItem);

        menuBar.add(menu);

        this.add(menuBar);

    }

}