package com.github.creme332;

import com.formdev.flatlaf.FlatLightLaf;
import com.github.creme332.controller.Controller;

public class App {
    public static void main(String[] args) {
        FlatLightLaf.setup();
        new Controller();
    }
}
