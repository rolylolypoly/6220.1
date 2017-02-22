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
    private VictorSP shooter;
    private Encoder encoder;
    private PIDController flywol;
    private int counter = 0;
    private Spark belt;
    private VictorSP indexer;
    private Spark spindexer;
    private VictorSP climber;

    @Override
    protected void robotInit() {
        super.robotInit();
        joystick = new Joystick(0);
        talon1 = new CANTalon(1);
        talon2 = new CANTalon(2);
        talon3 = new CANTalon(3);
        talon4 = new CANTalon(4);
        drive = new RobotDrive(talon1, talon2, talon3, talon4);
        belt = new Spark(2);
        indexer = new VictorSP(3);
        shooter = new VictorSP(0);
        spindexer = new Spark(1);
        climber = new VictorSP(4);

        CameraServer.getInstance().startAutomaticCapture();

        encoder = new Encoder(1, 0, false, CounterBase.EncodingType.k4X);
        encoder.setMaxPeriod(.01);
        encoder.setMinRate(100);
        encoder.setDistancePerPulse(20);
        encoder.setSamplesToAverage(24);
        encoder.setPIDSourceType(PIDSourceType.kRate);
        flywol = new PIDController(0.00001, 0, 0.000015, encoder, shooter);
        flywol.setOutputRange(0, 1);
        flywol.setSetpoint(18000);
        LiveWindow.addActuator("Shooter", "PID", flywol);
        LiveWindow.addSensor("Shooter", "Encoder", encoder);
    }

    @Override
    protected void disabled() {
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
                spindexer.set(.5);
                indexer.set(.75);
            }

            else if (joystick.getRawButton(2)){

                belt.set(1);
                indexer.set(-.75);
            }

            else if (joystick.getRawButton(8)) {
                climber.set(1);
            }

            else if (joystick.getRawButton(7)) {
                climber.set(-1);
            }

            else {
                flywol.disable();
                belt.set(0);
                spindexer.set(0);
                indexer.set(0);
                climber.set(0);
            }
            Timer.delay(.001);


        }
    }

    @Override
    public void test() {
        while (isTest() && isEnabled()) {
            if (counter > 250) {
                System.out.println(String.format("RPM: %f" +
                                " Throttle: %f",
                        encoder.getRate(),
                        shooter.getSpeed()));
                counter = 0;
            }
            LiveWindow.run();
            Timer.delay(.001);
            counter++;
        }
    }

}
