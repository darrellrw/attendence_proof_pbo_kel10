import java.sql.*;

public class DatabaseUser {
    private String namaUser;
    private String nimUser;
    private char kelasUser;
    private int pointUser;

    private boolean dbConnect = false;
    private boolean dbDisconnect = false;

    public DatabaseUser(String userid, String password) {
        try {
            String sqlLogin = "SELECT * FROM logindata WHERE nim='"+userid+"' AND password='"+password+"'";

            Class.forName("com.mysql.jdbc.Driver");
            Connection condb = DriverManager.getConnection("jdbc:mysql://localhost:3306/tubesk10pboa", "root", "");
            Statement stmdb = condb.createStatement();
            ResultSet rsdb = stmdb.executeQuery(sqlLogin);

            if(rsdb.next()) {
                this.nimUser =  rsdb.getString("nim");
                this.namaUser = rsdb.getString("name");
                this.kelasUser = rsdb.getString("classtype").charAt(0);
                this.pointUser = rsdb.getInt("points");

                dbConnect = true;
            }
            else {
                dbConnect = false;
            }

            condb.close();
        }
        catch(Exception e) {
            System.out.println(e);
            dbDisconnect = true;
        }
    }

    public String getNamaUser() {
        return this.namaUser;
    }

    public String getNIMUser() {
        return this.nimUser;
    }

    public char getKelasUser() {
        return this.kelasUser;
    }

    public int getPointUser() {
        return this.pointUser;
    }

    public boolean getLoginDB() {
        return this.dbConnect;
    }

    public boolean getConnectionDB() {
        return this.dbDisconnect;
    }
}
