/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.BCHS;

import edu.wpi.first.wpilibj.Jaguar;
/**
 *
 * @author Laxman
 */
public class Retrieval 
{
	Jaguar retrievalJag;
	
	public Retrieval(int channel)
	{
		retrievalJag = new Jaguar(channel);
	}
	public void pushOut()
	{
		retrievalJag.set(1.0);
	}
	public void pullIn()
	{
		retrievalJag.set(-1.0);
	}
    public void Still()
    {
        retrievalJag.set(0.0);
    }  
	public String getPosition()
	{
		if (retrievalJag.getSpeed() == 1.0)
			return "Out";
		else
			return "In";
	}
    public void Limit()
    {
        
    }
}
