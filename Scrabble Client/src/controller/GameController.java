package controller;

import model.Play;
import views.GameView;
import views.swing.gameboard.Scrabble;

/**
 * 
 * @author Romain <ro.foncier@gmail.com>
 */
public class GameController {
		public GameView MainView = null;
		private Play play = null; // Model in MVC Architecture

		public GameController (Play play){
				this.play = play;
				MainView = new Scrabble(this);
				addListenersToModel();
		}

		private void addListenersToModel() {
				play.addTileListener(MainView);
				play.addRackListener(MainView);
		}

		public void displayViews(){
				MainView.display();
		}

		public void closeViews(){
				MainView.close();
		}

		public void notifyCreateWord(int sourcePos, int x, int y){
				play.createWord(sourcePos, x, y);
		}
		
		public void notifyModifiedWord(int sX, int sY, int tX, int tY){
				play.modifiedWord(sX, sY, tX, tY);
		}
		
		public void notifyRemoveLetterFromWord(int x, int y, int targetPos){
				play.removeLetterFromWord(x, y, targetPos);
		}
		
		public void notifyOrganizeRack(int sourcePos, int targetPos){
				play.organizeRack(sourcePos, targetPos);
		}
		
		public void notifyReArrangeRack(){
				play.reArrangeRack();
		}
		
		public void notifyValidWord(){
				play.validateWord();
		}
}