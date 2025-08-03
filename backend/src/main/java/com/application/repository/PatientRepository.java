package com.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.application.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, String> {
    // Optional: for updating or fetching by email if needed
    Patient findByEmail(String email);
}
