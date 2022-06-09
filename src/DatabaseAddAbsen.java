import java.sql.*;
import java.util.*;

public class DatabaseAddAbsen {
    private Timestamp realTime;

    public DatabaseAddAbsen(String matakuliah, char kelas) {
        try {
            CourseType ctP = new CourseType(kelas);
            ctP.setKelasMataKuliah(matakuliah);

            String sqlDataKelas = "INSERT INTO kelasdata (matakuliah, kelas, passwordkelas, waktu) VALUES (?, ?, ?, ?)";
            String sqlKet = "INSERT INTO absendata (nim, matakuliah, kelas, waktu, keterangan) VALUES (?, ?, ?, ?, ?)";
            String sqlNIM = "SELECT * FROM logindata";

            this.realTime = new Timestamp(System.currentTimeMillis());

            Class.forName("com.mysql.jdbc.Driver");
            Connection condb = DriverManager.getConnection("jdbc:mysql://localhost:3306/tubesk10pboa", "root", "");
            PreparedStatement stmdb = condb.prepareStatement(sqlDataKelas);
            PreparedStatement stmadb = condb.prepareStatement(sqlKet);

            Statement stmaadb = condb.createStatement();
            ResultSet rsdb = stmaadb.executeQuery(sqlNIM);
            
            stmdb.setString(1, matakuliah);
            stmdb.setString(2, String.valueOf(ctP.getKelasMataKuliah()));
            stmdb.setString(3, passRandom());
            stmdb.setTimestamp(4, this.realTime);
            
            stmdb.execute();

            while(rsdb.next()) {
                if(rsdb.getString("classtype").equals(String.valueOf(ctP.getKelasMataKuliah()))) {
                    stmadb.setString(1, rsdb.getString("nim"));
                    stmadb.setString(2, matakuliah);
                    stmadb.setString(3, String.valueOf(ctP.getKelasMataKuliah()));
                    stmadb.setTimestamp(4, this.realTime);
                    stmadb.setString(5, "Belum");
                    
                    stmadb.execute();
                }
            }

            condb.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public String passRandom() {
        int len = 8;
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%&";
        Random rnd = new Random();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(chars.charAt(rnd.nextInt(chars.length())));
        System.out.println(sb.toString());
        return sb.toString();
    }
}
