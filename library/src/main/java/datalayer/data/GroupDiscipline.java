package datalayer.data;

public class GroupDiscipline {
    private String group;
    private String discipline;

    public GroupDiscipline(String group, String discipline) {
        this.group = group;
        this.discipline = discipline;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }
}
