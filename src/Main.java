import java.io.Console;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static final int PORT = 15091;

    public static void main(String[] args) {
        try {
            MulticastSocket socket = new MulticastSocket(PORT);
            InetAddress group = InetAddress.getByName(args[0]);

            String message = "Hello";
            byte[] buf = message.getBytes();

            DatagramPacket datagramPacket1 = new DatagramPacket(buf, buf.length, group, PORT);
            socket.send(datagramPacket1);


    /*        byte[] rbuf = new byte[100];
            DatagramPacket rDatagramPacket = new DatagramPacket(rbuf, rbuf.length);
            socket.receive(rDatagramPacket);
            String s = new String(rDatagramPacket.getAddress().toString());
            System.out.println(s);
        } catch (Exception e) {
            System.out.println("Error");
        }
    }*/

/*            MulticastSocket socket = new MulticastSocket(PORT);*/
            byte[] address = new byte[65];
            List<InetAddress> addresses = new ArrayList<>();
            DatagramPacket datagramPacket = new DatagramPacket(address, address.length, group, PORT);
            while (true) {
                socket.receive(datagramPacket);
                addresses.add(datagramPacket.getAddress());
                System.out.println(addresses);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
