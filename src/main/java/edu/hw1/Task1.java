package edu.hw1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Task1 {
    private final static Logger LOGGER = LogManager.getLogger();

    public void greetWorld() {
        LOGGER.info("Привет, мир!");
    }
}
