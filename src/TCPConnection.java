import java.io.*;
import java.net.Socket;

public class TCPConnection {
    private Socket socket;
    private Thread thread;
    private BufferedReader reader;
    private BufferedWriter writer;
    private TCPConnectionListener listener;             // Экземпляр контракта
    private boolean isSender;                           // Переменная, которая указывает на то, что соединение является отправителем сообщения
    private boolean isReceiver;                         // Переменная, которая указывает на то, что соединение является получателем сообщения

    public TCPConnection(TCPConnectionListener listener, String ip, int port) throws IOException {
        this(listener, new Socket(ip, port));
    }

    public TCPConnection(TCPConnectionListener listener, Socket socket) throws IOException {
        this.socket = socket;
        this.listener = listener;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String msg = "";
                listener.onConnectionReady(TCPConnection.this);
                while (!thread.isInterrupted()){
                    try {
                        msg = reader.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    listener.onMessageReceived(TCPConnection.this, msg);
                }
            }
        });
        thread.start();
    }

    public synchronized void sendString(String str) {
        try {
            writer.write(str + "\r\n");
            writer.flush();
        } catch (IOException e) {
            listener.onException(TCPConnection.this, e);
            disconnect();
        }
    }

    public synchronized void disconnect() {
        thread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            listener.onException(TCPConnection.this, e);
        }
    }

    public synchronized void setReceiverTrue() {
        isReceiver = true;
    }

    public synchronized void setReceiverFalse() {
        isReceiver = false;
    }

    public synchronized boolean isReceiver() {
        return isReceiver;
    }


    public synchronized void setSenderTrue() {
        isSender = true;
    }

    public synchronized void setSenderFalse() {
        isSender = false;
    }

    public synchronized boolean isSender() {
        return isSender;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public String toString() {
        return "TCPConnection: " + socket.getInetAddress() + ": " + socket.getPort();
    }
}
