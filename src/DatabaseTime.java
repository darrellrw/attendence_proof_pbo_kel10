import java.sql.*;

public class DatabaseTime {
    private Timestamp waktusql;
    private String matakuliah;
    private boolean continueDB = false;

    public DatabaseTime(String nim, Timestamp waktuse) {
        long ti = waktuse.getTime();
        long substracted = ti - 60 * 60 * 1000;

        try {
            String sqlTime = "SELECT * FROM absendata WHERE waktu BETWEEN '"+(new Timestamp(substracted))+"' AND '"+waktuse+"'";

            Class.forName("com.mysql.jdbc.Driver");
            Connection condb = DriverManager.getConnection("jdbc:mysql://localhost:3306/tubesk10pboa", "root", "");
            Statement stmdb = condb.createStatement();
            ResultSet rsdb = stmdb.executeQuery(sqlTime);

            while(rsdb.next()) {
                this.waktusql = rsdb.getTimestamp("waktu");
                this.matakuliah= rsdb.getString("matakuliah");
                continueDB = true;
            }
            condb.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public String getMataKuliahYangAda() {
        return this.matakuliah;
    }

    public Timestamp getWaktudariSQLAktif() {
        return waktusql;
    }

    public boolean getConnectionDB() {
        return continueDB;
    }
}
