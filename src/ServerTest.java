import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;



class ServerTest {

    TCPConnectionListener tcpConnectionListener = new TCPConnectionListener() {
        @Override
        public void onConnectionReady(TCPConnection tcpConnection) {
        }

        @Override
        public void onDisconnect(TCPConnection TCPConnection) {

        }

        @Override
        public void onMessageReceived(TCPConnection tcpConnect, String str) {

        }

        @Override
        public void onException(TCPConnection tcpConnection, Exception ex) {

        }
    };

    @Test
    public void testConnection() {
        Server server = new Server();
        try {
            server.onConnectionReady(new TCPConnection(tcpConnectionListener,"127.0.0.1", 8085));
        } catch (IOException e) {

        }
        Assertions.assertEquals(1, server.getConnections().size());
    }
}
