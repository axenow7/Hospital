/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.text.ParseException;
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
                        System.out.println(ex);
                        System.out.println("Ошибка ввода даты. Введите дату в формате \"dd-mm-yyyy\". Для отмены нажмите 1");
                        date = sc.next();
                        if (date.equals("1")){
                            System.out.println("Добавление отменено");
                            break;
                        }
                        
                        continue;
                    }
                    catch (Exception ex) {
                        System.out.println(ex);
                        System.out.println("Ошибка при создании госпиталя. Дя повторной попытки нажмите 1, в противном случае нажмите любую другую клавишу ");
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
