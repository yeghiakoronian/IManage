package internetr;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.filechooser.*;
import java.io.File;
import java.io.*;
import java.sql.*;





public class IManage implements ActionListener
{
    private static Connection con;
    private static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

 
  public IManage()
  {

    JFrame frame = new JFrame("IManage");
    Container content = frame.getContentPane();
    content.setLayout(new GridLayout());
    Icon icon=new ImageIcon("IManage.gif");
    JPanel pan=new JPanel(new BorderLayout());
    JLabel jl=new JLabel("",icon,JLabel.CENTER);
    pan.add(jl,BorderLayout.CENTER);

    JMenuBar menuBar = new JMenuBar();

    // Representative menu, R - Mnemonic
    JMenu NewMenu = new JMenu("Repr");
    NewMenu.setMnemonic(KeyEvent.VK_R);
    menuBar.add(NewMenu);

    // Repr->New repr , N - Mnemonic
    JMenuItem newMenuItem = new JMenuItem("New Repr", KeyEvent.VK_N);
    newMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
       final JFrame f1=new JFrame("New Repr");
       f1.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
       Container c = f1.getContentPane();
       c.setLayout(new GridLayout(5,2));

       JPanel p1=new JPanel(new BorderLayout());
       JPanel p2=new JPanel(new BorderLayout());
       JPanel p3=new JPanel(new BorderLayout());
       JPanel p4=new JPanel(new BorderLayout());
       JPanel p5=new JPanel(new BorderLayout());
       
       JLabel l1=new JLabel("  Repr ID");
       final JTextField t1=new JTextField(10);
       l1.setLabelFor(t1);
       p1.add(l1,BorderLayout.WEST);
       p1.add(t1,BorderLayout.EAST);
       JLabel l2=new JLabel("  Repr Name");
       final JTextField t2=new JTextField(10);
       l2.setLabelFor(t2);
       p3.add(l2,BorderLayout.WEST);
       p3.add(t2,BorderLayout.EAST);
       JLabel l3=new JLabel("  DOB");
       final JTextField t3=new JTextField(10);
       l3.setLabelFor(t3);
       p4.add(l3,BorderLayout.WEST);
       p4.add(t3,BorderLayout.EAST);
       JLabel l4=new JLabel("  Repr Tel");
       final JTextField t4=new JTextField(10);
       l4.setLabelFor(t4);
       p5.add(l4,BorderLayout.WEST);
       p5.add(t4,BorderLayout.EAST);

       JButton b1=new JButton("Ok");
       JButton b2=new JButton("Cancel");

       b2.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent actionEvent){ f1.setVisible(false); }});
       b1.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent actionEvent) {
       String s1=new String(t1.getText());
       String s2=new String(t2.getText());
       String s3=new String(t3.getText());
       String s4=new String(t4.getText());
         
       try
        {
        addRepr(s1,s2,s3,s4);
        }
        catch(SQLException e) {JOptionPane.showMessageDialog(null, e.getMessage());}
       t1.setText(" ");
       t2.setText(" ");
       t3.setText(" ");
       t4.setText(" ");
       }});

       p2.add(b1,BorderLayout.WEST);
       p2.add(b2,BorderLayout.EAST);

       c.add(p1);
       c.add(p3);
       c.add(p4);
       c.add(p5);
       c.add(p2);
       f1.setSize(250, 150);
       f1.setVisible(true);
      }});
    NewMenu.add(newMenuItem);
    // Repr->All repr , S - Mnemonic
    JMenuItem newMenuItem1 = new JMenuItem("Show All Repr", KeyEvent.VK_S);
    newMenuItem1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
          try
          {
          Statement stmt1 = con.createStatement();
          ResultSet S = stmt1.executeQuery("Select * from Repr");
          InterationThroghRows(S);
          }
          catch(SQLException e) {JOptionPane.showMessageDialog(null, e.getMessage());}
          }});
       
          
    NewMenu.add(newMenuItem1);
