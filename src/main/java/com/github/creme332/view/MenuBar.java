package com.github.creme332.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;

import com.github.creme332.utils.IconLoader;

public class MenuBar extends JMenuBar {
    // menubar frame components
    private JMenu menu;
    private JMenuItem menuItem;
    private JButton sidebarButton;
    private JButton guidelinesButton; // Added guidelines button

    public MenuBar() throws Exception {
        FontIcon icon;

        // Set background color and border for the JMenuBar
        this.setBackground(new Color(60, 63, 65)); // Dark background color
        Border border = BorderFactory.createLineBorder(new Color(40, 40, 40), 2);
        this.setBorder(border);

        // Set font for the menus and menu items
        Font menuFont = new Font("Arial", Font.PLAIN, 16);

        // Build the menu for cursor
        menu = new JMenu();
        menu.setIcon(IconLoader.loadIcon("/icons/cursor.png", 50));
        menu.setToolTipText("Cursor");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.setFont(menuFont);
        menu.setForeground(Color.WHITE); // White text color
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        this.add(menu);

        menuItem = new JMenuItem("Move",
                IconLoader.loadIcon("/icons/cursor.png", 50));
        menuItem.setMnemonic(KeyEvent.VK_B);
        menuItem.setFont(menuFont);
        menuItem.setForeground(Color.WHITE); // White text color
        menu.add(menuItem);

        menuItem = new JMenuItem("Freehand Shape",
                IconLoader.loadIcon("/icons/freehand.png", 50));
        menuItem.setMnemonic(KeyEvent.VK_B);
        menuItem.setFont(menuFont);
        menuItem.setForeground(Color.WHITE); // White text color
        menu.add(menuItem);

        // Build menu for line drawing
        menu = new JMenu();
        menu.setIcon(IconLoader.loadIcon("/icons/line.png", 50));
        menu.setToolTipText("Line Drawing");
        menu.setFont(menuFont);
        menu.setForeground(Color.WHITE); // White text color
        menuItem = new JMenuItem("Line: DDA", IconLoader.loadIcon("/icons/line.png", 50));
        menuItem.setMnemonic(KeyEvent.VK_B);
        menuItem.setFont(menuFont);
        menuItem.setForeground(Color.WHITE); // White text color
        menu.add(menuItem);

        menuItem = new JMenuItem("Line: Bresenham", IconLoader.loadIcon("/icons/line.png", 50));
        menuItem.setFont(menuFont);
        menuItem.setForeground(Color.WHITE); // White text color
        menu.add(menuItem);

        this.add(menu);

        // Build menu for circle
        menu = new JMenu();
        menu.setIcon(IconLoader.loadIcon("/icons/circle.png", 50));
        menu.setToolTipText("Circle");
        menu.setFont(menuFont);
        menu.setForeground(Color.WHITE); // White text color
        menuItem = new JMenuItem("Circle with Center through Point",
                IconLoader.loadIcon("/icons/circle-center.png", 50));
        menuItem.setMnemonic(KeyEvent.VK_B);
        menuItem.setFont(menuFont);
        menuItem.setForeground(Color.WHITE); // White text color
        menu.add(menuItem);

        menuItem = new JMenuItem("Circle: Center & Radius", IconLoader.loadIcon("/icons/circle-radius.png", 50));
        menuItem.setFont(menuFont);
        menuItem.setForeground(Color.WHITE); // White text color
        menu.add(menuItem);

        this.add(menu);

        // ellipse menu
        menu = new JMenu();
        menu.setIcon(IconLoader.loadIcon("/icons/ellipse.png", 50));
        menu.setToolTipText("Ellipse");
        menu.setFont(menuFont);
        menu.setForeground(Color.WHITE); // White text color
        menuItem = new JMenuItem("Ellipse", IconLoader.loadIcon("/icons/ellipse.png", 50));
        menuItem.setFont(menuFont);
        menuItem.setForeground(Color.WHITE); // White text color
        menu.add(menuItem);

        this.add(menu);

        // polygon menu
        menu = new JMenu();
        menu.setIcon(IconLoader.loadIcon("/icons/triangle.png", 50));
        menu.setToolTipText("Polygon");
        menu.setFont(menuFont);
        menu.setForeground(Color.WHITE); // White text color
        menuItem = new JMenuItem("Polygon", IconLoader.loadIcon("/icons/triangle.png", 50));
        menuItem.setFont(menuFont);
        menuItem.setForeground(Color.WHITE); // White text color
        menu.add(menuItem);

        menuItem = new JMenuItem("Regular Polygon", IconLoader.loadIcon("/icons/regular-polygon.png", 50));
        menuItem.setFont(menuFont);
        menuItem.setForeground(Color.WHITE); // White text color
        menu.add(menuItem);

        this.add(menu);

        // transformations menu
        menu = new JMenu();
        menu.setIcon(IconLoader.loadIcon("/icons/reflect-about-line.png", 50));
        menu.setToolTipText("Transformations");
        menu.setFont(menuFont);
        menu.setForeground(Color.WHITE); // White text color
        menuItem = new JMenuItem("Reflect about Line", IconLoader.loadIcon("/icons/reflect-about-line.png", 50));
        menuItem.setFont(menuFont);
        menuItem.setForeground(Color.WHITE); // White text color
        menu.add(menuItem);

        menuItem = new JMenuItem("Reflect about Point", IconLoader.loadIcon("/icons/reflect-about-point.png", 50));
        menuItem.setFont(menuFont);
        menuItem.setForeground(Color.WHITE); // White text color
        menu.add(menuItem);

        menuItem = new JMenuItem("Rotate around Point", IconLoader.loadIcon("/icons/rotate-around-point.png", 50));
        menuItem.setFont(menuFont);
        menuItem.setForeground(Color.WHITE); // White text color
        menu.add(menuItem);

        this.add(menu);

        // move menu
        menu = new JMenu();
        menu.setIcon(FontIcon.of(BootstrapIcons.ARROWS_MOVE, 35));
        menu.setToolTipText("Move");
        menu.setFont(menuFont);
        menu.setForeground(Color.WHITE); // White text color
        menuItem = new JMenuItem("Move Graphics View", FontIcon.of(BootstrapIcons.ARROWS_MOVE, 35));
        menuItem.setFont(menuFont);
        menuItem.setForeground(Color.WHITE); // White text color
        menu.add(menuItem);

        menuItem = new JMenuItem("Zoom In", FontIcon.of(BootstrapIcons.ZOOM_IN, 35));
        menuItem.setFont(menuFont);
        menuItem.setForeground(Color.WHITE); // White text color
        menu.add(menuItem);

        menuItem = new JMenuItem("Zoom Out", FontIcon.of(BootstrapIcons.ZOOM_OUT, 35));
        menuItem.setFont(menuFont);
        menuItem.setForeground(Color.WHITE); // White text color
        menu.add(menuItem);

        icon = FontIcon.of(BootstrapIcons.ERASER, 40);
        menuItem = new JMenuItem("Delete", icon);
        menuItem.setFont(menuFont);
        menuItem.setForeground(Color.WHITE); // White text color
        menu.add(menuItem);

        this.add(menu);

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        leftPanel.setOpaque(false);

        // undo button
        JButton btn = new JButton();
        btn.setToolTipText("Undo");
        btn.setIcon(FontIcon.of(BootstrapIcons.ARROW_COUNTERCLOCKWISE, 40));
        btn.setBorderPainted(false);
        btn.setBackground(new Color(60, 63, 65)); // Same as menubar
        btn.setForeground(Color.WHITE);
        btn.setOpaque(true);
        leftPanel.add(btn);

        // redo button
        btn = new JButton();
        btn.setToolTipText("redo");
        btn.setIcon(FontIcon.of(BootstrapIcons.ARROW_CLOCKWISE, 40));
        btn.setBorderPainted(false);
        btn.setBackground(new Color(60, 63, 65)); // Same as menubar
        btn.setForeground(Color.WHITE);
        btn.setOpaque(true);
        leftPanel.add(btn);

        // open sidebar menu button
        sidebarButton = new JButton();
        sidebarButton.setToolTipText("Sidebar");
        sidebarButton.setIcon(FontIcon.of(BootstrapIcons.LIST, 40));
        sidebarButton.setBorderPainted(false);
        sidebarButton.setBackground(new Color(60, 63, 65)); // Same as menubar
        sidebarButton.setForeground(Color.WHITE);
        sidebarButton.setOpaque(true);
        leftPanel.add(sidebarButton);

        // guidelines button
        guidelinesButton = new JButton();
        guidelinesButton.setToolTipText("Toggle Guidelines");
        guidelinesButton.setIcon(FontIcon.of(BootstrapIcons.BORDER_OUTER, 40));
        guidelinesButton.setBorderPainted(false);
        guidelinesButton.setBackground(new Color(60, 63, 65)); // Same as menubar
        guidelinesButton.setForeground(Color.WHITE);
        guidelinesButton.setOpaque(true);
        leftPanel.add(guidelinesButton);

        this.add(leftPanel);
    }

    public JButton getSideBarButton() {
        return sidebarButton;
    }

    public JButton getGuidelinesButton() {
        return guidelinesButton;
    }
}
