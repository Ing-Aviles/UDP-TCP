

import java.net.*;
import java.io.*;

class servidorUDP {
    public static void main(String args[]) {
        // Primero indicamos la dirección IP local
        try {
            System.out.println("LocalHost = " + InetAddress.getLocalHost().toString());
        } catch (UnknownHostException uhe) {
            System.err.println("No puedo saber la dirección IP local :" + uhe);
        }
        // Abrimos un Socket UDP en el puerto 1234.
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket(1234);
        } catch (SocketException se) {
            System.err.println("Se ha producido un error al abrir el socket : " + se);
            System.exit(-1);
        }
        // Bucle infinito
        while (true) {
            try {
                // Nos preparamos a recibir un número entero
                byte bufferEntrada[] = new byte[4];
                // Creamos un "contenedor" de datagrama, cuyo

                // será el array creado antes
                DatagramPacket dp = new DatagramPacket(bufferEntrada, 4);
                ds.receive(dp);
                int puerto = dp.getPort();
                // Dirección de Internet desde donde se envió
                InetAddress direcc = dp.getAddress();
                ByteArrayInputStream bais = new ByteArrayInputStream(bufferEntrada);
                DataInputStream dis = new DataInputStream(bais);

                int entrada = dis.readInt();
                long salida = (long) entrada * (long) entrada;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                // Lo envolvemos con un DataOutputStream
                DataOutputStream dos = new DataOutputStream(baos);
                // Escribimos el resultado, que debe ocupar 8
                dos.writeLong(salida);
                // Cerramos el buffer de escritura
                dos.close();
                // Gen

                dp = new DatagramPacket(baos.toByteArray(), 8, direcc, puerto);

                ds.send(dp);
                // Registramos en salida estandard
                System.out.println("Cliente = " + direcc + ":"
                        + puerto + "\tEntrada = " +
                        entrada + "\tSalida = " + salida);
            } catch (Exception e) {
                System.err.println("Se ha producido el error " + e);
            }
        }
    }
}
