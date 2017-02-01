package com.paint.factory.model;

/**
 * User: Sergey Sachkov
 * Date: 13/07/15
 */
public enum ColourType {
    GLOSSY(0), MATE(1);

    int value;

    ColourType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
