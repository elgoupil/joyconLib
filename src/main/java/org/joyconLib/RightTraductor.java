/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joyconLib;

import java.util.HashMap;

/**
 * <b>The traductor for the right joycon</b>
 * <p>
 * This class will translate the raw value of the joycon</p>
 *
 * @version 1.0
 * @author goupil
 */
public class RightTraductor {

    private int inputValueByte0;
    private int inputValueByte1;
    private HashMap<String, Boolean> inputs;
    private byte joystick;

    public RightTraductor() {
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
                joystick = 5;
                break;
            case 1:
                joystick = 6;
                break;
            case 2:
                joystick = 7;
                break;
            case 3:
                joystick = 8;
                break;
            case 4:
                joystick = 1;
                break;
            case 5:
                joystick = 2;
                break;
            case 6:
                joystick = 3;
                break;
            case 7:
                joystick = 4;
                break;
        }
        switch (inputByte0) {
            case JoyconConstant.A_ON:
                inputs.put(JoyconConstant.A, true);
                break;
            case JoyconConstant.A_OFF:
                inputs.put(JoyconConstant.A, false);
                break;
            case JoyconConstant.X_ON:
                inputs.put(JoyconConstant.X, true);
                break;
            case JoyconConstant.X_OFF:
                inputs.put(JoyconConstant.X, false);
                break;
            case JoyconConstant.B_ON:
                inputs.put(JoyconConstant.B, true);
                break;
            case JoyconConstant.B_OFF:
                inputs.put(JoyconConstant.B, false);
                break;
            case JoyconConstant.Y_ON:
                inputs.put(JoyconConstant.Y, true);
                break;
            case JoyconConstant.Y_OFF:
                inputs.put(JoyconConstant.Y, false);
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
            case JoyconConstant.PLUS_ON:
                inputs.put(JoyconConstant.PLUS, true);
                break;
            case JoyconConstant.PLUS_OFF:
                inputs.put(JoyconConstant.PLUS, false);
                break;
            case JoyconConstant.R_CLICKJOY_ON:
                inputs.put(JoyconConstant.R_CLICKJOY, true);
                break;
            case JoyconConstant.R_CLICKJOY_OFF:
                inputs.put(JoyconConstant.R_CLICKJOY, false);
                break;
            case JoyconConstant.HOME_ON:
                inputs.put(JoyconConstant.HOME, true);
                break;
            case JoyconConstant.HOME_OFF:
                inputs.put(JoyconConstant.HOME, false);
                break;
            case JoyconConstant.R_ON:
                inputs.put(JoyconConstant.R, true);
                break;
            case JoyconConstant.R_OFF:
                inputs.put(JoyconConstant.R, false);
                break;
            case JoyconConstant.ZR_ON:
                inputs.put(JoyconConstant.ZR, true);
                break;
            case JoyconConstant.ZR_OFF:
                inputs.put(JoyconConstant.ZR, false);
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
