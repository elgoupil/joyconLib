
import java.util.Map;
import org.joyconLib.Joycon;
import org.joyconLib.JoyconConstant;
import org.joyconLib.JoyconEvent;
import org.joyconLib.JoyconListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author renardn
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Create a new Joycon with the identifier of the left joycon
        Joycon joycon = new Joycon(JoyconConstant.JOYCON_LEFT);
        //Set the listener for the Joycon and create a new Listener on the go
        joycon.setListener(new JoyconListener() {
            //Override the method to do what you want with the inputs
            @Override
            public void handleNewInput(JoyconEvent je) {
                //Navigate in the inputs map
                for (Map.Entry<String, Boolean> entry : je.getNewInputs().entrySet()) {
                    //Print to the console the name of the button and his state
                    System.out.print("Button: " + entry.getKey() + " is " + (entry.getValue() ? "ON \t" : "OFF\t"));
                    //If the button is the capture button it will stop the progam
                    if (entry.getKey().equals(JoyconConstant.CAPTURE)) {
                        System.exit(0);
                        //If the button is the minus button it will close the connection with the joycon
                    } else if (entry.getKey().equals(JoyconConstant.MINUS)) {
                        joycon.close();
                    }
                }
                //Print to the console the position of the joystick
                System.out.println("Joystick\tX: " + je.getHorizontal() + "\tY: " + je.getVertical());

                System.out.println("Battery: " + je.getBattery());
            }
        });
    }

}
