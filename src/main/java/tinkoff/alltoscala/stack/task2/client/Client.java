package tinkoff.alltoscala.stack.task2.client;

import tinkoff.alltoscala.stack.task2.domain.Address;
import tinkoff.alltoscala.stack.task2.domain.Event;
import tinkoff.alltoscala.stack.task2.domain.Payload;
import tinkoff.alltoscala.stack.task2.domain.Result;

public interface Client {

	//блокирующий метод для чтения данных
	Event readData();

	//блокирующий метод отправки данных
	Result sendData(Address dest, Payload payload);
}
