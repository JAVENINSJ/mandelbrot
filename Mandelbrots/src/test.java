/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import static java.lang.Math.*;
import java.util.Stack;
import static javax.swing.SwingUtilities.*;
/**
 *
 * @author Mr. Nobody
 */
class ima{
    double real=0,img=0;    
    public ima(double x,double y){
        this.real=x;
        this.img=y;
    }      
}

class loc{
    String aStart,bStart,aEnd,bEnd;
    public loc(String a,String b,String c,String d){
        this.aStart=a;
        this.bStart=b;
        this.aEnd=c;
        this.bEnd=d;
    }
}

public class test extends javax.swing.JFrame {
    
    /**
     * Creates new form Mandelbrot
     */    
    
    int midY,midX,xc,yc,FirstLVL=10,SecondLVL=20,ThirdLVL=100,stage=0,escape=2;
    double speedA,speedB,tempMouseX,tempMouseY;
    int[] pix;
    Graphics g;
    boolean vir=true;
    Stack<loc> stack = new Stack<loc>();       
    
    public test() {
        initComponents();
        pan.addMouseListener(new MouseAdapter() { 
            @Override
            public void mousePressed(MouseEvent e){
                if(isLeftMouseButton(e)){
                    tempMouseX=Double.parseDouble(aFrom.getText())+e.getX()*speedA;
                    tempMouseY=Double.parseDouble(bTo.getText())-e.getY()*speedB;
                    g.setColor(Color.green);                
                    g.fillRect(e.getX(),e.getY(),4,4);                  
                }
            }
            @Override
            public void mouseReleased(MouseEvent e){   
                if(isLeftMouseButton(e)){ 
                    change();
                    double toX=Double.parseDouble(aFrom.getText())+e.getX()*speedA;    
                    aFrom.setText(String.valueOf(tempMouseX));
                    bFrom.setText(String.valueOf(tempMouseY));
                    aTo.setText(String.valueOf(toX));
                    bTo.setText(String.valueOf(Math.abs(toX-tempMouseX)+tempMouseY));                    
                }else if(isRightMouseButton(e)){
                    if(!stack.empty()){
                        aFrom.setText(stack.peek().aStart);                               
                        aTo.setText(stack.peek().aEnd);                 
                        bFrom.setText(stack.peek().bStart);
                        bTo.setText(stack.pop().bEnd);                         
                    }
                }
                doThings();
            }
        });
    }
    
    public void change(){
        stack.add(new loc(aFrom.getText(),bFrom.getText(),aTo.getText(),bTo.getText()));                
    }
    
    public void doThings(){
        if(Double.parseDouble(aFrom.getText())>Double.parseDouble(aTo.getText())){
            String temp=aFrom.getText();
            aFrom.setText(aTo.getText());
            aTo.setText(temp);
        }
        if(Double.parseDouble(bFrom.getText())>Double.parseDouble(bTo.getText())){
            String temp=bFrom.getText();
            bFrom.setText(bTo.getText());
            bTo.setText(temp);
        }
        getIter();
        draw();
    }
    
    public double len(ima a){
        return (double) sqrt(a.real*a.real+a.img*a.img);
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
        speedA=(double) Math.abs(Double.parseDouble(aFrom.getText())-Double.parseDouble(aTo.getText()))/pan.getWidth();
        speedB=(double) Math.abs(Double.parseDouble(bFrom.getText())-Double.parseDouble(bTo.getText()))/pan.getHeight();     
        for(int y=0;y<pan.getHeight();y++){
            for(int x=0;x<pan.getWidth();x++){            
                ima a=new ima(0,0);  //start number (squarable)                
                ima b=new ima(Double.parseDouble(aFrom.getText())+x*speedA,Double.parseDouble(bTo.getText())-y*speedB);  //the adding number
                int loc=y*pan.getWidth()+x;
                switch (stage) {
                    case 0:
                        if(len(iteration(a,b,0,ThirdLVL))<10){
                            pix[loc]=(0<<16)|(0<<8)|0;
                        }else if(len(iteration(a,b,0,FirstLVL))>10){
                            pix[loc]=(65<<16)|(105<<8)|225;
                        }else if(len(iteration(a,b,0,SecondLVL))>10){
                            pix[loc]=(0<<16)|(191<<8)|255;
                        }else{
                            pix[loc]=(70<<16)|(130<<8)|180;
                        }   break;
                    case 1:
                        if(len(iteration(a,b,0,100))<10)pix[loc]=(0<<16)|(0<<8)|0;
                        else pix[loc]=(255<<16)|(255<<8)|255;
                        break;
                    case 2:
                        if(escapeVelo(a,b,0,24)==0)pix[loc]=(0<<16)|(0<<8)|0;
                        else pix[loc]=(255<<16)|(escapeVelo(a,b,0,24)*10<<8)|100;               
                        //else pix[loc]=(100<<16)|(escapeVelo(a,b,0,24)*10<<8)|escapeVelo(a,b,0,24)*10;
                        break;
                    default:
                        break;
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
            java.util.logging.Logger.getLogger(test.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(test.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(test.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(test.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new test().setVisible(true);
        });
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
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
        reset = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(810, 900));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        
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
        getContentPane().add(modeSel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 800, 160, 40));

        reset.setText("Reset");
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });
        getContentPane().add(reset, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 840, 160, 40));

        pack();
    }// </editor-fold>                        

    private void aFromActionPerformed(java.awt.event.ActionEvent evt) {                                      
        // TODO add your handling code here:
    }                                     

    private void aToActionPerformed(java.awt.event.ActionEvent evt) {                                    
        // TODO add your handling code here:
    }                                   

    private void bFromActionPerformed(java.awt.event.ActionEvent evt) {                                      
        // TODO add your handling code here:
    }                                     

    private void bToActionPerformed(java.awt.event.ActionEvent evt) {                                    
        // TODO add your handling code here:
    }                                   

    private void drawButActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // TODO add your handling code here:
        if(vir){
            drawBut.setText("Redraw");
            xc=pan.getWidth()/2;
            yc=pan.getHeight()/2;
            pix=new int[pan.getWidth()*pan.getHeight()];
            g=pan.getGraphics();
            vir=false;
        }else{
            change();
        }        
        doThings();              
    }                                       

    private void modeSelActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // TODO add your handling code here:
        switch (stage) {
            case 0:
                modeSel.setText("B/W");
                stage=1;
                break;
            case 1:
                modeSel.setText("Escape Speed");
                stage=2;
                break;
            case 2:
                modeSel.setText("Levels");
                stage=0;
                break;
            default:
                break;
        }
    }                                       

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {                                      
        // TODO add your handling code here:
        aFrom.setText("-2");
        aTo.setText("2");
        bFrom.setText("-2");
        bTo.setText("2");
        doThings();
    }                                     
 
    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify                     
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
    private javax.swing.JButton reset;
    // End of variables declaration                   
}
