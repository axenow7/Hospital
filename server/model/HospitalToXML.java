/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Александр
 */
public class HospitalToXML {

    private static final String FILE_NAME = "C:\\Igri\\jaxb-hospital.xml";

//    public static void main(String[] args) {
//        Model m = new Model();
//        Hospital h = new Hospital(1, "name123", "4564123", new Date(System.currentTimeMillis()));
//        Department dep = new Department(2, "department123", h);
//        Position p = new Position(20, "PosName", dep, 3);
//        Employee e = new Employee("Rab", dep, 21, p, 3700.6);
////        dep.addPosition();
//        h.addDepartment(dep);
//        m.hospitals = new HashSet<Hospital>();
//        m.hospitals.add(h);
////        jaxbObjectToXML(h);
//        jaxbObjectToXML(m, FILE_NAME);
//
////        Hospital hospFromFile = jaxbXMLToObject();
//        Model modelFromFile = jaxbXMLToObject(FILE_NAME);
//        System.out.println(modelFromFile.toString());
//        Hospital h1 = (Hospital) m.getHospitals().toArray()[0];
//        Department d = (Department) h1.getDepartments().toArray()[0];
//        System.out.println(d.getName());
//        Position p1 = (Position) d.getPositions().keySet().toArray()[0];
//        System.out.println(p1.getName());
//        Employee e1 = (Employee) d.getEmployees().toArray()[0];
//        System.out.println(e1.toString());
//    }

    public static Model jaxbXMLToObject(String FILE_NAME) {
        try {
            JAXBContext context = JAXBContext.newInstance(Model.class);
            Unmarshaller un = context.createUnmarshaller();
            Model m = (Model) un.unmarshal(new File(FILE_NAME));
            return m;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void jaxbObjectToXML(Model model, String FILE_NAME) {

        try {
            JAXBContext context = JAXBContext.newInstance(Model.class);
            Marshaller m = context.createMarshaller();
            //for pretty-print XML in JAXB
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // Write to System.out for debugging
//             m.marshal(model, System.out);
            // Write to File
            File f = new File(FILE_NAME);
            f.createNewFile();
            m.marshal(model, f);
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(HospitalToXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
