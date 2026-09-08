import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class HospitalSystem {

    // Store all registered patients
    private ArrayList<Patient> patients;

    // 4 rows x 5 columns = 20 beds
    private String[][] beds;

    // Constructor
    public HospitalSystem() {
        patients = new ArrayList<>();
        beds = new String[4][5];

        // Give each bed a number
        int bedNumber = 1;

        for (int row = 0; row < beds.length; row++) {
            for (int column = 0; column < beds[row].length; column++) {
                beds[row][column] = "B" + String.format("%02d", bedNumber);
                bedNumber++;
            }
        }
    }

    // =========================
    // PATIENT MANAGEMENT
    // =========================

    // Register a new patient
    public boolean registerPatient(Patient patient) {

        // Prevent duplicate Patient IDs
        if (searchPatient(patient.getPatientId()) != null) {
            return false;
        }

        patients.add(patient);
        return true;
    }

    // Search patient by Patient ID
    public Patient searchPatient(String patientId) {

        for (Patient patient : patients) {

            if (patient.getPatientId().equalsIgnoreCase(patientId)) {
                return patient;
            }
        }

        return null;
    }

    // Update patient details
    public boolean updatePatient(String patientId, String firstName,
                                 String lastName, int age, String gender,
                                 String medicalCondition,
                                 PatientCategory category) {

        Patient patient = searchPatient(patientId);

        if (patient == null) {
            return false;
        }

        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setAge(age);
        patient.setGender(gender);
        patient.setMedicalCondition(medicalCondition);
        patient.setPatientCategory(category);

        return true;
    }

    // Delete a patient
    public boolean deletePatient(String patientId) {

        Patient patient = searchPatient(patientId);

        if (patient == null) {
            return false;
        }

        patients.remove(patient);
        return true;
    }

    // Display all patients
    public void displayAllPatients() {

        if (patients.isEmpty()) {
            System.out.println("No patients registered.");
            return;
        }

        for (Patient patient : patients) {
            patient.displayDetails();
            System.out.println("-----------------------------");
        }
    }

    // Get total number of patients
    public int getTotalPatients() {
        return patients.size();
    }

    // =========================
    // BED MANAGEMENT
    // =========================

    // Allocate the first available bed to an inpatient
    public boolean allocateBed(Inpatient inpatient) {

        // Only inpatients can receive beds
        if (inpatient.getPatientCategory() != PatientCategory.INPATIENT) {
            return false;
        }

        // Prevent allocating another bed to the same patient
        if (findBedByPatient(inpatient.getPatientId()) != null) {
            return false;
        }

        for (int row = 0; row < beds.length; row++) {

            for (int column = 0; column < beds[row].length; column++) {

                // An available bed starts with B
                // An occupied bed contains a Patient ID
                if (beds[row][column].startsWith("B")) {

                    String bedNumber = beds[row][column];

                    beds[row][column] = inpatient.getPatientId();

                    inpatient.setBedNumber(bedNumber);
                    inpatient.setWardNumber("Ward 1");

                    return true;
                }
            }
        }

        System.out.println("No beds available.");
        return false;
    }

    // Release a patient's bed
    public boolean releaseBed(String patientId) {

        for (int row = 0; row < beds.length; row++) {

            for (int column = 0; column < beds[row].length; column++) {

                if (beds[row][column].equals(patientId)) {

                    beds[row][column] =
                            "B" + String.format("%02d",
                            (row * 5) + column + 1);

                    return true;
                }
            }
        }

        return false;
    }

    // Find the bed occupied by a patient
    public String findBedByPatient(String patientId) {

        for (int row = 0; row < beds.length; row++) {

            for (int column = 0; column < beds[row].length; column++) {

                if (beds[row][column].equals(patientId)) {

                    return "B" + String.format("%02d",
                            (row * 5) + column + 1);
                }
            }
        }

        return null;
    }

    // Display complete ward layout
    public void displayWardLayout() {

        System.out.println("\n===== WARD LAYOUT =====");

        for (int row = 0; row < beds.length; row++) {

            for (int column = 0; column < beds[row].length; column++) {

                String bed = beds[row][column];

                if (bed.startsWith("B")) {
                    System.out.print("[" + bed + " Available] ");
                } else {
                    System.out.print("[" + bed + " Occupied] ");
                }
            }

            System.out.println();
        }
    }

    // Display available beds
    public void displayAvailableBeds() {

        System.out.println("\n===== AVAILABLE BEDS =====");

        boolean found = false;

        for (int row = 0; row < beds.length; row++) {

            for (int column = 0; column < beds[row].length; column++) {

                if (beds[row][column].startsWith("B")) {
                    System.out.print(beds[row][column] + " ");
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("No beds available.");
        } else {
            System.out.println();
        }
    }

    // Display occupied beds
    public void displayOccupiedBeds() {

        System.out.println("\n===== OCCUPIED BEDS =====");

        boolean found = false;

        for (int row = 0; row < beds.length; row++) {

            for (int column = 0; column < beds[row].length; column++) {

                if (!beds[row][column].startsWith("B")) {

                    System.out.println(
                            beds[row][column]
                            + " - Patient ID: "
                            + beds[row][column]);

                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("No occupied beds.");
        }
    }

    // Count occupied beds
    public int getOccupiedBeds() {

        int count = 0;

        for (int row = 0; row < beds.length; row++) {

            for (int column = 0; column < beds[row].length; column++) {

                if (!beds[row][column].startsWith("B")) {
                    count++;
                }
            }
        }

        return count;
    }

    // Calculate occupancy percentage
    public double getOccupancyPercentage() {

        return (getOccupiedBeds() / 20.0) * 100;
    }

    // =========================
    // SORTING
    // =========================

    // Sort patients by surname
    public Patient[] sortPatientsBySurname() {

        Patient[] patientArray = patients.toArray(new Patient[0]);

        Arrays.sort(patientArray,
                Comparator.comparing(Patient::getLastName));

        return patientArray;
    }

    // Display sorted patients
    public void displaySortedPatients() {

        Patient[] sortedPatients = sortPatientsBySurname();

        if (sortedPatients.length == 0) {
            System.out.println("No patients registered.");
            return;
        }

        System.out.println("\n===== PATIENTS SORTED BY SURNAME =====");

        for (Patient patient : sortedPatients) {
            patient.displayDetails();
            System.out.println("-----------------------------");
        }
    }

    // =========================
    // ENUM METHODS
    // =========================

    // Display all patient categories
    public void displayPatientCategories() {

        System.out.println("\n===== PATIENT CATEGORIES =====");

        for (PatientCategory category : PatientCategory.values()) {
            System.out.println(category);
        }
    }

    // Convert text into a PatientCategory
    public PatientCategory getCategory(String category) {

        return PatientCategory.valueOf(category.toUpperCase());
    }
}
