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
import java.time.Instant;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import racunovodstvo.kontniNacrt;
import static racunovodstvo.Login.anUporabnik;
import static racunovodstvo.Login.dbpassword;
import static racunovodstvo.Login.dburl;
import static racunovodstvo.Login.dbuser;
import static racunovodstvo.Login.geslo1;
import static racunovodstvo.Login.skupina;

/**
 *
 * @author gogi
 */
public class vnosPrometa extends javax.swing.JFrame {
    DefaultTableModel tm;
    int dovoljenjaVnosPrometa, maxStevilkaZacasneTemeljnice, zacetnaStevilkaTemeljnice,
            maxStevilkaTemeljnice, maxStevilkaTemeljnic, dodajTemeljnicaMesec, dodajTemeljnicaLeto,
            dodajNapaka, zaklep, i;
    public static int prva, druga, tretja, stevilkaZacasneTemeljnice, zaklenjen, dovoljenOgledTujeTemeljnice;
    String dodajTemeljnicaOpomba, uporabnik, tekstNapake, naslovNapake;
    long dodajDatumZacasneTemeljnice;
    Date dodajDatumZacasneTemeljniceRaw;
    public static Object value;
    public static String uporabnikTemeljnice;
    /**
     * Creates new form vnosPrometa
     */
    public vnosPrometa() {
        initComponents();
    }
    
    public void populateZacasnaTemeljnica()
    {
        //osnovna.test.setText("populateZacasnaTemeljnica");
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
            pst = con.prepareStatement("SELECT stevilka, mesec, leto, opomba FROM zacasneTemeljnice");
            rs = pst.executeQuery();            
            pst2 = con.prepareStatement("SELECT vnosPrometa, ogledTujeTemeljnice FROM dovoljenja WHERE skupina=?");
            pst2.setInt(1, Login.skupina);
            rs2 = pst2.executeQuery();
            

            if (rs2.next()) {
                dovoljenjaVnosPrometa = rs2.getInt("vnosPrometa");
                dovoljenOgledTujeTemeljnice = rs2.getInt("ogledTujetemeljnice");
                //osnovna.test.setText(String.valueOf(dovoljenjaVnosPrometa));
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

        } catch (Exception ex) {
            //JOptionPane.showMessageDialog(this, ex, ex.getMessage(), WIDTH, null);
        }
    }
    
    public void dodajTemeljnico()
    {
        preberiDodajSpremenljivke();
        preveriDodajSpremenljivke();        
    }
    
    public void preberiDodajSpremenljivke()
    {
        dodajDatumZacasneTemeljniceRaw=(Date)dodajTemeljnicaDatumFTF.getValue();
        dodajDatumZacasneTemeljnice=dodajDatumZacasneTemeljniceRaw.getTime();
        dodajTemeljnicaMesec=((Number)dodajTemeljnicaMesecFTF.getValue()).intValue();
        dodajTemeljnicaLeto=((Number)dodajTemeljnicaLetoFTF.getValue()).intValue();
        dodajTemeljnicaOpomba=dodajTemeljnicaOpombaTXT.getText();
        uporabnik=Login.anUporabnik;
    }
    
