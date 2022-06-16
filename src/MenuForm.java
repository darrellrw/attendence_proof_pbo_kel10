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
    JLabel lblStat = new JLabel();

    public MenuForm(String userid, String password) {
        User man = new User(userid, password);
        DatabaseAbsen absenDB = new DatabaseAbsen();
        DatabaseTabelAbsen sabsenDB = new DatabaseTabelAbsen(userid);
        CourseType tipeKelas = new CourseType(man.getKelas());

        this.kelas = tipeKelas.getListCourse();
        this.noKelas = man.getKelas();
        this.userids = userid;
        this.passwords = password;

        this.points = man.getPoint();

        lblStat.setBounds(20, 45, 500, 30);
        lblStat.setFont(new Font("Tw Cen MT", 0, 18));

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
            Dosen ds = new Dosen(userid, password, "High Permission");
            frame.add(btnAddAbsen);
            tblAbsensiModel.setColumnIdentifiers(ds.getHeader());
            for(int i = 0; i < dataTabel.size(); i++) {
                tblAbsensiModel.addRow(dataTabel.get(i));
            }
            lblStat.setText("Status: " + ds.getKeterangan());
            frame.add(lblStat);
        }
        else {
            this.dataTabel = sabsenDB.getDataTableKelas();
            Mahasiswa mhs = new Mahasiswa(userid, password);
            frame.add(btnSetAbsen);
            tblAbsensiModel.setColumnIdentifiers(mhs.getHeader());
            for(int i = 0; i < dataTabel.size(); i++) {
                tblAbsensiModel.addRow(dataTabel.get(i));
            }

        }

        lblUser.setBounds(20, 5, 500, 30);
        lblUser.setText("Selamat Datang, " + man.getNama());
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
        frame.setTitle("Attendence Proof: " + man.getNIM());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(1024, 480);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == btnAddAbsen) {
            this.mkPilih = (String)JOptionPane.showInputDialog(null, "Tambah Absen Kelas:", "Tambah Absen", JOptionPane.QUESTION_MESSAGE, null, kelas, kelas[0]);
            new DatabaseAddAbsen(this.mkPilih, this.noKelas);
            frame.dispose();
            new MenuForm(userids, passwords);
        }
        if(ae.getSource() == btnLogout) {
            frame.dispose();
            new LoginForm();
        }
        if(ae.getSource() == btnSetAbsen) {
            DatabaseTime timeSQL = new DatabaseTime(userids, new Timestamp(System.currentTimeMillis()));
            DatabasePass passKelas = new DatabasePass(timeSQL.getWaktudariSQLAktif());
            if(timeSQL.getConnectionDB() == true && passKelas.getSudahAbsenDB() == false) {
                try{
                    this.passPilih = (String)JOptionPane.showInputDialog(null, "Password:", timeSQL.getMataKuliahYangAda(), JOptionPane.QUESTION_MESSAGE);
                    if(this.passPilih.equals(passKelas.getPasswordKelas())){
                        this.abPilih = (String)JOptionPane.showInputDialog(null, "Keterangan:", "Absensi", JOptionPane.QUESTION_MESSAGE, null, absenKet, absenKet[0]);
                        new DatabaseUserAbsen(this.abPilih, timeSQL.getWaktudariSQLAktif(), userids);
                        frame.dispose();
                        if(this.abPilih.equals("Hadir")) {
                            new PointSystem(userids);
                        }
                        new MenuForm(userids, passwords);
                    }
                    else {
                        System.out.println("Wrong");
                        JOptionPane.showMessageDialog(null, "Wrong Password");
                    }
                }
                catch(Exception e) {
                    System.out.println(e);
                }
            }
            else {
                System.out.println("Tidak ada jadwal");
                JOptionPane.showMessageDialog(null, "Tidak ada jadwal yang tersedia");
            }
        }
    }
}
