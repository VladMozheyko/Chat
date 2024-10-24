import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class UnitTest {


    public ArrayList<TCPConnection> tcpConnections;

    int count;
    public static void main(String[] args) {
        UnitTest unitTest = new UnitTest();

        if(unitTest.testConnection()){
            System.out.println("Тест пройден");
        }
        else {
            System.out.println("Тест не пройден");
        }

    }

    public UnitTest() {
        initContext();
        count = 0;
    }

    public boolean testConnection(){
        addConnection();
        count++;
        if(count == tcpConnections.size()){
            return true;
        }
        else
            return false;
    }

    private void initContext() {

        tcpConnections = new ArrayList<>();
    }

    public void addConnection(){
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


        try {
            tcpConnections.add(new TCPConnection(tcpConnectionListener, "127.0.0.1", 8085));
        } catch (IOException e) {

        }
    }
}
