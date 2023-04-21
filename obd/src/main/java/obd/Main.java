package obd;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// autor: Bernard Gaworczyk

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            //setupDropTables(stmt);              // metoda nieaktywna - bywa przydatna (ale trzeba uważać)

            //createTablesAndConstraints(stmt);  //  metoda nieaktywna = więzy spójności po stronie bazy danych nieaktywne
            createTablesNoConstraints(stmt);     //  tworzymy tabele bez wiezów spójności

            sampleInserts(stmt);
            printSampleTables(stmt);

            Konsola k1 = new Konsola();
            // obiekt służy zbudowaniu SQL insert poprzez okno dialogowe (Scanner) z while loop, który wymusi poprawność wprowadzaonych danych
            // przy uruchomieniu metody inputNauczyciel przekazuję listę kluczy PK z tabeli nauczyciel,
            // metoda sprawdzi czy proponowane przez użytkownika idNauczyciela jest na liście kluczy głównych tabeli nauczyciel (= klucz obcy na tabeli ocenianie).

            k1.inputRodzaj();
            k1.inputNauczyciel(CheckKeys("NAUCZYCIEL", stmt));
            k1.inputPrzedmiot(CheckKeys( "PRZEDMIOT", stmt));
            k1.inputUczen(CheckKeys(     "UCZEN", stmt));
            k1.inputOcena(CheckKeys( "OCENA", stmt));
            System.out.println("\nUtworzony SQL insert wygląda tak: \n"+  k1.toString());

            String SQLinsert = k1.toString();
            stmt.executeQuery(SQLinsert);
            insertsConfirmation(stmt);

            conn.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }


    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");

        String dbURL = "";
        String username = "";
        String password = "";
        Connection conn = DriverManager.getConnection(dbURL, username, password);
        return conn;
    }


    private static void insertsConfirmation(Statement stmt) throws SQLException {
        //metoda służy do potwierdzenia pomyślenego wystawienia oceny - drukuję odczyt z 5 tabel w formie narracyjnej

        String SQLcheck = "select  'UCZEŃ           ' || imie_ucznia || nazwisko_ucznia as uczen, "+
                "'\nOTRZYMAŁ OCENĘ  ' || wartosc_opisowa, '\nTYPU            ' ||  rodzaj_oceny, '\nZ PRZEDMIOTU    ' || nazwa_przedmiotu, " +
                "'\nNAUCZYCIEL      ' || imie_nauczyciela || nazwisko_nauczyciela " +
                "from OCENIANIE o join NAUCZYCIEL n on o.idn = n.id join UCZEN u on o.idu = u.id join OCENA c on o.ido = c.id join PRZEDMIOT P on o.idp = p.id";

        System.out.println("\n ---tabela ocenianie----------------------------------");
        ResultSet rs;
        rs = stmt.executeQuery("select * from OCENIANIE");
        while (rs.next())
            System.out.println(rs.getString(1) + "|" + rs.getString(2) + "|" + rs.getString(3) + "|" + rs.getString(4) + "|" + rs.getString(5));

        System.out.println("\n---potwierdzenie wykonanej operacji--------------------");
        rs = stmt.executeQuery(SQLcheck);
        while (rs.next())
            System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5));
    }

    private static void printSampleTables(Statement stmt) throws SQLException {
        //metoda pokazuje tabele wypełnione przykładowymi danymi
        System.out.println("\n---tabela nauczyciele----------------------------------");
        ResultSet rs = stmt.executeQuery("select * from NAUCZYCIEL");
        while (rs.next())
            System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));

        System.out.println("\n ---tabela przedmioty--------------------------------");
        rs = stmt.executeQuery("select * from PRZEDMIOT");
        while (rs.next())
            System.out.println(rs.getInt(1) + "  " + rs.getString(2));

        System.out.println("\n ---tabela uczniowie---------------------------------");
        rs = stmt.executeQuery("select * from UCZEN");
        while (rs.next())
            System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));

        System.out.println("\n ---tabela oceny--------------------------------------");
        rs = stmt.executeQuery("select * from OCENA");
        while (rs.next())
            System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
    }

    private static void sampleInserts(Statement stmt) throws SQLException {
        stmt.execute("insert into NAUCZYCIEL values (1, 'Tester', 'Justyna')");
        stmt.execute("insert into NAUCZYCIEL values (3, 'Hacker', 'Anna')");
        stmt.execute("insert into NAUCZYCIEL values (5, 'Cracker', 'Henryk')");
        stmt.execute("insert into UCZEN values (14, 'Abacka', 'Monika')");
        stmt.execute("insert into UCZEN values (15, 'Babacki', 'Bartosz')");
        stmt.execute("insert into UCZEN values (16, 'Dabacka', 'Patrycja')");
        stmt.execute("insert into PRZEDMIOT values (1, 'Biologia')");
        stmt.execute("insert into PRZEDMIOT values (2, 'Matematyka')");
        stmt.execute("insert into PRZEDMIOT values (4, 'WF')");
        stmt.execute("insert into PRZEDMIOT values (9, 'Angielski')");
        stmt.execute("insert into OCENA values (9, 'bardzo dobry', 5.0)");
        stmt.execute("insert into OCENA values (8, 'dobry plus', 4.5)");
        stmt.execute("insert into OCENA values (7, 'dobry', 4.0)");
        stmt.execute("insert into OCENA values (6, 'dostateczny plus', 3.5)");
        stmt.execute("insert into OCENA values (5, 'dostateczny', 3.0)");
    }

    private static void createTablesAndConstraints(Statement stmt) throws SQLException {
        //metoda tworzy tabele z więzami spójności po stronie SQL - nieaktywna
        stmt.execute(SetDBwithConstraints.getSQLcreateNauczyciel());
        stmt.execute(SetDBwithConstraints.getSQLcreatePrzedmiot());
        stmt.execute(SetDBwithConstraints.getSQLcreateUczen());
        stmt.execute(SetDBwithConstraints.getSQLcreateOcena());
        stmt.execute(SetDBwithConstraints.getSQLcreateOcenianie());
    }

    private static void createTablesNoConstraints(Statement stmt) throws SQLException {
        //metoda tworzy tabele bez więzów spójności - aktywna
        stmt.execute(SetDBnoConstraints.getSQLcreateNauczyciel());
        stmt.execute(SetDBnoConstraints.getSQLcreatePrzedmiot());
        stmt.execute(SetDBnoConstraints.getSQLcreateUczen());
        stmt.execute(SetDBnoConstraints.getSQLcreateOcena());
        stmt.execute(SetDBnoConstraints.getSQLcreateOcenianie());
    }

    private static void setupDropTables(Statement stmt) throws SQLException {
        //metoda nieaktywna - założenie, że baza danych nie zawiera tabel z ćwiczenia
        stmt.execute("drop table OCENIANIE");
        stmt.execute("drop table NAUCZYCIEL");
        stmt.execute("drop table PRZEDMIOT");
        stmt.execute("drop table UCZEN");
        stmt.execute("drop table OCENA");
    }

    private static List<Integer> CheckKeys(String nameOfTable, Statement st) {
        //metoda zwraca listę kluczy głównych z tabeli o podanej nazwie, dzięki czemu,
        // w dalszych krokach użytkownik uniknie odwołania się do nieistniejącego rekordu

        //boolean answer = false;
        String queryToCheckKey = "select id from "+nameOfTable;
        List<Integer> primaryKeys = new ArrayList<Integer>();

        ResultSet rs = null;
        try {
            rs = st.executeQuery(queryToCheckKey);
            while (rs.next())
                primaryKeys.add(rs.getInt(1));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return primaryKeys;
    }


}









