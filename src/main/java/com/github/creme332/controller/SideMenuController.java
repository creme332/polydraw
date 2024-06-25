package com.github.creme332.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.github.creme332.model.AppState;
import com.github.creme332.model.CanvasModel;
import com.github.creme332.view.SideMenuPanel;
import com.github.creme332.model.Screen;

/**
 * Controller responsible for managing sidebar in CanvasConsole.
 */
public class SideMenuController implements PropertyChangeListener {
    private SideMenuPanel sidebar;

    public SideMenuController(AppState app, SideMenuPanel sidebar) {
        this.sidebar = sidebar;

        app.addPropertyChangeListener(this);

        sidebar.setVisible(app.getSideBarVisibility());

        // Initialize button listeners
        initializeButtonListeners(app);
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        String propertyName = e.getPropertyName();
        if ("sidebarVisibility".equals(propertyName)) {
            sidebar.setVisible((boolean) e.getNewValue());
        }
    }

    private void initializeButtonListeners(AppState app) {
        CanvasModel canvasModel = app.getCanvasModel();

        // New button
        sidebar.getNewCanvasButton().addActionListener(e -> canvasModel.clearShapes());

        // Export Image button
        sidebar.getExportImageButton().addActionListener(e -> app.startPrintingProcess());

        // Tutorials button
        sidebar.getTutorialsButton().addActionListener(e -> app.switchScreen(Screen.TUTORIAL_SCREEN));

        // Report Problem button
        sidebar.getReportProblemButton().addActionListener(e -> {
            try {
                java.awt.Desktop.getDesktop().browse(new java.net.URI("https://github.com/creme332/polydraw/issues/new"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // About button
        sidebar.getAboutButton().addActionListener(e -> {
            JDialog aboutDialog = new JOptionPane(
                    "Project Description\nVersion: v0.0\nLicense: MIT",
                    JOptionPane.INFORMATION_MESSAGE).createDialog("About");
            aboutDialog.setVisible(true);
        });

        // Guidelines checkbox
        sidebar.getGridLinesCheckBox().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                canvasModel.setGuidelinesEnabled(!canvasModel.isGuidelinesEnabled());
                sidebar.getGridLinesCheckBox().setSelected(canvasModel.isGuidelinesEnabled());
            }
        });

        // Axes checkbox
        sidebar.getAxesCheckBox().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                canvasModel.setAxesVisible(!canvasModel.isAxesVisible());
                sidebar.getAxesCheckBox().setSelected(canvasModel.isAxesVisible());
            }
        });

        // Reset button
        sidebar.getResetButton().addActionListener(e -> {
            // Reset guidelines checkbox and model
            if (sidebar.getGridLinesCheckBox().isSelected()) {
                sidebar.getGridLinesCheckBox().setSelected(false);
                canvasModel.setGuidelinesEnabled(true);
            }

            // Reset axes checkbox and model
            if (sidebar.getAxesCheckBox().isSelected()) {
                sidebar.getAxesCheckBox().setSelected(false);
                canvasModel.setAxesVisible(true);
            }
        });
    }
}
