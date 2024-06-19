package com.github.creme332.controller;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

import com.github.creme332.model.AppState;
import com.github.creme332.model.MenuModel;
import com.github.creme332.model.CanvasModel;
import com.github.creme332.model.Mode;
import com.github.creme332.model.Screen;
import com.github.creme332.view.Canvas;
import com.github.creme332.view.MenuBar;

public class MenuBarController implements PropertyChangeListener {
    private MenuBar menubar;
    private AppState app;

    private static final MatteBorder VISIBLE_BORDER = BorderFactory.createMatteBorder(
            2, 2, 2, 2, new Color(97, 97, 255));

    MenuModel[] menuModels;
    int activeMenuIndex;
    Border defaultBorder;

    public MenuBarController(AppState app, MenuBar menubar) {
        this.menubar = menubar;
        this.app = app;
        this.menuModels = app.getMenuModels();

        app.addPropertyChangeListener(this);

        activeMenuIndex = app.getModeToMenuMapper().get(app.getMode());
        defaultBorder = menubar.getMenu(0).getBorder();

        // for each menu in menubar
        for (int i = 0; i < menuModels.length; i++) {
            JMenu jMenu = menubar.getMenu(i);
            MenuModel menuModel = menuModels[i];

            // listen to changes in each menu model
            menuModel.addPropertyChangeListener(this);

            // create a menu controller
            new MenuController(menuModel, jMenu);

            if (i == activeMenuIndex) {
                jMenu.setBorder(VISIBLE_BORDER);
            }

            final int menuIndex = i;
            // when user clicks on a menu
            jMenu.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    // remove border from previously active menu
                    menubar.getMenu(activeMenuIndex).setBorder(defaultBorder);

                    activeMenuIndex = menuIndex;

                    JMenu clickedMenu = (JMenu) e.getComponent();

                    // add border to clickedMenu
                    clickedMenu.setBorder(VISIBLE_BORDER);

                    // update global mode using menu model for clicked menu
                    app.setMode(menuModel.getActiveItem().getMode());
                }
            });
        }

        menubar.getSideBarButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // toggle sidebar visibility
                app.setSideBarVisibility(!app.getSideBarVisibility());
            }
        });

        menubar.getGuidelinesButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // toggle guidelines visibility
                CanvasModel canvasModel = app.getCanvasModel();
                canvasModel.setGuidelinesEnabled(!canvasModel.isGuidelinesEnabled());
            }
        });

        menubar.getHelpButton().addActionListener(e -> app.switchScreen(Screen.TUTORIAL_SCREEN));

        menubar.getToggleAxesButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // toggle axes visibility
                CanvasModel canvasModel = app.getCanvasModel();
                canvasModel.setAxesVisible(!canvasModel.isAxesVisible());
            }
        });

        menubar.getExportButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // export canvas as image
                exportCanvasAsImage();
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        String propertyName = e.getPropertyName();
        if ("modeChange".equals(propertyName)) {
            Mode newMode = (Mode) e.getNewValue();

            // remove border from previously active menu
            menubar.getMenu(activeMenuIndex).setBorder(defaultBorder);

            // store index of clicked menu
            activeMenuIndex = app.getModeToMenuMapper().get(newMode);

            // add border to clicked menu
            menubar.getMenu(activeMenuIndex).setBorder(VISIBLE_BORDER);

            // update global mode
            app.setMode(newMode);
        }

        if ("mode".equals(propertyName)) {
            Mode newMode = (Mode) e.getNewValue();

            // remove border from previously active menu
            menubar.getMenu(activeMenuIndex).setBorder(defaultBorder);

            // store index of new active menu
            activeMenuIndex = app.getModeToMenuMapper().get(newMode);

            // add border to clicked menu
            menubar.getMenu(activeMenuIndex).setBorder(VISIBLE_BORDER);
        }
    }

    private void exportCanvasAsImage() {
        System.out.println("Export button clicked");
        Canvas canvas = app.getCanvas(); // Ensure this returns a valid Canvas object
        if (canvas == null) {
            System.out.println("Canvas is null");
            return;
        }
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        System.out.println("Canvas width: " + width + ", height: " + height);
    
        // Create a BufferedImage
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
    
        // Paint the canvas to the BufferedImage
        canvas.paint(g2); // Ensure this method is correctly painting the canvas
        g2.dispose();
    
        // Save the BufferedImage as a PNG file
        try {
            File outputfile = new File("canvas.png");
            ImageIO.write(image, "png", outputfile);
            System.out.println("Canvas exported as image: " + outputfile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
