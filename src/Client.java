import java.io.IOException;
import java.util.Scanner;
//TODO Сформировать формат для чтения объект
public class Client implements TCPConnectionListener{

    TCPConnection tcpConnection;  // Контракт для связи клиент и сервера

    public static void main(String[] args) throws IOException {
        new Client();      // Анонимный объект
    }

    public Client() throws IOException {
        System.out.println("Введите сообщение");
        Scanner scanner = new Scanner(System.in);    //Поток ввода из консоли
        tcpConnection = new TCPConnection(this, "127.0.0.1", 8085);
        String msg = "";
        while (true){
            msg = scanner.nextLine();
            tcpConnection.sendString(msg);
        }

    }

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {

    }

    @Override
    public void onDisconnect(TCPConnection TCPConnection) {
        tcpConnection.disconnect();
    }

    @Override
    public void onMessageReceived(TCPConnection tcpConnection, String str) {
        System.out.println("Сообщение:" + str + " от: " + tcpConnection.getSocket().getPort());
    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception ex) {

    }
}
