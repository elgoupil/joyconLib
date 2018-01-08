/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joyconLib;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import purejavahidapi.DeviceRemovalListener;
import purejavahidapi.HidDevice;
import purejavahidapi.HidDeviceInfo;
import purejavahidapi.InputReportListener;
import purejavahidapi.PureJavaHidApi;

/**
 * <b>Main class of the library. This class will be your joycon</b>
 * <p>
 * To use it, just create a new Joycon object</p>
 * <p>
 * You can check the example to learn how to use this library</p>
 *
 * @author goupil
 * @version 1.0
 */
public class Joycon {

    protected boolean j_Open;
    protected JoyconListener j_Listener;
    private HidDeviceInfo joyconInfo;
    private HidDevice joycon;
    private LeftTranslator leftTranslator;
    private RightTranslator rightTranslator;

    /**
     * <b>Constructor of the Joycon.</b>
     *
     * <p>
     * To specify a correct identifier just use
     * <b>JoyconConstant.JOYCON_LEFT</b> to use the left Joycon or
     * <b>JoyconConstant.JOYCON_RIGHT</b></p>
     *
     *
     * @param joyconId Identifier of the joycon you'll use
     */
    public Joycon(short joyconId) {
        if ((joyconId == JoyconConstant.JOYCON_LEFT) || (joyconId == JoyconConstant.JOYCON_RIGHT)) {
            initialize(joyconId);
        } else {
            System.out.println("Wrong joycon id!\nPlease use 'JoyconConstant.JOYCON_RIGHT' or 'JoyconConstant.JOYCON_LEFT'");
        }
    }

    /**
     * <b>Set the listener of the Joycon to handle his input</b>
     *
     * @param li The listener, specify null to remove it
     */
    public void setListener(JoyconListener li) {
        j_Listener = li;
    }

    /**
     * <b>Close the connection with the Joycon</b>
     *
     * @return True or false if closed correctly
     */
    public boolean close() {
        boolean isClosed = false;
        try {
            joycon.close();
            isClosed = true;
        } catch (IllegalStateException e) {
            System.out.println("Error while closing conection to the joycon!");
            isClosed = false;
        }
        return isClosed;
    }

    private void initialize(short joyconId) {
        joyconInfo = null;
        joycon = null;
        leftTranslator = new LeftTranslator();
        rightTranslator = new RightTranslator();
        System.out.println("Listing Hid devices...");
        List<HidDeviceInfo> list = PureJavaHidApi.enumerateDevices();
        for (HidDeviceInfo info : list) {
            if ((info.getManufacturerString().equals(JoyconConstant.MANUFACTURER)) && (info.getVendorId() == JoyconConstant.VENDOR_ID) && (info.getProductId() == joyconId)) {
                System.out.println("Found a Nintendo gear!\nConecting...");
                joyconInfo = info;
            }
        }
        if (joyconInfo != null) {
            try {
                joycon = PureJavaHidApi.openDevice(joyconInfo);
                System.out.print("Connected to Joy-Con ");
                if (joyconInfo.getProductId() == JoyconConstant.JOYCON_LEFT) {
                    System.out.println("Left!");
                } else if (joyconInfo.getProductId() == JoyconConstant.JOYCON_RIGHT) {
                    System.out.println("Right!");
                }
                byte ids = 1;
                byte[] datat = new byte[48];
                datat[9] = 0x02;

                Thread.sleep(16);

                datat = new byte[48];
                datat[9] = 0x03;
                datat[10] = 0x30;

                Thread.sleep(16);

                datat = new byte[48];
                datat[9] = 0x30;
                datat[10] = (byte) 240;

                Thread.sleep(16);

                datat = new byte[48];
                datat[9] = 0x48;
                datat[10] = 0x01;

                Thread.sleep(16);

                datat = new byte[48];
                datat[1] = (byte) 0xc2;
                datat[2] = (byte) 0xc8;
                datat[3] = 0x03;
                datat[4] = 0x72;

                Thread.sleep(90);

                datat = new byte[48];
                datat[1] = 0x00;
                datat[2] = 0x01;
                datat[3] = 0x40;
                datat[4] = 0x40;

                Thread.sleep(16);

                datat = new byte[48];
                datat[1] = (byte) 0xc3;
                datat[2] = (byte) 0xc8;
                datat[3] = 0x60;
                datat[4] = 0x64;

                Thread.sleep(30);

                datat = new byte[48];
                datat[9] = 0x48;
                datat[10] = 0x00;

                Thread.sleep(100);
                joycon.setInputReportListener(new InputReportListener() {
                    @Override
                    public void onInputReport(HidDevice source, byte id, byte[] data, int len) {
                        HashMap<String, Boolean> newInputs = new HashMap<>();
                        byte joystick = -1;
                        if (joyconInfo.getProductId() == JoyconConstant.JOYCON_LEFT) {
                            leftTranslator.translate(id, data);
                            newInputs = leftTranslator.getInputs();
                            joystick = leftTranslator.getJoystick();
                        } else if (joyconInfo.getProductId() == JoyconConstant.JOYCON_RIGHT) {
                            rightTranslator.translate(id, data);
                            newInputs = rightTranslator.getInputs();
                            joystick = rightTranslator.getJoystick();
                        }
                        if (j_Listener != null) {
                            if (!newInputs.isEmpty() && joystick != 0) {
                                j_Listener.handleNewInput(new JoyconEvent(newInputs, joystick));
                            }
                        }
                    }
                });
                joycon.setDeviceRemovalListener(new DeviceRemovalListener() {
                    @Override
                    public void onDeviceRemoval(HidDevice source) {
                        System.out.println("Joy-Con disconnected!");
                        System.exit(0);
                    }
                });
            } catch (IOException ex) {
                System.out.println("Error while opening connection to the Joy-Con!\nPlease try to close all software that could communicate with it and retry.");
                System.exit(0);
            } catch (InterruptedException ex) {
                System.out.println("Error, the Joy-Con is not connected anymore!");
                System.exit(0);
            }
        } else {
            System.out.println("No Joy-Con was found :(\nTry to conect a Joy-Con and launch the software again.");
            System.exit(0);
        }
    }
}
