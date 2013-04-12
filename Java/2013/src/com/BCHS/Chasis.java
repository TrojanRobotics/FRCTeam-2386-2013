package com.BCHS;

import edu.wpi.first.wpilibj.*;


public class Chasis 
{
	
	public static class TilterMode
	{
		public final int value;
	
		public static final TilterMode tiltup = new TilterMode(1);
		public static final TilterMode tiltdown = new TilterMode(2);
		
		public TilterMode(int value)
		{
			this.value = value;
		}
	}
    
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
	PIDController leftSidePID, rightSidePID, leftClimbPID, rightClimbPID;
	Solenoid tableupSolenoid, tabledownSolenoid, wheelyBarUp, wheelyBarDown, shooterOut, shooterIn;
	Compressor compressor;

	public Chasis(int leftAChannel, int leftBChannel, int rightAChannel, int rightBChannel)
	{
		leftEncoder = new Encoder(leftAChannel, leftBChannel);
		rightEncoder = new Encoder(rightAChannel, rightBChannel);
		this.leftSide = new Bundle(Config.LDRIVE[0], Config.LDRIVE[1]);
		this.rightSide = new Bundle(Config.RDRIVE[0], Config.RDRIVE[1]);

		leftEncoder.setDistancePerPulse(Config.LE_DPP);
		rightEncoder.setDistancePerPulse(Config.RE_DPP);
		
		leftEncoder.start();
		rightEncoder.start();
		
		leftEncoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kDistance);
		rightEncoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kDistance);
		
		leftSidePID = new PIDController(Config.PID[0],Config.PID[1],Config.PID[2],leftEncoder, this.leftSide);
		rightSidePID = new PIDController(Config.PID[0],Config.PID[1],Config.PID[2],rightEncoder, this.rightSide);
		
		leftClimbPID = new PIDController(Config.CLIMB_PID[0],Config.CLIMB_PID[1],Config.CLIMB_PID[2],leftEncoder, this.leftSide);
		rightClimbPID = new PIDController(Config.CLIMB_PID[0],Config.CLIMB_PID[1],Config.CLIMB_PID[2],rightEncoder, this.rightSide);
		compressor = new Compressor(Config.PNEUMATICS[0], Config.PNEUMATICS[1]);
		
		tableupSolenoid = new Solenoid(Config.SOLENOID_CHANNEL[0]);
		tabledownSolenoid = new Solenoid(Config.SOLENOID_CHANNEL[1]);
        wheelyBarUp = new Solenoid(Config.SOLENOID_CHANNEL[2]);
        wheelyBarDown = new Solenoid(Config.SOLENOID_CHANNEL[3]);
		shooterOut = new Solenoid(Config.SOLENOID_CHANNEL[4]);
		shooterIn = new Solenoid(Config.SOLENOID_CHANNEL[5]);
		
		wheelyBarUp.set(true);
	}													
	
	public void set(double speed)
	{
		leftSide.set(speed);
		rightSide.set(-speed);
	}
	
	public void stop()
	{
		leftSide.stop();
		rightSide.stop();
		leftSidePID.disable();
		rightSidePID.disable();
		leftClimbPID.disable();
		rightClimbPID.disable();
	}
	
	public void reset()
	{
		leftEncoder.reset();
		rightEncoder.reset();
	}
    
    /*public void changeMode(RobotMode mode) //true is climb, false is drive
    {
        if (mode == RobotMode.climbMode) {
			leftEncoder.setDistancePerPulse(Config.CLIMB_DPP);
			rightEncoder.setDistancePerPulse(Config.CLIMB_DPP);
            tabledownSolenoid.set(true);
            tableupSolenoid.set(false);
        } else {
			leftEncoder.setDistancePerPulse(Config.LE_DPP);
			rightEncoder.setDistancePerPulse(Config.RE_DPP);
            tabledownSolenoid.set(false);
            tableupSolenoid.set(true);
        }
	}
  */
	
    public void setWheelyOn()
    {
        wheelyBarDown.set(false);
        wheelyBarUp.set(true); 
    }
    public void setWheelyOff()
    {
        wheelyBarUp.set(false);
        wheelyBarDown.set(true);
    }
	public void pushOut() {
		shooterOut.set(true);
		shooterIn.set(false);
	}
	public void pullIn() {
		shooterOut.set(false);
		shooterIn.set(true);
	}
	
	public boolean getIsWheelyBarDown()
	{
		if (wheelyBarDown.get()) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setTablePosition(TilterMode position)
	{
		if (position == TilterMode.tiltup){
			tableupSolenoid.set(true);
			tabledownSolenoid.set(false);
		} else if (position == TilterMode.tiltdown){
			tableupSolenoid.set(false);
			tabledownSolenoid.set(true);
		} 
		
		
	}
}
