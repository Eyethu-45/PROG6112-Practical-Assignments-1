/**
 * PROG6112 - Inpatient extends Patient
 * REFERENCE: Farrell, 2023 - Using super() and overriding
 */
public class Inpatient extends Patient {
    private String wardNumber;
    private String bedNumber;

    public Inpatient(String patientId, String firstName, String lastName, int age,
                     String gender, String medicalCondition, PatientCategory patientCategory,
                     String wardNumber, String bedNumber) {
        super(patientId, firstName, lastName, age, gender, medicalCondition, patientCategory);
        this.wardNumber = wardNumber;
        this.bedNumber = bedNumber;
    }

    public String getWardNumber() { return wardNumber; }
    public String getBedNumber() { return bedNumber; }
    public void setWardNumber(String wardNumber) { this.wardNumber = wardNumber; }
    public void setBedNumber(String bedNumber) { this.bedNumber = bedNumber; }

    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println(" -> Ward: " + wardNumber + " | Bed: " + bedNumber);
    }
}
