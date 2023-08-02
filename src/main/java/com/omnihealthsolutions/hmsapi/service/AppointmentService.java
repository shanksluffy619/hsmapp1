package com.omnihealthsolutions.hmsapi.service;

import com.omnihealthsolutions.hmsapi.payload.AppointmentDTO;

import java.util.List;

public interface AppointmentService {
    AppointmentDTO createAppointment(Long patientId, AppointmentDTO appointmentDTO);
    AppointmentDTO getAppointmentById(Long patientId, Long appointmentId);
    List<AppointmentDTO> getAllAppointments(Long patientId);
    void deleteAppointment(Long patientId, Long appointmentId);
}