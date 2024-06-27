package com.github.creme332.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.github.creme332.model.CanvasModel;
import com.github.creme332.model.Mode;
import com.github.creme332.utils.exception.InvalidIconSizeException;
import com.github.creme332.utils.exception.InvalidPathException;

/**
 * A panel that contains control buttons for Canvas. It should be placed on top
 * of the canvas and should have the same dimensions as Canvas.
 */
public class CanvasConsole extends JPanel {
    private Toolbar toolbar;
    private Toast toast;
    private ZoomPanel zoomPanel = new ZoomPanel();
    SideMenuPanel sideMenu = new SideMenuPanel();
    private JPanel toastContainer = new JPanel(new CardLayout());

    transient CanvasModel canvasModel;

    public CanvasConsole(CanvasModel canvasModel, Mode defaultMode) {
        this.canvasModel = canvasModel;
        toast = new Toast(defaultMode);

        setOpaque(false);
        setLayout(new BorderLayout());

        this.add(createMainPanel(), BorderLayout.CENTER);

        this.add(sideMenu, BorderLayout.EAST);
    }

    /**
     * Toggles visibility of toast without shifting position of other components in
     * canvas console.
     * 
     * @param isVisible
     */
    public void toggleToastVisibility(boolean isVisible) {
        CardLayout cl = (CardLayout) (toastContainer.getLayout());
        if (isVisible) {
            cl.show(toastContainer, "COMPONENT");
        } else {
            cl.show(toastContainer, "EMPTY");
        }
    }

    /**
     * Main panel contains control buttons and is always visible.
     * 
     * @return
     */
    public JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false); // make panel transparent

        try {
            toolbar = new Toolbar(canvasModel.getLineType(), canvasModel.getShapeColor(),
                    canvasModel.getLineThickness());
        } catch (InvalidIconSizeException | InvalidPathException e) {
            e.printStackTrace();
            System.exit(0);
        }

        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);
        topPanel.add(toolbar);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // create toast container to be placed south
        toastContainer.setOpaque(false);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBorder(new EmptyBorder(new Insets(0, 20, 10, 0)));
        southPanel.setOpaque(false);
        southPanel.add(toast, BorderLayout.WEST);
        toastContainer.add(southPanel, "COMPONENT");

        JPanel emptyToastPanel = new JPanel();
        emptyToastPanel.setOpaque(false);
        emptyToastPanel.setPreferredSize(southPanel.getPreferredSize());
        toastContainer.add(emptyToastPanel, "EMPTY");

        toastContainer.setPreferredSize(southPanel.getPreferredSize());
        mainPanel.add(toastContainer, BorderLayout.SOUTH);

        JPanel eastPanel = new JPanel(new BorderLayout());
        eastPanel.setBorder(new EmptyBorder(new Insets(0, 0, 0, 20)));
        eastPanel.setOpaque(false);

        eastPanel.add(zoomPanel, BorderLayout.SOUTH);
        mainPanel.add(eastPanel, BorderLayout.EAST);

        return mainPanel;
    }

    public SideMenuPanel getSidebar() {
        return sideMenu;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public Toast getToast() {
        return toast;
    }

    public ZoomPanel getZoomPanel() {
        return zoomPanel;
    }
}
