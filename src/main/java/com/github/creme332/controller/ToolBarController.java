package com.github.creme332.controller;

import com.github.creme332.model.CanvasModel;
import com.github.creme332.model.LineType;
import com.github.creme332.view.Toolbar;
import com.github.creme332.utils.exception.InvalidIconSizeException;
import com.github.creme332.utils.exception.InvalidPathException;

import javax.swing.*;
import java.awt.*;

public class ToolBarController {
    private Toolbar toolbar;
    private CanvasModel canvasModel;

    public ToolBarController(Toolbar toolbar, CanvasModel canvasModel) {
        this.toolbar = toolbar;
        this.canvasModel = canvasModel;

        toolbar.getThicknessSlider().addChangeListener(e -> {
            int thickness = toolbar.getThicknessSlider().getValue();
            canvasModel.setLineThickness(thickness);
            toolbar.updateThicknessLabel(thickness);
        });

        toolbar.getColorBox().addActionListener(e -> {
            Color color = JColorChooser.showDialog(toolbar, "Select a color", Color.black);
            if (color != null) {
                canvasModel.setFillColor(color);
                toolbar.getColorBox().setBackground(color);
            }
        });

        toolbar.getLineTypeMenuItems().forEach(item -> item.addActionListener(e -> {
            LineType lineType = LineType.valueOf(item.getText().toUpperCase());
            canvasModel.setLineType(lineType);
            try {
                toolbar.updateLineTypeIcon(lineType);
            } catch (InvalidIconSizeException | InvalidPathException ex) {
                ex.printStackTrace();
            }
        }));
    }
}
