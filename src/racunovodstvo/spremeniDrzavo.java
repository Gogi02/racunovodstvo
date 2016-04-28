/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racunovodstvo;

import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import static racunovodstvo.Login.geslo1;
import static racunovodstvo.Login.anUporabnik;
import static racunovodstvo.Login.dbpassword;
import static racunovodstvo.Login.dburl;
import static racunovodstvo.Login.dbuser;
import static racunovodstvo.Login.skupina;

/**
 *
 * @author gogi
 */
public class spremeniDrzavo extends javax.swing.JFrame {
    
    String nSifraDrzave, oznakaAN, oznakaAN2, opisAN, opisAN2, opis, sifraDrzave2, oznaka;
    int zapst, napakaDrzava;


    private void spremeniDrzavo3(){
        String sifraDrzave=sifraTxt.getText();
        nSifraDrzave=sifraDrzave.replaceAll("[^0-9]", "");      
        oznaka=oznakaTxt.getText();
        oznakaAN=oznaka.replaceAll("[^a-zA-Z0-9]", "");
        opis=opisTxt.getText();
        opisAN=opis.replaceAll("[^a-zA-Z0-9]", "");        
                            
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(dburl, dbuser, dbpassword);
            pst = con.prepareStatement("UPDATE drzave SET sifraDrzave=?, kratica=?, opis=? WHERE zapst=?");
            pst.setString(1, nSifraDrzave);
            pst.setString(2, oznakaAN);
            pst.setString(3, opisAN);            
            String statement=String.valueOf(pst);
            pst.executeUpdate();
            pst = con.prepareStatement("INSERT INTO log"+"(tekst, uporabnik, podrocje, datum) VALUES"+"(?,?,?,?)");
            pst.setString(1, statement);
            pst.setString(2, Login.anUporabnik);
            String podrocje="drzave";
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
    }
    
