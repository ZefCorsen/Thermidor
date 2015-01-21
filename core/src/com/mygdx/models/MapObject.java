package com.mygdx.models;

import java.awt.geom.Point2D;

/**
 * Created by Whole lotta love on 21/01/2015.
 */
public class MapObject {

    Point2D coordinates;

    public MapObject() {
        coordinates.setLocation(0, 0);
    }

    public void setCoordinates(Point2D point) {

        this.coordinates.setLocation(point);

    }
}
