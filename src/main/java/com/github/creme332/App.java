package com.github.creme332;

import com.github.creme332.controller.Controller;
import com.github.creme332.utils.PolydrawTheme;

public class App {
    public static void main(String[] args) {
        PolydrawTheme.setup();
        new Controller();
    }
}
