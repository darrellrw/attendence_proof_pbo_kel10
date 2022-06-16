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

    private Timestamp waktuSekarang;

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

    public void DatabaseTabelAdmin() {
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

    public void DatabaseTabelUser(String nim) {
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
            Kelas tipeKelas = new Kelas(kelas);
            tipeKelas.setKelasMataKuliah(mataKuliah);

            String sqlDataKelas = "INSERT INTO kelasdata (matakuliah, kelas, passwordkelas, waktu) VALUES (?, ?, ?, ?)";
            String sqlKet = "INSERT INTO absendata (nim, matakuliah, kelas, waktu, keterangan) VALUES (?, ?, ?, ?, ?)";
            String sqlNIM = "SELECT * FROM logindata";

            this.waktuSekarang = new Timestamp(System.currentTimeMillis());

            Class.forName("com.mysql.jdbc.Driver");
            Connection conDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/tubesk10pboa", "root", "");
            PreparedStatement pstmDKDB = conDB.prepareStatement(sqlDataKelas);
            PreparedStatement pstmKDB = conDB.prepareStatement(sqlKet);

            Statement stmDB = conDB.createStatement();
            ResultSet rsDB = stmDB.executeQuery(sqlNIM);
            
            pstmDKDB.setString(1, mataKuliah);
            pstmDKDB.setString(2, String.valueOf(tipeKelas.getKelasMataKuliah()));
            pstmDKDB.setString(3, passRandom());
            pstmDKDB.setTimestamp(4, this.waktuSekarang);
            pstmDKDB.execute();

            while(rsDB.next()) {
                if(rsDB.getString("classtype").equals(String.valueOf(tipeKelas.getKelasMataKuliah()))) {
                    pstmKDB.setString(1, rsDB.getString("nim"));
                    pstmKDB.setString(2, mataKuliah);
                    pstmKDB.setString(3, String.valueOf(tipeKelas.getKelasMataKuliah()));
                    pstmKDB.setTimestamp(4, this.waktuSekarang);
                    pstmKDB.setString(5, "Belum");
                    
                    pstmKDB.execute();
                }
            }

            conDB.close();
        }
        catch(Exception e) {
            System.out.println(e);
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
