/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Александр
 */
public class EmployeeSelector implements ClassSelector {

    @Override
    public void printMenu() {
        System.out.println("    1: Add visit");
    }

    @Override
    public void execute(int operation, Controller ctr) {
        Scanner sc = new Scanner(System.in);
        switch (operation) {
            case 1:
                System.out.println("Enter the patient name:");
                String name = sc.next();
                try {
                    ctr.addVisit(name);
                } catch (NullPointerException ex) {
                    //System.out.println(ex);
                    System.out.println("Are you want to add patient this name " + name + "? 1-yes; ");
                    if (sc.next().trim().equals("1")) {
                        System.out.println("Enter the address: ");
                        String address = sc.next();
                        System.out.println("Enter the passport");
                        String passport = sc.next();
                        try {
                            ctr.addPatient(name, address, passport);
                        } catch (ArrayIndexOutOfBoundsException ex1){
                            System.out.println("Error. Too long name of patient");
                        }
                        catch (Exception ex1) {
                            System.out.println(ex1);
                        }
                        break;
                    }
                    else {
                        System.out.println("Canceled");
                        break;
                    }
                    //Logger.getLogger(EmployeeSelector.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
    }

}
