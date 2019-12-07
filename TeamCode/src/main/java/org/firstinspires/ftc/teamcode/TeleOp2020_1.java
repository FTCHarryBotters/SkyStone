package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name = "TeleOp2020_1", group = "Sample")
public class TeleOp2020_1 extends LinearOpMode{

    //declare motors
    private DcMotor driveFLM;
    private DcMotor driveFRM;
    private DcMotor driveBLM;
    private DcMotor driveBRM;
    private DcMotor stoneLeftM;
    private DcMotor stoneRghtM;
    private DcMotor verticalM;

    //declare swervos
    private Servo succLeftS;
    private Servo succRghtS;
    private Servo armS;
    private Servo stoneS;
    private Servo skystoneForeS;
    private Servo skystoneBackS;
    private Servo waffleForeS;
    private Servo waffleBackS;

    //declare distance sensor/detector
    private DistanceSensor stoneDS;

    double drivespeed = 0.5;
    boolean stoned = false;

    boolean stopdown = false;

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
            verticalM  = hardwareMap.dcMotor.get("verticalM");

            //configure swervos
            succLeftS     = hardwareMap.servo.get("succLeftS");
            succRghtS     = hardwareMap.servo.get("succRightS");
            armS          = hardwareMap.servo.get("armS");
            stoneS        = hardwareMap.servo.get("stoneS");
            skystoneForeS = hardwareMap.servo.get("skystoneFrontS");
            skystoneBackS = hardwareMap.servo.get("skystoneBackS");
            waffleForeS   = hardwareMap.servo.get("waffleFrontS");
            waffleBackS   = hardwareMap.servo.get("waffleBackS");

            //configure distance detector
            stoneDS = hardwareMap.get(DistanceSensor.class, "stoneDS");

            //set motor directions
            driveFLM.setDirection(DcMotor.Direction.FORWARD);
            driveFRM.setDirection(DcMotor.Direction.REVERSE);
            driveBLM.setDirection(DcMotor.Direction.FORWARD);
            driveBRM.setDirection(DcMotor.Direction.REVERSE);
            stoneLeftM.setDirection(DcMotor.Direction.REVERSE);
            stoneRghtM.setDirection(DcMotor.Direction.FORWARD);
            verticalM.setDirection(DcMotor.Direction.FORWARD);

            //set most motors to not use encoders
            driveFLM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            driveFRM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            driveBLM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            driveBRM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            stoneLeftM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            stoneRghtM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            //set vertical slide to using encoders.
            verticalM.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        waitForStart();

        //vertical encoder
        verticalM.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        verticalM.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //move succ motors out at start of teleop
        succLeftS.setPosition(0.47);
        succRghtS.setPosition(0.57);

