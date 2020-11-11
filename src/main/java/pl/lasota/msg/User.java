package pl.lasota.msg;

import java.util.Objects;

public class User<M> {

    private final Broker<M> broker;
    private final ReceiverMessageEvent<M> receiverListener;
    private final String id;

    public User(String id) {
        this.id = id;
        this.broker = null;
        this.receiverListener = null;
    }

    public User(Broker<M> broker, ReceiverMessageEvent<M> receiverListener, String id) throws UserAlreadyRegistryException {
        this.id = id;
        broker.registryUser(this);
        this.broker = broker;
        this.receiverListener = receiverListener;
    }

    public void sendMessage(M m) {
        if (broker == null) {
            return;
        }
        broker.onBroker(m, this);
    }

    public void sendMessage(M m, String to) {
        if (broker == null) {
            return;
        }
        broker.onBroker(m, this, to);
    }

    public void sendMessage(M m, String... to) {
        if (broker == null) {
            return;
        }
        broker.onBroker(m, this, to);
    }

    public void receiverMessage(M m, String from) {
        if (receiverListener == null) {
            return;
        }
        receiverListener.onReceive(m, this, from);
    }


    public String id() {
        return id;
    }

    public void logout() {
        if (broker == null) {
            return;
        }
        broker.removeUser(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User<?> user = (User<?>) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
