package client.model.event;

import java.awt.Point;
import java.util.EventObject;

/**
 *
 * @author Romain <ro.foncier@gmail.com>
 */
public class TileFromRackToGridEvent extends EventObject {

    private final int sourcePosition;
    private final int targetX;
    private final int targetY;
    private final boolean isBlank;

    public TileFromRackToGridEvent(Object source, int sourcePos, int x, int y, boolean isBlank) {
        super(source);
        sourcePosition = sourcePos;
        targetX = x;
        targetY = y;
        this.isBlank = isBlank;
    }

    public int getSourcePosition() {
        return sourcePosition;
    }

    public Point getTargetPosition() {
        return new Point(targetX, targetY);
    }

    public boolean isBlank() {
        return this.isBlank;
    }
}