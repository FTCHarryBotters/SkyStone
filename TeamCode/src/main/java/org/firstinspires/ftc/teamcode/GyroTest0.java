package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.util.Locale;

@Autonomous(name = "GyroTest0", group = "Sample")
public class GyroTest0 extends LinearOpMode {

    //declare motors
    private DcMotor driveFLM;
    private DcMotor driveFRM;
    private DcMotor driveBLM;
    private DcMotor driveBRM;

    // The IMU sensor object
    BNO055IMU imu;

    // State used for updating telemetry
    Orientation angles;
    Orientation             lastAngles = new Orientation();
    double                  globalAngle, power = .30, correction;

    @Override
    public void runOpMode() throws InterruptedException {

        //initialize motors
        driveFLM = hardwareMap.dcMotor.get("driveFLM");
        driveFRM = hardwareMap.dcMotor.get("driveFRM");
        driveBLM = hardwareMap.dcMotor.get("driveBLM");
        driveBRM = hardwareMap.dcMotor.get("driveBRM");

        //set motor directions
        driveFLM.setDirection(DcMotor.Direction.FORWARD);
        driveFRM.setDirection(DcMotor.Direction.REVERSE);
        driveBLM.setDirection(DcMotor.Direction.FORWARD);
        driveBRM.setDirection(DcMotor.Direction.REVERSE);

        //set motors for encoders
        driveFLM.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveFRM.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveBLM.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveBRM.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        // Wait until we're told to go
        waitForStart();

        resetAngle();
        rotate(90, 0.2);

    }
    //methods
    private void spinLeftG90(){

        driveFLM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveFRM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveBLM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveBRM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //spinLeft
        driveFLM.setPower(-.5);
        driveFRM.setPower(0.5);
        driveBLM.setPower(-.5);
        driveBRM.setPower(0.5);

        Orientation angles1 = imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
        while (angles1.secondAngle < 90) {
            angles1 = imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
            telemetry.addData("bruh", angles1.firstAngle);
            telemetry.addData("bruh", angles1.secondAngle);
            telemetry.addData("bruh", angles1.thirdAngle);
            telemetry.update();
        }

        //stopMoving();
        driveFLM.setPower(0);
        driveFRM.setPower(0);
        driveBLM.setPower(0);
        driveBRM.setPower(0);

        driveFLM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driveFRM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driveBLM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driveBRM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        driveFLM.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveFRM.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveBLM.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveBRM.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    private void resetAngle()
    {
        lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

        globalAngle = 0;
    }
    private double getAngle()
    {
        // We experimentally determined the Z axis is the axis we want to use for heading angle.
        // We have to process the angle because the imu works in euler angles so the Z axis is
        // returned as 0 to +180 or 0 to -180 rolling back to -179 or +179 when rotation passes
        // 180 degrees. We detect this transition and track the total cumulative angle of rotation.

        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

        double deltaAngle = angles.secondAngle - lastAngles.secondAngle;

        if (deltaAngle < -180)
            deltaAngle += 360;
        else if (deltaAngle > 180)
            deltaAngle -= 360;

        globalAngle += deltaAngle;

        lastAngles = angles;

        return globalAngle;
    }
    private void rotate(int degrees, double power)
    {
        double  leftPower, rightPower;

        // restart imu movement tracking.
        resetAngle();

        // getAngle() returns + when rotating counter clockwise (left) and - when rotating
        // clockwise (right).

        if (degrees < 0)
        {   // turn right.
            leftPower = power;
            rightPower = -power;
        }
        else if (degrees > 0)
        {   // turn left.
            leftPower = -power;
            rightPower = power;
        }
        else return;


        //spinLeft
        driveFLM.setPower(leftPower);
        driveFRM.setPower(rightPower);
        driveBLM.setPower(leftPower);
        driveBRM.setPower(rightPower);


        // rotate until turn is completed.
        if (degrees < 0)
        {
            // On right turn we have to get off zero first.
            while (opModeIsActive() && getAngle() == 0) {
                getAngle();
                telemetry.update();
            }

            while (opModeIsActive() && getAngle() > degrees) {
                getAngle();
                telemetry.update();
            }
        }
        else    // left turn.
            while (opModeIsActive() && getAngle() < degrees) {
                getAngle();
                telemetry.addData("global angle", globalAngle);
                telemetry.update();
            }

        //stopMoving();
        driveFLM.setPower(0);
        driveFRM.setPower(0);
        driveBLM.setPower(0);
        driveBRM.setPower(0);

        driveFLM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driveFRM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driveBLM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driveBRM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        driveFLM.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveFRM.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveBLM.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveBRM.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // wait for rotation to stop.
        sleep(1000);

        // reset angle tracking on new heading.
        resetAngle();
    }
    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    String formatDegrees(double degrees){
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }

}
