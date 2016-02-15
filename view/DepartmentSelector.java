/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;

import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import model.Employee;
import model.Position;

/**
 *
 * @author Александр
 */
public class DepartmentSelector implements ClassSelector {

    @Override
    public void printMenu() {
        System.out.println("    1: Add position");
        System.out.println("    2: View positions");
        System.out.println("    3: Delete position");
        System.out.println("    4: Add employee");
        System.out.println("    5: View employees");
        System.out.println("    6: Delete employee");
        System.out.println("    7: Choose employee");
    }

    @Override
    public void execute(int operation, Controller ctr) {
        Scanner sc = new Scanner(System.in);
        switch (operation) {
            case 1:
                System.out.print("Name: ");
                String posName = sc.next();
                System.out.print("Count: ");
                int freeCount = 0;
                try {
                    freeCount = sc.nextInt();
                    ctr.addPosition(posName, freeCount);
                    System.out.println("Position was succesful added");
                } catch (InputMismatchException ex){
                    System.out.println(ex.toString());
                    System.out.println("Error. Enter integer value");
                }
                catch (Exception ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
                break;
            case 2:
                try {
                    System.out.println("Positions : ");
                    for (Position p : ctr.getPositions()) {
                        if(p == null){
                            System.out.println("Positions not found");
                            break;
                        }
                        System.out.println(p.toString());
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
                break;
            case 3:
                System.out.print("Name: ");
                String posDelName = sc.next();
                try {
                    ctr.removePosition(posDelName);
                    System.out.println("Position was succesful deleted");
                } catch (Exception ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
                break;
            case 4:
                System.out.print("Name: ");
                String empName = sc.next();
                System.out.print("Salary: ");
                double salary = 0;
                String sal = sc.next();
                System.out.print("Position name: ");
                String positName = sc.next();
                try {
                    salary = Double.parseDouble(sal);
                    ctr.addEmployee(empName, salary, positName);
                    System.out.println("Employee was succesful added");
                } catch (NumberFormatException ex){
                    System.out.println(ex.getLocalizedMessage());
                    System.out.println("Error. Please, enter the integer or double value in the field \"Salary\"");
                }
                catch (Exception ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
                break;
            case 5:
                try {
                    System.out.println("Employyes : ");
                    for (Employee e : ctr.getEmployees()) {
                        if(e == null){
                            System.out.println("Employees not found");
                            break;
                        }
                        System.out.println(e.toString());
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
                break;
            case 6:
                System.out.print("Name: ");
                String empNameDel = sc.next();
                try {
                    ctr.removeEmployee(empNameDel);
                    System.out.println("Employee was succesful deleted");
                } catch (Exception ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
                break;
            case 7:
                System.out.print("Name: ");
                String empNameChoose = sc.next();
                try {
                    ctr.chooseEmployee(empNameChoose);
                } catch (Exception ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
                break;
        }
    }

}
