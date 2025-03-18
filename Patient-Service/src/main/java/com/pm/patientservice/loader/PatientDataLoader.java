package com.pm.patientservice.loader;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import com.pm.patientservice.service.PatientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PatientDataLoader implements CommandLineRunner {
    private final PatientRepository patientRepository;

    public PatientDataLoader(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }
    @Override
    public void run(String... args) {
        loadSamplePatients();
    }
    @Transactional
    public void loadSamplePatients() {
        if (patientRepository.count() == 0) {
            List<Patient> patients = List.of(
                    new Patient("John Doe", "john.doe@example.com", "123 Main St, Springfield",
                            LocalDate.of(1985, 6, 15), LocalDate.of(2024, 1, 10)),

                    new Patient("Jane Smith", "jane.smith@example.com", "456 Elm St, Shelbyville",
                            LocalDate.of(1990, 9, 23), LocalDate.of(2023, 12, 1)),

                    new Patient("Alice Johnson", "alice.johnson@example.com", "789 Oak St, Capital City",
                            LocalDate.of(1978, 3, 12), LocalDate.of(2022, 6, 20)),

                    new Patient("Bob Brown", "bob.brown@example.com", "321 Pine St, Springfield",
                            LocalDate.of(1982, 11, 30), LocalDate.of(2023, 5, 14)),

                    new Patient("Emily Davis", "emily.davis@example.com", "654 Maple St, Shelbyville",
                            LocalDate.of(1995, 2, 5), LocalDate.of(2024, 3, 1))
            );

            patientRepository.saveAll(patients);
            System.out.println("✅ Sample patients inserted successfully.");
        } else {
            System.out.println("ℹ️ Patient data already exists. Skipping insertion.");
        }
    }
}
