package records;

public class Main {
    public static void main(String[] args) {
        Studente s = new Studente("Gis","and","123456789");
        System.out.println(s);

        Studente s1 = new Studente("luigi","rossi",CorsoDiStudi.LM);
        Studente s2 = new Studente("luigi","rossi",CorsoDiStudi.LT);
        System.out.println(s1);
        System.out.println(s2);
    }
}
