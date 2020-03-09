/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Samuel
 */
public class ChatServer extends Thread {

    ServerSocket serverSocket;
    Socket socketClientes;
    DataInputStream is;
    DataOutputStream os;
    static ArrayList<Socket> arraySocket = new ArrayList<Socket>();

    public ChatServer(Socket socket, ArrayList<Socket> arraySocket) {
        socketClientes = socket;
        this.arraySocket = arraySocket;
        arraySocket.add(socketClientes);
    }

    public void reenvioClientes(String mensaje, String usuario) {
        try {
            
            for (Socket cliente : arraySocket) {
             
                String mensajeEnv = usuario + ": " + mensaje;
                os = new DataOutputStream(cliente.getOutputStream());
                try{
                os.writeUTF(mensajeEnv);
                }catch(Exception ex)
            {    
            }}
            os.flush();

        } catch (IOException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   public void reenvioClientesLleno(String mensaje, String usuario,Socket socketCliente) {
        try {
            
            for (Socket cliente : arraySocket) {
                if(socketCliente.equals(cliente)){
                String mensajeEnv = "servidorLleno";
                os = new DataOutputStream(cliente.getOutputStream());
                try {
                os.writeUTF(mensajeEnv);
                } catch (Exception ex) {
                
                }
                 os.flush();
                }}
        } catch (IOException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static int selecPuerto() {
        String puerto = JOptionPane.showInputDialog("Indique puerto donde alojar su servidor");
        int port = 0;
        if (puerto.length() >= 2 && puerto.matches("[0-9]+")) {
            port = Integer.parseInt(puerto);
        } else {
            port = selecPuerto();
        }
        return port;

    }

    public void run() {

        try {
            boolean compConexion = true;
            System.out.println("Hilo iniciado!");
            is = new DataInputStream(new BufferedInputStream(socketClientes.getInputStream()));
            os = new DataOutputStream(socketClientes.getOutputStream());
            String usuario = is.readUTF();
            if(arraySocket.size()>10){
                
                reenvioClientesLleno("lleno", usuario,socketClientes);
                if(arraySocket.contains(socketClientes)){
                arraySocket.remove(socketClientes);
            }
            }else{
            int contador=0;
            if(contador<=2){
            while (compConexion=true) {
                String mensajeLeer = is.readUTF();
                reenvioClientes(mensajeLeer, usuario);
            }}}
            
                
        } catch (IOException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {

        try {
            System.out.println("Creando socket servidor");
            ServerSocket serverSocket = new ServerSocket();
            System.out.println("Realizando el bind");  
            int puertoCOnexion=Integer.parseInt(JOptionPane.showInputDialog(null, "¿Que puerto desea usar?"));
            InetSocketAddress addr = new InetSocketAddress("localhost", puertoCOnexion);
            serverSocket.bind(addr);
            System.out.println("Aceptando conexiones");
            while (serverSocket != null) {
                int contador=0;
                Socket cliente = serverSocket.accept();
                System.out.println("Conexión recibida");
                ChatServer obj = new ChatServer(cliente, arraySocket);
               //
               if(contador<=2)
                obj.start();
//                else{
//                JOptionPane.showMessageDialog(null, "maximo de conexiones alcanzado!");
//                }
                    
                
                
            }
               
        } catch (IOException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
