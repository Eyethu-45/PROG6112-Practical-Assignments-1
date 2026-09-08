import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HospitalSystem hs = new HospitalSystem();

        while (true) {
            try {
                System.out.println("\n1.Register 2.Search 3.Display All 4.Allocate Bed 5.Release Bed 6.Layout 7.Reports 8.Sort by Surname 9.Exit");
                System.out.print("Choose: ");
                int choice = Integer.parseInt(sc.nextLine());

                if (choice == 1) {
                    System.out.print("ID: "); String id = sc.nextLine();
                    System.out.print("First Name: "); String fn = sc.nextLine();
                    System.out.print("Last Name: "); String ln = sc.nextLine();
                    System.out.print("Age: "); int age = Integer.parseInt(sc.nextLine());
                    System.out.print("Gender: "); String gender = sc.nextLine();
                    System.out.print("Condition: "); String cond = sc.nextLine();
                    System.out.print("Category (INPATIENT, OUTPATIENT, EMERGENCY): ");
                    PatientCategory cat = PatientCategory.valueOf(sc.nextLine().toUpperCase());

                    Patient p;
                    if (cat == PatientCategory.INPATIENT) {
                        p = new Inpatient(id, fn, ln, age, gender, cond, cat, "Ward-A", null);
                    } else {
                        p = new Patient(id, fn, ln, age, gender, cond, cat);
                    }

                    if (hs.registerPatient(p)) System.out.println("Registered");
                    else System.out.println("Duplicate ID!");
                }
                else if (choice == 2) {
                    System.out.print("Enter ID to search: ");
                    Patient p = hs.searchPatient(sc.nextLine());
                    if (p!= null) p.displayDetails(); else System.out.println("Not found");
                }
                else if (choice == 3) {
                    for (Patient p : hs.getAllPatients()) p.displayDetails();
                }
                else if (choice == 4) {
                    System.out.print("Enter Inpatient ID: ");
                    Patient p = hs.searchPatient(sc.nextLine());
                    if (p instanceof Inpatient) {
                        String bed = hs.allocateBed((Inpatient)p);
                        if (bed!= null) System.out.println("Allocated bed " + bed);
                        else System.out.println("No beds available!");
                    } else System.out.println("Only inpatients can get beds");
                }
                else if (choice == 5) {
                    System.out.print("Enter Bed No to release (e.g. B01): ");
                    if (hs.releaseBed(sc.nextLine())) System.out.println("Released");
                    else System.out.println("Bed not occupied");
                }
                else if (choice == 6) hs.displayWardLayout();
                else if (choice == 7) {
                    System.out.println("Total Patients: " + hs.getTotalPatients());
                    System.out.println("Occupied Beds: " + hs.getOccupiedCount());
                    System.out.println("Occupancy: " + hs.getOccupancyPercentage() + "%");
                    hs.displayAvailableBeds();
                    hs.displayOccupiedBeds();
                }
                else if (choice == 8) {
                    hs.sortBySurname();
                    System.out.println("Sorted by surname");
                }
                else if (choice == 9) break;

            } catch (Exception e) {
                System.out.println("Invalid input: " + e.getMessage()); // exception handling
            }
        }
        sc.close();
    }
}
