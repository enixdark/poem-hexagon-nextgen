package poem.boundary;

import java.util.function.Consumer;

import org.requirementsascode.Model;
import org.requirementsascode.ModelRunner;

import poem.boundary.driven_port.IObtainPoems;
import poem.boundary.driver_port.IReactToCommands;
import poem.boundary.internal.command_handler.PickRandomPoem;

/**
 * The boundary class is the only point of communication with left-side driver
 * adapters. It accepts commands, and calls the appropriate command handler.
 * 
 * On creation, this class wires up the dependencies between command types and
 * command handlers, by injecting the command handlers into a use case model.
 * 
 * After creation, this class sends each command it receives to the runner of
 * the use case model. The model runner then dispatches the command to the
 * appropriate command handler, which in turn calls the driven adapters.
 * 
 * @author b_muth
 *
 */
public class Boundary implements IReactToCommands {
	private Model model;

	public Boundary(IObtainPoems poemObtainer) {
		model = buildModel(poemObtainer);

	}

	private Model buildModel(IObtainPoems poemObtainer) {
		// Create the command handler(s)
		PickRandomPoem pickRandomPoem = new PickRandomPoem(poemObtainer);

		// Inject command handler(s) into use case model, to tie them to command
		// types.
		Model model = UseCaseModel.build(pickRandomPoem);
		return model;
	}

	@Override
	public void reactTo(Object commandObject, Consumer<Object> eventPublisher) {
		ModelRunner modelRunner = new ModelRunner().publishWith(eventPublisher).run(model);
		modelRunner.reactTo(commandObject);
	}
}