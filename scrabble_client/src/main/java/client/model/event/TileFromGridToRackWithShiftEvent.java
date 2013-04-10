package client.model.event;

import java.awt.Point;
import java.util.EventObject;

/**
 *
 * @author Romain <ro.foncier@gmail.com>
 */
public class TileFromGridToRackWithShiftEvent extends EventObject {

    private final int targetPosition;
    private final int sourceX;
    private final int sourceY;
    private final boolean isBlank;

    public TileFromGridToRackWithShiftEvent(Object source, int x, int y, int targetPos, boolean isBlank) {
        super(source);
        sourceX = x;
        sourceY = y;
        targetPosition = targetPos;
        this.isBlank = isBlank;
    }

    public Point getSourcePosition() {
        return new Point(sourceX, sourceY);
    }

    public int getTargetPosition() {
        return targetPosition;
    }

    public boolean isBlank() {
        return this.isBlank;
    }
}