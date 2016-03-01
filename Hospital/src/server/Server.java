/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import server.controller.Controller;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.model.Department;
import server.model.Employee;
import server.model.Hospital;
import server.model.Model;
import server.view.ClassSelector;
import server.view.DepartmentSelector;
import server.view.EmployeeSelector;
import server.view.HospitalSelector;
import server.view.ModelSelector;

/**
 *
 * @author Александр
 */
public class Server {

    private final static String pathToBase = "C://Igri/da1.xml";
    private final static Model model = Model.loadModel(pathToBase);

    public SocketProcessor createSocketProcessor(Socket s) throws IOException {
        return new SocketProcessor(s);
    }

    private static class SocketProcessor implements Runnable {

        private final Controller controller = new Controller(model);
        private final DataInputStream dis;
        private final DataOutputStream dos;
        private final Socket s;

        public String readAnswer() throws IOException {
            controller.setClientSocket(dis.readUTF());
            return dis.readUTF();
        }

        public SocketProcessor(Socket s) throws IOException {
            this.dis = new DataInputStream(s.getInputStream());
            this.dos = new DataOutputStream(s.getOutputStream());
            this.s = s;
//            model = Model.loadModel(pathToBase);
//            controller.loadModel(pathToBase);
//            controller.setModel(model);
        }

        @Override
        public void run() {
            System.out.println("Client with ip address = " + s.getInetAddress() + " accepted");
//            if (!model.getClients().contains(s)) {
            
//            }
//            String line = null;
            while (true) {
                try {
//                    line = dis.readUTF();
                    workWithClient();
//                    System.out.println("Client write line: " + line);
//                    dos.writeUTF("Client write line ^ " + line);
//                    dos.flush();
                } catch (SocketException ex1) {
                    System.out.println("Client has off");
                    Thread.currentThread().stop();

                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);

                } catch (InterruptedException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        public void workWithClient() throws IOException, SocketException, InterruptedException {
            int num = -1;
            String clientSocket = "";
            try {
//                clientSocket = dis.readUTF();
//                controller.setClientSocket(clientSocket);
                num = Integer.parseInt(readAnswer());
                System.out.println(controller.getClientSocket());
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
//            } catch (SocketException ex){
//                this.wait();
//            }
            DataOutputStream out = this.dos;
            ClassSelector executor = null;
            String numb = new String();
            System.out.println(this.controller.getModel());
//            System.out.println(this.controller.getModel().hospitals);
//            System.out.println(this.controller.nowObject.isEmpty());
            while (num != 0) {
                System.out.println("Add socket "+ s + " with nowObject = "+ controller.nowObject);
                model.addClient(s, controller.nowObject);
                if (this.controller.nowObject.isEmpty() || this.controller.getModel().hospitals == null) {
                    executor = new ModelSelector(dis, dos);
                } else if (this.controller.nowObject.getLast() instanceof Hospital) {
                    executor = new HospitalSelector(dos, dis);
                } else if (this.controller.nowObject.getLast() instanceof Department) {
                    executor = new DepartmentSelector(dos, dis);
                } else if (this.controller.nowObject.getLast() instanceof Employee) {
                    executor = new EmployeeSelector(dis, dos);
                }
                if (!this.controller.nowObject.isEmpty()) {
                    out.writeUTF("Current object : " + this.controller.nowObject.getLast());
                }
                executor.printMenu();
                out.writeUTF("    99: level up");
                out.writeUTF("    100: commit");
                out.writeUTF("    101: load model");
                out.writeUTF("    102: save model");
                out.writeUTF("    0: exit");
                numb = readAnswer();
                num = Integer.parseInt(numb);
//                while (true) {
//                    try {
//                        break;
//                    } catch (NumberFormatException ex) {
//                        out.writeUTF("Error. Enter the integer value");
//                        continue;
//                    }
//                }
                executor.execute(num, this.controller);
                switch (num) {
                    case 99:
                        this.controller.levelUp();
                        break;
                    case 100:
//                        out.writeUTF("Enter the path to file");
//                        String path = dis.readUTF();
                        this.controller.saveModel(pathToBase);
                        break;
                    case 101:
                        out.writeUTF("Enter the path to file");
                        String path1 = readAnswer();
                        this.controller.loadModel(path1);
                        break;
//                    case 0:
//                        System.exit(0);
                }
//            } else if (model.nowObject instanceof Department) {
//            }
            }
        }

    }

    public static void main(String[] args) {
        int port = 6789;
        ServerSocket ss;
        Server ser = new Server();
        try {
            ss = new ServerSocket(port);
            System.out.println("Server was created");
            while (true) {
                System.out.println("Waiting clients");
                Socket s = ss.accept();
                System.out.println("Client 123");
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
//                dos.writeUTF("Waiting client");
                SocketProcessor sp = ser.createSocketProcessor(s);
                System.out.println("sp created");
                Thread t = new Thread(sp);
//                try {
                t.start();
//                } catch (Exception ex) {
//                    System.out.println("Thread stop");
//                    t.stop();
//                }

                System.out.println("start thread");
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
