package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name = "AutoBluStone1", group = "Sample")
public class AutoBluStone1 extends LinearOpMode {

    //declare motors
    private boolean isSkystone = false;
    private int tries = 0;
    private int robotWhere;

    private DcMotor driveFLM;
    private DcMotor driveFRM;
    private DcMotor driveBLM;
    private DcMotor driveBRM;

    //declare servos
    private Servo skystoneForeS;
    private Servo skystoneBackS;

    //declare distance sensors (DS)
    private DistanceSensor wallLCDS;
    private DistanceSensor wallLBDS;
    private DistanceSensor wallRearDS;
    
    //declare color sensor (CS) and related variables
    private ColorSensor stoneForeCS;
    float hsvValues[] = {0F, 0F, 0F};
    final float values[] = hsvValues;
    final double SCALE_FACTOR = 255;
    int relativeLayoutId;
    View relativeLayout;

    @Override
    public void runOpMode() throws InterruptedException {

        if (true) {
            //change screen color
            relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
            relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

            //initialize motors
            driveFLM = hardwareMap.dcMotor.get("driveFLM");
            driveFRM = hardwareMap.dcMotor.get("driveFRM");
            driveBLM = hardwareMap.dcMotor.get("driveBLM");
            driveBRM = hardwareMap.dcMotor.get("driveBRM");

            //initialize servos
            skystoneForeS = hardwareMap.servo.get("skystoneFrontS");
            skystoneBackS = hardwareMap.servo.get("skystoneBackS");

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

            //initialize distance sensors
            wallLCDS = hardwareMap.get(DistanceSensor.class, "robotDS");
            wallLBDS = hardwareMap.get(DistanceSensor.class, "wallDS");
            wallRearDS = hardwareMap.get(DistanceSensor.class, "wallRearDS");

            //initialize Color sensor
            stoneForeCS = hardwareMap.colorSensor.get("stoneFrontCS");
        }

        waitForStart();
        //what runs

        moveRghtE(0.5, 1200);
        checkStones();

        if (robotWhere == 0) {
            driveForwardE(0.5, 200,0);
            skystoneForeS.setPosition(0.4);
            Thread.sleep(1500);
            moveLeftE(0.5, 700);

            driveForwardE(0.5, 2500, 0.1);
            moveRghtE(0.5, 500);
            skystoneForeS.setPosition(0);
            Thread.sleep(500);
            moveLeftE(0.5, 500);
            driveBackwardE(0.5, 2950);

            alignLeft(0.2, 33);
            moveRghtE(0.5, 300);
            alignLeft(0.2, 33);

            moveRghtE(0.5, 900);
            skystoneBackS.setPosition(0.6);
            Thread.sleep(1500);
            moveLeftE(0.5, 700);

            driveForwardE(0.5, 3100, 0);
            moveRghtE(0.5, 500);
            skystoneBackS.setPosition(1);
            Thread.sleep(500);
            moveLeftE(0.5, 400);
            driveBackwardE(0.5, 1000);
            moveRghtE(0.5, 500);
        }
        if (robotWhere == 1) {
            driveForwardE(0.5, 200,0);
            skystoneForeS.setPosition(0.4);
            Thread.sleep(1500);
            moveLeftE(0.5, 700);

            driveForwardE(0.5, 2900, 0.1);
            moveRghtE(0.5, 500);
            skystoneForeS.setPosition(0);
            Thread.sleep(500);
            moveLeftE(0.5, 500);
            driveBackwardE(0.5, 3250);

            alignLeft(0.2, 33);
            moveRghtE(0.5, 300);
            alignLeft(0.2, 33);

            moveRghtE(0.5, 900);
            skystoneBackS.setPosition(0.6);
            Thread.sleep(1500);
            moveLeftE(0.5, 700);

            driveForwardE(0.5, 3500, 0);
            moveRghtE(0.5, 500);
            skystoneBackS.setPosition(1);
            Thread.sleep(500);
            moveLeftE(0.5, 400);
            driveBackwardE(0.5, 1000);
            moveRghtE(0.5, 500);
        }
        if (robotWhere == 2) {
            driveForwardE(0.5, 200,0);
            skystoneForeS.setPosition(0.4);
            Thread.sleep(1500);
            moveLeftE(0.5, 700);

            driveForwardE(0.5, 3300, 0.1);
            moveRghtE(0.5, 500);
            skystoneForeS.setPosition(0);
            Thread.sleep(500);
            moveLeftE(0.5, 500);
            driveBackwardE(0.5, 3750);

            alignLeft(0.2, 33);
            moveRghtE(0.5, 300);
            alignLeft(0.2, 33);

            moveRghtE(0.5, 900);
            skystoneBackS.setPosition(0.6);
            Thread.sleep(1500);
            moveLeftE(0.5, 700);

            driveForwardE(0.5, 3900, 0);
            moveRghtE(0.5, 500);
            skystoneBackS.setPosition(1);
            Thread.sleep(500);
            moveLeftE(0.5, 400);
            driveBackwardE(0.5, 1000);
            moveRghtE(0.5, 500);
        }
        

    }
    public void alignLeft(double power, int distanceCM) {
        double distC=wallLCDS.getDistance(DistanceUnit.CM);//saves distance measurement
        double distB=wallLBDS.getDistance(DistanceUnit.CM);//saves distance measurement
        
        
        while (distC>distanceCM || distB>distanceCM) {       //checks if we've reached dist from wall
            
            distC = wallLCDS.getDistance(DistanceUnit.CM); //re-saves distance measurement
            distB = wallLBDS.getDistance(DistanceUnit.CM); //re-saves distance measurement
            
            telemetry.addData("FLS", distC);
            telemetry.addData("BLS", distB);
            telemetry.update();
            
            if (distC>distanceCM) {                               //checks if we've reached distance
                driveFLM.setPower(-power);                       //moves motors
                driveFRM.setPower(+power);                       //moves motors
            } else {
                driveFLM.setPower(0);
                driveFRM.setPower(0);
            }
            
            if (distB>distanceCM) {                               //checks if we've reached distance
                driveBLM.setPower(+power);                       //moves motors
                driveBRM.setPower(-power);                       //moves motors
            } else {
                driveBLM.setPower(0);
                driveBRM.setPower(0);
            }
        }
    }
    private void getSkystone() {

        Color.RGBToHSV(
        (int) (stoneForeCS.red() * SCALE_FACTOR),
        (int) (stoneForeCS.green() * SCALE_FACTOR),
        (int) (stoneForeCS.blue() * SCALE_FACTOR),
        hsvValues);

        telemetry.addData("Hoo", hsvValues[0]);
        telemetry.addData("Satchurashun", hsvValues[1]);
        telemetry.addData("Valyoo", hsvValues[2]);
        telemetry.update();

        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
            }
        });

        if (hsvValues[0]>120) {isSkystone = true;}
    }
    private void checkStones() {
        while (!isSkystone&&tries<2) {
            tries++;
            robotWhere = 0;
            getSkystone();
            if (!isSkystone) {
                driveBackwardE(0.3, 400);
                robotWhere = 1;
                getSkystone();
                if (!isSkystone) {
                    driveBackwardE(0.3, 400);
                    robotWhere = 2;
                    getSkystone();
                    if (!isSkystone) {
                        driveForwardE(0.3, 700, 0);
                        robotWhere = 0;
                    }
                }
            }
        }
    }
    public void driveForwardE(double power, int ticks, double steer) {
        //Reset Encoders
        driveFLM.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveFRM.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveBLM.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveBRM.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //set target position
        driveFLM.setTargetPosition(ticks);
        driveFRM.setTargetPosition(ticks);
        driveBLM.setTargetPosition(ticks);
        driveBRM.setTargetPosition(ticks);

        //set ot RUN_TO_POSITION mode
        driveFLM.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveFRM.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveBLM.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveBRM.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //driveForward
        driveFLM.setPower(power);
        driveFRM.setPower(power+steer);
        driveBLM.setPower(power);
        driveBRM.setPower(power+steer);

        //wait until target position
        while (driveFLM.isBusy() && driveFRM.isBusy() && driveBLM.isBusy() && driveBRM.isBusy())
        {

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
    public void driveBackwardE(double power, int ticks) {
        //Reset Encoders
        driveFLM.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveFRM.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveBLM.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveBRM.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //set target position
        driveFLM.setTargetPosition(-ticks);
        driveFRM.setTargetPosition(-ticks);
        driveBLM.setTargetPosition(-ticks);
        driveBRM.setTargetPosition(-ticks);

        //set ot RUN_TO_POSITION mode
        driveFLM.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveFRM.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveBLM.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveBRM.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //driveBackward
        driveFLM.setPower(power);
        driveFRM.setPower(power);
        driveBLM.setPower(power);
        driveBRM.setPower(power);

        //wait until target position
        while (driveFLM.isBusy() && driveFRM.isBusy() && driveBLM.isBusy() && driveBRM.isBusy())
        {

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
    public void spinLeftE(double power, int ticks) {
        //Reset Encoders
        driveFLM.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveFRM.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveBLM.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveBRM.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //set target position
        driveFLM.setTargetPosition(-ticks);
        driveFRM.setTargetPosition(ticks);
        driveBLM.setTargetPosition(-ticks);
        driveBRM.setTargetPosition(ticks);

        //set ot RUN_TO_POSITION mode
        driveFLM.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveFRM.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveBLM.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveBRM.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //spinLeft
        driveFLM.setPower(power);
        driveFRM.setPower(power);
        driveBLM.setPower(power);
        driveBRM.setPower(power);

        //wait until target position
        while (driveFLM.isBusy() && driveFRM.isBusy() && driveBLM.isBusy() && driveBRM.isBusy())
        {

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
    public void spinRghtE(double power, int ticks) {
        //Reset Encoders
        driveFLM.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveFRM.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveBLM.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveBRM.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //set target position
        driveFLM.setTargetPosition(ticks);
        driveFRM.setTargetPosition(-ticks);
        driveBLM.setTargetPosition(ticks);
        driveBRM.setTargetPosition(-ticks);

        //set ot RUN_TO_POSITION mode
        driveFLM.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveFRM.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveBLM.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveBRM.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //spinRight
        driveFLM.setPower(power);
        driveFRM.setPower(power);
        driveBLM.setPower(power);
        driveBRM.setPower(power);

        //wait until target position
        while (driveFLM.isBusy() && driveFRM.isBusy() && driveBLM.isBusy() && driveBRM.isBusy())
        {

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
    public void moveLeftE(double power, int ticks) {
        //Reset Encoders
        driveFLM.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveFRM.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveBLM.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveBRM.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //set target position
        driveFLM.setTargetPosition(-ticks);
        driveFRM.setTargetPosition(ticks);
        driveBLM.setTargetPosition(ticks);
        driveBRM.setTargetPosition(-ticks);

        //set ot RUN_TO_POSITION mode
        driveFLM.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveFRM.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveBLM.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveBRM.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //moveLeft
        driveFLM.setPower(power);
        driveFRM.setPower(power);
        driveBLM.setPower(power);
        driveBRM.setPower(power);

        //wait until target position
        while (driveFLM.isBusy() && driveFRM.isBusy() && driveBLM.isBusy() && driveBRM.isBusy())
        {

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
    public void moveRghtE(double power, int ticks) {
        //Reset Encoders
        driveFLM.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveFRM.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveBLM.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveBRM.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //set target position
        driveFLM.setTargetPosition(ticks);
        driveFRM.setTargetPosition(-ticks);
        driveBLM.setTargetPosition(-ticks);
        driveBRM.setTargetPosition(ticks);

        //set ot RUN_TO_POSITION mode
        driveFLM.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveFRM.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveBLM.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveBRM.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //moveRight
        driveFLM.setPower(power);
        driveFRM.setPower(power);
        driveBLM.setPower(power);
        driveBRM.setPower(power);

        //wait until target position
        while (driveFLM.isBusy() && driveFRM.isBusy() && driveBLM.isBusy() && driveBRM.isBusy()) {

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
}
