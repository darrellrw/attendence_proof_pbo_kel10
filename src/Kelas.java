import java.util.*;

public class Kelas {
    private List<String> listKelas = new ArrayList<String>();
    private char tipeKelas;

    public Kelas(char kelas) {
        if(kelas == 'A') {
            listKelas.add("Kalkulus 2 - A");
            listKelas.add("Fisika Dasar 2 - A");
            listKelas.add("Sistem Digital - A");
        }
        else if(kelas == 'B') {
            listKelas.add("Kalkulus 2 - B");
            listKelas.add("Pengantar Metode Statistik - B");
            listKelas.add("Pemrograman Berorientasi Objek - B");
        }
        else if(kelas == 'S') {
            listKelas.add("Kalkulus 2 - A");
            listKelas.add("Kalkulus 2 - B");
            listKelas.add("Fisika Dasar 2 - A");
            listKelas.add("Sistem Digital - A");
            listKelas.add("Pengantar Metode Statistik - B");
            listKelas.add("Pemrograman Berorientasi Objek - B");
        }
    }
    public String[] getListCourse() {
        return listKelas.toArray(new String[0]);
    }
    public void setKelasMataKuliah(String matakuliah) {
        if(matakuliah.equals("Kalkulus 2 - A") || matakuliah.equals("Fisika Dasar 2 - A") || matakuliah.equals("Sistem Digital - A")) {
            this.tipeKelas = 'A';
        }
        else if(matakuliah.equals("Kalkulus 2 - B") || matakuliah.equals("Pengantar Metode Statistik - B") || matakuliah.equals("Pemrograman Berorientasi Objek - B")) {
            this.tipeKelas = 'B';
        }
    }
    public char getKelasMataKuliah() {
        return this.tipeKelas;
    }
}
