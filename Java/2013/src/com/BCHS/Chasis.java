package com.BCHS;

import edu.wpi.first.wpilibj.*;


public class Chasis 
{
    
    public static class RobotMode
    {
        public final int value;
        
        public static final RobotMode driveMode = new RobotMode(1);
        public static final RobotMode climbMode = new RobotMode(2);  
        
        public RobotMode(int value)
        {
            this.value = value;
        }
    }
    
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
    
    public void changeMode(RobotMode mode) //true is climb, false is drive
    {
        if (mode == RobotMode.climbMode) {
            climbSolenoid.set(true);
            driveSolenoid.set(false);
        } else {
            climbSolenoid.set(false);
            driveSolenoid.set(true);
        }
    }
    public RobotMode getMode()
    {
        if (climbSolenoid.get()) {
            return RobotMode.climbMode;
        } else {
            return RobotMode.driveMode;
        }
    }
}
