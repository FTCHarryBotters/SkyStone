package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "AutoStoneParkCity", group = "Sample")
public class AutoStoneParkCity extends LinearOpMode {

    //declare servos
    private Servo waffleForeS;
    private Servo waffleBackS;

    @Override
    public void runOpMode() throws InterruptedException {

        if (true) {
            //init servos
            waffleForeS = hardwareMap.servo.get("waffleFrontS");
            waffleBackS = hardwareMap.servo.get("waffleBackS");
        }

        waitForStart();

        Thread.sleep(5000);
        waffledown();
        Thread.sleep(5000);

    }
    public void waffledown() {
        waffleForeS.setPosition(0.35);
        waffleBackS.setPosition(0.62);
    }
    public void waffleup() {
        waffleForeS.setPosition(0.00);
        waffleBackS.setPosition(1.00);
    }
}
//sksksksks
