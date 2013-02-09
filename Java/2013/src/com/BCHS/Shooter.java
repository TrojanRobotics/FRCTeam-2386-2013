package com.BCHS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Relay;

public class Shooter 
{
	Bundle motorBundle;
	Encoder encoder;
	PIDController ShooterPID;
	Relay relay;
	double kp, ki, kd;
	
	public Shooter(int channelOne, int channelTwo, int aChannel, int bChannel)
	{
		motorBundle = new Bundle(channelOne, channelTwo);
		ShooterPID = new PIDController(kp, ki, kd, encoder, motorBundle);
		encoder = new Encoder(aChannel, bChannel);
		encoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kRate);
		encoder.setDistancePerPulse(Config.SE_DPP);
		relay = new Relay(10);
		relay.setDirection(Relay.Direction.kForward);
		encoder.start();
		
		
		kp = 0.0;
		ki = 0.0;
		kd = 0.0;
		
	}
	
	public void setPRM(double RPM)
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
		motorBundle.stop();
		ShooterPID.disable();
	}
	
	public void setTableForward()
	{
		relay.setDirection(Relay.Direction.kForward);
		relay.set(Relay.Value.kOn);
	}
	
	public void setTableReverse()
	{
		relay.setDirection(Relay.Direction.kReverse);
		relay.set(Relay.Value.kOn);
	}
	
	public void setTableNeutral()
	{
		relay.set(Relay.Value.kOff);
	}
			
}
