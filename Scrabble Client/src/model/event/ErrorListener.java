package model.event;

import java.util.EventListener;

/**
 *
 * @author Romain <ro.foncier@gmail.com>
 */
public interface ErrorListener extends EventListener {
		public void displayError(ErrorMessageEvent event);
}