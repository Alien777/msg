package pl.lasota.msg;

/**
 * Broker for net application
 * @param <M>
 */
public class OnlineBroker<M> implements Broker<M> {

    private final LocalBroker<M> localBroker;
    private final Configure<M> configure;

    /**
     * @param localBroker  @{@link LocalBroker}
     * @param configure @{@link Configure}
     */
    public OnlineBroker(LocalBroker<M> localBroker, Configure<M> configure) {
        configure.registryLocalBroker(localBroker);
        this.localBroker = localBroker;
        this.configure = configure;
    }

    @Override
    public void onBroker(M m, User<M> u) {
        configure.options(() -> localBroker.onBroker(m, u));
        configure.onBroker(m, u.id());
    }

    @Override
    public void onBroker(M m, User<M> from, String... to) {
        configure.options(() -> localBroker.onBroker(m, from, to));
        configure.onBroker(m, from.id(), to);
    }

    @Override
    public void registryUser(User<M> user) throws UserAlreadyRegistryException {
        localBroker.registryUser(user);
    }

    @Override
    public void removeUser(User<M> user) {
        localBroker.removeUser(user);
    }
}
