package com.github.creme332.view;

import java.awt.*;
import javax.swing.*;

/**
 * Frame of the GUI application.
 */
public class Frame extends JFrame {
    // frame properties
    private int frameWidth = 1600;
    private int frameHeight = 1000;

    // screens
    JPanel screenContainer = new JPanel(); // a container for all screens
    private CardLayout cl = new CardLayout(); // used to swap between screens

    SplashScreen splashScreen = new SplashScreen();

    public Frame(MenuBar menubar, Canvas grid) {
        // set frame title
        this.setTitle("polydraw");

        // set frame size
        this.setSize(frameWidth, frameHeight);

        // make frame resizable
        this.setResizable(true);

        // add close button to frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // center frame on startup if frame is not maximized
        if (this.getExtendedState() != JFrame.MAXIMIZED_BOTH) {
            this.setLocationRelativeTo(null);
        }

        screenContainer.setLayout(cl);
        screenContainer.add(splashScreen, "splashScreen");
        screenContainer.add(grid, "canvas");
        this.add(screenContainer);

        setJMenuBar(menubar);
        this.setVisible(true);
    }

    /**
     * Displays frame which is initially hidden.
     * 
     * Call this function once all components have been added to the frame
     * to ensure proper rendering.
     */
    public void showMainScreen() {
        cl.show(screenContainer, "canvas");
    }

    public void showSplashScreen() {
        cl.show(screenContainer, "splashScreen");
    }
}