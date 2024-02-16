package tinkoff.alltoscala.stack.task2.handler;

import java.time.Duration;

public interface Handler {

	Duration timeout();

	void performOperation();

}
