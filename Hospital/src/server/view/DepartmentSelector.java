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

import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

import server.model.Employee;
import server.model.Position;

/**
 *
 * @author Александр
 */
public class DepartmentSelector implements ClassSelector {

    private final DataOutputStream out;
    private final DataInputStream sc;

    public String readAnswer() throws IOException {
//            controller.setClientSocket(sc.readUTF());
        sc.readUTF();
        return sc.readUTF();
    }

    public DepartmentSelector(DataOutputStream out, DataInputStream sc) {
//        super.out=out;
        this.out = out;
//        super.sc = sc;

        this.sc = sc;
    }

    @Override
    public void printMenu() throws IOException {
        out.writeUTF("    1: Add position");
        out.writeUTF("    2: View positions");
        out.writeUTF("    3: Delete position");
        out.writeUTF("    4: Add employee");
        out.writeUTF("    5: View employees");
        out.writeUTF("    6: Delete employee");
        out.writeUTF("    7: Choose employee");
    }

    @Override
    public void execute(int operation, Controller ctr) throws IOException {
        switch (operation) {
            case 1:
                out.writeUTF("Name: ");
                String posName = readAnswer();
                out.writeUTF("Count: ");
                int freeCount = 0;
                freeCount = Integer.parseInt(readAnswer());
                try {
                    ctr.addPosition(posName, freeCount);
                    out.writeUTF("Position was succesful added");
                } catch (InputMismatchException ex) {
                    out.writeUTF("Error. Enter integer value");
                } catch (Exception ex) {
                    out.writeUTF(ex.toString());
                }
                break;
            case 2:
                try {
                    out.writeUTF("Positions : ");
                    for (Position p : ctr.getPositions()) {
                        if (p == null) {
                            out.writeUTF("Positions not found");
                            break;
                        }
                        out.writeUTF(p.toString());
                    }
                } catch (Exception ex) {
                    out.writeUTF("Impossible to view positions");
                }
                break;
            case 3:
                out.writeUTF("Name: ");
                String posDelName = sc.readUTF();
                try {
                    ctr.removePosition(posDelName);
                    out.writeUTF("Position was succesful deleted");
                } catch (Exception ex) {
                    out.writeUTF("Impossible to delete position " + posDelName);
                }
                break;
            case 4:
                out.writeUTF("Name: ");
                String empName = readAnswer();
                out.writeUTF("Salary: ");
                double salary = 0;
                String sal = readAnswer();
                out.writeUTF("Position name: ");
                String positName = readAnswer();
                try {
                    salary = Double.parseDouble(sal);
                    ctr.addEmployee(empName, salary, positName);
                    out.writeUTF("Employee was succesful added");
                } catch (NumberFormatException ex) {
                    out.writeUTF("Error. Please, enter the integer or double value in the field \"Salary\"");
                } catch (ArrayIndexOutOfBoundsException ex) {
                    out.writeUTF("Empty positions " + positName + " not found");
                } catch (Exception ex) {
                    out.writeUTF("Impossible to add employee");
                }
                break;
            case 5:
                try {
                    out.writeUTF("Employyes : ");
                    for (Employee e : ctr.getEmployees()) {
                        if (e == null) {
                            out.writeUTF("Employees not found");
                            break;
                        }
                        out.writeUTF(e.toString());
                    }
                } catch (Exception ex) {
                    out.writeUTF("Impossible to view employees");
                }
                break;
            case 6:
                out.writeUTF("Name: ");
                String empNameDel = readAnswer();
                try {
                    ctr.removeEmployee(empNameDel);
                    out.writeUTF("Employee was succesful deleted");
                } catch (IllegalAccessError iae) {
                    out.writeUTF("Can't delete Employee name="+empNameDel+ ". This employee is used by another client");
                } catch (Exception ex) {
                    out.writeUTF("Impossible to view departments");
                }
                break;
//            case 7:
//
//                Set<Employee> employeeSet =
//                String empNameChoose = sc.readUTF();
//                try {
//                    ctr.chooseEmployee(empNameChoose);
//                } catch (Exception ex) {
//                    out.writeUTF("Impossible to choose employee"+ empNameChoose);
//                }
//                break;
        }
    }

}
