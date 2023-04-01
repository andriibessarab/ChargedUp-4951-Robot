package frc.robot.commands;
/*
 * Balances the robot on the Charge Station
 * Robot should start on the Charge station at an non zero pitch
 */

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.MathUtil;

public class Cmd_DriveBalanceFwd extends CommandBase {
    
    private final DriveSubsystem m_driveTrain;
    private PIDController BalancePID; 
    private double minAngle=0;
    public Cmd_DriveBalanceFwd( DriveSubsystem drive) {
        m_driveTrain = drive;
        addRequirements(m_driveTrain);
     }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        BalancePID = new PIDController(Constants.BalanceKp, Constants.BalanceKi,Constants.BalanceKd);
        BalancePID.setSetpoint(0.0);
        BalancePID.setTolerance( 5.0);
    }

    // Called every time the scheduler runs while the command is scheduled.
    
    //@Override
    public void execute() {
        SmartDashboard.putString("Drive Cmd","DriveBalance");
        SmartDashboard.putString("Drive bal","DriveBalance running");
        if (m_driveTrain.getRoll() < minAngle )
            minAngle = m_driveTrain.getRoll();
//was 0.35
        double DriveSpeed = -0.15; //BalancePID.calculate(m_driveTrain.getRoll());
        if ( (minAngle - m_driveTrain.getRoll())>-1)
            DriveSpeed =0;
        DriveSpeed = MathUtil.clamp(DriveSpeed, -0.25, 0.25);
        
         m_driveTrain.arcadeDrive(DriveSpeed,0);  

         SmartDashboard.putNumber("Pitch", m_driveTrain.getPitch());
         SmartDashboard.putNumber("Roll", m_driveTrain.getRoll());
         SmartDashboard.putNumber("Max Angle",minAngle);
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
        if (minAngle - m_driveTrain.getRoll()>-1)
        {
            SmartDashboard.putString("Drive bal","DriveBalance finishe");
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
