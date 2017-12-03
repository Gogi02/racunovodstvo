/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racunovodstvo;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.*;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import static racunovodstvo.Login.dbpassword;
import static racunovodstvo.Login.dburl;
import static racunovodstvo.Login.dbuser;
import static racunovodstvo.partnerji.value;
import static racunovodstvo.vnosPrometa.stevilkaZacasneTemeljnice;

/**
 *
 * @author gogi
 */
public class knjizenje extends javax.swing.JFrame {
    
    int isciPartner, isciPartner2, isciVezniDokument, isciValuto, dovoljenjaSpremeniPartnerja, dovoljenjaDodajPartnerja, sifraPartnerja;
    String naziv, priimek, ime, davcnaStevilka, sifraPartnerjaTemp, vezniDokumentTemp, valutaTemp;
    
    DefaultTableModel tm, tm3, tm4;

    /**
     * Creates new form knjizenje
     */
    public knjizenje() {
        isciPartner2=1;
        initComponents();
        stevilkaZacasneTemeljniceLabel.setText(String.valueOf(stevilkaZacasneTemeljnice));
        stevilkaZacasneTemeljniceLabel2.setText(String.valueOf(stevilkaZacasneTemeljnice));
    }
    
    public void getValueOfSelectedRow3()
    {
        int row = jTable3.getSelectedRow();
        value = jTable3.getValueAt(row, 0);
        vezniDokumentTemp = String.valueOf(value);
    }
    
    public void getValueOfSelectedRow4()
    {
        int row = knjizenjeIsciValutoTable.getSelectedRow();
        value = knjizenjeIsciValutoTable.getValueAt(row, 0);
        valutaTemp = String.valueOf(value);
    }
    
    public void populateIsciValuto()
    {
        
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        

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
            pst = con.prepareStatement("SELECT sifraValute, kratica, opis FROM valute");
            rs = pst.executeQuery();
            
        } catch (SQLException ex) {
            // Logger lgr = Logger.getLogger(Version.class.getName());
            //lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

        try {

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            tm4 = (DefaultTableModel) knjizenjeIsciValutoTable.getModel();
            knjizenjeIsciValutoTable.setModel(tm4);

            knjizenjeIsciValutoTable.setRowSelectionAllowed(true);
            tm4.setColumnCount(0);

            // add specified columns to table
            for (i = 1; i <= columnCount; i++) {
                tm4.addColumn(rsmd.getColumnName(i));
            }

            // clear existing rows
            tm4.setRowCount(0);
            
            
            // add rows to table
            while (rs.next()) {                
                String[] a = new String[columnCount];
                for (i = 0; i < columnCount; i++) {
                    a[i] = rs.getString(i + 1);
                }
                tm4.addRow(a);
            }
            tm4.fireTableDataChanged();

            // Close ResultSet and Statement
            rs.close();
            pst.close();

        } catch (Exception ex) {
            //JOptionPane.showMessageDialog(this, ex, ex.getMessage(), WIDTH, null);
        }
    }
    
    public void populateZacasneKnjizbe()
    {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        

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
            pst = con.prepareStatement("SELECT konto, protikonto, debet, kredit, stm FROM knjizbe WHERE stevilkaTemeljnice=?");
            pst.setInt(1,stevilkaZacasneTemeljnice);
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
            for (i = 1; i <= columnCount; i++) {
                tm.addColumn(rsmd.getColumnName(i));
            }

            // clear existing rows
            tm.setRowCount(0);

            // add rows to table
            while (rs.next()) {
                //int parrent = rs.getInt("stevilka");
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
            con.close();

        } catch (Exception ex) {
            //JOptionPane.showMessageDialog(this, ex, ex.getMessage(), WIDTH, null);
        }
    }

    public void populateVezniDokument2()
    {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        //dodajKnjizboPartnerFTF.setValue(new Integer(isciPartner2));
        //isciPartner2=((Number)dodajKnjizboPartnerFTF.getValue()).intValue();
        
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
            String tempPartner2x=dodajKnjizboPartnerFTF.getText();
            if(tempPartner2x!=null&&!tempPartner2x.isEmpty())
            {
                pst = con.prepareStatement("SELECT vezniDokument, datum, konto, debet, kredit FROM glavnaKnjiga WHERE odprto=? AND sifraPartnerja=?");
                int odprto=1;
                pst.setInt(1, odprto);
                int tempPartner2=Integer.parseInt(tempPartner2x);
                pst.setInt(2, tempPartner2);
            }
            else
            {
                pst = con.prepareStatement("SELECT vezniDokument, datum, konto, debet, kredit FROM glavnaKnjiga WHERE odprto=?");
                int odprto=1;
                pst.setInt(1, odprto);
            }
            
            rs = pst.executeQuery();            

        } catch (SQLException ex) {
            // Logger lgr = Logger.getLogger(Version.class.getName());
            //lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

        try {

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            tm3 = (DefaultTableModel) jTable3.getModel();
            jTable3.setModel(tm3);

            jTable3.setRowSelectionAllowed(true);
            tm3.setColumnCount(0);

            // add specified columns to table
            for (i = 1; i <= columnCount; i++) {
                tm3.addColumn(rsmd.getColumnName(i));
            }

            // clear existing rows
            tm3.setRowCount(0);
            //osnovna.test.setText("test");
            // add rows to table
            while (rs.next()) {
                Long datumEpoch=rs.getLong("datum");
                datumEpoch=datumEpoch*1000L;
                Date datum= new Date(datumEpoch);
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                                
                String datumString=format.format(datum);
                //osnovna.test.setText(datumString);                           
                
                String[] a = new String[columnCount];
                for (i = 0; i < columnCount; i++) {
                    if (i==1)
                    {
                        a[1] = datumString;
                    }                    
                    else
                    {
                        a[i] = rs.getString(i + 1);
                    }
                }
                tm3.addRow(a);
            }
            tm3.fireTableDataChanged();

            // Close ResultSet and Statement
            rs.close();
            pst.close();

        } catch (Exception ex) {
            //JOptionPane.showMessageDialog(this, ex, ex.getMessage(), WIDTH, null);
        }
    }
    
