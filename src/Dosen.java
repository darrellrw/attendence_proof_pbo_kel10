public class Dosen extends User{
    private String keterangan;
    private String[] headerTable = {"No", "Mata Kuliah", "Kelas", "Password", "Waktu"};

    public Dosen(String nim, String password, String keterangan) {
        super(nim, password);
        this.keterangan = keterangan;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public String[] getHeader() {
        return this.headerTable;
    }
}
