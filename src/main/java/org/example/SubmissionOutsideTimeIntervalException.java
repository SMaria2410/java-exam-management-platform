package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SubmissionOutsideTimeIntervalException extends Exception {
    private final LocalDateTime timestamp;
    private final String numeStudent;

    //cosntructor
    public SubmissionOutsideTimeIntervalException(LocalDateTime timestamp, String numeStudent) {
        this.timestamp = timestamp;
        this.numeStudent = numeStudent;
    }

    //getters
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public String getNumeStudent() {
        return numeStudent;
    }

    //metoda toString
    public String toString() {
        String timestampFormatat = timestamp.format(DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm"));
        return timestampFormatat + " | Submission outside of time interval for student " + numeStudent;
    }
}
