import java.io.*;
import java.net.*;

/**
 * This thread is responsible to handle client connection.
 *
 */
public class ServerThread extends Thread {
    private Socket socket;
    private InputStream input;
    private BufferedReader reader;
    private OutputStream output;
    private PrintWriter writer;
    private boolean isReady;

    public ServerThread(Socket socket) {
        this.socket = socket;
        this.isReady = false;
    }

    public void run() {

        try {
            input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));

            output = socket.getOutputStream();
            writer = new PrintWriter(output, true);

            writer.println("Hello");

            String text;

            do {
                text = reader.readLine();
                String read = new StringBuilder(text).toString();
                //System.out.println(read);
                if (read.equals("Ready")){
                    isReady = true;
                    writer.println("Yep fucking ready");
                }
            } while (!text.equals("bye"));

            writer.println("Closed connection");
            socket.close();

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void sendFile(File file) throws IOException {
        // Envia el nombre del archivo que se va a mandar
        writer.println("prueba.txt");

        // Get the size of the file
        long length = file.length();
        byte[] bytes = new byte[16 * 1024];
        InputStream in = new FileInputStream(file);

        int count;
        while ((count = in.read(bytes)) > 0) {
            output.write(bytes, 0, count);
        }
        System.out.println("Finalizo el envio del archivo");
    }
}