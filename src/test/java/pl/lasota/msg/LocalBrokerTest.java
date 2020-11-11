package pl.lasota.msg;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;

public class LocalBrokerTest {
    private LocalBroker<String> broker;

    @BeforeEach
    public void beforeTest() {
        broker = new LocalBroker<>();
    }

    @Test
    public void send_message_to_all_local_client() {
        ReceiverMessageEvent<String> piotrekMock = Mockito.mock(ReceiverMessageEvent.class);
        ReceiverMessageEvent<String> adamMock = Mockito.mock(ReceiverMessageEvent.class);
        ReceiverMessageEvent<String> daveMock = Mockito.mock(ReceiverMessageEvent.class);
        User<String> piotrek = new User<>(broker, piotrekMock, "piotrek");
        User<String> adam = new User<>(broker, adamMock, "adam");
        User<String> dave = new User<>(broker, daveMock, "dave");

        piotrek.sendMessage("wiadomość");

        Mockito.verify(piotrekMock, new Times(0)).onReceive(Mockito.any(), Mockito.any(), Mockito.any());
        Mockito.verify(adamMock).onReceive(Mockito.same("wiadomość"), Mockito.same(adam), Mockito.same("piotrek"));
        Mockito.verify(daveMock).onReceive(Mockito.same("wiadomość"), Mockito.same(dave), Mockito.same("piotrek"));
    }


    @Test
    public void send_message_to_specified_local_client() {
        ReceiverMessageEvent<String> piotrekMock = Mockito.mock(ReceiverMessageEvent.class);
        ReceiverMessageEvent<String> adamMock = Mockito.mock(ReceiverMessageEvent.class);
        ReceiverMessageEvent<String> daveMock = Mockito.mock(ReceiverMessageEvent.class);
        User<String> piotrek = new User<>(broker, piotrekMock, "piotrek");
        User<String> adam = new User<>(broker, adamMock, "adam");
        User<String> dave = new User<>(broker, daveMock, "dave");

        piotrek.sendMessage("wiadomość", "dave");

        Mockito.verify(piotrekMock, new Times(0)).onReceive(Mockito.any(), Mockito.any(), Mockito.any());
        Mockito.verify(adamMock, new Times(0)).onReceive(Mockito.any(), Mockito.any(), Mockito.any());
        Mockito.verify(daveMock).onReceive(Mockito.same("wiadomość"), Mockito.same(dave), Mockito.same("piotrek"));
    }

    @Test
    public void expect_if_try_to_add_the_same_client_id() throws Exception {
        new User<>(broker, null, "piotrek");
        Assertions.assertThrows(UserAlreadyRegistryException.class, () -> new User<>(broker, null, "piotrek"));
    }

}
