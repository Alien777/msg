package pl.lasota.msg;

/**
 * look hazelcast implementation online broker @{@link HazelcastBrokerConfigure}
 * @param <M>
 */
public interface Configure<M> {


    /**
     * Send message to all user registry expect sender.
     *
     *
     * @param m message
     * @param from sender
     */
    void onBroker(M m, String from);

    /**
     * Send message to specific user
     * @param m message
     * @param from sender
     * @param to receiver
     */
    void onBroker(M m, String from, String... to);

    /**
     *
     *
     * @param mLocalBroker @{@link LocalBroker}
     */
    void registryLocalBroker(LocalBroker<M> mLocalBroker);

    /**
     * enable local broker.
     * @param config
     */
    void options(Config config);
}
