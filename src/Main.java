public class Main {

    public static void main(String[] args) {
        try {
            MessageReceiver messageReceiver = new MessageReceiver(args[0]);
            MessageSender messageSender = new MessageSender(args[0]);

            messageSender.SendMessage();
            messageReceiver.ReceiveMessages();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
