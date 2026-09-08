import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        HospitalSystem hospital = new HospitalSystem();

        int choice = -1;

        do {
            try {

                System.out.println("\n=================================");
                System.out.println("      MEDICARE HOSPITAL SYSTEM");
                System.out.println("=================================");
                System.out.println("1. Register Patient");
                System.out.println("2. Search Patient");
                System.out.println("3. Update Patient");
                System.out.println("4. Delete Patient");
                System.out.println("5. Display All Patients");
                System.out.println("6. Allocate Bed");
                System.out.println("7. Release Bed");
                System.out.println("8. Display Ward Layout");
                System.out.println("9. Display Available Beds");
                System.out.println("10. Display Occupied Beds");
                System.out.println("11. Display Reports");
                System.out.println("12. Sort Patients by Surname");
                System.out.println("13. Display Patient Categories");
                System.out.println("0. Exit");
                System.out.println("=================================");
                System.out.print("Enter your choice: ");

                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {

                    case 1:
                        registerPatient(scanner, hospital);
                        break;

                    case 2:
                        searchPatient(scanner, hospital);
                        break;

                    case 3:
                        updatePatient(scanner, hospital);
                        break;

                    case 4:
                        deletePatient(scanner, hospital);
                        break;

                    case 5:
                        hospital.displayAllPatients();
                        break;

                    case 6:
                        allocateBed(scanner, hospital);
                        break;

                    case 7:
                        releaseBed(scanner, hospital);
                        break;

                    case 8:
                        hospital.displayWardLayout();
                        break;

                    case 9:
                        hospital.displayAvailableBeds();
                        break;

                    case 10:
                        hospital.displayOccupiedBeds();
                        break;

                    case 11:
                        displayReports(hospital);
                        break;

                    case 12:
                        hospital.displaySortedPatients();
                        break;

                    case 13:
                        hospital.displayPatientCategories();
                        break;

                    case 0:
                        System.out.println("Thank you for using MediCare Hospital System.");
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

            } catch (NumberFormatException e) {

                System.out.println("Invalid input. Please enter a number.");

            } catch (Exception e) {

                System.out.println("An error occurred: " + e.getMessage());
            }

        } while (choice != 0);

        scanner.close();
    }

    // =========================
    // REGISTER PATIENT
    // =========================

    public static void registerPatient(Scanner scanner,
                                       HospitalSystem hospital) {

        System.out.println("\n===== REGISTER PATIENT =====");

        System.out.print("Patient ID: ");
        String patientId = scanner.nextLine();

        if (hospital.searchPatient(patientId) != null) {
            System.out.println("Patient ID already exists.");
            return;
        }

        System.out.print("First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Age: ");
        int age = Integer.parseInt(scanner.nextLine());

        System.out.print("Gender: ");
        String gender = scanner.nextLine();

        System.out.print("Medical Condition: ");
        String condition = scanner.nextLine();

        System.out.println("Patient Categories:");
        System.out.println("1. INPATIENT");
        System.out.println("2. OUTPATIENT");
        System.out.println("3. EMERGENCY");

        System.out.print("Choose category: ");
        int categoryChoice = Integer.parseInt(scanner.nextLine());

        PatientCategory category;

        if (categoryChoice == 1) {
            category = PatientCategory.INPATIENT;

        } else if (categoryChoice == 2) {
            category = PatientCategory.OUTPATIENT;

        } else if (categoryChoice == 3) {
            category = PatientCategory.EMERGENCY;

        } else {
            System.out.println("Invalid category.");
            return;
        }

        if (category == PatientCategory.INPATIENT) {

            Inpatient inpatient = new Inpatient(
                    patientId,
                    firstName,
                    lastName,
                    age,
                    gender,
                    condition,
                    category,
                    "Ward 1",
                    "Not Allocated"
            );

            if (hospital.registerPatient(inpatient)) {
                System.out.println("Patient registered successfully.");

                if (hospital.allocateBed(inpatient)) {
                    System.out.println(
                            "Bed allocated: "
                            + inpatient.getBedNumber()
                    );
                } else {
                    System.out.println(
                            "Patient registered, but no bed was available."
                    );
                }

            } else {
                System.out.println("Patient could not be registered.");
            }

        } else {

            Patient patient = new Patient(
                    patientId,
                    firstName,
                    lastName,
                    age,
                    gender,
                    condition,
                    category
            );

            if (hospital.registerPatient(patient)) {
                System.out.println("Patient registered successfully.");
            } else {
                System.out.println("Patient ID already exists.");
            }
        }
    }

    // =========================
    // SEARCH PATIENT
    // =========================

    public static void searchPatient(Scanner scanner,
                                     HospitalSystem hospital) {

        System.out.println("\n===== SEARCH PATIENT =====");

        System.out.print("Enter Patient ID: ");
        String patientId = scanner.nextLine();

        Patient patient = hospital.searchPatient(patientId);

        if (patient != null) {
            patient.displayDetails();
        } else {
            System.out.println("Patient not found.");
        }
    }

    // =========================
    // UPDATE PATIENT
    // =========================

    public static void updatePatient(Scanner scanner,
                                     HospitalSystem hospital) {

        System.out.println("\n===== UPDATE PATIENT =====");

        System.out.print("Enter Patient ID: ");
        String patientId = scanner.nextLine();

        Patient patient = hospital.searchPatient(patientId);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        System.out.print("New First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("New Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("New Age: ");
        int age = Integer.parseInt(scanner.nextLine());

        System.out.print("New Gender: ");
        String gender = scanner.nextLine();

        System.out.print("New Medical Condition: ");
        String condition = scanner.nextLine();

        System.out.println("1. INPATIENT");
        System.out.println("2. OUTPATIENT");
        System.out.println("3. EMERGENCY");

        System.out.print("New Category: ");
        int categoryChoice = Integer.parseInt(scanner.nextLine());

        PatientCategory category;

        if (categoryChoice == 1) {
            category = PatientCategory.INPATIENT;
        } else if (categoryChoice == 2) {
            category = PatientCategory.OUTPATIENT;
        } else if (categoryChoice == 3) {
            category = PatientCategory.EMERGENCY;
        } else {
            System.out.println("Invalid category.");
            return;
        }

        if (hospital.updatePatient(
                patientId,
                firstName,
                lastName,
                age,
                gender,
                condition,
                category)) {

            System.out.println("Patient updated successfully.");

        } else {
            System.out.println("Patient could not be updated.");
        }
    }

    // =========================
    // DELETE PATIENT
    // =========================

    public static void deletePatient(Scanner scanner,
                                     HospitalSystem hospital) {

        System.out.println("\n===== DELETE PATIENT =====");

        System.out.print("Enter Patient ID: ");
        String patientId = scanner.nextLine();

        if (hospital.deletePatient(patientId)) {
            hospital.releaseBed(patientId);
            System.out.println("Patient deleted successfully.");
        } else {
            System.out.println("Patient not found.");
        }
    }

    // =========================
    // ALLOCATE BED
    // =========================

    public static void allocateBed(Scanner scanner,
                                   HospitalSystem hospital) {

        System.out.println("\n===== ALLOCATE BED =====");

        System.out.print("Enter Inpatient ID: ");
        String patientId = scanner.nextLine();

        Patient patient = hospital.searchPatient(patientId);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        if (!(patient instanceof Inpatient)) {
            System.out.println(
                    "Only Inpatients can be allocated a bed."
            );
            return;
        }

        Inpatient inpatient = (Inpatient) patient;

        if (hospital.allocateBed(inpatient)) {
            System.out.println(
                    "Bed allocated successfully: "
                    + inpatient.getBedNumber()
            );
        } else {
            System.out.println("Bed could not be allocated.");
        }
    }

    // =========================
    // RELEASE BED
    // =========================

    public static void releaseBed(Scanner scanner,
                                   HospitalSystem hospital) {

        System.out.println("\n===== RELEASE BED =====");

        System.out.print("Enter Patient ID: ");
        String patientId = scanner.nextLine();

        if (hospital.releaseBed(patientId)) {
            System.out.println("Bed released successfully.");
        } else {
            System.out.println("Patient does not have an occupied bed.");
        }
    }

    // =========================
    // REPORTS
    // =========================

    public static void displayReports(HospitalSystem hospital) {

        System.out.println("\n=================================");
        System.out.println("          HOSPITAL REPORT");
        System.out.println("=================================");

        System.out.println(
                "Total registered patients: "
                + hospital.getTotalPatients()
        );

        System.out.println(
                "Total occupied beds: "
                + hospital.getOccupiedBeds()
        );

        System.out.println(
                "Total available beds: "
                + (20 - hospital.getOccupiedBeds())
        );

        System.out.println(
                "Ward occupancy: "
                + hospital.getOccupancyPercentage()
                + "%"
        );

        System.out.println("=================================");
    }
}
