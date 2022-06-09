import java.sql.*;

public class DatabasePass {
    private String password;
    private boolean sudahAbsenDB = false;

    public DatabasePass(Timestamp waktu) {
        try {
            String sqlPassword = "SELECT * FROM kelasdata WHERE waktu = '"+waktu+"'";
            String sqlKet = "SELECT * FROM absendata WHERE waktu = '"+waktu+"'";

            Class.forName("com.mysql.jdbc.Driver");
            Connection condb = DriverManager.getConnection("jdbc:mysql://localhost:3306/tubesk10pboa", "root", "");
            Statement stamdb = condb.createStatement();
            ResultSet rsdb = stamdb.executeQuery(sqlPassword);

            Statement stmdb = condb.createStatement();
            ResultSet rsadb = stmdb.executeQuery(sqlKet);

            if(rsadb.next()) {
                if(rsadb.getString("keterangan").equals("Hadir") || rsadb.getString("keterangan").equals("Izin") || rsadb.getString("keterangan").equals("Absen")) {
                    sudahAbsenDB = true;
                    System.out.println("True");
                }
                else {
                    sudahAbsenDB = false;
                    System.out.println("False");
                }
            }

            while(rsdb.next()) {
                this.password = rsdb.getString("passwordkelas");
            }

            condb.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
    public String getPasswordKelas() {
        return this.password;
    }
    public boolean getSudahAbsenDB() {
        return this.sudahAbsenDB;
    }
}
