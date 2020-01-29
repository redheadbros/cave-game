package lukesgames.cavegame;

public class CarConstants {


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
}
