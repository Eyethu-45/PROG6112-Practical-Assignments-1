public class Inpatient extends Patient {

    private String wardNumber;
    private String bedNumber;

    // Constructor
    public Inpatient(String patientId, String firstName, String lastName,
                     int age, String gender, String medicalCondition,
                     PatientCategory patientCategory,
                     String wardNumber, String bedNumber) {

        super(patientId, firstName, lastName, age, gender,
              medicalCondition, patientCategory);

        this.wardNumber = wardNumber;
        this.bedNumber = bedNumber;
    }

    // Getters
    public String getWardNumber() {
        return wardNumber;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    // Setters
    public void setWardNumber(String wardNumber) {
        this.wardNumber = wardNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    // Override displayDetails()
    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Ward Number: " + wardNumber);
        System.out.println("Bed Number: " + bedNumber);
    }
}
