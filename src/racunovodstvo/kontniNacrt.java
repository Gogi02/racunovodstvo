/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racunovodstvo;

/**
 *
 * @author gogi
 */
import com.genedavissoftware.printing.FormattedPrint;
import com.genedavissoftware.printing.GDSPrintException;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSetMetaData;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.JTable;

import java.io.IOException;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static racunovodstvo.Login.anUporabnik;
import static racunovodstvo.Login.dbpassword;
import static racunovodstvo.Login.dburl;
import static racunovodstvo.Login.dbuser;

//import static racunovodstvo.Login.value;
//import static racunovodstvo.Login.stariKonto;

public class kontniNacrt extends javax.swing.JFrame {
    DefaultTableModel tm;
    int dovoljenjaDodajKonto, dovoljenjaSpremeniKonto;
    public static Object value;
    public static String stariKonto=null, staraDrzava;
    String konto, konto2, nKonto, opis, parrent, nParrent, tekstNapake, naslovNapake;
    String dodajKonto2, dodajKonto3, nDodajKonto, dodajOpis, dodajParrent, nDodajParrent, dodajTekstNapake;
    int debet, kredit, stm, zapst, partner, napaka;
    int dodajDebet, dodajKredit, dodajStm, dodajZapst, dodajPartner, dodajNapaka;

