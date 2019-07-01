package poem.boundary;

import java.util.function.Consumer;

/**
 * An event publisher reacts to the events published by the hexagon boundary. It
 * forwards them to event receivers, and provides the latest processed event.
 * 
 * @author b_muth
 *
 */
public class EventPublisher implements Consumer<Object> {
	private Consumer<Object>[] eventReceivers;

	@SafeVarargs
	public EventPublisher(Consumer<Object>... eventReceivers) {
		this.eventReceivers = eventReceivers;
	}

	@Override
	public void accept(Object eventToBePublished) {
		for (Consumer<Object> eventReceiver : eventReceivers) {
			eventReceiver.accept(eventToBePublished);
		}
	}
}
