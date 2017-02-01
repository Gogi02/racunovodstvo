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
import javax.swing.table.DefaultTableModel;
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
    int dovoljenjaVnosPrometa;
    /**
     * Creates new form vnosPrometa
     */
    public vnosPrometa() {
        initComponents();
    }
    
    public void preveriObstajaZacasnaTemeljnica()
    {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;       

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
                
                dburl = aryNastavitve[0];//spremenjivke iz tabele
                dbuser = aryNastavitve[1];
                dbpassword = aryNastavitve[2];                

                try {
            con = DriverManager.getConnection(dburl, dbuser, dbpassword);
            pst = con.prepareStatement("SELECT stevilka FROM zacasnaTemeljnica");
            rs = pst.executeQuery();

            if (rs.next()) {
               populateZacasnaTemeljnica();
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
    
    public void populateZacasnaTemeljnica()
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
            pst = con.prepareStatement("SELECT stevilka, datum, mesec, leto, opomba FROM zacasnaTemeljnica");
            rs = pst.executeQuery();
            pst2 = con.prepareStatement("SELECT vnosPrometa FROM dovoljenja WHERE skupina=?");
            pst2.setInt(1, Login.skupina);
            rs2 = pst2.executeQuery();

            if (rs2.next()) {
                dovoljenjaVnosPrometa = rs2.getInt("vnosPrometa");                
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;   //Disallow the editing of any cell
            }};
            jLabel1 = new javax.swing.JLabel();

            setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

            preveriObstajaZacasnaTemeljnica();
            jTable1.setModel(tm);
            jScrollPane1.setViewportView(jTable1);

            jLabel1.setText("jLabel1");

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 694, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, Short.MAX_VALUE)))
                    .addContainerGap())
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jLabel1)
                    .addContainerGap(186, Short.MAX_VALUE))
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents

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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
