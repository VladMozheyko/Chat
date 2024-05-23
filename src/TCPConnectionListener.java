public interface TCPConnectionListener {

    public void onConnectionReady(TCPConnection tcpConnection);

    public void onDisconnect(TCPConnection TCPConnection);

    public void onMessageReceived(TCPConnection tcpConnect, String str);

    public void onException(TCPConnection tcpConnection, Exception ex);
}
