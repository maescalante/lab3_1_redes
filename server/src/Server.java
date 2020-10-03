import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Server {

    int port;
    ArrayList<ServerThread> conexiones;
    int currentId;

    public Server(String logName) {
        port = 5000;
        conexiones = new ArrayList<>();
        currentId = 0;

    }

    public void main() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            FileWriter fw = new FileWriter("./log.txt", true);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            fw.write(dtf.format(now) + " Server is listening on port " + port + "\n");
            System.out.println("Server is listening on port " + port);

            fw.close();

            while (true) {

                fw = new FileWriter("./log.txt", true);

                Socket socket = serverSocket.accept();
                System.out.println("New client connected, assigned Id: " + currentId);
                now = LocalDateTime.now();
                fw.write(dtf.format(now) + " New client connected, assigned Id: " + currentId + "\n");

                // Agrega la conexion a la lista de conexiones activas
                ServerThread thread = new ServerThread(socket, currentId);
                thread.start();
                conexiones.add(thread);

                InputThread consoleInput = new InputThread(conexiones);
                consoleInput.start();

                currentId++;
                fw.close();
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Server servidor = new Server("./log.txt");
        servidor.main();
    }

}
