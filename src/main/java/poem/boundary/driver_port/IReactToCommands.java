package poem.boundary.driver_port;

import java.util.function.Consumer;

public interface IReactToCommands{
	public void reactTo(Object commandObject, Consumer<Object> eventPublisher);
}
