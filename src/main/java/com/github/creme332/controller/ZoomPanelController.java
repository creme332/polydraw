package com.github.creme332.controller;

import com.github.creme332.model.CanvasModel;
import com.github.creme332.view.ZoomPanel;

/**
 * Controller for ZoomPanel in CanvasConsole.
 */
public class ZoomPanelController {
    CanvasModel model;
    ZoomPanel view;

    public ZoomPanelController(CanvasModel model, ZoomPanel view) {
        this.model = model;
        this.view = view;

        // Add action listeners for the zoom panel buttons
        view.getHomeButton().addActionListener(e -> model.resetZoom());
        view.getZoomInButton().addActionListener(e -> model.updateCanvasZoom(true));
        view.getZoomOutButton().addActionListener(e -> model.updateCanvasZoom(false));
    }
}