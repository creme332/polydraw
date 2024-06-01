package com.github.creme332.view;

import java.awt.FlowLayout;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;

import com.github.creme332.utils.IconLoader;

public class MenuBar extends JMenuBar {
    // menubar frame components
    private JMenu menu;
    private JMenuItem menuItem;

    private JButton sidebarButton;

    public MenuBar() throws Exception {
        IconLoader loader = new IconLoader();
        FontIcon icon;

        // Build the menu for cursor
        menu = new JMenu();
        menu.setIcon(loader.loadIcon("/icons/cursor.png", 50));

        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        this.add(menu);

        menuItem = new JMenuItem("Move",
                loader.loadIcon("/icons/cursor.png", 50));
        menuItem.setMnemonic(KeyEvent.VK_B);
        menu.add(menuItem);

        menuItem = new JMenuItem("Freehand Shape",
                loader.loadIcon("/icons/freehand.png", 50));
        menuItem.setMnemonic(KeyEvent.VK_B);
        menu.add(menuItem);

        // Build menu for line drawing
        menu = new JMenu();
        menu.setIcon(loader.loadIcon("/icons/line.png", 50));

        menuItem = new JMenuItem("Line: DDA", loader.loadIcon("/icons/line.png", 50));
        menuItem.setMnemonic(KeyEvent.VK_B);
        menu.add(menuItem);

        menuItem = new JMenuItem("Line: Bresenham", loader.loadIcon("/icons/line.png", 50));
        menu.add(menuItem);

        this.add(menu);

        // Build menu for circle
        menu = new JMenu();
        menu.setIcon(loader.loadIcon("/icons/circle.png", 50));

        menuItem = new JMenuItem("Circle with Center through Point",
                loader.loadIcon("/icons/circle-center.png", 50));
        menuItem.setMnemonic(KeyEvent.VK_B);
        menu.add(menuItem);

        menuItem = new JMenuItem("Circle: Center & Radius", loader.loadIcon("/icons/circle-radius.png", 50));
        menu.add(menuItem);

        this.add(menu);

        // ellipse menu
        menu = new JMenu();
        menu.setIcon(loader.loadIcon("/icons/ellipse.png", 50));

        menuItem = new JMenuItem("Ellipse", loader.loadIcon("/icons/ellipse.png", 50));
        menu.add(menuItem);

        this.add(menu);

        // polygon menu
        menu = new JMenu();
        menu.setIcon(loader.loadIcon("/icons/triangle.png", 50));

        menuItem = new JMenuItem("Polygon", loader.loadIcon("/icons/triangle.png", 50));
        menu.add(menuItem);

        menuItem = new JMenuItem("Regular Polygon", loader.loadIcon("/icons/regular-polygon.png", 50));
        menu.add(menuItem);

        this.add(menu);

        // transformations menu
        menu = new JMenu();
        menu.setIcon(loader.loadIcon("/icons/reflect-about-line.png", 50));

        menuItem = new JMenuItem("Reflect about Line", loader.loadIcon("/icons/reflect-about-line.png", 50));
        menu.add(menuItem);

        menuItem = new JMenuItem("Reflect about Point", loader.loadIcon("/icons/reflect-about-point.png", 50));
        menu.add(menuItem);

        menuItem = new JMenuItem("Rotate around Point", loader.loadIcon("/icons/rotate-around-point.png", 50));
        menu.add(menuItem);

        this.add(menu);

        // move menu
        menu = new JMenu();
        menu.setIcon(FontIcon.of(BootstrapIcons.ARROWS_MOVE, 35));

        menuItem = new JMenuItem("Move Graphics View", FontIcon.of(BootstrapIcons.ARROWS_MOVE, 35));
        menu.add(menuItem);

        menuItem = new JMenuItem("Zoom In", FontIcon.of(BootstrapIcons.ZOOM_IN, 35));
        menu.add(menuItem);

        menuItem = new JMenuItem("Zoom Out", FontIcon.of(BootstrapIcons.ZOOM_OUT, 35));
        menu.add(menuItem);

        icon = FontIcon.of(BootstrapIcons.ERASER, 40);
        menuItem = new JMenuItem("Delete", icon);
        menu.add(menuItem);

        this.add(menu);

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        leftPanel.setOpaque(false);

        // undo button
        JButton btn = new JButton();
        btn.setIcon(FontIcon.of(BootstrapIcons.ARROW_COUNTERCLOCKWISE, 40));
        btn.setBorderPainted(false);
        leftPanel.add(btn);

        // redo button
        btn = new JButton();
        btn.setIcon(FontIcon.of(BootstrapIcons.ARROW_CLOCKWISE, 40));
        btn.setBorderPainted(false);
        leftPanel.add(btn);

        // open sidebar menu button
        sidebarButton = new JButton();
        sidebarButton.setIcon(FontIcon.of(BootstrapIcons.LIST, 40));
        sidebarButton.setBorderPainted(false);
        leftPanel.add(sidebarButton);

        this.add(leftPanel);
    }

    public JButton getSideBarButton() {
        return sidebarButton;
    }
}
