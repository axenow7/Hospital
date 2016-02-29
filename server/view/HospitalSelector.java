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
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.model.Department;
import server.model.Hospital;
import server.model.Patient;

/**
 *
 * @author Александр
 */
public class HospitalSelector implements ClassSelector {

    private final DataOutputStream out;
    private final DataInputStream sc;

    public HospitalSelector(DataOutputStream out, DataInputStream sc) {
        this.out = out;
        this.sc = sc;
    }

    public void printMenu() throws IOException {
        out.writeUTF("Select option");
        out.writeUTF("    1: Add Hospital");
        out.writeUTF("    2: View Hospitals");
        out.writeUTF("    3: Choose Hospital");
        out.writeUTF("    4: Delete Hospital");
        out.writeUTF("    5: Add Departament");
        out.writeUTF("    6: View Departments");
        out.writeUTF("    7: Choose Department");
        out.writeUTF("    8: Delete Department");
        out.writeUTF("    9: Add patient");
        out.writeUTF("    10: View patients");
    }

    public void execute(int operation, server.controller.Controller ctr) throws IOException {
        switch (operation) {
            case 1:
                out.writeUTF("Name: ");
                String name = sc.readUTF();
                out.writeUTF("INN: ");
                String INN = sc.readUTF();
                out.writeUTF("Date(dd-mm-yyyy): ");
                String date = sc.readUTF();
                while (true) {
                    try {
                        ctr.addHospital(name, INN, date);
                        out.writeUTF("Hospital with name = " + name + " was added");
                        break;
                    } catch (ParseException ex) {
//                        out.writeUTF(ex);
                        out.writeUTF("Data entering error. Enter the date in format \"dd-mm-yyyy\". Press 1 to cancel");
                        date = sc.readUTF();
                        if (date.equals("1")) {
                            out.writeUTF("Cancelled");
                            break;
                        }
//                        continue;
                    } catch (IllegalArgumentException ex) {
                        out.writeUTF("This hospital already exists ");
                        break;
                    } catch (Exception ex) {
                        out.writeUTF("Creating hospital error. press 1 to retry, otherwise press any another key ");
                        if (sc.readUTF().equals("1")) {
                            out.writeUTF("Name: ");
                            name = sc.readUTF();
                            out.writeUTF("INN: ");
                            INN = sc.readUTF();
                            out.writeUTF("Date(dd-mm-yyyy): ");
                            date = sc.readUTF();
                            continue;
                        } else {
                            out.writeUTF("Cancelled.");
                            break;
                        }
                        //                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case 2:
                Set<Hospital> hospitals = ctr.getHospitals();
                out.writeUTF("Hospitals : ");
                for (Hospital h : hospitals) {
                    out.writeUTF(h.toString());
                }
                break;
            case 3:
         //       out.writeUTF("Name:");

                String n = new String();
                Set<Hospital> hospitals1 = ctr.getHospitals();

                out.writeUTF("You can choose one of this hospitals:");
                out.writeUTF("Enter the number");
                for (Hospital h : hospitals1){
                    out.writeUTF(h.toString());
                }

                Hospital[] hospitalArray = hospitals1.toArray(new Hospital[hospitals1.size()]);
                try {
                    int num = Integer.parseInt(sc.readUTF());
                    n = hospitalArray[num - 1].getName();
                    ctr.chooseHospital(n);
                    out.writeUTF("Hospital with name = " + n + " was choosed");

                } catch (NullPointerException ex) {
                    out.writeUTF("Hospital with name: " + n + " not exists");
                } catch (NumberFormatException ex){
                    out.writeUTF("Please, enter the integer value");
                } catch (ArrayIndexOutOfBoundsException ex){
                    out.writeUTF("Hospital with this number is not exist");
                }
                catch (Exception ex) {
                    out.writeUTF("Impossible to choose hospital " + n);
//                    out.writeUTF(ex);
//                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 4:
                out.writeUTF("Name: ");
                String remName = sc.readUTF();

                try {
                    ctr.removeHospital(remName);
                    out.writeUTF("Hospital with name = " + remName + " was deleted");
                } catch (NullPointerException ex) {
                    out.writeUTF("Hospital with name " + remName + " not found");
                } catch (ArrayIndexOutOfBoundsException ex) {
                    out.writeUTF("It have only one hospital. Cant delete last hospital");
                } catch (Exception ex) {
//                    out.writeUTF(ex);
                    out.writeUTF("Deleting hospital error. ");
                    break;
                }

                break;
            case 5:
                out.writeUTF("Name: ");
                String d = sc.readUTF();
                try {
                    ctr.addDepartment(d);
                    out.writeUTF("Department with name = " + d + " was added");
                } catch (ArrayIndexOutOfBoundsException ex) {
                    out.writeUTF("Error. Too long name of department");
                } catch (Exception ex) {
//                    out.writeUTF(ex);
                    out.writeUTF("Department adding error. Try again");
//                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 6:
                try {
                    Set<Department> dep = ctr.getDepartments();
                    out.writeUTF("Departments : ");
                    for (Department depar : dep) {
                        out.writeUTF(depar.toString());
                    }
                } catch (NullPointerException ex) {
                    out.writeUTF("Departments not found! ");
//                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    out.writeUTF("Impossible to view departments");
                }
                break;
            case 7:
                int i = 1;
                String dname = new String();
                try {
                    Set<Department> dep1 = ctr.getDepartments();
                    out.writeUTF("You can choose one of this departments:");
                    out.writeUTF("Enter the number");

                for (Department d1 : dep1){
                    out.writeUTF("[" + i + "]" + d1.toString());
                    i++;
                }

                    int dnum = Integer.parseInt(sc.readUTF());
                    Department[] depArray = dep1.toArray(new Department[dep1.size()]);
                    dname = depArray[dnum - 1].getName();
                    ctr.chooseDepartment(dname);
                    out.writeUTF("Department with name = " + dname + " was choosed");
                } catch (NullPointerException ex) {
                    out.writeUTF("Department with name: \"" + dname + "\" not found");
                }  catch (NumberFormatException ex){
                    out.writeUTF("Please, enter the integer value");
                } catch (ArrayIndexOutOfBoundsException ex){
                    out.writeUTF("Department with this number is not exist");
                }catch (Exception ex) {
                    out.writeUTF("Impossible to choose department " + dname);

//                    out.writeUTF(ex);
//                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 8:
                out.writeUTF("Name: ");
                String delName = sc.readUTF();
                try {
                    ctr.removeDepartment(delName);
                    out.writeUTF("Department with name = " + delName + " was deleted");
                } catch (NoSuchFieldException ex) {
                    out.writeUTF("The department was not found");
                } catch (NullPointerException ex) {
                    out.writeUTF("It have no departments");
                } catch (Exception ex) {
                    out.writeUTF("Impossible to remove department " + delName);

//                    out.writeUTF(ex);
//                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 9:
                out.writeUTF("Enter the name: ");
                String pName = sc.readUTF();
                out.writeUTF("Enter the address: ");
                String address = sc.readUTF();
                out.writeUTF("Enter the passport");
                String passport = sc.readUTF();
                try {
                    ctr.addPatient(pName, address, passport);
                } catch (ArrayIndexOutOfBoundsException ex) {
                    out.writeUTF("Too long name of patient. Try again");
                } catch (Exception ex) {
                    out.writeUTF("Impossible to add patient");
//                    out.writeUTF(ex);
                }
                break;
            case 10:
                if (ctr.getPatients() != null) {
                    for (Patient p : ctr.getPatients()) {
                        out.writeUTF(p.toString());
                    }
                    break;
                } else {
                    out.writeUTF("Patients not found");
                }
        }
    }

}
