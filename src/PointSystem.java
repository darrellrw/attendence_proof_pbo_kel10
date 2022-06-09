import java.sql.*;

public class PointSystem {
    private int pointUser;

    public PointSystem(String nim) {
        try {  
            String sqlInsert = "UPDATE logindata SET points = ? WHERE nim = '"+nim+"'";
            String sqlLogin = "SELECT * FROM logindata WHERE nim='"+nim+"'";

            Class.forName("com.mysql.jdbc.Driver");
            Connection condb = DriverManager.getConnection("jdbc:mysql://localhost:3306/tubesk10pboa", "root", "");

            PreparedStatement stmdb = condb.prepareStatement(sqlInsert);

            Statement stmadb = condb.createStatement();
            ResultSet rsdb = stmadb.executeQuery(sqlLogin);

            if(rsdb.next()) {
                this.pointUser = rsdb.getInt("points");
            }

            stmdb.setInt(1, pointUser + 1);
            
            stmdb.executeUpdate();

            condb.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
}
