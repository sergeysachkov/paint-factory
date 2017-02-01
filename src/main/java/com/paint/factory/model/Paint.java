package com.paint.factory.model;

/**
 * User: Sergey Sachkov
 * Date: 13/07/15
 */
public class Paint {

    private int colour;
    private ColourType type = ColourType.MATE;

    public Paint(int colour, ColourType type) {
        this.colour = colour;
        this.type = type;
    }

    public int getColour() {
        return colour;
    }

    public ColourType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Paint paint = (Paint) o;

        if (colour != paint.colour) return false;
        return type == paint.type;

    }

    @Override
    public int hashCode() {
        int result = colour;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Paint{");
        sb.append("colour=").append(colour);
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }
}
