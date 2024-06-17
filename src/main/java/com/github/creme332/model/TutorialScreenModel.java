package com.github.creme332.model;

import java.util.ArrayList;
import java.util.List;
import com.github.creme332.view.tutorial.GridItem;

public class TutorialScreenModel {
    private List<GridItem> gridItems;

    public TutorialScreenModel() {
        gridItems = new ArrayList<>();
        loadDefaultItems();
    }

    private void loadDefaultItems() {
        gridItems.add(new GridItem("Getting Started", null));
        gridItems.add(new GridItem("Draw Line", null));
        gridItems.add(new GridItem("Draw Circle", null));
        gridItems.add(new GridItem("Draw Polygon", null));
        gridItems.add(new GridItem("Rotating Polygons", null));
        gridItems.add(new GridItem("Translating Polygons", null));
    }

    public List<GridItem> getGridItems() {
        return gridItems;
    }

    public void setGridItems(List<GridItem> gridItems) {
        this.gridItems = gridItems;
    }
}
