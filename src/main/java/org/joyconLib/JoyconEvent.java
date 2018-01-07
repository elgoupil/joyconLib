/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joyconLib;

import java.util.HashMap;

/**
 * <b>The object that will be sent when an input is triggered</b>
 * <p>
 * <b>newInputs</b> contains the new inputs with this format:
 * <b>NAME_OF_THE_INPUT</b>:<b>STATE_OF_THE_INPUT</b> (true/false)</p>
 *
 * <p>
 * <b>joystick</b> contains the value of the joystick (0 - 8)</p>
 *
 * @version 1.0
 * @author goupil
 */
public class JoyconEvent {

    private HashMap<String, Boolean> newInputs;
    private byte joystick;

    public JoyconEvent(HashMap<String, Boolean> newInputs, byte joystick) {
        this.newInputs = newInputs;
        this.joystick = joystick;
    }

    public HashMap<String, Boolean> getNewInputs() {
        return newInputs;
    }

    public byte getJoystick() {
        return joystick;
    }

}
