import java.sql.*;
import java.util.ArrayList;

public class DatabaseAbsen {
    private int idNo;
    private String mataKuliah;
    private char kelas;
    private String passwordKelas;

    private ArrayList<Object[]> dataTabel = new ArrayList<Object[]>();

    private Timestamp waktu;

    public DatabaseAbsen() {
        try {
            String sqlDataKelas = "SELECT * FROM kelasdata";

            Class.forName("com.mysql.jdbc.Driver");
            Connection condb = DriverManager.getConnection("jdbc:mysql://localhost:3306/tubesk10pboa", "root", "");
            Statement stmdb = condb.createStatement();
            ResultSet rsdb = stmdb.executeQuery(sqlDataKelas);

            while(rsdb.next()) {
                this.idNo = rsdb.getInt("id");
                this.mataKuliah =  rsdb.getString("matakuliah");
                this.kelas = rsdb.getString("kelas").charAt(0);
                this.passwordKelas = rsdb.getString("passwordkelas");
                this.waktu = rsdb.getTimestamp("waktu");

                this.dataTabel.add(new String[]{String.valueOf(this.idNo), this.mataKuliah, String.valueOf(this.kelas), this.passwordKelas, this.waktu.toString()});
            }

            condb.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public int getidNo() {
        return this.idNo;
    }

    public String getMataKuliah() {
        return this.mataKuliah;
    }

    public char getKelas() {
        return this.kelas;
    }

    public String getPasswordKelas() {
        return this.passwordKelas;
    }

    public ArrayList<Object[]> getDataTableKelas() {
        return this.dataTabel;
    }
}
