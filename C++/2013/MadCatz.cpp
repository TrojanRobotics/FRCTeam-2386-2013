#include "MadCatz.h"
#include "DriverStation.h"
#include "NetworkCommunication/UsageReporting.h"
#include "WPIErrors.h"
#include <math.h>

//axis for controller
const UINT32 MadCatz::kDefaultLXAxis;
const UINT32 MadCatz::kDefaultLYAxis;
const UINT32 MadCatz::kDefaultRXAxis;
const UINT32 MadCatz::kDefaultRYAxis;
const UINT32 MadCatz::kDefaultLTAxis;
const UINT32 MadCatz::kDefaultRTAxis;
const UINT32 MadCatz::kDefaultPadX;
const UINT32 MadCatz::kDefaultPadY;
static MadCatz *joysticks[DriverStation::kJoystickPorts];
static bool joySticksInitialized = false;

MadCatz::MadCatz(UINT32 port)
	: m_ds (NULL)
	, m_port(port)
	, m_axes (NULL)
	, m_buttons (NULL)
{
	InitMadCatz(kNumAxisTypes, kNumButtonTypes);
	
	m_axes[kLXAxis] = kDefaultLXAxis;
	m_axes[kLYAxis] = kDefaultLYAxis;
	m_axes[kRXAxis] = kDefaultRXAxis;
	m_axes[kRYAxis] = kDefaultRYAxis;
	m_axes[kLTAxis] = kDefaultLTAxis;
	m_axes[kRTAxis] = kDefaultRTAxis;
	m_axes[kPadX] = kDefaultPadX;
	m_axes[kPadY] = kDefaultPadY;
	
	m_buttons[kAButton] = kDefaultAButton;
	m_buttons[kBButton] = kDefaultBButton;
	m_buttons[kXButton] = kDefaultXButton;
	m_buttons[kYButton] = kDefaultYButton;
	m_buttons[kLBump] = kDefaultLBump;
	m_buttons[kRBump] = kDefaultRBump;
	m_buttons[kStart] = kDefaultStart;
	m_buttons[kBack] = kDefaultBack;
	m_buttons[kLStick] = kDefaultLStick;
	m_buttons[kRStick] = kDefaultRStick;
	
	nUsageReporting::report(nUsageReporting::kResourceType_Joystick, port);
}

MadCatz::MadCatz(UINT32 port, UINT32 numAxisTypes, UINT32 numButtonTypes)
	: m_ds (NULL)
	, m_port (port)
	, m_axes (NULL)
	, m_buttons (NULL)
{
	InitMadCatz(numAxisTypes, numButtonTypes);
}

void MadCatz::InitMadCatz(UINT32 numAxisTypes, UINT32 numButtonTypes)
{
	if (!joySticksInitialized)
	{
		for (unsigned i = 0; i < DriverStation::kJoystickPorts; i++) {
			joysticks[i] = NULL;
		}
		joySticksInitialized = true;
	}
	joysticks[m_port - 1] = this;
	
	m_ds = DriverStation::GetInstance();
	m_axes = new UINT32[numAxisTypes];
	m_buttons = new UINT32[numButtonTypes];
}

MadCatz * MadCatz::GetStickForPort(UINT32 port)
{
	MadCatz *stick = joysticks[port - 1];
	if (stick == NULL) {
		stick = new MadCatz(port);
		joysticks[port - 1] = stick;
	}
	return stick;
}

MadCatz::~MadCatz()
{
	delete [] m_buttons;
	delete [] m_axes;
}

float MadCatz::GetX(JoystickHand hand)
{
	if (hand == GenericHID::kLeftHand) {
		return GetRawAxis(m_axes[kLXAxis]);
	} else {
		return GetRawAxis(m_axes[kRXAxis]);
	}
}

float MadCatz::GetY(JoystickHand hand)
{
	if (hand == GenericHID::kLeftHand) {
		return GetRawAxis(m_axes[MadCatz::kLYAxis]);
	} else {
		return GetRawAxis(m_axes[kRYAxis]);
	}
}

float MadCatz::GetZ()
{
	return 0.0;
}

float MadCatz::GetThrottle()
{
	return 0.0;
}

float MadCatz::GetTwist()
{
	return 0.0;
}

float MadCatz::GetRawAxis(UINT32 axis)
{
	return m_ds->GetStickAxis(m_port, axis);
}

float MadCatz::GetAxis(AxisType axis)
{
	return GetRawAxis(m_axes[(int)axis]);
}

bool MadCatz::GetTrigger(JoystickHand hand)
{
	return false;
}

bool MadCatz::GetRawButton(UINT32 button)
{
	return ((0x1 << (button-1)) & m_ds->GetStickButtons(m_port)) != 0;
}

bool MadCatz::GetButton(ButtonType button)
{
	return GetRawAxis(m_buttons[button]);
}

bool MadCatz::GetBumper(JoystickHand hand)
{
	return false;
}

bool MadCatz::GetTop(JoystickHand hand)
{
	return false;
}
