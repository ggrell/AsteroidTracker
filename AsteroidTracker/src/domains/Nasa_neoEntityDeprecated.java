package domains;


public class Nasa_neoEntityDeprecated {

	private String title;
	private String closeApproachDate;
	private String EstDiameter;
	private double missDistanceAU;
	private double missDistanceLD;	
	private double HMag;
	private double relativeVelocity;
	

	public void setTitle(String entityTitle) {
		this.title = entityTitle;
	}

	public void setCloseApproachDate(String closeApprDate) {
		this.closeApproachDate = closeApprDate;
	}

	public void setEstDiameter(String EstDiam) {
		this.EstDiameter = EstDiam;
	}

	public void setMissDistanceAU(double MissDistAU) {
		this.missDistanceAU = MissDistAU;
	}

	public void setMissDistanceLD(double missDistLD) {
		this.missDistanceLD = missDistLD;
	}
	
	public void setHMag(double H_Mag) {
		this.HMag = H_Mag;
	}

	public void setRelativeVelocity(double relativeV) {
		this.relativeVelocity = relativeV;
	}


	public String getTitle() {
		return title;
	}

	public String getCloseApproachDate() {
		return closeApproachDate;
	}

	public String getEstDiameter() {
		return EstDiameter;
	}

	public double getmissDistanceAU() {
		return missDistanceAU;
	}

	public double getmissDistanceLD() {
		return missDistanceLD;
	}
	
	public double getHMag() {
		return HMag;
	}
	
	public double getRelativeVelocity() {
		return relativeVelocity;
	}

}
