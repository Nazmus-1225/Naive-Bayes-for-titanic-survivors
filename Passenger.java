package UCI;

public class Passenger {
	private String firstName,lastName,gender;
	private int passengerClass,survived;
	private double age;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getPassengerClass() {
		return passengerClass;
	}
	public void setPassengerClass(int passengerClass) {
		this.passengerClass = passengerClass;
	}
	public int getSurvived() {
		return survived;
	}
	public void setSurvived(int survived) {
		this.survived = survived;
	}
	public double getAge() {
		return age;
	}
	public void setAge(double age) {
		this.age = age;
	}
	public Passenger(String firstName, String lastName, String gender, int passengerClass, double age, int survived) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.passengerClass = passengerClass;
		this.age = age;
		this.survived=survived;
	}


}
