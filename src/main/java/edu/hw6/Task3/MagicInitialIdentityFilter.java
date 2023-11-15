package edu.hw6.Task3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MagicInitialIdentityFilter implements AbstractFilter {
    private final byte magicInitialIdentity;
    private final static int HEXADECIMAL_NUMERAL_SYSTEM = 0xff;

    public MagicInitialIdentityFilter(byte magicInitialIdentity) {
        this.magicInitialIdentity = magicInitialIdentity;
    }

    @Override
    public boolean accept(Path entry) throws IOException {
        byte[] bytes = Files.readAllBytes(entry);
        return Integer.toHexString((int) bytes[0] & HEXADECIMAL_NUMERAL_SYSTEM)
            .equals(Integer.toHexString((int) magicInitialIdentity & HEXADECIMAL_NUMERAL_SYSTEM));
    }
}
