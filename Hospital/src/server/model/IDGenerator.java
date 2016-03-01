/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

/**
 *
 * @author Александр
 */
public class IDGenerator {
    private static IDGenerator idgenerator;
    private static long id=0;
    public static IDGenerator getIDGeneretor(){
        return idgenerator==null?new IDGenerator() : idgenerator;
    }
    public long getID(){
        return ++id;
    }
}
