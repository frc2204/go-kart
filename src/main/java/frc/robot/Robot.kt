package frc.robot

import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj.motorcontrol.Spark
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.CommandScheduler

/**
 * The VM is configured to automatically run this object (which basically functions as a singleton class),
 * and to call the functions corresponding to each mode, as described in the TimedRobot documentation.
 * This is written as an object rather than a class since there should only ever be a single instance, and
 * it cannot take any constructor arguments. This makes it a natural fit to be an object in Kotlin.
 *
 * If you change the name of this object or its package after creating this project, you must also update
 * the `Main.kt` file in the project. (If you use the IDE's Rename or Move refactorings when renaming the
 * object or package, it will get changed everywhere.)
 */
object Robot : TimedRobot()
{

    private var autonomousCommand: Command? = null

    private lateinit var driveController: XboxController
    private lateinit var drive: DifferentialDrive
    private const val driveDeadBand: Double = 0.05

    override fun robotInit()
    {
        val leftMotorController = Spark(0)
        val rightMotorController = Spark(1)
        drive = DifferentialDrive(leftMotorController, rightMotorController)
        drive.setDeadband(driveDeadBand)
        driveController = XboxController(0)

        // Access the RobotContainer object so that it is initialized. This will perform all our
        // button bindings, and put our autonomous chooser on the dashboard.
        RobotContainer
    }


    override fun robotPeriodic()
    {
        CommandScheduler.getInstance().run()
    }

    override fun disabledInit()
    {

    }

    override fun disabledPeriodic()
    {

    }

    override fun autonomousInit()
    {
        autonomousCommand = RobotContainer.getAutonomousCommand()
        autonomousCommand?.schedule()
    }

    override fun autonomousPeriodic()
    {

    }

    override fun teleopInit()
    {
        autonomousCommand?.cancel()
    }

    /** This method is called periodically during operator control.  */
    override fun teleopPeriodic()
    {
        val forwardSpeed: Double = driveController.getRawAxis(1)
        val rotation: Double = driveController.getRawAxis(0)
        drive.arcadeDrive(forwardSpeed, rotation, true)
    }

    override fun testInit()
    {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll()
    }

    override fun testPeriodic()
    {

    }

    override fun simulationInit()
    {

    }

    override fun simulationPeriodic()
    {

    }
}