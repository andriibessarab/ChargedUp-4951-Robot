package frc.robot.commands.intake_commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.intake_subsystems.PivotSubsystem;

public class PivotClose extends CommandBase{
    private PivotSubsystem motor;
    Timer time = new Timer();
    double m_runTime = 1.25;
    double m_speed = -0.2;

    public PivotClose(PivotSubsystem m){
        motor = m;
        addRequirements(motor);
    }

    @Override
    public void initialize() {
        time.reset();
        time.start();
    }

    @Override
    public void execute(){
        motor.setSpeed(m_speed);
    }

    @Override
    public void end(boolean bool){
        motor.stop();
    }
}
