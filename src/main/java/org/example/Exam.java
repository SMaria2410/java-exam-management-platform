package org.example;

import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Exam {
    private String denumire;
    private LocalDateTime dataInceput;
    private LocalDateTime dataFinal;
    //lista de intrebari din examen
    private List<Question> intrebari = new ArrayList<Question>();
    //map intre numeStudent si scor
    private Map<String, Double> scoruri =  new HashMap<String, Double>();
    //lista cu numele studentilor care au dat submit
    public Set<String> getStudentiSubmitTrue() {
        Set<String> numeStudenti = scoruri.keySet();
        return numeStudenti;
    }
    //constructor
    public Exam(String denumire, LocalDateTime dataInceput, LocalDateTime dataFinal) {
        this.denumire = denumire;
        this.dataInceput = dataInceput;
        this.dataFinal = dataFinal;
    }

    //getters
    public String getDenumire() {
        return denumire;
    }
    public LocalDateTime getDataInceput() {
        return dataInceput;
    }
    public LocalDateTime getDataFinal() {
        return dataFinal;
    }
    public double getScor(String numeStudent) {
        if(scoruri.containsKey(numeStudent)){
            return scoruri.get(numeStudent);
        }
        return 0.0;
    }
    //setters
    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }
    public void setDataInceput(LocalDateTime dataInceput) {
        this.dataInceput = dataInceput;
    }
    public void setDataFinal(LocalDateTime dataFinal) {
        this.dataFinal = dataFinal;
    }
    public void setScor(String numeStudent, double scor) {
        scoruri.put(numeStudent, scor);
    }

    //functionalitate pentru adaugarea unei intrebari in examen
    public void addIntrebare(Question intrebare) {
        intrebari.add(intrebare);
    }

    //sortare intrebari (cronologic + dupa nume pt egalitate)
    public List<Question> sortIntrebari() {
        Comparator<Question> comparator = new Comparator<Question>() {
            public int  compare(Question intrebarea1, Question intrebarea2) {
                //daca intrebarea1 e prima
                if(intrebarea1.getData().isBefore(intrebarea2.getData())) {
                    return -1;
                }
                //daca intrebarea2 e prima
                else if(intrebarea1.getData().isAfter(intrebarea2.getData())) {
                    return 1;
                }
                //comparam dupa nume in caz de egalitate cronologica
                return intrebarea1.getNumePersoana().compareTo(intrebarea2.getNumePersoana());
            }
        };
        Collections.sort(intrebari, comparator);
        return intrebari;
    }

    //printare examen/generare raport (sortam dupa dificultate crescator si dupa alfabetic pentru egalitate)
    public List<Question> sortIntrebari2() {
        Comparator<Question> comparator = new Comparator<Question>() {
            public int  compare(Question intrebarea1, Question intrebarea2) {
                //daca dificultatea pt intrebarea1 este mai mica
                if(intrebarea1.getDificultate() < intrebarea2.getDificultate()) {
                    return -1;
                }
                //daca dificultatea pt intrebarea1 este mai mare
                if(intrebarea1.getDificultate() > intrebarea2.getDificultate()) {
                    return 1;
                }
                //comparam alfabetic daca sunt egale dificultatile
                return intrebarea1.getTextIntrebare().compareTo(intrebarea2.getTextIntrebare());
            }
        };
        Collections.sort(intrebari, comparator);
        return intrebari;
    }

    //sortam studentii descrescator dupa scor
    public List<String> sortScor() {
        List<String> studenti = new ArrayList<String>(scoruri.keySet());
        Comparator<String> comparator = new Comparator<String>() {
            public int compare(String student1, String student2) {
                //daca scorul studentului 1 e mai mare
                if(scoruri.get(student1) >  scoruri.get(student2)) {
                    return -1;
                }
                //daca scorul studentului 1 e mai mic
                else if(scoruri.get(student1) <  scoruri.get(student2)) {
                    return 1;
                }
                //pt egalitate
                return 0;
            }
        };
        Collections.sort(studenti, comparator);
        return studenti;
    }

    //pt testul 5 cu exception
    public void submitBoundsCheck(LocalDateTime timestamp, String numeStudent) throws SubmissionOutsideTimeIntervalException {
        //verificam daca e timestamp ul e out of bounds
        if(timestamp.isBefore(dataInceput) || timestamp.isAfter(dataFinal)) {
            throw new SubmissionOutsideTimeIntervalException(timestamp, numeStudent);
        }
    }

    //functie pt submitExam
    public double submitExam(LocalDateTime timestamp, String numeStudent, List<String> raspunsuri) throws SubmissionOutsideTimeIntervalException {
        //verificam bounds pt timestamp
        submitBoundsCheck(timestamp, numeStudent);
        //intrebarile sortate 2
        List<Question> listaIntrebari = sortIntrebari2();
        //scorul curent al studentului
        double scorStudent = 0.0;
        //scorul total al studentului
        double scorTotalStudent = 0.0;
        //iteram prin lista de intrebari
        for(int i = 0; i < listaIntrebari.size(); i++) {
            //luam intrebarea si raspunsul
            Question intrebare = listaIntrebari.get(i);
            String raspunsStudent = raspunsuri.get(i);
            //procentul punctajului fiecarui student
            double procentPunctajStudent = 0.0;
            //luam pe rand fiecare tip de intrebare
            //pt intrebare de tip OpenEndedQuestion
            if(intrebare instanceof OpenEndedQuestion) {
                //verificam corectitudinea raspunsului
                Correctness corectitudineOpenEnded;
                corectitudineOpenEnded = intrebare.checkAnswer(raspunsStudent);
                //calculam procentul de punctaj prin Gradable
                procentPunctajStudent = intrebare.getGrade(corectitudineOpenEnded);
            }
            //pt intrebare de tip FillInTheBlankQuestion
            else if(intrebare instanceof FillInTheBlankQuestion) {
                //verificam corectitudinea raspunsului
                Correctness corectitudineFillInTheBlank;
                corectitudineFillInTheBlank = intrebare.checkAnswer(raspunsStudent);
                //calculam procentul de punctaj prin Gradable
                procentPunctajStudent = intrebare.getGrade(corectitudineFillInTheBlank);
            }
            //pt intrebare de tip MultipleChoiceQuestion
            else if(intrebare instanceof MultipleChoiceQuestion) {
                //verificam corectitudinea raspunsului (de tip A, B, C sau D)
                ResponseOption raspunsMC = ResponseOption.A;
                //verificam tipul de raspuns
                if(raspunsStudent.equals("A")) {
                    raspunsMC = ResponseOption.A;
                }
                else if(raspunsStudent.equals("B")) {
                    raspunsMC = ResponseOption.B;
                }
                else if(raspunsStudent.equals("C")) {
                    raspunsMC = ResponseOption.C;
                }
                else if(raspunsStudent.equals("D")) {
                    raspunsMC = ResponseOption.D;
                }
                Correctness corectitudineMultipleChoice;
                corectitudineMultipleChoice = intrebare.checkAnswer(raspunsMC);
                //calculam procentul de punctaj prin Gradable
                procentPunctajStudent = intrebare.getGrade(corectitudineMultipleChoice);
            }
            //calculam punctajul final per intrebare
            scorStudent = procentPunctajStudent * intrebare.getPunctajMaxim();
            //calculam scorul total al studentului
            scorTotalStudent = scorTotalStudent + scorStudent;
        }
        //actualizam scorul total al studentului pentru examen
        setScor(numeStudent, scorTotalStudent);
        return  scorTotalStudent;
    }
}
