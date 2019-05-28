package datalayer.data.literature;

public class LiteratureFundData {
    int inStock;
    int all;
    int outOfStock;
    int reserved;
    int writtenOff;

    public LiteratureFundData(int inStock, int all, int outOfStock, int reserved, int writtenOff) {
        this.inStock = inStock;
        this.all = all;
        this.outOfStock = outOfStock;
        this.reserved = reserved;
        this.writtenOff = writtenOff;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }

    public int getOutOfStock() {
        return outOfStock;
    }

    public void setOutOfStock(int outOfStock) {
        this.outOfStock = outOfStock;
    }

    public int getReserved() {
        return reserved;
    }

    public void setReserved(int reserved) {
        this.reserved = reserved;
    }

    public int getWrittenOff() {
        return writtenOff;
    }

    public void setWrittenOff(int writtenOff) {
        this.writtenOff = writtenOff;
    }
}
