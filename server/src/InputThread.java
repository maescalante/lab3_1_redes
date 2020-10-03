import java.util.Scanner;

class InputThread extends Thread {
    private final Server server;

    public InputThread(Server server) {
        this.server = server;
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            System.out.println(sc);
            // blocks for input, but won't block the server's thread
        }
    }
}