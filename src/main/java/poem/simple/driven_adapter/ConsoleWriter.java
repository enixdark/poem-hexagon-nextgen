package poem.simple.driven_adapter;

import java.util.function.Consumer;

import poem.boundary.internal.domain.Poem;

/**
 * Right-side, driven adapter for writing text to the console.
 * 
 * @author b_muth
 *
 */
public class ConsoleWriter implements Consumer<Object> {
	@Override
	public void accept(Object eventObject) {
		if (eventObject instanceof Poem) {
			Poem poem = (Poem) eventObject;
			String[] lines = poem.getVerses();
			for (String line : lines) {
				System.out.println(line);
			}
			System.out.println("");
		}
	}
}