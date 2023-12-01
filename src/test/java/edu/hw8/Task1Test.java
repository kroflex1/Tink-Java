package edu.hw8;

import edu.hw8.Task1.Client;
import edu.hw8.Task1.Server;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task1Test {
    static final int MAX_NUMBER_OF_CONNECTIONS = 3;
    Server server = new Server("127.0.0.1", 5555);
    ExecutorService executorService;
    @BeforeEach
    void startServer() {
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(()-> {
            try {
                server.start(MAX_NUMBER_OF_CONNECTIONS);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @AfterEach
    void stopServer() throws IOException {
        server.stop();
        executorService.shutdown();
    }

    @Test
    void clientGetRespondFromServer() throws IOException {
        Client client = new Client();
        client.startConnection(server.IP, server.PORT);
        String response = client.sendMessage("личности");
        assertEquals("Не переходи на личности там, где их нет", response);
    }

    @Test
    void clientSendManyMessagesGetRespondFromServer() throws IOException {
        Client client = new Client();
        client.startConnection(server.IP, server.PORT);
        assertEquals("Не переходи на личности там, где их нет",  client.sendMessage("личности"));
        assertEquals("Если твои противники перешли на личные оскорбления, будь уверена — твоя победа не за горами",  client.sendMessage("оскорбления"));
    }

    @Test
    void manyClientsGetRespondFromServer() throws IOException {
        Client firstClient = new Client();
        Client secondClient = new Client();
        Client thirdClient = new Client();
        firstClient.startConnection(server.IP, server.PORT);
        secondClient.startConnection(server.IP, server.PORT);
        thirdClient.startConnection(server.IP, server.PORT);
        assertEquals("Не переходи на личности там, где их нет", firstClient.sendMessage("личности"));
        assertEquals("Если твои противники перешли на личные оскорбления, будь уверена — твоя победа не за горами", secondClient.sendMessage("оскорбления"));
        assertEquals("А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма.", thirdClient.sendMessage("глупый"));
    }
}
