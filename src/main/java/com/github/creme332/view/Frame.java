package com.github.creme332.view;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import static com.github.creme332.utils.IconLoader.loadIcon;

import com.github.creme332.model.AppState;
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
     * A menubar for main screen.
     */
    MenuBar menubar = null;

    CanvasConsole canvasConsole;

    JLayeredPane canvasScreen;

    Canvas canvas;

    TutorialCenter tutorialCenter;

    public void initFrameProperties() throws InvalidPathException {
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

    }

    public Frame(AppState app)
            throws InvalidPathException {
        initFrameProperties();

        menubar = new MenuBar(app.getMenuModels());
        canvasConsole = new CanvasConsole(app.getCanvasModel(), app.getSideBarVisibility(), app.getMode());
        canvas = new Canvas(app.getCanvasModel());
        tutorialCenter = new TutorialCenter();

        // add menubar to frame
        setJMenuBar(menubar);

        // setup layered pane
        canvasScreen = new JLayeredPane();
        canvasScreen.add(canvas, JLayeredPane.DEFAULT_LAYER);
        canvasScreen.add(canvasConsole, JLayeredPane.PALETTE_LAYER);

        canvas.setBounds(0, 0, 600, 600);
        canvasConsole.setBounds(0, 0, 600, 600);

        // setup screen container
        screenContainer.add(splashScreen, Screen.SPLASH_SCREEN.name());
        screenContainer.add(canvasScreen, Screen.MAIN_SCREEN.name());
        screenContainer.add(tutorialCenter, Screen.TUTORIAL_SCREEN.name());
        this.add(screenContainer);

        // display frame
        this.setVisible(true);
    }

    public void setMenuBarVisibility(boolean visible) {
        menubar.setVisible(visible);
    }

    public void showScreen(Screen screen) {
        switch (screen) {
            case SPLASH_SCREEN:
                cl.show(screenContainer, Screen.SPLASH_SCREEN.name());
                break;
            case MAIN_SCREEN:
                requestFocus();
                menubar.setVisible(true);
                cl.show(screenContainer, Screen.MAIN_SCREEN.name());
                break;
            case TUTORIAL_SCREEN:
                menubar.setVisible(false);
                cl.show(screenContainer, Screen.TUTORIAL_SCREEN.name());
                break;
            default:
                break;
        }
    }

    public JLayeredPane getCanvasScreen() {
        return canvasScreen;
    }

    public TutorialCenter getTutorialCenter() {
        return tutorialCenter;
    }

    public CanvasConsole getCanvasConsole() {
        return canvasConsole;
    }

    public MenuBar getMyMenuBar() {
        return menubar;
    }

    public Canvas getMyCanvas() {
        return canvas;
    }
}