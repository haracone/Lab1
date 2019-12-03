import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

class MessageReceiver {

    static int PORT = 879;
    private static final int WAIT_TIME = 2500;
    private static final int TIME_TO_DELETE = 10000;
    private MulticastSocket socket;
    private ByteBuffer byteBuffer;
    private Map<InetAddress, byte[]> tableOfRequesters;
    private DatagramPacket datagramPacket;

    MessageReceiver(String address) throws IOException {
        socket = new MulticastSocket(PORT);
        InetAddress group = InetAddress.getByName(address);
        tableOfRequesters = new HashMap<>();
        byteBuffer = ByteBuffer.allocate(Long.SIZE);
        byte[] buf = new byte[Long.BYTES];
        datagramPacket = new DatagramPacket(buf, buf.length, group, PORT);
    }

    void ReceiveMessages() throws IOException {
        socket.setSoTimeout(WAIT_TIME);
        while (true) {
            for (Map.Entry<InetAddress, byte[]> entry : tableOfRequesters.entrySet()) {
                long value = bytesToLong(entry.getValue());
                byteBuffer.clear();
                long currentTime = System.currentTimeMillis();
                if (currentTime - value > TIME_TO_DELETE) {
                    System.out.println("Some clients don't send message so we remove them from map, now we have:");
                    tableOfRequesters.remove(entry.getKey());
                    printAddress();
                }
            }

            try {
                socket.receive(datagramPacket);
                System.out.println("We have new clients:");
            } catch (SocketTimeoutException e) {
                ReceiveMessages();
            }
            tableOfRequesters.put(datagramPacket.getAddress(), datagramPacket.getData());
            printAddress();
        }
    }

    private long bytesToLong(byte[] bytes) {
        byteBuffer.put(bytes, 0, bytes.length);
        byteBuffer.flip();
        return byteBuffer.getLong();
    }

    private void printAddress() {
        tableOfRequesters.forEach((key, value) -> System.out.println(key));
    }
}
