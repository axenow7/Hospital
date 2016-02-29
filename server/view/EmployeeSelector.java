/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import server.controller.Controller;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Александр
 */
public class EmployeeSelector implements ClassSelector {

    private final DataInputStream sc;
    private final DataOutputStream out;

    public EmployeeSelector(DataInputStream sc, DataOutputStream out) {
        this.sc = sc;
        this.out = out;
    }

    @Override
    public void printMenu() throws IOException {
        out.writeUTF("    1: Add visit");
    }

    @Override
    public void execute(int operation, Controller ctr) throws IOException{
        switch (operation) {
            case 1:
                out.writeUTF("Enter the patient name:");
                String name = sc.readUTF();
                try {
                    ctr.addVisit(name);
                } catch (NullPointerException ex) {
                    out.writeUTF("Are you want to add patient this name " + name + "? 1-yes; ");
                    if (sc.readUTF().trim().equals("1")) {
                        out.writeUTF("Enter the address: ");
                        String address = sc.readUTF();
                        out.writeUTF("Enter the passport");
                        String passport = sc.readUTF();
                        try {
                            ctr.addPatient(name, address, passport);
                        } catch (ArrayIndexOutOfBoundsException ex1) {
                            out.writeUTF("Error. Too long name of patient");
                        } catch (Exception ex1) {
                            out.writeUTF("Impossible to add parient");
                        }
                        break;
                    } else {
                        out.writeUTF("Canceled");
                        break;
                    }
                    //Logger.getLogger(EmployeeSelector.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
    }

}
