import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {

    int port;
    ArrayList<ServerThread> conexiones;

    public Server() {
        port = 5000;
        conexiones = new ArrayList<>();
    }

    public void main() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Server is listening on port " + port);

            while (true) {

                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                // Agrega la conexion a la lista de conexiones activas
                ServerThread thread = new ServerThread(socket);
                thread.start();
                conexiones.add(thread);

                InputThread consoleInput = new InputThread(conexiones);
                consoleInput.start();

            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server servidor = new Server();
        servidor.main();
    }

}
