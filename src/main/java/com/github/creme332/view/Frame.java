package com.github.creme332.view;

import java.awt.*;
import javax.swing.*;

import com.github.creme332.utils.IconLoader;
import com.github.creme332.utils.exception.InvalidPathException;

/**
 * Frame of the GUI application.
 */
public class Frame extends JFrame {
    // frame properties
    private int frameWidth = 1600;
    private int frameHeight = 1000;

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
        mainScreen.add(canvas);

        // add menubar to frame
        setJMenuBar(menubar);

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