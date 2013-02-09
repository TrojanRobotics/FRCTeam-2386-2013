package com.BCHS;

import edu.wpi.first.wpilibj.*;


public class Chasis 
{
	Bundle leftSide, rightSide;
	Encoder leftEncoder, rightEncoder;
	AnalogChannel ultrasonic;
	PIDController leftSidePID, rightSidePID;
	Solenoid driveSolenoid, climbSolenoid;
	Relay relay;

	public Chasis(int leftAChannel, int leftBChannel, int rightAChannel, int rightBChannel, int ultraSonic, int[] leftSide, int[] rightSide, int driveSoleChannel, int climbSoleChannel, int relayChannel)
	{
		leftEncoder = new Encoder(leftAChannel, leftBChannel);
		rightEncoder = new Encoder(rightAChannel, rightBChannel);
		ultrasonic = new AnalogChannel(ultraSonic);
		this.leftSide = new Bundle(leftSide[0], leftSide[1]);
		this.rightSide = new Bundle(rightSide[0], rightSide[1]);

		
		leftEncoder.setDistancePerPulse(Config.LE_DPP);
		rightEncoder.setDistancePerPulse(Config.RE_DPP);

		driveSolenoid = new Solenoid(driveSoleChannel);
		climbSolenoid = new Solenoid(climbSoleChannel);
		relay = new Relay(relayChannel);
		relay.setDirection(Relay.Direction.kForward);
		
		leftEncoder.setDistancePerPulse(Config.LEFT_SIDE_ENCODER_DPP);
		rightEncoder.setDistancePerPulse(Config.RIGHT_SIDE_ENCODER_DPP);
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
