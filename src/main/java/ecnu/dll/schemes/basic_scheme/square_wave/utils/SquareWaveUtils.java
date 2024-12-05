package ecnu.dll.schemes.basic_scheme.square_wave.utils;

public class SquareWaveUtils {
    public static Double getOptimalB(Double epsilon) {
        double topValue = Math.exp(epsilon);
        return (epsilon * topValue - topValue + 1) / (2 * topValue * (topValue - 1 - epsilon));
    }
}
