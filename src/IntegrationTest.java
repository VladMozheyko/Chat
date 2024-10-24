import java.io.IOException;
import java.util.ArrayList;

public class IntegrationTest {


    public ArrayList<TCPConnection> tcpConnections;

    String testStr = "Привет!";
    boolean res = false;

    TCPConnectionListener tcpConnectionListener;

    int count;
    public static void main(String[] args) {
        IntegrationTest integrationTest = new IntegrationTest();

        if(integrationTest.testConnection()){
            System.out.println("Тест пройден");
        }
        else {
            System.out.println("Тест не пройден");
        }



    }

    public IntegrationTest() {
        initContext();
        count = 0;
    }

    public boolean testConnection(){
        addConnection();
        sendMessage();
        count++;
        if(count == tcpConnections.size() && res){
            return true;
        }
        else
            return false;
    }

    private void sendMessage() {
        try {
            TCPConnection tcpConnection = new TCPConnection(new TCPConnectionListener() {
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
            }, "127.0.0.1", 8085);
            tcpConnectionListener.onMessageReceived(tcpConnection, "Привет!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void initContext() {

        tcpConnections = new ArrayList<>();
        //   Server server = new Server();         В интеграционном тесте должна запускаться вся система. Сейчас для простоты исходии из того, что сервер запустился

    }

    public void addConnection(){

        tcpConnectionListener = new TCPConnectionListener() {
            @Override
            public void onConnectionReady(TCPConnection tcpConnection) {

            }

            @Override
            public void onDisconnect(TCPConnection TCPConnection) {

            }

            @Override
            public void onMessageReceived(TCPConnection tcpConnect, String str) {
                if(str.equals(testStr)){
                    res = true;
                }

            }

            @Override
            public void onException(TCPConnection tcpConnection, Exception ex) {

            }
        };


        try {
            tcpConnections.add(new TCPConnection(tcpConnectionListener, "127.0.0.1", 8085));
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
