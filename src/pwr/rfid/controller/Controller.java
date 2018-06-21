package pwr.rfid.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Popup;
import pwr.rfid.dao.RfidDAO;
import pwr.rfid.model.Person;
import pwr.rfid.model.Tag;
import pwr.rfid.scanner.RfidScanner;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class Controller {

    @FXML
    private TextField nameTF = new TextField();
    @FXML
    private TextField surnameTF = new TextField();
    @FXML
    private TextField dateTF = new TextField();
    @FXML
    private TextField sexTF = new TextField();
    @FXML
    private TextField peselTF = new TextField();
    @FXML
    private TextField bloodTF = new TextField();
    @FXML
    private Label tagLabel = new Label();
    @FXML
    private Label fillFormLabel = new Label();
    @FXML
    private TextArea descriptionTA = new TextArea();
    @FXML
    private Button btnSave = new Button();
    RfidScanner rfidScanner;
    private StringBuilder rfidSeparator;
    private String rfid_id;
    private RfidDAO rfidDAO;
    private String dateTime;

    public Controller() {
        rfidDAO = new RfidDAO();
    }

    public void checkId() {
        if (rfid_id != null) {
            if (rfid_id.length() == 10) {
                tagLabel.setText(rfid_id);
                List<String> tags = rfidDAO.readAllTags();
                if (tags.contains(rfid_id)) {
                    System.out.println("true");
                    disableObjects();
                    setInformation();
                } else {
                    System.out.println("false");
                    enableObjects();
                    dateTime = getDate();
                }
            }
        }
    }

    private void setInformation() {
        Person person = rfidDAO.readPerson(rfid_id);
        System.out.println(person);
        nameTF.setText(person.getName());
        surnameTF.setText(person.getSurname());
        dateTF.setText(person.getDate_of_birth());
        sexTF.setText(person.getSex());
        peselTF.setText(person.getPesel());
        bloodTF.setText(person.getBlood_type());
        descriptionTA.setText(person.getDescription());
    }

    public String getDate() {
        Calendar calendar = Calendar.getInstance();
        String date = calendar.get(calendar.YEAR) + "-"
                + (calendar.get(calendar.MONTH) + 1) + "-"
                + calendar.get(calendar.DATE);
        String time = calendar.get(calendar.HOUR) + ":"
                + (calendar.get(calendar.MINUTE)) + ":"
                + calendar.get(calendar.SECOND);
        String dateTime = date + " " + time;
        return dateTime;
    }

    public void enableObjects() {
        fillFormLabel.setDisable(false);
        btnSave.setDisable(false);
        nameTF.setEditable(true);
        nameTF.setText("");
        surnameTF.setEditable(true);
        surnameTF.setText("");
        dateTF.setEditable(true);
        dateTF.setText("");
        sexTF.setEditable(true);
        sexTF.setText("");
        peselTF.setEditable(true);
        peselTF.setText("");
        bloodTF.setEditable(true);
        bloodTF.setText("");
        descriptionTA.setEditable(true);
        descriptionTA.setText("");
    }

    public void disableObjects() {
        fillFormLabel.setDisable(true);
        btnSave.setDisable(true);
        nameTF.setEditable(false);
        surnameTF.setEditable(false);
        dateTF.setEditable(false);
        sexTF.setEditable(false);
        peselTF.setEditable(false);
        bloodTF.setEditable(false);
        descriptionTA.setEditable(false);
    }

    @FXML
    public void onClick(ActionEvent event) {
        String name = nameTF.getText();
        String surname = surnameTF.getText();
        String dob = dateTF.getText();
        String sex = sexTF.getText();
        String pesel = peselTF.getText();
        String blood = bloodTF.getText();
        String description = descriptionTA.getText();
        if (checkConditions(name, surname, dob, sex, pesel, blood, description)) {
            Person person = new Person(name, surname, dob, sex, pesel, blood, description);
            rfidDAO.savePerson(person);
            Tag tag = new Tag(rfid_id, dateTime, rfidDAO.readPersonId(pesel));
            rfidDAO.saveTag(tag, person);
            disableObjects();
        }
    }

    @FXML
    public void onClickListen(ActionEvent event) throws IOException {
        rfidScanner = new RfidScanner();
        rfid_id = rfidScanner.getRfid_id();
        checkId();
        rfidScanner.close();
    }

    public boolean checkConditions(String name, String surname, String dob, String sex, String pesel, String blood, String description) {
        if (name.equals(null))
            return false;
        if (surname.equals(null))
            return false;
        if (dob.equals(null))
            return false;
        if (sex.equals(null))
            return false;
        if (pesel.equals(null))
            return false;
        if (blood.equals(null))
            return false;
        if (description.equals(null))
            return false;
        return true;
    }

}
