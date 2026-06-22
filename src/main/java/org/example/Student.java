package org.example;

import java.util.HashMap;
import java.util.Map;

public class Student {
    private String numeStudent;
    private String grupaStudent;
    //map pentru numeExamen si scor
    private Map<String, Double> examene =  new HashMap<String, Double>();

    //constructor
    public Student(String numeStudent, String grupaStudent) {
        this.numeStudent = numeStudent;
        this.grupaStudent = grupaStudent;
    }

    //getters
    public String getNumeStudent() {
        return numeStudent;
    }
    public String getGrupaStudent() {
        return grupaStudent;
    }
    public double getScorStudent(String numeExamen) {
        if(examene.containsKey(numeExamen)) {
            return examene.get(numeExamen);
        }
        return 0.0;
    }
    //setters
    public void setNumeStudent(String numeStudent) {
        this.numeStudent = numeStudent;
    }
    public void setGrupaStudent(String grupaStudent) {
        this.grupaStudent = grupaStudent;
    }
    public void setScorStudent(String numeExamen, double scor) {
        examene.put(numeExamen, scor);
    }
}
