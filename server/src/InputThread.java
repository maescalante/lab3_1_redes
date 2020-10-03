
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class InputThread extends Thread {

    ArrayList<ServerThread> conexiones;

    public InputThread(ArrayList<ServerThread> conexiones) {
        this.conexiones = conexiones;
    }

    public void run() {

        System.out.println("input thread");
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            try {
                if (input.equals("send")){
                    sendFile();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendFile() throws IOException {
        File file = new File("./prueba.txt");
        for (ServerThread conexion : conexiones) {
            if (conexion.isAlive()){
                System.out.println("enviando");
                conexion.sendFile(file);
            }
        }
    }
}