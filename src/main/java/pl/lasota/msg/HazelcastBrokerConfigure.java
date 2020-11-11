package pl.lasota.msg;


import com.hazelcast.topic.ITopic;

public class HazelcastBrokerConfigure<M> implements Configure<M> {

    private final ITopic<Message<M>> tr;

    public HazelcastBrokerConfigure(ITopic<Message<M>> tr) {
        this.tr = tr;
    }

    @Override
    public void onBroker(M m, String from) {
        tr.publish(new Message<>(m, from));
    }

    @Override
    public void onBroker(M m, String from, String... to) {
        tr.publish(new Message<>(m, from, to));
    }

    @Override
    public void registryLocalBroker(LocalBroker<M> mLocalBroker) {
        tr.addMessageListener(message -> {
           Message<M> m = message.getMessageObject();
            if (m.getTo().length == 0) {
                mLocalBroker.onBroker(m.getM(), new User<>(m.getFrom()));
                return;
            }
            mLocalBroker.onBroker(m.getM(), new User<>(m.getFrom()), m.getTo());
        });
    }

    @Override
    public void options(Config config) {

    }

}
