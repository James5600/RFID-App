package pwr.rfid.model;

public class Tag {

    private int id_read;
    private String tag;
    private String date;
    private int id_person;

    public Tag(String tag, String date, int id_person) {
        this.id_read = id_read;
        this.tag = tag;
        this.date = date;
        this.id_person = id_person;
    }

    public Tag() {
    }

    public int getId_read() {
        return id_read;
    }

    public void setId_read(int id_read) {
        this.id_read = id_read;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId_person() {
        return id_person;
    }

    public void setId_person(int id_person) {
        this.id_person = id_person;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tag='" + tag + '\'' +
                ", date='" + date + '\'' +
                ", id_person=" + id_person +
                '}';
    }
}
