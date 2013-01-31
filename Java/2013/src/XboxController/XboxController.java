package XboxController;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.parsing.IInputOutput;

public class XboxController extends GenericHID implements IInputOutput
{
    final int kDefaultLXAxis = 1;
    final int kDefaultLYAxis = 2;
    final int kDefaultRXAxis = 3;
    final int kDefaultRYAxis = 4;
    final int kDefaultTriggerAxis = 5;
    
    public static class XboxButtons
    {
        public final int value;
        
        public static final XboxButtons kAButton = new XboxButtons(1);
        public static final XboxButtons kBButton = new XboxButtons(2);
        public static final XboxButtons kXButton = new XboxButtons(3);
        public static final XboxButtons kYButton = new XboxButtons(4);
        public static final XboxButtons kLBButton = new XboxButtons(5);
        public static final XboxButtons kRBButton = new XboxButtons(6);
        public static final XboxButtons kBackButton = new XboxButtons(7);
        public static final XboxButtons kStartButton = new XboxButtons(8);
        public static final XboxButtons kLeftButton = new XboxButtons(9);
        public static final XboxButtons kRightButton = new XboxButtons(10);
        
        
        public XboxButtons(int value)
        {
            this.value = value;
        }
    }
    
    public double getZ(GenericHID.Hand hand)
    {
        return 0.0;
    }

    public double getX(GenericHID.Hand hand) 
    {
       if (hand.value == GenericHID.Hand.kLeft.value)
           return m_ds.getStickAxis(m_port, m_axes[AxisType.kLX.value]);
       else
           return m_ds.getStickAxis(m_port, m_axes[AxisType.kLY.value]);
       
    }

    public double getY(GenericHID.Hand hand)
    {
        if (hand.value == GenericHID.Hand.kRight.value)
           return m_ds.getStickAxis(m_port, m_axes[AxisType.kRX.value]);
       else
           return m_ds.getStickAxis(m_port,m_axes[AxisType.kRY.value]);
    }

    public boolean getRawButton(XboxButtons button)       
    {
        return getRawButton(button.value);
    }
    
    public double getRawAxis(final int axis)
    {
        return m_ds.getStickAxis(m_port, axis);
    }

    public double getTwist()
    {
        return 0.0;
    }

    public double getThrottle()
    {
        return 0.0;
    }

    public double getRawAxis(AxisType axis)
    {
        return getRawAxis(axis.value);  
    }

    public boolean getTrigger(GenericHID.Hand hand)
    {
        return false;
    }

    public boolean getTop(GenericHID.Hand hand)
    {
        return false;
    }

    public boolean getBumper(GenericHID.Hand hand)
    {
        return false;
    }
    
    
    public static class AxisType
    {
        public final int value;
        
        static final int kNumAxis = 4;
        
        public static final AxisType kRY = new AxisType(0);
        public static final AxisType kRX = new AxisType(1);
        public static final AxisType kLY = new AxisType(2);
        public static final AxisType kLX = new AxisType(3);
        
        public AxisType(int value)
        
        {
            this.value = value;
        }
    }
        
        private DriverStation m_ds;
        private final int m_port;
        private final byte[] m_axes;
        
        public XboxController(final int port)
        {
            m_ds = DriverStation.getInstance();
            m_axes = new byte[AxisType.kNumAxis];
            m_port = port;
            
            
            m_axes[AxisType.kRY.value] = kDefaultRYAxis;
            m_axes[AxisType.kRX.value] = kDefaultRXAxis;
            m_axes[AxisType.kLY.value] = kDefaultLYAxis;
            m_axes[AxisType.kLX.value] = kDefaultLXAxis;
        }
        
        public boolean getRawButton(final int button)
        {
            int Bit = button - 1;
            int Shift = (0x1 << Bit);
            int Driver = m_ds.getStickButtons(m_port);
            return (Shift & Driver) != 0;
            
        }
        
        public double getAxis(AxisType axis)
        {
            return getRawAxis(axis.value); 
        }
}
    
    


    
