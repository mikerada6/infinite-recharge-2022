// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants.AutoConstants;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public
class Robot extends TimedRobot {
  public static Trajectory forward;
  public static Trajectory backward;
  private final SendableChooser<String> autoChooser = new SendableChooser<>();
  private Command m_autonomousCommand;
  private RobotContainer m_robotContainer;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override

  public
  void robotInit() {
    autoChooser.setDefaultOption("Drive",
                                 AutoConstants.DRIVE);
    autoChooser.addOption("Simple Auto",
                          AutoConstants.SIMPLE_AUTO);
    autoChooser.addOption("Simple Auto Double Low",
                          AutoConstants.SIMPLE_AUTO_LOW_LOW);
    autoChooser.addOption("Simple Auto Double High",
                          AutoConstants.SIMPLE_AUTO_HIGH_HIGH);
    autoChooser.addOption("Double cargo low low",
                          AutoConstants.DOUBLE_CARGO_LOW_LOW);
    autoChooser.addOption("Double cargo low high",
                          AutoConstants.DOUBLE_CARGO_LOW_HIGH);
    SmartDashboard.putData("Auto Options",
                           autoChooser);
    try {
      forward = TrajectoryUtil.fromPathweaverJson(Filesystem
                                                    .getDeployDirectory()
                                                    .toPath()
                                                    .resolve("paths/output/SimpleForward.wpilib.json"));
    } catch (Exception e) {
      System.out.println("Can't read file");
    }
    try {
      backward = TrajectoryUtil.fromPathweaverJson(Filesystem
                                                     .getDeployDirectory()
                                                     .toPath()
                                                     .resolve("paths/output/SimpleForward.wpilib.json"));
    } catch (Exception e) {
      System.out.println("Can't read file");
    }

    CameraServer.startAutomaticCapture("cam0",
                                       0);
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public
  void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();


  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public
  void disabledInit() {
  }

  @Override
  public
  void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
   */
  @Override
  public
  void autonomousInit() {
    String temp = autoChooser.getSelected();
    m_autonomousCommand = m_robotContainer.getAutonomousCommand(temp);

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public
  void autonomousPeriodic() {
  }

  @Override
  public
  void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public
  void teleopPeriodic() {


  }

  @Override
  public
  void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public
  void testPeriodic() {
  }
}
