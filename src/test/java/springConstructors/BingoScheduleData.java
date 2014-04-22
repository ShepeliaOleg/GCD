package springConstructors;

import java.util.List;

public class BingoScheduleData{
    private List<String> headerColumns;

    public List<String> getHeaderColumns() {
        return headerColumns;
    }

    public void setHeaderColumns(List<String> headerColumns) {
        this.headerColumns = headerColumns;
    }

    public int getColumnsNumber() {
        return headerColumns.size();
    }
}
