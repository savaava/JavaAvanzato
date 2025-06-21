package com.servicesproject.services;

import com.servicesproject.model.Utente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.Random;

public class MyService extends Service<ObservableList<Utente>> {
    private int num;

    @Override
    protected Task<ObservableList<Utente>> createTask() {
        
        return new Task<>() {
            @Override
            protected ObservableList<Utente> call() throws InterruptedException {
                ObservableList<Utente> utenteObservableList = FXCollections.observableArrayList();
                utenteObservableList.clear();
                updateValue(utenteObservableList);

                int iterations = num;
                updateTitle("Servizio-" + iterations);

                for (int i = 0; i < iterations; i++) {
                    utenteObservableList.add(new Utente(
                            "Utente-"+i,
                            new Random().nextInt(0,101))
                    );

                    updateProgress(i, iterations);

                    int progress = (int)(((double) i / iterations) * 100);
                    if(progress%5 == 0) {
                        updateValue(utenteObservableList);
                        updateMessage("Progresso: " + progress + "%");
                    }
                    
                    Thread.sleep(10);
                }

                if (!isCancelled()) {
                    updateProgress(iterations, iterations);
                    updateMessage("Progresso: 100% -> Servizio completato");
                    Thread.sleep(2000);
                }

                return utenteObservableList;
            }
        };
    }

    public void setNum(int value) {
        num = value;
    }
}