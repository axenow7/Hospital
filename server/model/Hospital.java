/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;

/**
 *
 * @author Александр
 */
//@XmlRootElement(name = "Hospital")
//@XmlType(propOrder = {"id", "name", "dateCreate", "INN", "departments", "address"})
public class Hospital {

//    @XmlElement
    private long id;
    private String name;
    private Date dateCreate;
//    @XmlElement
    private String INN;
    private Set<Department> departments;
    private String address;
    private Map<Position, Integer> positions;
    private Set<Patient> patients;

    public void setPatients(Set<Patient> patients) {
        this.patients = patients;
    }

    @XmlElementWrapper(name = "patients")
    @XmlElements({
        @XmlElement(name = "Patient", type = Patient.class)
    })
    public Set<Patient> getPatients() {
        return patients;
    }

    public void addPatient(String name, String address, String passport, long id) {
        Patient p = new Patient(id, name, address, passport);
        if (patients == null) {
            patients = new HashSet<Patient>();
        }
        if (patients.contains(p)) {
            throw new IllegalArgumentException("The patient " + p + " is already in the hospital");
        }
        patients.add(p);
    }

    public Hospital() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public String toString() {
        return "Hospital[" + this.getId() + "], name = " + this.getName() + " date= "+ this.dateCreate; //To change body of generated methods, choose Tools | Templates.
    }

    public Department getDepartmentByName(String name) {
        for (Department d : departments) {
            if (d.getName().equals(name)) {
                return d;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Hospital other = (Hospital) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
//    public void addPosition(Position e){
//        if(positions==null){
//            positions = new HashMap();
//            positions.put(e, 1);
//        }else
//        if(positions.containsKey(e)){
//            int value = positions.get(e);
//            positions.remove(e);
//            positions.put(e, ++value);
//        }else{
//            positions.put(e, 1);
//        }
//    }
//    public void removeAllPositions(Position e){
//        if(!positions.containsKey(e)){
//            throw new IllegalArgumentException("Hospital doesn't have position "+e);
//        }
//        positions.remove(e);
//    }
//    public void removePosition(Position e){
//        if(!positions.containsKey(e)){
//            throw new IllegalArgumentException("Hospital doesn't have position "+e);
//        }
//        int count = positions.get(e);
//        if(count!=1){
//            positions.remove(e);
//            positions.put(e, --count);
//        }else{
//            positions.remove(e);
//        }
//    }

    public Hospital(long id, String name, String INN, Date dateCreate) {
        this.id = id;
        this.name = name;
        this.INN = INN;
        this.dateCreate = dateCreate;
    }

    public void addDepartment(Department d) {
        if (departments == null) {
            departments = new HashSet<Department>();
        }
        if (departments.contains(d)) {
            throw new IllegalArgumentException("The department " + d + " is already in the hospital");
        }
        departments.add(d);
    }

    public void removeDepartment(Department d) throws NullPointerException,NoSuchFieldException {
        if (departments == null) {
            throw new NullPointerException();
        }
        if (!departments.contains(d)) {
            throw new NoSuchFieldException("The department was not found");
        }
        departments.remove(d);
    }

    @XmlElementWrapper(name = "departments")
    @XmlElements({
        @XmlElement(name = "department", type = Department.class)
    })
    public Set<Department> getDepartments() {
        return departments;
    }

//    @XmlElement(name = "department")
//    @XmlElementWrapper
    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }

    public long getId() {
        return id;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public String getName() {
        return name;
    }

    public String getINN() {
        return INN;
    }

    public String getAddress() {
        return address;
    }

//    @XmlElement
    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

//    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

//    @XmlElement
    public void setAddress(String address) {
        this.address = address;
    }

}
