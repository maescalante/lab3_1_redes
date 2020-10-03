import java.io.*;
import java.net.*;
import java.util.Date;

public class Server {

    int port;

    //ServerSocket serverSocket = new ServerSocket(6868);

    // Create a class constructor for the MyClass class
    public Server() throws IOException {
        port = 5000;
    }

    public static void main(String[] args) throws IOException {

        Server servidor = new Server(); // Create an object of class MyClass (This will call the constructor)

        try (ServerSocket serverSocket = new ServerSocket(servidor.port)) {

            System.out.println("Server is listening on port " + servidor.port);

            while (true) {
                Socket socket = serverSocket.accept();

                System.out.println("New client connected");

                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);

                writer.println(new Date().toString());
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

}
