package com.github.SimonXianyu.codefather.util;

public class StringUtils {
    public static void main(String[] args) {
        System.out.println(formatAndNoPrefix("KB_CARD"));
    }

    /**
     * @date 2015年3月12日 下午9:46:17
     * @param string
     * @return
     * @Descriptoin Format database type into java type, eg format "card_type"  into "cardType"
     */
    public static String format(String string) {
        StringBuilder sb = new StringBuilder();
        char[] cArr = string.trim().toLowerCase().toCharArray();
        for (int i = 0; i < cArr.length; i++) {
            char c = cArr[i];
            if (c == '_') {
                i++;
                sb.append(Character.toUpperCase(cArr[i]));
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * @date 2015年3月12日 下午9:51:51
     * @param tableName
     * @return
     * @Descriptoin
     */
    public static String formatAndNoPrefix(String tableName) {
        String noPrefixTableName = tableName.toLowerCase().replaceFirst(
                PropertiesUtils.getTablePrefix(), "");
        noPrefixTableName = format(noPrefixTableName);
        return noPrefixTableName;
    }

    /**
     * @date 2015年3月12日 下午9:51:54
     * @param string
     * @return
     * @Descriptoin
     */
    public static String firstUpper(String string) {
        String str = format(string);
        str = str.substring(0, 1).toUpperCase() + str.substring(1);
        return str;
    }

    /**
     * @date 2015年3月12日 下午9:51:57
     * @param tableName
     * @return
     * @Descriptoin
     */
    public static String firstUpperAndNoPrefix(String tableName) {
        String noPrefixTableName = tableName.toLowerCase().replaceFirst(
                PropertiesUtils.getTablePrefix(), "");
        noPrefixTableName = firstUpper(noPrefixTableName);
        return noPrefixTableName;
    }

    /**
     * @date 2015年3月12日 下午9:52:01
     * @param string
     * @return
     * @Descriptoin
     */
    public static String firstUpperNoFormat(String string) {
        String str = string.substring(0, 1).toUpperCase() + string.substring(1);
        return str;
    }
}
