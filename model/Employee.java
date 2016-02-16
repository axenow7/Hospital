/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Александр
 */
public class Employee {

    private String name;
//    private Department department;
    private long id;
    private Position position;
    private double salary;
    private Set<Visit> visits;



    public void setPosition(Position position) {
        this.position = position;
    }
    @XmlElementWrapper(name = "visits")
    @XmlElements({
        @XmlElement(name = "visit", type = Visit.class)
    })
    public Set<Visit> getVisits() {
        return visits;
    }
    public void setVisits(Set<Visit> visits) {
        this.visits = visits;
    }

    public void addVisit(long id, Patient p) throws IllegalArgumentException, NullPointerException, ArrayIndexOutOfBoundsException {
        Visit v = new Visit(id, p, this);
        if (visits == null) {
            visits = new HashSet<Visit>();
        }
        if (visits.contains(v)) {
            throw new IllegalArgumentException("Impossible to add this visit " + v);
        }
        visits.add(v);
//        p.addVisit(v);
    }

    public Employee() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.name);
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
        final Employee other = (Employee) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Employee[" + this.getId() + "] name = " + this.getName() + ",  position = " + this.getPosition().getName(); //To change body of generated methods, choose Tools | Templates.
    }

    public Employee(String name, Department department, long id, Position position, double salary) throws Exception {
        this.name = name;
//        this.department = department;
        this.id = id;
        this.position = position;
        this.salary = salary;
        department.addEmployee(this, position);
    }

    public void setName(String name) {
        this.name = name;
    }

//    public void setDepartment(Department department, Position p) throws NoSuchFieldException, Exception {
//        this.department.removeEmployee(this);
//        Map<Position, Integer> oldPositions = this.department.getPositions();
//        int countOfThisPosition = oldPositions.get(this.getPosition());
//        this.department = department;
//        this.department.addEmployee(this, p);
//    }
    //TODO сделать активным  
//    @XmlTransient
//    public void setPosition(Position position) throws Exception {
//        Map<Position, Integer> positions = department.getPositions();
//        if (!positions.containsKey(position)) {
//            throw new ArrayIndexOutOfBoundsException("In this department" + department + " no position " + position);
//        } else {
//            if (position.getFreeCount() > 0) {
//                Integer oldValue = positions.get(this.position);
//                positions.remove(this.position);
//                this.position.setFreeCount(this.position.getFreeCount() + 1);
//                positions.put(this.position, oldValue);
//                Integer newValue = positions.get(position);
//                positions.remove(position);
//                position.setFreeCount(position.getFreeCount() - 1);
//                positions.put(position, newValue);
//                this.position = position;
//            } else {
//                throw new Exception("[Employee.setPosition] can't set position");
//            }
//        }
//    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

//    @XmlTransient
//    public Department getDepartment() {
//        return department;
//    }

    public long getId() {
        return id;
    }

    public Position getPosition() {
        return position;
    }

    public double getSalary() {
        return salary;
    }
}
