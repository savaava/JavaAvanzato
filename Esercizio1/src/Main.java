import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Anagrafica a = new Anagrafica();

//        Studente sEccezione = new Studente("SVSNDR0128","Andrea Savastano","062707904",29.1,CorsoDiStudi.LT);

        Studente s1 = new Studente("SVSNDR0128","Andrea Savastano","0612707904",29.1,CorsoDiStudi.LT);
        Studente s2 = new Studente("UASNDIUNAD","Luigi Marrone","880507940",25.1,CorsoDiStudi.PhD);
        Studente s3 = new Studente("IBSAD97656","MATTIA Sanzari","0622789792222",21.2,CorsoDiStudi.LM);

        Docente d1 = new Docente("IHJABEFIA22","D1","56787656","oop");
        Docente d2 = new Docente("JKMNJKMSAA1","D2","87656789","eng");
        Docente d3 = new Docente("ASIDNJAKAS1","D3","09876542","circuiti");

        a.aggiuntaMembroUnisa(s1);
        a.aggiuntaMembroUnisa(s2);
        a.aggiuntaMembroUnisa(s3);
        a.aggiuntaMembroUnisa(d1);
        a.aggiuntaMembroUnisa(d2);
        a.aggiuntaMembroUnisa(d3);

        System.out.println(a);

        a.rimozioneMembroUnisa(d3);
        a.rimozioneMembroUnisa(s3);

        System.out.println(a);

        a.writeCSV("anagrafica.csv"); /* 4 membri in totale */
        System.out.println(
                Anagrafica.readCSV("anagrafica.csv")
        );
    }
}