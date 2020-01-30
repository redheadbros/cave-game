package lukesgames.cavegame;

public class CarConstants {

    public static float coefficientOfFriction = 0.7f;
    public static float radius = 22; //approximate square root of 512
    public static float desiredTurnSpeedFactor = 5;
    public static float turningPower = 3;

    //attractors
    public static class WheelTurnAttractor {
        public static float wheelTurnSpeedMargin = 30;
        public static float maxWheelTurnSpeed = 500;
        public static float getRate(float currentAngle, float desiredAngle) {
            return Attractor.staticGetRate(maxWheelTurnSpeed, wheelTurnSpeedMargin, currentAngle, desiredAngle);
        }
    }

    public static class AccelerationAttractor {
        public static float speedMargin = 10;
        public static float maxAcceleration = 1000;
        public static float getRate(float currentSpeed, float desiredSpeed) {
            return Attractor.staticGetRate(maxAcceleration, speedMargin, currentSpeed, desiredSpeed);
        }
    }

    public static class BrakeAttractor {
        public static float brakeMargin = 5;
        public static float maxBrake = 250;
        public static float getRate(float currentSpeed, float desiredSpeed) {
            return Attractor.staticGetRate(maxBrake, brakeMargin, currentSpeed, desiredSpeed);
        }
    }

    public static class FrictionAttractor {
        public static float frictionMargin = 5;
        public static float maxFriction = coefficientOfFriction * 300;
        public static float getRate(float currentSpeed, float desiredSpeed) {
            return Attractor.staticGetRate(maxFriction, frictionMargin, currentSpeed, desiredSpeed);
        }
    }

    public static class RotationAttractor {
        public static float spinSpeedMargin = 50;
        public static float maxRotationalAcceleration = 300;
        public static float getRate(float currentAngularSpeed, float desiredAngularSpeed) {
            return Attractor.staticGetRate(maxRotationalAcceleration, spinSpeedMargin, currentAngularSpeed, desiredAngularSpeed);
        }
    }
}
