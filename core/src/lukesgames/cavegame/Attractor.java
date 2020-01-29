package lukesgames.cavegame;

/**
 * Attractor: a function for 'attracting' a variable to a certain value over time.
 */
public class Attractor {
    private float maxAttractionRate;
    private float marginOfAttraction;

    public Attractor(float maxRate, float dampingRadius) {
        maxAttractionRate = maxRate;
        marginOfAttraction = dampingRadius;
        assert (marginOfAttraction != 0) : "margin must not be zero";
    }

    public float getRate(float currentValue, float desiredValue) {
        if (currentValue >= desiredValue) {
            return Math.max(-maxAttractionRate, -(currentValue - desiredValue) * maxAttractionRate / marginOfAttraction);
        } else {
            return Math.min(maxAttractionRate, -(currentValue - desiredValue) * maxAttractionRate / marginOfAttraction);
        }
    }

    public static float staticGetRate(float maxRate, float dampingRadius, float currentValue, float desiredValue) {
        if (currentValue >= desiredValue) {
            return Math.max(-maxRate, -(currentValue - desiredValue) * maxRate / dampingRadius);
        } else {
            return Math.min(maxRate, -(currentValue - desiredValue) * maxRate / dampingRadius);
        }
    }
}
