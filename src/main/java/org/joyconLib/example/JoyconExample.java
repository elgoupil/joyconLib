/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joyconLib.example;

import java.util.Map;
import org.joyconLib.Joycon;
import org.joyconLib.JoyconConstant;
import org.joyconLib.JoyconEvent;
import org.joyconLib.JoyconListener;

/**
 * <b>The example of the JoyconLib</b>
 * <p>
 * This example will try to connect to the left joycon and print on the console
 * the inputs that are triggered</p>
 *
 * @version 1.0
 * @author goupil
 */
public class JoyconExample {

    /**
     * <b>The example of the JoyconLib</b>
     * <p>
     * This example will try to connect to the left joycon and print on the
     * console the inputs that are triggered</p>
     * 
     * <p>Comment are in the method to learn how it work</p>
     */
    public void example() {
        //Create a new Joycon with the identifier of the left joycon
        Joycon joycon = new Joycon(JoyconConstant.JOYCON_LEFT);
        //Set the listener for the Joycon and create a new Listener on the go
        joycon.setListener(new JoyconListener() {
            //Override the method to do what you want with the inputs
            @Override
            public void handleNewInput(JoyconEvent je) {
                //Navigate in the inputs map
                for (Map.Entry<String, Boolean> entry : je.getNewInputs().entrySet()) {
                    //Print to the console the name of the button and his state
                    System.out.print("Button: " + entry.getKey() + " is " + (entry.getValue() ? "ON \t" : "OFF\t"));
                    //If the button is the capture button it will stop the progam
                    if (entry.getKey().equals(JoyconConstant.CAPTURE)) {
                        System.exit(0);
                    //If the button is the minus button it will close the connection with the joycon
                    } else if (entry.getKey().equals(JoyconConstant.MINUS)) {
                        joycon.close();
                    }
                }
                //Print to the console the position of the joystick
                System.out.println("Joystick\tX: " + je.getHorizontal() + "\tY: " + je.getVertical());
            }
        });
    }
}
