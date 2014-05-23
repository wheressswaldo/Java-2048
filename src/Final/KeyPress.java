/*
 * Kong Huang
 * CS1213
 * 2048 in swing
 * This file contains all the keyevents which are mapped to their respective functions in Grid.
 */

package Final;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_UP;
import static java.awt.event.KeyEvent.VK_R;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class KeyPress extends KeyAdapter{

	// hashmap containing keys and methods
    private static final HashMap<Integer, Method> keyMapping = new HashMap<>();
    // keys
    private static Integer[] KEYS = { VK_UP, VK_DOWN, VK_LEFT, VK_RIGHT, VK_R };
    // methods from grid.java
    private static String[] methodName = { "up", "down", "left", "right", "initializeTiles" };
    private static Grid board;
    private static final KeyPress INSTANCE = new KeyPress();
    
    private KeyPress() {
        initKey(KEYS);
    }
    
    public static KeyPress getKeyPress(Grid g) {
        board = g;
        return INSTANCE;
    }

    // initialize the keys
    void initKey(Integer[] kcs) {
        for (int i = 0; i < kcs.length; i++) {
            try {
            	// get method and put into keymapping
                keyMapping.put(kcs[i], Grid.class.getMethod(methodName[i]));
            } catch (NoSuchMethodException | SecurityException e) {
            	// catching exception
                e.printStackTrace();
            }
        }
    }

    // on key press, do things
    public void keyPressed(KeyEvent k) {
        super.keyPressed(k);
        Method action = keyMapping.get(k.getKeyCode());
        if (action == null) {
            System.gc();
            return;
        }
        try {
            action.invoke(board);
            board.repaint();
        } catch (InvocationTargetException | IllegalAccessException
                | IllegalArgumentException e) {
            e.printStackTrace();
        }
        // if you can't move, lose the game :(
        if (!board.checkIfCanMove()) {
            board.host.lose();
        }

    }

}
