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
import java.sql.SQLException;
import java.time.Instant;
import static racunovodstvo.Login.anUporabnik;
import static racunovodstvo.Login.geslo;
import static racunovodstvo.Login.geslo1;
import static racunovodstvo.Login.skupina;
import static racunovodstvo.Login.uporabnik;


import java.awt.AWTKeyStroke;
import java.awt.KeyboardFocusManager;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import static racunovodstvo.Login.anUporabnik;
import static racunovodstvo.Login.dbpassword;
import static racunovodstvo.Login.dburl;
import static racunovodstvo.Login.dbuser;
import static racunovodstvo.Login.geslo1;
import static racunovodstvo.Login.skupina;

import javax.swing.JOptionPane;


/**
 *
 * @author gogi
 */
public class dodajKonto extends javax.swing.JFrame {
    
    

    private void dodajKonto2()
    {
       int debet, kredit, stm, partner;
       String konto=kontoTxt.getText();
       String nKonto=konto.replaceAll("[^0-9]", "");       
       String opis=opisTxt.getText();
       String parrent=parrentTxt.getText();
       String nParrent=parrent.replaceAll("[^0-9]", "");
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
                    
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        geslo1=null;        

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
            pst = con.prepareStatement("INSERT INTO kontniNacrt"+"(konto, opis, debet, kredit, parrent, stm, partner) VALUES"+"(?,?,?,?,?,?,?)");
            pst.setString(1, nKonto);
            pst.setString(2, opis);
            pst.setInt(3, debet);
            pst.setInt(4, kredit);
            pst.setString(5, nParrent);
            pst.setInt(6, stm);
            pst.setInt(7,partner);
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
    }
    
    public dodajKonto() {
        
        Set forwardKeys = getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS);
        Set newForwardKeys = new HashSet(forwardKeys);
        newForwardKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
        setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, newForwardKeys);
        
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

        kontoTxt = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        opisTxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        debetCheck = new javax.swing.JCheckBox();
        kreditCheck = new javax.swing.JCheckBox();
        stmCheck = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        parrentTxt = new javax.swing.JTextField();
        preklici = new javax.swing.JButton();
        potrdi = new javax.swing.JButton();
        kontoLabel = new javax.swing.JLabel();
        partnerCheck = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Dodaj konto");

        kontoTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                kontoTxtFocusLost(evt);
            }
        });

        jLabel1.setText("Konto");

        jLabel2.setText("Opis");

        jLabel3.setText("Dovoljena knjižba");

        debetCheck.setText("Debet");

        kreditCheck.setText("Kredit");

        stmCheck.setText("Obvezno stroškovno mesto");

        jLabel5.setText("Gornja skupina");

        parrentTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parrentTxtActionPerformed(evt);
            }
        });

        preklici.setText("Prekliči");
        preklici.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prekliciActionPerformed(evt);
            }
        });

        potrdi.setText("Potrdi");
        potrdi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                potrdiActionPerformed(evt);
            }
        });
        potrdi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                potrdiKeyPressed(evt);
            }
        });

        partnerCheck.setText("Obvezen vnos partnerja");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(parrentTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(preklici, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(potrdi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(opisTxt)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(kontoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(kontoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(stmCheck)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(debetCheck)
                                .addGap(18, 18, 18)
                                .addComponent(kreditCheck))
                            .addComponent(partnerCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 34, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kontoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(kontoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(opisTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(debetCheck)
                    .addComponent(kreditCheck))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(stmCheck)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(partnerCheck)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(potrdi)
                    .addComponent(parrentTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(preklici)
                .addGap(16, 16, 16))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void parrentTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_parrentTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_parrentTxtActionPerformed

    private void prekliciActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prekliciActionPerformed
        dispose();
    }//GEN-LAST:event_prekliciActionPerformed

    private void potrdiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_potrdiActionPerformed
        String konto=kontoTxt.getText();
        String nKonto=konto.replaceAll("[^0-9]", "");
        String opis=opisTxt.getText();
        String parrent=parrentTxt.getText();
        String nParrent=parrent.replaceAll("[^0-9]", "");
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
                        try {
            con = DriverManager.getConnection(dburl, dbuser, dbpassword);
            pst = con.prepareStatement("SELECT konto FROM kontniNacrt WHERE konto=?");
            pst.setString(1, konto);
            rs = pst.executeQuery();

            if (rs.next()) {           
               String kontodb= rs.getString("konto");
               JOptionPane.showMessageDialog(null,"Številka konta mora biti unikatna!","Dodaj konto - napaka",JOptionPane.ERROR_MESSAGE);
            }
            
            else {
                dodajKonto2();
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
        
    }//GEN-LAST:event_potrdiActionPerformed

    private void kontoTxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_kontoTxtFocusLost
        String konto=kontoTxt.getText();
        String nKonto=konto.replaceAll("[^0-9]", "");
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
            try {
            con = DriverManager.getConnection(dburl, dbuser, dbpassword);
            pst = con.prepareStatement("SELECT konto FROM kontniNacrt WHERE konto=?");
            pst.setString(1, nKonto);
            rs = pst.executeQuery();

            if (rs.next()) {           
               String kontodb= rs.getString("konto");
               kontoLabel.setText("Konto že obstaja");
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
        
    }//GEN-LAST:event_kontoTxtFocusLost

    private void potrdiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_potrdiKeyPressed
       if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            String konto=kontoTxt.getText();
        String nKonto=konto.replaceAll("[^0-9]", "");
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
                        try {
            con = DriverManager.getConnection(dburl, dbuser, dbpassword);
            pst = con.prepareStatement("SELECT konto FROM kontniNacrt WHERE konto=?");
            pst.setString(1, konto);
            rs = pst.executeQuery();

            if (rs.next()) {           
               String kontodb= rs.getString("konto");
               JOptionPane.showMessageDialog(null,"Številka konta mora biti unikatna!","Dodaj konto - napaka",JOptionPane.ERROR_MESSAGE);
            }
            
            else {
                dodajKonto2();
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
    }//GEN-LAST:event_potrdiKeyPressed

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
            java.util.logging.Logger.getLogger(dodajKonto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dodajKonto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dodajKonto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dodajKonto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dodajKonto().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox debetCheck;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel kontoLabel;
    private javax.swing.JTextField kontoTxt;
    private javax.swing.JCheckBox kreditCheck;
    private javax.swing.JTextField opisTxt;
    private javax.swing.JTextField parrentTxt;
    private javax.swing.JCheckBox partnerCheck;
    private javax.swing.JButton potrdi;
    private javax.swing.JButton preklici;
    private javax.swing.JCheckBox stmCheck;
    // End of variables declaration//GEN-END:variables
}
