package com.github.creme332.view;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;

import com.github.creme332.utils.IconLoader;
import com.github.creme332.utils.exception.InvalidPathException;

/**
 * Frame of the GUI application.
 */
public class Frame extends JFrame {
    // initial frame properties
    private final int frameWidth = 1600; // initial width
    private final int frameHeight = 1000; // initial height

    // screens
    private JPanel screenContainer = new JPanel(); // a container for all screens
    private CardLayout cl = new CardLayout(); // used to swap between screens

    private SplashScreen splashScreen = new SplashScreen();
    private JPanel mainScreen = new JPanel(new BorderLayout());

    public Frame(MenuBar menubar, Canvas canvas) throws InvalidPathException {
        // set frame title
        this.setTitle("polydraw");

        // set frame size
        this.setSize(frameWidth, frameHeight);

        // make frame resizable
        this.setResizable(true);

        // add close button to frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set application icon
        this.setIconImage(new IconLoader().loadIcon("/icons/icosahedron.png").getImage());

        // center frame on startup if frame is not maximized
        if (this.getExtendedState() != JFrame.MAXIMIZED_BOTH) {
            this.setLocationRelativeTo(null);
        }

        // setup screen container
        screenContainer.setLayout(cl);
        screenContainer.add(splashScreen, "splashScreen");
        screenContainer.add(mainScreen, "mainScreen");
        this.add(screenContainer);

        // add canvas to mainScreen
        mainScreen.add(canvas, BorderLayout.CENTER);

        SideMenuPanel sideMenu = new SideMenuPanel();

        mainScreen.add(sideMenu, BorderLayout.EAST);
        mainScreen.setBackground(Color.gray);

        // add menubar to frame
        setJMenuBar(menubar);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int sideWidth = Math.min(400, getWidth() / 3);
                System.out.println("Frame size: " + getWidth() + " x " + getHeight());
                mainScreen.setPreferredSize(new Dimension(getWidth(), getHeight() - menubar.getHeight()));
                sideMenu.setPreferredSize(new Dimension(sideWidth, getHeight() - menubar.getHeight()));
            }
        });

        // display frame
        this.setVisible(true);
    }

    /**
     * Displays frame which is initially hidden.
     * 
     * Call this function once all components have been added to the frame
     * to ensure proper rendering.
     */
    public void showMainScreen() {
        cl.show(screenContainer, "mainScreen");
    }

    public void showSplashScreen() {
        cl.show(screenContainer, "splashScreen");
    }
}