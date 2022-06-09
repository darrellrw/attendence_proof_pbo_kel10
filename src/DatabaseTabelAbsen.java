import java.sql.*;
import java.util.ArrayList;

public class DatabaseTabelAbsen {
    private int idNo;
    private String mataKuliah;
    private char kelas;
    private Timestamp waktu;
    private String keterangan;

    private ArrayList<Object[]> dataTabel = new ArrayList<Object[]>();

    public DatabaseTabelAbsen(String nim) {
        try {
            String sqlTable = "SELECT * FROM absendata WHERE nim='"+nim+"'";

            Class.forName("com.mysql.jdbc.Driver");
            Connection condb = DriverManager.getConnection("jdbc:mysql://localhost:3306/tubesk10pboa", "root", "");
            Statement stmdb = condb.createStatement();
            ResultSet rsdb = stmdb.executeQuery(sqlTable);

            while(rsdb.next()) {
                this.idNo = rsdb.getInt("id");
                this.mataKuliah =  rsdb.getString("matakuliah");
                this.kelas = rsdb.getString("kelas").charAt(0);
                this.waktu = rsdb.getTimestamp("waktu");
                this.keterangan = rsdb.getString("keterangan");

                this.dataTabel.add(new String[]{String.valueOf(this.idNo), this.mataKuliah, String.valueOf(this.kelas), this.waktu.toString(), this.keterangan});
            }

            condb.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
    public ArrayList<Object[]> getDataTableKelas() {
        return this.dataTabel;
    }
}
