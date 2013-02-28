package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Relay;

public class Shooter 
{
	Jaguar motorBundle;
	Encoder encoder;
	PIDController ShooterPID;
	Relay relay;
	double kp, ki, kd;
	
	public Shooter(int channelOne, int aChannel, int bChannel)
	{
		motorBundle = new Jaguar(channelOne);
		//ShooterPID = new PIDController(kp, ki, kd, encoder, motorBundle);
		//encoder = new Encoder(aChannel, bChannel);
		//encoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kRate);
		//encoder.setDistancePerPulse(Config.SE_DPP);
		relay = new Relay(Config.SHOOTER_RELAY_CHANNEL);
		relay.setDirection(Relay.Direction.kBoth);
		//encoder.start();
		
		
		kp = 0.0;
		ki = 0.0;
		kd = 0.0;
		
	}
	
	public void setRPM(double RPM)
	{
		ShooterPID.enable();
		ShooterPID.setSetpoint(RPM);
	}

	public void set(double speed)
	{
		motorBundle.set(-speed);
	}
	
	public void stop()
	{
		motorBundle.stopMotor();
		ShooterPID.disable();
	}
	
	public void setTableForwards()
	{
		relay.set(Relay.Value.kForward);
	}
	
	public void setTableReverse()
	{
		relay.set(Relay.Value.kReverse);
	}
	
	public void setTableNeutral()
	{
		relay.set(Relay.Value.kOff);
	}
}
