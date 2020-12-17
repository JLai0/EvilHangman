import java.util.*;

public class HangmanManager {
	private final int wordLength;
	private int guesses;
	private TreeSet<Character> guessedLetters = new TreeSet<Character>();
	private String currentPattern = "";
	private TreeSet<String> setWords = new TreeSet<String>();

	public HangmanManager(Collection<String> dictionary, int length, int max) {
		this.wordLength = length;
		this.guesses = max;
		for (String word : dictionary) {
			if (word != null && word.length() == length) {
				setWords.add(word);
			}
		}
		for (int x = 0; x < wordLength; x++) {
			this.currentPattern += "- ";
		}
		this.currentPattern = this.currentPattern.substring(0, currentPattern.length() - 1);
		throwException();

	}

	public TreeSet<String> words() {
		return setWords;
	}

	public int guessesLeft() {
		return this.guesses;
	}

	public TreeSet<Character> guesses() {
		return this.guessedLetters;
	}

	public String pattern() {
		throwException();
		return currentPattern;

	}

	public void throwException() {
		if (this.wordLength < 1) {
			throw new IllegalArgumentException("Word length cannot be less than 1");
		}
		if (setWords.size() == 0) {
			throw new IllegalStateException("There are no words that can be guessed");
		}
		if (this.guesses < 1) {
			throw new IllegalStateException("There are no guesses remaining");
		}

	}

	public int record(char guess) {
		throwException();
		if (guessedLetters.contains(guess)) {
			throw new IllegalArgumentException("Letter already guessed");
		}
		guessedLetters.add(guess);
		TreeMap<String, TreeSet<String>> possibleSets = new TreeMap<String, TreeSet<String>>();
		String newPattern = "";
		int mostOccurances = 0;
		for (String word : setWords) {
			int newOccurances = 0;
			for (int x = 0; x < wordLength; x++) {
				if (word.charAt(x) == guess) {
					newPattern = newPattern + guess + " ";
					newOccurances++;
				} else if ((currentPattern.charAt(x) != '-') && (currentPattern.charAt(x) != ' ')) {
					newPattern = newPattern + word.charAt(x) + ' ';
				} else {
					newPattern = newPattern + "- ";
				}
			}
			if(newOccurances > mostOccurances) {
				mostOccurances = newOccurances;
			}
		}
		return mostOccurances;
	}
}
