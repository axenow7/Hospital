/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Александр
 */
public class Patient {

    private long id;

    private String name;
    private String address;
    private String passport;
//    private Set<Visit> visits;
//    private Hospital hospital;
    public void setId(long id) {
        this.id = id;
    }

    public Patient(long id, String name, String address, String passport) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.passport = passport;
//        this.hospital = hospital;
    }

//    @XmlTransient
//    public Hospital getHospital() {
//        return hospital;
//    }
//
//    public void setHospital(Hospital hospital) {
//        this.hospital = hospital;
//    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
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
        final Patient other = (Patient) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
//        if (!Objects.equals(this.address, other.address)) {
//            return false;
//        }
//        if (!Objects.equals(this.passport, other.passport)) {
//            return false;
//        }
        return true;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPassport() {
        return passport;
    }

//    @XmlElementWrapper(name = "visits")
//    @XmlElements({
//        @XmlElement(name = "visit", type = Visit.class)
//    })
//    public Set<Visit> getVisits() {
//        return visits;
//    }
//    public void addVisit(Visit v){
//        if(visits==null){
//            visits = new HashSet<Visit>();
//        }
//        visits.add(v);
//    }

    public Patient() {
    }

    @Override
    public String toString() {
        return "Patient["+this.id+"] name= " + this.name; //To change body of generated methods, choose Tools | Templates.
    }
    
}
