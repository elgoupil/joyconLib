/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joyconLib;

import java.util.HashMap;

/**
 *
 * @author renardn
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
