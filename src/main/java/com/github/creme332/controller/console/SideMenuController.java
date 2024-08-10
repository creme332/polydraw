package com.github.creme332.controller.console;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.github.creme332.model.AppState;
import com.github.creme332.model.CanvasModel;
import com.github.creme332.model.Screen;
import com.github.creme332.model.ShapeWrapper;
import com.github.creme332.utils.DesktopApi;
import com.github.creme332.view.console.SideMenuPanel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Controller responsible for managing sidebar in CanvasConsole.
 */
public class SideMenuController implements PropertyChangeListener {

    private SideMenuPanel sidebar;
    private AppState app;
    CanvasModel canvasModel;

    public SideMenuController(AppState app, SideMenuPanel sidebar) {
        this.app = app;
        this.sidebar = sidebar;
        canvasModel = app.getCanvasModel();

        app.addPropertyChangeListener(this);

        refreshCanvasSettingsUI();

        initializeButtonListeners();

        // consume click events on sidebar otherwise the events will happen on the
        // canvas below it
        sidebar.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                e.consume();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                e.consume();
            }
        });
    }

    /**
     * Updates default values in canvas settings to match values from canvas model
     */
    public void refreshCanvasSettingsUI() {
        // set default values in canvas settings
        sidebar.getGridLinesCheckBox().setSelected(app.getCanvasModel().isGuidelinesEnabled());
        sidebar.getAxesCheckBox().setSelected(app.getCanvasModel().isAxesVisible());
        sidebar.getFontSizeSelector().setSelectedItem(String.format("%d", app.getCanvasModel().getLabelFontSize()));
    }

    /**
     * Adds action listeners to all buttons in sidebar.
     */
    private void initializeButtonListeners() {
        // New button
        sidebar.handleCanvasClear(e -> {
            int confirmation = JOptionPane.showConfirmDialog(null,
                    "Do you want to clear the current canvas? This action is irreversible.",
                    "Clear canvas",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirmation == JOptionPane.YES_OPTION) {
                canvasModel.getShapeManager().reset();
            }
        });

        // Export Image button
        sidebar.handleImageExport(e -> app.startPrintingProcess());

        // Tutorials button
        sidebar.handleTutorialAction(e -> app.switchScreen(Screen.TUTORIAL_SCREEN));

        // Report Problem button
        sidebar.handleReportAction(e -> {
            try {
                DesktopApi.browse(new java.net.URI("https://github.com/creme332/polydraw/issues"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        sidebar.handleExportCanvasAsFile(e -> handleCanvasToJSON());

        sidebar.handleOpenFile(e -> handleCanvasImport());

        // Guidelines checkbox
        sidebar.getGridLinesCheckBox().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                canvasModel.setGuidelinesEnabled(!canvasModel.isGuidelinesEnabled());
            }
        });

        // Axes checkbox
        sidebar.getAxesCheckBox().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                canvasModel.setAxesVisible(!canvasModel.isAxesVisible());
            }
        });

        sidebar.getFontSizeSelector().addActionListener(
                e -> canvasModel
                        .setLabelFontSize(
                                Integer.valueOf(
                                        (String) sidebar.getFontSizeSelector()
                                                .getSelectedItem())));

        // Reset button
        sidebar.handleCanvasSettingsReset(e -> resetCanvasSettings());
    }

    private void resetCanvasSettings() {
        int confirmation = JOptionPane.showConfirmDialog(null,
                "Do you want to reset the canvas settings to its default values? This action is irreversible.",
                "Reset canvas settings",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirmation == JOptionPane.YES_OPTION) {
            boolean settingsChanged = false;
            // Reset guidelines checkbox and model
            if (!canvasModel.isGuidelinesEnabled()) {
                settingsChanged = true;
                canvasModel.setGuidelinesEnabled(true);
            }

            // Reset axes checkbox and model
            if (!canvasModel.isAxesVisible()) {
                settingsChanged = true;
                canvasModel.setAxesVisible(true);
            }

            // Reset font size
            if (canvasModel.getLabelFontSize() != CanvasModel.DEFAULT_LABEL_FONT_SIZE) {
                settingsChanged = true;
                canvasModel.setLabelFontSize(CanvasModel.DEFAULT_LABEL_FONT_SIZE);
            }

            if (settingsChanged)
                refreshCanvasSettingsUI();
        }
    }

    /**
     * Let user choose a JSON file and return the file path as a string.
     * 
     * @return null if no file was selected
     */
    public static String chooseFileAndReadToString() {
        JFileChooser fileChooser = new JFileChooser();

        // Set the file selection mode to files only
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // Set a filter to only allow JSON files
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("JSON Files", "json"));

        // Open the file chooser dialog and capture the result
        int result = fileChooser.showOpenDialog(null);

        // Check if the user selected a file
        if (result == JFileChooser.APPROVE_OPTION) {
            // Get the selected file
            File selectedFile = fileChooser.getSelectedFile();
            // Convert the file to a string
            try {
                Path filePath = selectedFile.toPath();
                return new String(Files.readAllBytes(filePath));
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "An error occurred while reading the file: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No file selected.");
        }
        return null;
    }

    private void handleCanvasImport() {
        // get json from a file of user's choice
        String json = chooseFileAndReadToString();
        if (json == null)
            return;

        // convert json to array
        Gson gson = new Gson();
        ShapeWrapper[] newShapes = gson.fromJson(json, ShapeWrapper[].class);

        // replace current shapes with new shapes
        app.getCanvasModel().getShapeManager().importShapes(newShapes);
    }

    /**
     * Adds time prefix to a string. Useful for generating unique file names.
     * 
     * @return
     */
    public static String addTimePrefix(String str) {
        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd-HH-mm-ss");
        String formattedDate = now.format(formatter);

        // Return the formatted string
        return formattedDate + "-" + str;
    }

    public static boolean saveJsonToFile(String jsonString, String filePath) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            // Write the JSON string to the file
            fileWriter.write(jsonString);
            // Flush to ensure all data is written out
            fileWriter.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void handleCanvasToJSON() {
        // convert list of shapes on canvas to json
        GsonBuilder gsonBuilder = new GsonBuilder();
        List<ShapeWrapper> shapes = app.getCanvasModel().getShapeManager().getShapes();
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        String json = gson.toJson(shapes);

        // let user choose folder location
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose folder to save canvas");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false); // disable the "All files" option.

        // if user cancels export, return
        int returnValue = fileChooser.showDialog(sidebar.getTopLevelAncestor(), "Save");
        if (returnValue != JFileChooser.APPROVE_OPTION)
            return;

        final String folderLocation = fileChooser.getSelectedFile().toString();
        final String fileName = addTimePrefix("polydraw-export.json");
        Path filePath = Paths.get(folderLocation, fileName);

        boolean success = saveJsonToFile(json, filePath.toString());
        if (success) {
            // show success message
            JOptionPane.showMessageDialog(sidebar.getTopLevelAncestor(),
                    fileName + " was successfully saved at " + folderLocation);
        }
        sidebar.getTopLevelAncestor().requestFocus();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // if printingCanvas property has changed to true, handle export
        if ("exportCanvasToJSON".equals(evt.getPropertyName())) {
            handleCanvasToJSON();
        }
    }
}
