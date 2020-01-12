package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name = "TeleOp2020_2", group = "Sample")
public class TeleOp2020_2 extends LinearOpMode{

    //declare motors
    private DcMotor driveFLM;
    private DcMotor driveFRM;
    private DcMotor driveBLM;
    private DcMotor driveBRM;
    private DcMotor stoneLeftM;
    private DcMotor stoneRghtM;

    //declare swervos
    private Servo succLeftS;
    private Servo succRghtS;
    private Servo skystoneForeS;
    private Servo skystoneBackS;
    private Servo waffleForeS;
    private Servo waffleBackS;

    private double driveSpeed = 0.5;

    @Override
    public void
    runOpMode() {

        //configuration
        if (true) {
            //configure motors
            driveFLM   = hardwareMap.dcMotor.get("driveFLM");
            driveFRM   = hardwareMap.dcMotor.get("driveFRM");
            driveBLM   = hardwareMap.dcMotor.get("driveBLM");
            driveBRM   = hardwareMap.dcMotor.get("driveBRM");
            stoneLeftM = hardwareMap.dcMotor.get("stoneLeftM");
            stoneRghtM = hardwareMap.dcMotor.get("stoneRightM");

            //configure swervos
            succLeftS     = hardwareMap.servo.get("succLeftS");
            succRghtS     = hardwareMap.servo.get("succRightS");
            skystoneForeS = hardwareMap.servo.get("skystoneFrontS");
            skystoneBackS = hardwareMap.servo.get("skystoneBackS");
            waffleForeS   = hardwareMap.servo.get("waffleFrontS");
            waffleBackS   = hardwareMap.servo.get("waffleBackS");

            //set motor directions
            driveFLM.setDirection(DcMotor.Direction.FORWARD);
            driveFRM.setDirection(DcMotor.Direction.REVERSE);
            driveBLM.setDirection(DcMotor.Direction.FORWARD);
            driveBRM.setDirection(DcMotor.Direction.REVERSE);
            stoneLeftM.setDirection(DcMotor.Direction.REVERSE);
            stoneRghtM.setDirection(DcMotor.Direction.FORWARD);

            //set most motors to not use encoders
            driveFLM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            driveFRM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            driveBLM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            driveBRM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            stoneLeftM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            stoneRghtM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        }

        waitForStart();

        //move succ motors out at start of teleop
        succLeftS.setPosition(0.50);
        succRghtS.setPosition(0.54);

        while(opModeIsActive()) {

            //KV can change how fast he drives.
            if (gamepad1.dpad_down) {
                driveSpeed = 0.5;
            } else {
                if (gamepad1.dpad_up) {
                    driveSpeed = 1.0;
                }
            }

            //lets KV move the robot
            //left stick moves the robot without turning, it is driving and strafing
            //right stick x for spinning
            driveFLM.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x) * driveSpeed);
            driveFRM.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x) * driveSpeed);
            driveBLM.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x) * driveSpeed);
            driveBRM.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x) * driveSpeed);

            //KV can start, stop, and reverse the succ motors
            //pressing 'A' reactivates the stone distance sensor
            //this is because he HAS to turn on the succ motors to get another stone
            if (gamepad1.a) {
                stoneLeftM.setPower(0.75);
                stoneRghtM.setPower(0.75);
            } else {
                if (gamepad1.b) {
                    stoneLeftM.setPower(0);
                    stoneRghtM.setPower(0);
                } else {
                    if (gamepad1.y) {
                        stoneLeftM.setPower(-0.75);
                        stoneRghtM.setPower(-0.75);
                    }
                }
            }

            //KV can move the servos holding the succ motors in and out
            //if the stone sensor sees the stone, at 10 cm, itll stop the succ motors and move them in
            //'stoned' boolean is basically whether the sensor is active or not
            //so that KV can move the succ motors out and the sensor wont keep trying to pull it in
            if (gamepad1.right_bumper) {
                succLeftS.setPosition(0.50);
                succRghtS.setPosition(0.54);
            } else {
                if (gamepad1.left_bumper) {
                    succLeftS.setPosition(1);
                    succRghtS.setPosition(0);
                } else {
                    if (gamepad1.right_trigger > 0.1) {
                        succLeftS.setPosition(0.25);
                        succRghtS.setPosition(0.75);
                    }
                }
            }

            //Mike can use any of four buttons to move the two arms at the side
            //the "Plan B"
            //dpad up and down move the forward one, x and y move the back one.
            if (gamepad2.dpad_left) skystoneForeS.setPosition(0.5);
            if (gamepad2.dpad_right) skystoneForeS.setPosition(0.1);
            if (gamepad2.x) skystoneBackS.setPosition(0.0);
            if (gamepad2.y) skystoneBackS.setPosition(0.4);

            //KV can grab the waffle for Endgame.
            if (gamepad1.dpad_left) {
                waffleForeS.setPosition(0.00);
                waffleBackS.setPosition(1.00);
            } else {
                if (gamepad1.dpad_right) {
                    waffleForeS.setPosition(0.35);
                    waffleBackS.setPosition(0.62);
                }
            }
        }
    }
}
//0,1150,2250,3300,4450