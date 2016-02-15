/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;
import model.Department;
import model.Hospital;

/**
 *
 * @author Александр
 */
public class ModelSelector implements ClassSelector {

    @Override
    public void printMenu() {
        System.out.println("Select option");
        System.out.println("    1: Add Hospital");
    }

    @Override
    public void execute(int operation, controller.Controller ctr) {
        Scanner sc = new Scanner(System.in);

        switch (operation) {
            case 1:
                System.out.print("Name: ");
                String name = sc.next();
                System.out.print("INN: ");
                String INN = sc.next();
                System.out.print("Date(dd-mm-yyyy): ");
                String date = sc.next();
                while (true) {
                    try {
                        ctr.addHospital(name, INN, date);
                        System.out.println("Hospital with name = " + name + " was added");
                        break;
                    } catch (ParseException ex){
                        System.out.println(ex.getLocalizedMessage());
                        System.out.println("Date entering error. Please, enter the date in format \"dd-mm-yyyy\". For cancel press 1");
                        date = sc.next();
                        if (date.equals("1")){
                            System.out.println("Adding is cancelled");
                            break;
                        }
                        
                        continue;
                    }
                    catch (Exception ex) {
                        System.out.println(ex.getLocalizedMessage());
                        System.out.println("Hospital creating error. Press 1 to retry, otherwise press any another key ");
                        if (sc.next().equals("1")){
                            System.out.print("Name: ");
                            name = sc.next();
                            System.out.print("INN: ");
                            INN = sc.next();
                            System.out.print("Date(dd-mm-yyyy): ");
                            date = sc.next();
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
