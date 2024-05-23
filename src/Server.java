import java.net.ServerSocket;
import java.util.ArrayList;

public class Server implements TCPConnectionListener {

    public ArrayList<TCPConnection> getConnections() {
        return connections;
    }

    private ArrayList<TCPConnection> connections = new ArrayList<>();
    TCPConnection connection;
    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        try(ServerSocket serverSocket = new ServerSocket(8085)) {
            System.out.println("Server running...");
            while (true){
               connection = new TCPConnection(this, serverSocket.accept());
                System.out.println("Клиент подключился" );
            }
        }
        catch (Exception ex){
            //  System.out.println("Error");

        }
    }

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
         connections.add(tcpConnection);
         sendToAllConnections("Привет от сервера");

    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) {
        tcpConnection.disconnect();
    }

    @Override
    public void onMessageReceived(TCPConnection tcpConnection, String str) {
        tcpConnection.setSenderTrue();
        for (int i = 0; i < connections.size(); i++) {
            connections.get(i).setReceiverTrue();
        }

        sendToAllConnections(str);
        tcpConnection.setSenderFalse();
    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception ex) {
        tcpConnection.disconnect();
        connections.remove(tcpConnection);
    }

    public void sendToAllConnections(String msg){
        for (int i = 0; i < connections.size(); i++) {
              if(connections.get(i).isReceiver() && !connections.get(i).isSender()){
                  connections.get(i).sendString(msg);
              }
        }
    }
}
