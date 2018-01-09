/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joyconLib.example;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import purejavahidapi.DeviceRemovalListener;
import purejavahidapi.HidDevice;
import purejavahidapi.HidDeviceInfo;
import purejavahidapi.InputReportListener;
import purejavahidapi.PureJavaHidApi;

/**
 *
 * @author virus
 */
public class FlyingJoyTestJoycon2 {

    static byte nbOld = 0;
    static HidDevice joycon;
    static int[] factory_stick_cal = new int[18];
    static int[] stick_cal_x_l = new int[3];
    static int[] stick_cal_y_l = new int[3];

    /**
     * @param args the command line arguments
     */
    public static void example() throws InterruptedException {
        HidDeviceInfo joyconInfo = null;
        List<HidDeviceInfo> list = PureJavaHidApi.enumerateDevices();
        for (HidDeviceInfo info : list) {
            if ((info.getVendorId() == 0x057E) && info.getManufacturerString().equals("Nintendo")) {
                System.out.printf("VID = 0x%04X PID = 0x%04X Manufacturer = %s Product = %s Path = %s\n", //
                        info.getVendorId(), //
                        info.getProductId(), //
                        info.getManufacturerString(), //
                        info.getProductString(), //
                        info.getPath());
                joyconInfo = info;
            }
        }
        if (joyconInfo != null) {
            try {
                joycon = PureJavaHidApi.openDevice(joyconInfo);
                joycon.setInputReportListener(new InputReportListener() {
                    @Override
                    public void onInputReport(HidDevice source, byte id, byte[] data, int len) {
                        handleInputs(source, id, data, len);
                    }
                });
                joycon.setDeviceRemovalListener(new DeviceRemovalListener() {
                    @Override
                    public void onDeviceRemoval(HidDevice source) {
                        System.out.println("Device " + joycon.toString() + " disconnected!");
                    }
                });
            } catch (IOException ex) {
                Logger.getLogger(FlyingJoyTestJoycon2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Thread.sleep(100);
        byte ids = 1;
        byte[] datat = new byte[48];
        datat[9] = 0x02;
        System.out.println("output " + joycon.setOutputReport(ids, datat, 48));

        Thread.sleep(16);

        datat = new byte[48];
        datat[9] = 0x03;
        datat[10] = 0x3F;
        System.out.println("output " + joycon.setOutputReport(ids, datat, 48));
        Thread.sleep(16);

        datat = new byte[48];
        datat[9] = 0x30;
        datat[10] = (byte) 240;
        System.out.println("output " + joycon.setOutputReport(ids, datat, 48));

        Thread.sleep(16);

        datat = new byte[48];
        datat[9] = 0x48;
        datat[10] = 0x01;
        System.out.println("output " + joycon.setOutputReport(ids, datat, 48));

        Thread.sleep(16);

        datat = new byte[48];
        datat[1] = (byte) 0xc2;
        datat[2] = (byte) 0xc8;
        datat[3] = 0x03;
        datat[4] = 0x72;
        System.out.println("output " + joycon.setOutputReport(ids, datat, 48));

        Thread.sleep(90);

        datat = new byte[48];
        datat[1] = 0x00;
        datat[2] = 0x01;
        datat[3] = 0x40;
        datat[4] = 0x40;
        System.out.println("output " + joycon.setOutputReport(ids, datat, 48));

        Thread.sleep(16);

        datat = new byte[48];
        datat[1] = (byte) 0xc3;
        datat[2] = (byte) 0xc8;
        datat[3] = 0x60;
        datat[4] = 0x64;
        System.out.println("output " + joycon.setOutputReport(ids, datat, 48));

        Thread.sleep(30);

        datat = new byte[48];
        datat[9] = 0x48;
        datat[10] = 0x00;
        System.out.println("output " + joycon.setOutputReport(ids, datat, 48));

        Thread.sleep(16);

        datat = new byte[48];
        datat[9] = 0x10;
        datat[10] = 0x3D;
        datat[11] = 0x60;
        datat[14] = 0x12;
        System.out.println("output " + joycon.setOutputReport(ids, datat, 48));

        Thread.sleep(100);

        stick_cal_x_l[1] = (factory_stick_cal[4] << 8) & 0xF00 | factory_stick_cal[3];
        stick_cal_y_l[1] = (factory_stick_cal[5] << 4) | (factory_stick_cal[4] >> 4);
        stick_cal_x_l[0] = stick_cal_x_l[1] - ((factory_stick_cal[7] << 8) & 0xF00 | factory_stick_cal[6]);
        stick_cal_y_l[0] = stick_cal_y_l[1] - ((factory_stick_cal[8] << 4) | (factory_stick_cal[7] >> 4));
        stick_cal_x_l[2] = stick_cal_x_l[1] + ((factory_stick_cal[1] << 8) & 0xF00 | factory_stick_cal[0]);
        stick_cal_y_l[2] = stick_cal_y_l[1] + ((factory_stick_cal[2] << 4) | (factory_stick_cal[2] >> 4));

        Thread.sleep(100);

        datat = new byte[48];
        datat[9] = 0x03;
        datat[10] = 0x30;
        System.out.println("output " + joycon.setOutputReport(ids, datat, 48));
        Thread.sleep(16);

//        System.exit(0);
    }

    private static void handleInputs(HidDevice source, byte id, byte[] data, int len) {
        if (id == 33) {
            if (data[12] == -112) {
                for (int i = 19; i < 37; i++) {
                    int c;
                    byte b = data[i];
                    if (b < 0) {
                        c = b + 256;
                    }else{
                        c = b;
                    }
                    factory_stick_cal[i - 19] = c;
                }
            }
        }
        System.out.println("ID: " + id);
        System.out.println("Length: " + len);
        System.out.println("Data: ");
        for (int i = 0; i < data.length; i++) {
            byte b = data[i];
//            if (i == 14) {
//                System.out.println("");
//                System.out.println("SubCommand:");
//            }

            if (b < 0) {
                int c = b + 256;
                System.out.print(Integer.toHexString(c) + ";");
            } else {
                System.out.print(Integer.toHexString(b) + ";");
            }
        }
        System.out.println("");
        System.out.println("Length Data: " + data.length);
        System.out.println("");
        System.out.print("Joystick : ");
        int[] temp = new int[8];
        for (int i = 5; i < 8; i++) {
            byte b = data[i];
            if (b < 0) {
                temp[i] = b + 256;
            } else {
                temp[i] = b;
            }
        }
        for (int i : temp) {
            System.out.print(i + ";");
        }
        System.out.println("");
        int horizontal = temp[5] | ((temp[6] & 0xF) << 8);
        int vertical = (temp[6] >> 4) | (temp[7] << 4);
        System.out.print("\tHorizontal : " + horizontal);
        System.out.println("\tVertical : " + vertical);
        System.out.println("");
        analogStickCalc(horizontal, vertical, stick_cal_x_l, stick_cal_y_l);
    }

    private static void analogStickCalc(int x, int y, int[] x_calc, int[] y_calc) {
        float xF, yF, hori, vert;
        float deadZoneCenter = 0.15f;
        float deadZoneOuter = 0.10f;

        x = Math.max(x_calc[0], Math.min(x_calc[2], x));
        y = Math.max(y_calc[0], Math.min(y_calc[2], y));

        if (x >= x_calc[1]) {
            xF = (float) (x - x_calc[1]) / (float) (x_calc[2] - x_calc[1]);
        } else {
            xF = -((float) (x - x_calc[1]) / (float) (x_calc[0] - x_calc[1]));
        }
        if (y >= y_calc[1]) {
            yF = (float) (y - y_calc[1]) / (float) (y_calc[2] - y_calc[1]);
        } else {
            yF = -((float) (y - y_calc[1]) / (float) (y_calc[0] - y_calc[1]));
        }

        float mag = (float) Math.sqrt(xF * xF + yF * yF);

        if (mag > deadZoneCenter) {
            // scale such that output magnitude is in the range [0.0f, 1.0f]
            float legalRange = 1.0f - deadZoneOuter - deadZoneCenter;
            float normalizedMag = Math.min(1.0f, (mag - deadZoneCenter) / legalRange);
            float scale = normalizedMag / mag;
            hori = xF * scale;
            vert = yF * scale;
        } else {
            // stick is in the inner dead zone
            hori = 0.0f;
            vert = 0.0f;
        }
        
        BigDecimal bdHori = new BigDecimal(hori);
        bdHori = bdHori.setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal bdVert = new BigDecimal(vert);
        bdVert = bdVert.setScale(2, RoundingMode.HALF_EVEN);

        System.out.println("");
        System.out.print("\tHorizontal : " + bdHori.floatValue());
        System.out.println("\tVertical : " + bdVert.floatValue());
        System.out.println("");
    }

}
