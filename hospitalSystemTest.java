import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit Tests - REFERENCE: IIE PROG6112 LU3 Unit Testing
 */
public class HospitalSystemTest {

    @Test
    public void testRegisterPatient() {
        HospitalSystem hs = new HospitalSystem();
        Patient p = new Patient("P01", "John", "Doe", 30, "M", "Flu", PatientCategory.OUTPATIENT);
        assertTrue(hs.registerPatient(p));
    }

    @Test
    public void testPreventDuplicateId() {
        HospitalSystem hs = new HospitalSystem();
        Patient p1 = new Patient("P01", "John", "Doe", 30, "M", "Flu", PatientCategory.OUTPATIENT);
        Patient p2 = new Patient("P01", "Jane", "Doe", 25, "F", "Cold", PatientCategory.OUTPATIENT);
        hs.registerPatient(p1);
        assertFalse(hs.registerPatient(p2));
    }

    @Test
    public void testAllocateAndReleaseBed() {
        HospitalSystem hs = new HospitalSystem();
        Inpatient ip = new Inpatient("P02", "A", "B", 40, "M", "Fever", PatientCategory.INPATIENT, "Ward-A", null);
        hs.registerPatient(ip);
        String bed = hs.allocateBed(ip);
        assertNotNull(bed);
        assertTrue(hs.releaseBed(bed));
    }

    @Test
    public void testPreventAllocationWhenFull() {
        HospitalSystem hs = new HospitalSystem();
        for (int i = 0; i < 20; i++) {
            Inpatient ip = new Inpatient("P" + i, "F" + i, "L" + i, 20, "M", "C", PatientCategory.INPATIENT, "Ward-A", null);
            hs.registerPatient(ip);
            hs.allocateBed(ip);
        }
        Inpatient extra = new Inpatient("P99", "Extra", "Patient", 20, "M", "C", PatientCategory.INPATIENT, "Ward-A", null);
        assertNull(hs.allocateBed(extra));
    }
}
