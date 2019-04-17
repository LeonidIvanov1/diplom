package datalayer.data;

import java.util.List;

public class Group {
	private String name;
	private Specialty specialty;
	private List<User> students;
	private List<String> disciplines;
 
	public Group(String name, Specialty specialty, List<User> students, List<String> disciplines) {
		super();
		this.name = name;
		this.specialty = specialty;
		this.setStudents(students);
		this.setDisciplines(disciplines);
	}

	public Group() {
		this.name = null;
		this.specialty = null;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the specialty
	 */
	public Specialty getSpecialty() {
		return specialty;
	}

	/**
	 * @param specialty the specialty to set
	 */
	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
	}

	/**
	 * @return the students
	 */
	public List<User> getStudents() {
		return students;
	}

	/**
	 * @param students the students to set
	 */
	public void setStudents(List<User> students) {
		this.students = students;
	}

	/**
	 * @return the disciplines
	 */
	public List<String> getDisciplines() {
		return disciplines;
	}

	/**
	 * @param disciplines the disciplines to set
	 */
	public void setDisciplines(List<String> disciplines) {
		this.disciplines = disciplines;
	}

}
