/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.controller;

import server.model.IDGenerator;

import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
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

    public LinkedList nowObject = new LinkedList();
//    private static final IDGenerator gen = IDGenerator.getIDGeneretor();
    private static Model model = new Model();

    public LinkedList getNowObject() {
        return nowObject;
    }

    public void setModel(Model model) {
        this.model = model;
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
        this.model = model.loadModel(path);
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
        }else{
            nowObject.remove();
            nowObject.add(model.addHospital(name, INN, date));
        }
    }

    public void removeHospital(String name) throws ArrayIndexOutOfBoundsException {
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
    public void removeDepartment(String name) throws Exception {
        model.removeDepartment(name, nowObject);
    }

    public void addPosition(String name, int freeCount) throws Exception {
        model.addPosition(name, freeCount, nowObject);
    }

    public void removePosition(String name) throws Exception {
        model.removePosition(name, nowObject);
    }

    public void addEmployee(String name, double salary, String position) throws Exception {
        nowObject.add(model.addEmployee(name, salary, position, nowObject));
    }

    public void removeEmployee(String name) throws Exception {
        model.removeEmployee(name, nowObject);
    }
}
