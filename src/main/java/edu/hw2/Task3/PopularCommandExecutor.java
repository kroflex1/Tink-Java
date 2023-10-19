package edu.hw2.Task3;

import edu.hw2.Task3.ConnectionManagers.ConnectionManager;
import edu.hw2.Task3.Connections.Connection;
import edu.hw2.Task3.Exceptions.ConnectionException;

public final class PopularCommandExecutor {
    private final ConnectionManager manager;
    private final int maxAttempts;

    public PopularCommandExecutor(ConnectionManager manager, int maxAttempts) {
        this.manager = manager;
        this.maxAttempts = maxAttempts;
    }

    public void updatePackages() {
        tryExecute("apt update && apt upgrade -y");
    }

    void tryExecute(String command) throws ConnectionException {
        for (int i = 1; i <= maxAttempts; ++i) {
            try (Connection currentConnection = manager.getConnection();) {
                currentConnection.execute(command);
            } catch (Exception e) {
                if (i == maxAttempts) {
                    throw new ConnectionException("The number of attempts to connect has been exhausted", e);
                }
            }
            break;
        }
    }
}
