/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joyconLib;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import purejavahidapi.DeviceRemovalListener;
import purejavahidapi.HidDevice;
import purejavahidapi.HidDeviceInfo;
import purejavahidapi.InputReportListener;
import purejavahidapi.PureJavaHidApi;

/**
 *
 * @author renardn
 */
public class Joycon {

    protected boolean j_Open;
    protected JoyconListener j_Listener;
    private HidDeviceInfo joyconInfo;
    private HidDevice joycon;
    private LeftTraductor leftTraductor;
    private RightTraductor rightTraductor;

    public Joycon(short joyconId) {
        if ((joyconId == JoyconConstant.JOYCON_LEFT) || (joyconId == JoyconConstant.JOYCON_RIGHT)) {
            initialize(joyconId);
        } else {
            System.out.println("Wrong joycon id!\nPlease use 'JoyconConstant.JOYCON_RIGHT or JoyconConstant.JOYCON_LEFT'");
        }
    }

    public void setListener(JoyconListener li) {
        j_Listener = li;
    }

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
        leftTraductor = new LeftTraductor();
        rightTraductor = new RightTraductor();
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
                joycon.setInputReportListener(new InputReportListener() {
                    @Override
                    public void onInputReport(HidDevice source, byte id, byte[] data, int len) {
                        HashMap<String, Boolean> newInputs = new HashMap<>();
                        byte joystick = -1;
                        if (joyconInfo.getProductId() == JoyconConstant.JOYCON_LEFT) {
                            leftTraductor.translate(data);
                            newInputs = leftTraductor.getInputs();
                            joystick = leftTraductor.getJoystick();
                        } else if (joyconInfo.getProductId() == JoyconConstant.JOYCON_RIGHT) {
                            rightTraductor.translate(data);
                            newInputs = rightTraductor.getInputs();
                            joystick = rightTraductor.getJoystick();
                        }
                        if (j_Listener != null) {
                            j_Listener.handleNewInput(new JoyconEvent(newInputs, joystick));
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
            }
        } else {
            System.out.println("No Joy-Con was found :(\nTry to conect a Joy-Con and launch the software again.");
            System.exit(0);
        }
    }

}
