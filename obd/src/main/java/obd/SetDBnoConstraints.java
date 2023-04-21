package obd;
public class SetDBnoConstraints {

    static String SQLcreateNauczyciel = "create table NAUCZYCIEL (id int, nazwisko_nauczyciela char(30), imie_nauczyciela char(20))";
    static String SQLcreatePrzedmiot = "create table PRZEDMIOT (id int, nazwa_przedmiotu char(20))";
    static String SQLcreateUczen = "create table UCZEN (id int, nazwisko_ucznia char(30), imie_ucznia char(20))";
    static String SQLcreateOCENA = "create table OCENA (id int, wartosc_opisowa char(20), wartosc_numeryczna float)";
    static String SQLcreateOCENIANIE = "create table OCENIANIE (rodzaj_oceny char(1), idn int, idp int, idu int, ido int)";


    public static String getSQLcreateNauczyciel() {return SQLcreateNauczyciel;}
    public static String getSQLcreatePrzedmiot() {return SQLcreatePrzedmiot;}
    public static String getSQLcreateUczen() {
        return SQLcreateUczen;
    }
    public static String getSQLcreateOcena() {
        return SQLcreateOCENA;
    }
    public static String getSQLcreateOcenianie() {
        return SQLcreateOCENIANIE;
    }
}
