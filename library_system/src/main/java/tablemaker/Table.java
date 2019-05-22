package tablemaker;


import java.util.ArrayList;
import java.util.List;

public class Table {
    private ArrayList<TableColumn> columns;
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Table(String name) {
        this.columns = new ArrayList<TableColumn>();
        this.name = name;
    }

    public void addColumn(TableColumn tableColumn) {
        columns.add(tableColumn);
    }


    public List<TableColumn> getColumns() {
        return columns;
    }

    @Override
    public String toString() {
        return "Table{" +
                "columns=" + columns +
                ", name='" + name + '\'' +
                '}';
    }

    public String createSequence() {
        StringBuilder result = new StringBuilder();
        result.append(
                "CREATE SEQUENCE ").append(this.name).append("_SEQ").append("\n" +
                "  MINVALUE 1\n" +
                "  START WITH 1\n" +
                "  INCREMENT BY 1\n" +
                "  CACHE 20;");
        return result.toString();
    }

    public String createTableSynonym(String name) {
        return "\n CREATE OR REPLACE SYNONYM " + name + "_SYN FOR " + name + ";\n";
    }

    public String createResultTables() {
        StringBuilder result = new StringBuilder();
        for (TableColumn tableColumn : columns) {
            result.append("CREATE TABLE ").append(name).append("_").append(tableColumn.getName()).append(System.getProperty("line.separator"));
            result.append("( ID NUMBER(10),").append(System.getProperty("line.separator"));
            result.append("  VALUE ").append(tableColumn.getType()).append(",").append(System.getProperty("line.separator"));
            result.append("  CHANGE_TIME TIMESTAMP ").append(System.getProperty("line.separator")).append(");").append(System.getProperty("line.separator"));
        }
        return result.toString();
    }

    private String selectMaxValueFromTable(String tableName, String columnName, String foreignID) {
        StringBuilder sb = new StringBuilder();
        sb.append("(SELECT ").append(tableName).append("_").append(columnName).append(".VALUE").append("\n");
        sb.append("FROM ").append(tableName).append("_").append(columnName).append("\n");
        sb.append("WHERE CHANGE_TIME = (SELECT MAX(CHANGE_TIME) FROM ").append(tableName).append("_").append(columnName).append("\n").append("WHERE ID = ").append(foreignID);
        sb.append(" GROUP BY ").append(foreignID).append(") \n AND ID = ").append(foreignID).append(")");
        return sb.toString();
    }

    public String createTableView() {
        StringBuilder result = new StringBuilder();
        result.append(createTableSynonym(name + "_" + columns.get(0).getName()));

        result.append("CREATE VIEW ").append(this.name).append("_VIEW AS \n");
        String firstColumnID = name + "_" + columns.get(0).getName() + "_SYN" + ".ID";
        result.append("SELECT ").append(firstColumnID).append(" AS ID, ");
        for (TableColumn tableColumn : columns) {
            String tableColumnValue = name + "_" + tableColumn.getName() + ".VALUE";
            String maxValue = selectMaxValueFromTable(name, tableColumn.getName(), firstColumnID);
            result.append(maxValue).append(" AS ").append(tableColumn.getName()).append(", ");
        }
        result.setLength(result.length() - 2);
        result.append("\n");
        result.append("FROM \n ");
        result.append(name + "_" + columns.get(0).getName()).append("_SYN, ");
        for (TableColumn tableColumn : columns) {
            String tableColumnValue = name + "_" + tableColumn.getName();
            result.append(tableColumnValue).append(",");
        }
        result.setLength(result.length() - 1);
        result.append("\n");
        result.append("WHERE ");
        for (TableColumn tableColumn : columns) {
            String tableColumnValue = name + "_" + tableColumn.getName() + ".ID";
            result.append(firstColumnID).append(" = ").append(tableColumnValue).append(" AND ");
        }
        result.setLength(result.length() - 5);
        result.append("\nGROUP BY " + firstColumnID + ";\n");
        return result.toString();
    }

