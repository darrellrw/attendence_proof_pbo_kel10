import java.sql.*;
import java.util.*;

public class Database {
    private String namaUser;
    private String nimUser;
    private char kelasUser;
    private int pointUser;
    private String keteranganUser;

    private boolean dbConnected = false;
    private boolean dbDisconnect = false;
    private boolean continueDB = false;
    private boolean sudahAbsenDB = false;

    private int idNo;
    private String mataKuliah;
    private char kelasMataKuliah;
    private String passwordKelas;
    private Timestamp waktuKelas;
    private ArrayList<Object[]> dataTabel = new ArrayList<Object[]>();

    private Timestamp waktuSekarang;
    private Timestamp waktuSQL;

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

    public void DatabaseWaktuSQL(String nim, Timestamp batasWaktu) {
        long ti = batasWaktu.getTime();
        long substracted = ti - 60 * 60 * 1000;

        try {
            String sqlTime = "SELECT * FROM absendata WHERE waktu BETWEEN '"+(new Timestamp(substracted))+"' AND '"+batasWaktu+"'";

            Class.forName("com.mysql.jdbc.Driver");
            Connection condb = DriverManager.getConnection("jdbc:mysql://localhost:3306/tubesk10pboa", "root", "");
            Statement stmdb = condb.createStatement();
            ResultSet rsdb = stmdb.executeQuery(sqlTime);

            while(rsdb.next()) {
                this.waktuSQL = rsdb.getTimestamp("waktu");
                this.mataKuliah= rsdb.getString("matakuliah");
                continueDB = true;
            }
            condb.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public void DatabaseLanjut(Timestamp waktuSpesifik) {
        try {
            String sqlPassword = "SELECT * FROM kelasdata WHERE waktu = '"+waktuSpesifik+"'";
            String sqlKet = "SELECT * FROM absendata WHERE waktu = '"+waktuSpesifik+"'";

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
                this.passwordKelas = rsdb.getString("passwordkelas");
            }

            condb.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public void DatabaseUserAbsen(String ket, Timestamp waktu, String userid) {
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

    public void PointSystem(String nim) {
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

    public String getMataKuliahYangAda() {
        return this.mataKuliah;
    }

    public Timestamp getWaktudariSQLAktif() {
        return waktuSQL;
    }

    public boolean getBolehLanjut() {
        return continueDB;
    }

    public String getPasswordKelas() {
        return this.passwordKelas;
    }
    public boolean getSudahAbsenDB() {
        return this.sudahAbsenDB;
    }
}
