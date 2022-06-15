import java.sql.*;
import java.util.*;

public class Database {
    private String namaUser;
    private String nimUser;
    private char kelasUser;
    private int pointUser;

    private boolean dbConnected = false;
    private boolean dbDisconnect = false;

    private int idNo;
    private String mataKuliah;
    private char kelasMataKuliah;
    private String passwordKelas;
    private Timestamp waktuKelas;
    private ArrayList<Object[]> dataTabel = new ArrayList<Object[]>();

    private String keteranganUser;

    public void DatabaseUserPass(String nim, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/tubesk10pboa", "root", "");
            Statement stmDB = conDB.createStatement();
            ResultSet rsDB = stmDB.executeQuery("SELECT * FROM logindata WHERE nim='"+nim+"' AND password='"+password+"'");
            if(rsDB.next()) {
                this.namaUser = rsDB.getString("nim");
                this.nimUser = rsDB.getString("name");
                this.kelasUser = rsDB.getString("classtype").charAt(0);
                this.pointUser = rsDB.getInt("points");
                dbConnected = true;
            }
            else {
                dbConnected = false;
            }
            conDB.close();
        }
        catch(Exception e) {
            System.out.println(e);
            dbDisconnect = true;
        }
    }

    public void DatabaseAbsenAdmin() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/tubesk10pboa", "root", "");
            Statement stmDB = conDB.createStatement();
            ResultSet rsDB = stmDB.executeQuery("SELECT * FROM kelasdata");
            while(rsDB.next()) {
                this.idNo = rsDB.getInt("id");
                this.mataKuliah = rsDB.getString("matakuliah");
                this.kelasMataKuliah = rsDB.getString("kelas").charAt(0);
                this.passwordKelas = rsDB.getString("passwordkelas");
                this.waktuKelas = rsDB.getTimestamp("waktu");
                this.dataTabel.add(new String[]{String.valueOf(this.idNo), this.mataKuliah, String.valueOf(this.kelasMataKuliah), this.passwordKelas, this.waktuKelas.toString()});
            }
            conDB.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public void DatabaseAbsenUser(String nim) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/tubesk10pboa", "root", "");
            Statement stmDB = conDB.createStatement();
            ResultSet rsDB = stmDB.executeQuery("SELECT * FROM absendata WHERE nim='"+nim+"'");
            while(rsDB.next()) {
                this.idNo = rsDB.getInt("id");
                this.mataKuliah = rsDB.getString("matakuliah");
                this.kelasMataKuliah = rsDB.getString("kelas").charAt(0);
                this.waktuKelas = rsDB.getTimestamp("waktu");
                this.keteranganUser = rsDB.getString("keterangan");
                this.dataTabel.add(new String[]{String.valueOf(this.idNo), this.mataKuliah, String.valueOf(this.kelasMataKuliah), this.waktuKelas.toString(), this.keteranganUser});
            }
            conDB.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
    public void DatabaseTambahAbsen(String mataKuliah, char kelas) {
        try {

        }
        catch(Exception e) {
            
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
    public boolean getLoginDB() {
        return this.dbConnected;
    }
    public boolean getConnectionDB() {
        return this.dbDisconnect;
    }
    public int getPointUser() {
        return this.pointUser;
    }

    public ArrayList<Object[]> getDataTabelKelas() {
        return this.dataTabel;
    }
}
