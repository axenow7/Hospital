/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Александр
 */
@XmlRootElement(name = "Model")
public class Model {
    private Map<Socket, LinkedList> clients = new HashMap<>();

    public Map<Socket, LinkedList> getClients() {
        return clients;
    }

    public void setClients(Map<Socket, LinkedList> clients) {
        this.clients = clients;
    }
    public void addClient(Socket s, LinkedList nowObject){
        clients.put(s, nowObject);
    }
   
    //логка
    @XmlElementWrapper(name = "hospitals")
    @XmlElements({
        @XmlElement(name = "hospital", type = Hospital.class)
    })
    public Set<Hospital> hospitals = null;
    // to controleer in map
//    public Hospital nowHospital;
//    public Department nowDepartment;
    private IDGenerator gen = IDGenerator.getIDGeneretor();
//    public int 

    public Object addPatient(String name, String address, String passport, LinkedList nowObject) throws ArrayIndexOutOfBoundsException {
        if (name.length() > 50){
            throw new ArrayIndexOutOfBoundsException();
        } else {
            Hospital h = (Hospital) (nowObject).get(0);
            h.addPatient(name, address, passport, gen.getID());
            return nowObject.get(0);
        }
    }

    public Set<Patient> getPatients(LinkedList nowObject) {
        return ((Hospital) nowObject.get(0)).getPatients();
    }

    public Object addPosition(String name, int freeCount, LinkedList nowObject) throws Exception {
        Department d = (Department)nowObject.get(1);
        Position p = new Position(gen.getID(), name, d, freeCount);
        return d;
    }

    public Object removePosition(String name, LinkedList nowObject) throws Exception {
        Department d = (Department)(nowObject).get(1);
        d.removePosition(getPositionByName(name, d));
        return d;
    }

    public Object addEmployee(String name, double salary, String position, LinkedList nowObject) throws Exception {
        Department d = (Department)(nowObject).get(1);
        Employee e = new Employee(name, d, gen.getID(), getPositionByName(position, d), salary);
        return e;
    }

    public Object removeEmployee(String name, LinkedList nowObject) throws Exception {
        Department d = (Department)(nowObject).get(1);
        d.removeEmployee(getEmployeeByName(name, d));
        return d;
    }

    public Set<Employee> getEmployees(LinkedList nowObject) throws Exception {
        Department d = (Department) nowObject.get(1);
        return d.getEmployees();
    }

    public Employee getEmployeeByName(String name, Department nowObject) throws Exception {
        Department d = nowObject;
        for (Employee e : d.getEmployees()) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        return null;
    }

    public Position getPositionByName(String name, Department nowObject) throws Exception {
        Department d = nowObject;
        for (Position p : d.getPositions().keySet()) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        throw new Exception("Position with name: " + name + " not found");
    }

    public Set<Position> getPositions(LinkedList nowObject) throws Exception {
        Department d = (Department) nowObject.get(1);
        if (d == null) {
            throw new Exception("Can't get position");
        }
        return d.getPositions().keySet();
    }

    public Set<Hospital> getHospitals() {
        return hospitals;
    }

    public Set<Department> getDepartments(LinkedList nowObject) throws NullPointerException {
        Hospital h = (Hospital) nowObject.get(0);
        return h.getDepartments();
    }

    public Object addDepartment(String name, LinkedList nowObject) throws ArrayIndexOutOfBoundsException {
        if (name.length() > 70) {
            throw new ArrayIndexOutOfBoundsException();
        } else{
        Hospital h = (Hospital) (nowObject).get(0);
        Department d = new Department(gen.getID(), name, h);
        h.addDepartment(d);
        return d;
        }
    }

    public Patient getPatientByName(String name, LinkedList nowObject) throws ArrayIndexOutOfBoundsException, NullPointerException {
        Hospital h = (Hospital)nowObject.get(0);
        if (h.getPatients().size() > 0) {
            for (Patient p : h.getPatients()) {
                if (p.getName().equals(name)) {
                    return p;
                }
            }
        }
        throw new NullPointerException("Patient with name " + name + " not found");
    }

