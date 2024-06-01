package com.github.creme332.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.github.creme332.utils.IconLoader;
import com.github.creme332.utils.exception.InvalidPathException;

/**
 * Frame of the GUI application.
 */
public class Frame extends JFrame {
    // initial frame properties
    private final int frameWidth = 1600; // initial width
    private final int frameHeight = 1000; // initial height

    /**
     * Layout used for screenContainer for swapping screens
     */
    private CardLayout cl = new CardLayout();

    /**
     * A container for mainScreen and splashScreen
     */
    private JPanel screenContainer = new JPanel(cl);

    /**
     * Screen which is displayed on startup
     */
    private SplashScreen splashScreen = new SplashScreen();

    /**
     * A container for canvas and side menu
     */
    private JPanel mainScreen = new JPanel(new BorderLayout());

    SideMenuPanel sideMenu = new SideMenuPanel();
    MenuBar menubar = null;

    public Frame(Canvas canvas) throws InvalidPathException {
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

        // add menubar to frame
        try {
            menubar = new MenuBar();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        setJMenuBar(menubar);

        // setup screen container
        screenContainer.add(splashScreen, "splashScreen");
        screenContainer.add(mainScreen, "mainScreen");
        this.add(screenContainer);

        // add canvas to mainScreen
        mainScreen.add(canvas, BorderLayout.CENTER);

        // add side menu to main screen
        mainScreen.add(sideMenu, BorderLayout.EAST);

        // display frame
        this.setVisible(true);
    }

    public void toggleMenuBarVisibility(boolean visible) {
        menubar.setVisible(visible);
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

    public SideMenuPanel getSideMenuPanel() {
        return sideMenu;
    }

    public MenuBar getMyMenuBar() {
        return menubar;
    }

    /**
     * 
     * @return Container panel for canvas and sidebar
     */
    public JPanel getMainPanel() {
        return mainScreen;
    }
}