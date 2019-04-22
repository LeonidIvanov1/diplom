package tablemaker;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private List<TableColumn> columns;
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

    public String createResultTables() {
        StringBuilder result = new StringBuilder();
        for (TableColumn tableColumn : columns) {
            result.append("CREATE TABLE ").append(name).append("_").append(tableColumn.getName()).append(System.getProperty("line.separator"));
            result.append("( ID NUMBER(10),").append(System.getProperty("line.separator"));
            result.append("  VALUE ").append(tableColumn.getType()).append(",").append(System.getProperty("line.separator"));
            result.append("  CHANGE_TIME DATE").append(System.getProperty("line.separator")).append(");").append(System.getProperty("line.separator"));
        }
        return result.toString();
    }

    public String createTableView() {
        return null;
    }

    public String createAddProcedure() {
        return null;
    }

    public String createDeleteProcedure() {
        return null;
    }

    public  String createChangeProcedure() {
        return null;
    }

    public String createTableInfo() {
        return null;
    }

}
