package com.BCHS;

import edu.wpi.first.wpilibj.*;


public class Chasis 
{
	Bundle leftSide, rightSide;
	Encoder leftEncoder, rightEncoder;
	PIDController leftSidePID, rightSidePID;
	Solenoid driveSolenoid, climbSolenoid;
	Compressor compressor;

	public Chasis(int leftAChannel, int leftBChannel, int rightAChannel, int rightBChannel, int[] leftSide, int[] rightSide)
	{
		leftEncoder = new Encoder(leftAChannel, leftBChannel);
		rightEncoder = new Encoder(rightAChannel, rightBChannel);
		this.leftSide = new Bundle(leftSide[0], leftSide[1]);
		this.rightSide = new Bundle(rightSide[0], rightSide[1]);

		leftEncoder.setDistancePerPulse(Config.LE_DPP);
		rightEncoder.setDistancePerPulse(Config.RE_DPP);
		
		leftEncoder.start();
		rightEncoder.start();
		
		leftEncoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kDistance);
		rightEncoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kDistance);
		
		leftSidePID = new PIDController(Config.PID[0],Config.PID[1],Config.PID[2],leftEncoder, this.leftSide);
		rightSidePID = new PIDController(Config.PID[0],Config.PID[1],Config.PID[2],rightEncoder, this.rightSide);
		
		compressor = new Compressor(Config.PNEUMATICS[0], Config.PNEUMATICS[1]);
		
		driveSolenoid = new Solenoid(Config.SOLENOID_CHANNEL[0]);
		climbSolenoid = new Solenoid(Config.SOLENOID_CHANNEL[1]);

	}													
	
	public void set(double speed)
	{
		leftSide.set(-speed);
		rightSide.set(speed);
	}
	
	public void enable()
	{
		leftSidePID.enable();
		rightSidePID.enable();
	}
	
	public void stop()
	{
		leftSide.stop();
		rightSide.stop();
		leftSidePID.disable();
		rightSidePID.disable();
	}
	
	public void reset()
	{
		leftEncoder.reset();
		rightEncoder.reset();
	}
	
	public void setSetpoint(double setpoint)
	{
		leftSidePID.setSetpoint(setpoint);
		rightSidePID.setSetpoint(-setpoint);
	}
}
