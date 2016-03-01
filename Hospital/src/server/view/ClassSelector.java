/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author Александр
 */
public interface ClassSelector {
//    DataOutputStream out= null;
//    DataInputStream sc = null;
    public void printMenu() throws IOException;
    public void execute(int operation, server.controller.Controller ctr) throws IOException;
}