    private void spremeniDrzavo2(){
       String sDrzava=Login.staraDrzava;
       osnovna.test.setText(Login.staraDrzava);
       
       
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        //geslo1=null;                        

            try {
            con = DriverManager.getConnection(dburl, dbuser, dbpassword); //populate with old values
            pst = con.prepareStatement("SELECT * FROM drzave WHERE sifraDrzave=?");          
            pst.setString(1, sDrzava);                        
            rs=pst.executeQuery();
            if (rs.next()) {               
               opis= rs.getString("opis");
               sifraDrzave2= rs.getString("sifraDrzave");
               oznaka=rs.getString("kratica");
               zapst=rs.getInt("zapst");
            }
            
            sifraTxt.setText(sifraDrzave2);
            opisTxt.setText(opis);
            oznakaTxt.setText(oznaka);
            
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
    
    public spremeniDrzavo() {
        
        Set forwardKeys = getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS);
        Set newForwardKeys = new HashSet(forwardKeys);
        newForwardKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
        setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, newForwardKeys);
        
        initComponents();
        spremeniDrzavo2();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sifraTxt = new javax.swing.JTextField();
        oznakaTxt = new javax.swing.JTextField();
        opisTxt = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        potrdiButt = new javax.swing.JButton();
        prekliciButt = new javax.swing.JButton();
        sifraLabel = new javax.swing.JLabel();
        oznakaLabel = new javax.swing.JLabel();
        opisLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Dodaj državo");

        sifraTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                sifraTxtFocusLost(evt);
            }
        });
        sifraTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sifraTxtActionPerformed(evt);
            }
        });

        oznakaTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                oznakaTxtFocusLost(evt);
            }
        });

        opisTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                opisTxtFocusLost(evt);
            }
        });

        jLabel1.setText("Šifra države:");

        jLabel2.setText("Oznaka države:");

        jLabel3.setText("Opis države:");

        potrdiButt.setText("Potrdi");
        potrdiButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                potrdiButtActionPerformed(evt);
            }
        });

        prekliciButt.setText("Prekliči");

        oznakaLabel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                oznakaLabelFocusLost(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(232, Short.MAX_VALUE)
                .addComponent(prekliciButt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(potrdiButt, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(opisLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(oznakaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(oznakaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(sifraTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(sifraLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(opisTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sifraLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(sifraTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(oznakaTxt)
                    .addComponent(jLabel2)
                    .addComponent(oznakaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(opisTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(opisLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prekliciButt)
                    .addComponent(potrdiButt))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sifraTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sifraTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sifraTxtActionPerformed

    private void potrdiButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_potrdiButtActionPerformed
        String sifraDrzave=sifraTxt.getText();
        nSifraDrzave=sifraDrzave.replaceAll("[^0-9]", "");
        oznaka=oznakaTxt.getText();
        oznakaAN2=oznaka.replaceAll("[^a-zA-Z0-9]", "");
        opis=opisTxt.getText();
        opisAN2=opis.replaceAll("[^a-zA-Z0-9]", "");
        
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        if(!nSifraDrzave.equals(sifraDrzave2)){
            try {
            con = DriverManager.getConnection(dburl, dbuser, dbpassword);
            pst = con.prepareStatement("SELECT sifraDrzave FROM drzave WHERE sifraDrzave=?");
            pst.setString(1, nSifraDrzave);
            rs = pst.executeQuery();

            if (rs.next()) {           
               String kontodb= rs.getString("sifraDrzave");
               napakaDrzava=1;
            }            

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
        
        if(!oznakaAN2.equals(oznakaAN)){
            try {
            con = DriverManager.getConnection(dburl, dbuser, dbpassword);
            pst = con.prepareStatement("SELECT kratica FROM drzave WHERE kratica=?");
            pst.setString(1, oznakaAN2);
            rs = pst.executeQuery();

            if (rs.next()) {           
               String kontodb= rs.getString("sifraDrzave");
               napakaDrzava=1;
            }            

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
        
        if(!opisAN.equals(opisAN2)){
            try {
            con = DriverManager.getConnection(dburl, dbuser, dbpassword);
            pst = con.prepareStatement("SELECT opis FROM drzave WHERE sifraDrzave=?");
            pst.setString(1, nSifraDrzave);
            rs = pst.executeQuery();

            if (rs.next()) {           
               String kontodb= rs.getString("sifraDrzave");
               napakaDrzava=1;
            }            

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

    }//GEN-LAST:event_potrdiButtActionPerformed

    private void sifraTxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sifraTxtFocusLost
        String sifraDrzave=sifraTxt.getText();
        String nSifraDrzave=sifraDrzave.replaceAll("[^0-9]", "");
        //int sifra=Integer.parseInt(nSifraDrzave);
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
            try {
            con = DriverManager.getConnection(dburl, dbuser, dbpassword);
            pst = con.prepareStatement("SELECT sifraDrzave FROM drzave WHERE sifraDrzave=?");
            pst.setString(1, nSifraDrzave);
            rs = pst.executeQuery();

            if (rs.next()) {           
               String kontodb= rs.getString("sifraDrzave");
               sifraLabel.setText("Šifra že obstaja");
            }
            

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
    }//GEN-LAST:event_sifraTxtFocusLost

    private void oznakaLabelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_oznakaLabelFocusLost

    }//GEN-LAST:event_oznakaLabelFocusLost

    private void oznakaTxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_oznakaTxtFocusLost
        String oznakaDrzave=oznakaTxt.getText();
        String nOznakaDrzave=oznakaDrzave.replaceAll("[^0-9]", "");
        //int sifra=Integer.parseInt(nSifraDrzave);
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
                        try {
            con = DriverManager.getConnection(dburl, dbuser, dbpassword);
            pst = con.prepareStatement("SELECT kratica FROM drzave WHERE kratica=?");
            pst.setString(1, nOznakaDrzave);
            rs = pst.executeQuery();

            if (rs.next()) {           
               String kontodb= rs.getString("sifraDrzave");
               sifraLabel.setText("Oznaka že obstaja");
            }
            

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
    }//GEN-LAST:event_oznakaTxtFocusLost

    private void opisTxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_opisTxtFocusLost
        String opisDrzave=opisTxt.getText();
        String nOpisDrzave=opisDrzave.replaceAll("[^0-9]", "");
        //int sifra=Integer.parseInt(nSifraDrzave);
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
                        try {
            con = DriverManager.getConnection(dburl, dbuser, dbpassword);
            pst = con.prepareStatement("SELECT opis FROM drzave WHERE opis=?");
            pst.setString(1, nOpisDrzave);
            rs = pst.executeQuery();

            if (rs.next()) {           
               String kontodb= rs.getString("opisDrzave");
               sifraLabel.setText("Opis že obstaja");
            }
            

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
    }//GEN-LAST:event_opisTxtFocusLost

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
            java.util.logging.Logger.getLogger(spremeniDrzavo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(spremeniDrzavo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(spremeniDrzavo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(spremeniDrzavo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new spremeniDrzavo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel opisLabel;
    private javax.swing.JTextField opisTxt;
    private javax.swing.JLabel oznakaLabel;
    private javax.swing.JTextField oznakaTxt;
    private javax.swing.JButton potrdiButt;
    private javax.swing.JButton prekliciButt;
    private javax.swing.JLabel sifraLabel;
    private javax.swing.JTextField sifraTxt;
    // End of variables declaration//GEN-END:variables
}
