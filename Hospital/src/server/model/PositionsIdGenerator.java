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
public class PositionsIdGenerator {
    static int id = 0;
    private PositionsIdGenerator(){};
    public static int getID(){
        return ++id;
    }
}