    public void preveriDodajSpremenljivke()
    {
        dodajNapaka=0;
        String dodajDatumZacasneTemljniceString=String.valueOf(dodajDatumZacasneTemeljnice);
        if(dodajDatumZacasneTemljniceString!=null&&!dodajDatumZacasneTemljniceString.isEmpty()) //preveri, če je vrednost datuma prazna
        {
            
        }
        else
        {
            dodajNapaka=1;            
        }
        if(String.valueOf(dodajTemeljnicaMesec)!=null&&!String.valueOf(dodajTemeljnicaMesec).isEmpty()) //preveri, če je vrednost meseca prazna
        {
            
        }
        else
        {
            dodajNapaka=2;            
        }
        if(String.valueOf(dodajTemeljnicaLeto)!=null&&!String.valueOf(dodajTemeljnicaLeto).isEmpty()) //preveri, če je vrednost leta prazna
        {
            
        }
        else
        {
            dodajNapaka=3;            
        }
        switch (dodajNapaka)
        {
            case 1:
                tekstNapake="Datum mora biti določen!";
                break;
            case 2:
                tekstNapake="Mesec mora biti določen!";
                break;
            case 3:
                tekstNapake="Leto mora biti določeno!";
                break;
        }
        if(dodajNapaka==0)
        {
            dodajZacasnoTemeljnico2();
        }
        else
        {
            naslovNapake="Dodaj konto - napaka";
            kontniNacrt.prikazNapake(tekstNapake, naslovNapake);
        }        
    }
    
