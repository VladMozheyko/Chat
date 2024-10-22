import java.io.*;
import java.net.Socket;


//TODO Разобраться почем это Стратегия.  Разобраться где здесь Наблюдатель
public class TCPConnection {                             // Класс обертка
    private Socket socket;                               // Сокет для соединения на порту
    private Thread thread;                               // Поток исполнения(нить - тред)
    private BufferedReader reader;                       // Класс обертка для чтения
    private BufferedWriter writer;                       // Класс обертка для записи
    private TCPConnectionListener listener;             // Экземпляр контракта
    private boolean isSender;                           // Переменная, которая указывает на то, что соединение является отправителем сообщения
    private boolean isReceiver;                         // Переменная, которая указывает на то, что соединение является получателем сообщения


    /**
     * Конструктор для подключения клиента
     * @param listener
     * @param ip
     * @param port
     * @throws IOException
     */
    public TCPConnection(TCPConnectionListener listener, String ip, int port) throws IOException {
        this(listener, new Socket(ip, port));
    }


    /**
     * Конструктор для запуска сервера
     * @param listener
     * @param socket
     * @throws IOException
     */
    public TCPConnection(TCPConnectionListener listener, Socket socket) throws IOException {
        this.socket = socket;                       // Порт для работы
        this.listener = listener;                   // Интерфейс
        // Обмен информацией - чтение и запись
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        // Сам сервер
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String msg = "";
                listener.onConnectionReady(TCPConnection.this);          // Открываем сокет для связи по переданному алгоритму
                while (!thread.isInterrupted()){
                    try {
                        msg = reader.readLine();                                     // Получаем строку
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    listener.onMessageReceived(TCPConnection.this, msg);  // Обрабатываем полученное сообщение по переданному алгоритму
                }
            }
        });
        thread.start();
    }

    public synchronized void sendString(String str) {
        try {
            writer.write(str + "\r\n");          // Запись сообщения
            writer.flush();                          // Сброс буфера
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
