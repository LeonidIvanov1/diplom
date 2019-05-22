package datalayer.data;

public class Discipline {
    private int disciplineID;
    private String name;

    public Discipline(int disciplineID, String name) {
        this.disciplineID = disciplineID;
        this.name = name;
    }

    public int getDisciplineID() {
        return disciplineID;
    }

    public void setDisciplineID(int disciplineID) {
        this.disciplineID = disciplineID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
