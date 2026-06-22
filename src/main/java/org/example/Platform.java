package org.example;

import java.nio.charset.CoderResult;
import java.time.LocalDateTime;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class Platform {
    //numele testului
    private String numeTest;
    //map pentru numeStudent si Student
    private Map<String, Student> studenti = new HashMap<String, Student>();
    //map pentru numeExamen si Exam
    private Map<String, Exam> examene = new HashMap<String, Exam>();
    //pt console
    private FileWriter cw;

    //constructor
    public Platform(String numeTest) {
        this.numeTest = numeTest;
    }

    //getter
    public String getNumeTest() {
        return numeTest;
    }
    //setter
    public void setNumeTest(String numeTest) {
        this.numeTest = numeTest;
    }

    //pt comanda 1
    public void createExam(LocalDateTime timestamp, String name, LocalDateTime dataStart, LocalDateTime dataEnd) {
        //cream examenul
        Exam exam= new Exam(name, dataStart, dataEnd);
        //punem examenul in map
        examene.put(name, exam);
    }

    //pt comanda 2
    public void addQuestion(LocalDateTime timestamp, String tipIntrebare, String numeExamen, String autor, int dificultate, double punctajMaxim, String textIntrebare, String raspunsCorect) {
        //luam examenul din map cu get
        Exam examen = examene.get(numeExamen);
        //adaugam intrebarea in functie de tipul specificat
        Question intrebare = null;
        //pt open_ended
        if(tipIntrebare.equals("open_ended")) {
            intrebare = new OpenEndedQuestion(raspunsCorect, textIntrebare, autor, timestamp, dificultate, punctajMaxim);
        }
        //pt fill_in_the_blank
        else if(tipIntrebare.equals("fill_in_the_blank")) {
            intrebare = new FillInTheBlankQuestion(raspunsCorect, textIntrebare, autor, timestamp, dificultate, punctajMaxim);
        }
        //pt multiple_choice
        else if(tipIntrebare.equals("multiple_choice")) {
            //verificam tipul de raspuns
            ResponseOption raspunsCorectMC = ResponseOption.A;
            if(raspunsCorect.equals("A")) {
                raspunsCorectMC = ResponseOption.A;
            }
            else if(raspunsCorect.equals("B")) {
                raspunsCorectMC = ResponseOption.B;
            }
            else if(raspunsCorect.equals("C")) {
                raspunsCorectMC = ResponseOption.C;
            }
            else if(raspunsCorect.equals("D")) {
                raspunsCorectMC = ResponseOption.D;
            }
            //cream intrebarea cu campurile necesare
            intrebare = new MultipleChoiceQuestion(raspunsCorectMC, textIntrebare, autor, timestamp, dificultate, punctajMaxim);
        }
        examen.addIntrebare(intrebare);
    }

    //pt comanda 3
    public void listQuestionsHistory(LocalDateTime timestamp, String numeExamen) throws IOException {
        //luam examenul din map cu get
        Exam examen = examene.get(numeExamen);
        //fisierul pt output
        FileWriter fw = new FileWriter("src/main/resources/" + numeTest + "/questions_history_" + numeExamen + "_" + timestamp.format(DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm")) + ".out");
        //lista de intrebari sortata 1
        List<Question> listaIntrebari = examen.sortIntrebari();
        for(int i = 0; i < listaIntrebari.size(); i++) {
            Question intrebare  = listaIntrebari.get(i);
            //raspuns in functie de intrebare
            String raspuns = null;
            //pt OpenEndedQuestion
            if(intrebare instanceof OpenEndedQuestion) {
                //cast
                OpenEndedQuestion intrebareOpenEnded = (OpenEndedQuestion) intrebare;
                raspuns = intrebareOpenEnded.getRaspunsCorect();
            }
            //pt FillInTheBlankQuestion
            else if(intrebare instanceof FillInTheBlankQuestion) {
                //cast
                FillInTheBlankQuestion intrebareFillInTheBlank = (FillInTheBlankQuestion) intrebare;
                raspuns = intrebareFillInTheBlank.getRaspunsCorect();
            }
            //pt MultipleChoiceQuestion
            else if(intrebare instanceof MultipleChoiceQuestion) {
                //cast
                MultipleChoiceQuestion intrebareMultipleChoice = (MultipleChoiceQuestion) intrebare;
                ResponseOption raspunsInitial = intrebareMultipleChoice.getRaspunsCorect();
                //convertim la string
                raspuns = raspunsInitial.toString();
            }
            //data formatata
            LocalDateTime data = intrebare.getData();
            //formatter ul
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm");
            String dataFormatata = data.format(formatter);
            //scriem in out
            fw.write(dataFormatata + " | " + intrebare.getTextIntrebare() + " | " + raspuns + " | " + intrebare.getDificultate() + " | " + intrebare.getPunctajMaxim() + " | " + intrebare.getNumePersoana() + "\n");
        }
        //inchidem fisierul
        fw.close();
    }
    //pt comanda 4
    public void  printExam(LocalDateTime timestamp, String numeExamen) throws IOException {
        //luam examenul din map cu get
        Exam examen = examene.get(numeExamen);
        //fisierul pt output
        FileWriter fw = new FileWriter("src/main/resources/" + numeTest + "/print_exam_" + numeExamen + "_" + timestamp.format(DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm")) + ".out");
        //lista de intrebari sortata 2
        List<Question> listaIntrebari = examen.sortIntrebari2();
        for(int i = 0; i < listaIntrebari.size(); i++) {
            Question intrebare = listaIntrebari.get(i);
            //raspuns in functie de tipul de intrebare
            String raspuns = null;
            //pt OpenEndedQuestion
            if(intrebare instanceof OpenEndedQuestion) {
                //cast
                OpenEndedQuestion intrebareOpenEnded = (OpenEndedQuestion) intrebare;
                raspuns = intrebareOpenEnded.getRaspunsCorect();
            }
            //pt FillInTheBlankQuestion
            else if(intrebare instanceof FillInTheBlankQuestion) {
                //cast
                FillInTheBlankQuestion intrebareFillInTheBlank = (FillInTheBlankQuestion) intrebare;
                raspuns = intrebareFillInTheBlank.getRaspunsCorect();
            }
            //pt MultipleChoiceQuestion
            else if(intrebare instanceof MultipleChoiceQuestion) {
                //cast
                MultipleChoiceQuestion intrebareMultipleChoice = (MultipleChoiceQuestion) intrebare;
                ResponseOption raspunsInitial = intrebareMultipleChoice.getRaspunsCorect();
                //convertim la string
                raspuns = raspunsInitial.toString();
            }
            //scriem in out
            fw.write(intrebare.getPunctajMaxim() + " | " + intrebare.getTextIntrebare() + " | " + intrebare.getDificultate() + " | " + raspuns + "\n");
        }
        //inchidem fisierul
        fw.close();
    }
    //pt comanda 5
    public void registerStudent(LocalDateTime timestamp, String numeStudent, String grupa) {
        //cream obiectul de tip student
        Student student = new Student(numeStudent, grupa);
        //il punem in map
        studenti.put(numeStudent, student);
    }
    //pt comanda 6
    public void submitExam(LocalDateTime timestamp, String numeExamen, String numeStudent, List<String> raspunsuri) throws IOException {
        //luam examenul si studentul din map
        Exam examen = examene.get(numeExamen);
        Student student = studenti.get(numeStudent);
        double scorTotalStudent = 0.0;
        //apelam functia din Exam
        try {
            scorTotalStudent = examen.submitExam(timestamp, numeStudent, raspunsuri);
            //setam scorul total
            student.setScorStudent(numeExamen, scorTotalStudent);
        }
        catch (SubmissionOutsideTimeIntervalException e) {
            //setam scorul la 0.0
            examen.setScor(numeStudent, 0.0);
            student.setScorStudent(numeExamen, 0.0);
            //scriem in console.out
            if(cw == null) {
                cw = new FileWriter("src/main/resources/" + numeTest + "/console.out");
            }
            //String timestampFormatat = timestamp.format(DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm"));
            cw.write(e.toString() + "\n");
            return;
        }
    }
    //pt comanda 7
    public void showStudentScore(LocalDateTime timestamp, String numeStudent, String numeExamen) throws IOException {
        //cream console.out ul
        if(cw == null) {
            cw = new FileWriter("src/main/resources/" + numeTest + "/console.out");
        }
        //luam studentul si scorul
        Student student = studenti.get(numeStudent);
        double scorStudent = student.getScorStudent(numeExamen);
        //formatam timestamp ul
        String timestampFormatat = timestamp.format(DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm"));
        //formatam scorul
        String scorFinalStudent = String.format("%.2f", scorStudent);
        //scriem in console.out
        cw.write(timestampFormatat + " | The score of " + numeStudent + " for exam " + numeExamen + " is " + scorFinalStudent + "\n");
    }
    //pt comanda 8
    public void generateReport(LocalDateTime timestamp, String numeExamen) throws IOException {
        //luam examenul din map
        Exam examen = examene.get(numeExamen);
        //timestamp ul formatat
        String timestampFormatat = timestamp.format(DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm"));
        //fisierul de out
        FileWriter fw = new FileWriter("src/main/resources/" + numeTest + "/exam_report_" + numeExamen + "_" + timestampFormatat + ".out");
        //lista de studenti care trebuie sortata
        List<String> listaStudentiSubmitTrue = new ArrayList<>(examen.getStudentiSubmitTrue());
        //comparator pt sortare
        Comparator<String> comparator = new Comparator<String>() {
          public int compare(String student1, String student2) {
              //daca student1 este primul
              if(student1.compareTo(student2) < 0) {
                  return -1;
              }
              //daca student2 este primul
              else if(student1.compareTo(student2) > 0) {
                  return 1;
              }
              //pt cazul in care sunt egali
              return 0;
          }
        };
        Collections.sort(listaStudentiSubmitTrue, comparator);
        //scriem in out
        for(int i = 0; i < listaStudentiSubmitTrue.size(); i++) {
            //luam numele si scorul studentului curent
            String numeStudent = listaStudentiSubmitTrue.get(i);
            double scorStudent = examen.getScor(numeStudent);
            //formatam scorul
            String scorStudentFormatat = String.format("%.2f", scorStudent);
            //scriem output ul
            fw.write(numeStudent + " | " + scorStudentFormatat + "\n");
        }
        //inchidem fisierul
        fw.close();
    }
    //pt close
    public void exit() throws IOException {
        //inchidem consolewriter ul, daca a fost creat anterior
        if(cw != null) {
            cw.close();
        }
    }
}
