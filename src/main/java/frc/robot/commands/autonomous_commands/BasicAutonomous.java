package frc.robot.commands.autonomous_commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrain_subsystems.DriveSubsystem;

public class BasicAutonomous extends CommandBase {
    private final DriveSubsystem m_drive;
    Timer m_timer = new Timer();

    public BasicAutonomous(DriveSubsystem drive) {
        m_drive = drive;
        addRequirements(m_drive);
    }

    @Override
    public void initialize() {
        m_timer.reset();
        m_timer.start();
    }

    @Override
    public void execute() {
        if (m_timer.get() < 0.5) {
            m_drive.driveMecanum(0, -0.75, 0);
        } else if (m_timer.get() < 1) {
            m_drive.driveMecanum(0, 0.75, 0);
        } else if(m_timer.get()<1.3) {
            m_drive.driveMecanum(0, 0, 0);
        }
    }

    @Override
    public boolean isFinished() {
        if (m_timer.get() > 1.5) {
            return true;
        }
        return false;
    }

    @Override
    public void end(boolean bool){
        m_drive.driveMecanum(0, 0, 0);
    }
}