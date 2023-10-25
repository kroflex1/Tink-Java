package edu.hw3.Task6;

import java.util.PriorityQueue;

public class StockMarket {

    private final PriorityQueue<Stock> stackQueue;

    public StockMarket() {
        stackQueue = new PriorityQueue<>(new StockComparator().reversed());
    }

    public void add(Stock stock) {
        stackQueue.add(stock);
    }

    public void remove(Stock stock) {
         boolean isRemove =  stackQueue.remove(stock);
         if(!isRemove){
             throw new IllegalArgumentException("There is no such element in Stock Market");
         }
    }

    public Stock mostValuableStock() {
        Stock mostValuableStock = stackQueue.peek();
        if(mostValuableStock == null){
            throw new IllegalArgumentException("Stock Market is empty");
        }
        return mostValuableStock;
    }
}
