package poem.boundary;

import java.util.function.Consumer;

public class EventPublisher implements Consumer<Object>{
	private Consumer<Object>[] eventReceivers;

	@SafeVarargs
	public EventPublisher(Consumer<Object>... eventReceivers) {
		this.eventReceivers = eventReceivers;
	}

	@Override
	public void accept(Object event) {
		for (Consumer<Object> eventReceiver : eventReceivers) {
			eventReceiver.accept(event);
		}
	}	
}
