/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Александр
 */
public class AllPostions {

    private Map<Integer, Position> positions;
    private Map<Integer, Integer> count;

    public void addPosition(Position e) {
        if (positions == null) {
            positions = new HashMap<Integer, Position>();
            int id = PositionsIdGenerator.getID();
            positions.put(id, e);
            count = new HashMap<Integer, Integer>();
            count.put(id, 1);
        } else if (positions != null && positions.containsValue(e)) {
            Integer key = new Integer(-1);
            Collection<Integer> c = positions.keySet();
            for (Integer key1 : c) {
                Position value = positions.get(key1);
                if (value.equals(e)) {
                    key = key1;
                }
            }
            Integer newValue = count.get(key);
            count.remove(key);
            count.put(key, newValue);
        } else {
            int id = PositionsIdGenerator.getID();
            positions.put(id, e);
            count.put(id, 1);
        }
        
    }
}
