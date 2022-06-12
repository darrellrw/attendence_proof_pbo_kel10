import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.Color;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MenuForm implements ActionListener {
    private String userids;
    private String passwords;

    private ArrayList<Object[]> dataTabel = new ArrayList<Object[]>();
    private String[] kelas;

    private String[] absenKet = {"Absen", "Hadir", "Izin"};

    private String mkPilih;
    private String abPilih;
    private String passPilih;

    private int points;

    private char noKelas;

    JFrame frame = new JFrame();
    JLabel lblUser = new JLabel();
    JLabel lblPoint = new JLabel();
    JButton btnLogout = new JButton("Logout");
    JButton btnAddAbsen = new JButton("Tambah");
    JButton btnSetAbsen = new JButton("Absensi");
    DefaultTableModel tblAbsensiModel = new DefaultTableModel();
    JTable tblAbsensi = new JTable(tblAbsensiModel);
    JScrollPane jsPane = new JScrollPane(tblAbsensi);

    JOptionPane joPane = new JOptionPane();

    public MenuForm(String userid, String password) {
        DatabaseUser userDB = new DatabaseUser(userid, password);
        DatabaseAbsen absenDB = new DatabaseAbsen();
        DatabaseTabelAbsen sabsenDB = new DatabaseTabelAbsen(userid);
        CourseType tipeKelas = new CourseType(userDB.getKelasUser());

        this.kelas = tipeKelas.getListCourse();
        this.noKelas = userDB.getKelasUser();
        this.userids = userid;
        this.passwords = password;

        this.points = userDB.getPointUser();

        btnLogout.setBounds(874, 50, 125, 30);
        btnLogout.addActionListener(this);
        btnLogout.setFocusable(false);
        btnLogout.setBackground(new Color(204, 153, 0));
        btnLogout.setFont(new Font("Tw Cen MT", 0, 18));
        frame.add(btnLogout);

        btnAddAbsen.setBounds(724, 50, 125, 30);
        btnAddAbsen.addActionListener(this);
        btnAddAbsen.setFocusable(false);
        btnAddAbsen.setBackground(new Color(204, 153, 0));
        btnAddAbsen.setFont(new Font("Tw Cen MT", 0, 18));

        btnSetAbsen.setBounds(724, 50, 125, 30);
        btnSetAbsen.addActionListener(this);
        btnSetAbsen.setFocusable(false);
        btnSetAbsen.setBackground(new Color(204, 153, 0));
        btnSetAbsen.setFont(new Font("Tw Cen MT", 0, 18));

        if(userid.equals("admin")) {
            this.dataTabel = absenDB.getDataTableKelas();
            frame.add(btnAddAbsen);
            tblAbsensiModel.setColumnIdentifiers(new String[]{"No", "Mata Kuliah", "Kelas", "Password", "Waktu"});
            for(int i = 0; i < dataTabel.size(); i++) {
                tblAbsensiModel.addRow(dataTabel.get(i));
            }
        }
        else{
            this.dataTabel = sabsenDB.getDataTableKelas();
            frame.add(btnSetAbsen);
            tblAbsensiModel.setColumnIdentifiers(new String[]{"No", "Mata Kuliah", "Kelas", "Waktu", "Keterangan"});
            for(int i = 0; i < dataTabel.size(); i++) {
                tblAbsensiModel.addRow(dataTabel.get(i));
            }

        }

        lblUser.setBounds(20, 5, 500, 30);
        lblUser.setText("Selamat Datang, " + userDB.getNamaUser());
        lblUser.setFont(new Font("Tw Cen MT", 0, 18));
        frame.add(lblUser);

        lblPoint.setBounds(20, 25, 300, 30);
        lblPoint.setText("Points: " + this.points);
        lblPoint.setFont(new Font("Tw Cen MT", 0, 18));
        frame.add(lblPoint);

        tblAbsensi.setBackground(new Color(204, 153, 0));
        tblAbsensi.setFont(new Font("Tw Cen MT", 0, 18));
        tblAbsensi.getColumnModel().getColumn(0).setPreferredWidth(1);
        tblAbsensi.getColumnModel().getColumn(1).setPreferredWidth(200);
        tblAbsensi.getColumnModel().getColumn(2).setPreferredWidth(1);
        tblAbsensi.getTableHeader().setFont(new Font("Tw Cen MT", 0, 18));
        tblAbsensi.getTableHeader().setBackground(new Color(204, 153, 0));
        tblAbsensi.setEnabled(false);
        tblAbsensi.getTableHeader().setResizingAllowed(false);
        jsPane.setBounds(10, 100, 1004, 320);
        frame.add(jsPane);

        frame.getContentPane().setBackground(new Color(0, 153, 153));
        frame.setTitle("Attendence Proof: " + userDB.getNIMUser());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(1024, 480);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == btnAddAbsen) {
            this.mkPilih = (String)joPane.showInputDialog(null, "Tambah Absen Kelas:", "Tambah Absen", JOptionPane.QUESTION_MESSAGE, null, kelas, kelas[0]);
            DatabaseAddAbsen anb = new DatabaseAddAbsen(this.mkPilih, this.noKelas);
            frame.dispose();
            MenuForm mf = new MenuForm(userids, passwords);
        }
        if(ae.getSource() == btnLogout) {
            frame.dispose();
            LoginForm login = new LoginForm();
        }
        if(ae.getSource() == btnSetAbsen) {
            DatabaseTime timeSQL = new DatabaseTime(userids, new Timestamp(System.currentTimeMillis()));
            DatabasePass passKelas = new DatabasePass(timeSQL.getWaktudariSQLAktif());
            if(timeSQL.getConnectionDB() == true && passKelas.getSudahAbsenDB() == false) {
                try{
                    this.passPilih = (String)joPane.showInputDialog(null, "Password:", timeSQL.getMataKuliahYangAda(), JOptionPane.QUESTION_MESSAGE);
                    if(this.passPilih.equals(passKelas.getPasswordKelas())){
                        this.abPilih = (String)joPane.showInputDialog(null, "Keterangan:", "Absensi", JOptionPane.QUESTION_MESSAGE, null, absenKet, absenKet[0]);
                        DatabaseUserAbsen userAbsen = new DatabaseUserAbsen(this.abPilih, timeSQL.getWaktudariSQLAktif(), userids);
                        frame.dispose();
                        if(this.abPilih.equals("Hadir")) {
                            PointSystem ps = new PointSystem(userids);
                        }
                        MenuForm mf = new MenuForm(userids, passwords);
                    }
                    else {
                        System.out.println("Wrong");
                        joPane.showMessageDialog(null, "Wrong Password");
                    }
                }
                catch(Exception e) {
                    System.out.println(e);
                }
            }
            else {
                System.out.println("Tidak ada jadwal");
                joPane.showMessageDialog(null, "Tidak ada jadwal yang tersedia");
            }
        }
    }
}