// Repr->assign repr , A - Mnemonic
    JMenuItem newMenuItem2 = new JMenuItem("Assign Repr To Order", KeyEvent.VK_A);
    newMenuItem2.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
       final JFrame f1=new JFrame("Assign");
       f1.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
       Container c = f1.getContentPane();
       c.setLayout(new GridLayout(3,2));

       JPanel p1=new JPanel(new BorderLayout());
       JPanel p2=new JPanel(new BorderLayout());
       JPanel p3=new JPanel(new BorderLayout());
       
       JLabel l1=new JLabel("  Repr ID");
       final JTextField t1=new JTextField(10);
       l1.setLabelFor(t1);
       p1.add(l1,BorderLayout.WEST);
       p1.add(t1,BorderLayout.EAST);
       JLabel l2=new JLabel("  Order ID");
       final JTextField t2=new JTextField(10);
       l2.setLabelFor(t2);
       p3.add(l2,BorderLayout.WEST);
       p3.add(t2,BorderLayout.EAST);

       JButton b1=new JButton("Ok");
       JButton b2=new JButton("Cancel");

       b2.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent actionEvent){ f1.setVisible(false); }});
       b1.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent actionEvent) {
       String s1=new String(t1.getText());
       String s2=new String(t2.getText());
         
       try
        {
        assRepr_toOrder(s1,s2);
        }
        catch(SQLException e) {JOptionPane.showMessageDialog(null, e.getMessage());}
       t1.setText(" ");
       t2.setText(" ");
       }});

       p2.add(b1,BorderLayout.WEST);
       p2.add(b2,BorderLayout.EAST);

       c.add(p1);
       c.add(p3);
       c.add(p2);
       f1.setSize(250, 150);
       f1.setVisible(true);
      }});
    NewMenu.add(newMenuItem2);
    
    // Customer menu, C - Mnemonic
    JMenu NewMenu1 = new JMenu("Cust");
    NewMenu1.setMnemonic(KeyEvent.VK_C);
    menuBar.add(NewMenu1);

    // Cust->New cust , N - Mnemonic
    JMenuItem newMenuItem4 = new JMenuItem("New Cust", KeyEvent.VK_N);
    newMenuItem4.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
       final JFrame f1=new JFrame("New Customer");
       f1.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
       Container c = f1.getContentPane();
       c.setLayout(new GridLayout(7,2));

       JPanel p1=new JPanel(new BorderLayout());
       JPanel p2=new JPanel(new BorderLayout());
       JPanel p3=new JPanel(new BorderLayout());
       JPanel p4=new JPanel(new BorderLayout());
       JPanel p5=new JPanel(new BorderLayout());
       JPanel p6=new JPanel(new BorderLayout());
       JPanel p7=new JPanel(new BorderLayout());
       
       JLabel l1=new JLabel("  Cust ID");
       final JTextField t1=new JTextField(10);
       l1.setLabelFor(t1);
       p1.add(l1,BorderLayout.WEST);
       p1.add(t1,BorderLayout.EAST);
       JLabel l2=new JLabel("  Cust Name");
       final JTextField t2=new JTextField(10);
       l2.setLabelFor(t2);
       p3.add(l2,BorderLayout.WEST);
       p3.add(t2,BorderLayout.EAST);
       JLabel l3=new JLabel("  Building");
       final JTextField t3=new JTextField(10);
       l3.setLabelFor(t3);
       p4.add(l3,BorderLayout.WEST);
       p4.add(t3,BorderLayout.EAST);
       JLabel l4=new JLabel("  City");
       final JTextField t4=new JTextField(10);
       l4.setLabelFor(t4);
       p5.add(l4,BorderLayout.WEST);
       p5.add(t4,BorderLayout.EAST);
       
       JLabel l5=new JLabel("  Street");
       final JTextField t5=new JTextField(10);
       l5.setLabelFor(t5);
       p6.add(l5,BorderLayout.WEST);
       p6.add(t5,BorderLayout.EAST);
       
       JLabel l6=new JLabel("  Tel");
       final JTextField t6=new JTextField(10);
       l6.setLabelFor(t6);
       p7.add(l6,BorderLayout.WEST);
       p7.add(t6,BorderLayout.EAST);

       JButton b1=new JButton("Ok");
       JButton b2=new JButton("Cancel");

       b2.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent actionEvent){ f1.setVisible(false); }});
       b1.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent actionEvent) {
       String s1=new String(t1.getText());
       String s2=new String(t2.getText());
       String s3=new String(t3.getText());
       String s4=new String(t4.getText());
       String s5=new String(t5.getText());
       String s6=new String(t6.getText());

         String Cust = "INSERT INTO Customer VALUES('" + s1 +"','" + s2 + "','"+ s3 +"','"+s4+"','"+ s5 +"','"+ s6 +"');";
         try
         {
         Statement stmt = con.createStatement();
         stmt.executeUpdate(Cust);
         }
        catch(SQLException e) {JOptionPane.showMessageDialog(null, e.getMessage());} 
 
       t1.setText(" ");
       t2.setText(" ");
       t3.setText(" ");
       t4.setText(" ");
       t5.setText(" ");
       t6.setText(" ");
       }});

       p2.add(b1,BorderLayout.WEST);
       p2.add(b2,BorderLayout.EAST);

       c.add(p1);
       c.add(p3);
       c.add(p4);
       c.add(p5);
       c.add(p6);
       c.add(p7);
       c.add(p2);
       f1.setSize(300, 200);
       f1.setVisible(true);
      }});
    NewMenu1.add(newMenuItem4);
        // Cust->Delete cust , D - Mnemonic
    JMenuItem newMenuItem5 = new JMenuItem("Delete Customer", KeyEvent.VK_D);
    newMenuItem5.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
       final JFrame f1=new JFrame("Delete Customer");
       f1.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
       Container c = f1.getContentPane();
       c.setLayout(new GridLayout(2,2));

       JPanel p1=new JPanel(new BorderLayout());
       JPanel p2=new JPanel(new BorderLayout());
       
       JLabel l1=new JLabel("  Cust ID");
       final JTextField t1=new JTextField(10);
       l1.setLabelFor(t1);
       p1.add(l1,BorderLayout.WEST);
       p1.add(t1,BorderLayout.EAST);
     
       JButton b1=new JButton("Ok");
       JButton b2=new JButton("Cancel");

       b2.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent actionEvent){ f1.setVisible(false); }});
       b1.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent actionEvent) {
       String s1=new String(t1.getText());
       String Del="Delete from Customer where (CID='"+s1+"');";
       try
        {
        Statement stmt = con.createStatement();
        stmt.executeUpdate(Del);
        }
        catch(SQLException e) {JOptionPane.showMessageDialog(null, e.getMessage());}
       t1.setText(" ");
       }});

       p2.add(b1,BorderLayout.WEST);
       p2.add(b2,BorderLayout.EAST);

       c.add(p1);
       c.add(p2);
       f1.setSize(250, 150);
       f1.setVisible(true);
      }});
    NewMenu1.add(newMenuItem5);
    // Cust->Find cust , F - Mnemonic
    JMenuItem newMenuItem6 = new JMenuItem("Find Customer", KeyEvent.VK_F);
    newMenuItem6.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
       final JFrame f1=new JFrame("Find Customer");
       f1.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
       Container c = f1.getContentPane();
       c.setLayout(new GridLayout(2,2));

       JPanel p1=new JPanel(new BorderLayout());
       JPanel p2=new JPanel(new BorderLayout());
       
       JLabel l1=new JLabel("  Cust ID");
       final JTextField t1=new JTextField(10);
       l1.setLabelFor(t1);
       p1.add(l1,BorderLayout.WEST);
       p1.add(t1,BorderLayout.EAST);
     
       JButton b1=new JButton("Ok");
       JButton b2=new JButton("Cancel");

       b2.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent actionEvent){ f1.setVisible(false); }});
       b1.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent actionEvent) {
       String s1=new String(t1.getText());
       try
       {
           Statement stmt2 = con.createStatement();
           ResultSet S = stmt2.executeQuery("Select * from Customer where(CID='"+s1+"');");
            String s = new String();
            while(S.next()){
            String id = S.getString("CID");
            String name = S.getString("CName");
            String bld = S.getString("bldg");
            String city = S.getString("city");
            String str = S.getString("street");
            String tel=S.getString("tel");
            s = s +"\neid: " +id+ "\nname: " +name+ "\nbuilding: " +bld +"\ncity: " +city +"\nstreet: " +str +"\ntel: " +tel + "\n\n";
        } 

      
      if(s.equals("")) JOptionPane.showMessageDialog(null, "Not found");
      else JOptionPane.showMessageDialog(null,s);
        
        }
        catch(SQLException e) {JOptionPane.showMessageDialog(null, e.getMessage());}
       t1.setText(" ");
       }});

       p2.add(b1,BorderLayout.WEST);
       p2.add(b2,BorderLayout.EAST);

       c.add(p1);
       c.add(p2);
       f1.setSize(250, 150);
       f1.setVisible(true);
      }});
    NewMenu1.add(newMenuItem6);
            // Cust->All orders done by cust , A - Mnemonic
    JMenuItem newMenuItem7 = new JMenuItem("All Orders Done by Customer", KeyEvent.VK_A);
    newMenuItem7.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
       final JFrame f1=new JFrame("All Orders Done by Customer");
       f1.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
       Container c = f1.getContentPane();
       c.setLayout(new GridLayout(2,2));

       JPanel p1=new JPanel(new BorderLayout());
       JPanel p2=new JPanel(new BorderLayout());
       
       JLabel l1=new JLabel("  Cust ID");
       final JTextField t1=new JTextField(10);
       l1.setLabelFor(t1);
       p1.add(l1,BorderLayout.WEST);
       p1.add(t1,BorderLayout.EAST);
     
       JButton b1=new JButton("Ok");
       JButton b2=new JButton("Cancel");

       b2.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent actionEvent){ f1.setVisible(false); }});
       b1.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent actionEvent) {
       String s1=new String(t1.getText());
       try
        {
           Statement stmt3 = con.createStatement();
           ResultSet S = stmt3.executeQuery("Select OID from Order1 where(Customer='"+s1+"');");
            String s = new String();
            while(S.next()){
            String id = S.getString("OID");
            s = s +"\nOrder: " +id+ "\n\n";
        } 

      
      if(s.equals("")) JOptionPane.showMessageDialog(null, "No Orders");
      else JOptionPane.showMessageDialog(null,s);
        }
        catch(SQLException e) {JOptionPane.showMessageDialog(null, e.getMessage());}
       t1.setText(" ");
       }});

       p2.add(b1,BorderLayout.WEST);
       p2.add(b2,BorderLayout.EAST);

       c.add(p1);
       c.add(p2);
       f1.setSize(250, 150);
       f1.setVisible(true);
      }});
    NewMenu1.add(newMenuItem7);
            // Cust->All Shipments sent by cust , S - Mnemonic
    JMenuItem newMenuItem8 = new JMenuItem("All Shipments by Customer", KeyEvent.VK_S);
    newMenuItem8.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
       final JFrame f1=new JFrame("Shipments sent by Customer");
       f1.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
       Container c = f1.getContentPane();
       c.setLayout(new GridLayout(2,2));

       JPanel p1=new JPanel(new BorderLayout());
       JPanel p2=new JPanel(new BorderLayout());
       
       JLabel l1=new JLabel("  Cust ID");
       final JTextField t1=new JTextField(10);
       l1.setLabelFor(t1);
       p1.add(l1,BorderLayout.WEST);
       p1.add(t1,BorderLayout.EAST);
     
       JButton b1=new JButton("Ok");
       JButton b2=new JButton("Cancel");

       b2.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent actionEvent){ f1.setVisible(false); }});
       b1.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent actionEvent) {
       String s1=new String(t1.getText());
       try
        {
          Statement stmt2 = con.createStatement();
           ResultSet S = stmt2.executeQuery("Select ShipID from Shipment where(Customer='"+s1+"');");
            String s = new String();
            while(S.next()){
            String id = S.getString("ShipID");
            s = s +"\nShipment: " +id+"\n\n";
        } 

      
      if(s.equals("")) JOptionPane.showMessageDialog(null, "No Shipments");
      else JOptionPane.showMessageDialog(null,s);
        }
        catch(SQLException e) {JOptionPane.showMessageDialog(null, e.getMessage());}
       t1.setText(" ");
       }});

       p2.add(b1,BorderLayout.WEST);
       p2.add(b2,BorderLayout.EAST);

       c.add(p1);
       c.add(p2);
       f1.setSize(250, 150);
       f1.setVisible(true);
      }});
    NewMenu1.add(newMenuItem8);
       // Supplier menu, S - Mnemonic
    JMenu NewMenu3 = new JMenu("Supplier");
    NewMenu3.setMnemonic(KeyEvent.VK_S);
    menuBar.add(NewMenu3);

    // Supplier->New Supplier , N - Mnemonic
    JMenuItem Supp = new JMenuItem("New Supplier", KeyEvent.VK_N);
    Supp.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
       final JFrame f1=new JFrame("New Supplier");
       f1.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
       Container c = f1.getContentPane();
       c.setLayout(new GridLayout(7,2));

       JPanel p1=new JPanel(new BorderLayout());
       JPanel p2=new JPanel(new BorderLayout());
       JPanel p3=new JPanel(new BorderLayout());
       JPanel p4=new JPanel(new BorderLayout());
       JPanel p5=new JPanel(new BorderLayout());
       JPanel p6=new JPanel(new BorderLayout());
       JPanel p7=new JPanel(new BorderLayout());
       
       JLabel l1=new JLabel("  Supplier ID");
       final JTextField t1=new JTextField(10);
       l1.setLabelFor(t1);
       p1.add(l1,BorderLayout.WEST);
       p1.add(t1,BorderLayout.EAST);
       JLabel l2=new JLabel("  Supplier Name");
       final JTextField t2=new JTextField(10);
       l2.setLabelFor(t2);
       p3.add(l2,BorderLayout.WEST);
       p3.add(t2,BorderLayout.EAST);
       JLabel l3=new JLabel("  Tel");
       final JTextField t3=new JTextField(10);
       l3.setLabelFor(t3);
       p4.add(l3,BorderLayout.WEST);
       p4.add(t3,BorderLayout.EAST);
       JLabel l4=new JLabel("  City");
       final JTextField t4=new JTextField(10);
       l4.setLabelFor(t4);
       p5.add(l4,BorderLayout.WEST);
       p5.add(t4,BorderLayout.EAST);
       
       JLabel l5=new JLabel("  Street");
       final JTextField t5=new JTextField(10);
       l5.setLabelFor(t5);
       p6.add(l5,BorderLayout.WEST);
       p6.add(t5,BorderLayout.EAST);
       
       JLabel l6=new JLabel("  Building");
       final JTextField t6=new JTextField(10);
       l6.setLabelFor(t6);
       p7.add(l6,BorderLayout.WEST);
       p7.add(t6,BorderLayout.EAST);

       JButton b1=new JButton("Ok");
       JButton b2=new JButton("Cancel");

       b2.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent actionEvent){ f1.setVisible(false); }});
       b1.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent actionEvent) {
       String s1=new String(t1.getText());
       String s2=new String(t2.getText());
       String s3=new String(t3.getText());
       String s4=new String(t4.getText());
       String s5=new String(t5.getText());
       String s6=new String(t6.getText());
       
         String Sup = "INSERT INTO Supplier VALUES('" + s1 +"','" + s2 + "','"+ s3 +"','"+s4+"','"+ s5 +"','"+ s6 +"');";
       try
        {
           Statement stmt = con.createStatement();
           stmt.executeUpdate(Sup); 
        }
        catch(SQLException e) {JOptionPane.showMessageDialog(null, e.getMessage());}
       t1.setText(" ");
       t2.setText(" ");
       t3.setText(" ");
       t4.setText(" ");
       t5.setText(" ");
       t6.setText(" ");
       }});

       p2.add(b1,BorderLayout.WEST);
       p2.add(b2,BorderLayout.EAST);

       c.add(p1);
       c.add(p3);
       c.add(p4);
       c.add(p5);
       c.add(p6);
       c.add(p7);
       c.add(p2);
       f1.setSize(250, 150);
       f1.setVisible(true);
      }});
    NewMenu3.add(Supp);
    // Supplier->Delete Supplier , D - Mnemonic
    JMenuItem Delete = new JMenuItem("Delete Supplier", KeyEvent.VK_D);
    Delete.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
       final JFrame f1=new JFrame("Delete Supplier");
       f1.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
       Container c = f1.getContentPane();
       c.setLayout(new GridLayout(2,2));

       JPanel p1=new JPanel(new BorderLayout());
       JPanel p2=new JPanel(new BorderLayout());
       
       JLabel l1=new JLabel("  Supplier ID");
       final JTextField t1=new JTextField(10);
       l1.setLabelFor(t1);
       p1.add(l1,BorderLayout.WEST);
       p1.add(t1,BorderLayout.EAST);
     
       JButton b1=new JButton("Ok");
       JButton b2=new JButton("Cancel");

       b2.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent actionEvent){ f1.setVisible(false); }});
       b1.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent actionEvent) {
       String s1=new String(t1.getText());
       String Del="Delete from Supplier where (SID='"+s1+"');";
       try
        {
        Statement stmt = con.createStatement();
        int x=stmt.executeUpdate(Del);
        if(x==1)
        {
           JOptionPane.showMessageDialog(null, "Deleted"); 
        }
        else
        {
            JOptionPane.showMessageDialog(null, "No such Supplier to delete");
        }
    }
        catch(SQLException e) {JOptionPane.showMessageDialog(null, e.getMessage());}
       t1.setText(" ");
       }});

       p2.add(b1,BorderLayout.WEST);
       p2.add(b2,BorderLayout.EAST);

       c.add(p1);
       c.add(p2);
       f1.setSize(250, 150);
       f1.setVisible(true);
      }});
    NewMenu3.add(Delete);
     // Supplier->Show all products supplied by supplier, S - Mnemonic
    JMenuItem show = new JMenuItem("Show All Products supplied by supplier", KeyEvent.VK_S);
    show.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
       final JFrame f1=new JFrame("Products by Supplier");
       f1.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
       Container c = f1.getContentPane();
       c.setLayout(new GridLayout(2,2));

       JPanel p1=new JPanel(new BorderLayout());
       JPanel p2=new JPanel(new BorderLayout());
       
       JLabel l1=new JLabel("  Supplier ID");
       final JTextField t1=new JTextField(10);
       l1.setLabelFor(t1);
       p1.add(l1,BorderLayout.WEST);
       p1.add(t1,BorderLayout.EAST);
     
       JButton b1=new JButton("Ok");
       JButton b2=new JButton("Cancel");

       b2.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent actionEvent){ f1.setVisible(false); }});
       b1.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent actionEvent) {
       String s1=new String(t1.getText());
       try
        {
          Statement stmt2 = con.createStatement();
           ResultSet S = stmt2.executeQuery("Select PID from Supply where(SID='"+s1+"');");
            String s = new String();
            while(S.next()){
            String pid = S.getString("PID");
            s = s +"\nproduct: " +pid+"\n\n";
        } 

      
      if(s.equals("")) JOptionPane.showMessageDialog(null, "No Products");
      else JOptionPane.showMessageDialog(null,s);
        }
        catch(SQLException e) {JOptionPane.showMessageDialog(null, e.getMessage());}
       t1.setText(" ");
       }});

       p2.add(b1,BorderLayout.WEST);
       p2.add(b2,BorderLayout.EAST);

       c.add(p1);
       c.add(p2);
       f1.setSize(250, 150);
       f1.setVisible(true);
      }});
    NewMenu3.add(show);
           // Orders menu, O - Mnemonic
    JMenu order = new JMenu("Orders");
    order.setMnemonic(KeyEvent.VK_O);
    menuBar.add(order);

    // Orders->New Order , N - Mnemonic
    JMenuItem newO = new JMenuItem("New Order", KeyEvent.VK_N);
    newO.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
       final JFrame f1=new JFrame("New Order");
       f1.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
       Container c = f1.getContentPane();
       c.setLayout(new GridLayout(3,2));

       JPanel p1=new JPanel(new BorderLayout());
       JPanel p2=new JPanel(new BorderLayout());
       JPanel p4=new JPanel(new BorderLayout());

       
       JLabel l1=new JLabel("  Order ID");
       final JTextField t1=new JTextField(10);
       l1.setLabelFor(t1);
       p1.add(l1,BorderLayout.WEST);
       p1.add(t1,BorderLayout.EAST);

       JLabel l3=new JLabel("  Balance");
       final JTextField t3=new JTextField(10);
       l3.setLabelFor(t3);
       p4.add(l3,BorderLayout.WEST);
       p4.add(t3,BorderLayout.EAST);

       JButton b1=new JButton("Ok");
       JButton b2=new JButton("Cancel");

       b2.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent actionEvent){ f1.setVisible(false); }});
       b1.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent actionEvent) {
       String s1=new String(t1.getText());
       String s3=new String(t3.getText());
       
         String new_Order = "INSERT INTO Order1(OID,Balance) VALUES('" + s1 +"','" +  s3 +"');";
       try
        {
           Statement stmt4 = con.createStatement();
           stmt4.executeUpdate(new_Order); 
        }
        catch(SQLException e) {JOptionPane.showMessageDialog(null, e.getMessage());}
       t1.setText(" ");
       t3.setText(" ");
       }});

       p2.add(b1,BorderLayout.WEST);
       p2.add(b2,BorderLayout.EAST);

       c.add(p1);
       c.add(p4);
       c.add(p2);
       f1.setSize(250, 150);
       f1.setVisible(true);
      }});
    order.add(newO);
   // Orders->Delete Order , D - Mnemonic
    JMenuItem delO = new JMenuItem("Delete Order", KeyEvent.VK_D);
    delO.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
       final JFrame f1=new JFrame("Delete Order");
       f1.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
       Container c = f1.getContentPane();
       c.setLayout(new GridLayout(2,2));

       JPanel p1=new JPanel(new BorderLayout());
       JPanel p2=new JPanel(new BorderLayout());
       
       JLabel l1=new JLabel("  Order ID");
       final JTextField t1=new JTextField(10);
       l1.setLabelFor(t1);
       p1.add(l1,BorderLayout.WEST);
       p1.add(t1,BorderLayout.EAST);
     
       JButton b1=new JButton("Ok");
       JButton b2=new JButton("Cancel");

       b2.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent actionEvent){ f1.setVisible(false); }});
       b1.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent actionEvent) {
       String s1=new String(t1.getText());
       String Del1="Delete from Order1 where (OID='"+s1+"');";
       try
        {
        Statement stmt = con.createStatement();
        int y=stmt.executeUpdate(Del1);
        if(y==1)
        {
           JOptionPane.showMessageDialog(null, "Deleted"); 
        }
        else
        {
            JOptionPane.showMessageDialog(null, "No such Order to delete");
        }
    }
        catch(SQLException e) {JOptionPane.showMessageDialog(null, e.getMessage());}
       t1.setText(" ");
       }});

       p2.add(b1,BorderLayout.WEST);
       p2.add(b2,BorderLayout.EAST);

       c.add(p1);
       c.add(p2);
       f1.setSize(250, 150);
       f1.setVisible(true);
      }});
    order.add(delO);