    /**
     * Creates new form kontniNacrt
     */
    public static void printKontniNacrt(){
        
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int counter = 0;
        //String [] kontniNacrt = null;
        
        try {
        con = DriverManager.getConnection(dburl, dbuser, dbpassword);
        pst = con.prepareStatement("SELECT konto, opis FROM kontniNacrt");
        rs = pst.executeQuery();        
        rs.last();
        counter=rs.getRow();
        rs = null;
        
        pst = con.prepareStatement("SELECT konto, opis FROM kontniNacrt WHERE parrent = ?");
        pst.setString(1,"1234567890");
        rs=pst.executeQuery();
        counter=counter*2;
        String [] kontniNacrt;
        kontniNacrt = new String[counter];
        int i=2;
        kontniNacrt[0]="konto";
        kontniNacrt[1]="opis";
        while(rs.next()){
            kontniNacrt[i]=rs.getString("konto");
            i++;
            kontniNacrt[i]=rs.getString("opis");
            i++;
            String parrent = rs.getString("konto");
            
                pst = con.prepareStatement("SELECT konto, opis FROM kontniNacrt WHERE parrent = ?");
                pst.setString(1,parrent);
                ResultSet rs2=pst.executeQuery();        
                while(rs2.next()){
                    kontniNacrt[i]=rs2.getString("konto");
                    i++;
                    kontniNacrt[i]=rs2.getString("opis");
                    i++;
                    parrent = rs2.getString("konto");
                    
                    pst = con.prepareStatement("SELECT konto, opis FROM kontniNacrt WHERE parrent = ?");
                    pst.setString(1,parrent);
                    ResultSet rs3=pst.executeQuery();        
                    while(rs3.next()){
                        kontniNacrt[i]=rs3.getString("konto");
                        i++;
                        kontniNacrt[i]=rs3.getString("opis");
                        i++;
                        parrent = rs3.getString("konto");
                        
                        pst = con.prepareStatement("SELECT konto, opis FROM kontniNacrt WHERE parrent = ?");
                        pst.setString(1,parrent);
                        ResultSet rs4=pst.executeQuery();        
                        while(rs4.next()){
                            kontniNacrt[i]=rs4.getString("konto");
                            i++;
                            kontniNacrt[i]=rs4.getString("opis");
                            i++;                            
        }
        }
        }
        }
        //osnovna.test.setText(String.valueOf(kontniNacrt[3]));
        FormattedPrint formatted=new FormattedPrint();                
                formatted.addDocumentTitle("KONTNI NAČRT");
                formatted.addTable(2, counter, kontniNacrt);
                try {
                formatted.print();
                } catch (GDSPrintException ex) {
                Logger.getLogger(print.class.getName()).log(Level.SEVERE, null, ex);
                }
        
        }
        
        catch (SQLException ex) {
        
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
    
    public void populateKN(){
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ResultSet rs3 = null;
        ResultSet rs2 = null;
        ResultSet rs4 = null;
        ResultSet rs5 = null;
        PreparedStatement pst2 = null;
        PreparedStatement pst3 = null;
        PreparedStatement pst4 = null;
        PreparedStatement pst5 = null;
        String parrent="a";

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
            pst = con.prepareStatement("SELECT konto, opis, debet, kredit, stm, partner FROM kontniNacrt WHERE parrent=?");
            pst.setString(1, "1234567890");
            rs = pst.executeQuery();
            pst5 = con.prepareStatement("SELECT spremeniKonto, dodajKonto FROM dovoljenja WHERE skupina=?");
            pst5.setInt(1, Login.skupina);
            rs5 = pst5.executeQuery();
            
            if (rs5.next()) {           
              dovoljenjaSpremeniKonto=rs5.getInt("spremeniKonto");
              dovoljenjaDodajKonto=rs5.getInt("dodajKonto");
              //osnovna.test.setText(String.valueOf(dovoljenjaSpremeniKonto));
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
            for (i = 1; i <= columnCount; i++ ) {
            tm.addColumn(rsmd.getColumnName(i));
            }   

        // clear existing rows
        tm.setRowCount(0);

        // add rows to table
        while (rs.next()) {
            parrent = rs.getString("konto");
            //osnovna.test.setText(String.valueOf(parrent));
            String[] a = new String[columnCount];
            for(i = 0; i < columnCount; i++) {
                a[i] = rs.getString(i+1);
            }
        tm.addRow(a);
        
                    pst2 = con.prepareStatement("SELECT konto, opis, debet, kredit, stm, partner FROM kontniNacrt WHERE parrent=?");
                    pst2.setString(1, parrent);
                    rs2 = pst2.executeQuery();
                    while (rs2.next()) {
                    parrent = rs2.getString("konto");                   
                    String[] b = new String[columnCount];
                    for(int j = 0; j < columnCount; j++) {
                        b[j] = rs2.getString(j+1);
                    }
                    tm.addRow(b);

                        pst3 = con.prepareStatement("SELECT konto, opis, debet, kredit, stm, partner FROM kontniNacrt WHERE parrent=?");
                        pst3.setString(1, parrent);
                        rs3 = pst3.executeQuery();
                        while (rs3.next()) {
                        parrent = rs3.getString("konto");                   
                        String[] c = new String[columnCount];
                        for(int k = 0; k < columnCount; k++) {
                            c[k] = rs3.getString(k+1);
                        }
                        tm.addRow(c);

                            pst4 = con.prepareStatement("SELECT konto, opis, debet, kredit, stm, partner FROM kontniNacrt WHERE parrent=?");
                            pst4.setString(1, parrent);
                            rs4 = pst4.executeQuery();
                            while (rs4.next()) {
                            parrent = rs4.getString("konto");                   
                            String[] d = new String[columnCount];
                            for(int l = 0; l < columnCount; l++) {
                                d[l] = rs4.getString(l+1);
                            }
                            tm.addRow(d);



                    }

                    }

                    }                    

        }
        tm.fireTableDataChanged();

        // Close ResultSet and Statement
        rs.close();
        pst.close();

        } catch (Exception ex) { 
    //JOptionPane.showMessageDialog(this, ex, ex.getMessage(), WIDTH, null);
        }

    }
    
    public kontniNacrt() {
        
        initComponents();        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dodajKonto = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;   //Disallow the editing of any cell
            }
        };
        natisni = new javax.swing.JButton();
        osvezi = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        dodajKontoPanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        dodajKontoTxt = new javax.swing.JTextField();
        dodajOpisTxt = new javax.swing.JTextField();
        dodajParrentTxt = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        dodajDebetCheck = new javax.swing.JCheckBox();
        dodajKreditCheck = new javax.swing.JCheckBox();
        dodajStmCheck = new javax.swing.JCheckBox();
        dodajParnterCheck = new javax.swing.JCheckBox();
        dodajButt = new javax.swing.JButton();
        spremeniKontoPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        kontoTxt = new javax.swing.JTextField();
        opisTxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        parrentTxt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        debetCheck = new javax.swing.JCheckBox();
        kreditCheck = new javax.swing.JCheckBox();
        stmCheck = new javax.swing.JCheckBox();
        partnerCheck = new javax.swing.JCheckBox();
        potrdiButt = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Kontni načrt");

        dodajKonto.setText("Dodaj konto");
        dodajKonto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dodajKontoActionPerformed(evt);
            }
        });

        populateKN();

        /*Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ResultSet rs3 = null;
        ResultSet rs2 = null;
        ResultSet rs4 = null;
        PreparedStatement pst2 = null;
        PreparedStatement pst3 = null;
        PreparedStatement pst4 = null;
        String parrent="a";

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
            pst = con.prepareStatement("SELECT konto, opis, debet, kredit, stm FROM kontniNacrt WHERE parrent=?");
            pst.setString(1, "1234567890");
            rs = pst.executeQuery();

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
            for (i = 1; i <= columnCount; i++ ) {
                tm.addColumn(rsmd.getColumnName(i));
            }

            // clear existing rows
            tm.setRowCount(0);

            // add rows to table
            while (rs.next()) {
                parrent = rs.getString("konto");
                osnovna.test.setText(String.valueOf(parrent));
                String[] a = new String[columnCount];
                for(i = 0; i < columnCount; i++) {
                    a[i] = rs.getString(i+1);
                }
                tm.addRow(a);

                pst2 = con.prepareStatement("SELECT konto, opis, debet, kredit, stm FROM kontniNacrt WHERE parrent=?");
                pst2.setString(1, parrent);
                rs2 = pst2.executeQuery();
                while (rs2.next()) {
                    parrent = rs2.getString("konto");
                    String[] b = new String[columnCount];
                    for(int j = 0; j < columnCount; j++) {
                        b[j] = rs2.getString(j+1);
                    }
                    tm.addRow(b);

                    pst3 = con.prepareStatement("SELECT konto, opis, debet, kredit, stm FROM kontniNacrt WHERE parrent=?");
                    pst3.setString(1, parrent);
                    rs3 = pst3.executeQuery();
                    while (rs3.next()) {
                        parrent = rs3.getString("konto");
                        String[] c = new String[columnCount];
                        for(int k = 0; k < columnCount; k++) {
                            c[k] = rs3.getString(k+1);
                        }
                        tm.addRow(c);

                        pst4 = con.prepareStatement("SELECT konto, opis, debet, kredit, stm FROM kontniNacrt WHERE parrent=?");
                        pst4.setString(1, parrent);
                        rs4 = pst4.executeQuery();
                        while (rs4.next()) {
                            parrent = rs4.getString("konto");
                            String[] d = new String[columnCount];
                            for(int l = 0; l < columnCount; l++) {
                                d[l] = rs4.getString(l+1);
                            }
                            tm.addRow(d);

                        }

                    }

                }

            }
            tm.fireTableDataChanged();

            // Close ResultSet and Statement
            rs.close();
            pst.close();

        } catch (Exception ex) {
            //JOptionPane.showMessageDialog(this, ex, ex.getMessage(), WIDTH, null);
        }*/
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        natisni.setText("Natisni");
        natisni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                natisniActionPerformed(evt);
            }
        });

        osvezi.setText("Osveži");
        osvezi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                osveziActionPerformed(evt);
            }
        });

        jLabel5.setText("Konto");

        jLabel6.setText("Opis");

        jLabel7.setText("Gornja skupina");

        jLabel8.setText("Dovoljena knjižba");

        dodajDebetCheck.setText("Debet");

        dodajKreditCheck.setText("Kredit");

        dodajStmCheck.setText("Obvezno stroškovno mesto");
        dodajStmCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dodajStmCheckActionPerformed(evt);
            }
        });

        dodajParnterCheck.setText("Obvezen vnos partnerja");

        dodajButt.setText("Dodaj konto");
        dodajButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dodajButtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dodajKontoPanelLayout = new javax.swing.GroupLayout(dodajKontoPanel);
        dodajKontoPanel.setLayout(dodajKontoPanelLayout);
        dodajKontoPanelLayout.setHorizontalGroup(
            dodajKontoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dodajKontoPanelLayout.createSequentialGroup()
                .addGroup(dodajKontoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dodajKontoPanelLayout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addGroup(dodajKontoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(dodajKontoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dodajKontoPanelLayout.createSequentialGroup()
                                .addComponent(dodajKontoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(200, 200, 200)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(dodajDebetCheck)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(dodajKreditCheck))
                            .addGroup(dodajKontoPanelLayout.createSequentialGroup()
                                .addComponent(dodajOpisTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(dodajKontoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dodajParnterCheck)
                                    .addComponent(dodajStmCheck)))))
                    .addGroup(dodajKontoPanelLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dodajParrentTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(dodajButt)
                .addContainerGap())
        );
        dodajKontoPanelLayout.setVerticalGroup(
            dodajKontoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dodajKontoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dodajKontoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(dodajKontoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(dodajDebetCheck)
                    .addComponent(dodajKreditCheck))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dodajKontoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(dodajOpisTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dodajStmCheck))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dodajKontoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(dodajParrentTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dodajParnterCheck))
                .addContainerGap(19, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dodajKontoPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dodajButt)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Dodaj konto", dodajKontoPanel);

        jLabel1.setText("Konto");

        jLabel2.setText("Opis");

        jLabel3.setText("Gornja skupina");

        parrentTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parrentTxtActionPerformed(evt);
            }
        });

        jLabel4.setText("Dovoljena knjižba");

        debetCheck.setText("Debet");

        kreditCheck.setText("Kredit");

        stmCheck.setText("Obvezno stroškovno mesto");
        stmCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stmCheckActionPerformed(evt);
            }
        });

        partnerCheck.setText("Obvezen vnos partnerja");

        potrdiButt.setText("Spremeni konto");
        potrdiButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                potrdiButtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout spremeniKontoPanelLayout = new javax.swing.GroupLayout(spremeniKontoPanel);
        spremeniKontoPanel.setLayout(spremeniKontoPanelLayout);
        spremeniKontoPanelLayout.setHorizontalGroup(
            spremeniKontoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(spremeniKontoPanelLayout.createSequentialGroup()
                .addGroup(spremeniKontoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(spremeniKontoPanelLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(parrentTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(spremeniKontoPanelLayout.createSequentialGroup()
                        .addGroup(spremeniKontoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(spremeniKontoPanelLayout.createSequentialGroup()
                                .addGap(65, 65, 65)
                                .addComponent(jLabel1))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, spremeniKontoPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(spremeniKontoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(kontoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(opisTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(23, 23, 23)
                .addGroup(spremeniKontoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(spremeniKontoPanelLayout.createSequentialGroup()
                        .addComponent(partnerCheck)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                        .addComponent(potrdiButt))
                    .addComponent(stmCheck)
                    .addGroup(spremeniKontoPanelLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(debetCheck)
                        .addGap(18, 18, 18)
                        .addComponent(kreditCheck)))
                .addContainerGap())
        );
        spremeniKontoPanelLayout.setVerticalGroup(
            spremeniKontoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(spremeniKontoPanelLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(spremeniKontoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(kontoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(kreditCheck)
                    .addComponent(debetCheck))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(spremeniKontoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(opisTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stmCheck))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(spremeniKontoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(spremeniKontoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(parrentTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(partnerCheck))
                .addGap(0, 25, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, spremeniKontoPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(potrdiButt)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Spremeni konto", spremeniKontoPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(dodajKonto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(osvezi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(natisni)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jTabbedPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dodajKonto)
                    .addComponent(natisni)
                    .addComponent(osvezi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rsDovoljenja = null;

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

            pst = con.prepareStatement("SELECT * FROM dovoljenja WHERE skupina=?");
            pst.setInt(1,Login.skupina);
            rsDovoljenja = pst.executeQuery();

            if (rsDovoljenja.next()) {
                dovoljenjaDodajKonto=rsDovoljenja.getInt("dodajKonto");
            }

        } catch (SQLException ex) {

        }

        finally {
            try {

                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {

            }
        }
        if (dovoljenjaDodajKonto==0)
        {
            dodajKonto.setEnabled(false);
        }

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dodajKontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dodajKontoActionPerformed
        new dodajKonto().setVisible(true);
    }//GEN-LAST:event_dodajKontoActionPerformed

    private void natisniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_natisniActionPerformed
        
        printKontniNacrt();
    }//GEN-LAST:event_natisniActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        
        if (evt.getClickCount() == 2){
            
            //osnovna.test.setText(String.valueOf(Login.skupina));
            if(dovoljenjaSpremeniKonto==1)
            {
            getValueOfSelectedRow();
            jTabbedPane1.setSelectedIndex(1);
            if(stariKonto!=null)
                {
                    populateSK();
                }
            
            }
        }     
    }//GEN-LAST:event_jTable1MouseClicked

    private void osveziActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_osveziActionPerformed
        populateKN();
    }//GEN-LAST:event_osveziActionPerformed

    private void parrentTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_parrentTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_parrentTxtActionPerformed

    private void stmCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stmCheckActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stmCheckActionPerformed

    private void potrdiButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_potrdiButtActionPerformed
        if(dovoljenjaSpremeniKonto==1)
        {
            preberiSpremenljivke();
            preveriSpremenljivke();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Nimate dovoljenja za spremembo konta","Spremeni konto - napaka",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_potrdiButtActionPerformed

    private void dodajStmCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dodajStmCheckActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dodajStmCheckActionPerformed

    private void dodajButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dodajButtActionPerformed
        if(dovoljenjaDodajKonto==1)
        {
            dodajKonto();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Nimate dovoljenja za dodajanje konta","Spremeni konto - napaka",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_dodajButtActionPerformed

    public void dodajKonto()
    {
        preberiDodajSpremenljivke();
        preveriDodajSpremenljivke();        
    }
    
    public void preberiDodajSpremenljivke()
    {
       dodajKonto2=dodajKontoTxt.getText();
       nDodajKonto=dodajKonto2.replaceAll("[^0-9]", "");       
       dodajOpis=dodajOpisTxt.getText();
       dodajParrent=dodajParrentTxt.getText();
       nDodajParrent=dodajParrent.replaceAll("[^0-9]", "");
       if(dodajDebetCheck.isSelected())
       {
           dodajDebet=1;
       }
       else
       {
           dodajDebet=0;
       }
       if(dodajKreditCheck.isSelected())
       {
           dodajKredit=1;
       }
       else
       {
           dodajKredit=0;
       }
       if(dodajStmCheck.isSelected())
       {
           dodajStm=1;
       }
       else
       {
           dodajStm=0;
       }
    }
    
    public void preveriDodajSpremenljivke()
    {
        dodajNapaka=0; //resetira napake
        //osnovna.test.setText(String.valueOf(nDodajKonto));
        
        if(nDodajKonto!=null&&!nDodajKonto.isEmpty()) //preveri, če je vrednost konta prazna
        {
            
        }
        else
        {
            dodajNapaka=1;            
        }        
                
        Connection con = null; //če novi konto ni enak staremu, preveri, če že obstaja
        PreparedStatement pst = null;
        ResultSet rs = null;
            
        try {
            con = DriverManager.getConnection(dburl, dbuser, dbpassword); 
            pst = con.prepareStatement("SELECT * FROM kontniNacrt WHERE konto=?");          
            pst.setString(1, nDodajKonto);                        
            rs=pst.executeQuery();
            if (rs.next()) {           
               dodajNapaka=2;
               }            
            }            
            catch (SQLException ex)
            {
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
            } 
            catch (SQLException ex) {
                //Logger lgr = Logger.getLogger(Version.class.getName());
                //lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }            
        
        
        if(dodajOpis!=null&&!dodajOpis.isEmpty()) //preveri, če je vrednost opisa prazna
        {
            
        }
        else
        {
            dodajNapaka=3;            
        }
        
        if(nDodajParrent!=null&&!nDodajParrent.isEmpty()) //preveri, če je vrednost gornje skupine prazna
        {
            
        }
        else
        {
            dodajNapaka=4;            
        } 
        
        osnovna.test.setText(String.valueOf(dodajNapaka));
        
        switch (dodajNapaka)
        {
            case 1:
                tekstNapake="Vrednost konta ne sme biti prazna!";
                break;
            case 2:
                tekstNapake="Konto že obstaja!";
                break;
            case 3:
                tekstNapake="Vrednost opisa ne sme biti prazna!";
                break;
            case 4:
                tekstNapake="Vrednost gornje skupine ne sme biti prazna!";
                break;
        }        
        if(dodajNapaka==0)
        {
            dodajKonto2();
        }
        else
        {
            naslovNapake="Dodaj konto - napaka";
            prikazNapake(tekstNapake);
        }
    }
    
    public void dodajKonto2()
    {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;       

        try {
            con = DriverManager.getConnection(dburl, dbuser, dbpassword);
            pst = con.prepareStatement("INSERT INTO kontniNacrt"+"(konto, opis, debet, kredit, parrent, stm, partner) VALUES"+"(?,?,?,?,?,?,?)");
            pst.setString(1, nDodajKonto);
            pst.setString(2, dodajOpis);
            pst.setInt(3, dodajDebet);
            pst.setInt(4, dodajKredit);
            pst.setString(5, nDodajParrent);
            pst.setInt(6, dodajStm);            
            pst.setInt(7,dodajPartner);            
            //osnovna.test.setText(String.valueOf(pst));
            String statement=String.valueOf(pst);
            pst.executeUpdate();
            pst = con.prepareStatement("INSERT INTO log"+"(tekst, uporabnik, podrocje, datum) VALUES"+"(?,?,?,?)");
            pst.setString(1, statement);
            pst.setString(2, Login.anUporabnik);
            String podrocje="kontni načrt";
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
        populateKN();
    }
    
    public void preberiSpremenljivke()
    {
       konto=kontoTxt.getText();
       nKonto=konto.replaceAll("[^0-9]", "");       
       opis=opisTxt.getText();
       parrent=parrentTxt.getText();
       nParrent=parrent.replaceAll("[^0-9]", "");
       if(debetCheck.isSelected())
       {
           debet=1;
       }
       else
       {
           debet=0;
       }
       if(kreditCheck.isSelected())
       {
           kredit=1;
       }
       else
       {
           kredit=0;
       }
       if(stmCheck.isSelected())
       {
           stm=1;
       }
       else
       {
           stm=0;
       }
    }
    
    public void preveriSpremenljivke()
    {
        napaka=0; //resetira napake
        
        if(nKonto!=null&&!nKonto.isEmpty()) //preveri, če je vrednost konta prazna
        {
            
        }
        else
        {
            napaka=1;            
        }        
        
        if(!stariKonto.equals(nKonto)) //preveri, če je novi konto enak staremu
        {
            Connection con = null; //če novi konto ni enak staremu, preveri, če že obstaja
            PreparedStatement pst = null;
            ResultSet rs = null;
            
            try {
            con = DriverManager.getConnection(dburl, dbuser, dbpassword); 
            pst = con.prepareStatement("SELECT * FROM kontniNacrt WHERE konto=?");          
            pst.setString(1, nKonto);                        
            rs=pst.executeQuery();
            if (rs.next()) {           
               napaka=2;
               }            
            }            
            catch (SQLException ex)
            {
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
            } 
            catch (SQLException ex) {
                //Logger lgr = Logger.getLogger(Version.class.getName());
                //lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }            
        }
        
        if(opis!=null&&!opis.isEmpty()) //preveri, če je vrednost opisa prazna
        {
            
        }
        else
        {
            napaka=3;            
        }
        
        if(nParrent!=null&&!nParrent.isEmpty()) //preveri, če je vrednost gornje skupine prazna
        {
            
        }
        else
        {
            napaka=4;            
        } 
        
        osnovna.test.setText(String.valueOf(napaka));
        
        switch (napaka)
        {
            case 1:
                tekstNapake="Vrednost konta ne sme biti prazna!";
                break;
            case 2:
                tekstNapake="Konto že obstaja!";
                break;
            case 3:
                tekstNapake="Vrednost opisa ne sme biti prazna!";
                break;
            case 4:
                tekstNapake="Vrednost gornje skupine ne sme biti prazna!";
                break;
        }        
        if(napaka==0)
        {
            updateSK();
        }
        else
        {
            naslovNapake="Spremeni konto - napaka";
            prikazNapake(tekstNapake);
        }
    }
    
    public void updateSK()
    {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        konto=kontoTxt.getText();
        nKonto=konto.replaceAll("[^0-9]", "");       
        opis=opisTxt.getText();
        parrent=parrentTxt.getText();
        nParrent=parrent.replaceAll("[^0-9]", "");
        if(debetCheck.isSelected())
        {
           debet=1;
        }
        else
        {
           debet=0;
        }
        if(kreditCheck.isSelected())
        {
           kredit=1;
        }
        else
        {
           kredit=0;
        }
        if(stmCheck.isSelected())
        {
           stm=1;
        }
        else
        {
           stm=0;
        }
        if(partnerCheck.isSelected())
        {
           partner=1;
        }
        else
        {
           partner=0;
        }

        try {
            con = DriverManager.getConnection(dburl, dbuser, dbpassword);
            pst = con.prepareStatement("UPDATE kontniNacrt SET konto=?, opis=?, debet=?, kredit=?, parrent=?, stm=?, partner=? WHERE zapst=?");
            pst.setString(1, nKonto);
            pst.setString(2, opis);
            pst.setInt(3, debet);
            pst.setInt(4, kredit);
            pst.setString(5, nParrent);
            pst.setInt(6, stm);            
            pst.setInt(7,partner);
            pst.setInt(8, zapst);
            //osnovna.test.setText(String.valueOf(pst));
            String statement=String.valueOf(pst);
            pst.executeUpdate();
            pst = con.prepareStatement("INSERT INTO log"+"(tekst, uporabnik, podrocje, datum) VALUES"+"(?,?,?,?)");
            pst.setString(1, statement);
            pst.setString(2, Login.anUporabnik);
            String podrocje="kontni načrt";
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
        populateKN();
    }
    
    public void prikazNapake(String tekstNapake)
    {
        JOptionPane.showMessageDialog(null,tekstNapake,naslovNapake,JOptionPane.ERROR_MESSAGE);
    }
    
    public void getValueOfSelectedRow(){
        int row = jTable1.getSelectedRow();
        value=jTable1.getValueAt(row, 0);
        stariKonto=String.valueOf(value);
    }
    
    public void populateSK() //izpolni vrednosti v področju spremeni konto
    {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        //geslo1=null;                        

        try {
            con = DriverManager.getConnection(dburl, dbuser, dbpassword); //populate with old values
            pst = con.prepareStatement("SELECT * FROM kontniNacrt WHERE konto=?");          
            pst.setString(1, stariKonto);                        
            rs=pst.executeQuery();
            if (rs.next()) {           
               konto2= rs.getString("konto");
               opis= rs.getString("opis");
               parrent= rs.getString("parrent");
               debet=rs.getInt("debet");
               kredit=rs.getInt("kredit");
               stm=rs.getInt("stm");
               zapst=rs.getInt("zapst");
               partner=rs.getInt("partner");
            }
            
            kontoTxt.setText(konto2);
            opisTxt.setText(opis);
            parrentTxt.setText(parrent);
            
            if(debet==1){
                debetCheck.setSelected(true);
            }
            if(kredit==1){
                kreditCheck.setSelected(true);
            }
            if(stm==1){
                stmCheck.setSelected(true);
            }
            if(partner==1){
                partnerCheck.setSelected(true);
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
            java.util.logging.Logger.getLogger(kontniNacrt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(kontniNacrt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(kontniNacrt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(kontniNacrt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {           
            public void run() {               
                             
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox debetCheck;
    private javax.swing.JButton dodajButt;
    private javax.swing.JCheckBox dodajDebetCheck;
    private javax.swing.JButton dodajKonto;
    private javax.swing.JPanel dodajKontoPanel;
    private javax.swing.JTextField dodajKontoTxt;
    private javax.swing.JCheckBox dodajKreditCheck;
    private javax.swing.JTextField dodajOpisTxt;
    private javax.swing.JCheckBox dodajParnterCheck;
    private javax.swing.JTextField dodajParrentTxt;
    private javax.swing.JCheckBox dodajStmCheck;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    public javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    public javax.swing.JTable jTable1;
    private javax.swing.JTextField kontoTxt;
    private javax.swing.JCheckBox kreditCheck;
    private javax.swing.JButton natisni;
    private javax.swing.JTextField opisTxt;
    private javax.swing.JButton osvezi;
    private javax.swing.JTextField parrentTxt;
    private javax.swing.JCheckBox partnerCheck;
    private javax.swing.JButton potrdiButt;
    private javax.swing.JPanel spremeniKontoPanel;
    private javax.swing.JCheckBox stmCheck;
    // End of variables declaration//GEN-END:variables
}
