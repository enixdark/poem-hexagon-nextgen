package poem.boundary;

import java.util.function.Function;

import org.requirementsascode.Model;

import poem.command.AskForPoem;

/**
 * The use case model ties each type of command to its appropriate command
 * handler interface.
 * 
 * In business terms, this example model means:
 * 
 * The user asks for a poem. The system picks a random poem, and publishes it.
 * 
 * @author b_muth
 *
 */
class UseCaseModel {
	private static final Class<AskForPoem> asksForPoem = AskForPoem.class;

	public static Model build(Function<AskForPoem, Object[]> picksRandomPoem) {
		Model model = Model.builder()
			.user(asksForPoem).systemPublish(picksRandomPoem)
		.build();

		return model;
	}
}