        while(opModeIsActive()) {

            //telemetry for the distance sensor for stone
            //and for the encoder value of the vertical pulley
            telemetry.addData("is stone", stoneDS.getDistance(DistanceUnit.CM));
            telemetry.addData("vertical tix", verticalM.getCurrentPosition());
            telemetry.update();

            //KV can change how fast he drives.
            if (gamepad1.dpad_down) {
                drivespeed = 0.5;
            } else {
                if (gamepad1.dpad_up) {
                    drivespeed = 1.0;
                }
            }

            //lets KV move the robot
            //left stick moves the robot without turning, it is driving and strafing
            //right stick x for spinning
            driveFLM.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x) * drivespeed);
            driveFRM.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x) * drivespeed);
            driveBLM.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x) * drivespeed);
            driveBRM.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x) * drivespeed);

            //KV can start, stop, and reverse the succ motors
            //pressing 'A' reactivates the stone distance sensor
            //this is because he HAS to turn on the succ motors to get another stone
            if (gamepad1.a) {
                stoneLeftM.setPower(1);
                stoneRghtM.setPower(1);
                stoned = true;
            } else {
                if (gamepad1.b) {
                    stoneLeftM.setPower(0);
                    stoneRghtM.setPower(0);
                } else {
                    if (gamepad1.y) {
                        stoneLeftM.setPower(-1);
                        stoneRghtM.setPower(-1);
                    }
                }
            }

            //Mike controls the vertical pulleys using bumpers.
            //the encoders are used as limits so he cant loosen the string
            //0 ticks is the bottom because it starts there
            //4350 was determined through testing.
            if (gamepad2.right_bumper && verticalM.getCurrentPosition() < 4350) {
                verticalM.setPower(0.5);
            } else {
                if (gamepad2.left_bumper && verticalM.getCurrentPosition() > 0) {
                    verticalM.setPower(-0.5);
                } else {
                    verticalM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                    verticalM.setPower(0);
                    verticalM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                }
            }

            //KV can move the servos holding the succ motors in and out
            //if the stone sensor sees the stone, at 10 cm, itll stop the succ motors and move them in
            //'stoned' boolean is basically whether the sensor is active or not
            //so that KV can move the succ motors out and the sensor wont keep trying to pull it in
            if (gamepad1.right_bumper) {
                succLeftS.setPosition(0.47);
                succRghtS.setPosition(0.57);
            } else {
                if (gamepad1.left_bumper) {
                    succLeftS.setPosition(1);
                    succRghtS.setPosition(0);
                } else {
                    if (gamepad1.right_trigger > 0.1) {
                        succLeftS.setPosition(0.25);
                        succRghtS.setPosition(0.75);
                    } else {
                        if (stoneDS.getDistance(DistanceUnit.CM) < 10 && stoned) {
                            stoneLeftM.setPower(0);
                            stoneRghtM.setPower(0);
                            succLeftS.setPosition(1);
                            succRghtS.setPosition(0);
                            stoned = false;
                        }
                    }
                }
            }

            //Mike moves the arm sideways. it is continuous and moves slowly
            if (gamepad2.right_trigger > 0.1) {
                armS.setPosition(0.4);
            } else {
                if (gamepad2.left_trigger > 0.1) {
                    armS.setPosition(0.6);
                } else {
                    armS.setPosition(0.5);
                }
            }

            //Mike grabs or lets go of the stone at the end of the arm
            if (gamepad2.b) {
                stoneS.setPosition(0.5);
            } else {
                if (gamepad2.a) {
                    stoneS.setPosition(1.0);
                }
            }

            //Mike can use any of four buttons to move the two arms at the side
            //the "Plan B"
            //dpad up and down move the forward one, x and y move the back one.
            if (gamepad2.dpad_down) skystoneForeS.setPosition(0.5);
            if (gamepad2.dpad_up) skystoneForeS.setPosition(0.1);
            if (gamepad2.x) skystoneBackS.setPosition(0.0);
            if (gamepad2.y) skystoneBackS.setPosition(0.4);

            //KV can grab the waffle for Endgame.
            if (gamepad1.dpad_left) {
                waffleForeS.setPosition(0.70);
                waffleBackS.setPosition(0.13);
            } else {
                if (gamepad1.dpad_right) {
                    waffleForeS.setPosition(0.32);
                    waffleBackS.setPosition(0.55);
                }
            }

            //Automatic function for the vertical pulley
            //basically we just do a normal ecnoder moving like in autonomous to 0 ticks
            //this entire program is in the while loop so that we can still use the robot
            //as it is moving down, we dont have to just wait.
            if (gamepad2.left_stick_button) {
                verticalM.setTargetPosition(0);
                verticalM.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                verticalM.setPower(-0.5);

                while (verticalM.isBusy() && !stopdown) {

                    telemetry.addData("is stone", stoneDS.getDistance(DistanceUnit.CM));
                    telemetry.addData("vertical tix", verticalM.getCurrentPosition());
                    telemetry.update();

                    if (gamepad1.dpad_down) {
                        drivespeed = 0.5;
                    } else {
                        if (gamepad1.dpad_up) {
                            drivespeed = 1.0;
                        }
                    }

                    //lets KV move the robot
                    //left stick moves the robot without turning, it is driving and strafing
                    //right stick x for spinning
                    driveFLM.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x) * drivespeed);
                    driveFRM.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x) * drivespeed);
                    driveBLM.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x) * drivespeed);
                    driveBRM.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x) * drivespeed);

                    //KV can start and stop the succ motors
                    if (gamepad1.a) {
                        stoneLeftM.setPower(1);
                        stoneRghtM.setPower(1);
                        stoned = true;
                    } else {
                        if (gamepad1.b) {
                            stoneLeftM.setPower(0);
                            stoneRghtM.setPower(0);
                        } else {
                            if (gamepad1.y) {
                                stoneLeftM.setPower(-1);
                                stoneRghtM.setPower(-1);
                            }
                        }
                    }

                    //KV can move the servos holding the succ motors in and out
                    if (gamepad1.right_bumper) {
                        succLeftS.setPosition(0.47);
                        succRghtS.setPosition(0.57);
                    } else {
                        if (gamepad1.left_bumper) {
                            succLeftS.setPosition(1);
                            succRghtS.setPosition(0);
                        } else {
                            if (gamepad1.right_trigger > 0.1) {
                                succLeftS.setPosition(0.25);
                                succRghtS.setPosition(0.75);
                            } else {
                                if (stoneDS.getDistance(DistanceUnit.CM) < 10 && stoned) {
                                    stoneLeftM.setPower(0);
                                    stoneRghtM.setPower(0);
                                    succLeftS.setPosition(1);
                                    succRghtS.setPosition(0);
                                    stoned = false
                                    ;
                                }
                            }
                        }
                    }

                    //moves sideways
                    if (gamepad2.right_trigger > 0.1) {
                        armS.setPosition(0.4);
                    } else {
                        if (gamepad2.left_trigger > 0.1) {
                            armS.setPosition(0.6);
                        } else {
                            armS.setPosition(0.5);
                        }
                    }

                    //Mike grabs or lets go of the stone at the end of the arm
                    if (gamepad2.b) {
                        stoneS.setPosition(0.5);
                    } else {
                        if (gamepad2.a) {
                            stoneS.setPosition(1.0);
                        }
                    }

                    if (gamepad2.dpad_down) skystoneForeS.setPosition(0.5);
                    if (gamepad2.dpad_up) skystoneForeS.setPosition(0.1);
                    if (gamepad2.x) skystoneBackS.setPosition(0.0);
                    if (gamepad2.y) skystoneBackS.setPosition(0.4);


                    if (gamepad1.dpad_left) {
                        waffleForeS.setPosition(0.70);
                        waffleBackS.setPosition(0.13);
                    } else {
                        if (gamepad1.dpad_right) {
                            waffleForeS.setPosition(0.32);
                            waffleBackS.setPosition(0.55);
                        }
                    }
                }

                stopdown = false;
                verticalM.setPower(0);
                verticalM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                verticalM.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
        }
    }
}
