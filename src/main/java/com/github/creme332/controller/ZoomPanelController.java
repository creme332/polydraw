package com.github.creme332.controller;

import com.github.creme332.model.AppState;
import com.github.creme332.model.CanvasModel;
import com.github.creme332.view.ZoomPanel;

/**
 * Controller for ZoomPanel in CanvasConsole.
 */
public class ZoomPanelController {
    CanvasModel model;
    ZoomPanel view;
    AppState appState;

    public ZoomPanelController(CanvasModel model, ZoomPanel view, AppState appState) {
        this.model = model;
        this.view = view;
        this.appState = appState;
        // Add action listeners for the zoom panel buttons
        view.getHomeButton().addActionListener(e -> model.resetZoom());
        view.getZoomInButton().addActionListener(e -> model.updateCanvasZoom(true));
        view.getZoomOutButton().addActionListener(e -> model.updateCanvasZoom(false));
        view.getFullScreenButton().addActionListener(e -> appState.setMaximizeFrame(!appState.isMaximizeFrame())); // New  action listener                                                                                                                   
    }
}