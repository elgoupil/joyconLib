/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joyconLib;

/**
 * <b>The listener for the joycon</b>
 * <p>
 * Give this listener to the joycon to handle his inputs</p>
 * <p>
 * Refer to the example to learn how to use it</p>
 *
 * @version 1.0
 * @author goupil
 */
public interface JoyconListener {

    /**
     *
     *
     * @param e The event object
     */
    public void handleNewInput(JoyconEvent e);
}
