/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Александр
 */
public class Position {

    private int freeCount;
    private String name;
    private Set<Employee> employees;
    private long id;
    private Department department;

    public Position() {
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.name);
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
        final Position other = (Position) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Position["+this.getId()+"] name = "+this.getName() + "count = "+this.freeCount; //To change body of generated methods, choose Tools | Templates.
    }

    public Position(long id, String name, Department department, int freeCount) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.freeCount = freeCount;
        department.addPosition(this);
    }

//    public void addEmployee(Employee e) {
//        if (employees == null) {
//            employees = new HashSet<Employee>();
//        }
//        if (employees.contains(e)) {
//            throw new IllegalArgumentException("Данный сотрудник " + e + " уже числится на данной должности" + this);
//        }
//        if (freeCount > 0) {
//            employees.add(e);
//            freeCount--;
//        }else{
//            throw new ArrayIndexOutOfBoundsException("Свободных должностей "+this+" нет");
//        }
//    }

//    public void removeEmployee(Employee e) throws NoSuchFieldException {
//        if (employees == null) {
//            throw new NullPointerException("В департаменте нет ни одного сотрудника!");
//        }
//        if (!employees.contains(e)) {
//            throw new NoSuchFieldException("Данного сотрудника в департаменте не было найдено");
//        }
//        employees.remove(e);
//    }

    public void setFreeCount(int freeCount) {
        this.freeCount = freeCount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFreeCount() {
        return freeCount;
    }

    public String getName() {
        return name;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public long getId() {
        return id;
    }
    @XmlTransient
    public Department getDepartment() {
        return department;
    }

}
