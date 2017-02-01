package team6220;

/**
 * Created by rolypoly on 1/16/17.
 * @author rolypoly
 */

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.*;

public class Robot extends SampleRobot {

    private RobotDrive drive;
    private CANTalon talon1, talon2, talon3, talon4;
    private XboxController xbox;
    private ADXRS450_Gyro gyro;

    @Override
    protected void robotInit() {
        super.robotInit();
        xbox = new XboxController(0);
        talon1 = new CANTalon(1);
        talon2 = new CANTalon(2);
        talon3 = new CANTalon(3);
        talon4 = new CANTalon(4);
        drive = new RobotDrive(talon1, talon2, talon3, talon4);
        gyro = new ADXRS450_Gyro();
        gyro.calibrate();
        gyro.reset();
        CameraServer.getInstance().startAutomaticCapture();
    }

    @Override
    public void autonomous() {
        super.autonomous();
    }

    @Override
    public void operatorControl() {
        super.operatorControl();
        while (isOperatorControl() && isEnabled()) {
            drive.mecanumDrive_Cartesian(
                    xbox.getX(GenericHID.Hand.kRight),
                    xbox.getY(GenericHID.Hand.kRight),
                    xbox.getY(GenericHID.Hand.kLeft),
                    gyro.getAngle());
            Timer.delay(.005);
        }
    }

}
