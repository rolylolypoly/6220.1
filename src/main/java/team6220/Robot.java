package team6220;

/**
 * Created by rolypoly on 1/16/17.
 *
 * @author rolypoly
 */

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Robot extends SampleRobot {

    private RobotDrive drive;
    private CANTalon talon1, talon2, talon3, talon4;
    private Joystick joystick;
    private VictorSP spinfast;
    private Encoder encoder;
    private PIDController flywol;
    private int counter = 0;
    private Victor belt;
    private Spark indexer;
    private IO io;

    @Override
    protected void robotInit() {
        super.robotInit();
        joystick = new Joystick(0);
        talon1 = new CANTalon(1);
        talon2 = new CANTalon(2);
        talon3 = new CANTalon(3);
        talon4 = new CANTalon(4);
        drive = new RobotDrive(talon1, talon2, talon3, talon4);
        belt = new Victor(1);
        indexer = new Spark(2);
        CameraServer.getInstance().startAutomaticCapture();

        spinfast = new VictorSP(0);
        encoder = new Encoder(1, 0, false, CounterBase.EncodingType.k4X);
        encoder.setMaxPeriod(.01);
        encoder.setMinRate(100);
        encoder.setDistancePerPulse(20);
        encoder.setSamplesToAverage(24);
        encoder.setPIDSourceType(PIDSourceType.kRate);
        flywol = new PIDController(0.00001, 0, 0.000015, encoder, spinfast);
        flywol.setOutputRange(0, 1);
        flywol.setSetpoint(18000);
        LiveWindow.addActuator("Shooter", "PID", flywol);
        LiveWindow.addSensor("Shooter", "Encoder", encoder);

        io = new IO(joystick);
        io.start();
    }

    @Override
    protected void disabled() {
        try {
            io.join();
        } catch (InterruptedException e) {
            System.err.println("Error stopping IO thread.");
            e.printStackTrace();
        }
    }

    @Override
    public void autonomous() {
        super.autonomous();
    }

    @Override
    public void operatorControl() {
        while (isOperatorControl() && isEnabled()) {
            drive.mecanumDrive_Cartesian(
                    joystick.getX(),
                    joystick.getY(),
                    joystick.getTwist(),
                    0);
            if (joystick.getTrigger()) {
                flywol.enable();
                belt.set(1);
                indexer.set(-1);
            }
            else {
                flywol.disable();
                belt.set(0);
                indexer.set(0);
            }
            if (joystick.getRawButton(7)){
                belt.set(1);
                indexer.set(1);
            }
            else {
                belt.set(0);
                indexer.set(0);
            }
            counter++;
            Timer.delay(.005); //200 Hz
        }
    }

    @Override
    public void test() {
        while (isTest() && isEnabled()) {
            if (counter > 250) {
                System.out.println(String.format("RPM: %f" +
                                " Throttle: %f",
                        encoder.getRate(),
                        spinfast.getSpeed()));
                counter = 0;
            }
            LiveWindow.run();
            Timer.delay(.001);
            counter++;
        }
    }

}