    public void dodajZacasnoTemeljnico2()
    {
        generirajStevilkoTemeljnice();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;       

        try {
            con = DriverManager.getConnection(dburl, dbuser, dbpassword);
            pst = con.prepareStatement("INSERT INTO zacasneTemeljnice"+"(stevilka, datum, mesec, leto, opomba, uporabnik, zaklenjen) VALUES"+"(?,?,?,?,?,?,?)");
            pst.setInt(1, stevilkaZacasneTemeljnice);
            pst.setLong(2, dodajDatumZacasneTemeljnice);
            pst.setInt(3, dodajTemeljnicaMesec);
            pst.setInt(4, dodajTemeljnicaLeto);
            pst.setString(5, dodajTemeljnicaOpomba);
            pst.setString(6, uporabnik);            
            zaklenjen=1;
            pst.setInt(7,zaklenjen);
            //osnovna.test.setText(String.valueOf(pst));
            String statement=String.valueOf(pst);
            pst.executeUpdate();
            pst = con.prepareStatement("INSERT INTO log"+"(tekst, uporabnik, podrocje, datum) VALUES"+"(?,?,?,?)");
            pst.setString(1, statement);
            pst.setString(2, Login.anUporabnik);
            String podrocje="zacasneTemeljnice";
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
        populateZacasnaTemeljnica();        
        new knjizenje().setVisible(true);
    }
    
    public void generirajStevilkoTemeljnice()
    {
        ugotoviMaxStevilkeTemeljnic();
        maxStevilkaTemeljnic=ugotoviMaxStevilkoTemeljnice(maxStevilkaZacasneTemeljnice, maxStevilkaTemeljnice, zacetnaStevilkaTemeljnice);
        stevilkaZacasneTemeljnice=maxStevilkaTemeljnic+1;
        //osnovna.test.setText(String.valueOf(maxStevilkaTemeljnic));
    }
    
    public void ugotoviMaxStevilkeTemeljnic()
    {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        PreparedStatement pst2 = null;
        ResultSet rs2 = null;
        PreparedStatement pst3 = null;
        ResultSet rs3 = null;

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
            pst = con.prepareStatement("SELECT stevilka FROM zacasneTemeljnice ORDER BY stevilka DESC LIMIT 1");          
            rs=pst.executeQuery();
            pst2 = con.prepareStatement("SELECT stevilkaTemeljnice FROM glavnaKnjiga ORDER BY stevilkaTemeljnice DESC LIMIT 1");          
            rs2=pst2.executeQuery();
            pst3 = con.prepareStatement("SELECT zacetnaStevilkaTemeljnice FROM nastavitve");          
            rs3=pst3.executeQuery();
            
            if (rs.next()) {           
               maxStevilkaZacasneTemeljnice=rs.getInt("stevilka");
               }
            else
            {
               maxStevilkaZacasneTemeljnice=0;
            }
            
            if (rs2.next()) {           
               maxStevilkaTemeljnice=rs2.getInt("stevilkaTemeljnice");
               }
            else
            {
               maxStevilkaTemeljnice=0;
            }
            
            if (rs3.next()) {           
               zacetnaStevilkaTemeljnice=rs3.getInt("zacetnaStevilkaTemeljnice");
               }
            else
            {
               zacetnaStevilkaTemeljnice=0;
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
                if (rs2 != null) {
                    rs2.close();
                }
                if (pst2 != null) {
                    pst2.close();
                }
                if (rs3 != null) {
                    rs3.close();
                }
                if (pst3 != null) {
                    pst3.close();
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
    
    public static int ugotoviMaxStevilkoTemeljnice(int prva, int druga, int tretja)
    {
        if (prva==druga)
        {
            druga=prva-1;
        }
        if(prva==tretja)
        {
            tretja=prva-1;
        }
        if(druga==tretja)
        {
            tretja=druga-1;
        }
        
        if (prva > druga && prva > tretja) {
            return prva;
        } else if (druga > prva && druga > tretja) {
            return druga;
        } else if (tretja > prva && tretja > druga) {
            return tretja;
        }
        return Integer.MIN_VALUE;
    }
    
    public void getValueOfSelectedRow()
    {
        int row = jTable1.getSelectedRow();
        value = jTable1.getValueAt(row, 0);
        String stevilkaZacasneTemeljniceTemp= String.valueOf(value);
        stevilkaZacasneTemeljnice=Integer.parseInt(stevilkaZacasneTemeljniceTemp);
    }
    
    public static String ugotoviUporabnikaTemeljnice()
    {
        Connection con = null;
            PreparedStatement pst = null;
            ResultSet rs = null;
        
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
            }
            catch (IOException e) 
            {
                e.printStackTrace();
            }

            String dburl = aryNastavitve[0];//spremenjivke iz tabele
            String dbuser = aryNastavitve[1];
            String dbpassword = aryNastavitve[2];
        
            try {
                con = DriverManager.getConnection(dburl, dbuser, dbpassword); 
                pst = con.prepareStatement("SELECT uporabnik FROM zacasneTemeljnice WHERE stevilka =?");          
                pst.setInt(1, stevilkaZacasneTemeljnice);
                rs=pst.executeQuery();            
            
                if (rs.next()) {           
                    uporabnikTemeljnice=rs.getString("uporabnik");
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
        return uporabnikTemeljnice;
    }
    
    public static int ugotoviZaklenjenaTemleljnica()
    {
            Connection con = null;
            PreparedStatement pst = null;
            ResultSet rs = null;
        
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
            }
            catch (IOException e) 
            {
                e.printStackTrace();
            }

            String dburl = aryNastavitve[0];//spremenjivke iz tabele
            String dbuser = aryNastavitve[1];
            String dbpassword = aryNastavitve[2];
        
            try {
                con = DriverManager.getConnection(dburl, dbuser, dbpassword); 
                pst = con.prepareStatement("SELECT zaklenjen FROM zacasneTemeljnice WHERE stevilka =?");          
                pst.setInt(1, stevilkaZacasneTemeljnice);
                rs=pst.executeQuery();            
            
                if (rs.next()) {           
                    zaklenjen=rs.getInt("zaklenjen");
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
            return zaklenjen;
    }
    
    public static void zakleniOdkleniTemeljnico(int stevilka, int zaklep)
    {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;       

        try {
            con = DriverManager.getConnection(dburl, dbuser, dbpassword);
            pst = con.prepareStatement("UPDATE zacasneTemeljnice SET zaklenjen=? WHERE stevilka=?");
            pst.setInt(1, zaklep);
            pst.setInt(2, stevilka);            
            //osnovna.test.setText(String.valueOf(pst));            
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
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;   //Disallow the editing of any cell
            }};
            jTabbedPane1 = new javax.swing.JTabbedPane();
            jPanel1 = new javax.swing.JPanel();
            jLabel1 = new javax.swing.JLabel();
            dodajTemeljnicaDatumFTF = new javax.swing.JFormattedTextField();
            jLabel2 = new javax.swing.JLabel();
            jLabel3 = new javax.swing.JLabel();
            dodajTemeljnicaMesecFTF = new javax.swing.JFormattedTextField();
            dodajTemeljnicaLetoFTF = new javax.swing.JFormattedTextField();
            jLabel4 = new javax.swing.JLabel();
            dodajTemeljnicaOpombaTXT = new javax.swing.JTextField();
            dodajTemeljnicaButton = new javax.swing.JButton();
            jLabel5 = new javax.swing.JLabel();
            zacasnaTemeljnicaOdpriButton = new javax.swing.JButton();
            zacasnaTemeljnicaIzbrisiButton = new javax.swing.JButton();

            setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

            populateZacasnaTemeljnica();
            jTable1.setModel(tm);
            jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    jTable1MouseClicked(evt);
                }
            });
            jScrollPane1.setViewportView(jTable1);

            jLabel1.setText("Datum:");

            dodajTemeljnicaDatumFTF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
            dodajTemeljnicaDatumFTF.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    dodajTemeljnicaDatumFTFKeyPressed(evt);
                }
            });

