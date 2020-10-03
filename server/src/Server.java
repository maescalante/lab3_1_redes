import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {

    int port;
    ArrayList<Socket> sockets;
    ArrayList<ServerThread> conexiones;

    public Server() {
        port = 5000;
        sockets = new ArrayList<>();
        conexiones = new ArrayList<>();
    }

    public void main() {
        // InputThread background = new InputThread().start();
        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Server is listening on port " + port);

            while (true) {

                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                // Agrega la conexion a la lista de conexiones activas
                sockets.add(socket);

                ServerThread thread = new ServerThread(socket);
                thread.start();
                conexiones.add(thread);

                sendFile();

            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void sendFile() throws IOException {
        File file = new File("./prueba.txt");
        for (ServerThread conexion : conexiones) {
            System.out.println("enviando");
            conexion.sendFile(file);
        }
    }

    public static void main(String[] args) {
        Server servidor = new Server();
        servidor.main();
    }

}
