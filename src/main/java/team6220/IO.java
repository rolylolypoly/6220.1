package team6220;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

/**
 * Created by rolypoly on 2/11/17.
 *
 * @author rolypoly
 */
public class IO extends Thread {

    private int counter = 0;
    private Joystick joystick;
    boolean beltToggle, indexerToggle, indexerIn;


    IO(Joystick joystick) {
        this.joystick = joystick;
    }


    @Override
    public synchronized void start() {
        System.out.println("IO Thread starting");
    }

    @Override
    public void run() {
        while (this.isAlive()) {
            if (counter > 100) {
                System.out.println(getStatus());
                counter = 0;
            }
            else counter++;
            beltToggle = joystick.getRawButton(4);
            indexerToggle = joystick.getRawButton(3);
            indexerIn = joystick.getRawButton(5);
            Timer.delay(.005);
        }
    }

    String getStatus() {
        String beltToggle;
        String indexerToggle;
        String indexerIn;
        if (this.beltToggle) beltToggle = "On";
        else beltToggle = "Off";
        if (this.indexerToggle) indexerToggle = "On";
        else indexerToggle = "Off";
        if (this.indexerIn) indexerIn = "On";
        else indexerIn = "Off";
        return "Belt State: " + beltToggle + "\n" + "Indexer State: " + indexerToggle + " Direction: " + indexerIn;
    }
}
