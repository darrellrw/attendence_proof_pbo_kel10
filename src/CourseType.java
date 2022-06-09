import java.util.*;

public class CourseType {
    private List<String> listcourse = new ArrayList<String>();
    private char tipekelas;

    public CourseType(char tipeKelas) {
        if(tipeKelas == 'A') {
            listcourse.add("Kalkulus 2 - A");
            listcourse.add("Fisika Dasar 2 - A");
            listcourse.add("Sistem Digital - A");
        }
        else if(tipeKelas == 'B') {
            listcourse.add("Kalkulus 2 - B");
            listcourse.add("Pengantar Metode Statistik - B");
            listcourse.add("Pemrograman Berorientasi Objek - B");
        }
        else if(tipeKelas == 'S') {
            listcourse.add("Kalkulus 2 - A");
            listcourse.add("Kalkulus 2 - B");
            listcourse.add("Fisika Dasar 2 - A");
            listcourse.add("Sistem Digital - A");
            listcourse.add("Pengantar Metode Statistik - B");
            listcourse.add("Pemrograman Berorientasi Objek - B");
        }
    }

    public String[] getListCourse() {
        return listcourse.toArray(new String[0]);
    }

    public void setKelasMataKuliah(String matakuliah) {
        if(matakuliah.equals("Kalkulus 2 - A") || matakuliah.equals("Fisika Dasar 2 - A") || matakuliah.equals("Sistem Digital - A")) {
            this.tipekelas = 'A';
        }
        else if(matakuliah.equals("Kalkulus 2 - B") || matakuliah.equals("Pengantar Metode Statistik - B") || matakuliah.equals("Pemrograman Berorientasi Objek - B")) {
            this.tipekelas = 'B';
        }
    }

    public char getKelasMataKuliah() {
        return this.tipekelas;
    }
}
