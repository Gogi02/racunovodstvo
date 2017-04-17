/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racunovodstvo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.Instant;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import static racunovodstvo.Login.dbpassword;
import static racunovodstvo.Login.dburl;
import static racunovodstvo.Login.dbuser;

/**
 *
 * @author gogi
 */
public class partnerji extends javax.swing.JFrame {

    int dovoljenjaDodajPartnerja, dovoljenjaSpremeniPartnerja, napaka, sifraPartnerja, staraSifraPartnerja, dobavitelj, kupec, aktiven,
            zavezanecDDV, popraviDobavitelj, popraviKupec, popraviAktiven, popraviZavezanecDDV, popraviNapaka, dodajDobavitelj, dodajKupec,
            dodajAktiven, dodajZavezanecDDV, dodajNapaka, isciDrzava;
    String sifraDrzaveTemp, tekstNapake, naslovNapake, ime, priimek, naziv, davcnaStevilka, predpona, hisnaStevilka, posta, postnaStevilka, kraj,
            naslov, transakcijskiRacun, drzava, drzavaText, sifraPartnerjaTemp, popraviIme, popraviPriimek, popraviNaziv, popraviDavcnaStevilka, popraviPredpona, popraviHisnaStevilka, popraviPosta, popraviPostnaStevilka, popraviKraj,
            popraviNaslov, popraviTransakcijskiRacun, popraviDrzava, popraviDrzavaText, popraviSifraPartnerjaTemp, dodajIme, dodajPriimek, dodajNaziv, dodajDavcnaStevilka, dodajPredpona, dodajHisnaStevilka, dodajPosta, dodajPostnaStevilka, dodajKraj,
            dodajNaslov, dodajTransakcijskiRacun, dodajDrzava, dodajDrzavaText, dodajSifraPartnerjaTemp;
    DefaultTableModel tm, tm2;
    public static Object value, value2;

    /**
     * Creates new form partnerji
     */
    public partnerji() {
        initComponents();
    }

