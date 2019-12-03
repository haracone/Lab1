import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.ByteBuffer;

class MessageSender {
    private int PORT = 879;
    private MulticastSocket socket;
    private InetAddress group;
    private ByteBuffer byteBuffer;

    MessageSender(String address) throws IOException {
        socket = new MulticastSocket(PORT);
        group = InetAddress.getByName(address);
        byteBuffer = ByteBuffer.allocate(Long.BYTES);
    }

    void SendMessage() throws IOException {
        long time = System.currentTimeMillis();
        byte[] buf = LongToBytes(time);
        DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length, group, PORT);
        socket.send(datagramPacket);
    }

    private byte[] LongToBytes(long time) {
        byteBuffer.putLong(time);
        return byteBuffer.array();
    }
}
