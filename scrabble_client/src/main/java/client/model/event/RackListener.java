package client.model.event;

import java.util.EventListener;

/**
 * @author Romain <ro.foncier@gmail.com>
 */
public interface RackListener extends EventListener {

    public void initRack(InitRackEvent event);
    public void rackReArrange(RackReArrangeEvent event);
}