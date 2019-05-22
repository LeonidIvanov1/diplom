package datalayer.data;

public class StudentData {
    private Group group;
    private String libCardNumber;

    public StudentData(Group group, String libCardNumber) {
        this.group = group;
        this.libCardNumber = libCardNumber;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getLibCardNumber() {
        return libCardNumber;
    }

    public void setLibCardNumber(String libCardNumber) {
        this.libCardNumber = libCardNumber;
    }
}
