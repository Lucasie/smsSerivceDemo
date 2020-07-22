package com.blueview.MQTT;

public final class HexUtils {
  private static final int[] DEC =
      new int[] {
        0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, 10, 11, 12, 13, 14, 15
      };
  private static final byte[] HEX =
      new byte[] {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
  private static final char[] hex = "0123456789abcdef".toCharArray();

  public HexUtils(String text) {}

  /** @deprecated */
  @Deprecated
  public static void load() {}

  public static int getDec(int index) {
    try {
      return DEC[index - 48];
    } catch (ArrayIndexOutOfBoundsException var2) {
      return -1;
    }
  }

  public static byte getHex(int index) {
    return HEX[index];
  }

  public static String toHexString(byte[] bytes) {
    if (null == bytes) {
      return null;
    } else {
      StringBuilder sb = new StringBuilder(bytes.length << 1);

      for (int i = 0; i < bytes.length; ++i) {
        sb.append(hex[(bytes[i] & 240) >> 4]).append(hex[bytes[i] & 15]);
      }

      return sb.toString();
    }
  }

  public static String bytes_String16(byte[] b) {
    char[] _16 = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < b.length; i++) {
      sb.append(_16[b[i] >> 4 & 0xf]).append(_16[b[i] & 0xf]);
    }
    return sb.toString();
  }

  public static byte[] hexStringToByteArray(String hexString) {
    hexString = hexString.replaceAll(" ", "");
    int len = hexString.length();
    byte[] bytes = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
      // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个字节
      bytes[i / 2] =
          (byte)
              ((Character.digit(hexString.charAt(i), 16) << 4)
                  + Character.digit(hexString.charAt(i + 1), 16));
    }
    return bytes;
  }

  /**
   * 16进制编码字符串转字节数组
   *<p>Title: toByteArray</p>
   *<p>Description: </p>
   *@param hexString
   *@return
   */
  public static byte[] toByteArray(String hexString) {
    hexString = hexString.toLowerCase();
    final byte[] byteArray = new byte[hexString.length() / 2];
    int k = 0;
    for (int i = 0; i < byteArray.length; i++) {// 因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
      byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
      byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
      byteArray[i] = (byte) (high << 4 | low);
      k += 2;
    }
    return byteArray;
  }
}
