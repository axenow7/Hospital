/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Александр
 */
public class Visit {
    private long id;

    public void setId(long id) {
        this.id = id;
    }
//    private Patient patient;
//    private Employee employee;
    public Visit(long id, Patient p, Employee e){
        this.id=id;
//        this.patient=p;
//        this.employee=e;
    }
//    public void setPatient(Patient patient) {
//        this.patient = patient;
//    }
//
//    public void setEmployee(Employee employee) {
//        this.employee = employee;
//    }

    public long getId() {
        return id;
    }
//    public Patient getPatient() {
//        return patient;
//    }
//    public Employee getEmployee() {
//        return employee;
//    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Visit other = (Visit) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Visit["  + id + "]" /*, patient=" + patient + ", employee=" + employee*/ ;
    }

    public Visit() {
    }
    
    
}
