package edu.hw6.Task6;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PortManager {

    private static final Path PORTS_INF_PATH =
        Paths.get(Paths.get("").toAbsolutePath().toString(), "/src/main/java/edu/hw6/Task6/ports-inf.txt");
    private Map<Integer, String> portNames;
    private TreeSet<Integer> ports;

    public PortManager() {
        initializePortsInformation();
    }

    public String getPortsInformation() {
        StringBuilder sockets = new StringBuilder();
        sockets.append("Protocol  Port   Status  Service").append("\n");
        for (Integer currentPort : ports) {
            String serviceName = portNames.get(currentPort);
            try (ServerSocket serverSocket = new ServerSocket(currentPort)) {
                sockets.append(convertPortInfToString(Protocol.TCP, Status.OPEN, currentPort, serviceName))
                    .append("\n");
            } catch (IOException e) {
                sockets.append(convertPortInfToString(Protocol.TCP, Status.CLOSED, currentPort, serviceName))
                    .append("\n");
            }
            try (DatagramSocket datagramSocket = new DatagramSocket(currentPort)) {
                sockets.append(convertPortInfToString(Protocol.UDP, Status.OPEN, currentPort, serviceName))
                    .append("\n");
            } catch (IOException e) {
                sockets.append(convertPortInfToString(Protocol.UDP, Status.CLOSED, currentPort, serviceName))
                    .append("\n");
            }
        }
        return sockets.toString();
    }

    private String convertPortInfToString(Protocol protocol, Status status, int port, String serviceName) {
        return String.format("%-8s  %-5d  %-6s  %s", protocol, port, status, serviceName);
    }

    private void initializePortsInformation() {
        ports = new TreeSet<>();
        portNames = new HashMap<>();
        try (BufferedReader bufferedReader = Files.newBufferedReader(PORTS_INF_PATH)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Pattern pattern = Pattern.compile("Port (\\d+): (.+)");
                Matcher matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    Integer portNumber = Integer.parseInt(matcher.group(1));
                    String portName = matcher.group(2);
                    ports.add(portNumber);
                    portNames.put(portNumber, portName);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private enum Protocol {
        TCP,
        UDP
    }

    private enum Status {
        OPEN,
        CLOSED
    }
}
