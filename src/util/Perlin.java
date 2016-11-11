package util;

public class Perlin {

    float m_vert = 256;
    float m_vert_mask = m_vert - 1;
    float amp = 1;
    float scale = 2;
    float[] r = new float[(int)m_vert];

    public Perlin() {
        for (int i = 0; i < m_vert; ++i) {
            r[i] = (float) Math.random();
        }
    }

    public float getVal(float x) {
        float scaledX = x * scale;
        float xFloor = (float) Math.floor(scaledX);
        float t = scaledX - xFloor;
        float tRemapSmoothstep = t * t * (3_2 * t);

        float xMin = xFloor % m_vert_mask;
        float xMax = (xMin + 1) % m_vert_mask;

        float y = lerp(r[(int) xMin], r[(int) xMax], tRemapSmoothstep);
        return y * amp;
    }

    public float lerp(float a, float b, float t) {
        return a*(1-t)+b*t;
    }
}
