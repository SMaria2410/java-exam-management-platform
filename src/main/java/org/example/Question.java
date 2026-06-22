package org.example;

import java.time.LocalDateTime;

public abstract class Question implements Gradable {
    private String textIntrebare;
    private String numePersoana;
    private LocalDateTime data;
    private int dificultate;
    private double punctajMaxim;

    //constructor
    public Question(String textIntrebare, String numePersoana, LocalDateTime data, int dificultate, double punctajMaxim) {
        this.textIntrebare = textIntrebare;
        this.numePersoana = numePersoana;
        this.data = data;
        this.dificultate = dificultate;
        this.punctajMaxim = punctajMaxim;
    }

    //getters
    public String getTextIntrebare() {
        return textIntrebare;
    }
    public String getNumePersoana() {
        return numePersoana;
    }
    public LocalDateTime getData() {
        return data;
    }
    public int getDificultate() {
        return dificultate;
    }
    public double getPunctajMaxim() {
        return punctajMaxim;
    }

    //setters
    public void setTextIntrebare(String textIntrebare) {
        this.textIntrebare = textIntrebare;
    }
    public void setNumePersoana(String numePersoana) {
        this.numePersoana = numePersoana;
    }
    public void setData(LocalDateTime data) {
        this.data = data;
    }
    public void setDificultate(int dificultate) {
        this.dificultate = dificultate;
    }
    public void setPunctajMaxim(double punctajMaxim) {
        this.punctajMaxim = punctajMaxim;
    }

    //metoda abstracta pentru verificarea raspusului (cu input generic)
    public abstract <T> Correctness checkAnswer(T raspuns);


}
