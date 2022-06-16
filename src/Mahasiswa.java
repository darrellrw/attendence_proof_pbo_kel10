public class Mahasiswa extends Manusia{
    private String[] headerTable = {"No", "Mata Kuliah", "Kelas", "Waktu", "Keterangan"};

    public Mahasiswa(String nim, String password) {
        super(nim, password);
        new MenuForm(nim, password);
    }
    
    public String[] getHeader() {
        return this.headerTable;
    }
}
