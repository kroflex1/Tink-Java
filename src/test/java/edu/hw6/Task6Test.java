package edu.hw6;

import edu.hw6.Task6.PortManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task6Test {
    @Test
    void test(){
        PortManager portManager = new PortManager();
        String result = portManager.getPortsInformation();
        assertFalse(result.isEmpty());
    }
}
