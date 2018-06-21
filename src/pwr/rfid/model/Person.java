package pwr.rfid.model;

public class Person {

    private int id_person;
    private String name;
    private String surname;
    private String date_of_birth;
    private String sex;
    private String pesel;
    private String blood_type;
    private String description;

    public Person(String name, String surname, String date_of_birth, String sex, String pesel, String blood_type, String description) {
        this.id_person = id_person;
        this.name = name;
        this.surname = surname;
        this.date_of_birth = date_of_birth;
        this.sex = sex;
        this.pesel = pesel;
        this.blood_type = blood_type;
        this.description = description;
    }

    public Person() {}

    public int getId_person() {
        return id_person;
    }

    public void setId_person(int id_person) {
        this.id_person = id_person;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getBlood_type() {
        return blood_type;
    }

    public void setBlood_type(String blood_type) {
        this.blood_type = blood_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", date_of_birth='" + date_of_birth + '\'' +
                ", sex='" + sex + '\'' +
                ", pesel='" + pesel + '\'' +
                ", blood_type='" + blood_type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
