package com.github.creme332.controller;

import com.github.creme332.view.*;

/**
 * Controls all the logic in the application by linking views and model.
 */
public class Controller {
    public Frame frame = new Frame(); // frame of app

    public Controller() {
        frame.showFrame();
    }

}