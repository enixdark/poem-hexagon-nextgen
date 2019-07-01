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

	private Boundary boundary;
	private EventPublisher eventPublisher;

	@Before
	public void setup() {
		PoemObtainerStub poemObtainerStub = new PoemObtainerStub();
		eventPublisher = new EventPublisher();
		boundary = new Boundary(poemObtainerStub, eventPublisher);
	}

	@Test
	public void englishPoem() throws Exception {
		boundary.reactTo(new AskForPoem("en"));
		assertPoemIs(EXPECTED_ENGLISH_POEM);
	}

	@Test
	public void englishPoemWhenUnknownLanguage() throws Exception {
		boundary.reactTo(new AskForPoem("fr"));
		assertPoemIs(EXPECTED_ENGLISH_POEM);
	}

	@Test
	public void germanPoem() throws Exception {
		boundary.reactTo(new AskForPoem("de"));
		assertPoemIs(EXPECTED_GERMAN_POEM);
	}

	private void assertPoemIs(String expectedPoemVerse) {
		String[] actualPoemVerses = getLatestPoemVerses();
		assertEquals(expectedPoemVerse, actualPoemVerses[0]);
	}

	private String[] getLatestPoemVerses() {
		String[] actualPoemVerses = eventPublisher.takeLatestEvent().map(event -> (RandomVersesPicked) event)
				.map(event -> event.getVerses()).orElse(new String[0]);
		return actualPoemVerses;
	}
}
