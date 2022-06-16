public class Manusia {
    private String nama;
    private String nim;
    private char kelas;
    private int point;

    public Manusia(String nim, String password) {
        DatabaseUser dbUser = new DatabaseUser(nim, password);
        this.nama = dbUser.getNamaUser();
        this.nim = dbUser.getNIMUser();
        this.kelas = dbUser.getKelasUser();
        this.point = dbUser.getPointUser();
    }

    public String getNama() {
        return this.nama;
    }
    public String getNIM() {
        return this.nim;
    }
    public char getKelas() {
        return this.kelas;
    }
    public int getPoint() {
        return this.point;
    }
}
