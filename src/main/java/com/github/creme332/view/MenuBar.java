package com.github.creme332.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.kordamp.ikonli.swing.FontIcon;

import com.github.creme332.model.MenuItemModel;
import com.github.creme332.model.MenuModel;
import com.github.creme332.utils.IconLoader;
import com.github.creme332.utils.exception.InvalidIconSizeException;
import com.github.creme332.utils.exception.InvalidPathException;

import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;

public class MenuBar extends JMenuBar {
    private JButton sidebarButton;
    private JButton guidelinesButton; // Button to toggle guidelines
    private JButton toggleAxesButton; // Button to toggle axes visibility
    private JButton helpButton;
    private JButton exportButton; // Button to export canvas as image

    private List<JMenu> jmenus = new ArrayList<>();

    public MenuBar(MenuModel[] menus) throws InvalidIconSizeException, InvalidPathException {

        setPreferredSize(new Dimension(getWidth(), 70));
        setBorder(new EmptyBorder(new Insets(7, 0, 7, 0)));

        // add menus to menubar
        for (MenuModel menuModel : menus) {
            JMenu menu = new RoundedMenu();
            jmenus.add(menu);
            menu.setIcon(menuModel.getActiveItem().getIcon());
            menu.setToolTipText(menuModel.getActiveItem().getName());

            for (MenuItemModel item : menuModel.getItems()) {
                JMenuItem menuItem = new JMenuItem(item.getName(), item.getIcon());
                menu.add(menuItem);
                menuItem.addActionListener(e -> menu.setToolTipText(item.getName()));
            }
            this.add(Box.createHorizontalStrut(10));
            this.add(menu);
        }

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        leftPanel.setOpaque(false);

        // undo button
        JButton btn = new JButton();
        btn.setIcon(FontIcon.of(BootstrapIcons.ARROW_COUNTERCLOCKWISE, 40));
        btn.setBorderPainted(false);
        leftPanel.add(btn);
        btn.setToolTipText("Undo");

        // redo button
        btn = new JButton();
        btn.setIcon(FontIcon.of(BootstrapIcons.ARROW_CLOCKWISE, 40));
        btn.setBorderPainted(false);
        leftPanel.add(btn);
        btn.setToolTipText("Redo");

        // guidelines button
        guidelinesButton = new JButton();
        guidelinesButton.setIcon(FontIcon.of(BootstrapIcons.GRID_3X3, 37));
        guidelinesButton.setBorderPainted(false);
        leftPanel.add(guidelinesButton);
        guidelinesButton.setToolTipText("Toggle guidelines");

        // toggle axes button
        toggleAxesButton = new JButton();
        toggleAxesButton.setIcon(IconLoader.loadIcon("/icons/axes.png", 40));
        toggleAxesButton.setBorderPainted(false);
        toggleAxesButton.setToolTipText("Toggle axes");
        leftPanel.add(toggleAxesButton);

        // help button
        helpButton = new JButton();
        helpButton.setIcon(FontIcon.of(BootstrapIcons.QUESTION_CIRCLE, 37));
        helpButton.setBorderPainted(false);
        helpButton.setToolTipText("Help");
        leftPanel.add(helpButton);

        // export button
        exportButton = new JButton();
        exportButton.setIcon(FontIcon.of(BootstrapIcons.CAMERA, 37)); // Use an appropriate icon for export
        exportButton.setBorderPainted(false);
        exportButton.setToolTipText("Export canvas");
        leftPanel.add(exportButton);

        // sidebar menu button
        sidebarButton = new JButton();
        sidebarButton.setIcon(FontIcon.of(BootstrapIcons.LIST, 40));
        sidebarButton.setBorderPainted(false);
        leftPanel.add(sidebarButton);
        sidebarButton.setToolTipText("Toggle sidebar");

        this.add(leftPanel);
    }

    /**
     * Use this method to get the i-th JMenu in the menubar. Do not use the default
     * getMenu() as it does not account for empty spaces and counts buttons.
     * 
     * @param i
     * @return
     */
    public JMenu getMyMenu(int i) {
        return jmenus.get(i);
    }

    public JButton getSideBarButton() {
        return sidebarButton;
    }

    public JButton getGuidelinesButton() {
        return guidelinesButton;
    }

    public JButton getToggleAxesButton() {
        return toggleAxesButton;
    }

    public JButton getHelpButton() {
        return helpButton;
    }

    public JButton getExportButton() {
        return exportButton;
    }
}
