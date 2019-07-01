package poem.boundary;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
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
	private CompletableFuture<Object> latestPublishedEvent;

	@SafeVarargs
	public EventPublisher(Consumer<Object>... eventReceivers) {
		this.eventReceivers = eventReceivers;
		clearSinglePublishedEvent();
	}

	@Override
	public void accept(Object eventToBePublished) {
		for (Consumer<Object> eventReceiver : eventReceivers) {
			eventReceiver.accept(eventToBePublished);
		}
		latestPublishedEvent.complete(eventToBePublished);
	}

	public Optional<Object> takeLatestEvent() {
		Object eventObjectOrNull = latestPublishedEvent.getNow(null);
		Optional<Object> eventObject = Optional.ofNullable(eventObjectOrNull);
		clearSinglePublishedEvent();
		return eventObject;
	}

	private void clearSinglePublishedEvent() {
		latestPublishedEvent = new CompletableFuture<>();
	}
}
