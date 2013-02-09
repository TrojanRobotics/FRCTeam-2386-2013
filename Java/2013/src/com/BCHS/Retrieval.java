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
	Solenoid solenoid;
	
	public Retrieval(int channel)
	{
		solenoid = new Solenoid(channel);
	}
	public void pushOut()
	{
		solenoid.set(true);
	}
	public void pullIn()
	{
		solenoid.set(false);
	}
	
    public void Limit()
    {
        
    }
}
