package edu.hw3;

import edu.hw3.Task6.Stock;
import edu.hw3.Task6.StockMarket;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class Task6Test {

    static Stream<Arguments> stocks() {
        return Stream.of(
            arguments(Arrays.asList(
                new Stock("apple", 100),
                new Stock("adidas", 70),
                new Stock("tesla", 1000),
                new Stock("nokia", 40)
            ), new Stock("tesla", 1000)),
            arguments(Arrays.asList(
                new Stock("apple", 700),
                new Stock("adidas", 10),
                new Stock("tesla", 20),
                new Stock("nokia", 40)
            ), new Stock("apple", 700)),
            arguments(Arrays.asList(
                new Stock("apple", 100),
                new Stock("adidas", 10),
                new Stock("tesla", 20),
                new Stock("nokia", 1000)
            ), new Stock("nokia", 1000))
        );
    }

    @ParameterizedTest
    @MethodSource("stocks")
    void testMostValuableStockInStockMarket(List<Stock> stocks, Stock mostValuableStock) {
        StockMarket stockMarket = new StockMarket();
        for (Stock currentStock : stocks) {
            stockMarket.add(currentStock);
        }
        assertEquals(mostValuableStock, stockMarket.mostValuableStock());
    }

    @ParameterizedTest
    @MethodSource("stocks")
    void testRemoveMostValuableStock(List<Stock> stocks, Stock mostValuableStock) {
        StockMarket stockMarket = new StockMarket();
        for (Stock currentStock : stocks) {
            stockMarket.add(currentStock);
        }
        stockMarket.remove(mostValuableStock);
        assertNotEquals(mostValuableStock, stockMarket.mostValuableStock());
    }

    @Test
    void testTryDeleteNotExistentStock() {
        StockMarket stockMarket = new StockMarket();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            stockMarket.remove(new Stock("Some stock", 10));
        });
        assertEquals("There is no such element in Stock Market", exception.getMessage());
    }

    @Test
    void testCantGetMostValuableStock() {
        StockMarket stockMarket = new StockMarket();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            stockMarket.mostValuableStock();
        });
        assertEquals("Stock Market is empty", exception.getMessage());
    }

}
