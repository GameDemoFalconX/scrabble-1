package common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>
 */
public class EmailValidator {
 
	private static Pattern pattern;
	private static Matcher matcher;
 
	private static final String EMAIL_PATTERN = 
		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
 
	public EmailValidator() {
		pattern = Pattern.compile(EMAIL_PATTERN);
	}
 
	/**
	 * Validate hex with regular expression 
	 * @param hex hex for validation
	 * @return true valid hex, false invalid hex
	 */
	public static boolean validate(final String hex) {
		matcher = pattern.matcher(hex);
		return matcher.matches();
	}
}
