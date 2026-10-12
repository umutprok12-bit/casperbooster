package com.casperboost;

/**
 * High-performance math approximations engineered to reduce CPU cycles
 * on low-spec processors (e.g. Intel Celeron N4020).
 * Uses static lookup tables with bitmask wrapping.
 */
public final class FastMath {
    private static final int TABLE_SIZE = 4096;
    private static final int MASK = TABLE_SIZE - 1;
    private static final float RAD_TO_INDEX = TABLE_SIZE / (float) (Math.PI * 2.0);
    private static final float[] SIN_TABLE = new float[TABLE_SIZE];

    static {
        for (int i = 0; i < TABLE_SIZE; ++i) {
            SIN_TABLE[i] = (float) Math.sin((i + 0.5F) / TABLE_SIZE * (Math.PI * 2.0));
        }
        for (int i = 0; i < 360; i += 90) {
            SIN_TABLE[(int) (i * TABLE_SIZE / 360.0F) & MASK] = (float) Math.sin(i * Math.PI / 180.0);
        }
    }

    private FastMath() {}

    public static float sin(float radians) {
        return SIN_TABLE[(int) (radians * RAD_TO_INDEX) & MASK];
    }

    public static float cos(float radians) {
        return SIN_TABLE[(int) ((radians + (float) (Math.PI / 2.0)) * RAD_TO_INDEX) & MASK];
    }

    public static float fastInvSqrt(float x) {
        float xhalf = 0.5F * x;
        int i = Float.floatToIntBits(x);
        i = 0x5f3759df - (i >> 1);
        x = Float.intBitsToFloat(i);
        x = x * (1.5F - xhalf * x * x);
        return x;
    }

    public static float fastSqrt(float x) {
        return x * fastInvSqrt(x);
    }

    public static double distanceSq(double x1, double y1, double z1, double x2, double y2, double z2) {
        double dx = x1 - x2;
        double dy = y1 - y2;
        double dz = z1 - z2;
        return dx * dx + dy * dy + dz * dz;
    }
}
