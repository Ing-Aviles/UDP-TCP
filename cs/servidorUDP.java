import java.net.*;
import java.io.*;

class servidorUDP {
    public static void main(String args[]) {
        
        try {
            System.out.println("LocalHost = " + InetAddress.getLocalHost().toString());
        } catch (UnknownHostException uhe) {
            System.err.println("No puedo saber la dirección IP local :" + uhe);
        }
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket(1234);
        } catch (SocketException se) {
            System.err.println("Se ha producido un error al abrir el socket : " + se);
            System.exit(-1);
        }
        
        while (true) {
            try {
                
                byte bufferEntrada[] = new byte[4];
                
                DatagramPacket dp = new DatagramPacket(bufferEntrada, 4);
                ds.receive(dp);
                int puerto = dp.getPort();
                
                InetAddress direcc = dp.getAddress();
                ByteArrayInputStream bais = new ByteArrayInputStream(bufferEntrada);
                DataInputStream dis = new DataInputStream(bais);

                int entrada = dis.readInt();
                long salida = (long) entrada * (long) entrada;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                
                DataOutputStream dos = new DataOutputStream(baos);
                
                dos.writeLong(salida);
                dos.close();

                dp = new DatagramPacket(baos.toByteArray(), 8, direcc, puerto);

                ds.send(dp);
                
                System.out.println("Cliente = " + direcc + ":"
                        + puerto + "\tEntrada = " +
                        entrada + "\tSalida = " + salida);
            } catch (Exception e) {
                System.err.println("Se ha producido el error " + e);
            }
        }
    }
}