            jLabel2.setText("Mesec:");

            jLabel3.setText("Leto:");

            dodajTemeljnicaMesecFTF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
            dodajTemeljnicaMesecFTF.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    dodajTemeljnicaMesecFTFKeyPressed(evt);
                }
            });

            dodajTemeljnicaLetoFTF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
            dodajTemeljnicaLetoFTF.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    dodajTemeljnicaLetoFTFKeyPressed(evt);
                }
            });

            jLabel4.setText("Opomba:");

            dodajTemeljnicaOpombaTXT.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    dodajTemeljnicaOpombaTXTKeyPressed(evt);
                }
            });

            Connection con = null;
            PreparedStatement pst = null;
            ResultSet rsDovoljenja = null;

            String [] aryNastavitve;
            aryNastavitve=new String[3];
            i=0;

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

            dburl = aryNastavitve[0];//spremenjivke iz tabele
            dbuser = aryNastavitve[1];
            dbpassword = aryNastavitve[2];

            try {
                con = DriverManager.getConnection(dburl, dbuser, dbpassword);
                pst = con.prepareStatement("SELECT * FROM dovoljenja WHERE skupina=?");
                pst.setInt(1,Login.skupina);
                rsDovoljenja = pst.executeQuery();
                if (rsDovoljenja.next()) {
                    dovoljenjaVnosPrometa=rsDovoljenja.getInt("spremeniVnosPrometa");
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

            if (dovoljenjaVnosPrometa==0)
            {
                dodajTemeljnicaButton.setEnabled(false);
            }
            dodajTemeljnicaButton.setText("Dodaj temeljnico");
            dodajTemeljnicaButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    dodajTemeljnicaButtonActionPerformed(evt);
                }
            });
            dodajTemeljnicaButton.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    dodajTemeljnicaButtonKeyPressed(evt);
                }
            });

            jLabel5.setText("dd/mm/LLLL");

            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
            jPanel1.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel2)
                        .addComponent(jLabel1)
                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(dodajTemeljnicaMesecFTF, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                        .addComponent(dodajTemeljnicaDatumFTF)
                        .addComponent(dodajTemeljnicaLetoFTF))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(dodajTemeljnicaOpombaTXT))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addGap(0, 314, Short.MAX_VALUE)
                                    .addComponent(dodajTemeljnicaButton)))
                            .addContainerGap())
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addComponent(jLabel5)
                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            );
            jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(dodajTemeljnicaDatumFTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(dodajTemeljnicaMesecFTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel4)
                        .addComponent(dodajTemeljnicaOpombaTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(dodajTemeljnicaLetoFTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3))
                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                            .addComponent(dodajTemeljnicaButton)
                            .addContainerGap())))
            );

            jTabbedPane1.addTab("Dodaj Temeljnico", jPanel1);

            con = null;
            pst = null;
            rsDovoljenja = null;

            aryNastavitve=new String[3];
            i=0;

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

            dburl = aryNastavitve[0];//spremenjivke iz tabele
            dbuser = aryNastavitve[1];
            dbpassword = aryNastavitve[2];

            try {
                con = DriverManager.getConnection(dburl, dbuser, dbpassword);
                pst = con.prepareStatement("SELECT * FROM dovoljenja WHERE skupina=?");
                pst.setInt(1,Login.skupina);
                rsDovoljenja = pst.executeQuery();
                if (rsDovoljenja.next()) {
                    dovoljenjaVnosPrometa=rsDovoljenja.getInt("spremeniVnosPrometa");
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

            if (dovoljenjaVnosPrometa==0)
            {
                zacasnaTemeljnicaOdpriButton.setEnabled(false);
            }
            zacasnaTemeljnicaOdpriButton.setText("Odpri");
            zacasnaTemeljnicaOdpriButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    zacasnaTemeljnicaOdpriButtonActionPerformed(evt);
                }
            });

            con = null;
            pst = null;
            rsDovoljenja = null;

            aryNastavitve=new String[3];
            i=0;

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

            dburl = aryNastavitve[0];//spremenjivke iz tabele
            dbuser = aryNastavitve[1];
            dbpassword = aryNastavitve[2];

            try {
                con = DriverManager.getConnection(dburl, dbuser, dbpassword);
                pst = con.prepareStatement("SELECT * FROM dovoljenja WHERE skupina=?");
                pst.setInt(1,Login.skupina);
                rsDovoljenja = pst.executeQuery();
                if (rsDovoljenja.next()) {
                    dovoljenjaVnosPrometa=rsDovoljenja.getInt("spremeniVnosPrometa");
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

            if (dovoljenjaVnosPrometa==0)
            {
                zacasnaTemeljnicaIzbrisiButton.setEnabled(false);
            }
            zacasnaTemeljnicaIzbrisiButton.setText("Izbriši");
            zacasnaTemeljnicaIzbrisiButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    zacasnaTemeljnicaIzbrisiButtonActionPerformed(evt);
                }
            });

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1)
                        .addComponent(jTabbedPane1)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(zacasnaTemeljnicaOdpriButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(zacasnaTemeljnicaIzbrisiButton)
                            .addGap(0, 0, Short.MAX_VALUE)))
                    .addContainerGap())
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(4, 4, 4)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(zacasnaTemeljnicaOdpriButton)
                        .addComponent(zacasnaTemeljnicaIzbrisiButton))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents
            
    private void dodajTemeljnicaButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dodajTemeljnicaButtonKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {

        }
    }//GEN-LAST:event_dodajTemeljnicaButtonKeyPressed

    private void dodajTemeljnicaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dodajTemeljnicaButtonActionPerformed
        dodajTemeljnico();
    }//GEN-LAST:event_dodajTemeljnicaButtonActionPerformed

    private void dodajTemeljnicaOpombaTXTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dodajTemeljnicaOpombaTXTKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            dodajTemeljnicaButton.requestFocusInWindow();
        }
    }//GEN-LAST:event_dodajTemeljnicaOpombaTXTKeyPressed

    private void dodajTemeljnicaLetoFTFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dodajTemeljnicaLetoFTFKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            dodajTemeljnicaOpombaTXT.requestFocusInWindow();
        }
    }//GEN-LAST:event_dodajTemeljnicaLetoFTFKeyPressed

    private void dodajTemeljnicaMesecFTFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dodajTemeljnicaMesecFTFKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            dodajTemeljnicaLetoFTF.requestFocusInWindow();
        }
    }//GEN-LAST:event_dodajTemeljnicaMesecFTFKeyPressed

    private void dodajTemeljnicaDatumFTFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dodajTemeljnicaDatumFTFKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            dodajTemeljnicaMesecFTF.requestFocusInWindow();
        }
    }//GEN-LAST:event_dodajTemeljnicaDatumFTFKeyPressed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if (evt.getClickCount() == 2) {
            
            getValueOfSelectedRow();
            zaklenjen=ugotoviZaklenjenaTemleljnica();
            if (zaklenjen==0)
            {
                if (dovoljenOgledTujeTemeljnice==1)
                {
                   zaklep=1;
                    zakleniOdkleniTemeljnico(stevilkaZacasneTemeljnice, zaklep);
                    new knjizenje().setVisible(true); 
                }
                else
                {
                    uporabnikTemeljnice=ugotoviUporabnikaTemeljnice();
                    if(Login.uporabnik.equals(uporabnikTemeljnice))
                    {
                        zaklep=1;
                        zakleniOdkleniTemeljnico(stevilkaZacasneTemeljnice, zaklep);
                        new knjizenje().setVisible(true);
                    }
                    else
                    {
                        naslovNapake="Odpri temeljnico - napaka";
                        tekstNapake="Nimate dovoljenja za ogled te temeljnice";
                        kontniNacrt.prikazNapake(tekstNapake, naslovNapake);
                    }
                }                
            }
            else
            {
                naslovNapake="Odpri temeljnico - napaka";
                tekstNapake="Temeljnica je že odprta";
                kontniNacrt.prikazNapake(tekstNapake, naslovNapake);
            }
                       
            //osnovna.test.setText(String.valueOf(stevilkaZacasneTemeljnice));            
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void zacasnaTemeljnicaOdpriButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zacasnaTemeljnicaOdpriButtonActionPerformed
        getValueOfSelectedRow();
            zaklenjen=ugotoviZaklenjenaTemleljnica();
            if (zaklenjen==0)
            {
                if (dovoljenOgledTujeTemeljnice==1)
                {
                   zaklep=1;
                    zakleniOdkleniTemeljnico(stevilkaZacasneTemeljnice, zaklep);
                    new knjizenje().setVisible(true); 
                }
                else
                {
                    uporabnikTemeljnice=ugotoviUporabnikaTemeljnice();
                    if(Login.uporabnik.equals(uporabnikTemeljnice))
                    {
                        zaklep=1;
                        zakleniOdkleniTemeljnico(stevilkaZacasneTemeljnice, zaklep);
                        new knjizenje().setVisible(true);
                    }
                    else
                    {
                        naslovNapake="Odpri temeljnico - napaka";
                        tekstNapake="Nimate dovoljenja za ogled te temeljnice";
                        kontniNacrt.prikazNapake(tekstNapake, naslovNapake);
                    }
                }                
            }
            else
            {
                naslovNapake="Odpri temeljnico - napaka";
                tekstNapake="Temeljnica je že odprta";
                kontniNacrt.prikazNapake(tekstNapake, naslovNapake);
            }
    }//GEN-LAST:event_zacasnaTemeljnicaOdpriButtonActionPerformed

    private void zacasnaTemeljnicaIzbrisiButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zacasnaTemeljnicaIzbrisiButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_zacasnaTemeljnicaIzbrisiButtonActionPerformed

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
            java.util.logging.Logger.getLogger(vnosPrometa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vnosPrometa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vnosPrometa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vnosPrometa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new vnosPrometa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton dodajTemeljnicaButton;
    private javax.swing.JFormattedTextField dodajTemeljnicaDatumFTF;
    private javax.swing.JFormattedTextField dodajTemeljnicaLetoFTF;
    private javax.swing.JFormattedTextField dodajTemeljnicaMesecFTF;
    private javax.swing.JTextField dodajTemeljnicaOpombaTXT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton zacasnaTemeljnicaIzbrisiButton;
    private javax.swing.JButton zacasnaTemeljnicaOdpriButton;
    // End of variables declaration//GEN-END:variables
}
