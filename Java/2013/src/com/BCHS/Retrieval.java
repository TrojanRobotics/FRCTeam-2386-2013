/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.BCHS;

import edu.wpi.first.wpilibj.Solenoid;
/**
 *
 * @author Laxman
 */
public class Retrieval 
{
	Solenoid openSolenoid, closeSolenoid;
	
	public Retrieval(int openChannel, int closeChannel)
	{
		openSolenoid = new Solenoid(openChannel);
		closeSolenoid = new Solenoid(closeChannel);
	}
	public void pushOut()
	{
		openSolenoid.set(true);
		closeSolenoid.set(false);
	}
	public void pullIn()
	{
		openSolenoid.set(false);
		closeSolenoid.set(true);
	}
}
