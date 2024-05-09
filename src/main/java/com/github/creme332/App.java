package com.github.creme332;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.github.creme332.controller.Controller;

public class App {
    public static void main(String[] args) {
        FlatMacLightLaf.setup();
        new Controller();
    }
}
