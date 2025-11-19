package edu.secourse.patientportal.models;

import java.time.LocalDateTime;

public class Appointment {

    private int appointmentId;
    private Patient patient;
    private Doctor doctor;
    //LocalDateTime.of(2025,11,1,8,30);
    private LocalDateTime appointmentDateTime;
    private Status status;


    public Status getStatus() {
        return status;
    }


    public enum Status {
        ACTIVE, CANCELLED
    }

    public Appointment(Patient patient, Doctor doctor, LocalDateTime appointmentDateTime) {

        this.patient = patient;
        this.doctor = doctor;
        this.appointmentDateTime = appointmentDateTime;
        this.status = Status.ACTIVE;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {

        this.appointmentId = appointmentId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public LocalDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }

    public void cancelAppointment() {
        this.status = Status.CANCELLED;
    }


}