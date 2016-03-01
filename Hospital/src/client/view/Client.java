/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Александр
 */
public class Client {

    public static void main(String[] args) {
        int serverPort = 6789;
        String ip = "127.0.0.1";
        try {
            InetAddress ipAddress = InetAddress.getByName(ip);
            java.net.Socket socket = new java.net.Socket(ipAddress, serverPort);
            socket.setSoTimeout(1000);
//            System.out.println("1 "+socket.getLocalAddress()+" "+socket.getLocalPort());
            System.out.println("2 "+ socket.getLocalSocketAddress()+" ");
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line = null;
            while (true) {
//                System.out.println("Enter the text here: ");
                line = br.readLine();
                if(line.trim().equals("0")){
                    System.exit(0);
                }
//                System.out.println("Sending text to server");
                dos.writeUTF(socket.getLocalSocketAddress().toString());
                dos.writeUTF(line);
                dos.flush();
                String answer = null;
                ArrayList<String> response = new ArrayList<String>();
                try {
                    do {
                        answer = dis.readUTF();
                        response.add(answer);
                    } while ((answer != null && !answer.equals("")));
                } catch (java.net.SocketTimeoutException ex) {
                    Iterator it = response.iterator();
                    for (; it.hasNext();) {
                        System.out.println(it.next());
                    }
                }
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
