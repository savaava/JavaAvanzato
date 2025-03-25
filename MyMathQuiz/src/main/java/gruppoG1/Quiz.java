package gruppoG1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;

public class Quiz {
    private final ObservableList<NumericQuestionAttempt> quiz;
    private final String playerName;
    private final String playerSurname;

    public Quiz(String playerName, String playerSurname) {
        this.playerName = playerName;
        this.playerSurname = playerSurname;
        quiz = FXCollections.observableArrayList();
    }

    public ObservableList<NumericQuestionAttempt> getQuiz() {
        return quiz;
    }
    public String getPlayerName(){
        return playerName;
    }
    public String getPlayerSurname(){
        return playerSurname;
    }

    public void addQuestion(NumericQuestionAttempt numericQuestionAttempt) {
        this.quiz.add(numericQuestionAttempt);
    }

    public void exportTXT(File savingFile) throws IOException {
        try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(savingFile)))){
            pw.println("MATH QUIZ di " + playerName + " " + playerSurname);
            pw.println(getCorrectAnswers() + "/" + quiz.size());
            //TODO: completare
            //STAMPA PERCENTUALE RISPOSTE CORRETTE
            //STAMPA DOMANDA CON RISPOSTA UTENTE E CORRETTO o SBAGLIATO

            int currentCounter = 0;
            for(NumericQuestionAttempt nqa : quiz) {
                currentCounter++;
                pw.println("Domanda " + currentCounter + " -> Risposta utente: " + nqa.getGivenAnswer() + " Esito: " + nqa.getResult());
            }
            float result = Math.round(((float)getCorrectAnswers()/quiz.size())*100);
            pw.println("Risposte corrette:" + result + "%");
        } catch(IOException e) {
            throw new IOException(e);
        }
    }

    public int getCorrectAnswers() {
        //DA IMPLEMENTARE UTILIZZANDO isCorrect di NumericQuestionAttempt, iterando su collezione
        int correctCounter = 0;
        for(NumericQuestionAttempt nqa : quiz){
            if(nqa.isCorrect())
                correctCounter++;
        }
        return correctCounter;
    }
}
