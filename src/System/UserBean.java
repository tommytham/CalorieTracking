package System;

public class UserBean {
	
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    
    private String gender; 
    private float currentWeight; // in kg
	private int height; // in cm
    private String activityLevel; // set string to "low", "moderate" or "high"
    private String goal; // set string to "lose", "maintain", "gain"
    private int age;
    private double BMR;
    
    public boolean valid;
    
    public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public float getCurrentWeight() {
		return currentWeight;
	}

	public void setCurrentWeight(float currentWeight) {
		this.currentWeight = currentWeight;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getActivityLevel() {
		return activityLevel;
	}

	public void setActivityLevel(String activityLevel) {
		this.activityLevel = activityLevel;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
    public String getFirstName() {
       return firstName;
	}

    public void setFirstName(String newFirstName) {
       firstName = newFirstName;
	}

	
    public String getLastName() {
       return lastName;
			}

    public void setLastName(String newLastName) {
       lastName = newLastName;
			}
			

    public String getPassword() {
       return password;
	}

    public void setPassword(String newPassword) {
       password = newPassword;
	}
	
			
    public String getUsername() {
       return username;
			}

    public void setUserName(String newUsername) {
       username = newUsername;
			}

				
    public boolean isValid() {
       return valid;
	}

    public void setValid(boolean newValid) {
       valid = newValid;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getBMR() {
		return BMR;
	}

	public void setBMR(double bMR) {
		BMR = bMR;
	}	
}

						
