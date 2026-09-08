import java.util.ArrayList;

/**
 * PROG6112 - Hospital System Logic
 * REFERENCE: Coronel & Morris concepts adapted to Java - 2D arrays, ArrayList, nested loops
 */
public class HospitalSystem {
    private ArrayList<Patient> patients = new ArrayList<>();
    private String[][] ward = new String[4][5]; // 4x5 = 20 beds
    private boolean[][] occupied = new boolean[4][5];

    public HospitalSystem() {
        int count = 1;
        for (int i = 0; i < 4; i++) { // nested loops for 2D array (Farrell, 2023)
            for (int j = 0; j < 5; j++) {
                ward[i][j] = String.format("B%02d", count++);
                occupied[i][j] = false;
            }
        }
    }

    // Feature 1: Register with duplicate check
    public boolean registerPatient(Patient p) {
        for (Patient existing : patients) {
            if (existing.getPatientId().equalsIgnoreCase(p.getPatientId())) {
                return false; // prevent duplicate ID
            }
        }
        patients.add(p);
        return true;
    }

    public Patient searchPatient(String id) {
        for (Patient p : patients) {
            if (p.getPatientId().equalsIgnoreCase(id)) return p;
        }
        return null;
    }

    public boolean deletePatient(String id) {
        Patient p = searchPatient(id);
        if (p!= null) {
            if (p instanceof Inpatient) {
                releaseBed(((Inpatient) p).getBedNumber());
            }
            patients.remove(p);
            return true;
        }
        return false;
    }

    // Feature 2: Bed Management
    public String allocateBed(Inpatient inpatient) {
        if (inpatient.getPatientCategory()!= PatientCategory.INPATIENT) return null;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (!occupied[i][j]) {
                    occupied[i][j] = true;
                    String bedNo = ward[i][j];
                    inpatient.setBedNumber(bedNo);
                    return bedNo;
                }
            }
        }
        return null; // no beds available
    }

    public boolean releaseBed(String bedNo) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (ward[i][j].equals(bedNo) && occupied[i][j]) {
                    occupied[i][j] = false;
                    return true;
                }
            }
        }
        return false;
    }

    public void displayWardLayout() {
        System.out.println("\n--- WARD LAYOUT (4x5) ---");
        for (int i = 0; i < ward.length; i++) {
            for (int j = 0; j < ward[i].length; j++) {
                String status = occupied[i][j]? "[X]" : "[ ]";
                System.out.print(ward[i][j] + status + " ");
            }
            System.out.println();
        }
    }

    public void displayAvailableBeds() {
        System.out.print("Available: ");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (!occupied[i][j]) System.out.print(ward[i][j] + " ");
            }
        }
        System.out.println();
    }

    public void displayOccupiedBeds() {
        System.out.print("Occupied: ");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (occupied[i][j]) System.out.print(ward[i][j] + " ");
            }
        }
        System.out.println();
    }

    // Feature 3: Reports
    public int getTotalPatients() { return patients.size(); }
    public int getOccupiedCount() {
        int count = 0;
        for (boolean[] row : occupied) {
            for (boolean b : row) if (b) count++;
        }
        return count;
    }
    public double getOccupancyPercentage() { return (getOccupiedCount() / 20.0) * 100; }
    public ArrayList<Patient> getAllPatients() { return patients; }
    public String[][] getWard() { return ward; }

    // Sorting by surname
    public void sortBySurname() {
        patients.sort((a, b) -> a.getLastName().compareToIgnoreCase(b.getLastName()));
    }
}
