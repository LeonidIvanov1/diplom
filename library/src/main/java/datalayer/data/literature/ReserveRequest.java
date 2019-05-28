package datalayer.data.literature;

import datalayer.data.Discipline;
import datalayer.data.Group;
import datalayer.data.User;

import java.util.List;

public class ReserveRequest {
    private int id;
    private User teacher;
    private Group group;
    private Discipline discipline;
    private List<Literature> literatureList;

    public ReserveRequest(int id, User teacher, Group group, Discipline discipline, List<Literature> literatureList) {
        this.id = id;
        this.teacher = teacher;
        this.group = group;
        this.discipline = discipline;
        this.literatureList = literatureList;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public List<Literature> getLiteratureList() {
        return literatureList;
    }

    public void setLiteratureList(List<Literature> literatureList) {
        this.literatureList = literatureList;
    }
}
