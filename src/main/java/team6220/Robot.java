package team6220;

/**
 * Created by rolypoly on 1/16/17.
 */

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.VictorSP;

public class Robot extends SampleRobot {

    private RobotDrive drive;
    private CANTalon talon1, talon2, talon3, talon4;
    private VictorSP victor;
    private Joystick joystick;

    @Override
    protected void robotInit() {
        super.robotInit();
        joystick = new Joystick(0);
        talon1 = new CANTalon(1);
        talon2 = new CANTalon(2);
        talon3 = new CANTalon(3);
        talon4 = new CANTalon(4);
        victor = new VictorSP(0);
        drive = new RobotDrive(talon1, talon2, talon3, talon4);
    }

    @Override
    public void autonomous() {
        super.autonomous();
    }

    @Override
    public void operatorControl() {
        super.operatorControl();
        while (isOperatorControl() && isEnabled()) {
            drive.tankDrive(joystick.getRawAxis(1), joystick.getRawAxis(5));
            victor.set(joystick.getRawAxis(2));
        }
    }

}
