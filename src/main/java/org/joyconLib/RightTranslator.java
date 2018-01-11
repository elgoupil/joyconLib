/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joyconLib;

import java.util.HashMap;

/**
 * <b>The translator for the right joycon</b>
 * <p>
 * This class will translate the raw value of the joycon</p>
 *
 * @version 1.0
 * @author goupil
 */
public class RightTranslator {

    private int lastShared;
    private int lastRight;
    private HashMap<String, Boolean> inputs;
    private HashMap<String, Boolean> oldInputs;
    private float horizontal;
    private float vertical;
    private byte battery;
    private JoyconStickCalc calculator;
    private int[] stick_cal_x_r;
    private int[] stick_cal_y_r;

    public RightTranslator(JoyconStickCalc calculator, int[] stick_cal_x_r, int[] stick_cal_y_r) {
        lastShared = 0;
        lastRight = 0;
        battery = 0;
        horizontal = 0f;
        vertical = 0f;
        inputs = new HashMap<>();
        this.calculator = calculator;
        this.stick_cal_x_r = stick_cal_x_r;
        this.stick_cal_y_r = stick_cal_y_r;
    }

    public void translate(byte[] data) {
        //Clearing the inputs
        inputs.clear();

        int[] temp = new int[12];
        for (int i = 5; i < 12; i++) {
            byte b = data[i];
            if (b < 0) {
                temp[i] = b + 256;
            } else {
                temp[i] = b;
            }
        }
        int x = temp[8] | ((temp[9] & 0xF) << 8);
        int y = (temp[9] >> 4) | (temp[10] << 4);
        calculator.analogStickCalc(x, y, stick_cal_x_r, stick_cal_y_r);

        horizontal = calculator.getHorizontal();
        vertical = calculator.getVertical();

        //Getting input change
        int shared = data[3];
        int right = data[2];
        if (data[3] < 0) {
            shared = data[3] + 256;
        }
        if (data[2] < 0) {
            right = data[2] + 256;
        }
        int sharedByte = shared - lastShared;
        lastShared = shared;
        int rightByte = right - lastRight;
        lastRight = right;

        //Battery translation
        int batteryInt = data[1];
        if (data[1] < 0) {
            batteryInt = data[1] + 256;
        }
        battery = Byte.parseByte(Integer.toHexString(batteryInt).substring(0, 1));

        //Inputs translation
        switch (sharedByte) {
            case JoyconConstant.PLUS_ON:
                inputs.put(JoyconConstant.PLUS, true);
                break;
            case JoyconConstant.PLUS_OFF:
                inputs.put(JoyconConstant.PLUS, false);
                break;
            case JoyconConstant.RIGHT_STICK_ON:
                inputs.put(JoyconConstant.RIGHT_STICK, true);
                break;
            case JoyconConstant.RIGHT_STICK_OFF:
                inputs.put(JoyconConstant.RIGHT_STICK, false);
                break;
            case JoyconConstant.HOME_ON:
                inputs.put(JoyconConstant.HOME, true);
                break;
            case JoyconConstant.HOME_OFF:
                inputs.put(JoyconConstant.HOME, false);
                break;
        }
        switch (rightByte) {
            case JoyconConstant.Y_ON:
                inputs.put(JoyconConstant.Y, true);
                break;
            case JoyconConstant.Y_OFF:
                inputs.put(JoyconConstant.Y, false);
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
            case JoyconConstant.A_ON:
                inputs.put(JoyconConstant.A, true);
                break;
            case JoyconConstant.A_OFF:
                inputs.put(JoyconConstant.A, false);
                break;
            case JoyconConstant.SR_ON:
                inputs.put(JoyconConstant.SR, true);
                break;
            case JoyconConstant.SR_OFF:
                inputs.put(JoyconConstant.SR, false);
                break;
            case JoyconConstant.SL_ON:
                inputs.put(JoyconConstant.SL, true);
                break;
            case JoyconConstant.SL_OFF:
                inputs.put(JoyconConstant.SL, false);
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
        //Clearing inputs if the same
        if (inputs.equals(oldInputs)) {
            oldInputs = inputs;
            inputs.clear();
        }
    }

    public HashMap<String, Boolean> getInputs() {
        return inputs;
    }

    public byte getBattery() {
        return battery;
    }

    public float getHorizontal() {
        return horizontal;
    }

    public float getVertical() {
        return vertical;
    }

}
