package datalayer.data;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Discipline)) return false;
        Discipline that = (Discipline) o;
        return disciplineID == that.disciplineID &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(disciplineID, name);
    }
}
