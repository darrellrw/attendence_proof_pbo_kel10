public class Mahasiswa extends User{
    private String[] headerTable = {"No", "Mata Kuliah", "Kelas", "Waktu", "Keterangan"};

    public Mahasiswa(String nim, String password) {
        super(nim, password);
    }
    
    public String[] getHeader() {
        return this.headerTable;
    }
}
