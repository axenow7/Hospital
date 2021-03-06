/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.text.ParseException;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Department;
import model.Hospital;
import model.Patient;

/**
 *
 * @author Александр
 */
public class HospitalSelector implements ClassSelector {

    public void printMenu() {
        System.out.println("Select option");
        System.out.println("    1: Add Hospital");
        System.out.println("    2: View Hospitals");
        System.out.println("    3: Choose Hospital");
        System.out.println("    4: Delete Hospital");
        System.out.println("    5: Add Departament");
        System.out.println("    6: View Departments");
        System.out.println("    7: Choose Department");
        System.out.println("    8: Delete Department");
        System.out.println("    9: Add patient");
        System.out.println("    10: View patients");
    }

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
                    } catch (ParseException ex) {
//                        System.out.println(ex);
                        System.out.println("Data entering error. Enter the date in format \"dd-mm-yyyy\". Press 1 to cancel");
                        date = sc.next();
                        if (date.equals("1")) {
                            System.out.println("Cancelled");
                            break;
                        }
//                        continue;
                    } catch (IllegalArgumentException ex) {
                        System.out.println("This hospital already exists ");
                        break;
                    } catch (Exception ex) {
                        System.out.println("Creating hospital error. press 1 to retry, otherwise press any another key ");
                        if (sc.next().equals("1")) {
                            System.out.print("Name: ");
                            name = sc.next();
                            System.out.print("INN: ");
                            INN = sc.next();
                            System.out.print("Date(dd-mm-yyyy): ");
                            date = sc.next();
                            continue;
                        } else {
                            System.out.println("Cancelled.");
                            break;
                        }
                        //                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case 2:
                Set<Hospital> hospitals = ctr.getHospitals();
                System.out.println("Hospitals : ");
                for (Hospital h : hospitals) {
                    System.out.println(h.toString());
                }
                break;
            case 3:
                System.out.print("Name:");
                String n = sc.next();
                try {
                    ctr.chooseHospital(n);
                    System.out.println("Hospital with name = " + n + " was choosed");

                } catch (NullPointerException ex) {
                    System.out.println("Hospital with name: " + n + " not exists");
                } catch (Exception ex) {
                    System.out.println("Impossible to choose hospital " + n);
//                    System.out.println(ex);
//                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 4:
                System.out.println("Name: ");
                String remName = sc.next();

                try {
                    ctr.removeHospital(remName);
                    System.out.println("Hospital with name = " + remName + " was deleted");
                } catch (NullPointerException ex) {
                    System.out.println("Hospital with name " + remName + " not found");
                } catch (ArrayIndexOutOfBoundsException ex) {
                    System.out.println("It have only one hospital. Cant delete last hospital");
                } catch (Exception ex) {
//                    System.out.println(ex);
                    System.out.println("Deleting hospital error. ");
                    break;
                }

                break;
            case 5:
                System.out.print("Name: ");
                String d = sc.next();
                try {
                    ctr.addDepartment(d);
                    System.out.println("Department with name = " + d + " was added");
                } catch (ArrayIndexOutOfBoundsException ex) {
                    System.out.println("Error. Too long name of department");
                } catch (Exception ex) {
//                    System.out.println(ex);
                    System.out.println("Department adding error. Try again");
//                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 6:
                try {
                    Set<Department> dep = ctr.getDepartments();
                    System.out.println("Departments : ");
                    for (Department depar : dep) {
                        System.out.println(depar.toString());
                    }
                } catch (NullPointerException ex) {
                    System.out.println("Departments not found! ");
//                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    System.out.println("Impossible to view departments");
                }
                break;
            case 7:
                System.out.println("Name: ");
                String dname = sc.next();
                try {
                    ctr.chooseDepartment(dname);
                    System.out.println("Department with name = " + dname + " was choosed");
                } catch (NullPointerException ex) {
                    System.out.println("Department with name: \"" + dname + "\" not found");
                } catch (Exception ex) {
                    System.out.println("Impossible to choose department " + dname);

//                    System.out.println(ex);
//                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 8:
                System.out.print("Name: ");
                String delName = sc.next();
                try {
                    ctr.removeDepartment(delName);
                    System.out.println("Department with name = " + delName + " was deleted");
                } catch (NoSuchFieldException ex) {
                    System.out.println("The department was not found");
                } catch (NullPointerException ex) {
                    System.out.println("It have no departments");
                } catch (Exception ex) {
                    System.out.println("Impossible to remove department " + delName);

//                    System.out.println(ex);
//                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 9:
                System.out.println("Enter the name: ");
                String pName = sc.next();
                System.out.println("Enter the address: ");
                String address = sc.next();
                System.out.println("Enter the passport");
                String passport = sc.next();
                try {
                    ctr.addPatient(pName, address, passport);
                } catch (ArrayIndexOutOfBoundsException ex) {
                    System.out.println("Too long name of patient. Try again");
                } catch (Exception ex) {
                    System.out.println("Impossible to add patient");
//                    System.out.println(ex);
                }
                break;
            case 10:
                if (ctr.getPatients() != null) {
                    for (Patient p : ctr.getPatients()) {
                        System.out.println(p.toString());
                    }
                    break;
                } else {
                    System.out.println("Patients not found");
                }
        }
    }

}
