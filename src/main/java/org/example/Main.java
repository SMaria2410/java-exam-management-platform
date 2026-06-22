package org.example;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.util.StringTokenizer;

public class Main {
    //facem functie pentru parsare
    private static LocalDateTime parsedDate(String date) {
        //formatter ul este cel din tabelul de comenzi
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm");
        //returnam data parsata
        return LocalDateTime.parse(date, formatter);
    }
    public static void main(String[] args) throws IOException {
        //cream o platforma
        //numele testului ce trebuie executat
        String numeTest = args[0];
        Platform platform = new Platform(numeTest);
        //calea de unde citim input ul
        Scanner scanner = new Scanner(new File("src/main/resources/" + numeTest + "/input.in"));
        //cat timp mai avem input de citit
        while(scanner.hasNextLine()) {
            //trecem la urmatoarea linie
            String linieCurenta = scanner.nextLine();
            //impartim dupa pipe
            StringTokenizer tokenizer = new StringTokenizer(linieCurenta, "|");
            List<String> parametriComenzi = new ArrayList<>();
            while (tokenizer.hasMoreTokens()) {
                //adauagam in lista token ul curent dupa ce eliminam spatiile
                parametriComenzi.add(tokenizer.nextToken().trim());
            }
            //timestamp ul este primul element al fiecarei comenzi
            LocalDateTime timestamp = parsedDate(parametriComenzi.get(0));
            String comandaCurenta = parametriComenzi.get(1);
            //verificam care este comanda
            //pt comanda 1
            if(comandaCurenta.equals("create_exam")) {
                //parsam datele
                LocalDateTime dataStart = parsedDate(parametriComenzi.get(3));
                LocalDateTime dataEnd = parsedDate(parametriComenzi.get(4));
                //apelam functia de createExam din platforma
                platform.createExam(timestamp, parametriComenzi.get(2), dataStart, dataEnd);
            }
            //pt comanda 2
            else if(comandaCurenta.equals("add_question")) {
                //parsam datele
                int dificultate = Integer.parseInt(parametriComenzi.get(5));
                double punctajMaxim = Double.parseDouble(parametriComenzi.get(6));
                //apelam functia de addQuestion din platforma
                platform.addQuestion(timestamp, parametriComenzi.get(2), parametriComenzi.get(3), parametriComenzi.get(4), dificultate, punctajMaxim, parametriComenzi.get(7), parametriComenzi.get(8));
            }
            //pt comanda 3
            else if(comandaCurenta.equals("list_questions_history")) {
                //apelam functia de listQuestionsHistory din platforma
                platform.listQuestionsHistory(timestamp, parametriComenzi.get(2));
            }
            //pt comanda 4
            else if(comandaCurenta.equals("print_exam")) {
                //apelam functia de printExam din platfoma
                platform.printExam(timestamp, parametriComenzi.get(2));
            }
            //pt comanda 5
            else if(comandaCurenta.equals("register_student")) {
                //apelam functia de registerStudent din platforma
                platform.registerStudent(timestamp, parametriComenzi.get(2), parametriComenzi.get(3));
            }
            //pt comanda 6
            else if(comandaCurenta.equals("submit_exam")) {
                //facem lista de raspunsuri
                List<String> raspunsuri = new ArrayList<>();
                for(int i = 4; i < parametriComenzi.size(); i++) {
                    raspunsuri.add(parametriComenzi.get(i));
                }
                //apelam functia de submitExam din platforma
                platform.submitExam(timestamp, parametriComenzi.get(2), parametriComenzi.get(3), raspunsuri);
            }
            //pt comanda 7
            else if(comandaCurenta.equals("show_student_score")) {
                //apelam functia de showStudentScore din platforma
                platform.showStudentScore(timestamp, parametriComenzi.get(2), parametriComenzi.get(3));
            }
            //pt comanda 8
            else if(comandaCurenta.equals("generate_report")) {
                //apelam functia de generateReport din platforma
                platform.generateReport(timestamp, parametriComenzi.get(2));
            }
            //pt exit
            else if(comandaCurenta.equals("exit")) {
                break;
            }
        }
        //inchidem scanner si dam exit din platforma
        scanner.close();
        platform.exit();
    }
}