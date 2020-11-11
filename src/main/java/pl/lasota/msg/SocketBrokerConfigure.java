package pl.lasota.msg;


import com.hazelcast.spi.annotation.Beta;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

@Beta
public class SocketBrokerConfigure<M> implements Configure<M> {

    private LocalBroker<M> mLocalBroker;
    private final List<Socket> clients = new LinkedList<>();

    public SocketBrokerConfigure(int portServer) throws IOException {
        ServerSocket serverSocket = new ServerSocket(portServer);
        new Thread(() -> {
            try {
                Socket accept = serverSocket.accept();
                clients.add(accept);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }

    public void addNode(InetSocketAddress address) throws IOException, ClassNotFoundException {
        Socket socket = new Socket(address.getAddress(), address.getPort());
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        while (socket.isConnected()) {
            Message<M> m = (Message<M>) objectInputStream.readObject();
            if (m.getTo().length == 0) {
                mLocalBroker.onBroker(m.getM(), new User<>(m.getFrom()));
                continue;
            }
            mLocalBroker.onBroker(m.getM(), new User<>(m.getFrom()), m.getTo());
        }
    }


    @Override
    public void onBroker(M m, String from) {
        for (Socket client : clients) {
            try {
                ObjectOutputStream objectInputStream = new ObjectOutputStream(client.getOutputStream());
                objectInputStream.writeObject(new Message<>(m, from));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onBroker(M m, String from, String... to) {
        for (Socket client : clients) {
            try {
                ObjectOutputStream objectInputStream = new ObjectOutputStream(client.getOutputStream());
                objectInputStream.writeObject(new Message<>(m, from, to));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void registryLocalBroker(LocalBroker<M> mLocalBroker) {
        this.mLocalBroker = mLocalBroker;
    }

    @Override
    public void options(Config config) {
        config.enableLocalBroker();
    }

}
