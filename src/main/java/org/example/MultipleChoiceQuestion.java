package org.example;

import java.time.LocalDateTime;

public class MultipleChoiceQuestion extends Question {
    //atribut suplimentar
    private ResponseOption raspunsCorect;

    //constructor
    public MultipleChoiceQuestion(ResponseOption raspunsCorect, String textIntrebare, String numePersoana, LocalDateTime data, int dificultate, double punctajMaxim) {
        super(textIntrebare, numePersoana, data, dificultate, punctajMaxim);
        this.raspunsCorect = raspunsCorect;
    }

    //getter
    public ResponseOption getRaspunsCorect() {
        return raspunsCorect;
    }
    //setter
    public void setRaspunsCorect(ResponseOption raspunsCorect) {
        this.raspunsCorect = raspunsCorect;
    }

    //implementam checkAnswer definit in clasa parinte
    public <T> Correctness checkAnswer(T raspuns) {
        //pt raspuns corect
        if(raspuns.equals(raspunsCorect)) {
            return Correctness.CORECT;
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
        //pt incorect
        return 0.0;
    }
}
