package datalayer;

public class Student {
    String fio;
    String group;
    String libCard;

    public Student(String fio, String group, String libCard) {
        this.fio = fio;
        this.group = group;
        this.libCard = libCard;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getLibCard() {
        return libCard;
    }

    public void setLibCard(String libCard) {
        this.libCard = libCard;
    }
}
