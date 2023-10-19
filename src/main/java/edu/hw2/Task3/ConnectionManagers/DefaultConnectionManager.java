package edu.hw2.Task3.ConnectionManagers;

import edu.hw2.Task3.Connections.Connection;
import edu.hw2.Task3.Connections.FaultyConnection;
import edu.hw2.Task3.Connections.StableConnection;

public class DefaultConnectionManager implements ConnectionManager {

    public static final double PROBABILITY_OF_CREATING_FAULTY_CONNECTION = 0.6;

    @Override
    public Connection getConnection() {
        if (Math.random() <= PROBABILITY_OF_CREATING_FAULTY_CONNECTION) {
            return new FaultyConnection();
        }
        return new StableConnection();
    }
}