// Orders->Show all products in an order with all info of both, S - Mnemonic
    JMenuItem showO = new JMenuItem("Show products in an order", KeyEvent.VK_S);
    showO.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
       final JFrame f1=new JFrame("Products in order with info");
       f1.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
       Container c = f1.getContentPane();
       c.setLayout(new GridLayout(2,2));

       JPanel p1=new JPanel(new BorderLayout());
       JPanel p2=new JPanel(new BorderLayout());
       
       JLabel l1=new JLabel("  Order ID");
       final JTextField t1=new JTextField(10);
       l1.setLabelFor(t1);
       p1.add(l1,BorderLayout.WEST);
       p1.add(t1,BorderLayout.EAST);
     
       JButton b1=new JButton("Ok");
       JButton b2=new JButton("Cancel");

       b2.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent actionEvent){ f1.setVisible(false); }});
       b1.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent actionEvent) {
       String s1=new String(t1.getText());
       try
        {
          Statement stmt2 = con.createStatement();
           ResultSet S = stmt2.executeQuery("Select Order1.*,Product.* from Order1,Include,Product where(Order1.OID='"+s1+"' AND Order1.OID=Include.OID AND Include.PID=Product.ProdID);");
            String s = new String();
            String id=new String();
            String date=new String();
            String bal=new String();
            String cust=new String();
            String reps=new String();
            String shp=new String();
            while(S.next())
            {
            id=S.getString("OID");
            date=S.getString("ODate");
            bal=S.getString("Balance");
            cust=S.getString("Customer");
            reps=S.getString("Repres");
            shp=S.getString("Shipment");
            }
            s="\nID: "+ id +"\nDate: "+ date +"\nBalance: "+ bal +"\nCustomer: "+ cust +"\nRepresentative: "+ reps +"\nShipment: "+ shp +"\n";
        
            S= stmt2.executeQuery("Select Order1.*,Product.* from Order1,Include,Product where(Order1.OID='"+s1+"' AND Order1.OID=Include.OID AND Include.PID=Product.ProdID);");
            while(S.next()){
            String pid = S.getString("ProdID");
            String pname = S.getString("Pname");
            String price = S.getString("Price");
            String quantity = S.getString("Quantity");
            String type = S.getString("Type");
            s = s +"\nproduct: " +pid+" , " +pname+" , " +price+"$ , " +quantity+" , "+type+"\n\n";
        } 

      
      if(s.equals("")) JOptionPane.showMessageDialog(null, "No Order");
      else JOptionPane.showMessageDialog(null,s);
        }
        catch(SQLException e) {JOptionPane.showMessageDialog(null, e.getMessage());}
       t1.setText(" ");
       }});

       p2.add(b1,BorderLayout.WEST);
       p2.add(b2,BorderLayout.EAST);

       c.add(p1);
       c.add(p2);
       f1.setSize(250, 150);
       f1.setVisible(true);
      }});
    order.add(showO);
    // Orders->Show all products in an order with all info of both, S - Mnemonic
    JMenuItem showO2 = new JMenuItem("Show products in an order (2)", KeyEvent.VK_W);
    showO2.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
       final JFrame f1=new JFrame("Products in order with info(2)");
       f1.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
       Container c = f1.getContentPane();
       c.setLayout(new GridLayout(2,2));

       JPanel p1=new JPanel(new BorderLayout());
       JPanel p2=new JPanel(new BorderLayout());
       
       JLabel l1=new JLabel("  Order ID");
       final JTextField t1=new JTextField(10);
       l1.setLabelFor(t1);
       p1.add(l1,BorderLayout.WEST);
       p1.add(t1,BorderLayout.EAST);
     
       JButton b1=new JButton("Ok");
       JButton b2=new JButton("Cancel");

       b2.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent actionEvent){ f1.setVisible(false); }});
       b1.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent actionEvent) {
       String s1=new String(t1.getText());
       try
        {
          Statement stmt2 = con.createStatement();
           ResultSet S = stmt2.executeQuery("Select * from Order1 Where (OID='"+s1+"');");
           String s = new String();
           while(S.next())
            {
            String id=S.getString("OID");
            String date=S.getString("ODate");
            String bal=S.getString("Balance");
            String cust=S.getString("Customer");
            String reps=S.getString("Repres");
            String shp=S.getString("Shipment");
            s="\nID: "+ id +"\nDate: "+ date +"\nBalance: "+ bal +"\nCustomer: "+ cust +"\nRepresentative: "+ reps +"\nShipment: "+ shp +"\n";
            }
            Statement stmt3 = con.createStatement();
            ResultSet R = stmt3.executeQuery("Select Product.* from Product,Include Where (OID='"+s1+"' And PID=ProdID);");
            while(R.next()){
            String pid = R.getString("ProdID");
            String pname = R.getString("Pname");
            String price = R.getString("Price");
            String quantity = R.getString("Quantity");
            String type = R.getString("Type");
            s = s +"\nproduct: " +pid+" , " +pname+" , " +price+" $ , " +quantity+" , "+type+"\n\n";
        } 

      
      if(s.equals("")) 
          JOptionPane.showMessageDialog(null, "No Order");
      else 
      JOptionPane.showMessageDialog(null,s);
        }
        catch(SQLException e) {JOptionPane.showMessageDialog(null, e.getMessage());}
       t1.setText(" ");
       }});

       p2.add(b1,BorderLayout.WEST);
       p2.add(b2,BorderLayout.EAST);

       c.add(p1);
       c.add(p2);
       f1.setSize(250, 150);
       f1.setVisible(true);
      }});
    order.add(showO2);
    
       // Shipment menu, h - Mnemonic
    JMenu shipment = new JMenu("Shipment");
    shipment.setMnemonic(KeyEvent.VK_H);
    menuBar.add(shipment);

    // Shipment->New Shipment , N - Mnemonic
    JMenuItem newSh = new JMenuItem("New Shipment", KeyEvent.VK_N);
    newSh.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
       final JFrame f1=new JFrame("New Shipment");
       f1.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
       Container c = f1.getContentPane();
       c.setLayout(new GridLayout(7,2));

       JPanel p1=new JPanel(new BorderLayout());
       JPanel p2=new JPanel(new BorderLayout());
       JPanel p3=new JPanel(new BorderLayout());
       JPanel p4=new JPanel(new BorderLayout());
       JPanel p5=new JPanel(new BorderLayout());
       JPanel p6=new JPanel(new BorderLayout());
       JPanel p7=new JPanel(new BorderLayout());
       
       JLabel l1=new JLabel("  Shipment ID");
       final JTextField t1=new JTextField(10);
       l1.setLabelFor(t1);
       p1.add(l1,BorderLayout.WEST);
       p1.add(t1,BorderLayout.EAST);
       JLabel l2=new JLabel("  Shipment Date");
       final JTextField t2=new JTextField(10);
       l2.setLabelFor(t2);
       p3.add(l2,BorderLayout.WEST);
       p3.add(t2,BorderLayout.EAST);
       JLabel l3=new JLabel("  Destination");
       final JTextField t3=new JTextField(10);
       l3.setLabelFor(t3);
       p4.add(l3,BorderLayout.WEST);
       p4.add(t3,BorderLayout.EAST);
       JLabel l4=new JLabel("  Building");
       final JTextField t4=new JTextField(10);
       l4.setLabelFor(t4);
       p5.add(l4,BorderLayout.WEST);
       p5.add(t4,BorderLayout.EAST);
       
       JLabel l5=new JLabel("  City");
       final JTextField t5=new JTextField(10);
       l5.setLabelFor(t5);
       p6.add(l5,BorderLayout.WEST);
       p6.add(t5,BorderLayout.EAST);
       
       JLabel l6=new JLabel("  Street");
       final JTextField t6=new JTextField(10);
       l6.setLabelFor(t6);
       p7.add(l6,BorderLayout.WEST);
       p7.add(t6,BorderLayout.EAST);

       JButton b1=new JButton("Ok");
       JButton b2=new JButton("Cancel");

       b2.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent actionEvent){ f1.setVisible(false); }});
       b1.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent actionEvent) {
       String s1=new String(t1.getText());
       String s2=new String(t2.getText());
       String s3=new String(t3.getText());
       String s4=new String(t4.getText());
       String s5=new String(t5.getText());
       String s6=new String(t6.getText());
       
         String new_Shipment = "INSERT INTO Shipment VALUES('" + s1 +"','" + s2 + "','"+ s3 +"','"+s4+"','"+ s5 +"','"+ s6 +"',null);";
       try
        {
           Statement stmt = con.createStatement();
           stmt.executeUpdate(new_Shipment); 
        }
        catch(SQLException e) {JOptionPane.showMessageDialog(null, e.getMessage());}
       t1.setText(" ");
       t2.setText(" ");
       t3.setText(" ");
       t4.setText(" ");
       t5.setText(" ");
       t6.setText(" ");
       }});

       p2.add(b1,BorderLayout.WEST);
       p2.add(b2,BorderLayout.EAST);

       c.add(p1);
       c.add(p3);
       c.add(p4);
       c.add(p5);
       c.add(p6);
       c.add(p7);
       c.add(p2);
       f1.setSize(250, 150);
       f1.setVisible(true);
      }});
    shipment.add(newSh);
     // Shipment->Delete Shipment , D - Mnemonic
    JMenuItem delSh = new JMenuItem("Delete Shipment", KeyEvent.VK_D);
    delSh.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
       final JFrame f1=new JFrame("Delete Shipment");
       f1.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
       Container c = f1.getContentPane();
       c.setLayout(new GridLayout(2,2));

       JPanel p1=new JPanel(new BorderLayout());
       JPanel p2=new JPanel(new BorderLayout());
       
       JLabel l1=new JLabel("  Shipment ID");
       final JTextField t1=new JTextField(10);
       l1.setLabelFor(t1);
       p1.add(l1,BorderLayout.WEST);
       p1.add(t1,BorderLayout.EAST);
     
       JButton b1=new JButton("Ok");
       JButton b2=new JButton("Cancel");

       b2.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent actionEvent){ f1.setVisible(false); }});
       b1.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent actionEvent) {
       String s1=new String(t1.getText());
       String Del="Delete from Shipment where (ShipID='"+s1+"');";
       try
        {
        Statement stmt = con.createStatement();
        int x=stmt.executeUpdate(Del);
        if(x==1)
        {
           JOptionPane.showMessageDialog(null, "Deleted"); 
        }
        else
        {
            JOptionPane.showMessageDialog(null, "No such Shipment to delete");
        }
    }
        catch(SQLException e) {JOptionPane.showMessageDialog(null, e.getMessage());}
       t1.setText(" ");
       }});

       p2.add(b1,BorderLayout.WEST);
       p2.add(b2,BorderLayout.EAST);

       c.add(p1);
       c.add(p2);
       f1.setSize(250, 150);
       f1.setVisible(true);
      }});
    shipment.add(delSh);
     // Shipment->Show all shipments in a special date, S - Mnemonic
    JMenuItem showSh = new JMenuItem("Show All Shipment On A Special Date", KeyEvent.VK_S);
    showSh.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
       final JFrame f1=new JFrame("Show Shipments on a Date");
       f1.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
       Container c = f1.getContentPane();
       c.setLayout(new GridLayout(2,2));

       JPanel p1=new JPanel(new BorderLayout());
       JPanel p2=new JPanel(new BorderLayout());
       
       JLabel l1=new JLabel("  Date");
       final JTextField t1=new JTextField(10);
       l1.setLabelFor(t1);
       p1.add(l1,BorderLayout.WEST);
       p1.add(t1,BorderLayout.EAST);
     
       JButton b1=new JButton("Ok");
       JButton b2=new JButton("Cancel");

       b2.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent actionEvent){ f1.setVisible(false); }});
       b1.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent actionEvent) {
       String s1=new String(t1.getText());
       try
        {
          Statement stmt2 = con.createStatement();
           ResultSet S = stmt2.executeQuery("Select ShipID from Shipment where(ShipDate=#"+s1+"#);");
            String s = new String();
            while(S.next()){
            String ShID = S.getString("ShipID");
            s = s +"\nShipment: " +ShID+"\n\n";
        } 

      
      if(s.equals("")) JOptionPane.showMessageDialog(null, "No Shipments");
      else JOptionPane.showMessageDialog(null,s);
        }
        catch(SQLException e) {JOptionPane.showMessageDialog(null, e.getMessage());}
       t1.setText(" ");
       }});

       p2.add(b1,BorderLayout.WEST);
       p2.add(b2,BorderLayout.EAST);

       c.add(p1);
       c.add(p2);
       f1.setSize(250, 150);
       f1.setVisible(true);
      }});
    shipment.add(showSh);
   


    


    
    // Repr->Exit, X - Mnemonic
    JMenuItem exitMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);
    exitMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) { System.exit(0); }});
    NewMenu.add(exitMenuItem);
    
    frame.setJMenuBar(menuBar);
    content.add(pan);
    frame.setSize(380, 380);
    frame.setVisible(true);

    
  }
  public static void main(String args[])throws SQLException,ClassNotFoundException,IOException
  {
   IManage pr=new IManage();
   Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");   
   String url= "jdbc:odbc:Project";
   con = DriverManager.getConnection(url,null,null);
  }
  private static void addRepr(String id , String Name, String DOB,String Tel)throws SQLException
  {
         
         String ADD = "INSERT INTO Repr VALUES('" + id +"' ,  '" +  Name + "' , '"+ DOB +"' , '"+Tel+"');";
         try
         {
         Statement stmt = con.createStatement();
         stmt.executeUpdate(ADD);
         }
        catch(SQLException e) {JOptionPane.showMessageDialog(null, e.getMessage());}
  }
    private static void InterationThroghRows(ResultSet S)throws SQLException{
        String s = new String();
        while(S.next()){
            String id = S.getString("EID");
            String name = S.getString("EName");
            String dob = S.getString("DOB");
            String tel = S.getString("Tel");
            
            s = s +"\neid: " +id+ "\nname: " +name+ "\ndob: " +dob +"\ntel: " +tel + "\n\n";
        } 

      
      if(s.equals("")) JOptionPane.showMessageDialog(null, "No Reprs");
      else JOptionPane.showMessageDialog(null,s);
    }
    private static void assRepr_toOrder(String reprId,String OID)throws SQLException
    {
        String ASS ="UPDATE Order1 set Repres='"+reprId+"' where (OID='"+OID+"');";
        try
         {
         Statement stmt = con.createStatement();
         stmt.executeUpdate(ASS);
         }
        catch(SQLException e) {JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
  public void actionPerformed(ActionEvent e)
  {
  }
}
