package pl.lasota.msg;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.topic.ITopic;

public class Main {

    static User<String> adam;
    static User<String> piotrek;
    static User<String> kamil;
    static User<String> wojtek;
    static User<String> sebastian;
    static User<String> paulina;
    static ReceiverMessageEvent<String> rme = (s, m, f) -> {

        System.out.println(m + " od " + f + ": " + s);

    };

    public static void main(String[] args) {
        ITopic<Message<String>> tr = Hazelcast.newHazelcastInstance().getReliableTopic("TR");

        serverOne();
        serverTwo();


        wojtek.sendMessage("WITAJCE");
    }

    private static void serverTwo() {
        ITopic<Message<String>> tr2 = HazelcastClient.newHazelcastClient().getReliableTopic("TR");
        HazelcastBrokerConfigure<String> online = new HazelcastBrokerConfigure<>(tr2);
        LocalBroker<String> localBroker = new LocalBroker<>();
        OnlineBroker<String> onlineBroker = new OnlineBroker<>(localBroker, online);
        wojtek = new User<>(onlineBroker, rme, "wojtek");
        sebastian = new User<>(onlineBroker, new ReceiverMessageEvent<String>() {
            @Override
            public void onReceive(String s, User<String> m, String f) {
                System.out.println(m + " od " + f + ": " + s);
                m.sendMessage("Dostalem wiadomosc od: " + f);
            }
        }, "sebastian");
        paulina = new User<>(onlineBroker, rme, "paulina");
    }

    private static void serverOne() {
        ITopic<Message<String>> tr2 = HazelcastClient.newHazelcastClient().getReliableTopic("TR");
        HazelcastBrokerConfigure<String> online = new HazelcastBrokerConfigure<>(tr2);
        LocalBroker<String> localBroker = new LocalBroker<>();
        OnlineBroker<String> onlineBroker = new OnlineBroker<>(localBroker, online);
        adam = new User<>(onlineBroker, rme, "adam");
        piotrek = new User<>(onlineBroker, new ReceiverMessageEvent<String>() {
            @Override
            public void onReceive(String s, User<String> m, String f) {
                System.out.println(m + " od " + f + ": " + s);
                m.sendMessage("Dostalem wiadomosc od: " + f);
            }
        }, "piotrek");
        kamil = new User<>(onlineBroker, rme, "kamil");


    }
}
