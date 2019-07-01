package poem.simple.driven_adapter;

import java.util.function.Consumer;

import poem.event.RandomVersesPicked;

/**
 * Right-side, driven adapter for writing text to the console.
 * 
 * @author b_muth
 *
 */
public class ConsoleWriter implements Consumer<Object> {
	@Override
	public void accept(Object eventObject) {
		if (eventObject instanceof RandomVersesPicked) {
			RandomVersesPicked event = (RandomVersesPicked) eventObject;
			String[] lines = event.getVerses();
			for (String line : lines) {
				System.out.println(line);
			}
			System.out.println("");
		}
	}
}