package pl.lasota.msg;

public interface Broker<M> {

    /**
     * Send message to all user registry expect sender.
     *
     *
     * @param m message
     * @param from sender
     */
    void onBroker(M m, User<M> from);

    /**
     * Send message to specific user
     * @param m message
     * @param from sender
     * @param to receiver
     */
    void onBroker(M m, User<M> from, String... to);

    /**
     * User registry for message participation
     * @param user
     */
    void registryUser(User<M> user) throws UserAlreadyRegistryException;

    /**
     * User unregistry for message participation
     * @param user
     */
    void removeUser(User<M> user);

}
