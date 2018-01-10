/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joyconLib;

import java.util.HashMap;

/**
 * <b>The translator for the left joycon</b>
 * <p>
 * This class will translate the raw value of the joycon</p>
 *
 * @version 1.0
 * @author goupil
 */
public class LeftTranslator {

    private int lastShared;
    private int lastLeft;
    private HashMap<String, Boolean> inputs;
    private HashMap<String, Boolean> oldInputs;
    private float horizontal;
    private float vertical;
    private byte battery;
    private JoyconStickCalc calculator;
    private int[] stick_cal_x_l;
    private int[] stick_cal_y_l;

    public LeftTranslator(JoyconStickCalc calculator, int[] stick_cal_x_l, int[] stick_cal_y_l) {
        lastShared = 0;
        lastLeft = 0;
        battery = 0;
        horizontal = 0f;
        vertical = 0f;
        inputs = new HashMap<>();
        this.calculator = calculator;
        this.stick_cal_x_l = stick_cal_x_l;
        this.stick_cal_y_l = stick_cal_y_l;
    }

    public void translate(byte[] data) {
        //Clearing the inputs
        inputs.clear();

        int[] temp = new int[8];
        for (int i = 5; i < 8; i++) {
            byte b = data[i];
            if (b < 0) {
                temp[i] = b + 256;
            } else {
                temp[i] = b;
            }
        }
        int x = temp[5] | ((temp[6] & 0xF) << 8);
        int y = (temp[6] >> 4) | (temp[7] << 4);
        calculator.analogStickCalc(x, y, stick_cal_x_l, stick_cal_y_l);

        horizontal = calculator.getHorizontal();
        vertical = calculator.getVertical();

        //Getting input change
        int shared = data[3];
        int left = data[4];
        if (data[3] < 0) {
            shared = data[3] + 256;
        }
        if (data[4] < 0) {
            left = data[4] + 256;
        }
        int sharedByte = shared - lastShared;
        lastShared = shared;
        int leftByte = left - lastLeft;
        lastLeft = left;

        //Battery translation
        int batteryInt = data[1];
        if (data[1] < 0) {
            batteryInt = data[1] + 256;
        }
        battery = Byte.parseByte(Integer.toHexString(batteryInt).substring(0, 1));

        //Inputs translation
        switch (sharedByte) {
            case JoyconConstant.MINUS_ON:
                inputs.put(JoyconConstant.MINUS, true);
                break;
            case JoyconConstant.MINUS_OFF:
                inputs.put(JoyconConstant.MINUS, false);
                break;
            case JoyconConstant.LEFT_STICK_ON:
                inputs.put(JoyconConstant.LEFT_STICK, true);
                break;
            case JoyconConstant.LEFT_STICK_OFF:
                inputs.put(JoyconConstant.LEFT_STICK, false);
                break;
            case JoyconConstant.CAPTURE_ON:
                inputs.put(JoyconConstant.CAPTURE, true);
                break;
            case JoyconConstant.CAPTURE_OFF:
                inputs.put(JoyconConstant.CAPTURE, false);
                break;
        }
        switch (leftByte) {
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
            case JoyconConstant.LEFT_ON:
                inputs.put(JoyconConstant.LEFT, true);
                break;
            case JoyconConstant.LEFT_OFF:
                inputs.put(JoyconConstant.LEFT, false);
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
