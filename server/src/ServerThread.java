import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This thread is responsible to handle client connection.
 */
public class ServerThread extends Thread {
    private Socket socket;
    private InputStream input;
    private BufferedReader reader;
    private OutputStream output;
    private PrintWriter writer;
    private boolean isReady;
    private int index;


    public ServerThread(Socket socket, int index) {
        this.socket = socket;
        this.isReady = false;
        this.index = index;

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

                if (read.equals("Ready")) {
                    isReady = true;
                }
                if (read.equals("OK")){
                    System.out.println("Confirmacion Recivida");
                }

            } while (!text.equals("bye"));


            System.out.println("Conexion cerrada");
            socket.close();

        } catch (IOException ex) {
            System.out.println("Excepcion del Servidor: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void sendFile(File file) throws IOException, NoSuchAlgorithmException {

        System.out.println(isReady);
        if (isReady) {
            // Envia el nombre del archivo que se va a mandar
            MessageDigest shaDigest = MessageDigest.getInstance("SHA-256");
            String shaChecksum = getFileChecksum(shaDigest, file);
            //System.out.println(shaChecksum);

            writer.println(file.getName()+","+shaChecksum);


            //writer.println(file.length());

            // Get the size of the file
            //long length = file.length();
            byte[] bytes = new byte[16 * 1024];
            InputStream in = new FileInputStream(file);

            int count;
            long startTime = System.nanoTime();
            while ((count = in.read(bytes)) > 0) {
                output.write(bytes, 0, count);
            }
            long elapsedTime = System.nanoTime() - startTime;
            System.out.println("Tiempo para enviar el archivo: "
                    + elapsedTime/1000000000+ "s");
            System.out.println("Archivo Enviado!");
            socket.shutdownOutput();
            //output.close();




        }

    }

    private static String getFileChecksum(MessageDigest digest, File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] byteArray = new byte[1024];
        int bytesCount = 0;

        while ((bytesCount = fis.read(byteArray)) != -1) {
            digest.update(byteArray, 0, bytesCount);
        }
        ;

        fis.close();
        byte[] bytes = digest.digest();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}