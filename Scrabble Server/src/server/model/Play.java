package server.model;

import java.util.UUID;
import server.model.Player;
import server.model.Grid;
import server.model.Rack;
import server.model.TileBag;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * 
 * @author Romain <ro.foncier@gmail.com>
 */
public class Play {
		private UUID playID;
		private UUID owner;
		private Date created;
		private Date modified;
		private Integer score;
		private Grid grid;
		private Rack rack;
		private TileBag bag;
		
		public Play(String playerID) {
				playID = UUID.randomUUID();
				owner = UUID.fromString(playerID);
				created = new Date();
				score = 0;
				grid = new Grid();
				bag = new TileBag();
				rack = new Rack(bag);
		}
}