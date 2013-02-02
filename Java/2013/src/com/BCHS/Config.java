package com.BCHS;

public class Config
{
    static double LEFT_SIDE_ENCODER_DPP = 0.0;
	static double RIGHT_SIDE_ENCODER_DPP = 0.0;
	
	/*
	 * GenericHID Contants
	 */
	
	static final int Madcatz_JOYSTICK = 1;
	//static final int SECONDARY_JOYSTICK = 2;
	
	static double [] CHASIS_PID = {1.0, 0.0, 0.0};
	
	/*
	 * PWM Constants
	 */
	static final int[] LDRIVE = { 3, 4 };
	static final int[] RDRIVE = { 1, 2 };
	
	/*
	 * 
	 */
	static final int ULTRASONIC = 1;
	
	/*
	 * Digital Constants
	 */
	static final int[] LENCODER = { 3, 4 };
	static final int[] RENCODER = { 1, 2 };
	static final int[] SENCODER = { 5, 6 };
}
