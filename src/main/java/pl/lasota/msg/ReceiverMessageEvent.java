package pl.lasota.msg;

/**
 * handle to recive message
 * @param <M>
 */
public interface ReceiverMessageEvent<M> {

    void onReceive(M message, User<M> me, String from);
}
