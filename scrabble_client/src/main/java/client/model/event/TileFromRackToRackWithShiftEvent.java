package client.model.event;

import java.util.EventObject;

/**
 *
 * @author Romain <ro.foncier@gmail.com>
 */
public class TileFromRackToRackWithShiftEvent extends EventObject {

    private final int sourcePosition;
    private final int targetPosition;

    public TileFromRackToRackWithShiftEvent(Object source, int sourcePos, int targetPos) {
        super(source);
        sourcePosition = sourcePos;
        targetPosition = targetPos;
    }

    public int getSourcePosition() {
        return sourcePosition;
    }

    public int getTargetPosition() {
        return targetPosition;
    }
}