    public void populateVezniDokument()
    {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        //dodajKnjizboPartnerFTF.setValue(new Integer(isciPartner2));
        //isciPartner2=((Number)dodajKnjizboPartnerFTF.getValue()).intValue();
        
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
            pst = con.prepareStatement("SELECT vezniDokument, datum, konto, debet, kredit FROM glavnaKnjiga WHERE odprto=?");
            //pst.setInt(1, isciPartner2);
            int odprto=1;
            pst.setInt(1, odprto);
            //String tempPartner2x=dodajKnjizboPartnerFTF.getText();
            //int tempPartner2=Integer.parseInt(tempPartner2x);
            //pst.setInt(2, tempPartner2);
            
            rs = pst.executeQuery();            

        } catch (SQLException ex) {
            // Logger lgr = Logger.getLogger(Version.class.getName());
            //lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

        try {

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            tm3 = (DefaultTableModel) jTable3.getModel();
            jTable3.setModel(tm3);

            jTable3.setRowSelectionAllowed(true);
            tm3.setColumnCount(0);

            // add specified columns to table
            for (i = 1; i <= columnCount; i++) {
                tm3.addColumn(rsmd.getColumnName(i));
            }

            // clear existing rows
            tm3.setRowCount(0);
            //osnovna.test.setText("test");
            // add rows to table
            while (rs.next()) {
                Long datumEpoch=rs.getLong("datum");
                datumEpoch=datumEpoch*1000L;
                Date datum= new Date(datumEpoch);
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                                
                String datumString=format.format(datum);
                //osnovna.test.setText(datumString);                           
                
                String[] a = new String[columnCount];
                for (i = 0; i < columnCount; i++) {
                    if (i==1)
                    {
                        a[1] = datumString;
                    }                    
                    else
                    {
                        a[i] = rs.getString(i + 1);
                    }
                }
                tm3.addRow(a);
            }
            tm3.fireTableDataChanged();

            // Close ResultSet and Statement
            rs.close();
            pst.close();

        } catch (Exception ex) {
            //JOptionPane.showMessageDialog(this, ex, ex.getMessage(), WIDTH, null);
        }
    }
    
