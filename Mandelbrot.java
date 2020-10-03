/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mandelbrot;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import static java.lang.Math.*;
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
    int midY,midX,xc,yc,FirstLVL=10,SecondLVL=20,ThirdLVL=100;
    int[] pix;
    Graphics g;
    boolean vir=true;
    
    public Mandelbrot() {
        initComponents();
    }

    public float len(ima a){
        return (float) sqrt(a.real*a.real+a.img*a.img);
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
    
    private ima iteration(ima a,ima x,int n,int k){
        if(n<k)return iteration(sum(square(a),x),x,n+1,k);
        else return a;
    }       
    
    public void getIter(){
        System.out.println("Starting Iterating");
        float distanceA=Math.abs(Integer.parseInt(aFrom.getText())-Integer.parseInt(aTo.getText()));
        float distanceB=Math.abs(Integer.parseInt(bFrom.getText())-Integer.parseInt(bTo.getText()));
        for(int y=yc-pan.getHeight();y<pan.getHeight()-yc;y++){
            for(int x=xc-pan.getWidth();x<pan.getWidth()-xc;x++){            
                ima a=new ima(0,0);  //start number (squarable)
                ima b=new ima(distanceA/pan.getWidth()*x,distanceB/pan.getWidth()*y);  //the adding number
                int loc=(y+yc)*pan.getWidth()+x+xc;
                if(len(iteration(a,b,0,100))<10){
                    pix[loc]=(0<<16)|(105<<8)|105;            
                }else if(len(iteration(a,b,0,20))>10){
                    pix[loc]=(0<<16)|(175<<8)|175;
                }else if(len(iteration(a,b,0,50))>10){
                    pix[loc]=(0<<16)|(255<<8)|255;
                }else{                    
                    pix[loc]=(0<<16)|(0<<8)|255;
                }
            }
        } 
        System.out.println("Done Iterating");
    }   
    
    private void draw(){
        System.out.println("Started drawing");          
        BufferedImage image=new BufferedImage(pan.getWidth(),pan.getHeight(),BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, pan.getWidth(), pan.getHeight(), pix, 0, pan.getWidth());      
        g.drawImage(image, 0, 0, this);
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
        pan.setPreferredSize(new java.awt.Dimension(800, 800));
        pan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panLayout = new javax.swing.GroupLayout(pan);
        pan.setLayout(panLayout);
        panLayout.setHorizontalGroup(
            panLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 846, Short.MAX_VALUE)
        );
        panLayout.setVerticalGroup(
            panLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        getContentPane().add(pan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 850, 900));

        drawBut.setText("Draw!");
        drawBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                drawButActionPerformed(evt);
            }
        });
        getContentPane().add(drawBut, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 910, 127, 80));

        jLabel1.setText("From");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 930, -1, -1));

        jLabel2.setText("To");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 960, -1, -1));

        jLabel3.setText("A");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 910, 10, -1));

        jLabel4.setText("B");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 910, 10, -1));

        aFrom.setText("-2");
        aFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aFromActionPerformed(evt);
            }
        });
        getContentPane().add(aFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 930, 30, -1));

        aTo.setText("2");
        aTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aToActionPerformed(evt);
            }
        });
        getContentPane().add(aTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 960, 30, -1));

        bFrom.setText("-2");
        bFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bFromActionPerformed(evt);
            }
        });
        getContentPane().add(bFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 930, 30, -1));

        bTo.setText("2");
        bTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bToActionPerformed(evt);
            }
        });
        getContentPane().add(bTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 960, 30, -1));

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
        if(vir){
        drawBut.setText("Redraw");
        xc=pan.getWidth()/2;
        yc=pan.getHeight()/2;
        pix=new int[pan.getWidth()*pan.getHeight()];
        g=pan.getGraphics();
        }        
        getIter(); 
        draw();
    }//GEN-LAST:event_drawButActionPerformed

    private void panMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_panMouseClicked

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
