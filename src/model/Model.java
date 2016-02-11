/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
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

    public Object addPatient(String name, String address, String passport, Object nowObject) throws Exception {
        Hospital h = (Hospital) getHospitalFromObject(nowObject);
        h.addPatient(name, address, passport, gen.getID());
        return nowObject;
    }

    public Set<Patient> getPatients(Object nowObject) {
        return ((Hospital) nowObject).getPatients();
    }

    public Object addPosition(String name, int freeCount, Object nowObject) throws Exception {
        Department d = getDepartmentFromObject(nowObject);
        Position p = new Position(gen.getID(), name, d, freeCount);
        return d;
    }

    public Object removePosition(String name, Object nowObject) throws Exception {
        Department d = getDepartmentFromObject(nowObject);
        d.removePosition(getPositionByName(name, nowObject));
        return d;
    }

    public Object addEmployee(String name, double salary, String position, Object nowObject) throws Exception {
        Department d = getDepartmentFromObject(nowObject);
        Employee e = new Employee(name, d, gen.getID(), getPositionByName(name, nowObject), salary);
//        d.addEmployee(e, getPositionByName(name, nowObject));
        return e;
    }

    public Object removeEmployee(String name, Object nowObject) throws Exception {
        Department d = getDepartmentFromObject(nowObject);
        d.removeEmployee(getEmployeeByName(name, nowObject));
        return d;
    }

    public Set<Employee> getEmployees(Object nowObject) throws Exception {
        Department d = getDepartmentFromObject(nowObject);
        return d.getEmployees();
    }

    public Employee getEmployeeByName(String name, Object nowObject) throws Exception {
        Department d = getDepartmentFromObject(nowObject);
        for (Employee e : d.getEmployees()) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        return null;
    }

    public Position getPositionByName(String name, Object nowObject) throws Exception {
        Department d = getDepartmentFromObject(nowObject);
        for (Position p : d.getPositions().keySet()) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        throw new Exception("Position with name: " + name + " not found");
    }

    public Set<Position> getPositions(Object nowObject) throws Exception {
        Department d = null;
        if (nowObject instanceof Department) {
            d = (Department) nowObject;
        } else if (nowObject instanceof Position) {
            d = ((Position) nowObject).getDepartment();
        }
        if (d == null) {
            throw new Exception("Can't get position");
        }
        return d.getPositions().keySet();
    }

    public Set<Hospital> getHospitals() {
        return hospitals;
    }

    public Set<Department> getDepartments(Object nowObject) throws Exception {
        Hospital h = getHospitalFromObject(nowObject);
        return h.getDepartments();
    }

    public Object addDepartment(String name, Object nowObject) throws Exception {
        Hospital h = getHospitalFromObject(nowObject);
        Department d = new Department(gen.getID(), name, h);
        h.addDepartment(d);
        nowObject = d;
        return nowObject;
    }

    public Patient getPatientByName(String name, Object nowObject) throws Exception {
        Hospital h = getHospitalFromObject(nowObject);
        if (h.getPatients().size() > 0) {
            for (Patient p : h.getPatients()) {
                if (p.getName().equals(name)) {
                    return p;
                }
            }
        }
        throw new Exception("Patient with name " + name + " not found");
    }

    public Object addVisit(String pName, Object nowObject) throws Exception {
        Employee e = (Employee) nowObject;
        Patient p = getPatientByName(pName, nowObject);
//        Visit v = new Visit(gen.getID(), p, e);
        e.addVisit(gen.getID(), p);
//        nowObject = v;
        return nowObject;
    }

    public Hospital getHospitalFromObject(Object nowObject) throws Exception {
        Hospital now;
        if (nowObject instanceof Hospital) {
            now = (Hospital) nowObject;
        } else if (nowObject instanceof Department) {
            now = ((Department) nowObject).getHospital();
        } else if (nowObject instanceof Position) {
            now = ((Position) nowObject).getDepartment().getHospital();
        } else if (nowObject instanceof Employee) {
            now = ((Employee) nowObject).getDepartment().getHospital();
        } else {
            throw new Exception("Can't choose hospital, get up");
        }
        return now;
    }

    public Object levelUp(Object nowObject) {
        Object now = nowObject;
        if (nowObject instanceof Department) {
            now = ((Department) nowObject).getHospital();
//        } else if (nowObject instanceof Position) {
//            now = ((Position) nowObject).getDepartment();
        } else if (nowObject instanceof Employee) {
            now = ((Employee) nowObject).getDepartment();
        } else if (nowObject instanceof Visit) {
            now = ((Visit) nowObject).getEmployee();
        }
        return now;
    }

    public Department getDepartmentFromObject(Object nowObject) throws Exception {
        Department now;
        if (nowObject instanceof Hospital) {
            throw new Exception("Can't get Department");
        } else if (nowObject instanceof Department) {
            now = (Department) nowObject;
        } else if (nowObject instanceof Position) {
            now = ((Position) nowObject).getDepartment();
        } else if (nowObject instanceof Employee) {
            now = ((Employee) nowObject).getDepartment();
        } else {
            throw new Exception("Can't get Department");
        }
        return now;
    }

    public Object removeDepartment(String name, Object nowObject) throws Exception {
        Hospital now = getHospitalFromObject(nowObject);
        if (nowObject.equals(now.getDepartmentByName(name))) {
            now.removeDepartment(now.getDepartmentByName(name));
            nowObject = now.getDepartments().toArray()[0];
        } else {
            now.removeDepartment(now.getDepartmentByName(name));
        }
        return nowObject;
    }

    public Object addHospital(String name, String INN, String date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date d = sdf.parse(date);
        Hospital h1 = new Hospital(gen.getID(), name, INN, d);
        if (hospitals == null) {
            hospitals = new HashSet<Hospital>();
        }
        if (hospitals.contains(h1)) {
//            System.err.println("This hospital already exists");
            throw new Exception("This hospital already exists");
        } else {
            hospitals.add(h1);
            Object nowObject = h1;
            return nowObject;
        }
    }

    public Hospital getHospitalByName(String name) throws Exception {
        for (Hospital h : hospitals) {
            if (h.getName().equals(name)) {
                return h;
            }
        }
        throw new Exception("Hospital with name: " + name + " not exists");
    }

    public Object removeHospital(String name, Object nowObject) throws Exception {
        if (hospitals.size() == 1) {
            throw new Exception("Can't delete last hospital");
        }
        if (nowObject.equals(this.getHospitalByName(name))) {
            hospitals.remove(getHospitalByName(name));
            nowObject = (Hospital) hospitals.toArray()[0];
            System.out.println("Hospital has been removed succesful");
        } else {
            hospitals.remove(getHospitalByName(name));
            System.out.println("Hospital has been removed succesful");
        }
        return nowObject;
    }

    public Object chooseEmployee(String name, Object nowObject) throws Exception {
        Department d = (Department) nowObject;
        for (Employee e : d.getEmployees()) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        throw new Exception("Employee with name " + name + " not found in this department");
    }

    public Object chooseDepartment(String name, Object nowObject) throws Exception {
        Hospital now = getHospitalFromObject(nowObject);

        if (now.getDepartmentByName(name) == null) {
            throw new Exception("Department with name: " + name + " not found");
        }
        nowObject = now.getDepartmentByName(name);
        return nowObject;
    }

    public Object chooseHospital(String name, Object nowObject) throws Exception {
//        if (nowObject instanceof Hospital) {
        nowObject = this.getHospitalByName(name);
        return nowObject;

//        } else {
//            throw new Exception("Can't choose hospital. Please, get up");
//        }
    }

    public void saveModel(String path) {
        HospitalToXML.jaxbObjectToXML(this, path);
    }

    public Model loadModel(String path) {
        return HospitalToXML.jaxbXMLToObject(path);
    }
}
