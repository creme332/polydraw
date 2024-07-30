package com.github.creme332.controller.console;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.colorchooser.AbstractColorChooserPanel;

import com.github.creme332.model.CanvasModel;
import com.github.creme332.model.LineType;
import com.github.creme332.model.ShapeManager;
import com.github.creme332.model.ShapeWrapper;
import com.github.creme332.view.console.Toolbar;

/**
 * Controller for managing Toolbar in CanvasConsole.
 */
public class ToolBarController implements PropertyChangeListener {
    CanvasModel canvasModel;
    Toolbar toolbar;

    public ToolBarController(Toolbar toolbar, CanvasModel canvasModel) {
        this.canvasModel = canvasModel;
        this.toolbar = toolbar;

        canvasModel.addPropertyChangeListener(this);

        toolbar.getThicknessSlider().addChangeListener(e -> {
            int thickness = toolbar.getThicknessSlider().getValue();
            int selectedShapeIndex = canvasModel.getSelectedShapeIndex();

            // if a shape is currently selected, edit the shape
            if (selectedShapeIndex >= 0) {
                ShapeManager manager = canvasModel.getShapeManager();
                ShapeWrapper wrapper = manager.getShapeByIndex(selectedShapeIndex);
                wrapper.setLineThickness(thickness);
                System.out.println(
                        "new thickness = " + thickness);
                manager.editShape(selectedShapeIndex, wrapper);
            } else {
                // edit global canvas attributes
                canvasModel.setLineThickness(thickness);
            }
        });

        toolbar.getColorBox().addActionListener(e -> {
            JColorChooser cc = new JColorChooser();

            // display only the swatches panel in the color chooser panel
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

                        int selectedShapeIndex = canvasModel.getSelectedShapeIndex();

                        // if a shape is currently selected, edit the shape
                        if (selectedShapeIndex >= 0) {
                            ShapeManager manager = canvasModel.getShapeManager();
                            ShapeWrapper wrapper = manager.getShapeByIndex(selectedShapeIndex);
                            wrapper.setLineColor(selectedColor);
                            manager.editShape(selectedShapeIndex, wrapper);
                        } else {
                            // else edit global canvas attributes
                            canvasModel.setShapeColor(selectedColor);
                        }
                        toolbar.getColorBox().setBackground(selectedColor);

                        // request focus again otherwise keyboard shortcuts will stop working after
                        // opening color dialog
                        toolbar.getTopLevelAncestor().requestFocus();
                    }
                }
            }, null);

            dialog.setVisible(true);
        });

        // add action listener to each line type menu item to handle click
        for (int i = 0; i < LineType.values().length; i++) {
            JMenuItem item = toolbar.getLineTypeMenu().getItem(i);

            final LineType currentLine = LineType.values()[i];
            item.addActionListener(e -> {
                ShapeManager manager = canvasModel.getShapeManager();
                int selectedShapeIndex = canvasModel.getSelectedShapeIndex();

                // if a shape is currently selected, edit the shape
                if (selectedShapeIndex >= 0) {
                    ShapeWrapper wrapper = manager.getShapeByIndex(selectedShapeIndex);
                    wrapper.setLineType(currentLine);
                    manager.editShape(selectedShapeIndex, wrapper);
                } else {
                    // else edit global canvas attributes
                    canvasModel.setLineType(currentLine);
                }

                toolbar.displayLineIcon(currentLine);
            });
        }
    }

    /**
     * Displays attributes of a particular shape in toolbar. This method is invoked
     * when user clicks on a shape.
     */
    public void displayShapeAttributes(ShapeWrapper wrapper) {
        toolbar.displayLineIcon(wrapper.getLineType());
        toolbar.setThickness(wrapper.getLineThickness());
        toolbar.getColorBox().setBackground(wrapper.getLineColor());
        toolbar.repaint();
    }

    /**
     * Displays global canvas attributes in toolbar. This method is invoked when
     * user has not selected any shape.
     */
    public void displayGlobalAttributes() {
        toolbar.displayLineIcon(canvasModel.getLineType());
        toolbar.setThickness(canvasModel.getLineThickness());
        toolbar.getColorBox().setBackground(canvasModel.getShapeColor());
        toolbar.repaint();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        if (propertyName.equals("selectedShapeIndex")) {
            int shapeIndex = (int) evt.getNewValue();
            if (shapeIndex < 0) {
                displayGlobalAttributes();
                return;
            }
            displayShapeAttributes(canvasModel.getShapeManager().getShapeByIndex(shapeIndex));
        }
    }
}
