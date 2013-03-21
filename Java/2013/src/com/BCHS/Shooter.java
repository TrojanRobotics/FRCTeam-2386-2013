package com.BCHS;

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
	
	
	public Shooter(int channelOne, int aChannel, int bChannel)
	{
		motorBundle = new Jaguar(channelOne);
		encoder = new Encoder(aChannel, bChannel);
		encoder.setDistancePerPulse(Config.SE_DPP);
		encoder.start();
		encoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kRate);
		ShooterPID = new PIDController(Config.SHOOTER_PID[0], Config.SHOOTER_PID[1], Config.SHOOTER_PID[2], encoder, motorBundle);
		relay = new Relay(Config.SHOOTER_RELAY_CHANNEL);
		relay.setDirection(Relay.Direction.kBoth);
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
