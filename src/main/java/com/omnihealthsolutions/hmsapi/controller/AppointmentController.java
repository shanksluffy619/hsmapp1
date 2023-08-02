package com.omnihealthsolutions.hmsapi.controller;

import com.omnihealthsolutions.hmsapi.payload.AppointmentDTO;
import com.omnihealthsolutions.hmsapi.service.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients/{patientId}/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<AppointmentDTO> createAppointment(
            @PathVariable Long patientId,
            @RequestBody AppointmentDTO appointmentDTO
    ) {
        AppointmentDTO createdAppointment = appointmentService.createAppointment(patientId, appointmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAppointment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getAppointmentById(
            @PathVariable Long patientId,
            @PathVariable Long appointmentid
    ) {
        AppointmentDTO appointment = appointmentService.getAppointmentById(patientId, appointmentid);
        return ResponseEntity.ok(appointment);
    }

    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments(@PathVariable Long patientId) {
        List<AppointmentDTO> appointments = appointmentService.getAllAppointments(patientId);
        return ResponseEntity.ok(appointments);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(
            @PathVariable Long patientId,
            @PathVariable Long id
    ) {
        appointmentService.deleteAppointment(patientId, id);
        return ResponseEntity.noContent().build();
    }
}