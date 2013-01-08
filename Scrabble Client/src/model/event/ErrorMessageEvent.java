package model.event;

import java.util.EventObject;

/**
 * 
 * @author Romain <ro.foncier@gmail.com>
 */
public class ErrorMessageEvent extends EventObject {
		
		private final String message;
		
		public ErrorMessageEvent(Object source, String error) {
				super(source);
				this.message = error;
		}
		
		public String getMessage() {
				return this.message;
		}
}