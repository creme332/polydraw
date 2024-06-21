package com.github.creme332.controller;

import com.github.creme332.model.AppState;
import com.github.creme332.view.CanvasConsole;

public class CanvasConsoleController {
    public CanvasConsoleController(CanvasConsole console, AppState app) {
        new ToolBarController(console.getToolbar(), app.getCanvasModel());
        new SideMenuController(app, console.getSidebar());
        new ToastController(app, console.getToast());
        new ZoomPanelController(app.getCanvasModel(), console.getZoomPanel(), app);
    }
}
