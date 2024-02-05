package edu.hw8;

import edu.hw8.Task1.Client;
import edu.hw8.Task1.Server;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task1Test {
    static final int MAX_NUMBER_OF_CONNECTIONS = 3;
    static Server server;
    static ExecutorService executorService;

    @BeforeAll
    static void startServer() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                server = new Server("127.0.0.1", 5555);
                server.start(MAX_NUMBER_OF_CONNECTIONS);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void clientGetRespondFromServer() {
        Client client = new Client();
        client.startConnection(server.ip, server.port);
        String response = client.sendMessage("личности");
        assertEquals("Не переходи на личности там, где их нет", response);
        client.stopConnection();
    }

    @Test
    void clientSendManyMessagesToServer() {
        Client client = new Client();
        client.startConnection(server.ip, server.port);
        assertEquals("Не переходи на личности там, где их нет", client.sendMessage("личности"));
        assertEquals(
            "Если твои противники перешли на личные оскорбления, будь уверена — твоя победа не за горами",
            client.sendMessage("оскорбления")
        );
        client.stopConnection();
    }

    @Test
    void manyClientsGetRespondFromServer() {
        Client firstClient = new Client();
        Client secondClient = new Client();
        Client thirdClient = new Client();
        firstClient.startConnection(server.ip, server.port);
        secondClient.startConnection(server.ip, server.port);
        thirdClient.startConnection(server.ip, server.port);
        assertEquals("Не переходи на личности там, где их нет", firstClient.sendMessage("личности"));
        assertEquals(
            "Если твои противники перешли на личные оскорбления, будь уверена — твоя победа не за горами",
            secondClient.sendMessage("оскорбления")
        );
        assertEquals(
            "А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма.",
            thirdClient.sendMessage("глупый")
        );
        firstClient.stopConnection();
        secondClient.stopConnection();
        thirdClient.stopConnection();
    }

    @Test
    void moreThanMaxClientsTryGetRespondFromServer() throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_NUMBER_OF_CONNECTIONS + 1);
        int secondsToSleep = 5;
        Callable<Void> clientConnectToServer = () -> {
            var client = new Client();
            client.startConnection(server.ip, server.port);
            TimeUnit.SECONDS.sleep(secondsToSleep);
            client.stopConnection();
            return null;
        };
        List<Callable<Void>> tasks =
            Stream.generate(() -> clientConnectToServer).limit(MAX_NUMBER_OF_CONNECTIONS).toList();
        tasks.forEach(executorService::submit);

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            Client client = new Client();
            client.startConnection(server.ip, server.port);
            String response = client.sendMessage("личности");
            return response;
        }, executorService);

        assertThrows(TimeoutException.class, () ->
            future.get(secondsToSleep - 1, TimeUnit.SECONDS));
    }
}
