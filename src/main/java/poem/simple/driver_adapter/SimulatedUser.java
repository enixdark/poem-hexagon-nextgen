package poem.simple.driver_adapter;

import poem.boundary.EventPublisher;
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
	private EventPublisher eventPublisher;

	public SimulatedUser(IReactToCommands driverPort, EventPublisher eventPublisher) {
		this.driverPort = driverPort;
		this.eventPublisher = eventPublisher;
	}

	public void run() {
		driverPort.reactTo(new AskForPoem("en"), eventPublisher);
		driverPort.reactTo(new AskForPoem("de"), eventPublisher);
	}
}
