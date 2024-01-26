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

    public Frame() {

        this.setTitle("polydraw"); // set frame title

        // set frame size
        this.setSize(frameWidth, frameHeight);

        // make frame resizable
        this.setResizable(true);

        // add close button to frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ! Maximize frame on startup may cause a white screen to appear.
        // this.setExtendedState(JFrame.MAXIMIZED_BOTH); // maximize frame on startup

        // center frame on startup if frame is not maximized
        if (this.getExtendedState() != JFrame.MAXIMIZED_BOTH) {
            // ! Do not set locationRelative to null if screen is maximized
            this.setLocationRelativeTo(null);
        }

        screenContainer.setLayout(cl);
        screenContainer.add(new JColorChooser());

        this.add(screenContainer);
    }

    /**
     * Displays frame which is initially hidden.
     * 
     * Call this function once all components have been added to the frame
     * to ensure proper rendering.
     */
    public void showFrame() {
        this.setVisible(true);
    }
}