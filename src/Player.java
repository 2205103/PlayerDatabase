import java.io.Serializable;

public class Player implements Serializable{
    private String name;
    private String country;
    private int age;
    private double height;
    private String club;
    private String position;
    private int number;
    private double salary;

    public Player() {
    }

    public Player(String name, String club, String country, String position, int age, int jersey_Number, double salary, double height) {
        this.name = name;
        this.club = club;
        this.country = country;
        this.position = position;
        this.age = age;
        this.height = height;
        this.number = jersey_Number;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public int getAge() {
        return age;
    }

    public double getHeight() {
        return height;
    }

    public String getClub() {
        return club;
    }

    public String getPosition() {
        return position;
    }

    public int getNumber() {
        return number;
    }

    public double getSalary() {
        return salary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\n" +
                "Country: " + country + "\n" +
                "Age: " + age + "\n" +
                "Height: " + height + " cm\n" +
                "Club: " + club + "\n" +
                "Position: " + position + "\n" +
                "Jersey Number: " + number + "\n" +
                "Salary: $" + salary;
    }
}
