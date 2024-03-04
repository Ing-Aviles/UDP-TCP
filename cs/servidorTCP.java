import java.net.*;
import java.io.*;

public class servidorTCP{
    public static void main(String args[]) {
        try {
            System.out.println("LocalHost = " + InetAddress.getLocalHost().toString());
        } catch (UnknownHostException uhe) {
            System.err.println("No puedo saber la dirección IP local : " + uhe);
        }
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(1234);
        } catch (IOException ioe) {
            System.err.println("Error al abrir el socket de servidor : " + ioe);
            System.exit(-1);
        }
        int entrada;
        long salida;
        
        while (true) {
            try {
                
                Socket sckt = ss.accept();
                
                DataInputStream dis = new DataInputStream(sckt.getInputStream());
                DataOutputStream dos = new DataOutputStream(sckt.getOutputStream());
                
                int puerto = sckt.getPort();
                
                InetAddress direcc = sckt.getInetAddress();
                
                entrada = dis.readInt();
                
                salida = (long) entrada * (long) entrada;
                
                dos.writeLong(salida);
                
                dis.close();
                dos.close();
                sckt.close();
                System.out.println(
                        "Cliente = " + direcc + ":" + puerto + "\tEntrada = " + entrada + "\tSalida = " + salida);
            } catch (Exception e) {
                System.err.println("Se ha producido la excepción : " + e);
            }
        }
    }
}