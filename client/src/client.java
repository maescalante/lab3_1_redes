import java.io.*;
import java.net.Socket;

public class client {
    private static String ip = "54.234.96.150";
    private static int port= 5000;
    public static void main(String[] args) throws Exception{
        Socket sock = new Socket(ip, port);
        PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(sock.getInputStream()));
        String fromServer="";
        String fromUser="";

        if ((fromServer = in.readLine()) != null) {
            System.out.println("Server: " + fromServer);
            if (fromServer.equals("Hello")){
                out.println("Ready");
                fromServer= in.readLine();
                System.out.println("Server: " + fromServer);
                FileOutputStream outFile = new FileOutputStream("./"+fromServer);
                InputStream inFile= sock.getInputStream();
                byte[] bytes = new byte[16*1024];

                int count;
                while ((count = inFile.read(bytes)) > 0) {
                    outFile.write(bytes, 0, count);
                }
                outFile.close();
                inFile.close();
                System.out.println("Cerrando conexión");
                out.println("Bye");
                out.close();
                in.close();
                sock.close();

            }

        }
    }
}
