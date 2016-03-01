/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.controller;

import java.net.Socket;
import server.model.IDGenerator;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.model.Department;
import server.model.Employee;
import server.model.Hospital;
import server.model.Model;
import server.model.Patient;
import server.model.Position;

/**
 *
 * @author Александр
 */
public class Controller {

    public Controller() {
    }
    public Controller(Model m){
        setModel(m);
    }

    private String clientSocket = "";

    public String getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(String clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void checkOperation(String code, String delName) throws IllegalAccessError {
        System.out.println("Try to delete object with name=" + delName + " code:" + code);
        Map<Socket, LinkedList> clients = model.getClients();
        System.out.println(clients.keySet());
        System.out.println("Next clients are accepted to server:  ");
        for(Socket s: clients.keySet()){
            System.out.println(s.getLocalSocketAddress());
        }
        System.out.println("Clients work with next elements: ");
        System.out.println(clients.values());
        for (Socket s : clients.keySet()) {
            System.out.println("1 = "+ s.getLocalSocketAddress());
            System.out.println("2 = "+ clientSocket);
            if (s.getLocalSocketAddress().equals(clientSocket)) {
                clients.remove(s);
            }
        }
        switch (code) {
            case "hospital":
                Hospital h = model.getHospitalByName(delName);
                for (LinkedList s : clients.values()) {
                    if (s.contains(h)) {
                        throw new IllegalAccessError();
                    }
                }
                break;
            case "department":
//                Hospital h1 = (Hospital) nowObject.get(0);
                for (LinkedList s : clients.values()) {
                    Department d = (Department) s.get(1);
                    if (d.getName().equals(delName)) {
                        throw new IllegalAccessError();
                    }

                }
                break;
            case "employee":
                for (LinkedList s : clients.values()) {
                    Employee e = (Employee) s.get(2);
                    if (e.getName().equals(delName)) {
                        throw new IllegalAccessError();
                    }

                }
                break;
            case "position":
                break;
        }
//        for (Socket s : clients) {
//
//        }
    }
    public LinkedList nowObject = new LinkedList();
//    private static final IDGenerator gen = IDGenerator.getIDGeneretor();
    private static Model model = new Model();

    public LinkedList getNowObject() {
        return nowObject;
    }

    public void setModel(Model model) {
        this.model = model;
        nowObject.add(model.hospitals.toArray()[0]);
    }

    public void levelUp() {
//        nowObject = model.levelUp(nowObject);
        if (nowObject.size() != 1 && nowObject.size() != 0) {
            nowObject.removeLast();
//            nowObject.getLast();
        }
    }

    public void saveModel(String path) {
        model.saveModel(path);
    }

    public void loadModel(String path) {
        this.model = Model.loadModel(path);
        nowObject.add(model.hospitals.toArray()[0]);
    }

    public void addVisit(String name) throws NullPointerException {
        model.addVisit(name, nowObject);
    }

    public void setNowObject(LinkedList nowObject) {
        this.nowObject = nowObject;
    }

    public Model getModel() {
        return model;
    }

    public void chooseEmployee(String name) throws Exception {
        nowObject.add(model.chooseEmployee(name, nowObject));
    }

    public void chooseHospital(String name) throws NullPointerException {
        if (model.chooseHospital(name, nowObject) == null) {
            throw new NullPointerException();
        } else {
            nowObject.clear();
            nowObject.add(model.chooseHospital(name, nowObject));
        }
    }

    public void chooseDepartment(String name) throws NullPointerException {
        nowObject.add(model.chooseDepartment(name, nowObject));
    }

    public Set<Hospital> getHospitals() {
        return model.getHospitals();
    }

    public Set<Department> getDepartments() throws NullPointerException {
        if (model.getDepartments(nowObject) == null) {
            throw new NullPointerException();
        } else {
            return model.getDepartments(nowObject);
//        for (Department d : model.nowHospital.getDepartments()) {
//            System.out.println(d.toString());
//        }
        }
    }

    public Set<Position> getPositions() throws Exception {
        return model.getPositions(nowObject);
    }

    public Set<Employee> getEmployees() throws Exception {
        return model.getEmployees(nowObject);
    }

    public Set<Patient> getPatients() {
        return model.getPatients(nowObject);
    }

    public void addPatient(String name, String address, String passport) throws ArrayIndexOutOfBoundsException, NullPointerException {
        model.addPatient(name, address, passport, nowObject);
    }

    public void addHospital(String name, String INN, String date) throws Exception {
        Hospital h1;
//         in model
//        if (model.hospitals == null) {
//            model.hospitals = new HashSet<Hospital>();
//        }
//        check parametr
//        in model
//        genid in model
        if (nowObject.size() == 0) {
            nowObject.add(model.addHospital(name, INN, date));
        } else {
            nowObject.remove();
            nowObject.add(model.addHospital(name, INN, date));
        }
    }

    public void removeHospital(String name) throws ArrayIndexOutOfBoundsException, IllegalAccessError {
        checkOperation("hospital", name);
        nowObject = (LinkedList) model.removeHospital(name, nowObject);
    }

    public void addDepartment(String name) throws ArrayIndexOutOfBoundsException {
        nowObject.add(model.addDepartment(name, nowObject));
//        Department d = new Department(gen.getID(), name, model.nowHospital);
//        model.nowHospital.addDepartment(d);
//        model.nowDepartment = d;
    }

    /**
     *
     * @param name
     */
    public void removeDepartment(String name) throws Exception, IllegalAccessError {
        checkOperation("department", name);
        model.removeDepartment(name, nowObject);
    }

    public void addPosition(String name, int freeCount) throws Exception {
        model.addPosition(name, freeCount, nowObject);
    }

    public void removePosition(String name) throws Exception {
        checkOperation("position", name);
        model.removePosition(name, nowObject);
    }

    public void addEmployee(String name, double salary, String position) throws Exception {
        nowObject.add(model.addEmployee(name, salary, position, nowObject));
    }

    public void removeEmployee(String name) throws Exception, IllegalAccessError {
        checkOperation("employee", name);
        model.removeEmployee(name, nowObject);
    }
}
