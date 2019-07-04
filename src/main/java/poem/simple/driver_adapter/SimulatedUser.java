package poem.simple.driver_adapter;

import java.util.function.Consumer;

import poem.boundary.driver_port.IReactToCommands;
import poem.command.AskForPoem;

/**
 * The driver adapter. It's on the left side of the hexagon. It sends user
 * requests as command objects to a driver port on the hexagon boundary. For
 * simplicity, sending is done autonomously without user interaction. That's
 * why the class is called {@link SimulatedUser}.
 * 
 * @author b_muth
 *
 */
public class SimulatedUser {
	private IReactToCommands driverPort;
	private Consumer<Object> eventConsumer;

	public SimulatedUser(IReactToCommands driverPort, Consumer<Object> eventConsumer) {
		this.driverPort = driverPort;
		this.eventConsumer = eventConsumer;
	}

	public void run() {
		driverPort.reactTo(new AskForPoem("en"), eventConsumer);
		driverPort.reactTo(new AskForPoem("de"), eventConsumer);
	}
}
