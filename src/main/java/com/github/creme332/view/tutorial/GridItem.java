package com.github.creme332.view.tutorial;

public class GridItem {
    private String heading;
    private String imageHeader;

    public GridItem(String heading, String imageHeader) {
        this.heading = heading;
        this.imageHeader = imageHeader;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getImageHeader() {
        return imageHeader;
    }

    public void setImageHeader(String imageHeader) {
        this.imageHeader = imageHeader;
    }
}
