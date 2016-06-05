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
import java.util.logging.Level;
import java.util.logging.Logger;
import static racunovodstvo.Login.anUporabnik;
import static racunovodstvo.Login.dbpassword;
import static racunovodstvo.Login.dburl;
import static racunovodstvo.Login.dbuser;

import static racunovodstvo.Login.value;
import static racunovodstvo.Login.stariKonto;

public class kontniNacrt extends javax.swing.JFrame {
    DefaultTableModel tm;
    int dovoljenjaDodajKonto, dovoljenjaSpremeniKonto;
    

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
            pst5 = con.prepareStatement("SELECT spremeniKonto FROM dovoljenja WHERE skupina=?");
            pst5.setInt(1, Login.skupina);
            rs5 = pst5.executeQuery();
            
            if (rs5.next()) {           
              dovoljenjaSpremeniKonto=rs5.getInt("spremeniKonto");
              osnovna.test.setText(String.valueOf(dovoljenjaSpremeniKonto));
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Kontni načrt");
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(dodajKonto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(osvezi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(natisni)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 836, Short.MAX_VALUE))
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
                .addContainerGap())
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
            }
        }     
    }//GEN-LAST:event_jTable1MouseClicked

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained

    }//GEN-LAST:event_formFocusGained

    private void osveziActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_osveziActionPerformed
        populateKN();
    }//GEN-LAST:event_osveziActionPerformed

    public void getValueOfSelectedRow(){
        int row = jTable1.getSelectedRow();
        value=jTable1.getValueAt(row, 0);
        Login.stariKonto=String.valueOf(value);
        //osnovna.test.setText(Login.stariKonto);
        
        new spremeniKonto().setVisible(true);
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
    private javax.swing.JButton dodajKonto;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jTable1;
    private javax.swing.JButton natisni;
    private javax.swing.JButton osvezi;
    // End of variables declaration//GEN-END:variables
}