    public void populatePartnerji() {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        PreparedStatement pst2 = null;

        //String parrent="a";
        String[] aryNastavitve;
        aryNastavitve = new String[3];
        int i = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("properties.txt")))//preberi nastavive iz datoteke
        {

            String trenutnaVrstica;

            while ((trenutnaVrstica = br.readLine()) != null) { //preberi nastavitve v tabelo				
                aryNastavitve[i] = trenutnaVrstica;
                i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        String dburl = aryNastavitve[0];//spremenjivke iz tabele
        String dbuser = aryNastavitve[1];
        String dbpassword = aryNastavitve[2];

        try {
            con = DriverManager.getConnection(dburl, dbuser, dbpassword);
            pst = con.prepareStatement("SELECT sifraPartnerja, naziv, priimek, ime, davcnaStevilka FROM partnerji");
            rs = pst.executeQuery();
            pst2 = con.prepareStatement("SELECT spremeniPartnerja, dodajPartnerja FROM dovoljenja WHERE skupina=?");
            pst2.setInt(1, Login.skupina);
            rs2 = pst2.executeQuery();

            if (rs2.next()) {
                dovoljenjaSpremeniPartnerja = rs2.getInt("spremeniPartnerja");
                dovoljenjaDodajPartnerja = rs2.getInt("dodajPartnerja");
            }

        } catch (SQLException ex) {
            // Logger lgr = Logger.getLogger(Version.class.getName());
            //lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

        try {

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            tm = (DefaultTableModel) jTable1.getModel();
            jTable1.setModel(tm);

            jTable1.setRowSelectionAllowed(true);
            tm.setColumnCount(0);

            // add specified columns to table
            for (i = 1; i <= columnCount; i++) {
                tm.addColumn(rsmd.getColumnName(i));
            }

            // clear existing rows
            tm.setRowCount(0);

            // add rows to table
            while (rs.next()) {
                //parrent = rs.getString("konto");
                //osnovna.test.setText(String.valueOf(parrent));
                String[] a = new String[columnCount];
                for (i = 0; i < columnCount; i++) {
                    a[i] = rs.getString(i + 1);
                }
                tm.addRow(a);
            }
            tm.fireTableDataChanged();

            // Close ResultSet and Statement
            rs.close();
            pst.close();

        } catch (Exception ex) {
            //JOptionPane.showMessageDialog(this, ex, ex.getMessage(), WIDTH, null);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        isciDrzavo = new javax.swing.JDialog();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;   //Disallow the editing of any cell
            }
        };
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;   //Disallow the editing of any cell
            }
        };
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        isciNazivTxt = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        isciPriimekTxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        isciImeTxt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        isciSifraPartnerjaTxt = new javax.swing.JTextField();
        isciDavcnaStevilkaTxt = new javax.swing.JTextField();
        isciButton = new javax.swing.JToggleButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        popraviNazivTxt = new javax.swing.JTextField();
        popraviImeTxt = new javax.swing.JTextField();
        popraviPriimekTxt = new javax.swing.JTextField();
        popraviNaslovTxt = new javax.swing.JTextField();
        popraviHisnaStevilkaTxt = new javax.swing.JTextField();
        popraviKrajTxt = new javax.swing.JTextField();
        popraviPostnaStevilkaTxt = new javax.swing.JTextField();
        popraviPostaTxt = new javax.swing.JTextField();
        sifraPartnerjaLabel = new javax.swing.JLabel();
        popraviDrzavaTxt = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        popraviPredponaTxt = new javax.swing.JTextField();
        popraviDavcnaStevilkaTxt = new javax.swing.JTextField();
        popraviDobaviteljCheck = new javax.swing.JCheckBox();
        popraviZavezanecDDVCheck = new javax.swing.JCheckBox();
        popraviKupecCheck = new javax.swing.JCheckBox();
        popraviAktivenCheck = new javax.swing.JCheckBox();
        popraviButton = new javax.swing.JButton();
        popraviOpustiButton = new javax.swing.JButton();
        popraviDrzavaLabel = new javax.swing.JLabel();
        popraviTransakcijskiRacunTxt = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        dodajNazivTxt = new javax.swing.JTextField();
        dodajImeTxt = new javax.swing.JTextField();
        dodajPriimekTxt = new javax.swing.JTextField();
        dodajNaslovTxt = new javax.swing.JTextField();
        dodajHisnaStevilkaTxt = new javax.swing.JTextField();
        dodajKrajTxt = new javax.swing.JTextField();
        dodajPostnaStevilkaTxt = new javax.swing.JTextField();
        dodajPostaTxt = new javax.swing.JTextField();
        dodajDrzavaTxt = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        dodajDrzavaLabel = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        dodajPredponaTxt = new javax.swing.JTextField();
        dodajDavcnaStevilkaTxt = new javax.swing.JTextField();
        dodajTransakcijskiRacunTxt = new javax.swing.JTextField();
        dodajDobaviteljCheck = new javax.swing.JCheckBox();
        dodajKupecCheck = new javax.swing.JCheckBox();
        dodajZavezanecDDVCheck = new javax.swing.JCheckBox();
        dodajAktivenCheck = new javax.swing.JCheckBox();

        isciDrzavo.setMinimumSize(new java.awt.Dimension(525, 420));
        isciDrzavo.setModal(true);
        isciDrzavo.setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);

        jButton2.setText("Potrdi");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        populateDrzava2();
        jTable2.setModel(tm2);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jButton3.setText("Prekliči");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout isciDrzavoLayout = new javax.swing.GroupLayout(isciDrzavo.getContentPane());
        isciDrzavo.getContentPane().setLayout(isciDrzavoLayout);
        isciDrzavoLayout.setHorizontalGroup(
            isciDrzavoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, isciDrzavoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addGap(8, 8, 8))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE)
        );
        isciDrzavoLayout.setVerticalGroup(
            isciDrzavoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(isciDrzavoLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(isciDrzavoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        populatePartnerji();
        jTable1.setModel(tm);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setText("Naziv:");

        jLabel2.setText("Priimek:");

        jLabel3.setText("Ime:");

        isciImeTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isciImeTxtActionPerformed(evt);
            }
        });

        jLabel4.setText("Šifra partnerja:");

        jLabel5.setText("Davčna Številka:");

        isciButton.setText("Išči");
        isciButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isciButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(isciPriimekTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(isciNazivTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(isciDavcnaStevilkaTxt)
                            .addComponent(isciSifraPartnerjaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(isciImeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(isciButton)))
                .addContainerGap(414, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(isciNazivTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(isciSifraPartnerjaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(112, 112, 112)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(isciPriimekTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(isciDavcnaStevilkaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(isciImeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(isciButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(143, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Išči partnerja", jPanel1);

        jLabel6.setText("Naziv:");

        jLabel7.setText("Ime:");

        jLabel8.setText("Priimek:");

        jLabel9.setText("Naslov:");

        jLabel10.setText("Hišna številka: ");

        jLabel11.setText("Kraj:");

        jLabel12.setText("Poštna številka:");

        jLabel13.setText("Pošta:");

        jLabel14.setText("Država:");

        jLabel15.setText("Šifra partnerja:");

        jLabel17.setText("Davčna številka:");

        popraviNazivTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popraviNazivTxtActionPerformed(evt);
            }
        });

        popraviPriimekTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popraviPriimekTxtActionPerformed(evt);
            }
        });

        popraviNaslovTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popraviNaslovTxtActionPerformed(evt);
            }
        });

        popraviDrzavaTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popraviDrzavaTxtActionPerformed(evt);
            }
        });

        jButton1.setText("Išči");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        popraviDobaviteljCheck.setText("Dobavitej");
        popraviDobaviteljCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popraviDobaviteljCheckActionPerformed(evt);
            }
        });

        popraviZavezanecDDVCheck.setText("Zavezanec za DDV");

        popraviKupecCheck.setText("Kupec");

        popraviAktivenCheck.setText("Aktiven");

        popraviButton.setText("Popravi");
        popraviButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popraviButtonActionPerformed(evt);
            }
        });

        popraviOpustiButton.setText("Opusti");
        popraviOpustiButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popraviOpustiButtonActionPerformed(evt);
            }
        });

        jLabel16.setText("Transakcijski račun:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(popraviButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(popraviOpustiButton))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel13))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(popraviPostaTxt)
                                    .addComponent(sifraPartnerjaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(popraviImeTxt)
                                    .addComponent(popraviPriimekTxt)
                                    .addComponent(popraviNaslovTxt)
                                    .addComponent(popraviKrajTxt)
                                    .addComponent(popraviPostnaStevilkaTxt)
                                    .addComponent(popraviHisnaStevilkaTxt)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(popraviDrzavaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton1))
                                    .addComponent(popraviDrzavaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(popraviNazivTxt))))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(popraviDobaviteljCheck)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(popraviKupecCheck)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(popraviZavezanecDDVCheck)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(popraviAktivenCheck))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(140, 140, 140)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel16))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(popraviPredponaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(popraviDavcnaStevilkaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(popraviTransakcijskiRacunTxt)))))
                    .addComponent(jLabel11)
                    .addComponent(jLabel10))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sifraPartnerjaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(popraviNazivTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(popraviImeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(popraviPriimekTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(popraviNaslovTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(popraviHisnaStevilkaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(popraviKrajTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(popraviPostnaStevilkaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(popraviPostaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(popraviDrzavaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(popraviZavezanecDDVCheck)
                            .addComponent(popraviAktivenCheck)
                            .addComponent(popraviKupecCheck)
                            .addComponent(popraviDobaviteljCheck))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(popraviTransakcijskiRacunTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))))
                .addGap(2, 2, 2)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(popraviDrzavaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(popraviButton)
                            .addComponent(popraviOpustiButton)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(popraviPredponaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(popraviDavcnaStevilkaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Popravi partnerja", jPanel2);

        jLabel18.setText("Naziv:");

        jLabel19.setText("Ime:");

        jLabel20.setText("Priimek:");

        jLabel21.setText("Naslov:");

        jLabel22.setText("Hišna številka:");

        jLabel23.setText("Kraj:");

        jLabel24.setText("Poštna številka:");

        jLabel25.setText("Pošta:");

        jLabel26.setText("Država:");

        dodajHisnaStevilkaTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dodajHisnaStevilkaTxtActionPerformed(evt);
            }
        });

        dodajPostnaStevilkaTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dodajPostnaStevilkaTxtActionPerformed(evt);
            }
        });

        jButton4.setText("Išči");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Dodaj");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Opusti");

        jLabel27.setText("Davčna številka:");

        jLabel28.setText("Transakcijski račun:");

        dodajTransakcijskiRacunTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dodajTransakcijskiRacunTxtActionPerformed(evt);
            }
        });

        dodajDobaviteljCheck.setText("Dobavitelj");

        dodajKupecCheck.setText("Kupec");

        dodajZavezanecDDVCheck.setText("Zavezanec za DDV");

        dodajAktivenCheck.setSelected(true);
        dodajAktivenCheck.setText("Aktiven");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton6)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel19)
                            .addComponent(jLabel20)
                            .addComponent(jLabel21)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel25))
                            .addComponent(jLabel26))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dodajDrzavaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dodajPostnaStevilkaTxt)
                            .addComponent(dodajKrajTxt)
                            .addComponent(dodajHisnaStevilkaTxt)
                            .addComponent(dodajNaslovTxt)
                            .addComponent(dodajNazivTxt)
                            .addComponent(dodajImeTxt)
                            .addComponent(dodajPriimekTxt)
                            .addComponent(dodajPostaTxt)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(dodajDrzavaTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton4)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 141, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel28)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel27)
                                        .addGap(6, 6, 6)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dodajTransakcijskiRacunTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(dodajPredponaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(dodajDavcnaStevilkaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(dodajDobaviteljCheck)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dodajKupecCheck)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dodajZavezanecDDVCheck)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dodajAktivenCheck)))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(dodajNazivTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel20))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(dodajImeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dodajPriimekTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dodajNaslovTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dodajHisnaStevilkaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dodajKrajTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dodajPostnaStevilkaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dodajPostaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(dodajPredponaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(dodajDavcnaStevilkaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dodajDrzavaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26)
                            .addComponent(jButton4))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dodajDrzavaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel28)
                                .addComponent(dodajTransakcijskiRacunTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton5)
                            .addComponent(jButton6))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dodajDobaviteljCheck)
                            .addComponent(dodajKupecCheck)
                            .addComponent(dodajZavezanecDDVCheck)
                            .addComponent(dodajAktivenCheck))
                        .addGap(116, 116, 116))))
        );

        jTabbedPane1.addTab("Dodaj partnerja", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPane1)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if (evt.getClickCount() == 2) {
            getValueOfSelectedRow();
            jTabbedPane1.setSelectedIndex(1);
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        if (evt.getClickCount() == 2) {
            getValueOfSelectedRow2();
            if (isciDrzava==0)
            {
                popraviDrzavaTxt.setText(sifraDrzaveTemp);
                poisciTextDrzave();
                popraviDrzavaLabel.setText(drzavaText);
            }
            else
            {
                dodajDrzavaTxt.setText(sifraDrzaveTemp);
                poisciTextDrzave2();
                dodajDrzavaLabel.setText(drzavaText);
            }
            isciDrzavo.dispose();
        }
    }//GEN-LAST:event_jTable2MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        isciDrzavo.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
            getValueOfSelectedRow2();
            if (isciDrzava==0)
            {
                popraviDrzavaTxt.setText(sifraDrzaveTemp);
                poisciTextDrzave();
                popraviDrzavaLabel.setText(drzavaText);
            }
            else
            {
                dodajDrzavaTxt.setText(sifraDrzaveTemp);
                poisciTextDrzave2();
                dodajDrzavaLabel.setText(drzavaText);
            }
            
            isciDrzavo.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void popraviOpustiButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popraviOpustiButtonActionPerformed
        populatePP();
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_popraviOpustiButtonActionPerformed

    private void popraviButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popraviButtonActionPerformed
        if (dovoljenjaSpremeniPartnerja==1)
        {
            napaka=0;
            preberiSpremenljivke();
            preveriSpremenljivke();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Nimate dovoljenja za spremembo partnerja","Spremeni partnerja - napaka",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_popraviButtonActionPerformed

    private void popraviDobaviteljCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popraviDobaviteljCheckActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_popraviDobaviteljCheckActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        isciDrzava=0;
        isciDrzavo.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void popraviDrzavaTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popraviDrzavaTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_popraviDrzavaTxtActionPerformed

    private void popraviNaslovTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popraviNaslovTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_popraviNaslovTxtActionPerformed

    private void popraviPriimekTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popraviPriimekTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_popraviPriimekTxtActionPerformed

    private void popraviNazivTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popraviNazivTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_popraviNazivTxtActionPerformed

    private void isciButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isciButtonActionPerformed
        naziv = isciNazivTxt.getText();
        ime = isciImeTxt.getText();
        priimek = isciPriimekTxt.getText();
        sifraPartnerjaTemp = isciSifraPartnerjaTxt.getText().replaceAll("[^0-9]", "");
        if (sifraPartnerjaTemp != null && !sifraPartnerjaTemp.isEmpty()) {
            sifraPartnerja = Integer.parseInt(sifraPartnerjaTemp);
        }
        davcnaStevilka = isciDavcnaStevilkaTxt.getText().replaceAll("[^0-9]", "");
        //osnovna.test.setText(davcnaStevilka);

        if (naziv != null && !naziv.isEmpty()) {

        } else {
            naziv = "%";
        }

        if (ime != null && !ime.isEmpty()) {

        } else {
            ime = "%";
        }

        if (priimek != null && !priimek.isEmpty()) {

        } else {
            priimek = "%";
        }

        if (davcnaStevilka != null && !davcnaStevilka.isEmpty()) {

        } else {
            davcnaStevilka = "%";
        }

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(dburl, dbuser, dbpassword);
            if (sifraPartnerja > 0) {
                pst = con.prepareStatement("SELECT sifraPartnerja, naziv, priimek, ime, davcnaStevilka FROM partnerji WHERE (naziv LIKE ? AND ime LIKE ? AND priimek LIKE ? AND sifraPartnerja LIKE ? AND davcnaStevilka LIKE ?)");
                pst.setString(1, naziv);
                pst.setString(2, ime);
                pst.setString(3, priimek);
                pst.setInt(4, sifraPartnerja);
                pst.setString(5, davcnaStevilka);
            } else {
                pst = con.prepareStatement("SELECT sifraPartnerja, naziv, priimek, ime, davcnaStevilka FROM partnerji WHERE (naziv LIKE ? AND ime LIKE ? AND priimek LIKE ? AND davcnaStevilka LIKE ?)");
                pst.setString(1, naziv);
                pst.setString(2, ime);
                pst.setString(3, priimek);
                pst.setString(4, davcnaStevilka);
            }

            //osnovna.test.setText(String.valueOf(pst));
            rs = pst.executeQuery();
            /*if (rs.next()) {
                sifraPartnerjaTemp=rs.getString("davcnaStevilka");
                osnovna.test.setText(sifraPartnerjaTemp);
            }*/
        } catch (SQLException ex) {
        }
        try {
            int i = 0;
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            tm = (DefaultTableModel) jTable1.getModel();
            jTable1.setModel(tm);

            jTable1.setRowSelectionAllowed(true);
            tm.setColumnCount(0);

            // add specified columns to table
            for (i = 1; i <= columnCount; i++) {
                tm.addColumn(rsmd.getColumnName(i));
            }

            // clear existing rows
            tm.setRowCount(0);

            // add rows to table
            while (rs.next()) {
                //parrent = rs.getString("konto");
                //osnovna.test.setText(String.valueOf(parrent));
                String[] a = new String[columnCount];
                for (i = 0; i < columnCount; i++) {
                    a[i] = rs.getString(i + 1);
                }
                tm.addRow(a);
            }
            tm.fireTableDataChanged();

        } catch (Exception ex) {
            //JOptionPane.showMessageDialog(this, ex, ex.getMessage(), WIDTH, null);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                //Logger lgr = Logger.getLogger(Version.class.getName());
                //lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }//GEN-LAST:event_isciButtonActionPerformed

    private void isciImeTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isciImeTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_isciImeTxtActionPerformed

    private void dodajHisnaStevilkaTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dodajHisnaStevilkaTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dodajHisnaStevilkaTxtActionPerformed

    private void dodajPostnaStevilkaTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dodajPostnaStevilkaTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dodajPostnaStevilkaTxtActionPerformed

    private void dodajTransakcijskiRacunTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dodajTransakcijskiRacunTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dodajTransakcijskiRacunTxtActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (dovoljenjaDodajPartnerja==1)
        {
            napaka=0;
            preberiDodajSpremenljivke();
            preveriDodajSpremenljivke();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Nimate dovoljenja za dodajanje partnerja","Dodaj partnerja - napaka",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        isciDrzava=1;
        isciDrzavo.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    public void preberiDodajSpremenljivke()
    {
        dodajNaziv=dodajNazivTxt.getText();
        dodajIme=dodajImeTxt.getText();
        dodajPriimek=dodajPriimekTxt.getText();
        dodajDrzava=dodajDrzavaTxt.getText();
        dodajNaslov=dodajNaslovTxt.getText();
        dodajHisnaStevilka=dodajHisnaStevilkaTxt.getText();
        dodajKraj=dodajKrajTxt.getText();
        dodajPostnaStevilka=dodajPostnaStevilkaTxt.getText();
        dodajPosta=dodajPostaTxt.getText();
        dodajPredpona=dodajPredponaTxt.getText();
        dodajDavcnaStevilka=dodajDavcnaStevilkaTxt.getText();
        dodajTransakcijskiRacun=dodajTransakcijskiRacunTxt.getText();
        if (dodajDobaviteljCheck.isSelected())
        {
            dodajDobavitelj=1;
        }
        else
        {
            dodajDobavitelj=0;
        }
        if (dodajKupecCheck.isSelected())
        {
            dodajKupec=1;
        }
        else
        {
            dodajKupec=0;
        }
        if (dodajAktivenCheck.isSelected())
        {
            dodajAktiven=1;
        }
        else
        {
            dodajAktiven=0;
        }
        if (dodajZavezanecDDVCheck.isSelected())
        {
            dodajZavezanecDDV=1;
        }
        else
        {
            dodajZavezanecDDV=0;
        }
    }
    
    public void preveriDodajSpremenljivke()
    {
        int niPrazno=0;
        dodajNapaka=0;
        if(dodajNaziv!=null&&!dodajNaziv.isEmpty()) 
        {
            niPrazno=1;
        }
        if(dodajPriimek!=null&&!dodajPriimek.isEmpty()) 
        {
            niPrazno=1;
        }
        if(niPrazno==0)
        {
            dodajNapaka=1; //naziv ali priimek morata biti izpolnjena
        }
        int preveriPredpona=0;
        if(dodajPredpona!=null&&!dodajPredpona.isEmpty()) 
        {
            preveriPredpona=1;
        }
        if (preveriPredpona!=dodajZavezanecDDV)
                {
                    dodajNapaka=2; //če je partner zavezanec za DDV, mora biti tudi predpona izpolnjena
                }
        Connection con = null;        
        PreparedStatement pst = null;
        ResultSet rs = null;       

        try {
            con = DriverManager.getConnection(dburl, dbuser, dbpassword);
            pst = con.prepareStatement("SELECT dodajPartnerja FROM dovoljenja WHERE skupina=?");            
            pst.setInt(1, Login.skupina);
            rs=pst.executeQuery();
            if (rs.next()) {           
               dovoljenjaSpremeniPartnerja=rs.getInt("dodajPartnerja");
               //osnovna.test.setText(drzavaText);
            }
            
        } catch (SQLException ex) {

        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                //Logger lgr = Logger.getLogger(Version.class.getName());
                //lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        if(dovoljenjaDodajPartnerja==0)
        {
            dodajNapaka=3;
        }
        
        switch (dodajNapaka)
        {
            case 1:
                tekstNapake="Nazvi ali priimek morata biti izpolnjena!";
                break;
            case 2:
                tekstNapake="Če je partner zavezanec za DDV mora biti izponjena predpona!";
                break;
            case 3:
                tekstNapake="Nimate dovoljenja za dodajanje partnerja!";
                break;            
        }        
        if(dodajNapaka==0)
        {
            dodajPartner();
        }
        else
        {
            naslovNapake="Dodaj partnerja - napaka";
            prikazNapake(tekstNapake, naslovNapake);
        }
    }
    
    public void dodajPartner()
    {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        preberiDodajSpremenljivke();
        
        try {
            con = DriverManager.getConnection(dburl, dbuser, dbpassword);
            pst = con.prepareStatement("INSERT INTO partnerji"+"(naziv, priimek, ime, davcnaStevilka, naslov, hisnaStevilka, kraj, postnaStevilka, posta, drzava, transakcijskiRacun, predpona, dobavitelj, kupec, aktiven, zavezanecDDV) VALUES"+"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pst.setString(1, dodajNaziv);
            pst.setString(2, dodajPriimek);
            pst.setString(3, dodajIme);            
            pst.setString(4, dodajDavcnaStevilka);
            pst.setString(5, dodajNaslov);
            pst.setString(6, dodajHisnaStevilka);
            pst.setString(7, dodajKraj);
            pst.setString(8, dodajPostnaStevilka);
            pst.setString(9, dodajPosta);
            pst.setString(10, dodajDrzava);
            pst.setString(11, dodajTransakcijskiRacun);
            pst.setString(12, dodajPredpona);
            pst.setInt(13, dodajDobavitelj);
            pst.setInt(14, dodajKupec);
            pst.setInt(15, dodajAktiven);
            pst.setInt(16, dodajZavezanecDDV);            
            //osnovna.test.setText(String.valueOf(pst));
            String statement=String.valueOf(pst);
            pst.executeUpdate();
            //osnovna.test.setText(String.valueOf(pst));
            pst = con.prepareStatement("INSERT INTO log"+"(tekst, uporabnik, podrocje, datum) VALUES"+"(?,?,?,?)");
            pst.setString(1, statement);
            pst.setString(2, Login.anUporabnik);
            String podrocje="partnerji";
            pst.setString(3, podrocje);
            long zdaj = Instant.now().getEpochSecond();
            pst.setLong(4,zdaj);
            pst.executeUpdate();

        } catch (SQLException ex) {
           // Logger lgr = Logger.getLogger(Version.class.getName());
            //lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                //Logger lgr = Logger.getLogger(Version.class.getName());
                //lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        populatePartnerji();
    }
    
    public void getValueOfSelectedRow2()
    {
        int row = jTable2.getSelectedRow();
        value = jTable2.getValueAt(row, 0);
        sifraDrzaveTemp = String.valueOf(value);
    }
    
    public void getValueOfSelectedRow() {
        int row = jTable1.getSelectedRow();
        value = jTable1.getValueAt(row, 0);
        sifraPartnerjaTemp = String.valueOf(value);
        staraSifraPartnerja = Integer.parseInt(sifraPartnerjaTemp);        
        if (staraSifraPartnerja > 0) {
            populatePP();
        }
    }

    public void populatePP() 
    {
        Connection con = null;
        PreparedStatement pst = null;        
        ResultSet rs = null;              

        try {
            con = DriverManager.getConnection(dburl, dbuser, dbpassword); //populate with old values
            pst = con.prepareStatement("SELECT * FROM partnerji WHERE sifraPartnerja=?");          
            pst.setInt(1, staraSifraPartnerja);                        
            rs=pst.executeQuery();
            if (rs.next()) {           
               naziv= rs.getString("naziv"); 
               ime= rs.getString("ime");
               priimek= rs.getString("priimek");
               drzava=rs.getString("drzava");
               
               //poisciTextDrzave();           
               naslov=rs.getString("naslov");
               hisnaStevilka=rs.getString("hisnaStevilka");
               kraj=rs.getString("kraj");               
               postnaStevilka=rs.getString("postnaStevilka");
               //osnovna.test.setText(postnaStevilka);
               posta=rs.getString("posta");               
               davcnaStevilka=rs.getString("davcnaStevilka");
               transakcijskiRacun=rs.getString("transakcijskiRacun");
               dobavitelj=rs.getInt("dobavitelj");
               kupec=rs.getInt("kupec");
               aktiven=rs.getInt("aktiven");
               zavezanecDDV=rs.getInt("zavezanecDDV");
               predpona=rs.getString("predpona");
               
            }
            
            sifraPartnerjaLabel.setText(String.valueOf(staraSifraPartnerja));
            popraviNazivTxt.setText(naziv);
            popraviImeTxt.setText(ime);
            popraviPriimekTxt.setText(priimek);            
            popraviDrzavaTxt.setText(drzava);
            poisciTextDrzave();
            popraviDrzavaLabel.setText(drzavaText);
            popraviNaslovTxt.setText(naslov);
            popraviHisnaStevilkaTxt.setText(hisnaStevilka);
            popraviKrajTxt.setText(kraj);
            popraviPostnaStevilkaTxt.setText(postnaStevilka);
            popraviPostaTxt.setText(posta);
            popraviPredponaTxt.setText(predpona);
            popraviDavcnaStevilkaTxt.setText(davcnaStevilka);
            popraviTransakcijskiRacunTxt.setText(transakcijskiRacun);
            if(dobavitelj==1){
                popraviDobaviteljCheck.setSelected(true);
            }
            if(kupec==1){
                popraviKupecCheck.setSelected(true);
            }
            if(aktiven==1){
                popraviAktivenCheck.setSelected(true);
            }
            if(zavezanecDDV==1){
                popraviZavezanecDDVCheck.setSelected(true);
            }
            
        } catch (SQLException ex) {

        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                //Logger lgr = Logger.getLogger(Version.class.getName());
                //lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }
    
    public void poisciTextDrzave()
    {
        String drzavaText2=popraviDrzavaTxt.getText();
        //osnovna.test.setText(drzavaText2);
        Connection conDrzavaText = null;        
        PreparedStatement pstDrzavaText = null;
        ResultSet rsDrzavaText = null;       

        try {
            conDrzavaText = DriverManager.getConnection(dburl, dbuser, dbpassword);
            pstDrzavaText = conDrzavaText.prepareStatement("SELECT * FROM drzave WHERE sifraDrzave=?");            
            
            
            pstDrzavaText.setString(1, drzavaText2);            
            rsDrzavaText=pstDrzavaText.executeQuery();
            if (rsDrzavaText.next()) {           
               drzavaText=rsDrzavaText.getString("opis");
               
            }
            
        } catch (SQLException ex) {

        }
        finally {
            try {
                if (rsDrzavaText != null) {
                    rsDrzavaText.close();
                }
                if (pstDrzavaText != null) {
                    pstDrzavaText.close();
                }
                if (conDrzavaText != null) {
                    conDrzavaText.close();
                }

            } catch (SQLException ex) {
                //Logger lgr = Logger.getLogger(Version.class.getName());
                //lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }
    
    private void poisciTextDrzave2()
            {
        String drzavaText2=dodajDrzavaTxt.getText();
        //osnovna.test.setText(drzavaText2);
        Connection conDrzavaText = null;        
        PreparedStatement pstDrzavaText = null;
        ResultSet rsDrzavaText = null;       

        try {
            conDrzavaText = DriverManager.getConnection(dburl, dbuser, dbpassword);
            pstDrzavaText = conDrzavaText.prepareStatement("SELECT * FROM drzave WHERE sifraDrzave=?");            
            
            
            pstDrzavaText.setString(1, drzavaText2);            
            rsDrzavaText=pstDrzavaText.executeQuery();
            if (rsDrzavaText.next()) {           
               drzavaText=rsDrzavaText.getString("opis");
               
            }
            
        } catch (SQLException ex) {

        }
        finally {
            try {
                if (rsDrzavaText != null) {
                    rsDrzavaText.close();
                }
                if (pstDrzavaText != null) {
                    pstDrzavaText.close();
                }
                if (conDrzavaText != null) {
                    conDrzavaText.close();
                }

            } catch (SQLException ex) {
                //Logger lgr = Logger.getLogger(Version.class.getName());
                //lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }

    private void preberiSpremenljivke()
    {
        //osnovna.test.setText(String.valueOf(staraSifraPartnerja));
        sifraPartnerja=staraSifraPartnerja;
        popraviNaziv=popraviNazivTxt.getText();
        popraviIme=popraviImeTxt.getText();
        popraviPriimek=popraviPriimekTxt.getText();
        popraviDrzava=popraviDrzavaTxt.getText();
        popraviNaslov=popraviNaslovTxt.getText();
        popraviHisnaStevilka=popraviHisnaStevilkaTxt.getText();
        popraviKraj=popraviKrajTxt.getText();
        popraviPostnaStevilka=popraviPostnaStevilkaTxt.getText();
        popraviPosta=popraviPostaTxt.getText();
        popraviPredpona=popraviPredponaTxt.getText();
        popraviDavcnaStevilka=popraviDavcnaStevilkaTxt.getText();
        popraviTransakcijskiRacun=popraviTransakcijskiRacunTxt.getText();
        if (popraviDobaviteljCheck.isSelected())
        {
            popraviDobavitelj=1;
        }
        else
        {
            popraviDobavitelj=0;
        }
        if (popraviKupecCheck.isSelected())
        {
            popraviKupec=1;
        }
        else
        {
            popraviKupec=0;
        }
        if (popraviAktivenCheck.isSelected())
        {
            popraviAktiven=1;
        }
        else
        {
            popraviAktiven=0;
        }
        if (popraviZavezanecDDVCheck.isSelected())
        {
            popraviZavezanecDDV=1;
        }
        else
        {
            popraviZavezanecDDV=0;
        }
        
    }
    
    private void preveriSpremenljivke()
    {
        int niPrazno=0;
        popraviNapaka=0;
        if(popraviNaziv!=null&&!popraviNaziv.isEmpty()) 
        {
            niPrazno=1;
        }
        if(popraviPriimek!=null&&!popraviPriimek.isEmpty()) 
        {
            niPrazno=1;
        }
        if(niPrazno==0)
        {
            popraviNapaka=1; //naziv ali priimek morata biti izpolnjena
        }
        int preveriPredpona=0;
        if(popraviPredpona!=null&&!popraviPredpona.isEmpty()) 
        {
            preveriPredpona=1;
        }
        if (preveriPredpona!=popraviZavezanecDDV)
                {
                    popraviNapaka=2; //če je partner zavezanec za DDV, mora biti tudi predpona izpolnjena
                }
        Connection con = null;        
        PreparedStatement pst = null;
        ResultSet rs = null;       

        try {
            con = DriverManager.getConnection(dburl, dbuser, dbpassword);
            pst = con.prepareStatement("SELECT spremeniPartnerja FROM dovoljenja WHERE skupina=?");            
            pst.setInt(1, Login.skupina);
            rs=pst.executeQuery();
            if (rs.next()) {           
               dovoljenjaSpremeniPartnerja=rs.getInt("spremeniPartnerja");
               //osnovna.test.setText(drzavaText);
            }
            
        } catch (SQLException ex) {

        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                //Logger lgr = Logger.getLogger(Version.class.getName());
                //lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        if(dovoljenjaSpremeniPartnerja==0)
        {
            popraviNapaka=3;
        }
        
        switch (popraviNapaka)
        {
            case 1:
                tekstNapake="Nazvi ali priimek morata biti izpolnjena!";
                break;
            case 2:
                tekstNapake="Če je partner zavezanec za DDV mora biti izponjena predpona!";
                break;
            case 3:
                tekstNapake="Nimate dovoljenja za popravljanje partnerja!";
                break;            
        }        
        if(popraviNapaka==0)
        {
            popraviPartner();
        }
        else
        {
            naslovNapake="Popravi partnerja - napaka";
            prikazNapake(tekstNapake, naslovNapake);
        }
        
    }
    
    public void prikazNapake(String tekstNapake, String naslovNapake)
    {
        JOptionPane.showMessageDialog(null,tekstNapake,naslovNapake,JOptionPane.ERROR_MESSAGE);
    }
    
    public void popraviPartner()
    {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        preberiSpremenljivke();
        
        try {
            con = DriverManager.getConnection(dburl, dbuser, dbpassword);
            pst = con.prepareStatement("UPDATE partnerji SET naziv=?, priimek=?, ime=?, davcnaStevilka=?, naslov=?, hisnaStevilka=?, kraj=?, postnaStevilka=?, posta=?, drzava=?, transakcijskiRacun=?, predpona=?, dobavitelj=?, kupec=?, aktiven=?, zavezanecDDV=? WHERE sifraPartnerja=?");
            pst.setString(1, popraviNaziv);
            pst.setString(2, popraviPriimek);
            pst.setString(3, popraviIme);            
            pst.setString(4, popraviDavcnaStevilka);
            pst.setString(5, popraviNaslov);
            pst.setString(6, popraviHisnaStevilka);
            pst.setString(7, popraviKraj);
            pst.setString(8, popraviPostnaStevilka);
            pst.setString(9, popraviPosta);
            pst.setString(10, popraviDrzava);
            pst.setString(11, popraviTransakcijskiRacun);
            pst.setString(12, popraviPredpona);
            pst.setInt(13, popraviDobavitelj);
            pst.setInt(14, popraviKupec);
            pst.setInt(15, popraviAktiven);
            pst.setInt(16, popraviZavezanecDDV);
            pst.setInt(17, sifraPartnerja);
            //osnovna.test.setText(String.valueOf(pst));
            String statement=String.valueOf(pst);
            pst.executeUpdate();
            //osnovna.test.setText(String.valueOf(pst));
            pst = con.prepareStatement("INSERT INTO log"+"(tekst, uporabnik, podrocje, datum) VALUES"+"(?,?,?,?)");
            pst.setString(1, statement);
            pst.setString(2, Login.anUporabnik);
            String podrocje="partnerji";
            pst.setString(3, podrocje);
            long zdaj = Instant.now().getEpochSecond();
            pst.setLong(4,zdaj);
            pst.executeUpdate();

        } catch (SQLException ex) {
           // Logger lgr = Logger.getLogger(Version.class.getName());
            //lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                //Logger lgr = Logger.getLogger(Version.class.getName());
                //lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        populatePartnerji();
        
    }
    
    public void populateDrzava2(){
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;        
        //ResultSet rs2 = null;        
        //PreparedStatement pst2 = null;
        
        //String parrent="a";

        String [] aryNastavitve;
        aryNastavitve=new String[3];
        int i=0;
        
		try (BufferedReader br = new BufferedReader(new FileReader("properties.txt")))//preberi nastavive iz datoteke
		{

			String trenutnaVrstica;

			while ((trenutnaVrstica = br.readLine()) != null) { //preberi nastavitve v tabelo				
                            aryNastavitve[i]=trenutnaVrstica;
                            i++;                            
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
                
                String dburl = aryNastavitve[0];//spremenjivke iz tabele
                String dbuser = aryNastavitve[1];
                String dbpassword = aryNastavitve[2];                

                try {
            con = DriverManager.getConnection(dburl, dbuser, dbpassword);
            pst = con.prepareStatement("SELECT sifraDrzave, kratica, opis FROM drzave");            
            rs = pst.executeQuery();
            //pst2 = con.prepareStatement("SELECT spremeniDrzavo, dodajDrzavo FROM dovoljenja WHERE skupina=?");
            //pst2.setInt(1, Login.skupina);
            //rs2 = pst2.executeQuery();
            
            /*if (rs2.next()) {           
              dovoljenjaSpremeniDrzavo=rs2.getInt("spremeniDrzavo");
              dovoljenjaDodajDrzavo=rs2.getInt("dodajDrzavo");
            }*/
            
        } catch (SQLException ex) {
           // Logger lgr = Logger.getLogger(Version.class.getName());
            //lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

        try {

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            tm2 = (DefaultTableModel) jTable2.getModel();
            jTable2.setModel(tm2);

            jTable2.setRowSelectionAllowed(true);
            tm2.setColumnCount(0);

            // add specified columns to table
            for (i = 1; i <= columnCount; i++ ) {
            tm2.addColumn(rsmd.getColumnName(i));
            }   

        // clear existing rows
        tm2.setRowCount(0);

        // add rows to table
        while (rs.next()) {
            //parrent = rs.getString("konto");
            //osnovna.test.setText(String.valueOf(parrent));
            String[] a = new String[columnCount];
            for(i = 0; i < columnCount; i++) {
                a[i] = rs.getString(i+1);
            }
        tm2.addRow(a);
        }
        tm2.fireTableDataChanged();

        // Close ResultSet and Statement
        rs.close();
        pst.close();

        } catch (Exception ex) { 
    //JOptionPane.showMessageDialog(this, ex, ex.getMessage(), WIDTH, null);
        }

    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(partnerji.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(partnerji.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(partnerji.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(partnerji.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new partnerji().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox dodajAktivenCheck;
    private javax.swing.JTextField dodajDavcnaStevilkaTxt;
    private javax.swing.JCheckBox dodajDobaviteljCheck;
    private javax.swing.JLabel dodajDrzavaLabel;
    private javax.swing.JTextField dodajDrzavaTxt;
    private javax.swing.JTextField dodajHisnaStevilkaTxt;
    private javax.swing.JTextField dodajImeTxt;
    private javax.swing.JTextField dodajKrajTxt;
    private javax.swing.JCheckBox dodajKupecCheck;
    private javax.swing.JTextField dodajNaslovTxt;
    private javax.swing.JTextField dodajNazivTxt;
    private javax.swing.JTextField dodajPostaTxt;
    private javax.swing.JTextField dodajPostnaStevilkaTxt;
    private javax.swing.JTextField dodajPredponaTxt;
    private javax.swing.JTextField dodajPriimekTxt;
    private javax.swing.JTextField dodajTransakcijskiRacunTxt;
    private javax.swing.JCheckBox dodajZavezanecDDVCheck;
    private javax.swing.JToggleButton isciButton;
    private javax.swing.JTextField isciDavcnaStevilkaTxt;
    private javax.swing.JDialog isciDrzavo;
    private javax.swing.JTextField isciImeTxt;
    private javax.swing.JTextField isciNazivTxt;
    private javax.swing.JTextField isciPriimekTxt;
    private javax.swing.JTextField isciSifraPartnerjaTxt;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JCheckBox popraviAktivenCheck;
    private javax.swing.JButton popraviButton;
    private javax.swing.JTextField popraviDavcnaStevilkaTxt;
    private javax.swing.JCheckBox popraviDobaviteljCheck;
    private javax.swing.JLabel popraviDrzavaLabel;
    public static javax.swing.JTextField popraviDrzavaTxt;
    private javax.swing.JTextField popraviHisnaStevilkaTxt;
    private javax.swing.JTextField popraviImeTxt;
    private javax.swing.JTextField popraviKrajTxt;
    private javax.swing.JCheckBox popraviKupecCheck;
    private javax.swing.JTextField popraviNaslovTxt;
    private javax.swing.JTextField popraviNazivTxt;
    private javax.swing.JButton popraviOpustiButton;
    private javax.swing.JTextField popraviPostaTxt;
    private javax.swing.JTextField popraviPostnaStevilkaTxt;
    private javax.swing.JTextField popraviPredponaTxt;
    private javax.swing.JTextField popraviPriimekTxt;
    private javax.swing.JTextField popraviTransakcijskiRacunTxt;
    private javax.swing.JCheckBox popraviZavezanecDDVCheck;
    private javax.swing.JLabel sifraPartnerjaLabel;
    // End of variables declaration//GEN-END:variables
}