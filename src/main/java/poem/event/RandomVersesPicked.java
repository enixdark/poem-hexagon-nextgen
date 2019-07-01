package poem.event;

public class RandomVersesPicked {
	private String[] verses;

	public RandomVersesPicked(String[] verses) {
		this.verses = verses;
	}
	
	public String[] getVerses() {
		return verses;
	}
}
