package com.pm.patientservice.controller;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.dto.validators.CreatePatientValidationGroup;
import com.pm.patientservice.service.PatientService;
import jakarta.validation.groups.Default;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {
        logger.info("Start: Fetching all patients");

        List<PatientResponseDTO> patientResponseDTOS = patientService.getPatients();

        logger.info("End: Successfully fetched {} patients", patientResponseDTOS.size());
        return new ResponseEntity<>(patientResponseDTOS, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<PatientResponseDTO> createPatient(
            @Validated({Default.class, CreatePatientValidationGroup.class}) @RequestBody PatientRequestDTO patientRequestDTO) {

        logger.info("Start: Creating a new patient with data: {}", patientRequestDTO);

        PatientResponseDTO response = patientService.createPatient(patientRequestDTO);

        logger.info("End: Successfully created patient with ID: {}", response.getId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(
            @PathVariable UUID id,
            @Validated(Default.class) @RequestBody PatientRequestDTO patientRequestDTO) {

        logger.info("Start: Updating patient with ID: {}", id);

        PatientResponseDTO response = patientService.updatePatient(id, patientRequestDTO);

        logger.info("End: Successfully updated patient with ID: {}", id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

