package edu.hw2;

import edu.hw2.Task2.Rectangle;
import edu.hw2.Task2.Square;
import edu.hw2.Task3.ConnectionManagers.ConnectionManager;
import edu.hw2.Task3.ConnectionManagers.DefaultConnectionManager;
import edu.hw2.Task3.ConnectionManagers.FaultyConnectionManager;
import edu.hw2.Task3.Connections.Connection;
import edu.hw2.Task3.Connections.FaultyConnection;
import edu.hw2.Task3.Exceptions.ConnectionException;
import edu.hw2.Task3.PopularCommandExecutor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task3Test {

    private static int calculateProbableNumberOfOccurrencesInTrials(
        double probability,
        int trials
    ) {
        double q = 1 - probability;
        if (probability * trials % 1 < 1e-15) {
            return (int) (probability * trials);
        }
        if ((probability * trials - q) % 1 < 1e-15) {
            return (int) (probability * trials - q);
        }
        return (int) Math.ceil(probability * trials - q);
    }

    @ParameterizedTest
    @ValueSource(ints = {50, 100})
    @DisplayName("FaultyConnection обрывает соединение и вызывает ошибку с некой вероятностью")
    void testFaultyConnection(int trials) {
        FaultyConnection faultyConnection = new FaultyConnection();
        int probableNumberOfOccurrencesInTrials =
            calculateProbableNumberOfOccurrencesInTrials(FaultyConnection.PROBABILITY_OF_CONNECTION_FAILURE, trials);
        boolean isCatchException = false;
        for (int i = 0; i < probableNumberOfOccurrencesInTrials; ++i) {
            try {
                faultyConnection.execute("some command");
            } catch (ConnectionException e) {
                isCatchException = true;
                assertEquals(e.getMessage(), "Can't connect to the server");
                break;
            }
        }
        assertTrue(isCatchException);

    }

    @ParameterizedTest
    @ValueSource(ints = {50, 100})
    @DisplayName("DefaultConnectionManager возвращает FaultyConnection с некой вероятностью")
    void testDefaultConnectionManager(int trials) {
        DefaultConnectionManager defaultConnectionManager = new DefaultConnectionManager();
        int probableNumberOfOccurrencesInTrials =
            calculateProbableNumberOfOccurrencesInTrials(
                DefaultConnectionManager.PROBABILITY_OF_CREATING_FAULTY_CONNECTION,
                trials
            );
        boolean isReturnFaultyConnection = false;
        for (int i = 0; i < probableNumberOfOccurrencesInTrials; ++i) {
            Connection newConnection = defaultConnectionManager.getConnection();
            if (newConnection.getClass() == FaultyConnection.class) {
                isReturnFaultyConnection = true;
                break;
            }

        }
        assertTrue(isReturnFaultyConnection);
    }

}
