import java.sql.*;

public class DatabaseUserAbsen {
    public DatabaseUserAbsen(String ket, Timestamp waktu, String userid) {
        try {
            String sqlAbsen = "UPDATE absendata SET keterangan = ? WHERE nim = '"+userid+"' AND waktu = '"+waktu+"'";

            Class.forName("com.mysql.jdbc.Driver");
            Connection condb = DriverManager.getConnection("jdbc:mysql://localhost:3306/tubesk10pboa", "root", "");
            PreparedStatement stmdb = condb.prepareStatement(sqlAbsen);

            stmdb.setString(1, ket);
            
            stmdb.executeUpdate();

            condb.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
}
