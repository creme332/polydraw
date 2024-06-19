package com.github.creme332.controller;

import java.awt.Color;

import javax.swing.JColorChooser;
import javax.swing.JMenuItem;

import com.github.creme332.model.CanvasModel;
import com.github.creme332.model.LineType;
import com.github.creme332.view.Toolbar;

public class ToolBarController {
    public ToolBarController(Toolbar toolbar, CanvasModel canvasModel) { // Update the constructor
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

                // request focus again otherwise keyboard shortcuts will stop working after
                // opening color dialog
                toolbar.getTopLevelAncestor().requestFocus();
            }
        });

        // add action listener to each line type menu item
        for (int i = 0; i < LineType.values().length; i++) {
            JMenuItem item = toolbar.getLineTypeMenu().getItem(i);

            final LineType currentLine = LineType.values()[i];
            item.addActionListener(e -> {
                canvasModel.setLineType(currentLine);
                toolbar.displayLineIcon(currentLine);
            });
        }
    }
}
