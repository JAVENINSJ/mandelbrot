/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mandelbrot;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.*;
/**
 *
 * @author Mr. Nobody
 */
class ima{
    float real=0,img=0;    
    public ima(float x,float y){
        this.real=x;
        this.img=y;
    }      
}

public class Mandelbrot extends javax.swing.JFrame {
    /**
     * Creates new form Mandelbrot
     */    
    BufferedImage image;
    Graphics g;
    int k=20;
    List<Float> realval=new ArrayList<>(); 
    List<Float> imgval=new ArrayList<>(); 
    int midY,midX,xc,yc;
    float[][] iter;
    
    public Mandelbrot() {
        initComponents();
    }

    public ima square(ima a){
        ima sum=new ima(0,0);
        sum.real=a.real*a.real-a.img*a.img;
        sum.img=2*a.real*a.img;
        return sum;
    }
    
    public ima sum(ima x,ima y){
        ima sum=new ima(0,0);
        sum.real=x.real+y.real;
        sum.img=x.img+y.img;
        return sum;
    }
    
    private ima iteration(ima a,ima x,int n){
        if(realval.contains(a.real)){
            realval.clear();
            imgval.clear();
            return new ima(n,0);            
        }else if(n<k){
            realval.add(a.real);
            imgval.add(a.img);
            n++;
            return iteration(sum(square(a),x),x,n+1);           
        }      
        realval.clear();
        imgval.clear();        
        return new ima(-1,0);
    }       
    
    protected void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters            
    }
    
    public void getIter(){
        float distanceA=Math.abs(Integer.parseInt(aFrom.getText())-Integer.parseInt(aTo.getText()));
        float distanceB=Math.abs(Integer.parseInt(bFrom.getText())-Integer.parseInt(bTo.getText()));
        for(int y=yc-pan.getHeight();y<pan.getHeight()-yc;y++){
            for(int x=xc-pan.getWidth();x<pan.getWidth()-xc;x++){            
                ima a=new ima(0,0);  //start number (squarable)
                ima b=new ima(distanceA/pan.getWidth()*x,distanceB/pan.getWidth()*y);  //the adding number
                iter[x+xc][y+yc]=iteration(a,b,0).real;
            }
        }
        System.out.println("done");
    }
    
    private void draw(){
        image=new BufferedImage(pan.getWidth(),pan.getHeight(),BufferedImage.TYPE_INT_RGB);
        for(int y=0;y<image.getHeight();y++){
            for(int x=0;x<image.getWidth();x++){
                Graphics2D g2d = image.createGraphics();
                if(iter[x][y]>=0&&iter[x][y]<10){                
                    g2d.setColor(Color.BLUE);
                }else if(iter[x][y]>=10&&iter[x][y]<20){
                    g2d.setColor(Color.CYAN);                                                    
                }else if(iter[x][y]>=20){
                    g2d.setColor(Color.GREEN);
                }
                if(iter[x][y]!=-1)g2d.drawRect(x,y,1,1);
            }
        }
        g.drawImage(image,
                0,0,null);
        System.out.println("Done drawing");
    }
    
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
            java.util.logging.Logger.getLogger(Mandelbrot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Mandelbrot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Mandelbrot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Mandelbrot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Mandelbrot().setVisible(true);
        });
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pan = new javax.swing.JPanel();
        drawBut = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        aFrom = new javax.swing.JTextField();
        aTo = new javax.swing.JTextField();
        bFrom = new javax.swing.JTextField();
        bTo = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pan.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout panLayout = new javax.swing.GroupLayout(pan);
        pan.setLayout(panLayout);
        panLayout.setHorizontalGroup(
            panLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 396, Short.MAX_VALUE)
        );
        panLayout.setVerticalGroup(
            panLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 396, Short.MAX_VALUE)
        );

        getContentPane().add(pan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, -1));

        drawBut.setText("Draw!");
        drawBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                drawButActionPerformed(evt);
            }
        });
        getContentPane().add(drawBut, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 410, 127, 80));

        jLabel1.setText("From");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, -1, -1));

        jLabel2.setText("To");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, -1, -1));

        jLabel3.setText("A");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 410, 10, -1));

        jLabel4.setText("B");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 410, 10, -1));

        aFrom.setText("-2");
        aFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aFromActionPerformed(evt);
            }
        });
        getContentPane().add(aFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 430, 30, -1));

        aTo.setText("2");
        aTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aToActionPerformed(evt);
            }
        });
        getContentPane().add(aTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 460, 30, -1));

        bFrom.setText("-1");
        bFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bFromActionPerformed(evt);
            }
        });
        getContentPane().add(bFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 430, 30, -1));

        bTo.setText("1");
        bTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bToActionPerformed(evt);
            }
        });
        getContentPane().add(bTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 460, 30, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void aFromActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aFromActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_aFromActionPerformed

    private void aToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aToActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_aToActionPerformed

    private void bFromActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bFromActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bFromActionPerformed

    private void bToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bToActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bToActionPerformed

    private void drawButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_drawButActionPerformed
        // TODO add your handling code here:
        drawBut.setVisible(false);
        xc=pan.getWidth()/2;
        yc=pan.getHeight()/2;
        pan.setBackground(Color.black);
        iter=new float[pan.getWidth()][pan.getHeight()];
        getIter(); 
        draw();
    }//GEN-LAST:event_drawButActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField aFrom;
    private javax.swing.JTextField aTo;
    private javax.swing.JTextField bFrom;
    private javax.swing.JTextField bTo;
    private javax.swing.JButton drawBut;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel pan;
    // End of variables declaration//GEN-END:variables
}
