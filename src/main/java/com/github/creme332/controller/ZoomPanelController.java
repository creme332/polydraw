package com.github.creme332.controller;

import com.github.creme332.model.AppState;
import com.github.creme332.model.CanvasModel;
import com.github.creme332.view.ZoomPanel;

/**
 * Controller for ZoomPanel in CanvasConsole.
 */
public class ZoomPanelController {
    CanvasModel canvasModel;
    ZoomPanel view;

    public ZoomPanelController(AppState appState, ZoomPanel view) {
        this.canvasModel = appState.getCanvasModel();
        this.view = view;

        // Add action listeners for the zoom panel buttons
        view.getHomeButton().addActionListener(e -> canvasModel.toStandardView());
        view.getZoomInButton().addActionListener(e -> canvasModel.updateCanvasZoom(true));
        view.getZoomOutButton().addActionListener(e -> canvasModel.updateCanvasZoom(false));
        view.getFullScreenButton().addActionListener(e -> appState.setMaximizeFrame(!appState.isMaximizeFrame()));
    }
}