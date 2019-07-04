package poem.boundary;

import java.util.concurrent.CompletableFuture;
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
	private final Model model;
	private final Consumer<Object> eventPublisher;

	public Boundary(IObtainPoems poemObtainer, Consumer<Object> eventPublisher) {
		this.model = buildModel(poemObtainer);
		this.eventPublisher = eventPublisher;

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
	public CompletableFuture<Object> reactTo(Object commandObject) {
		CompletableFuture<Object> future = CompletableFuture.supplyAsync(() -> handleCommand(commandObject))
				.thenApply(this::publishEvent);
		return future;
	}

	private Object handleCommand(Object commandObject) {
		final CompletableFuture<Object> result = new CompletableFuture<>();
		new ModelRunner().publishWith(result::complete).run(model).reactTo(commandObject);
		return result.join();
	}
	
	public Object publishEvent(Object event) {
		eventPublisher.accept(event);
		return event;
	}
}