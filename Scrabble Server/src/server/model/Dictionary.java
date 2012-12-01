package server.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Model that contains the authorized words in the game.
	* It's a HashMap of String. The key value is the word. The value itself is null
	* since it's not used. 
 * @author Bernard <bernard.debecker@gmail.com>
 */
public class Dictionary {

		private Map<String, String> dico = new HashMap<>();
		
		final static String filename = "french.dic";
		final static Charset ENCODING = StandardCharsets.UTF_8;
		
		public Dictionary() throws IOException {
				Path path = Paths.get(filename);
				try (BufferedReader reader = Files.newBufferedReader(path, ENCODING)){
      String line;
      while ((line = reader.readLine()) != null) {
        dico.put(line, null);
      }      
    }
		}
		
		/**
			* Check in the dictionary if the word exist in it.
			* @param word a String
			* @return a boolean true if the word is in the dictionary, false otherwise.
			*/
		public boolean containsWord(String word) {
				return dico.containsKey(word);
		}
		
		/**
			* Used only for test purpose
			* @param args
			* @throws IOException 
			*/
		public static void main(String[] args) throws IOException {
				Dictionary dico = new Dictionary();
				System.out.println("BONJOUR : " + dico.containsWord("BONJOUR"));
				System.out.println("ZEKRLJ : " + dico.containsWord("ZEKRLJ"));
		}
		
}