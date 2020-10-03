import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;

public class Server {

    int port;
    ArrayList<Socket> sockets;

    //ServerSocket serverSocket = new ServerSocket(6868);

    // Create a class constructor for the MyClass class
    public Server() throws IOException {
        port = 5000;
        sockets = new ArrayList<Socket>();
    }

    public static void main(String[] args) throws IOException {

        Server servidor = new Server(); // Create an object of class MyClass (This will call the constructor)

        try (ServerSocket serverSocket = new ServerSocket(servidor.port)) {

            System.out.println("Server is listening on port " + servidor.port);

            while (true) {
                Socket socket = serverSocket.accept();

                System.out.println("New client connected");

                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);

                writer.println("Hello");

                String text;

                do {
                    text = reader.readLine();

                    String read = new StringBuilder(text).toString();

                    //System.out.println(read);

                    if (read.equals("Ready")){
                        // Agrega la conexion a la lista de conexiones activas
                        servidor.sockets.add(socket);

                        // Envia el nombre del archivo que se va a mandar
                        writer.println("prueba.txt");

                        File file = new File("./prueba.txt");
                        // Get the size of the file
                        long length = file.length();
                        byte[] bytes = new byte[16 * 1024];
                        InputStream in = new FileInputStream(file);

                        int count;
                        while ((count = in.read(bytes)) > 0) {
                            output.write(bytes, 0, count);
                        }
                    }


                } while (!text.equals("bye"));

                socket.close();
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

}
