/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;
import server.model.Department;
import server.model.Hospital;

/**
 *
 * @author Александр
 */
public class ModelSelector implements ClassSelector {
    private final DataInputStream sc;
    private final DataOutputStream out;
    public String readAnswer() throws IOException {
//            controller.setClientSocket(sc.readUTF());
        sc.readUTF();
        return sc.readUTF();
    }
    public ModelSelector(DataInputStream sc, DataOutputStream out) {
        this.sc = sc;
        this.out = out;
    }
    
    @Override
    public void printMenu() throws IOException{
        out.writeUTF("Select option");
        out.writeUTF("    1: Add Hospital");
    }

    @Override
    public void execute(int operation, server.controller.Controller ctr) throws IOException {

        switch (operation) {
            case 1:
                out.writeUTF("Name: ");
                out.flush();
                String name = readAnswer();
                out.writeUTF("INN: ");
                String INN = readAnswer();
                out.writeUTF("Date(dd-mm-yyyy): ");
                String date = readAnswer();
                while (true) {
                    try {
                        ctr.addHospital(name, INN, date);
                        out.writeUTF("Hospital with name = " + name + " was added");
                        break;
                    } catch (ParseException ex){
                        out.writeUTF("Date entering error. Please, enter the date in format \"dd-mm-yyyy\". For cancel press 1");
                        date = readAnswer();
                        if (date.equals("1")){
                            out.writeUTF("Adding is cancelled");
                            break;
                        }
                        continue;
                    }
                    catch (Exception ex) {
                        out.writeUTF("Hospital creating error. Press 1 to retry, otherwise press any another key ");
                        if (readAnswer().equals("1")){
                            out.writeUTF("Name: ");
                            name = readAnswer();
                            out.writeUTF("INN: ");
                            INN = readAnswer();
                            out.writeUTF("Date(dd-mm-yyyy): ");
                            date = readAnswer();
                            continue;
                        }
                        else break;
    //                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
        }
    }
}
