import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class client {
    private static String ip = "54.234.96.150";
    private static int port = 5000;

    public static void main(String[] args) throws Exception {
        Socket sock = new Socket(ip, port);
        PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(sock.getInputStream()));
        String fromServer = "";
        String fromUser = "";

        if ((fromServer = in.readLine()) != null) {
            System.out.println("Server: " + fromServer);
            if (fromServer.equals("Hello")) {
                out.println("Ready");
                fromServer = in.readLine();
                if (fromServer.contains(".")) {
                    System.out.println("Recibiendo archivo");
                    System.out.println("Server: " + fromServer);
                    FileOutputStream outFile = new FileOutputStream("./" + fromServer);
                    InputStream inFile = sock.getInputStream();

                    int count;
                    byte[] buffer = new byte[16 * 1024]; // or 4096, or more
                    while ((count = inFile.read(buffer)) > 0)
                    {
                        outFile.write(buffer, 0, count);
                    }

                    System.out.println("Cerrando conexión");
                    out.println("bye");
                    outFile.close();
                    inFile.close();
                }

                // out.println("Bye");
                out.close();
                in.close();
                sock.close();

            }

        }
    }
}
