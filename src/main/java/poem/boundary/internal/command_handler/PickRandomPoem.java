package poem.boundary.internal.command_handler;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import poem.boundary.driven_port.IObtainPoems;
import poem.boundary.internal.domain.Poem;
import poem.boundary.internal.domain.RandomPoemPicker;
import poem.command.AskForPoem;
import poem.event.RandomVersesPicked;

/**
 * The command handler for displaying a random poem.
 * 
 * @author b_muth
 *
 */
public class PickRandomPoem implements Function<AskForPoem, Object> {
	private IObtainPoems poemObtainer;
	private RandomPoemPicker randomPoemPicker;

	public PickRandomPoem(IObtainPoems poemObtainer) {
		this.poemObtainer = poemObtainer;
		this.randomPoemPicker = new RandomPoemPicker();
	}

	@Override
	public Object apply(AskForPoem askForPoem) {
		List<Poem> poems = obtainPoems(askForPoem);
		Optional<Poem> poem = pickRandomPoem(poems);
		RandomVersesPicked event = getRandomVersesPickedEvent(poem);
		return event;
	}

	private List<Poem> obtainPoems(AskForPoem askForPoem) {
		String language = askForPoem.getLanguage();
		String[] poems = poemObtainer.getMePoems(language);
		List<Poem> poemDomainObjects = Arrays.stream(poems).map(Poem::new).collect(Collectors.toList());
		return poemDomainObjects;
	}

	private Optional<Poem> pickRandomPoem(List<Poem> poemList) {
		Optional<Poem> randomPoem = randomPoemPicker.pickPoem(poemList);
		return randomPoem;
	}

	private RandomVersesPicked getRandomVersesPickedEvent(Optional<Poem> poem) {
		return poem.map(p -> new RandomVersesPicked(p.getVerses())).orElse(null);
	}
}