    public Object addVisit(String pName, LinkedList nowObject) throws ArrayIndexOutOfBoundsException, NullPointerException {
        Employee e = (Employee) nowObject.get(2);
        Patient p = getPatientByName(pName, nowObject);
//        Visit v = new Visit(gen.getID(), p, e);
        e.addVisit(gen.getID(), p);
//        nowObject = v;
        return nowObject.get(2);
    }

//    public Hospital getHospitalFromObject(Object nowObject) throws Exception {
//        Hospital now;
//        if (nowObject instanceof Hospital) {
//            now = (Hospital) nowObject;
//        } else if (nowObject instanceof Department) {
//            now = ((Department) nowObject).getHospital();
//        } else if (nowObject instanceof Position) {
//            now = ((Position) nowObject).getDepartment().getHospital();
//        } else if (nowObject instanceof Employee) {
//            now = ((Employee) nowObject).getDepartment().getHospital();
//        } else {
//            throw new Exception("Can't choose hospital, get up");
//        }
//        return now;
//    }

//    public Object levelUp(Object nowObject) {
//        Object now = nowObject;
//        if (nowObject instanceof Department) {
//            now = ((Department) nowObject).getHospital();
////        } else if (nowObject instanceof Position) {
////            now = ((Position) nowObject).getDepartment();
//        } else if (nowObject instanceof Employee) {
//            now = ((Employee) nowObject).getDepartment();
//        } else if (nowObject instanceof Visit) {
//            now = ((Visit) nowObject).getEmployee();
//        }
//        return now;
//    }

//    public Department getDepartmentFromObject(Object nowObject) throws Exception {
//        Department now;
//        if (nowObject instanceof Hospital) {
//            throw new Exception("Can't get Department");
//        } else if (nowObject instanceof Department) {
//            now = (Department) nowObject;
//        } else if (nowObject instanceof Position) {
//            now = ((Position) nowObject).getDepartment();
//        } else if (nowObject instanceof Employee) {
//            now = ((Employee) nowObject).getDepartment();
//        } else {
//            throw new Exception("Can't get Department");
//        }
//        return now;
//    }

    public Object removeDepartment(String name, LinkedList nowObject) throws Exception {
        Hospital now = (Hospital) nowObject.get(0);
        now.removeDepartment(now.getDepartmentByName(name));
        return now;
    }

    public Object addHospital(String name, String INN, String date) throws ParseException, IllegalArgumentException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date d = sdf.parse(date);
        Hospital h1 = new Hospital(gen.getID(), name, INN, d);
        if (hospitals == null) {
            hospitals = new HashSet<Hospital>();
        }
        if (hospitals.contains(h1)) {
//            System.err.println("This hospital already exists");
            throw new IllegalArgumentException("This hospital already exists ");
        } else {
            hospitals.add(h1);
            return h1;
        }
    }

    public Hospital getHospitalByName(String name) throws NullPointerException {
        for (Hospital h : hospitals) {
            if (h.getName().equals(name)) {
                return h;
            }
        }
        throw new NullPointerException();
    }

    public Object removeHospital(String name, LinkedList nowObject) throws ArrayIndexOutOfBoundsException {
        Hospital now = (Hospital) (nowObject).get(0);
        if (hospitals.size() == 1) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (now.equals(this.getHospitalByName(name))) {
            hospitals.remove(getHospitalByName(name));
            nowObject.clear();
             nowObject.add(hospitals.toArray()[0]);
        } else {
            hospitals.remove(getHospitalByName(name));
        }
        return nowObject;
    }

    public Object chooseEmployee(String name, LinkedList nowObject) throws Exception {
        Department d = (Department) nowObject.get(1);
        for (Employee e : d.getEmployees()) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        throw new Exception("Employee with name " + name + " not found in this department");
    }

    public Object chooseDepartment(String name, LinkedList nowObject) throws NullPointerException {
        Hospital now = (Hospital)(nowObject).get(0);

        if (now.getDepartmentByName(name) == null) {
            throw new NullPointerException();
        }
        return now.getDepartmentByName(name);
    }

    public Object chooseHospital(String name, LinkedList nowObject) throws NullPointerException {
//        if (nowObject instanceof Hospital) {
        return this.getHospitalByName(name);

//        } else {
//            throw new Exception("Can't choose hospital. Please, get up");
//        }
    }

    public void saveModel(String path) {
        HospitalToXML.jaxbObjectToXML(this, path);
    }

    public static Model loadModel(String path) {
        return HospitalToXML.jaxbXMLToObject(path);
    }
}
