package pl.jgwozdz.brickcommand.brick.helper;

public class HexDump {

    private final String lineSep;

    public HexDump(String lineSep) {
        this.lineSep = lineSep;
    }

    public static String formatBytes(byte[] rawMessage) {
        return new HexDump("\r\n").format(rawMessage);
    }

    public String format(byte[] rawMessage) {
        StringBuilder sb = new StringBuilder();
        StringBuilder print = new StringBuilder();
        int c0 = 0, c1 = 0, c2 = 0;
        for (byte b : rawMessage) {
            sb.append(String.format("%02x", b));
            if (b >= 32 && b < 127) {
                print.append((char) (int) (b));
            } else print.append('.');

            c0++;
            c1++;
            c2++;
            if (c1 == 2) {
                sb.append(' ');
                c1 = 0;
            }
            if (c2 == 32) {
                sb.append("    ").append(print).append(lineSep);
                c2 = 0;
                print = new StringBuilder();
            }
        }
        while (c2 < 32) {
            sb.append("  ");
            print.append(' ');
            c0++;
            c1++;
            c2++;
            if (c1 == 2) {
                sb.append(' ');
                c1 = 0;
            }
        }
        sb.append("    ").append(print);
        return sb.toString().trim();
    }

    public byte[] decode(String encoded) {
        String hexData = encoded.replaceAll("\\s+", "");
        int len = hexData.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            char charH = hexData.charAt(i);
            int digitH = Character.digit(charH, 16);
            char charL = hexData.charAt(i + 1);
            int digitL = Character.digit(charL, 16);
            if (digitH < 0) throw new IllegalArgumentException("Invalid char " + charH);
            if (digitL < 0) throw new IllegalArgumentException("Invalid char " + charL);
            data[i / 2] = (byte) ((digitH << 4) + digitL);
        }
        return data;
    }

    public static byte[] decodeBytes(String hexData) {
        return new HexDump("").decode(hexData);
    }
}
