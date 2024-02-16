package tinkoff.alltoscala.stack.task2.handler;

import tinkoff.alltoscala.stack.task2.client.Client;
import tinkoff.alltoscala.stack.task2.domain.Address;
import tinkoff.alltoscala.stack.task2.domain.Event;
import tinkoff.alltoscala.stack.task2.domain.Payload;
import tinkoff.alltoscala.stack.task2.domain.Result;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class HandlerImpl implements Handler {

	private final Client client;
	private final ExecutorService executor = ForkJoinPool.commonPool();
	private final Executor delayedExecutor = CompletableFuture.delayedExecutor(timeout().toNanos(), TimeUnit.NANOSECONDS, executor);

	public HandlerImpl(Client client) {
		this.client = client;
	}

	@Override
	public Duration timeout() {
		return Duration.of(10, ChronoUnit.SECONDS);
	}

	@Override
	public void performOperation() {
		while (true) {
			Event event = client.readData();
			for (Address address : event.recipients()) {
				executor.submit(createSendTask(address, event.payload()));
			}
		}
	}

	private Runnable createSendTask(Address address, Payload payload) {
		return () -> {
			Result result = client.sendData(address, payload);
			if (result == Result.REJECTED) {
				delayedExecutor.execute(createSendTask(address, payload));
			}
		};
	}

}
