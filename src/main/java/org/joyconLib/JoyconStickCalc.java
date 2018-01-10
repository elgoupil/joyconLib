/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joyconLib;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Administrateur
 */
public class JoyconStickCalc {

    private float horizontal;
    private float vertical;

    public JoyconStickCalc() {
        horizontal = 0.0f;
        vertical = 0.0f;
    }

    public void analogStickCalc(int x, int y, int[] x_calc, int[] y_calc) {
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

        horizontal = bdHori.floatValue();
        vertical = bdVert.floatValue();
    }

    public float getHorizontal() {
        return horizontal;
    }

    public float getVertical() {
        return vertical;
    }

}
