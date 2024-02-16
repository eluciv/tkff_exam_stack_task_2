package tinkoff.alltoscala.stack.task2.domain;

import java.util.List;

public record Event(List<Address> recipients, Payload payload) {

}
