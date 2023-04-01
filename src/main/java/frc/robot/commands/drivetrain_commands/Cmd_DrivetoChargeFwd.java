package frc.robot.commands;
/*
 * Balances the robot on the Charge Station
 * Robot should start on the Charge station at an non zero pitch
 */

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.MathUtil;

public class Cmd_DrivetoChargeFwd extends CommandBase {
    
    private final DriveSubsystem m_driveTrain;
    private boolean timing = false;
    private double timestart = 0.0;
    private PIDController BalancePID; 
    private double maxAngle=0;
    private double timeangle;

    public Cmd_DrivetoChargeFwd( DriveSubsystem drive) {
        m_driveTrain = drive;
        addRequirements(m_driveTrain);
     }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        BalancePID = new PIDController(Constants.BalanceKp, Constants.BalanceKi,Constants.BalanceKd);
        BalancePID.setSetpoint(0.0);
        BalancePID.setTolerance( 5.0);
        timeangle = Timer.getFPGATimestamp();
    }

    // Called every time the scheduler runs while the command is scheduled.
    
    //@Override
    public void execute() {
        SmartDashboard.putString("Drive Cmd","DriveCharge");
        SmartDashboard.putString("Drive Charge","Drivecharge running");
        if (m_driveTrain.getRoll() > maxAngle )
            maxAngle = m_driveTrain.getRoll();

        double DriveSpeed = -0.25; //BalancePID.calculate(m_driveTrain.getRoll());
        //if ( (maxAngle - m_driveTrain.getRoll())>1)
        //    DriveSpeed =0;
        DriveSpeed = MathUtil.clamp(DriveSpeed, -1, 1);
        
         m_driveTrain.arcadeDrive(DriveSpeed,0);  

         SmartDashboard.putNumber("Pitch", m_driveTrain.getPitch());
         SmartDashboard.putNumber("Roll", m_driveTrain.getRoll());
         SmartDashboard.putNumber("Max Angle",maxAngle);
         SmartDashboard.putNumber("balance out", DriveSpeed);
    }

    
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_driveTrain.arcadeDrive(0.0, 0.0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        double currenttime =  Timer.getFPGATimestamp() -timeangle;
        if (m_driveTrain.getRoll()<-10 && currenttime >2.5)
        {
            SmartDashboard.putString("Drive Charge","DriveCharge finishe");
                return true;
        }
        return false;
    }

    @Override
    public boolean runsWhenDisabled() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DISABLED
        return false;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DISABLED
    }
}
