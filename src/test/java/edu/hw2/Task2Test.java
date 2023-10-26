package edu.hw2;

import edu.hw2.Task2.Rectangle;
import edu.hw2.Task2.Square;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task2Test {
    static Arguments[] rectangles() {
        return new Arguments[] {
            Arguments.of(new Rectangle()),
            Arguments.of(new Square())
        };
    }

    @ParameterizedTest
    @MethodSource("rectangles")
    void rectangleArea(Rectangle rect) {
        var newRect = rect.setWidth(20).setHeight(10);
        assertThat(newRect.area()).isEqualTo(200.0);
    }

    @Test
    @DisplayName("Проверка нахождения площади квадрата")
    void squareArea() {
        var square = new Square(20);
        assertThat(square.area()).isEqualTo(400.0);
    }

    @Test
    @DisplayName("Получение нового прямоугольника из квадрата")
    void squareToRectangle() {
        var square = new Square(20);
        var rectangle = square.setHeight(10);
        assertThat(rectangle.area()).isEqualTo(200.0);
    }
}
