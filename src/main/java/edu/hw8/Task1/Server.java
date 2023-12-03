package edu.hw8.Task1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server {
    public final int port;
    public final String ip;
    private AtomicBoolean isON;
    private final Map<String, List<String>> answersForClient;
    private ServerSocket serverSocket;
    private ExecutorService executor;

    public Server(String ip, int port) {
        this.port = port;
        this.ip = ip;
        isON = new AtomicBoolean();
        answersForClient = getAnswersForClient();
    }

    public void start(int maxNumberOfConnections) throws IOException {
        isON.set(true);
        serverSocket = new ServerSocket(port);
        executor = Executors.newFixedThreadPool(maxNumberOfConnections);
        while (isON.get()) {
            executor.execute(new EchoClientHandler(serverSocket.accept(), answersForClient));
        }
    }

    public void stop() throws IOException {
        isON.set(false);
        executor.shutdown();
        serverSocket.close();
    }

    private Map<String, List<String>> getAnswersForClient() {
        Map<String, List<String>> result = new HashMap<>();
        result.put("личности", List.of("Не переходи на личности там, где их нет"));
        result.put(
            "оскорбления",
            List.of("Если твои противники перешли на личные оскорбления, будь уверена — твоя победа не за горами")
        );
        result.put(
            "глупый",
            List.of(
                "А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма.")
        );
        result.put("интеллект", List.of("Чем ниже интеллект, тем громче оскорбления"));
        return result;
    }

    private static class EchoClientHandler implements Runnable {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private final Map<String, List<String>> answersForClient;

        EchoClientHandler(Socket socket, Map<String, List<String>> answersForClient) {
            this.clientSocket = socket;
            this.answersForClient = answersForClient;
        }

        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
                while (true) {
                    String inputLine = in.readLine();
                    if (inputLine.equals("exit")) {
                        break;
                    }
                    if (!answersForClient.containsKey(inputLine)) {
                        out.println("");
                    } else {
                        int randomIndex =
                            ThreadLocalRandom.current().nextInt(0, answersForClient.get(inputLine).size());
                        out.println(answersForClient.get(inputLine).get(randomIndex));
                    }
                }
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
