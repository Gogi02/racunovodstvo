/*
 * To change this template, choose Tools | Templates
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
import java.sql.SQLException;

/**
 *
 * @author gogi
 */
public class osnovna extends javax.swing.JFrame {    
int dovoljenjaKontniNacrt=0;
int dovoljenjaPartnerji=0;
int dovoljenjaNastavitve=0;
int dovoljenjaDrzave=0;
int dovoljenjaValute=0;
    /**
     * Creates new form osnovna
     */
    public osnovna() {
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        
        
        
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

        test = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        program = new javax.swing.JMenu();
        odjava = new javax.swing.JMenuItem();
        izhod = new javax.swing.JMenuItem();
        sifranti = new javax.swing.JMenu();
        kontniNacrt = new javax.swing.JMenuItem();
        partnerji = new javax.swing.JMenuItem();
        drzave = new javax.swing.JMenuItem();
        valute = new javax.swing.JMenuItem();
        orodja = new javax.swing.JMenu();
        nastavitve = new javax.swing.JMenuItem();
        knjizenje = new javax.swing.JMenu();
        vnosPrometa = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Računovodstvo");

        test.setText("jLabel1");

        program.setText("Program");

        odjava.setText("Odjava");
        odjava.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                odjavaActionPerformed(evt);
            }
        });
        program.add(odjava);

        izhod.setText("Izhod");
        izhod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                izhodActionPerformed(evt);
            }
        });
        program.add(izhod);

        jMenuBar1.add(program);

        sifranti.setText("Šifranti");

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
                dovoljenjaKontniNacrt=rsDovoljenja.getInt("kontniNacrt");
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
        if (dovoljenjaKontniNacrt==0)
        {
            kontniNacrt.setEnabled(false);
        }
        kontniNacrt.setText("Kontni načrt");
        kontniNacrt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kontniNacrtActionPerformed(evt);
            }
        });
        sifranti.add(kontniNacrt);

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
                dovoljenjaPartnerji=rsDovoljenja.getInt("partnerji");
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

        if (dovoljenjaPartnerji==0)
        {
            partnerji.setEnabled(false);
        }
        partnerji.setText("Partnerji");
        partnerji.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                partnerjiActionPerformed(evt);
            }
        });
        sifranti.add(partnerji);

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
                dovoljenjaDrzave=rsDovoljenja.getInt("drzave");
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

        if (dovoljenjaDrzave==0)
        {
            drzave.setEnabled(false);
        }
        drzave.setText("Države");
        drzave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                drzaveActionPerformed(evt);
            }
        });
        sifranti.add(drzave);

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
                dovoljenjaValute=rsDovoljenja.getInt("valute");
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

        if (dovoljenjaValute==0)
        {
            valute.setEnabled(false);
        }
        valute.setText("Valute");
        valute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valuteActionPerformed(evt);
            }
        });
        sifranti.add(valute);

        jMenuBar1.add(sifranti);

        orodja.setText("Orodja");

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
                dovoljenjaNastavitve=rsDovoljenja.getInt("nastavitve");
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

        if (dovoljenjaNastavitve==0)
        {
            nastavitve.setEnabled(false);
        }
        nastavitve.setText("Nastavitve");
        orodja.add(nastavitve);

        jMenuBar1.add(orodja);

        knjizenje.setText("Knjiženje");

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
                dovoljenjaPartnerji=rsDovoljenja.getInt("vnosPrometa");
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

        if (dovoljenjaPartnerji==0)
        {
            vnosPrometa.setEnabled(false);
        }
        vnosPrometa.setText("Vnos prometa");
        vnosPrometa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vnosPrometaActionPerformed(evt);
            }
        });
        knjizenje.add(vnosPrometa);

        jMenuBar1.add(knjizenje);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(test, javax.swing.GroupLayout.DEFAULT_SIZE, 651, Short.MAX_VALUE)
                .addGap(63, 63, 63))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(250, Short.MAX_VALUE)
                .addComponent(test)
                .addGap(182, 182, 182))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void kontniNacrtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kontniNacrtActionPerformed
           new kontniNacrt().setVisible(true);           
    }//GEN-LAST:event_kontniNacrtActionPerformed

    private void partnerjiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_partnerjiActionPerformed
        new partnerji().setVisible(true);
    }//GEN-LAST:event_partnerjiActionPerformed

    private void izhodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_izhodActionPerformed
        System.exit(0);
    }//GEN-LAST:event_izhodActionPerformed

    private void odjavaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_odjavaActionPerformed
       new Login().setVisible(true);
       dispose();
    }//GEN-LAST:event_odjavaActionPerformed

    private void drzaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_drzaveActionPerformed
        new drzave().setVisible(true);
    }//GEN-LAST:event_drzaveActionPerformed

    private void valuteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valuteActionPerformed
        new valute().setVisible(true);
    }//GEN-LAST:event_valuteActionPerformed

    private void vnosPrometaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vnosPrometaActionPerformed
        new vnosPrometa().setVisible(true);
    }//GEN-LAST:event_vnosPrometaActionPerformed

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
            java.util.logging.Logger.getLogger(osnovna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(osnovna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(osnovna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(osnovna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                new osnovna().setVisible(true);                
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem drzave;
    private javax.swing.JMenuItem izhod;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu knjizenje;
    private javax.swing.JMenuItem kontniNacrt;
    private javax.swing.JMenuItem nastavitve;
    private javax.swing.JMenuItem odjava;
    private javax.swing.JMenu orodja;
    private javax.swing.JMenuItem partnerji;
    private javax.swing.JMenu program;
    private javax.swing.JMenu sifranti;
    public static javax.swing.JLabel test;
    private javax.swing.JMenuItem valute;
    private javax.swing.JMenuItem vnosPrometa;
    // End of variables declaration//GEN-END:variables
}
