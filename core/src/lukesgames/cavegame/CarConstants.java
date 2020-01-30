package lukesgames.cavegame;

public class CarConstants {

    public static float bodyTurnSpeed = 180;
    public static float turnSpeedCoefficient = 0.75f;


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
        public static float maxBrake = 400;
        public static float getRate(float currentSpeed, float desiredSpeed) {
            return Attractor.staticGetRate(maxBrake, brakeMargin, currentSpeed, desiredSpeed);
        }
    }

    //add friction attractor
    public static class FrictionAttractor {
        public static float speedMargin = 0.01f;
        public static float maxFriction = 1f;
        public static float getRate(float currentSpeed, float desiredSpeed) {
            return Attractor.staticGetRate(maxFriction,  speedMargin, currentSpeed, desiredSpeed);
        }
    }
}
