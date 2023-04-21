package obd;
import java.util.List;
import java.util.Scanner;

public class Konsola {
    char rodzajOceny;
    int idNauczyciela;
    int idPrzedmiotu;
    int idUcznia;
    int idOceny;


    public Konsola() {
    }

    public void setRodzajOceny(char rodzajOceny) {
        this.rodzajOceny = rodzajOceny;
    }

    public void setIdNauczyciela(int idNauczyciela) {
        this.idNauczyciela = idNauczyciela;
    }

    public void setIdPrzedmiotu(int idPrzedmiotu) {
        this.idPrzedmiotu = idPrzedmiotu;
    }

    public void setIdUcznia(int idUcznia) {
        this.idUcznia = idUcznia;
    }

    public void setIdOceny(int idOceny) {
        this.idOceny = idOceny;
    }

    @Override
    public String toString() {
        return "insert into OCENIANIE values('" + rodzajOceny + "'," + idNauczyciela + "," + idPrzedmiotu + "," + idUcznia + "," + idOceny + ")";
    }
/*
    public Konsola input() {
        Scanner myObject = new Scanner(System.in);
        System.out.println("\nWprowadź oddzielone spacjami atrybuty oceny: rodzaj oceny (c lub s), id nauczyciela, id przedmiotu, id ucznia, id oceny. \nPrzykład: c 3 2 15 7");
        char rodzajOceny = myObject.next().charAt(0);
        int idNauczyciela = myObject.nextInt();
        int idPrzedmiotu = myObject.nextInt();
        int idUcznia = myObject.nextInt();
        int idOceny = myObject.nextInt();

        Konsola k1 = new Konsola(rodzajOceny, idNauczyciela, idPrzedmiotu, idUcznia, idOceny);
        return k1;
*/

    public void inputRodzaj() {
        boolean waitingForValidInput = true;
        String validChars = "CcSs";
        while (waitingForValidInput){
            Scanner myObject = new Scanner(System.in);
            System.out.println("\nWprowadź rodzaj oceny. Dopuszczalne: C lub S");
            char rodzajOceny = myObject.next().charAt(0);
            if (validChars.contains(""+rodzajOceny)){
                System.out.println("wybór poprawny");
                waitingForValidInput = false;
                setRodzajOceny(rodzajOceny);
            }
            else System.out.println("dopuszczalne: C jak cząstkowa i S jak semestralna");
    }}

    public void inputNauczyciel(List<Integer> klucze) {
        boolean waitingForValidInput = true;
        while (waitingForValidInput){
            Scanner myObject = new Scanner(System.in);
            System.out.println("\nWprowadź id nauczyciela - dopuszczalne wartości: "+klucze.toString());
            int idNauczyciela = myObject.nextInt();
            if (klucze.contains(idNauczyciela)){
                System.out.println("wybór poprawny");
                waitingForValidInput = false;
                setIdNauczyciela(idNauczyciela);
            }
            else System.out.println("wprowadzona wartość narusza więzy spójności. ponów próbę");
        }}


    public void inputUczen(List<Integer> klucze) {
        boolean waitingForValidInput = true;
        while (waitingForValidInput){
            Scanner myObject = new Scanner(System.in);
            System.out.println("\nWprowadź id ucznia - dopuszczalne wartości: "+klucze.toString());
            int idUcznia = myObject.nextInt();
            if (klucze.contains(idUcznia)){
                System.out.println("wybór poprawny");
                waitingForValidInput = false;
                setIdUcznia(idUcznia);
            }
            else System.out.println("wprowadzona wartość narusza więzy spójności. ponów próbę");
        }}

    public void inputPrzedmiot(List<Integer> klucze) {
        boolean waitingForValidInput = true;
        while (waitingForValidInput){
            Scanner myObject = new Scanner(System.in);
            System.out.println("\nWprowadź id przedmiotu - dopuszczalne wartości: "+klucze.toString());
            int idPrzedmiotu = myObject.nextInt();
            if (klucze.contains(idPrzedmiotu)){
                System.out.println("wybór poprawny");
                waitingForValidInput = false;
                setIdPrzedmiotu(idPrzedmiotu);
            }
            else System.out.println("wprowadzona wartość narusza więzy spójności. ponów próbę");
        }}

    public void inputOcena(List<Integer> klucze) {
        boolean waitingForValidInput = true;
        while (waitingForValidInput){
            Scanner myObject = new Scanner(System.in);
            System.out.println("\nWprowadź id oceny - dopuszczalne wartości: "+klucze.toString());
            int idOceny = myObject.nextInt();
            if (klucze.contains(idOceny)){
                System.out.println("wybór poprawny");
                waitingForValidInput = false;
                setIdOceny(idOceny);
            }
            else System.out.println("wprowadzona wartość narusza więzy spójności. ponów próbę");
        }}
}










