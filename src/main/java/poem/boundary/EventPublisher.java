package poem.boundary;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class EventPublisher implements Consumer<Object> {
	private Consumer<Object>[] eventReceivers;
	private CompletableFuture<Object> singlePublishedEvent;

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
		singlePublishedEvent.complete(eventToBePublished);
	}

	public Optional<Object> take() {
		Object eventObjectOrNull = singlePublishedEvent.getNow(null);
		Optional<Object> eventObject = Optional.ofNullable(eventObjectOrNull);
		clearSinglePublishedEvent();
		return eventObject;
	}

	private void clearSinglePublishedEvent() {
		singlePublishedEvent = new CompletableFuture<>();
	}
}
