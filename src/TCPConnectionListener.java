public interface TCPConnectionListener {// Контракт для работы клиента и сервера


    // 4 операции, которые мы будем выполнять
    public void onConnectionReady(TCPConnection tcpConnection);

    public void onDisconnect(TCPConnection TCPConnection);

    public void onMessageReceived(TCPConnection tcpConnect, String str);

    public void onException(TCPConnection tcpConnection, Exception ex);
}
