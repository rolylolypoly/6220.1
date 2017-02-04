package team6220;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;

/**
 * Created by rolypoly on 2/1/17.
 */
public class Telemetry extends Thread {

    private Encoder encoder;
    private boolean isEnabled = true;

    Telemetry(Encoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public void run() {
        super.run();
        while (!this.isInterrupted()) {
            while (isEnabled) {
                System.out.println(encoder.getRate());
                Timer.delay(.2);
            }
            Timer.delay(.1);
        }
    }

    public synchronized boolean isEnabled() {
        return isEnabled;
    }

    public synchronized void isEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

}
