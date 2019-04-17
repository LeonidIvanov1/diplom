package datalayer.data;

public class Standart {
	private float parameter;
	private String description;
	
	
	public Standart() {
		parameter = 0f;
		description = null;
	}
	public Standart(String description, float parameter) {
		super();
		this.parameter = parameter;
		this.description = description;
	}

	/**
	 * @return the parameter
	 */
	public float getParameter() {
		return parameter;
	}

	/**
	 * @param parameter the parameter to set
	 */
	public void setParameter(float parameter) {
		this.parameter = parameter;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
}