    public void populatePartnerji()
    {
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

            tm = (DefaultTableModel) jTable2.getModel();
            jTable2.setModel(tm);

            jTable2.setRowSelectionAllowed(true);
            tm.setColumnCount(0);

            // add specified columns to table
            for (i = 1; i <= columnCount; i++) {
                tm.addColumn(rsmd.getColumnName(i));
            }

            // clear existing rows
            tm.setRowCount(0);
            
            
            // add rows to table
            while (rs.next()) {
                //String parrent = rs.getString("ime");
                //osnovna.test.setText("test3");
                //parrent="test";
                //rs.updateString("ime", parrent);
                //String parrent=rs.getString("ime");
                //osnovna.test.setText(parrent);
                //rs.updateRow();
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
    
    public void getValueOfSelectedRow2()
    {
        int row = jTable2.getSelectedRow();
        value = jTable2.getValueAt(row, 0);
        sifraPartnerjaTemp = String.valueOf(value);
    }
    
    public void preberiSpremenljivkeDodajKnjizbo()
    {
        
    }    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        knjizenjeIsciPartnerja = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;   //Disallow the editing of any cell
            }
        };
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        isciNazivTxt = new javax.swing.JTextField();
        isciPriimekTxt = new javax.swing.JTextField();
        isciImeTxt = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        isciSifraPartnerjaTxt = new javax.swing.JTextField();
        isciDavcnaStevilkaTxt = new javax.swing.JTextField();
        knjizenjeIsciButton = new javax.swing.JButton();
        knjizenjeIzberiButton = new javax.swing.JButton();
        knjizenjeIsciVezniDokument = new javax.swing.JDialog();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;   //Disallow the editing of any cell
            }
        };
        isciVezniDokumentIzberiButton = new javax.swing.JButton();
        isciVezniDokumentZapri = new javax.swing.JButton();
        knjizenjeIsciValuto = new javax.swing.JDialog();
        jScrollPane4 = new javax.swing.JScrollPane();
        knjizenjeIsciValutoTable = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;   //Disallow the editing of any cell
            }
        };
        knjizenjeIsciValutoIzberiButton = new javax.swing.JButton();
        knjizenjeIsciValutoZapriButton = new javax.swing.JButton();
        zapriButton = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        stevilkaZacasneTemeljniceLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        dodajKnjizboIsciPartnerButton = new javax.swing.JButton();
        dodajKnjizboIsciVezniDokumentButton = new javax.swing.JButton();
        dodajKnjizboKontoFTF = new javax.swing.JFormattedTextField();
        dodajKnjizboProtikontoFTF = new javax.swing.JFormattedTextField();
        dodajKnjizboPartnerFTF = new javax.swing.JFormattedTextField();
        dodajKnjizboPartnerLabel = new javax.swing.JLabel();
        dodajKnjizboVezniDokumentFTF = new javax.swing.JFormattedTextField();
        dodajKnjizboSTMFTF = new javax.swing.JFormattedTextField();
        dodajKnjizboDatumFTF = new javax.swing.JFormattedTextField();
        dodajKnjizboValutaFTF = new javax.swing.JFormattedTextField();
        dodajKnjizboIsciValutoButton = new javax.swing.JButton();
        dodajKnjizboValutaLabel = new javax.swing.JLabel();
        dodajKnjizboButton = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        dodajKnjizboTecajFTF = new javax.swing.JFormattedTextField();
        dodajKnjizboDebetFTF = new javax.swing.JFormattedTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        dodajKnjizboKreditFTF = new javax.swing.JFormattedTextField();
        jLabel25 = new javax.swing.JLabel();
        dodajKnjizboDebetVALFTF = new javax.swing.JFormattedTextField();
        jLabel26 = new javax.swing.JLabel();
        dodajKnjizboKreditVALFTF = new javax.swing.JFormattedTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        stevilkaZacasneTemeljniceLabel2 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        popraviKnjizboPatnerLabel = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        popraviKnjizboKontoFTF = new javax.swing.JFormattedTextField();
        popraviKnjizboProtikontoFTF = new javax.swing.JFormattedTextField();
        popraviKnjizboPartnerFTF = new javax.swing.JFormattedTextField();
        popraviKnjizboVezniDokumentFTF = new javax.swing.JFormattedTextField();
        jLabel19 = new javax.swing.JLabel();
        popraviKnjizboSTMFTF = new javax.swing.JFormattedTextField();
        popraviKnjizboDatumFTF = new javax.swing.JFormattedTextField();
        popraviKnjizboValutaFTF = new javax.swing.JFormattedTextField();
        popraviKnjizboIsciPartnerButton = new javax.swing.JButton();
        popraviKnjizboIsciVezniDokumentButton = new javax.swing.JButton();
        popraviKnjizboIsciValutoButton = new javax.swing.JButton();
        popraviKnjizboValutaLabel = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        popraviKnjizboTecajFTF = new javax.swing.JFormattedTextField();
        jLabel28 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;   //Disallow the editing of any cell
            }};

            knjizenjeIsciPartnerja.setMinimumSize(new java.awt.Dimension(560, 446));

            populatePartnerji();
            jTable2.setModel(tm);
            jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    jTable2MouseClicked(evt);
                }
            });
            jScrollPane2.setViewportView(jTable2);

            jLabel9.setText("Naziv:");

            jLabel10.setText("Priimek:");

            jLabel11.setText("Ime:");

            jLabel12.setText("Šifra partnerja:");

            jLabel13.setText("Davčna številka:");

            knjizenjeIsciButton.setText("Išči");
            knjizenjeIsciButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    knjizenjeIsciButtonActionPerformed(evt);
                }
            });

            knjizenjeIzberiButton.setText("Izberi");
            knjizenjeIzberiButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    knjizenjeIzberiButtonActionPerformed(evt);
                }
            });

            javax.swing.GroupLayout knjizenjeIsciPartnerjaLayout = new javax.swing.GroupLayout(knjizenjeIsciPartnerja.getContentPane());
            knjizenjeIsciPartnerja.getContentPane().setLayout(knjizenjeIsciPartnerjaLayout);
            knjizenjeIsciPartnerjaLayout.setHorizontalGroup(
                knjizenjeIsciPartnerjaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(knjizenjeIsciPartnerjaLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(knjizenjeIsciPartnerjaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 536, Short.MAX_VALUE)
                        .addGroup(knjizenjeIsciPartnerjaLayout.createSequentialGroup()
                            .addGroup(knjizenjeIsciPartnerjaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel10)
                                .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(knjizenjeIsciPartnerjaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(isciNazivTxt)
                                .addComponent(isciPriimekTxt)
                                .addComponent(isciImeTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                            .addGap(30, 30, 30)
                            .addGroup(knjizenjeIsciPartnerjaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(knjizenjeIsciPartnerjaLayout.createSequentialGroup()
                                    .addGroup(knjizenjeIsciPartnerjaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel12)
                                        .addComponent(jLabel13))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(knjizenjeIsciPartnerjaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(isciSifraPartnerjaTxt)
                                        .addComponent(isciDavcnaStevilkaTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)))
                                .addComponent(knjizenjeIsciButton)
                                .addComponent(knjizenjeIzberiButton))
                            .addGap(0, 0, Short.MAX_VALUE)))
                    .addContainerGap())
            );
            knjizenjeIsciPartnerjaLayout.setVerticalGroup(
                knjizenjeIsciPartnerjaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, knjizenjeIsciPartnerjaLayout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(knjizenjeIsciPartnerjaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(isciNazivTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12)
                        .addComponent(isciSifraPartnerjaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(knjizenjeIsciPartnerjaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(isciPriimekTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13)
                        .addComponent(isciDavcnaStevilkaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(knjizenjeIsciPartnerjaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(isciImeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(knjizenjeIsciButton))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(knjizenjeIzberiButton)
                    .addGap(211, 211, 211))
            );

            knjizenjeIsciVezniDokument.setMinimumSize(new java.awt.Dimension(450, 600));

            populateVezniDokument();
            jTable3.setModel(tm3);
            jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    jTable3MouseClicked(evt);
                }
            });
            jScrollPane3.setViewportView(jTable3);

            isciVezniDokumentIzberiButton.setText("Izberi");
            isciVezniDokumentIzberiButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    isciVezniDokumentIzberiButtonActionPerformed(evt);
                }
            });

            isciVezniDokumentZapri.setText("Zapri");
            isciVezniDokumentZapri.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    isciVezniDokumentZapriActionPerformed(evt);
                }
            });

            javax.swing.GroupLayout knjizenjeIsciVezniDokumentLayout = new javax.swing.GroupLayout(knjizenjeIsciVezniDokument.getContentPane());
            knjizenjeIsciVezniDokument.getContentPane().setLayout(knjizenjeIsciVezniDokumentLayout);
            knjizenjeIsciVezniDokumentLayout.setHorizontalGroup(
                knjizenjeIsciVezniDokumentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(knjizenjeIsciVezniDokumentLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(knjizenjeIsciVezniDokumentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(isciVezniDokumentZapri)
                        .addComponent(isciVezniDokumentIzberiButton)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            knjizenjeIsciVezniDokumentLayout.setVerticalGroup(
                knjizenjeIsciVezniDokumentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(knjizenjeIsciVezniDokumentLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(isciVezniDokumentIzberiButton)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(isciVezniDokumentZapri)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );

            knjizenjeIsciValuto.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            knjizenjeIsciValuto.setMinimumSize(new java.awt.Dimension(440, 501));

            populateIsciValuto();
            knjizenjeIsciValutoTable.setModel(tm4);
            knjizenjeIsciValutoTable.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    knjizenjeIsciValutoTableMouseClicked(evt);
                }
            });
            jScrollPane4.setViewportView(knjizenjeIsciValutoTable);

            knjizenjeIsciValutoIzberiButton.setText("Izberi");
            knjizenjeIsciValutoIzberiButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    knjizenjeIsciValutoIzberiButtonActionPerformed(evt);
                }
            });

            knjizenjeIsciValutoZapriButton.setText("Zapri");
            knjizenjeIsciValutoZapriButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    knjizenjeIsciValutoZapriButtonActionPerformed(evt);
                }
            });

            javax.swing.GroupLayout knjizenjeIsciValutoLayout = new javax.swing.GroupLayout(knjizenjeIsciValuto.getContentPane());
            knjizenjeIsciValuto.getContentPane().setLayout(knjizenjeIsciValutoLayout);
            knjizenjeIsciValutoLayout.setHorizontalGroup(
                knjizenjeIsciValutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(knjizenjeIsciValutoLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(knjizenjeIsciValutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(knjizenjeIsciValutoLayout.createSequentialGroup()
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 1, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, knjizenjeIsciValutoLayout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addComponent(knjizenjeIsciValutoIzberiButton)
                            .addGap(18, 18, 18)
                            .addComponent(knjizenjeIsciValutoZapriButton)))
                    .addContainerGap())
            );
            knjizenjeIsciValutoLayout.setVerticalGroup(
                knjizenjeIsciValutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(knjizenjeIsciValutoLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(knjizenjeIsciValutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(knjizenjeIsciValutoZapriButton)
                        .addComponent(knjizenjeIsciValutoIzberiButton))
                    .addContainerGap())
            );

            setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

            zapriButton.setText("Zapri");
            zapriButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    zapriButtonActionPerformed(evt);
                }
            });

            stevilkaZacasneTemeljniceLabel.setText("jLabel1");

            jLabel1.setText("Številka temeljnice:");

            jLabel2.setText("Konto:");

            jLabel3.setText("Protikonto:");

            jLabel4.setText("Partner:");

            jLabel5.setText("Vezni dokument:");

            jLabel6.setText("STM:");

            jLabel7.setText("Datum:");

            jLabel8.setText("Valuta:");

            dodajKnjizboIsciPartnerButton.setText("Išči");
            dodajKnjizboIsciPartnerButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    dodajKnjizboIsciPartnerButtonActionPerformed(evt);
                }
            });

            dodajKnjizboIsciVezniDokumentButton.setText("Išči");
            dodajKnjizboIsciVezniDokumentButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    dodajKnjizboIsciVezniDokumentButtonActionPerformed(evt);
                }
            });

            dodajKnjizboKontoFTF.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    dodajKnjizboKontoFTFActionPerformed(evt);
                }
            });
            dodajKnjizboKontoFTF.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    dodajKnjizboKontoFTFKeyPressed(evt);
                }
            });

            dodajKnjizboProtikontoFTF.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    dodajKnjizboProtikontoFTFKeyPressed(evt);
                }
            });

            dodajKnjizboPartnerFTF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
            dodajKnjizboPartnerFTF.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    dodajKnjizboPartnerFTFActionPerformed(evt);
                }
            });
            dodajKnjizboPartnerFTF.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    dodajKnjizboPartnerFTFKeyPressed(evt);
                }
            });

            dodajKnjizboVezniDokumentFTF.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    dodajKnjizboVezniDokumentFTFKeyPressed(evt);
                }
            });

            dodajKnjizboSTMFTF.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    dodajKnjizboSTMFTFKeyPressed(evt);
                }
            });

            dodajKnjizboDatumFTF.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    dodajKnjizboDatumFTFKeyPressed(evt);
                }
            });

            dodajKnjizboValutaFTF.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    dodajKnjizboValutaFTFKeyPressed(evt);
                }
            });

            dodajKnjizboIsciValutoButton.setText("Išči");
            dodajKnjizboIsciValutoButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    dodajKnjizboIsciValutoButtonActionPerformed(evt);
                }
            });

            dodajKnjizboButton.setText("Dodaj knjižbo");
            dodajKnjizboButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    dodajKnjizboButtonActionPerformed(evt);
                }
            });

            jLabel15.setText("Tečaj:");

            dodajKnjizboTecajFTF.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent evt) {
                    dodajKnjizboTecajFTFKeyTyped(evt);
                }
            });

            dodajKnjizboDebetFTF.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    dodajKnjizboDebetFTFActionPerformed(evt);
                }
            });
            dodajKnjizboDebetFTF.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    dodajKnjizboDebetFTFKeyPressed(evt);
                }
            });

            jLabel23.setText("Debet:");

            jLabel24.setText("Kredit:");

            jLabel25.setText("Debet VAL:");

            dodajKnjizboDebetVALFTF.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    dodajKnjizboDebetVALFTFKeyPressed(evt);
                }
            });

            jLabel26.setText("Kredit VAL:");

            dodajKnjizboKreditVALFTF.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    dodajKnjizboKreditVALFTFKeyPressed(evt);
                }
            });

            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
            jPanel1.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel8)
                        .addComponent(jLabel7)
                        .addComponent(jLabel6)
                        .addComponent(jLabel2)
                        .addComponent(jLabel1)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4)
                        .addComponent(jLabel5))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(stevilkaZacasneTemeljniceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(dodajKnjizboPartnerFTF, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                                                .addComponent(dodajKnjizboProtikontoFTF, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                                .addComponent(dodajKnjizboKontoFTF, javax.swing.GroupLayout.Alignment.LEADING))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(dodajKnjizboIsciPartnerButton)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(dodajKnjizboPartnerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGap(14, 14, 14)))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel23)
                                        .addComponent(jLabel15)
                                        .addComponent(jLabel25))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(dodajKnjizboDebetVALFTF, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                                                .addComponent(dodajKnjizboTecajFTF))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jLabel26)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(dodajKnjizboKreditVALFTF, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(dodajKnjizboDebetFTF, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                                            .addComponent(jLabel24)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(dodajKnjizboKreditFTF, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(dodajKnjizboValutaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(dodajKnjizboDatumFTF, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addComponent(dodajKnjizboSTMFTF, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                                                    .addComponent(dodajKnjizboVezniDokumentFTF, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(dodajKnjizboIsciVezniDokumentButton))))
                                    .addGap(0, 0, Short.MAX_VALUE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(dodajKnjizboValutaFTF, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(dodajKnjizboIsciValutoButton)
                                            .addGap(0, 386, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                            .addGap(0, 0, Short.MAX_VALUE)
                                            .addComponent(dodajKnjizboButton, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addContainerGap())))
            );
            jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(stevilkaZacasneTemeljniceLabel)
                        .addComponent(jLabel1))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(dodajKnjizboKontoFTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel25)
                        .addComponent(dodajKnjizboDebetVALFTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel26)
                        .addComponent(dodajKnjizboKreditVALFTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(dodajKnjizboProtikontoFTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15)
                        .addComponent(dodajKnjizboTecajFTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(dodajKnjizboIsciPartnerButton)
                                        .addComponent(dodajKnjizboPartnerFTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(dodajKnjizboPartnerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(dodajKnjizboVezniDokumentFTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5)
                                        .addComponent(dodajKnjizboIsciVezniDokumentButton))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel6)
                                        .addComponent(dodajKnjizboSTMFTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(6, 6, 6)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel7)
                                        .addComponent(dodajKnjizboDatumFTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel8)
                                        .addComponent(dodajKnjizboValutaFTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(dodajKnjizboIsciValutoButton))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(dodajKnjizboValutaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(68, 68, 68))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(dodajKnjizboButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap())))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(9, 9, 9)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(dodajKnjizboDebetFTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel24)
                                .addComponent(dodajKnjizboKreditFTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel23)))))
            );

            jTabbedPane1.addTab("Dodaj knjižbo", jPanel1);

            jLabel14.setText("Številka temeljnice:");

            stevilkaZacasneTemeljniceLabel2.setText("jLabel15");

            jLabel16.setText("Konto:");

            jLabel17.setText("Protikonto:");

            jLabel18.setText("Partner:");

            jLabel20.setText("STM:");

            jLabel21.setText("Datum:");

            jLabel22.setText("Valuta:");

            popraviKnjizboKontoFTF.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    popraviKnjizboKontoFTFActionPerformed(evt);
                }
            });
            popraviKnjizboKontoFTF.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    popraviKnjizboKontoFTFKeyPressed(evt);
                }
            });

            popraviKnjizboProtikontoFTF.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    popraviKnjizboProtikontoFTFKeyPressed(evt);
                }
            });

            popraviKnjizboPartnerFTF.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    popraviKnjizboPartnerFTFKeyPressed(evt);
                }
            });

            popraviKnjizboVezniDokumentFTF.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    popraviKnjizboVezniDokumentFTFKeyPressed(evt);
                }
            });

            jLabel19.setText("Vezni dokument:");

            popraviKnjizboSTMFTF.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    popraviKnjizboSTMFTFKeyPressed(evt);
                }
            });

            popraviKnjizboIsciPartnerButton.setText("Išči");

            popraviKnjizboIsciVezniDokumentButton.setText("Išči");

            popraviKnjizboIsciValutoButton.setText("Išči");

            jLabel27.setText("Tečaj:");

            jLabel28.setText("Debet:");

            jFormattedTextField1.setText("jFormattedTextField1");

            javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
            jPanel2.setLayout(jPanel2Layout);
            jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel27)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(popraviKnjizboValutaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(popraviKnjizboDatumFTF)
                                .addComponent(popraviKnjizboPatnerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(stevilkaZacasneTemeljniceLabel2)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(popraviKnjizboVezniDokumentFTF, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                                                .addComponent(popraviKnjizboSTMFTF))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(popraviKnjizboIsciVezniDokumentButton))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(popraviKnjizboPartnerFTF, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                                                .addComponent(popraviKnjizboProtikontoFTF, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(popraviKnjizboKontoFTF, javax.swing.GroupLayout.Alignment.LEADING))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(popraviKnjizboIsciPartnerButton))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(popraviKnjizboValutaFTF, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(popraviKnjizboIsciValutoButton)))
                                    .addGap(0, 44, Short.MAX_VALUE)))
                            .addGap(3, 3, 3)
                            .addComponent(jLabel28)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(137, 137, 137))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(popraviKnjizboTecajFTF, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            );
            jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(stevilkaZacasneTemeljniceLabel2))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(popraviKnjizboKontoFTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel28)
                        .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17)
                        .addComponent(popraviKnjizboProtikontoFTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel18)
                        .addComponent(popraviKnjizboPartnerFTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(popraviKnjizboIsciPartnerButton))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(popraviKnjizboPatnerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(4, 4, 4)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel19)
                        .addComponent(popraviKnjizboVezniDokumentFTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(popraviKnjizboIsciVezniDokumentButton))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel20)
                        .addComponent(popraviKnjizboSTMFTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel21)
                        .addComponent(popraviKnjizboDatumFTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel22)
                        .addComponent(popraviKnjizboValutaFTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(popraviKnjizboIsciValutoButton))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(popraviKnjizboValutaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel27)
                        .addComponent(popraviKnjizboTecajFTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );

            jTabbedPane1.addTab("Popravi knjižbo", jPanel2);

            populateZacasneKnjizbe();
            jTable1.setModel(tm);
            jScrollPane1.setViewportView(jTable1);

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(zapriButton)
                    .addGap(77, 77, 77))
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPane1)
                        .addComponent(jScrollPane1))
                    .addContainerGap())
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(19, 19, 19)
                    .addComponent(zapriButton)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void zapriButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zapriButtonActionPerformed
        int zaklep=0;
        vnosPrometa.zakleniOdkleniTemeljnico(stevilkaZacasneTemeljnice, zaklep);
        dispose();
    }//GEN-LAST:event_zapriButtonActionPerformed

    private void dodajKnjizboPartnerFTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dodajKnjizboPartnerFTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dodajKnjizboPartnerFTFActionPerformed

    private void dodajKnjizboKontoFTFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dodajKnjizboKontoFTFKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            dodajKnjizboProtikontoFTF.requestFocusInWindow();
        }
    }//GEN-LAST:event_dodajKnjizboKontoFTFKeyPressed

    private void dodajKnjizboProtikontoFTFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dodajKnjizboProtikontoFTFKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            dodajKnjizboPartnerFTF.requestFocusInWindow();
        }
    }//GEN-LAST:event_dodajKnjizboProtikontoFTFKeyPressed

    private void dodajKnjizboPartnerFTFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dodajKnjizboPartnerFTFKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            dodajKnjizboVezniDokumentFTF.requestFocusInWindow();
        }
    }//GEN-LAST:event_dodajKnjizboPartnerFTFKeyPressed

    private void dodajKnjizboVezniDokumentFTFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dodajKnjizboVezniDokumentFTFKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            dodajKnjizboSTMFTF.requestFocusInWindow();
        }
    }//GEN-LAST:event_dodajKnjizboVezniDokumentFTFKeyPressed

    private void dodajKnjizboSTMFTFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dodajKnjizboSTMFTFKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            dodajKnjizboDatumFTF.requestFocusInWindow();
        }
    }//GEN-LAST:event_dodajKnjizboSTMFTFKeyPressed

    private void dodajKnjizboDatumFTFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dodajKnjizboDatumFTFKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            dodajKnjizboValutaFTF.requestFocusInWindow();
        }
    }//GEN-LAST:event_dodajKnjizboDatumFTFKeyPressed

    private void dodajKnjizboValutaFTFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dodajKnjizboValutaFTFKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            dodajKnjizboTecajFTF.requestFocusInWindow();
        }
    }//GEN-LAST:event_dodajKnjizboValutaFTFKeyPressed

    private void dodajKnjizboIsciPartnerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dodajKnjizboIsciPartnerButtonActionPerformed
        isciPartner=1;
        knjizenjeIsciPartnerja.setVisible(true);
    }//GEN-LAST:event_dodajKnjizboIsciPartnerButtonActionPerformed

    private void knjizenjeIsciButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_knjizenjeIsciButtonActionPerformed
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

            tm = (DefaultTableModel) jTable2.getModel();
            jTable2.setModel(tm);

            jTable2.setRowSelectionAllowed(true);
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
    }//GEN-LAST:event_knjizenjeIsciButtonActionPerformed

    private void knjizenjeIzberiButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_knjizenjeIzberiButtonActionPerformed
        getValueOfSelectedRow2();
        if (isciPartner==0)
            {
                /*popraviDrzavaTxt.setText(sifraPartnerjaTemp);
                poisciTextDrzave();
                popraviDrzavaLabel.setText(drzavaText);*/
            }
            else
            {
                //osnovna.test.setText(String.valueOf(sifraPartnerjaTemp));
                dodajKnjizboPartnerFTF.setValue(new Integer(sifraPartnerjaTemp));
                //poisciTextDrzave2();
                //dodajDrzavaLabel.setText(drzavaText);
            }
            
            knjizenjeIsciPartnerja.dispose();
    }//GEN-LAST:event_knjizenjeIzberiButtonActionPerformed

    private void popraviKnjizboKontoFTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popraviKnjizboKontoFTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_popraviKnjizboKontoFTFActionPerformed

    private void dodajKnjizboKontoFTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dodajKnjizboKontoFTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dodajKnjizboKontoFTFActionPerformed

    private void popraviKnjizboKontoFTFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_popraviKnjizboKontoFTFKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            popraviKnjizboProtikontoFTF.requestFocusInWindow();
        }
    }//GEN-LAST:event_popraviKnjizboKontoFTFKeyPressed

    private void popraviKnjizboProtikontoFTFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_popraviKnjizboProtikontoFTFKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            popraviKnjizboPartnerFTF.requestFocusInWindow();
        }
    }//GEN-LAST:event_popraviKnjizboProtikontoFTFKeyPressed

    private void popraviKnjizboPartnerFTFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_popraviKnjizboPartnerFTFKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            popraviKnjizboVezniDokumentFTF.requestFocusInWindow();
        }
    }//GEN-LAST:event_popraviKnjizboPartnerFTFKeyPressed

    private void popraviKnjizboVezniDokumentFTFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_popraviKnjizboVezniDokumentFTFKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            popraviKnjizboSTMFTF.requestFocusInWindow();
        }
    }//GEN-LAST:event_popraviKnjizboVezniDokumentFTFKeyPressed

    private void popraviKnjizboSTMFTFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_popraviKnjizboSTMFTFKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            popraviKnjizboDatumFTF.requestFocusInWindow();
        }
    }//GEN-LAST:event_popraviKnjizboSTMFTFKeyPressed

    private void dodajKnjizboDebetFTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dodajKnjizboDebetFTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dodajKnjizboDebetFTFActionPerformed

    private void dodajKnjizboTecajFTFKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dodajKnjizboTecajFTFKeyTyped
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            dodajKnjizboDebetVALFTF.requestFocusInWindow();
        }
    }//GEN-LAST:event_dodajKnjizboTecajFTFKeyTyped

    private void dodajKnjizboDebetFTFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dodajKnjizboDebetFTFKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            dodajKnjizboKreditFTF.requestFocusInWindow();
        }
    }//GEN-LAST:event_dodajKnjizboDebetFTFKeyPressed

    private void dodajKnjizboDebetVALFTFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dodajKnjizboDebetVALFTFKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            dodajKnjizboKreditVALFTF.requestFocusInWindow();
        }
    }//GEN-LAST:event_dodajKnjizboDebetVALFTFKeyPressed

    private void dodajKnjizboKreditVALFTFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dodajKnjizboKreditVALFTFKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            dodajKnjizboDebetFTF.requestFocusInWindow();
        }
    }//GEN-LAST:event_dodajKnjizboKreditVALFTFKeyPressed

    private void dodajKnjizboIsciVezniDokumentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dodajKnjizboIsciVezniDokumentButtonActionPerformed
        isciVezniDokument=1;
        populateVezniDokument2();
        knjizenjeIsciVezniDokument.setVisible(true);
    }//GEN-LAST:event_dodajKnjizboIsciVezniDokumentButtonActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        if (evt.getClickCount() == 2) {
            getValueOfSelectedRow2();
        if (isciPartner==0)
            {
                /*popraviDrzavaTxt.setText(sifraPartnerjaTemp);
                poisciTextDrzave();
                popraviDrzavaLabel.setText(drzavaText);*/
            }
            else
            {
                //osnovna.test.setText(String.valueOf(sifraPartnerjaTemp));
                dodajKnjizboPartnerFTF.setValue(new Integer(sifraPartnerjaTemp));
                //poisciTextDrzave2();
                //dodajDrzavaLabel.setText(drzavaText);
            }
            
            knjizenjeIsciPartnerja.dispose();
        }
    }//GEN-LAST:event_jTable2MouseClicked

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        if (evt.getClickCount() == 2) {
            getValueOfSelectedRow3();
        if (isciVezniDokument==0)
            {
                /*popraviDrzavaTxt.setText(sifraPartnerjaTemp);
                poisciTextDrzave();
                popraviDrzavaLabel.setText(drzavaText);*/
            }
            else
            {
                //osnovna.test.setText(String.valueOf(sifraPartnerjaTemp));
                dodajKnjizboVezniDokumentFTF.setValue(new Integer(vezniDokumentTemp));
                //poisciTextDrzave2();
                //dodajDrzavaLabel.setText(drzavaText);
            }
            
            knjizenjeIsciVezniDokument.dispose();
        }
    }//GEN-LAST:event_jTable3MouseClicked

    private void isciVezniDokumentZapriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isciVezniDokumentZapriActionPerformed
        knjizenjeIsciVezniDokument.dispose();
    }//GEN-LAST:event_isciVezniDokumentZapriActionPerformed

    private void isciVezniDokumentIzberiButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isciVezniDokumentIzberiButtonActionPerformed
        getValueOfSelectedRow3();
        if (isciVezniDokument==0)
            {
                
            }
            else
            {
                dodajKnjizboVezniDokumentFTF.setValue(new Integer(vezniDokumentTemp));                
            }
            
            knjizenjeIsciVezniDokument.dispose();
    }//GEN-LAST:event_isciVezniDokumentIzberiButtonActionPerformed

    private void dodajKnjizboButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dodajKnjizboButtonActionPerformed
        preberiSpremenljivkeDodajKnjizbo();        
    }//GEN-LAST:event_dodajKnjizboButtonActionPerformed

    private void dodajKnjizboIsciValutoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dodajKnjizboIsciValutoButtonActionPerformed
        isciValuto=1;
        knjizenjeIsciValuto.setVisible(true);
    }//GEN-LAST:event_dodajKnjizboIsciValutoButtonActionPerformed

    private void knjizenjeIsciValutoIzberiButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_knjizenjeIsciValutoIzberiButtonActionPerformed
        getValueOfSelectedRow4();
        if (isciValuto==0)
            {
                
            }
            else
            {
                dodajKnjizboValutaFTF.setValue(new Integer(valutaTemp));                
            }
            
            knjizenjeIsciValuto.dispose();
    }//GEN-LAST:event_knjizenjeIsciValutoIzberiButtonActionPerformed

    private void knjizenjeIsciValutoZapriButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_knjizenjeIsciValutoZapriButtonActionPerformed
        knjizenjeIsciValuto.dispose();
    }//GEN-LAST:event_knjizenjeIsciValutoZapriButtonActionPerformed

    private void knjizenjeIsciValutoTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_knjizenjeIsciValutoTableMouseClicked
        if (evt.getClickCount() == 2) {
            getValueOfSelectedRow4();
        if (isciValuto==0)
            {
                
            }
            else
            {
                dodajKnjizboValutaFTF.setValue(new Integer(valutaTemp));                
            }
            
            knjizenjeIsciValuto.dispose();
        }
    }//GEN-LAST:event_knjizenjeIsciValutoTableMouseClicked

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
            java.util.logging.Logger.getLogger(knjizenje.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(knjizenje.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(knjizenje.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(knjizenje.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new knjizenje().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton dodajKnjizboButton;
    private javax.swing.JFormattedTextField dodajKnjizboDatumFTF;
    private javax.swing.JFormattedTextField dodajKnjizboDebetFTF;
    private javax.swing.JFormattedTextField dodajKnjizboDebetVALFTF;
    private javax.swing.JButton dodajKnjizboIsciPartnerButton;
    private javax.swing.JButton dodajKnjizboIsciValutoButton;
    private javax.swing.JButton dodajKnjizboIsciVezniDokumentButton;
    private javax.swing.JFormattedTextField dodajKnjizboKontoFTF;
    private javax.swing.JFormattedTextField dodajKnjizboKreditFTF;
    private javax.swing.JFormattedTextField dodajKnjizboKreditVALFTF;
    private javax.swing.JFormattedTextField dodajKnjizboPartnerFTF;
    private javax.swing.JLabel dodajKnjizboPartnerLabel;
    private javax.swing.JFormattedTextField dodajKnjizboProtikontoFTF;
    private javax.swing.JFormattedTextField dodajKnjizboSTMFTF;
    private javax.swing.JFormattedTextField dodajKnjizboTecajFTF;
    private javax.swing.JFormattedTextField dodajKnjizboValutaFTF;
    private javax.swing.JLabel dodajKnjizboValutaLabel;
    private javax.swing.JFormattedTextField dodajKnjizboVezniDokumentFTF;
    private javax.swing.JTextField isciDavcnaStevilkaTxt;
    private javax.swing.JTextField isciImeTxt;
    private javax.swing.JTextField isciNazivTxt;
    private javax.swing.JTextField isciPriimekTxt;
    private javax.swing.JTextField isciSifraPartnerjaTxt;
    private javax.swing.JButton isciVezniDokumentIzberiButton;
    private javax.swing.JButton isciVezniDokumentZapri;
    private javax.swing.JFormattedTextField jFormattedTextField1;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JButton knjizenjeIsciButton;
    private javax.swing.JDialog knjizenjeIsciPartnerja;
    private javax.swing.JDialog knjizenjeIsciValuto;
    private javax.swing.JButton knjizenjeIsciValutoIzberiButton;
    private javax.swing.JTable knjizenjeIsciValutoTable;
    private javax.swing.JButton knjizenjeIsciValutoZapriButton;
    private javax.swing.JDialog knjizenjeIsciVezniDokument;
    private javax.swing.JButton knjizenjeIzberiButton;
    private javax.swing.JFormattedTextField popraviKnjizboDatumFTF;
    private javax.swing.JButton popraviKnjizboIsciPartnerButton;
    private javax.swing.JButton popraviKnjizboIsciValutoButton;
    private javax.swing.JButton popraviKnjizboIsciVezniDokumentButton;
    private javax.swing.JFormattedTextField popraviKnjizboKontoFTF;
    private javax.swing.JFormattedTextField popraviKnjizboPartnerFTF;
    private javax.swing.JLabel popraviKnjizboPatnerLabel;
    private javax.swing.JFormattedTextField popraviKnjizboProtikontoFTF;
    private javax.swing.JFormattedTextField popraviKnjizboSTMFTF;
    private javax.swing.JFormattedTextField popraviKnjizboTecajFTF;
    private javax.swing.JFormattedTextField popraviKnjizboValutaFTF;
    private javax.swing.JLabel popraviKnjizboValutaLabel;
    private javax.swing.JFormattedTextField popraviKnjizboVezniDokumentFTF;
    private javax.swing.JLabel stevilkaZacasneTemeljniceLabel;
    private javax.swing.JLabel stevilkaZacasneTemeljniceLabel2;
    private javax.swing.JButton zapriButton;
    // End of variables declaration//GEN-END:variables
}
