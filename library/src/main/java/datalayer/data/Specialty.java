package datalayer.data;

public class Specialty {
	private int specialtyID;
	private String name;
	private String specialtyCode;
	private float standardParameter;
	private String description;

	public Specialty(int specialtyID, String name, String specialtyCode, float standardParameter, String description) {
		this.specialtyID = specialtyID;
		this.name = name;
		this.specialtyCode = specialtyCode;
		this.standardParameter = standardParameter;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecialtyCode() {
		return specialtyCode;
	}

	public void setSpecialtyCode(String specialtyCode) {
		this.specialtyCode = specialtyCode;
	}

	public float getStandardParameter() {
		return standardParameter;
	}

	public void setStandardParameter(float standardParameter) {
		this.standardParameter = standardParameter;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getSpecialtyID() {
		return specialtyID;
	}

	public void setSpecialtyID(int specialtyID) {
		this.specialtyID = specialtyID;
	}
}