    public String createAddProcedure() {
        StringBuilder result = new StringBuilder();
        result.append("CREATE OR REPLACE PROCEDURE  ADD_").append(this.name).append("(");
        for (TableColumn tableColumn : columns) {
            String inputType = tableColumn.getType().split("\\(")[0];
            result.append(tableColumn.getName()).append(" ").append(inputType).append(" ,");
        }
        result.setLength(result.length() - 1);
        result.append(") IS").append(System.getProperty("line.separator"));
        result.append("CurDate TIMESTAMP := CURRENT_TIMESTAMP;");
        result.append("CurID NUMBER(10) := ").append(this.name).append("_SEQ.NEXTVAL;").append("\n");
        result.append("BEGIN").append(System.getProperty("line.separator"));

        for (TableColumn tableColumn : columns) {
            result.append("INSERT INTO ").append(name).append("_").append(tableColumn.getName()).append("\n");
            result.append("(ID, VALUE, CHANGE_TIME)").append("\n").append("VALUES").append("\n");
            result.append("(CurID, ").append(tableColumn.getName()).append(", CurDate);");
        }
        result.append("END;");
        return result.toString();

    }

    public String createDeleteProcedure() {
        StringBuilder result = new StringBuilder();
        result.append("CREATE OR REPLACE PROCEDURE  DELETE_").append(this.name).append("(DELETE_ID NUMBER");
        result.append(") IS").append(System.getProperty("line.separator"));
        result.append("BEGIN").append(System.getProperty("line.separator"));

        for (TableColumn tableColumn : columns) {
            result.append("DELETE FROM ").append(name).append("_").append(tableColumn.getName()).append("\n");
            result.append("WHERE ").append(name).append("_").append(tableColumn.getName()).append(".ID = DELETE_ID;\n");
        }
        result.append("END;");
        return result.toString();
    }

    private String selectMaxValueFromTableWithInto(String tableName, String columnName, String foreignID) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ").append(tableName).append("_").append(columnName).append(".VALUE").append("\n").append("INTO LAST_").append(tableName).append("_").append(columnName).append("\n");
        sb.append("FROM ").append(tableName).append("_").append(columnName).append("\n");
        sb.append("WHERE CHANGE_TIME = (SELECT MAX(CHANGE_TIME) FROM ").append(tableName).append("_").append(columnName).append("\n").append("WHERE ID = ").append(foreignID);
        sb.append(" GROUP BY ").append(foreignID).append(");");
        return sb.toString();
    }

    public String createChangeProcedure() {
        StringBuilder result = new StringBuilder();
        result.append("CREATE OR REPLACE PROCEDURE  CHANGE_").append(this.name).append("(");
        for (TableColumn tableColumn : columns) {
            String inputType = tableColumn.getType().split("\\(")[0];
            result.append(tableColumn.getName()).append(" ").append(inputType).append(" ,");
        }
        result.append("ENTITY_ID IN NUMBER");
        result.append(") IS").append(System.getProperty("line.separator"));
        result.append("CurDate TIMESTAMP := CURRENT_TIMESTAMP;");
        for (TableColumn tableColumn : columns) {
            result.append("\nLAST_").append(name).append("_").append(tableColumn.getName()).append(" ").append(tableColumn.getType()).append(";\n");
        }
        result.append("BEGIN").append(System.getProperty("line.separator"));
        for (TableColumn tableColumn : columns) {
            result.append(selectMaxValueFromTableWithInto(name, tableColumn.getName(), "ENTITY_ID"));
        }
        for (TableColumn tableColumn : columns) {
            result.append("\nIF ").append(tableColumn.getName()).append(" != ");
            result.append("\nLAST_").append(name).append("_").append(tableColumn.getName()).append(" THEN \n");
            result.append("INSERT INTO ").append(name).append("_").append(tableColumn.getName()).append("\n");
            result.append("(ID, VALUE, CHANGE_TIME)").append("\n").append("VALUES").append("\n");
            result.append("(ENTITY_ID, ").append(tableColumn.getName()).append(", CurDate);");
            result.append("\nEND IF;");
        }
        result.append("END;");
        return result.toString();
    }

    public String createTableInfo() {
        StringBuilder result = new StringBuilder();
        result.append("/***************************TABLES*************************/\n");
        result.append(createResultTables());
        result.append("\n/*************************VIEW***************************/\n");
        result.append(createTableView());
        result.append("\n/*************************SEQUENCES***************************/\n");
        result.append(createSequence());
        result.append("\n/*************************ADD PROCEDURE***************************/\n");
        result.append(createAddProcedure());
        result.append("\n/*************************CHANGE PROCEDURE***************************/\n");
        result.append(createChangeProcedure());
        result.append("\n/*************************DELETE PROCEDURE***************************/\n");
        result.append(createDeleteProcedure());
        return result.toString();
    }

}
