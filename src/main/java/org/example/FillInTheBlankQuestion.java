package org.example;

import java.time.LocalDateTime;

public class FillInTheBlankQuestion extends Question {
    //atribut suplimentar
    private String raspunsCorect;

    //constructor
    public FillInTheBlankQuestion(String raspunsCorect, String textIntrebare, String numePersoana, LocalDateTime data, int dificultate, double punctajMaxim) {
        super(textIntrebare, numePersoana, data, dificultate, punctajMaxim);
        this.raspunsCorect = raspunsCorect;
    }

    //getter
    public String getRaspunsCorect() {
        return raspunsCorect;
    }
    //setter
    public void setRaspunsCorect(String raspunsCorect) {
        this.raspunsCorect = raspunsCorect;
    }

    //implementam checkAnswer definit in clasa parinte
    public <T> Correctness checkAnswer(T raspuns) {
        //facem cast la string
        String raspunsString = (String)raspuns;
        //pt raspuns corect
        if(raspuns.equals(raspunsCorect)) {
            return Correctness.CORECT;
        }
        //pt raspuns partial corect
        //verificam lungimea
        if(raspunsString.length() >= raspunsCorect.length() - 2 && raspunsString.length() <= raspunsCorect.length() + 2) {
            //verificam sa fie substring
            if(raspunsString.contains(raspunsCorect) || raspunsCorect.contains(raspunsString)) {
                return Correctness.PARTIALCORECT;
            }
        }
        //altfel e incorect
        return Correctness.INCORECT;
    }

    //implementam metoda din interfata
    public double getGrade(Correctness correctness) {
        //verificam corectitudinea si atribuim punctajul corespunzator
        //pt corect
        if(correctness.equals(Correctness.CORECT)) {
            return 1.0;
        }
        //pt partial corect
        if(correctness.equals(Correctness.PARTIALCORECT)) {
            return 0.5;
        }
        //pt incorect
        return 0.0;
    }
}
