/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joyconLib;

import java.util.HashMap;

/**
 * <b>The traductor for the left joycon</b>
 * <p>
 * This class will translate the raw value of the joycon</p>
 *
 * @version 1.0
 * @author goupil
 */
public class LeftTraductor {

    private int inputValueByte0;
    private int inputValueByte1;
    private HashMap<String, Boolean> inputs;
    private byte joystick;

    public LeftTraductor() {
        inputValueByte0 = 0;
        inputValueByte1 = 0;
        joystick = 0;
        inputs = new HashMap<>();
    }

    public void translate(byte[] data) {
        inputs.clear();
        int inputByte0 = data[0] - inputValueByte0;
        inputValueByte0 = data[0];
        int inputByte1 = data[1] - inputValueByte1;
        inputValueByte1 = data[1];
        switch (data[2]) {
            case 8:
                joystick = 0;
                break;
            case 0:
                joystick = 1;
                break;
            case 1:
                joystick = 2;
                break;
            case 2:
                joystick = 3;
                break;
            case 3:
                joystick = 4;
                break;
            case 4:
                joystick = 5;
                break;
            case 5:
                joystick = 6;
                break;
            case 6:
                joystick = 7;
                break;
            case 7:
                joystick = 8;
                break;
        }
        switch (inputByte0) {
            case JoyconConstant.LEFT_ON:
                inputs.put(JoyconConstant.LEFT, true);
                break;
            case JoyconConstant.LEFT_OFF:
                inputs.put(JoyconConstant.LEFT, false);
                break;
            case JoyconConstant.DOWN_ON:
                inputs.put(JoyconConstant.DOWN, true);
                break;
            case JoyconConstant.DOWN_OFF:
                inputs.put(JoyconConstant.DOWN, false);
                break;
            case JoyconConstant.UP_ON:
                inputs.put(JoyconConstant.UP, true);
                break;
            case JoyconConstant.UP_OFF:
                inputs.put(JoyconConstant.UP, false);
                break;
            case JoyconConstant.RIGHT_ON:
                inputs.put(JoyconConstant.RIGHT, true);
                break;
            case JoyconConstant.RIGHT_OFF:
                inputs.put(JoyconConstant.RIGHT, false);
                break;
            case JoyconConstant.SL_ON:
                inputs.put(JoyconConstant.SL, true);
                break;
            case JoyconConstant.SL_OFF:
                inputs.put(JoyconConstant.SL, false);
                break;
            case JoyconConstant.SR_ON:
                inputs.put(JoyconConstant.SR, true);
                break;
            case JoyconConstant.SR_OFF:
                inputs.put(JoyconConstant.SR, false);
                break;
        }
        switch (inputByte1) {
            case JoyconConstant.MINUS_ON:
                inputs.put(JoyconConstant.MINUS, true);
                break;
            case JoyconConstant.MINUS_OFF:
                inputs.put(JoyconConstant.MINUS, false);
                break;
            case JoyconConstant.L_CLICKJOY_ON:
                inputs.put(JoyconConstant.L_CLICKJOY, true);
                break;
            case JoyconConstant.L_CLICKJOY_OFF:
                inputs.put(JoyconConstant.L_CLICKJOY, false);
                break;
            case JoyconConstant.CAPTURE_ON:
                inputs.put(JoyconConstant.CAPTURE, true);
                break;
            case JoyconConstant.CAPTURE_OFF:
                inputs.put(JoyconConstant.CAPTURE, false);
                break;
            case JoyconConstant.L_ON:
                inputs.put(JoyconConstant.L, true);
                break;
            case JoyconConstant.L_OFF:
                inputs.put(JoyconConstant.L, false);
                break;
            case JoyconConstant.ZL_ON:
                inputs.put(JoyconConstant.ZL, true);
                break;
            case JoyconConstant.ZL_OFF:
                inputs.put(JoyconConstant.ZL, false);
                break;
        }
    }

    public HashMap<String, Boolean> getInputs() {
        return inputs;
    }

    public byte getJoystick() {
        return joystick;
    }

}
