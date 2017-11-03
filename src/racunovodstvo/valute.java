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
import javax.swing.JTable;
import static racunovodstvo.Login.dbpassword;
import static racunovodstvo.Login.dburl;
import static racunovodstvo.Login.dbuser;

//import static racunovodstvo.Login.value;

/**
 *
 * @author gogi
 */
public class valute extends javax.swing.JFrame {

    DefaultTableModel tm;
    int dovoljenjaDodajValuto, dovoljenjaSpremeniValuto;
    public static Object value;
    public static String staraValuta=null;
    String opisValute, oznakaValute, opisValuteAN, oznakaValuteAN, dodajOznakaValuteAN, dodajSifraValuteAN, dodajOpisValuteAN,sifraValuteAN, tekstNapake, naslovNapake, sifraValute;
    int zapst, napaka;
    /**
     * Creates new form drzave
     */
    public valute() {
        initComponents();
    }
    
        public void populateValuta(){
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;        
        ResultSet rs2 = null;        
        PreparedStatement pst2 = null;
        
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
            pst = con.prepareStatement("SELECT sifraValute, kratica, opis FROM valute");            
            rs = pst.executeQuery();
            pst2 = con.prepareStatement("SELECT spremeniValuto, dodajValuto FROM dovoljenja WHERE skupina=?");
            pst2.setInt(1, Login.skupina);
            rs2 = pst2.executeQuery();
            
            if (rs2.next()) {           
              dovoljenjaSpremeniValuto=rs2.getInt("spremeniValuto");
              dovoljenjaDodajValuto=rs2.getInt("dodajValuto");
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
            for (i = 1; i <= columnCount; i++ ) {
            tm.addColumn(rsmd.getColumnName(i));
            }   

        // clear existing rows
        tm.setRowCount(0);

        // add rows to table
        while (rs.next()) {
            //parrent = rs.getString("konto");
            //osnovna.test.setText(String.valueOf(parrent));
            String[] a = new String[columnCount];
            for(i = 0; i < columnCount; i++) {
                a[i] = rs.getString(i+1);
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

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;   //Disallow the editing of any cell
            }
        };
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        dodajSifraValuteTxt = new javax.swing.JTextField();
        dodajOznakaValuteTxt = new javax.swing.JTextField();
        dodajOpisValuteTxt = new javax.swing.JTextField();
        dodajValutoButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        spremeniSifraValuteTxt = new javax.swing.JTextField();
        spremeniOznakaValuteTxt = new javax.swing.JTextField();
        spremeniOpisValuteTxt = new javax.swing.JTextField();
        spremeniValutoButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        populateValuta();
        jTable2.setModel(tm);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jLabel4.setText("Šifra valute:");

        jLabel5.setText("Oznaka valute:");

        jLabel6.setText("Opis valute:");

        dodajOpisValuteTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dodajOpisValuteTxtActionPerformed(evt);
            }
        });

        dodajValutoButton.setText("Dodaj valuto");
        dodajValutoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dodajValutoButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dodajSifraValuteTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                            .addComponent(dodajOznakaValuteTxt))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
                        .addComponent(dodajValutoButton))
                    .addComponent(dodajOpisValuteTxt))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(dodajSifraValuteTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dodajValutoButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(dodajOznakaValuteTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(dodajOpisValuteTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Dodaj valuto", jPanel1);

        jLabel1.setText("Šifra valute:");

        jLabel2.setText("Oznaka valute:");

        jLabel3.setText("Opis valute:");

        spremeniSifraValuteTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spremeniSifraValuteTxtActionPerformed(evt);
            }
        });

        spremeniValutoButton.setText("Spremeni valuto");
        spremeniValutoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spremeniValutoButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(spremeniOznakaValuteTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                            .addComponent(spremeniSifraValuteTxt))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                        .addComponent(spremeniValutoButton))
                    .addComponent(spremeniOpisValuteTxt))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(spremeniSifraValuteTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spremeniValutoButton))
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(spremeniOznakaValuteTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(spremeniOpisValuteTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 19, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Spremeni valuto", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        if (evt.getClickCount() == 2){
            if(dovoljenjaSpremeniValuto==1)
            getValueOfSelectedRow();
            jTabbedPane1.setSelectedIndex(1);
            if(staraValuta!=null)
                {
                    populateSD();
                }
        }
    }//GEN-LAST:event_jTable2MouseClicked

    private void spremeniSifraValuteTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spremeniSifraValuteTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_spremeniSifraValuteTxtActionPerformed

    private void spremeniValutoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spremeniValutoButtonActionPerformed
        if (dovoljenjaSpremeniValuto==1)
        {
            napaka=0;
            preberiSpremenljivke();
            preveriSpremenljivke();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Nimate dovoljenja za spremembo valute","Spremeni valuto - napaka",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_spremeniValutoButtonActionPerformed

    private void dodajOpisValuteTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dodajOpisValuteTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dodajOpisValuteTxtActionPerformed

    private void dodajValutoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dodajValutoButtonActionPerformed
        if (dovoljenjaDodajValuto==1)
        {
            napaka=0;
            preberiDodajSpremenljivke();
            //osnovna.test.setText(sifraValuteAN);
            preveriDodajSpremenljivke();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Nimate dovoljenja za dodajanje valute","Dodaj valuto - napaka",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_dodajValutoButtonActionPerformed

    public void preberiDodajSpremenljivke()
    {
       opisValute=dodajOpisValuteTxt.getText();
       opisValuteAN=opisValute.replaceAll("[^\\p{L}]]", "");       
       oznakaValute=dodajOznakaValuteTxt.getText();
       dodajOznakaValuteAN=oznakaValute.replaceAll("[^\\p{L}]", "");       
       sifraValute=dodajSifraValuteTxt.getText();
       sifraValuteAN=sifraValute.replaceAll("[^a-zA-Z0-9]", "");
    }
    
    public void preveriDodajSpremenljivke()
    {
        //napaka=0;
        
        if(sifraValuteAN!=null&&!sifraValuteAN.isEmpty()) //preveri, če je vrednost šifre države prazna
        {
            
        }
        else
        {
            napaka=1;            
        }               
        
            Connection con = null; 
            PreparedStatement pst = null;
            ResultSet rs = null;
            
            try {
            con = DriverManager.getConnection(dburl, dbuser, dbpassword); 
            pst = con.prepareStatement("SELECT * FROM valute WHERE sifraValute=?");          
            pst.setString(1, String.valueOf(sifraValute));                        
            rs=pst.executeQuery();
            if (rs.next()) {           
               napaka=2;
               zapst=rs.getInt("zapst");
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
        
        
        if(opisValuteAN!=null&&!opisValuteAN.isEmpty()) //preveri, če je vrednost opisa prazna
        {
            
        }
        else
        {
            napaka=3;            
        }
        
        if(dodajOznakaValuteAN!=null&&!dodajOznakaValuteAN.isEmpty()) //preveri, če je vrednost gornje skupine prazna
        {
            
        }
        else
        {
            napaka=4;            
        } 
        
        //osnovna.test.setText(String.valueOf(napaka));
        
        switch (napaka)
        {
            case 1:
                tekstNapake="Šifra valute ne sme biti prazna!";
                break;
            case 2:
                tekstNapake="Valuta že obstaja!";
                break;
            case 3:
                tekstNapake="Vrednost opisa ne sme biti prazna!";
                break;
            case 4:
                tekstNapake="Oznaka valute ne sme biti prazna!";
                break;
        }        
        if(napaka==0)
        {
            dodajOpisValuteAN=opisValuteAN;
            dodajSifraValuteAN=sifraValuteAN;
            dodajD();
        }
        else
        {
            naslovNapake="Spremeni valuto - napaka";
            kontniNacrt prikazNapake=new kontniNacrt();
            prikazNapake.prikazNapake(tekstNapake, naslovNapake);
        }
    }
    
    public void dodajD()
    {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        
        preberiSpremenljivke();
        
        try {
            con = DriverManager.getConnection(dburl, dbuser, dbpassword);
            pstm = con.prepareStatement("INSERT INTO valute"+"(kratica, sifraValute, opis) VALUES"+"(?,?,?)");
            pstm.setString(1, dodajOznakaValuteAN);
            pstm.setString(2, dodajSifraValuteAN);
            pstm.setString(3, dodajOpisValuteAN);
                        
            osnovna.test.setText(String.valueOf(pstm));
            String statement=String.valueOf(pstm);
            pstm.executeUpdate();
            pstm = con.prepareStatement("INSERT INTO log"+"(tekst, uporabnik, podrocje, datum) VALUES"+"(?,?,?,?)");
            pstm.setString(1, statement);
            pstm.setString(2, Login.anUporabnik);
            String podrocje="valute";
            pstm.setString(3, podrocje);
            long zdaj = Instant.now().getEpochSecond();
            pstm.setLong(4,zdaj);
            pstm.executeUpdate();

        } catch (SQLException ex) {
           // Logger lgr = Logger.getLogger(Version.class.getName());
            //lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                //Logger lgr = Logger.getLogger(Version.class.getName());
                //lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        populateValuta();
    }
    
    public void preberiSpremenljivke()
    {
       opisValute=spremeniOpisValuteTxt.getText();
       opisValuteAN=opisValute.replaceAll("[^\\p{L}]]", "");       
       oznakaValute=spremeniOznakaValuteTxt.getText();
       oznakaValuteAN=oznakaValute.replaceAll("[^\\p{L}]", "");
       //osnovna.test.setText(opisValuteAN);
       sifraValute=spremeniSifraValuteTxt.getText();
       sifraValuteAN=sifraValute.replaceAll("[^a-zA-Z0-9]", "");
              
    }
    
    public void preveriSpremenljivke()
    {
        napaka=0;
        
        if(sifraValuteAN!=null&&!sifraValuteAN.isEmpty()) //preveri, če je vrednost šifre države prazna
        {
            if(!staraValuta.equals(sifraValuteAN)) //preveri, če je nova država enaka stari
            {
                Connection con = null; //če nova država ni enaka staremu, preveri, če že obstaja
                PreparedStatement pst = null;
                ResultSet rs = null;
            
                try {
                con = DriverManager.getConnection(dburl, dbuser, dbpassword); 
                pst = con.prepareStatement("SELECT * FROM valute WHERE sifraValute=?");          
                pst.setString(1, sifraValuteAN);                        
                rs=pst.executeQuery();
                if (rs.next()) {           
                napaka=2;
                zapst=rs.getInt("zapst");
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
        }
        else
        {
            napaka=1;            
        }       
        
        
        
        if(opisValuteAN!=null&&!opisValuteAN.isEmpty()) //preveri, če je vrednost opisa prazna
        {
            
        }
        else
        {
            napaka=3;            
        }
        
        if(oznakaValuteAN!=null&&!oznakaValuteAN.isEmpty()) //preveri, če je vrednost gornje skupine prazna
        {
            
        }
        else
        {
            napaka=4;            
        } 
        
        //osnovna.test.setText(String.valueOf(napaka));
        
        switch (napaka)
        {
            case 1:
                tekstNapake="Šifra valute ne sme biti prazna!";
                break;
            case 2:
                tekstNapake="Valuta že obstaja!";
                break;
            case 3:
                tekstNapake="Vrednost opisa ne sme biti prazna!";
                break;
            case 4:
                tekstNapake="Oznaka valute ne sme biti prazna!";
                break;
            case 5:
                tekstNapake="Šifra valute ne sme biti negativna";
                break;
        }        
        if(napaka==0)
        {
            updateSD();
        }
        else
        {
            naslovNapake="Spremeni valuto - napaka";
            kontniNacrt prikazNapake=new kontniNacrt();
            prikazNapake.prikazNapake(tekstNapake, naslovNapake);
        }
    }
    
    public void updateSD()
    {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        preberiSpremenljivke();
        
        try {
            con = DriverManager.getConnection(dburl, dbuser, dbpassword);
            pst = con.prepareStatement("UPDATE valute SET kratica=?, opis=?, sifraValute=? WHERE zapst=?");
            pst.setString(1, oznakaValuteAN);
            pst.setString(2, opisValuteAN);
            pst.setString(3, sifraValuteAN);            
            pst.setInt(4, zapst);
            //osnovna.test.setText(String.valueOf(pst));
            String statement=String.valueOf(pst);
            pst.executeUpdate();
            pst = con.prepareStatement("INSERT INTO log"+"(tekst, uporabnik, podrocje, datum) VALUES"+"(?,?,?,?)");
            pst.setString(1, statement);
            pst.setString(2, Login.anUporabnik);
            String podrocje="valute";
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
        populateValuta();
        
    }
    
    public void populateSD()
    {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        //geslo1=null;                        

        try {
            con = DriverManager.getConnection(dburl, dbuser, dbpassword); //populate with old values
            pst = con.prepareStatement("SELECT * FROM valute WHERE sifraValute=?");          
            pst.setString(1, String.valueOf(staraValuta));                        
            rs=pst.executeQuery();
            if (rs.next()) {           
               opisValute= rs.getString("opis"); 
               zapst= rs.getInt("zapst");
               oznakaValute= rs.getString("kratica");
               sifraValute=rs.getString("sifraValute"); 
              
            }
            
            spremeniOpisValuteTxt.setText(opisValute);
            spremeniOznakaValuteTxt.setText(oznakaValute);
            spremeniSifraValuteTxt.setText(String.valueOf(sifraValute));         
            
            
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
    
    public void getValueOfSelectedRow()
    {
        int row = jTable2.getSelectedRow();
        value=jTable2.getValueAt(row, 0);
        staraValuta=String.valueOf(value);
        //osnovna.test.setText(Login.stariKonto);
        //new spremeniValuto().setVisible(true);
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
            java.util.logging.Logger.getLogger(valute.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(valute.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(valute.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(valute.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new valute().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField dodajOpisValuteTxt;
    private javax.swing.JTextField dodajOznakaValuteTxt;
    private javax.swing.JTextField dodajSifraValuteTxt;
    private javax.swing.JButton dodajValutoButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField spremeniOpisValuteTxt;
    private javax.swing.JTextField spremeniOznakaValuteTxt;
    private javax.swing.JTextField spremeniSifraValuteTxt;
    private javax.swing.JButton spremeniValutoButton;
    // End of variables declaration//GEN-END:variables
}
