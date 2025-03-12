import java.io.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class Anagrafica {
    private final Map<String,MembroUnisa> membriUnisa;

    public Anagrafica() {
        membriUnisa = new HashMap<>();
    }

    public void aggiuntaMembroUnisa(MembroUnisa m){
        membriUnisa.put(m.getMatricola(),m);
    }
    public void rimozioneMembroUnisa(MembroUnisa m){
        membriUnisa.remove(m.getMatricola());
    }

    public static Anagrafica readCSV(String pathFile) throws IOException {
        Anagrafica aOut = new Anagrafica();

        try(Scanner scanner = new Scanner(new BufferedReader(new FileReader(pathFile)))){
            scanner.useDelimiter("[;\n]");
            scanner.useLocale(Locale.US);

            while(scanner.hasNext()){
                String codiceFiscale = scanner.next();
                String nomeCompleto = scanner.next();
                String matricola = scanner.next();
                String tipoMembro = scanner.next();

                MembroUnisa membroToAdd = switch(tipoMembro){
                    case "studente" -> {
                        double votoMedio = scanner.nextDouble();
                        CorsoDiStudi corsoDiStudio =CorsoDiStudi.valueOf(scanner.next());
                        Studente studenteToAdd = new Studente(
                                codiceFiscale, nomeCompleto, matricola, votoMedio, corsoDiStudio
                        );
                        yield studenteToAdd;
                    }
                    case "docente" -> {
                        String insegnamento = scanner.next();
                        Docente docenteToAdd = new Docente(
                                codiceFiscale, nomeCompleto, matricola, insegnamento
                        );
                        yield  docenteToAdd;
                    }
                    default -> null;
                };
                if(membroToAdd != null)
                    aOut.aggiuntaMembroUnisa(membroToAdd);
            }
        }

        return aOut;
    }
    public void writeCSV(String pathFile) throws IOException {
        try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(pathFile)))){
            String delimiter = ";";
            for(MembroUnisa membro : membriUnisa.values()){
                pw.append(membro.getCodiceFiscale()).append(delimiter);
                pw.append(membro.getNomeCompleto()).append(delimiter);
                pw.append(membro.getMatricola()).append(delimiter);
                switch(membro){
                    case Studente studente -> {
                        pw.append("studente").append(delimiter);
                        pw.append(Double.toString(studente.getVotoMedio())).append(delimiter);
                        pw.append(studente.getCorsoDiStudi().toString()).append("\n");
                    }
                    case Docente docente -> {
                        pw.append("docente").append(delimiter);
                        pw.append(docente.getInsegnamento()).append("\n");
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuffer strb = new StringBuffer();

        membriUnisa.values().forEach(mi -> {
            strb.append(mi).append("\n");
        });

        return strb.toString();
    }
}
