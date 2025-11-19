package edu.secourse.patientportal.services;

import edu.secourse.patientportal.models.Appointment;
import edu.secourse.patientportal.models.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class AppointmentService {

    ArrayList<Appointment> appointments = new ArrayList<Appointment>();


    private int nextId = 1;

    public boolean createAppointment(Appointment appointment) {

        for (Appointment existingAppointment : appointments) {

            boolean samePatient = existingAppointment.getPatient().equals(appointment.getPatient());
            boolean sameDoctor = existingAppointment.getDoctor().equals(appointment.getDoctor());
            boolean sameTime = existingAppointment.getAppointmentDateTime()
                    .truncatedTo(ChronoUnit.MINUTES)
                    .equals(appointment.getAppointmentDateTime().truncatedTo(ChronoUnit.MINUTES));

            if (samePatient && sameDoctor && sameTime) {

                System.out.println("Error: Appointment already exists for this patient, doctor, and that time.");

                return false;
            }
        }

        // Validation checks
        if (appointment.getPatient() == null) {

            throw new IllegalArgumentException("Appointment must have a patient");
        }

        if (appointment.getDoctor() == null) {

            throw new IllegalArgumentException("Appointment must have a doctor");
        }

        if (appointment.getAppointmentDateTime() == null || appointment.getAppointmentDateTime().toLocalDate().isBefore(LocalDate.now())) {

            throw new IllegalArgumentException("Appointment date must be today or later");
        }


        appointment.setAppointmentId(nextId++);
        appointments.add(appointment);

        System.out.println("Appointment created successfully with ID: " + appointment.getAppointmentId());
        return true;
    }







    public boolean cancelAppointment(int appointmentId) {
        for (int i = 0; i < appointments.size(); i++) {

            Appointment appointment = appointments.get(i);

            if (appointment.getAppointmentId() == appointmentId) {

                appointment.setStatus(Appointment.Status.CANCELLED);

                return true;
            }
        }

        return false;
    }



    public boolean modifyAppointment(int appointmentId, Patient patient, Doctor doctor, LocalDateTime newDateTime) {


        for (int i = 0; i < appointments.size(); i++) {

            Appointment appointment = appointments.get(i);

            if (appointment.getAppointmentId() == appointmentId) {

                appointment.setPatient(patient);
                appointment.setDoctor(doctor);
                appointment.setAppointmentDateTime(newDateTime);
                appointment.setStatus(Appointment.Status.ACTIVE);
                return true;
            }
        }
        return false;
    }


    public List<Appointment> getAppointmentsForUser(User user) {

        List<Appointment> result = new ArrayList<>();

        for (Appointment appointment : appointments) {

            if (user instanceof Patient && appointment.getPatient().equals(user)) {

                result.add(appointment);
            } else if (user instanceof Doctor && appointment.getDoctor().equals(user)) {

                result.add(appointment);
            }
        }
        return result;
    }










}