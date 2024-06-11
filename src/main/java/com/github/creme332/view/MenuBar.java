package com.github.creme332.view;

import java.awt.FlowLayout;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.kordamp.ikonli.swing.FontIcon;

import com.github.creme332.utils.exception.InvalidIconSizeException;
import com.github.creme332.utils.exception.InvalidPathException;

import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;

import static com.github.creme332.utils.IconLoader.loadIcon;;

public class MenuBar extends JMenuBar {
    // menubar frame components
    private JButton sidebarButton;

    public JMenu createCursorMenu() throws InvalidIconSizeException, InvalidPathException {
        JMenu menu = new JMenu();
        JMenuItem menuItem;

        menu.setIcon(loadIcon("/icons/cursor.png", 50));

        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");

        menuItem = new JMenuItem("Move",
                loadIcon("/icons/cursor.png", 50));
        menuItem.setMnemonic(KeyEvent.VK_B);
        menu.add(menuItem);

        menuItem = new JMenuItem("Freehand Shape",
                loadIcon("/icons/freehand.png", 50));
        menuItem.setMnemonic(KeyEvent.VK_B);
        menu.add(menuItem);

        return menu;
    }

    public JMenu createLineMenu() throws InvalidIconSizeException, InvalidPathException {
        JMenu menu = new JMenu();
        JMenuItem menuItem;

        menu.setIcon(loadIcon("/icons/line.png", 50));

        menuItem = new JMenuItem("Line: DDA", loadIcon("/icons/line.png", 50));
        menuItem.setMnemonic(KeyEvent.VK_B);
        menu.add(menuItem);

        menuItem = new JMenuItem("Line: Bresenham", loadIcon("/icons/line.png", 50));
        menu.add(menuItem);
        return menu;
    }

    public JMenu createCircleMenu() throws InvalidIconSizeException, InvalidPathException {
        JMenu menu = new JMenu();
        JMenuItem menuItem;

        menu.setIcon(loadIcon("/icons/circle.png", 50));

        menuItem = new JMenuItem("Circle with Center through Point",
                loadIcon("/icons/circle-center.png", 50));
        menuItem.setMnemonic(KeyEvent.VK_B);
        menu.add(menuItem);

        menuItem = new JMenuItem("Circle: Center & Radius", loadIcon("/icons/circle-radius.png", 50));
        menu.add(menuItem);

        return menu;
    }

    public JMenu createEllipseMenu() throws InvalidIconSizeException, InvalidPathException {
        JMenu menu = new JMenu();
        JMenuItem menuItem;

        menu.setIcon(loadIcon("/icons/ellipse.png", 50));

        menuItem = new JMenuItem("Ellipse", loadIcon("/icons/ellipse.png", 50));
        menu.add(menuItem);
        return menu;
    }

    public JMenu createPolygonMenu() throws InvalidIconSizeException, InvalidPathException {
        JMenu menu = new JMenu();
        JMenuItem menuItem;

        menu.setIcon(loadIcon("/icons/triangle.png", 50));

        menuItem = new JMenuItem("Polygon", loadIcon("/icons/triangle.png", 50));
        menu.add(menuItem);

        menuItem = new JMenuItem("Regular Polygon", loadIcon("/icons/regular-polygon.png", 50));
        menu.add(menuItem);
        return menu;
    }

    public JMenu createTransformationsMenu() throws InvalidIconSizeException, InvalidPathException {
        JMenu menu = new JMenu();
        JMenuItem menuItem;
        FontIcon icon;

        menu.setIcon(loadIcon("/icons/reflect-about-line.png", 50));

        menuItem = new JMenuItem("Reflect about Line", loadIcon("/icons/reflect-about-line.png", 50));
        menu.add(menuItem);

        menuItem = new JMenuItem("Reflect about Point", loadIcon("/icons/reflect-about-point.png", 50));
        menu.add(menuItem);

        menuItem = new JMenuItem("Rotate around Point", loadIcon("/icons/rotate-around-point.png", 50));
        menu.add(menuItem);

        menuItem = new JMenuItem("Translation", loadIcon("/icons/translate-vector.png", 50));
        menu.add(menuItem);

        icon = FontIcon.of(BootstrapIcons.ARROWS_ANGLE_EXPAND, 35);
        menuItem = new JMenuItem("Scaling", icon);
        menu.add(menuItem);

        icon = FontIcon.of(BootstrapIcons.BOX_ARROW_DOWN_LEFT, 35);
        menuItem = new JMenuItem("Shear", icon);
        menu.add(menuItem);

        icon = FontIcon.of(BootstrapIcons.SCISSORS, 35);
        menuItem = new JMenuItem("Clipping", icon);
        menu.add(menuItem);

        return menu;
    }

    public JMenu createGraphicsMenu() {
        JMenu menu = new JMenu();
        JMenuItem menuItem;

        menu.setIcon(FontIcon.of(BootstrapIcons.ARROWS_MOVE, 35));

        menuItem = new JMenuItem("Move Graphics View", FontIcon.of(BootstrapIcons.ARROWS_MOVE, 35));
        menu.add(menuItem);

        menuItem = new JMenuItem("Zoom In", FontIcon.of(BootstrapIcons.ZOOM_IN, 35));
        menu.add(menuItem);

        menuItem = new JMenuItem("Zoom Out", FontIcon.of(BootstrapIcons.ZOOM_OUT, 35));
        menu.add(menuItem);

        FontIcon icon = FontIcon.of(BootstrapIcons.ERASER, 40);
        menuItem = new JMenuItem("Delete", icon);
        menu.add(menuItem);

        return menu;
    }

    public MenuBar() throws Exception {
        // create menus and add to menubar
        this.add(createCursorMenu());
        this.add(createLineMenu());
        this.add(createCircleMenu());
        this.add(createEllipseMenu());
        this.add(createPolygonMenu());
        this.add(createTransformationsMenu());
        this.add(createGraphicsMenu());

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
