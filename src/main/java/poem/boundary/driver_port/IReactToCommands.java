package poem.boundary.driver_port;

import java.util.concurrent.CompletableFuture;

public interface IReactToCommands{
	/**
	 * Dispatches the specified command asynchronously to a command handler,
	 * and returns the event published as a result.
	 * 
	 * @param commandObject the command
	 * 
	 * @return a future to access the resulting event
	 */
	public CompletableFuture<Object> reactTo(Object commandObject);
}
