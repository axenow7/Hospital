/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import java.util.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Александр
 */
public class Department {

    private long id;
    private String name;
//    private Hospital hospital;
    private Set<Employee> employees;
    private Map<Position, Integer> positions;

    public Department() {
    }

    @Override
    public String toString() {
        return "Department name = " + this.getName(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.name);
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
        final Department other = (Department) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    public Department(long id, String name, Hospital hospital) {
        this.id = id;
        this.name = name;
//        this.hospital = hospital;
    }

    public void addPosition(Position e) throws InputMismatchException {
        //предупреждене о создан новой должност
        if (positions == null) {
            positions = new HashMap();
            positions.put(e, e.getFreeCount());
        } else if (positions.containsKey(e)) {
            int value = positions.get(e);
            positions.remove(e);
            positions.put(e, e.getFreeCount() + value);
        } else {
            positions.put(e, e.getFreeCount());
        }
    }

    public void removeAllPositions(Position e) throws NoSuchFieldException {
        //pattern
        if (!positions.containsKey(e)) {
            throw new IllegalArgumentException("This department doesn't have position " + e);
        }
        positions.remove(e);
        for (Employee emp : employees) {
            Position p = emp.getPosition();
            if (p.equals(e)) {
                this.removeEmployee(emp);
            }
        }
    }

    public void removePosition(Position e) {
//        if (!positions.containsKey(e)) {
//            throw new IllegalArgumentException("This department doesn't have position " + e);
//        }
        if (e.getFreeCount() > 0) {
            int count = positions.get(e);
            if (count != 1) {
                positions.remove(e);
                e.setFreeCount(e.getFreeCount() - 1);
                positions.put(e, --count);
            } else {
                positions.remove(e);
            }
        } else {
            throw new ArrayIndexOutOfBoundsException("Department doesn't have free position " + e);
        }
    }

    public void addEmployee(Employee e, Position p) throws Exception {
        if (employees == null) {
            employees = new HashSet<Employee>();
        }
        for(Employee e1 : employees){
            if (e1.getName().equals(e.getName())){
                throw new Exception("Employee with name "+e.getName()+" already exists");
            }
        }
//        for (Employee e1 : employees) {
//            if (e1!=null&&e.getName().equals(e.getName())) {
//                throw new IllegalArgumentException("Данный сотрудник " + e + " уже числится в департаменте");
//            }
//        }

       // if (positions.entrySet().isEmpty()) throw new NullPointerException("No one position not found. Firstly, add a position");

        for (Position p1 : positions.keySet()) {
            System.out.println(p1.getName() + e.getName());
            if ( p1.getFreeCount() > 0) {
                employees.add(e);
                p1.setFreeCount(p1.getFreeCount() - 1);
                return;
            } else {
                throw new ArrayIndexOutOfBoundsException();
            }
        }
    }

    public void removeEmployee(Employee e) throws NoSuchFieldException {
        if (employees == null) {
            throw new NullPointerException("The department has no employees!");
        }
        if (!employees.contains(e)) {
            throw new NoSuchFieldException("The employee in this department is not found");
        }
        Integer count = positions.get(e.getPosition());
        positions.remove(e.getPosition());
        e.getPosition().setFreeCount(e.getPosition().getFreeCount() + 1);
        positions.put(e.getPosition(), count);
        employees.remove(e);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

//    @XmlTransient
//    public Hospital getHospital() {
//        return hospital;
//    }

    @XmlElementWrapper(name = "employees")
    @XmlElements({
        @XmlElement(name = "employee", type = Employee.class)
    })
    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setName(String name) {
        this.name = name;
    }
//
//    public void setHospital(Hospital hospital) {
//        this.hospital = hospital;
//    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    @XmlElementWrapper(name = "positions")
    @XmlElements({
        @XmlElement(name = "position", type = Position.class)
    })
    public Map<Position, Integer> getPositions() {
        return positions;
    }

    public void setPositions(Map<Position, Integer> positions) {
        this.positions = positions;
    }

}
