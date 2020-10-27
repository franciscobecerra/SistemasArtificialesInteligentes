package vendedor;
import vendedor.Vendedor;

public class Interfaz extends javax.swing.JFrame {

    Vendedor vendedor;
    public Interfaz( Vendedor vendedor ) {
        this.vendedor = vendedor;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        book = new javax.swing.JTextField();
        price = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font(".SF NS Text", 0, 24)); // NOI18N
        jLabel1.setText("Que libro vas a vender?");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, -1, -1));

        book.setFont(new java.awt.Font(".SF NS Text", 0, 18)); // NOI18N
        book.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bookActionPerformed(evt);
            }
        });
        getContentPane().add(book, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 150, 30));

        price.setFont(new java.awt.Font(".SF NS Text", 0, 18)); // NOI18N
        getContentPane().add(price, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 70, 130, 30));

        jLabel2.setText("Libro");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, -1));

        jLabel3.setText("precio");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 110, -1, -1));

        jLabel5.setFont(new java.awt.Font(".SF NS Text", 0, 18)); // NOI18N
        jLabel5.setText("$");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, 20, 30));

        jButton1.setText("Vender");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 150, -1, -1));
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 210));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bookActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.vendedor.sellBook( book.getText(), price.getText() );
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField book;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField price;
    // End of variables declaration//GEN-END:variables
}
