import java.io.*;
import java.net.Socket;
import java.security.MessageDigest;
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
                    while ((count = inFile.read(buffer)) > 0) {
                        outFile.write(buffer, 0, count);
                        System.out.println("bytes recibidos:" + count);
                    }
                    System.out.println("Archivo recibido");
                    if (sock.isConnected()){
                        System.out.println("F");
                    }


                    String  checksum=in.readLine();


                    System.out.println(checksum);
                    MessageDigest shaDigest = MessageDigest.getInstance("SHA-256");

                    //SHA-1 checksum
                    File file = new File("./" + fromServer);
                    String shaChecksum = getFileChecksum(shaDigest,file );
                    System.out.println(shaChecksum);


                    System.out.println("Cerrando conexión");
                    out.println("bye");
                    outFile.close();
                    inFile.close();

                }

                // out.println("Bye");
                out.close();
                in.close();
                sock.close();
                System.out.println("Conexión cerrada");

            }

        }
    }

    private static String getFileChecksum(MessageDigest digest, File file) throws IOException
    {

        FileInputStream fis = new FileInputStream(file);


        byte[] byteArray = new byte[1024];
        int bytesCount = 0;


        while ((bytesCount = fis.read(byteArray)) != -1) {
            digest.update(byteArray, 0, bytesCount);
        };


        fis.close();


        byte[] bytes = digest.digest();


        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        //return complete hash
        return sb.toString();
    }
}
