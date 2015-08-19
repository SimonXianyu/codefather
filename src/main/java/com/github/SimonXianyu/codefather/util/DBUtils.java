package com.github.SimonXianyu.codefather.util;

import com.github.SimonXianyu.codefather.ConfigConstants;

import java.io.FileInputStream;
import java.sql.*;
import java.util.*;

public class DBUtils {
    /**
     * @return
     * @date 2015年3月12日 下午8:35:27
     * @Descriptoin Get nameList of all tables
     */
    public static List<String> getAllTables() {
        List<String> tableList = new ArrayList<String>();
        try {
            DatabaseMetaData meta = getDatabaseMetaData();
            ResultSet rs = null;
            if ("mysql".equals(getDatabaseType())) {
                rs = meta.getTables(null, "%", "%", new String[]{"TABLE"});
            } else if ("oracle".equals(getDatabaseType())) {
                rs = meta.getTables(null, getUsername(), "%", new String[]{"TABLE"});
            } else if ("db2".equals(getDatabaseType())) {
                rs = meta.getTables(null, "odsuser", "%", new String[]{"TABLE"});
            }
            while (rs.next()) {
                tableList.add(rs.getString("TABLE_NAME"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return tableList;
    }

    /**
     * @return
     * @date 2015年3月12日 下午8:36:14
     * @Descriptoin Get the Database MetaData
     */
    public static DatabaseMetaData getDatabaseMetaData() {
        Connection connection = getConnection();
        DatabaseMetaData meta = null;
        try {
            meta = connection.getMetaData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return meta;
    }

    /**
     * @return
     * @date 2015年3月12日 下午8:36:26
     * @Descriptoin Get the Database connection
     */
    public static Connection getConnection() {
        Properties prop = new Properties();
        Connection connection = null;
        try {
            prop.load(new FileInputStream(ConfigConstants.JDBC));
            String driverClassName = prop.getProperty("jdbc.driverClassName");
            String url = prop.getProperty("jdbc.url");
            String userName = prop.getProperty("jdbc.username");
            String password = prop.getProperty("jdbc.password");
            Class.forName(driverClassName);
            connection = DriverManager.getConnection(url, userName, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    /**
     * @return
     * @date 2015年3月12日 下午8:41:53
     * @Descriptoin Get the Database type
     */
    public static String getDatabaseType() {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(ConfigConstants.JDBC));
            String driverClassName = prop.getProperty("jdbc.driverClassName");
            if (driverClassName.contains("mysql")) {
                return "mysql";
            } else if (driverClassName.contains("oracle")) {
                return "oracle";
            } else if (driverClassName.contains("db2")) {
                return "db2";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return
     * @date 2015年3月12日 下午8:41:20
     * @Descriptoin Get the username of Oracle
     */
    public static String getUsername() {
        String dbType = getDatabaseType();
        String instance = null;
        if ("oracle".equals(dbType)) {
            try {
                Properties prop = new Properties();
                prop.load(new FileInputStream(ConfigConstants.JDBC));
                String username = prop.getProperty("jdbc.username");
                if (username != null) {
                    instance = username.toUpperCase();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    /**
     * @param tableName
     * @return primary key if table contains one
     * 2015年3月12日 下午8:52:46
     */
    public static Map<String, String> getPrimaryKeys(String tableName) {
        Map<String, String> map = new HashMap();
        try {
            ResultSet pkRSet = getDatabaseMetaData().getPrimaryKeys(null, null, tableName);
            while (pkRSet.next()) {
                String primaryKey = pkRSet.getString("COLUMN_NAME");
                String primaryKeyType = getColumnNameTypeMap(pkRSet.getString("TABLE_NAME")).get(
                        primaryKey);
                map.put(primaryKey.toLowerCase(), primaryKeyType);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    /**
     * @param tableName
     * @return
     * @date 2015年3月12日 下午8:58:43
     * @Descriptoin Get the map of cloumnName and columnType by tableName
     */
    public static Map<String, String> getColumnNameTypeMap(String tableName) {
        Map<String, String> colMap = new LinkedHashMap<String, String>();
        DatabaseMetaData meta = getDatabaseMetaData();
        try {
            ResultSet colRet = meta.getColumns(null, "%", tableName, "%");
            while (colRet.next()) {
                String columnName = colRet.getString("COLUMN_NAME");
                int digits = colRet.getInt("DECIMAL_DIGITS");
                int dataType = colRet.getInt("DATA_TYPE");
                String columnType = null;
                String typeName = colRet.getString("TYPE_NAME");
                int columnSize = colRet.getInt("COLUMN_SIZE");
                if ("mysql".equals(getDatabaseType())) {
                    columnType = getDataType(dataType, digits);
                } else if ("oracle".equals(getDatabaseType())) {
                    columnType = getDataTypeForOracle(typeName, columnSize, digits);
                } else if ("db2".equals(getDatabaseType())) {
                    columnType = getDataType(dataType, digits);
                }
                colMap.put(columnName, columnType);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return colMap;
    }

    /**
     * @param type
     * @param digits
     * @return
     * @date 2015年3月12日 下午9:01:53
     * @Descriptoin Translate database type into java type
     */
    private static String getDataType(int type, int digits) {
        String dataType = "";
        switch (type) {
            case Types.VARCHAR: // 12
                dataType = "String";
                break;
            case Types.INTEGER: // 4
                dataType = "Integer";
                break;
            case Types.BIT: // -7
                dataType = "Integer";
                break;
            case Types.LONGVARCHAR: // -1
                dataType = "Long";
                break;
            case Types.BIGINT: // -5
                dataType = "Long";
                break;
            case Types.DOUBLE: // 8
                dataType = getPrecisionType();
                break;
            case Types.REAL: // 7
                dataType = getPrecisionType();
                break;
            case Types.FLOAT: // 6
                dataType = getPrecisionType();
                break;
            case Types.DECIMAL: // 3
                dataType = "BigDecimal";
                break;
            case Types.NUMERIC: // 2
                switch (digits) {
                    case 0:
                        dataType = "Integer";
                        break;
                    default:
                        dataType = getPrecisionType();
                }
                break;
            case Types.DATE: // 91
                dataType = "Date";
                break;
            case Types.TIMESTAMP: // 93
                dataType = "Date";
                break;
            default:
                dataType = "String";
        }
        return dataType;
    }

    /**
     * @param typeName
     * @param columnSize
     * @param digits
     * @return
     * @date 2015年3月12日 下午9:03:01
     * @Descriptoin Get data type for Oracle
     */
    private static String getDataTypeForOracle(String typeName, int columnSize, int digits) {
        String dataType = "";
        if ("VARCHAR2".equals(typeName)) {
            dataType = "String";
        } else if ("DATE".equals(typeName)) {
            dataType = "Date";
        } else if (typeName.contains("TIMESTAMP")) {
            dataType = "Timestamp";
        } else if ("NUMBER".equals(typeName) && digits > 0) {
            dataType = getPrecisionType();
        } else if ("NUMBER".equals(typeName) && digits == 0 && columnSize <= 10) {
            dataType = "Integer";
        } else if ("NUMBER".equals(typeName) && digits == 0 && columnSize > 10) {
            dataType = "Long";
        } else {
            dataType = "String";
        }
        return dataType;
    }

    /**
     * @return
     * @date 2015年3月12日 下午9:03:48
     * @Descriptoin Get precision from config.properties
     */
    private static String getPrecisionType() {
        String dataType;
        if ("high".equals(PropertiesUtils.getPrecision())) {
            dataType = "BigDecimal";
        } else {
            dataType = "Double";
        }
        return dataType;
    }
}
