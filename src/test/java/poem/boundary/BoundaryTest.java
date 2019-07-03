package poem.boundary;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import poem.command.AskForPoem;
import poem.event.RandomVersesPicked;
import poem.simple.driven_adapter.PoemObtainerStub;

public class BoundaryTest {
	private static final String EXPECTED_ENGLISH_POEM = PoemObtainerStub.ENGLISH_POEM;
	private static final String EXPECTED_GERMAN_POEM = PoemObtainerStub.GERMAN_POEM;

	private EventPublisher eventPublisher;
	private Object publishedEvent;
	private Boundary boundary;

	@Before
	public void setup() {
		PoemObtainerStub poemObtainerStub = new PoemObtainerStub();
		eventPublisher = new EventPublisher(event -> publishedEvent = event);
		boundary = new Boundary(poemObtainerStub);
	}

	@Test
	public void englishPoem() throws Exception {
		boundary.reactTo(new AskForPoem("en"), eventPublisher);
		assertPoemIs(EXPECTED_ENGLISH_POEM);
	}

	@Test
	public void englishPoemWhenUnknownLanguage() throws Exception {
		boundary.reactTo(new AskForPoem("fr"), eventPublisher);
		assertPoemIs(EXPECTED_ENGLISH_POEM);
	}

	@Test
	public void germanPoem() throws Exception {
		boundary.reactTo(new AskForPoem("de"), eventPublisher);
		assertPoemIs(EXPECTED_GERMAN_POEM);
	}

	private void assertPoemIs(String expectedPoemVerse) {
		String[] actualPoemVerses = ((RandomVersesPicked)publishedEvent).getVerses();
		assertEquals(expectedPoemVerse, actualPoemVerses[0]);
	}
}
