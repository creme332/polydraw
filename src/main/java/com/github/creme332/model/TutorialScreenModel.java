package com.github.creme332.model;

import java.util.ArrayList;
import java.util.List;
import com.github.creme332.view.tutorial.TutorialCard;

public class TutorialScreenModel {
    private List<TutorialCard> gridItems;

    public TutorialScreenModel() {
        gridItems = new ArrayList<>();
        loadDefaultItems();
    }

    private void loadDefaultItems() {
        gridItems.add(new TutorialCard("Getting Started", null));
        gridItems.add(new TutorialCard("Draw Line", null));
        gridItems.add(new TutorialCard("Draw Circle", null));
        gridItems.add(new TutorialCard("Draw Polygon", null));
        gridItems.add(new TutorialCard("Rotating Polygons", null));
        gridItems.add(new TutorialCard("Translating Polygons", null));
    }

    public List<TutorialCard> getGridItems() {
        return gridItems;
    }

    public void setGridItems(List<TutorialCard> gridItems) {
        this.gridItems = gridItems;
    }
}
