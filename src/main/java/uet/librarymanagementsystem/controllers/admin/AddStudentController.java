package uet.librarymanagementsystem.controllers.admin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import uet.librarymanagementsystem.services.userServices.AddStudentService;
import uet.librarymanagementsystem.util.ValidationLabelUtil;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddStudentController implements Initializable {

    private boolean isAdded = false;
    private final ValidationLabelUtil validationUtil = new ValidationLabelUtil();

    @FXML
    private DatePicker fieldBirthdayStudent;

    @FXML
    private TextField fieldEmailStudent, fieldIDStudent, fieldNameStudent, fieldPhoneStudent;

    @FXML
    private Label statusLabel, idValidLabel, nameValidLabel, birthValidLabel, phoneValidLabel, emailValidLabel;

    @FXML
    private Label checkInforId, checkInforName, checkInforBirthday, checkInforPhone, checkInforEmail, checkInforPassword;

    /**
     * Validates inputs and populates information labels if valid.
     * @param event the mouse event triggering this action
     */
    @FXML
    void addStudentButtonOnClick(MouseEvent event) {
        if (validateInputs()) {
            populateCheckInforLabels();
            updateStatus("Information loaded. Please check and press 'Save' to save.", "green");
            isAdded = true;
            clearFields();
        } else {
            updateStatus("Please fill in all fields correctly.", "red");
            isAdded = false;
        }
    }

    /**
     * Validates all input fields including ID, Name, Date of Birth, Phone, and Email.
     * @return true if all inputs are valid, false otherwise
     */
    private boolean validateInputs() {
        return validateField(fieldIDStudent, idValidLabel, ValidationLabelUtil.ValidationType.EMPTY)
                & validateField(fieldNameStudent, nameValidLabel, ValidationLabelUtil.ValidationType.EMPTY)
                & validateDateField(fieldBirthdayStudent, birthValidLabel)
                & validateField(fieldPhoneStudent, phoneValidLabel, ValidationLabelUtil.ValidationType.PHONE)
                & validateField(fieldEmailStudent, emailValidLabel, ValidationLabelUtil.ValidationType.EMAIL);
    }

    /**
     * Validates a TextField based on a specific validation type.
     * @param field the TextField to validate
     * @param label the Label to display validation messages
     * @param type the validation type
     * @return true if the field is valid, false otherwise
     */
    private boolean validateField(TextField field, Label label, ValidationLabelUtil.ValidationType type) {
        String errorMessage = validationUtil.validateField(field.getText(), type);
        updateValidationLabel(label, errorMessage);
        return errorMessage.isEmpty();
    }

    /**
     * Validates a DatePicker input.
     * @param datePicker the DatePicker to validate
     * @param label the Label to display validation messages
     * @return true if the date is valid, false otherwise
     */
    private boolean validateDateField(DatePicker datePicker, Label label) {
        LocalDate date = datePicker.getValue();
        String errorMessage = (date == null) ? "Date cannot be empty." : "";
        updateValidationLabel(label, errorMessage);
        return errorMessage.isEmpty();
    }

    /**
     * Updates the validation label with an error message.
     * @param label the Label to update
     * @param errorMessage the error message to display
     */
    private void updateValidationLabel(Label label, String errorMessage) {
        label.setText(errorMessage);
        label.setVisible(!errorMessage.isEmpty());
    }

    /**
     * Sets default password to the student's ID.
     */
    private void populateCheckInforLabels() {
        String formattedDob = fieldBirthdayStudent.getValue() != null
                ? fieldBirthdayStudent.getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
                : "";

        checkInforId.setText(fieldIDStudent.getText());
        checkInforName.setText(fieldNameStudent.getText());
        checkInforBirthday.setText(formattedDob);
        checkInforPhone.setText(fieldPhoneStudent.getText());
        checkInforEmail.setText(fieldEmailStudent.getText());
        checkInforPassword.setText(fieldIDStudent.getText()); // Default password is the student ID
    }

    /**
     * Saves the student information.
     */
    @FXML
    private void saveStudentButtonOnClick() {
        if (isAdded) {
            String result = AddStudentService.addStudent(
                    checkInforId.getText(),
                    checkInforName.getText(),
                    checkInforBirthday.getText(),
                    checkInforPhone.getText(),
                    checkInforEmail.getText()
            );
            updateStatus(result, result.contains("success") ? "green" : "red");
        } else {
            updateStatus("Please fill in all valid information.", "red");
        }
    }

    /**
     * Clears all input fields and information labels.
     * @param event the mouse event triggering this action
     */
    @FXML
    void removeStudentButtonOnClick(MouseEvent event) {
        isAdded = false;
        clearFields();
        clearCheckInforLabels();
        updateStatus("Information has been removed.", "red");
    }

    /**
     * Updates the status label with a message and color.
     * @param message the message to display
     * @param color the color of the text (e.g., "red", "green")
     */
    private void updateStatus(String message, String color) {
        statusLabel.setText(message);
        statusLabel.setStyle("-fx-text-fill: " + color + ";");
    }

    /**
     * Clears all input fields in the form.
     */
    private void clearFields() {
        fieldIDStudent.clear();
        fieldNameStudent.clear();
        fieldBirthdayStudent.setValue(null);
        fieldPhoneStudent.clear();
        fieldEmailStudent.clear();
    }

    /**
     * Clears all information labels to their default "no information" state.
     */
    private void clearCheckInforLabels() {
        String emptyMessage = "no information available";
        checkInforId.setText(emptyMessage);
        checkInforName.setText(emptyMessage);
        checkInforBirthday.setText(emptyMessage);
        checkInforPhone.setText(emptyMessage);
        checkInforEmail.setText(emptyMessage);
        checkInforPassword.setText(emptyMessage);
    }

    /**
     * Initializes the controller by setting up validation listeners for input fields.
     * @param url the location of the FXML file
     * @param resourceBundle the resources used to localize the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupValidationListeners();
    }

    /**
     * Sets up validation listeners for all input fields.
     */
    private void setupValidationListeners() {
        fieldIDStudent.textProperty().addListener((obs, oldVal, newVal) ->
                validateField(fieldIDStudent, idValidLabel, ValidationLabelUtil.ValidationType.EMPTY));

        fieldNameStudent.textProperty().addListener((obs, oldVal, newVal) ->
                validateField(fieldNameStudent, nameValidLabel, ValidationLabelUtil.ValidationType.EMPTY));

        fieldBirthdayStudent.getEditor().textProperty().addListener((obs, oldVal, newVal) -> {
            String error = validationUtil.validateDateFormat(newVal);
            updateValidationLabel(birthValidLabel, error);
        });

        fieldPhoneStudent.textProperty().addListener((obs, oldVal, newVal) ->
                validateField(fieldPhoneStudent, phoneValidLabel, ValidationLabelUtil.ValidationType.PHONE));

        fieldEmailStudent.textProperty().addListener((obs, oldVal, newVal) ->
                validateField(fieldEmailStudent, emailValidLabel, ValidationLabelUtil.ValidationType.EMAIL));
    }

}
