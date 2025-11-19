package edu.secourse.patientportal.controllers;
import edu.secourse.patientportal.models.*;
import edu.secourse.patientportal.services.AppointmentService;

import java.time.LocalDateTime;
import java.util.List;

public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }


    public boolean createAppointment(Appointment appointment) {
        try {

            boolean success = appointmentService.createAppointment(appointment);
            if (success) {

                System.out.println("Appointment created successfully");
                return true;
            }
            else {

                System.out.println("Failed to create appointment.");
                return false;
            }
        } catch (IllegalArgumentException e) {

            System.out.println("Error creating the appointment: " + e.getMessage());
            return false;
        }
    }


    public boolean cancelAppointment(int appointmentId) {

        boolean success = appointmentService.cancelAppointment(appointmentId);
        if (success) {

            System.out.println("Appointment cancelled successfully");
            return true;
        } else {
            System.out.println("Appointment not found");
            return false;
        }
    }

    //Changes details
    public void modifyAppointment(int appointmentId, Patient patient, Doctor doctor, LocalDateTime newDateTime) {

        try {
            boolean success = appointmentService.modifyAppointment(appointmentId, patient, doctor, newDateTime);
            if (success) {
                System.out.println("Appointment modified successfully");
            } else {
                System.out.println("Appointment not found");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error modifying appointment: " + e.getMessage());
        }
    }




        public void getAppointmentsForUser(User user) {
            try {
                List<Appointment> appointments = appointmentService.getAppointmentsForUser(user);

                if (appointments.isEmpty()) {

                    System.out.println("No appointments found for user: " + user.getUsername());
                } else {
                    System.out.println("Appointments for " + user.getUsername() + ":");
                    for (Appointment appointment : appointments) {
                        printAppointment(appointment);
                        System.out.println("----------------------");
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error retrieving appointments: " + e.getMessage());
            }
        }




    public void printAppointment(Appointment appointment) {

        if (appointment == null) {
            System.out.println("Error: Appointment not found.");
            return;
        }

        System.out.println("\nAppointment #" + appointment.getAppointmentId() +
                "\nPatient: " + appointment.getPatient().getUsername() +
                "\nDoctor: " + appointment.getDoctor().getUsername() +
                "\nDate and Time: " + appointment.getAppointmentDateTime() +
                "\nStatus: " + appointment.getStatus());
    }



}
