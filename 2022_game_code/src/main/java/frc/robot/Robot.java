// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
// import edu.wpi.first.wpilibj.Compressor;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.DoubleSolenoid.*;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;





/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private final XboxController m_sticks = new XboxController(0);
  // private final GamepadVelocities gpv = new GamepadVelocities();

  //private final DoubleSolenoid p_clamp = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 1, 2);
  //private final DoubleSolenoid p_inatke = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 1, 2);
  //private final DoubleSolenoid p_climb = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 1, 2);

  private final WPI_VictorSPX m_conveyor = new WPI_VictorSPX(7);

  private final WPI_TalonFX m_f1 = new  WPI_TalonFX(1);
  private final WPI_TalonFX m_f2 = new  WPI_TalonFX(2);
  private final WPI_TalonFX m_f3 = new  WPI_TalonFX(3);
  private final WPI_TalonFX m_f4 = new  WPI_TalonFX(4);
  private final WPI_TalonFX m_fGreen = new  WPI_TalonFX(5);
  private final WPI_TalonFX m_fBlack = new  WPI_TalonFX(6);
  
  private final MotorControllerGroup m_left = new MotorControllerGroup(m_f1, m_f2);
  private final MotorControllerGroup m_right = new MotorControllerGroup(m_f3, m_f4);
  
  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_left,m_right);

  private final DoubleSolenoid s_shifters = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0,1);
  private final DoubleSolenoid s_clamp = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 2,3);
  private final DoubleSolenoid s_arms = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 4,5);
  private final DoubleSolenoid s_intake = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 6,7);
  double outputGreen = 0;
  double outputBlack = 0;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    m_left.setInverted(false);
    UsbCamera camera = CameraServer.startAutomaticCapture();
    // Set the resolution
    camera.setResolution(640, 480);

    
    //pcmCompressor.enableDigital();

  }
// Sebastian was here
  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);

  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {

  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

    // left trigger moves backwards
    // right trigger moves forwards
    //
    // 0 -> standstill
    // 1 -> forward
    // -1 -> backward
    //
    // backwards
    double power = m_sticks.getLeftTriggerAxis() * -1;
    // fowards
    power = power + m_sticks.getRightTriggerAxis();

    // controller inputs
    m_robotDrive.arcadeDrive(power,m_sticks.getRightX());

    // pnuematics 
    if (m_sticks.getAButtonReleased()) {

    }
    if (m_sticks.getBButtonReleased()) {
      s_shifters.set(Value.kForward);
    }
    if (m_sticks.getXButtonReleased()) {
      s_shifters.set(Value.kReverse);
    }
    if (m_sticks.getYButtonReleased()) {

    }
    if (m_sticks.getRightBumperReleased()) {
      
    }
    if (m_sticks.getLeftBumperReleased()) {
      
    }
    if (m_sticks.getLeftTriggerAxis() > .5) {
      
    }
    if (m_sticks.getRightTriggerAxis() > .5) {
      
    }

    // shooter motors

    // these are speed constants
    // we can use them to debug shot distance and shot arc

    // The motors below represent the black and green motors
    // on our shooter.
    //
    // set one motor to a constant value from above,
    // then run the other motor at full speed 0/1.
    //



    // experimental value
    m_fGreen.set(ControlMode.PercentOutput, outputGreen);

    // responds to controller
    m_fBlack.set(ControlMode.PercentOutput, outputBlack);
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}

class GamepadVelocities{
  private double threreshold = 0.5;
  
  public double getTargetVelocity(double inputrange) {
    if (inputrange < this.threreshold) { 
      return 1;
    }

    return 0;
  }
}
