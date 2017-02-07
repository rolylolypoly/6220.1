package team6220;

/**
 * Created by rolypoly on 1/16/17.
 * @author rolypoly
 */

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Robot extends SampleRobot {

    private RobotDrive drive;
    private CANTalon talon1, talon2, talon3, talon4;
    private Joystick joystick;
    private ADXRS450_Gyro gyro;
    private VictorSP spinfast;
    private Encoder encoder;
    private PIDController flywol;
    private Telemetry telemetry;

    @Override
    protected void robotInit() {
        super.robotInit();
        joystick = new Joystick(0);
        talon1 = new CANTalon(1);
        talon2 = new CANTalon(2);
        talon3 = new CANTalon(3);
        talon4 = new CANTalon(4);
        drive = new RobotDrive(talon1, talon2, talon3, talon4);
        gyro = new ADXRS450_Gyro();
        gyro.calibrate();
        gyro.reset();
        CameraServer.getInstance().startAutomaticCapture();

        spinfast = new VictorSP(0);
        encoder = new Encoder(0, 1, false, CounterBase.EncodingType.k1X);
        encoder.setDistancePerPulse(1 / 12);
        encoder.setMinRate(1);
        encoder.setSamplesToAverage(2);
        encoder.setPIDSourceType(PIDSourceType.kRate);
        flywol = new PIDController(1.2, 0.7, 0.3, 1, encoder, spinfast);
        flywol.setOutputRange(0, 1);
        flywol.setSetpoint(1000);
        flywol.setContinuous();
        telemetry = new Telemetry(encoder);
    }

    @Override
    public void autonomous() {
        super.autonomous();
    }

    @Override
    public void operatorControl() {
        super.operatorControl();
        telemetry.start();
        telemetry.isEnabled(isEnabled());
        while (isOperatorControl() && isEnabled()) {
            drive.mecanumDrive_Cartesian(
                    joystick.getX(),
                    joystick.getY(),
                    joystick.getTwist(),
                    gyro.getAngle());
            if (joystick.getTrigger())
                flywol.enable();
            else
                flywol.disable();
            Timer.delay(.005); //200 Hz
        }
        telemetry.isEnabled(isEnabled());
        try {
            telemetry.join();
        } catch (InterruptedException e) {
            System.err.println("Operator control stacktrace");
            e.printStackTrace();
        }
    }

    @Override
    public void test() {
        super.test();
        telemetry.isEnabled(isEnabled());
        telemetry.start();
        telemetry.isEnabled(isEnabled());
        flywol.startLiveWindowMode();
        encoder.startLiveWindowMode();
        while (isTest() && isEnabled()) {
            telemetry.isEnabled(isEnabled());
            LiveWindow.run();
            Timer.delay(.01);
        }
        flywol.stopLiveWindowMode();
        encoder.stopLiveWindowMode();
        try {
            telemetry.join();
        } catch (InterruptedException e) {
            System.err.println("Test control exception");
            e.printStackTrace();
        }
    }

}
