
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
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            String[] campos = input.split(";");
            try {
                if (campos[0].equals("send") && campos.length==3){
                    sendFile(Integer.parseInt(campos[1]), campos[2]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendFile(int maxClientes, String archivo) throws IOException {
        File file = new File(archivo);
        for (ServerThread conexion : conexiones) {
            if (conexion.isAlive()){
                System.out.println("enviando");
                conexion.sendFile(file);
            }
        }
    }
}