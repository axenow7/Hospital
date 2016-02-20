/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;
import model.Department;
import model.Employee;
import model.Hospital;
import model.Position;

/**
 *
 * @author Александр
 */
public class Main {

    public static void main(String[] args) {
        int num = -1;
        Scanner sc = new Scanner(System.in);
        controller.Controller ctr = new Controller();
        ClassSelector executor = null;
        LinkedList nowObject = ctr.nowObject;
        String numb = new String();
        while (num != 0) {
            if (nowObject.isEmpty()||ctr.getModel().hospitals == null) {
                executor = new ModelSelector();
            } else if (nowObject.getLast() instanceof Hospital) {
                executor = new HospitalSelector();
            } else if (nowObject.getLast() instanceof Department) {
                executor = new DepartmentSelector();
            } else if (nowObject.getLast() instanceof Employee) {
                executor = new EmployeeSelector();
            }
            if (!nowObject.isEmpty()) {
                System.out.println("Current object : " + nowObject.getLast());
            }
            executor.printMenu();
            System.out.println("    99: level up");
            System.out.println("    100: save model");
            System.out.println("    101: load model");
            System.out.println("    0: exit");
            while (true) {
                numb = sc.next();
                try {
                    num = Integer.parseInt(numb);
                    break;
                } catch (NumberFormatException ex) {
                    System.out.println("Error. Enter the integer value");
                    continue;
                }
            }
            executor.execute(num, ctr);
            switch (num) {
                case 99:
                    ctr.levelUp();
                    break;
                case 100:
                    System.out.println("Enter the path to file");
                    String path = sc.next();
                    ctr.saveModel(path);
                    break;
                case 101:
                    System.out.println("Enter the path to file");
                    String path1 = sc.next();
                    ctr.loadModel(path1);
                    break;
                case 0:
                    System.exit(0);
            }
//            } else if (model.nowObject instanceof Department) {
//            }
        }
    }
}
