package com.github.creme332.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import static com.github.creme332.utils.IconLoader.loadIcon;

import com.github.creme332.model.Screen;
import com.github.creme332.utils.exception.InvalidPathException;

/**
 * Frame of the GUI application.
 */
public class Frame extends JFrame {
    private static final int INITIAL_FRAME_WIDTH = 1600;
    private static final int INITIAL_FRAME_HEIGHT = 1000;

    /**
     * Layout used for screenContainer for swapping screens
     */
    private CardLayout cl = new CardLayout();

    /**
     * A container for all screens.
     */
    private JPanel screenContainer = new JPanel(cl);

    /**
     * Loading screen which is displayed on startup
     */
    private SplashScreen splashScreen = new SplashScreen();

    /**
     * A container for canvas and side menu
     */
    private JPanel mainScreen = new JPanel(new BorderLayout());

    /**
     * A sidemenu for main screen.
     */
    SideMenuPanel sideMenu = new SideMenuPanel();

    /**
     * A menubar for main screen.
     */
    MenuBar menubar = null;

    public Frame(Canvas canvas, MenuBar menubar, TutorialCenter tutorialCenter) throws InvalidPathException {
        // set frame title
        this.setTitle("polydraw");

        // set frame size
        this.setSize(INITIAL_FRAME_WIDTH, INITIAL_FRAME_HEIGHT);

        // make frame resizable
        this.setResizable(true);

        // add close button to frame
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // set application icon
        this.setIconImage(loadIcon("/icons/icosahedron.png").getImage());

        // center frame on startup if frame is not maximized
        if (this.getExtendedState() != MAXIMIZED_BOTH) {
            this.setLocationRelativeTo(null);
        }

        // add menubar to frame
        this.menubar = menubar;
        setJMenuBar(menubar);

        // setup screen container
        screenContainer.add(splashScreen, Screen.SPLASH_SCREEN.toString());
        screenContainer.add(mainScreen, Screen.MAIN_SCREEN.toString());
        screenContainer.add(tutorialCenter, Screen.TUTORIAL_SCREEN.toString());

        this.add(screenContainer);

        // add canvas to mainScreen
        mainScreen.add(canvas, BorderLayout.CENTER);

        // add side menu to main screen
        mainScreen.add(sideMenu, BorderLayout.EAST);

        // display frame
        this.setVisible(true);
    }

    public void setMenuBarVisibility(boolean visible) {
        menubar.setVisible(visible);
    }

    public void setSideBarVisibility(boolean visible) {
        sideMenu.setVisible(visible);
    }

    public void showScreen(Screen screen) {
        switch (screen) {
            case SPLASH_SCREEN:
                cl.show(screenContainer, Screen.SPLASH_SCREEN.toString());
                break;
            case MAIN_SCREEN:
                menubar.setVisible(true);
                cl.show(screenContainer, Screen.MAIN_SCREEN.toString());
                break;
            case TUTORIAL_SCREEN:
                // hide menubar and sidemenu belonging to main screen
                menubar.setVisible(false);
                sideMenu.setVisible(false);
                cl.show(screenContainer, Screen.TUTORIAL_SCREEN.toString());
                break;
            default:
                break;
        }
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