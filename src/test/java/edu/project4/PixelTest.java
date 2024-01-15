package edu.project4;

import edu.project4.Records.Pixel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.awt.Color;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PixelTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 3, 5, 15})
    void testIncreaseHitCount(int startHitCount) {
        Pixel pixel = new Pixel(Color.BLACK, startHitCount);
        pixel = pixel.increaseHitCount();
        assertEquals(startHitCount + 1, pixel.hitCount());
    }

    @Test
    void testColorMix() {
        Color firstColor = new Color(0, 50, 25);
        Color secondColor = new Color(100, 40, 30);
        Color exceptColor = new Color(100/2, 45, 27);
        Pixel pixel = new Pixel(firstColor, 0);
        pixel = pixel.mix(secondColor);
        assertEquals(exceptColor, pixel.color());
    }

}
