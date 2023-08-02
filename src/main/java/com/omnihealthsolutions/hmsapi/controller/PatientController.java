package com.omnihealthsolutions.hmsapi.controller;

import com.omnihealthsolutions.hmsapi.payload.PatientDTO;
import com.omnihealthsolutions.hmsapi.payload.PatientResponse;
import com.omnihealthsolutions.hmsapi.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/patients")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }
@PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createPatient(@Valid @RequestBody PatientDTO patientDTO, BindingResult result) {

        if(result.hasErrors()){
            ResponseEntity<?> tResponseEntity = new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
            return tResponseEntity;
        }else {
            PatientDTO createdPatient = patientService.createPatient(patientDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable Long id) {
        PatientDTO patient = patientService.getPatientById(id);
        return ResponseEntity.ok(patient);
    }

    @GetMapping
    public ResponseEntity<PatientResponse> getAllPatients(@RequestParam (name = "pageNo",required = false,defaultValue = "0") int pageNo,
                                                          @RequestParam(name = "pageSize",required = false,defaultValue = "2") int pageSize,
                                                          @RequestParam(name = "sortBy",required = false,defaultValue = "id") String sortBy,
                                                          @RequestParam(name = "sortDir",required = false,defaultValue = "asc") String sortDir




                                                           ) {
        PatientResponse allPatients = patientService.getAllPatients(pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(allPatients);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.softDeletePatient(id);

        ResponseEntity response = new ResponseEntity<>("deleted",HttpStatus.NO_CONTENT);
        return response;
//        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable long id,@RequestBody PatientDTO patientDTO){
        PatientDTO patientDTO1 = patientService.updatePatientById(id, patientDTO);
        ResponseEntity response = new ResponseEntity<>(patientDTO1,HttpStatus.OK);
        return response;
    }
}