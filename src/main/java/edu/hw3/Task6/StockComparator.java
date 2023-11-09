package edu.hw3.Task6;

import java.util.Comparator;

public class StockComparator implements Comparator<Stock> {
    @Override
    public int compare(Stock s1, Stock s2) {
        if (s1.price() > s2.price()) {
            return 1;
        } else if (s1.price() < s2.price()) {
            return -1;
        }
        return 0;
    }
}
