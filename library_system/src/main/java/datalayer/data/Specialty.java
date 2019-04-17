package datalayer.data;

public class Specialty {
	private String name;
	private Standart standart;
	
	
	public Specialty() {
		name = null;
		standart = null;
	}
	
	public Specialty(String name, Standart standart) {
		super();
		this.name = name;
		this.standart = standart;
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
	 * @return the standart
	 */
	public Standart getStandart() {
		return standart;
	}
	/**
	 * @param standart the standart to set
	 */
	public void setStandart(Standart standart) {
		this.standart = standart;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}
	
}
