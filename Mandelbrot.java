/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mandelbrot;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import static java.lang.Math.*;
import static javax.swing.SwingUtilities.*;
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
    int midY,midX,xc,yc,tempMouseX,tempMouseY,FirstLVL=10,SecondLVL=20,ThirdLVL=1000,stage=0,escape=2;
    int[] pix;
    Graphics g;
    boolean vir=true;
    
    
       
    
    public Mandelbrot() {
        initComponents();
        addMouseListener(new MouseAdapter() { 
            @Override
            public void mousePressed(MouseEvent e){
                System.out.println("Mouse Pressed");
                tempMouseX=e.getX()-xc;
                tempMouseY=e.getY()-yc;   
            }
            @Override
            public void mouseReleased(MouseEvent e){   
                float speedA=(float) Math.abs(Double.parseDouble(aFrom.getText())-Double.parseDouble(aTo.getText()))/pan.getWidth();
                float speedB=(float) Math.abs(Double.parseDouble(bFrom.getText())-Double.parseDouble(bTo.getText()))/pan.getHeight();               
                
                if(isLeftMouseButton(e)){                    
                    aFrom.setText(String.valueOf((tempMouseX)*speedA));
                    aTo.setText(String.valueOf((e.getX()-xc)*speedA));                 
                    bFrom.setText(String.valueOf((tempMouseY)*speedB));
                    bTo.setText(String.valueOf((e.getY()-xc)*speedB));                    
                }else if(isRightMouseButton(e)){
                    aFrom.setText("-1");
                    aTo.setText("1");                 
                    bFrom.setText("-1");
                    bTo.setText("1");  
                }
                System.out.println("Mouse Released");
                getIter(); 
                draw(); 
            }
        });
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
    
    private int escapeVelo(ima a,ima x,int n,int k){
        if(n>=k)return 0;
        else if(len(a)<escape)return escapeVelo(sum(square(a),x),x,n+1,k);  
        else return n;
    }
    
    public void getIter(){
        //System.out.println("Starting Iterating");
        float speedA=(float) Math.abs(Double.parseDouble(aFrom.getText())-Double.parseDouble(aTo.getText()))/pan.getWidth();
        float speedB=(float) Math.abs(Double.parseDouble(bFrom.getText())-Double.parseDouble(bTo.getText()))/pan.getHeight();
        System.out.println(speedA);
        System.out.println(speedB);       
        for(int y=0;y<pan.getHeight();y++){
            for(int x=0;x<pan.getWidth();x++){            
                ima a=new ima(0,0);  //start number (squarable)                
                ima b=new ima(Float.parseFloat(aFrom.getText())+x*speedA,Float.parseFloat(bFrom.getText())+y*speedB);  //the adding number
                int loc=y*pan.getWidth()+x;
                if(stage==0){                    
                    if(len(iteration(a,b,0,ThirdLVL))<10){
                        pix[loc]=(0<<16)|(0<<8)|0;            
                    }else if(len(iteration(a,b,0,FirstLVL))>10){                        
                        pix[loc]=(65<<16)|(105<<8)|225;
                    }else if(len(iteration(a,b,0,SecondLVL))>10){
                        pix[loc]=(0<<16)|(191<<8)|255;
                    }else{                    
                        pix[loc]=(70<<16)|(130<<8)|180;
                    }
                }else if(stage==1){
                    if(len(iteration(a,b,0,100))<10)pix[loc]=(0<<16)|(0<<8)|0;            
                    else pix[loc]=(255<<16)|(255<<8)|255;                    
                }else if(stage==2){
                    if(escapeVelo(a,b,0,24)==0)pix[loc]=(0<<16)|(0<<8)|0;
                    else pix[loc]=(255<<16)|(escapeVelo(a,b,0,24)*10<<8)|100;
                    //else pix[loc]=(100<<16)|(escapeVelo(a,b,0,24)*10<<8)|escapeVelo(a,b,0,24)*10;
                }               
            }
        } 
        //System.out.println("Done Iterating");
    }   
    
    private void draw(){
       // System.out.println("Started drawing");          
        BufferedImage image=new BufferedImage(pan.getWidth(),pan.getHeight(),BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, pan.getWidth(), pan.getHeight(), pix, 0, pan.getWidth());      
        g.drawImage(image, 0, 0, this);
        //System.out.println("Done drawing");
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
        modeSel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(810, 900));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pan.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pan.setMaximumSize(new java.awt.Dimension(800, 800));
        pan.setPreferredSize(new java.awt.Dimension(800, 800));

        javax.swing.GroupLayout panLayout = new javax.swing.GroupLayout(pan);
        pan.setLayout(panLayout);
        panLayout.setHorizontalGroup(
            panLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 796, Short.MAX_VALUE)
        );
        panLayout.setVerticalGroup(
            panLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 796, Short.MAX_VALUE)
        );

        getContentPane().add(pan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        drawBut.setText("Draw!");
        drawBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                drawButActionPerformed(evt);
            }
        });
        getContentPane().add(drawBut, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 800, 200, 80));

        jLabel1.setText("From");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 830, -1, -1));

        jLabel2.setText("To");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 860, -1, -1));

        jLabel3.setText("A");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 800, 10, -1));

        jLabel4.setText("B");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 800, 10, -1));

        aFrom.setText("-2");
        aFrom.setPreferredSize(new java.awt.Dimension(25, 20));
        aFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aFromActionPerformed(evt);
            }
        });
        getContentPane().add(aFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 820, 190, 30));

        aTo.setText("2");
        aTo.setPreferredSize(new java.awt.Dimension(25, 20));
        aTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aToActionPerformed(evt);
            }
        });
        getContentPane().add(aTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 850, 190, 30));

        bFrom.setText("-2");
        bFrom.setPreferredSize(new java.awt.Dimension(25, 20));
        bFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bFromActionPerformed(evt);
            }
        });
        getContentPane().add(bFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 820, 200, 30));

        bTo.setText("2");
        bTo.setPreferredSize(new java.awt.Dimension(25, 20));
        bTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bToActionPerformed(evt);
            }
        });
        getContentPane().add(bTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 850, 200, 30));

        modeSel.setText("Levels");
        modeSel.setPreferredSize(new java.awt.Dimension(65, 25));
        modeSel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modeSelActionPerformed(evt);
            }
        });
        getContentPane().add(modeSel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 800, 160, 80));

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

    private void modeSelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modeSelActionPerformed
        // TODO add your handling code here:
        if(stage==0){
            modeSel.setText("B/W");
            stage=1;
        }else if(stage==1){
            modeSel.setText("Escape Speed");
            stage=2;        
        }else if(stage==2){
            modeSel.setText("Levels");
            stage=0;
        }
    }//GEN-LAST:event_modeSelActionPerformed
 
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
    private javax.swing.JButton modeSel;
    private javax.swing.JPanel pan;
    // End of variables declaration//GEN-END:variables
}
