package security;

public class Compairison {

    /**
     * Compares two digests for equality. Does a simple time constant
     * comparison. This is crucial for being resistant to timing attacks.
     *
     * @param digesta one of the digests to compare.
     *
     * @param digestb the other digest to compare.
     *
     * @return true if the digests are equal, false otherwise.
     */
    public static boolean isEqual(byte[] digesta, byte[] digestb) {
        if (digesta.length != digestb.length) {
            return false;
        }

        int result = 0;
        for (int i = 0; i < digesta.length; i++) {
            result |= digesta[i] ^ digestb[i];
            if (digesta[i] != digestb[i]) {
                return false;
            }
        }
        return result == 0;

    }

}
