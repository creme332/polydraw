package com.github.creme332.controller.console;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.colorchooser.AbstractColorChooserPanel;

import com.github.creme332.model.CanvasModel;
import com.github.creme332.model.LineType;
import com.github.creme332.view.console.Toolbar;

/**
 * Controller for managing Toolbar in CanvasConsole.
 */
public class ToolBarController {
    public ToolBarController(Toolbar toolbar, CanvasModel canvasModel) {

        toolbar.getColorBox().setBackground(canvasModel.getShapeColor());

        toolbar.getThicknessSlider().addChangeListener(e -> {
            int thickness = toolbar.getThicknessSlider().getValue();
            canvasModel.setLineThickness(thickness);
            toolbar.updateThicknessLabel(thickness);
        });

        toolbar.getColorBox().addActionListener(e -> {
            JColorChooser cc = new JColorChooser();
            AbstractColorChooserPanel[] panels = cc.getChooserPanels();
            for (AbstractColorChooserPanel accp : panels) {
                if (!accp.getDisplayName().equals("Swatches")) {
                    cc.removeChooserPanel(accp);
                }
            }

            JDialog dialog = JColorChooser.createDialog(toolbar, "Select a Color", true, cc, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Color selectedColor = cc.getColor();
                    if (selectedColor != null) {
                        canvasModel.setShapeColor(selectedColor);
                        toolbar.getColorBox().setBackground(selectedColor);

                        // request focus again otherwise keyboard shortcuts will stop working after
                        // opening color dialog
                        toolbar.getTopLevelAncestor().requestFocus();
                    }
                }
            }, null);

            dialog.setVisible(true);
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
