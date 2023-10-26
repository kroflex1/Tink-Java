package edu.hw2;

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

    static Arguments[] connectionManagers() {
        return new Arguments[] {
            Arguments.of(new DefaultConnectionManager()),
            Arguments.of(new FaultyConnectionManager())
        };
    }

    @Test
    @DisplayName("FaultyConnection может обрвать соединение")
    void testFaultyConnection() {
        FaultyConnection faultyConnection = new FaultyConnection();
        boolean isHappenedConnectionException = false;
        while (!isHappenedConnectionException) {
            try {
                faultyConnection.execute("some command");
            } catch (ConnectionException e) {
                isHappenedConnectionException = true;
            }
        }
        assertTrue(isHappenedConnectionException);
    }

    @Test
    @DisplayName("DefaultConnectionManager возвращает FaultyConnection с некой вероятностью")
    void testDefaultConnectionManager() {
        DefaultConnectionManager defaultConnectionManager = new DefaultConnectionManager();
        boolean isReturnFaultyConnection = false;
        while (!isReturnFaultyConnection) {
            Connection newConnection = defaultConnectionManager.getConnection();
            if (newConnection instanceof FaultyConnection) {
                isReturnFaultyConnection = true;
            }
        }
        assertTrue(isReturnFaultyConnection);
    }

    @ParameterizedTest
    @MethodSource("connectionManagers")
    @DisplayName("PopularCommandExecutor выбрасывает ошибку при достижении максимальных попыток для подключения")
    void testPopularCommandExecutor(ConnectionManager connectionManager) {
        int maxAttempts = 3;
        PopularCommandExecutor commandExecutor = new PopularCommandExecutor(connectionManager, maxAttempts);
        while (true) {
            try {
                commandExecutor.updatePackages();
            } catch (ConnectionException e) {
                assertEquals("The number of attempts to connect has been exhausted", e.getMessage());
                assertEquals("Can't connect to the server", e.getCause().getMessage());
                break;
            }
        }
    }
}
