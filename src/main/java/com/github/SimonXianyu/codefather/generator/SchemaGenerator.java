package com.github.SimonXianyu.codefather.generator;

import com.github.SimonXianyu.codefather.ConfigConstants;
import com.github.SimonXianyu.codefather.util.DBUtils;
import com.github.SimonXianyu.codefather.util.FileUtils;
import com.github.SimonXianyu.codefather.util.PropertiesUtils;
import com.github.SimonXianyu.codefather.util.StringUtils;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public class SchemaGenerator {
    private List<String> tableList;

    /**
     * Generate the schema files by given tables' name from the config file
     * Created on 8/18
     */
    public void generatorSchemas() {
        FileUtils.createSchemaDirectory();

        this.setTableList();

        // generate the schema file of every table
        Map<String, String> pkMap = null;
        for (String tableName : this.tableList) {
            try {
                pkMap = DBUtils.getPrimaryKeys(tableName);
            } catch (Exception e) {
                System.err.println(tableName
                        + " doesn't exist or connection exception, please check it.");
                return;
            }

            if (!pkMap.isEmpty()) {
                String layers = PropertiesUtils.getLayers();
                if (layers.contains("dbschema")) {
                    this.generateSchema(tableName, pkMap);
                }
                System.out.println(tableName + " has been generated.");
            } else {
                System.err.println(tableName + " has no pk, ignored.");
            }
        }

        System.out.println("All finished.");
    }

    /**
     * Generate the schema file by table name and the primary key map.
     * @param tableName
     * @param pkMap
     */
    private void generateSchema(String tableName, Map<String, String> pkMap) {
        DatabaseMetaData meta = DBUtils.getDatabaseMetaData();
        String schemaName = StringUtils.firstUpperAndNoPrefix(tableName);

        try {
            StringBuilder schemaSb = new StringBuilder();
            schemaSb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            schemaSb.append("<entity "
                    + " java-controller=\"1\""
                    + " table=\"" + schemaName + "\""
                    + ">\n");

            StringBuilder sbKeys = new StringBuilder();
            StringBuilder sbColumns = new StringBuilder();

            ResultSet columns = meta.getColumns(null, "%", tableName, "%");
            if (pkMap.size() > 1) {
                // 目前设计的数据库没有复合主键
            } else {
                while (columns.next()) {
                    String columnName = columns.getString("COLUMN_NAME"); // 列名
                    // java.sql.Types 类型名称
                    String dataTypeName = columns.getString("TYPE_NAME");
                    int columnSize = columns.getInt("COLUMN_SIZE");
                    if (pkMap.containsKey(columnName.toLowerCase())) {
                        sbKeys.append("    <key name=\"" + columnName.toLowerCase() + "\" type=\""
                                + dataTypeName.toLowerCase() + "\" generated=\"false\" />\n");
                    } else {
                        sbColumns.append("    <property name=\"" + columnName.toLowerCase() + "\" type=\""
                                + dataTypeName.toLowerCase() + "\" />\n");
                    }
                }
            } // else end

            schemaSb.append(sbKeys);
            schemaSb.append("\n");
            schemaSb.append(sbColumns);
            schemaSb.append("</entity>\n");

            String dbSchemaDir = ConfigConstants.SCHEMA_PATH;
            FileUtils.write(schemaSb.toString(), dbSchemaDir + StringUtils.firstUpperAndNoPrefix(tableName) + ".xml");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the list of table's name from the config file
     * Created on 8/18.
     */
    private void setTableList() {
        try {
            this.tableList = PropertiesUtils.getTableList();
            if (this.tableList.size() == 0) {
                this.tableList = DBUtils.getAllTables();
            }
        } catch (Exception e) {
            System.err.println("connection exception, please check it.");
            return;
        }
    }

}
