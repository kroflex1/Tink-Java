package edu.hw2.Task3.Connections;

import edu.hw2.Task3.Exceptions.ConnectionException;

public class FaultyConnection implements Connection {
    public static final double PROBABILITY_OF_CONNECTION_FAILURE = 0.7;

    @Override
    public void execute(String command) throws ConnectionException {
        if (Math.random() <= PROBABILITY_OF_CONNECTION_FAILURE) {
            throw new ConnectionException("Can't connect to the server");
        }
    }

}
