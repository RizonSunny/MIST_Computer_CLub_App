/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author RAZ
 */
///import com.mysql.jdbc.Connection;
import com.toedter.calendar.DateUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import java.sql.Connection;
import java.awt.Color;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

public class front extends javax.swing.JFrame {

    public static UserInfoWindow userInfoWindow;

    public front() {
        initComponents();
        mysettings();

    }

    public void mysettings() {
        // time_to_remind();
        contest.setVisible(false);
        member_prnt.setVisible(false);
        ADDMEM.setVisible(false);
        INFO.setVisible(false);
        set_cont.setVisible(false);
        con_tbl_pnl.setVisible(false);
        remove_pnl.setVisible(false);
        // rem_name.setText(null);
        rem_id.setText(null);
        //rem_batch.setText(null);
        //rem_dept.setText(null);
        past_tbl_pnl.setVisible(false);
        SCHEDULE_PNL.setVisible(false);
        sts_pnl.setVisible(false);

    }

    public Connection getconnection1() {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/raz", "root", "");
            // JOptionPane.showMessageDialog(null, "connected");

            return con;

        } catch (SQLException ex) {
            Logger.getLogger(front.class.getName()).log(Level.SEVERE, null, ex);
            //JOptionPane.showMessageDialog(null, "not connected");
            return null;

        }
    }

    public Connection getconnection2() {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/contest", "root", "");
            // JOptionPane.showMessageDialog(null, "connected");

            return con;

        } catch (SQLException ex) {
            Logger.getLogger(front.class.getName()).log(Level.SEVERE, null, ex);
            //JOptionPane.showMessageDialog(null, "not connected");
            return null;

        }
    }

    public boolean check_input() {
        if (member_name.getText() == null
                || member_batch.getText() == null
                || member_dept.getText() == null
                || member_id.getText() == null
                || member_mail.getText() == null) {
            return false;
        } else {
            try {
                Integer.parseInt(member_id.getText());
                return true;
            } catch (Exception ex) {
                return false;

            }

        }

    }

    public ArrayList<Member> getmemberlist() {
        ArrayList<Member> memlist = new ArrayList<Member>();
        Connection con = getconnection1();
        String query = "SELECT * FROM member";
        Statement st;
        ResultSet rst;
        try {

            st = con.createStatement();
            rst = st.executeQuery(query);
            Member mem;
            while (rst.next()) {
                mem = new Member(rst.getString("NAME"), rst.getInt("ID"), rst.getString("BATCH"), rst.getString("DEPT"), rst.getString("MAIL"), rst.getString("HANDLE"));
                memlist.add(mem);
            }
        } catch (SQLException ex) {
            Logger.getLogger(front.class.getName()).log(Level.SEVERE, null, ex);
        }
        return memlist;
    }

    public void show_member_jtable() {
        ArrayList<Member> list = getmemberlist();
        DefaultTableModel model = (DefaultTableModel) jTable1_mem.getModel();
        Object[] row = new Object[4];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getId();
            row[1] = list.get(i).getName();
            row[2] = list.get(i).getBatch();
            row[3] = list.get(i).getDept();
            model.addRow(row);
        }
    }

    public void show_contest_jtable(int m) {
        ArrayList<contest> list = getcontestList();
        DefaultTableModel model;

        if (m == 1) { /// running contest
            model = (DefaultTableModel) con_tbl.getModel();
            Object[] row = new Object[4];
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getcontest();
                row[1] = list.get(i).getstart();
                row[2] = list.get(i).getend();
                row[3] = list.get(i).getdate();

                ///
                String make_date = new String();

                make_date = row[3].toString() + 'T' + row[1] + ":00";
                Date now = new Date();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm"); /// need line

                Date startingtime = new Date();
                try {
                    startingtime = sdf.parse(make_date); /// need line

                } catch (ParseException ex) {
                    System.out.println("none");
                }

                Calendar cal = Calendar.getInstance();// need
                Date current = cal.getTime(); //need

                Date endtime = new Date(startingtime.getTime() + (Integer) row[2] * 60 * 60 * 1000);

                System.out.println(startingtime.toString());
                System.out.println(row[2]);
                System.out.println(endtime.toString());
                System.out.println();

                if (current.compareTo(startingtime) > 0 && current.compareTo(endtime) < 0) /// jake argument hisebe dicci tar result ta dekhabe
                {
                    model.addRow(row);
                }

                ///
            }
        } else if (m == 2) {
            model = (DefaultTableModel) con_tbl2.getModel();
            Object[] row = new Object[4];
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getcontest();
                row[1] = list.get(i).getstart();
                row[2] = list.get(i).getend();
                row[3] = list.get(i).getdate();

                ///
                String make_date = new String();

                make_date = row[3].toString() + 'T' + row[1] + ":00";
                Date now = new Date();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm"); /// need line

                Date startingtime = new Date();
                try {
                    startingtime = sdf.parse(make_date); /// need line

                } catch (ParseException ex) {

                }

                Calendar cal = Calendar.getInstance();// need
                Date current = cal.getTime(); //need

                Date endtime = new Date(startingtime.getTime() + (Integer) row[2] * 60 * 60 * 1000);

                if (current.compareTo(endtime) > 0) /// jake argument hisebe dicci tar result ta dekhabe
                {
                    model.addRow(row);
                } else {

                }
            }

        } else {
            model = (DefaultTableModel) con_tbl3.getModel();
            Object[] row = new Object[4];
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getcontest();
                row[1] = list.get(i).getstart();
                row[2] = list.get(i).getend();
                row[3] = list.get(i).getdate();

                ///
                String make_date = new String();

                make_date = row[3].toString() + 'T' + row[1] + ":00";
                Date now = new Date();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm"); /// need line

                Date startingtime = new Date();
                try {
                    startingtime = sdf.parse(make_date); /// need line

                } catch (ParseException ex) {

                }

                Calendar cal = Calendar.getInstance();// need
                Date current = cal.getTime(); //need

                Date endtime = new Date(startingtime.getTime() + (Integer) row[2] * 60 * 60 * 1000);

                if (current.compareTo(startingtime) < 0) /// jake argument hisebe dicci tar result ta dekhabe
                {
                    model.addRow(row);
                } else {

                }
            }

        }

    }

    public void thequery(String query, String mssg) {
        Connection con = null;
        Statement st = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/raz", "root", "");
            st = con.createStatement();
            if (st.executeUpdate(query) == 1) {
                DefaultTableModel model = (DefaultTableModel) jTable1_mem.getModel();
                model.setRowCount(0);
                JOptionPane.showMessageDialog(null, "DATA " + mssg + " SUCCESSFULLY");

            } else {
                JOptionPane.showMessageDialog(null, "DATA Not found");

            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public ArrayList<contest> getcontestList() {
        ArrayList<contest> conlist = new ArrayList<contest>();
        Connection con = getconnection2();
        String query = "SELECT * FROM contest_dtbs";
        Statement st;
        ResultSet rst;
        try {

            st = con.createStatement();
            rst = st.executeQuery(query);
            contest cnt;
            while (rst.next()) {
                cnt = new contest(rst.getString("CONTEST"), rst.getString("START"), rst.getInt("LENGTH"), rst.getString("DATE"));
                conlist.add(cnt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(front.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conlist;
    }

//    public void show_item(int index){
//        member_id.setText(Integer.toString(getmemberlist().get(index).getId()));
//        member_name.setText(getmemberlist().get(index).getName());
//    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        front = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        contest = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        member_prnt = new javax.swing.JPanel();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        ADDMEM = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        member_dept = new javax.swing.JTextField();
        member_name = new javax.swing.JTextField();
        member_id = new javax.swing.JTextField();
        member_batch = new javax.swing.JTextField();
        member_add_btn = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        member_mail = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        member_user = new javax.swing.JTextField();
        ADDMEM1 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        member_dept1 = new javax.swing.JTextField();
        member_name1 = new javax.swing.JTextField();
        member_id1 = new javax.swing.JTextField();
        member_batch1 = new javax.swing.JTextField();
        member_add_btn1 = new javax.swing.JButton();
        jButton25 = new javax.swing.JButton();
        member_mail1 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        member_user1 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        INFO = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1_mem = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        set_cont = new javax.swing.JPanel();
        set_con_name = new javax.swing.JLabel();
        contest_name = new javax.swing.JTextField();
        set_con_strt = new javax.swing.JLabel();
        contest_start = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        contest_end = new javax.swing.JTextField();
        date_chs = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        con_add_btn = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        con_tbl_pnl = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        con_tbl = new javax.swing.JTable();
        jButton17 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        remove_pnl = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        rem_id = new javax.swing.JTextField();
        rem_btn = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jButton20 = new javax.swing.JButton();
        past_tbl_pnl = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        con_tbl2 = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        jButton21 = new javax.swing.JButton();
        SCHEDULE_PNL = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        con_tbl3 = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        jButton22 = new javax.swing.JButton();
        sts_pnl = new javax.swing.JPanel();
        jButton14 = new javax.swing.JButton();
        search_handle = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jButton24 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        front.setBackground(new java.awt.Color(0, 102, 102));
        front.setLayout(null);

        jLabel3.setFont(new java.awt.Font("Broadway", 1, 48)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 153));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("MCC APPS");
        front.add(jLabel3);
        jLabel3.setBounds(60, 20, 380, 60);

        jButton1.setBackground(new java.awt.Color(72, 73, 109));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setText("CONTEST");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        front.add(jButton1);
        jButton1.setBounds(179, 112, 130, 47);

        jButton2.setBackground(new java.awt.Color(56, 86, 108));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton2.setText("MEMBERS");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        front.add(jButton2);
        jButton2.setBounds(178, 194, 130, 46);

        jButton3.setBackground(new java.awt.Color(61, 97, 96));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton3.setText("NOTICES");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        front.add(jButton3);
        jButton3.setBounds(178, 271, 130, 46);

        jButton4.setBackground(new java.awt.Color(70, 74, 123));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton4.setText("ABOUT");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        front.add(jButton4);
        jButton4.setBounds(180, 350, 130, 46);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/supermassive_black_hole_5k-2560x1600.jpg"))); // NOI18N
        front.add(jLabel2);
        jLabel2.setBounds(0, 0, 500, 500);

        getContentPane().add(front, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 500));

        contest.setBackground(new java.awt.Color(0, 102, 204));
        contest.setBorder(new javax.swing.border.MatteBorder(null));
        contest.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        contest.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        contest.setPreferredSize(new java.awt.Dimension(500, 500));
        contest.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton5.setBackground(new java.awt.Color(0, 102, 255));
        jButton5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton5.setText("ARCHIVE");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        contest.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 140, 134, 59));

        jButton6.setBackground(new java.awt.Color(65, 34, 204));
        jButton6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton6.setText("SCHEDULED");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        contest.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 310, 134, 60));

        jButton7.setBackground(new java.awt.Color(0, 153, 204));
        jButton7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton7.setText("RUNNING");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        contest.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 230, 134, 59));

        jButton8.setBackground(new java.awt.Color(0, 51, 51));
        jButton8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton8.setForeground(new java.awt.Color(0, 153, 153));
        jButton8.setText("BACK");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        contest.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 70, 40));

        jButton23.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButton23.setText("SET CONTEST");
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });
        contest.add(jButton23, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 70, 130, 50));

        getContentPane().add(contest, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        member_prnt.setBackground(new java.awt.Color(0, 153, 153));
        member_prnt.setPreferredSize(new java.awt.Dimension(500, 500));

        jButton9.setBackground(new java.awt.Color(0, 102, 102));
        jButton9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton9.setText("BACK");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setBackground(new java.awt.Color(153, 204, 255));
        jButton10.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jButton10.setText("STATISTICS");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setBackground(new java.awt.Color(153, 204, 255));
        jButton11.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jButton11.setText("ADD MEMBER");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setBackground(new java.awt.Color(153, 204, 255));
        jButton12.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jButton12.setText("CURRENT");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout member_prntLayout = new javax.swing.GroupLayout(member_prnt);
        member_prnt.setLayout(member_prntLayout);
        member_prntLayout.setHorizontalGroup(
            member_prntLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(member_prntLayout.createSequentialGroup()
                .addGroup(member_prntLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(member_prntLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(member_prntLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, member_prntLayout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(163, 163, 163))
            .addGroup(member_prntLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jButton9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        member_prntLayout.setVerticalGroup(
            member_prntLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(member_prntLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(126, 126, 126))
        );

        getContentPane().add(member_prnt, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 500));

        ADDMEM.setBackground(new java.awt.Color(0, 204, 153));
        ADDMEM.setPreferredSize(new java.awt.Dimension(500, 500));
        ADDMEM.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("NAME");
        ADDMEM.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, 100, 40));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("ID");
        ADDMEM.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 130, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("BATCH");
        ADDMEM.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 180, -1, -1));

        member_dept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                member_deptActionPerformed(evt);
            }
        });
        ADDMEM.add(member_dept, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 230, 220, 30));

        member_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                member_nameActionPerformed(evt);
            }
        });
        ADDMEM.add(member_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 70, 220, 30));

        member_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                member_idActionPerformed(evt);
            }
        });
        ADDMEM.add(member_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 120, 220, 30));
        ADDMEM.add(member_batch, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 170, 220, 30));

        member_add_btn.setText("ADD");
        member_add_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                member_add_btnActionPerformed(evt);
            }
        });
        ADDMEM.add(member_add_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 390, 120, 50));

        jButton13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton13.setText("back");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        ADDMEM.add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, 30));
        ADDMEM.add(member_mail, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 280, 220, 30));

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setText("DEPT");
        ADDMEM.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 230, 40, 20));
        ADDMEM.add(member_user, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 330, 220, 30));

        ADDMEM1.setBackground(new java.awt.Color(0, 153, 102));
        ADDMEM1.setPreferredSize(new java.awt.Dimension(500, 500));
        ADDMEM1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("NAME");
        ADDMEM1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, 100, 40));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("ID");
        ADDMEM1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 130, -1, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel19.setText("BATCH");
        ADDMEM1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 180, -1, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setText("MAIL");
        ADDMEM1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 280, 40, 20));

        member_dept1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                member_dept1ActionPerformed(evt);
            }
        });
        ADDMEM1.add(member_dept1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 230, 220, 30));

        member_name1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                member_name1ActionPerformed(evt);
            }
        });
        ADDMEM1.add(member_name1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 70, 220, 30));

        member_id1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                member_id1ActionPerformed(evt);
            }
        });
        ADDMEM1.add(member_id1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 120, 220, 30));
        ADDMEM1.add(member_batch1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 170, 220, 30));

        member_add_btn1.setText("ADD");
        member_add_btn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                member_add_btn1ActionPerformed(evt);
            }
        });
        ADDMEM1.add(member_add_btn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 390, 120, 50));

        jButton25.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton25.setText("back");
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });
        ADDMEM1.add(jButton25, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, 30));
        ADDMEM1.add(member_mail1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 280, 220, 30));

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel21.setText("DEPT");
        ADDMEM1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 230, 40, 20));
        ADDMEM1.add(member_user1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 330, 220, 30));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("HANDLE");
        ADDMEM1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 330, 80, 20));

        ADDMEM.add(ADDMEM1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(ADDMEM, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        INFO.setBackground(new java.awt.Color(204, 204, 255));
        INFO.setPreferredSize(new java.awt.Dimension(500, 500));
        INFO.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1_mem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NAME", "BATCH", "DEPT"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1_mem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1_memMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1_mem);

        INFO.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("student informtion");
        INFO.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 180, 40));

        jButton15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton15.setText("back");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        INFO.add(jButton15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 60, 30));

        jButton16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton16.setText("REMOVE");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        INFO.add(jButton16, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 20, -1, -1));

        getContentPane().add(INFO, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        set_cont.setBackground(new java.awt.Color(0, 153, 153));
        set_cont.setPreferredSize(new java.awt.Dimension(500, 500));

        set_con_name.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        set_con_name.setText("Contest Name");

        contest_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contest_nameActionPerformed(evt);
            }
        });

        set_con_strt.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        set_con_strt.setText("START TIME");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("LENGTH");

        date_chs.setDateFormatString("yyyy-MM-dd");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("DATE");

        con_add_btn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        con_add_btn.setText("ADD");
        con_add_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                con_add_btnActionPerformed(evt);
            }
        });

        jButton18.setBackground(new java.awt.Color(153, 153, 255));
        jButton18.setText("BACK");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout set_contLayout = new javax.swing.GroupLayout(set_cont);
        set_cont.setLayout(set_contLayout);
        set_contLayout.setHorizontalGroup(
            set_contLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(set_contLayout.createSequentialGroup()
                .addGroup(set_contLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(set_contLayout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addGroup(set_contLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(set_con_name, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                            .addComponent(set_con_strt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(30, 30, 30)
                        .addGroup(set_contLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(contest_name)
                            .addComponent(contest_end, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                            .addComponent(contest_start)
                            .addComponent(date_chs, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)))
                    .addGroup(set_contLayout.createSequentialGroup()
                        .addGap(174, 174, 174)
                        .addComponent(con_add_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(set_contLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(125, Short.MAX_VALUE))
        );
        set_contLayout.setVerticalGroup(
            set_contLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(set_contLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addGroup(set_contLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(set_con_name, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(contest_name, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(set_contLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(set_con_strt, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(contest_start, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(set_contLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(contest_end, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(set_contLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(date_chs, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                .addGap(74, 74, 74)
                .addComponent(con_add_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(82, 82, 82))
        );

        getContentPane().add(set_cont, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        con_tbl_pnl.setBackground(new java.awt.Color(153, 204, 255));
        con_tbl_pnl.setPreferredSize(new java.awt.Dimension(500, 500));

        con_tbl.setBackground(new java.awt.Color(153, 204, 255));
        con_tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NAME", "START", "LENGTH", "DATE"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(con_tbl);

        jButton17.setText("BACK");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("RUNNING CONTEST");

        javax.swing.GroupLayout con_tbl_pnlLayout = new javax.swing.GroupLayout(con_tbl_pnl);
        con_tbl_pnl.setLayout(con_tbl_pnlLayout);
        con_tbl_pnlLayout.setHorizontalGroup(
            con_tbl_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(con_tbl_pnlLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(con_tbl_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(con_tbl_pnlLayout.createSequentialGroup()
                        .addComponent(jButton17)
                        .addGap(61, 61, 61)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        con_tbl_pnlLayout.setVerticalGroup(
            con_tbl_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, con_tbl_pnlLayout.createSequentialGroup()
                .addGroup(con_tbl_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(con_tbl_pnlLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton17, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE))
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        getContentPane().add(con_tbl_pnl, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        remove_pnl.setBackground(new java.awt.Color(0, 153, 102));
        remove_pnl.setPreferredSize(new java.awt.Dimension(500, 500));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("ID");

        rem_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rem_idActionPerformed(evt);
            }
        });

        rem_btn.setBackground(new java.awt.Color(204, 255, 255));
        rem_btn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        rem_btn.setText("DELETE");
        rem_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rem_btnActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Copperplate Gothic Bold", 1, 20)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("REMOVE MEMBERS");

        jButton20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton20.setText("BACK");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout remove_pnlLayout = new javax.swing.GroupLayout(remove_pnl);
        remove_pnl.setLayout(remove_pnlLayout);
        remove_pnlLayout.setHorizontalGroup(
            remove_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, remove_pnlLayout.createSequentialGroup()
                .addGroup(remove_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(remove_pnlLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, remove_pnlLayout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                        .addComponent(rem_id, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(81, 81, 81))
            .addGroup(remove_pnlLayout.createSequentialGroup()
                .addGap(175, 175, 175)
                .addComponent(rem_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        remove_pnlLayout.setVerticalGroup(
            remove_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(remove_pnlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(remove_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(97, 97, 97)
                .addGroup(remove_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(remove_pnlLayout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                        .addGap(63, 63, 63))
                    .addGroup(remove_pnlLayout.createSequentialGroup()
                        .addComponent(rem_id, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(rem_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(171, Short.MAX_VALUE))
        );

        getContentPane().add(remove_pnl, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        past_tbl_pnl.setBackground(new java.awt.Color(153, 204, 255));
        past_tbl_pnl.setPreferredSize(new java.awt.Dimension(500, 500));

        con_tbl2.setBackground(new java.awt.Color(153, 204, 255));
        con_tbl2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NAME", "START", "LENGTH", "DATE"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(con_tbl2);

        jLabel16.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("PAST CONTEST");

        jButton21.setBackground(new java.awt.Color(0, 204, 153));
        jButton21.setText("BACK");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout past_tbl_pnlLayout = new javax.swing.GroupLayout(past_tbl_pnl);
        past_tbl_pnl.setLayout(past_tbl_pnlLayout);
        past_tbl_pnlLayout.setHorizontalGroup(
            past_tbl_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(past_tbl_pnlLayout.createSequentialGroup()
                .addGroup(past_tbl_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(past_tbl_pnlLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(past_tbl_pnlLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        past_tbl_pnlLayout.setVerticalGroup(
            past_tbl_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, past_tbl_pnlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(past_tbl_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addGroup(past_tbl_pnlLayout.createSequentialGroup()
                        .addComponent(jButton21)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(past_tbl_pnl, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        SCHEDULE_PNL.setBackground(new java.awt.Color(204, 204, 255));
        SCHEDULE_PNL.setPreferredSize(new java.awt.Dimension(500, 500));

        con_tbl3.setBackground(new java.awt.Color(153, 204, 255));
        con_tbl3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NAME", "START", "LENGTH", "DATE"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(con_tbl3);

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("SCHEDULE");

        jButton22.setText("BACK");
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout SCHEDULE_PNLLayout = new javax.swing.GroupLayout(SCHEDULE_PNL);
        SCHEDULE_PNL.setLayout(SCHEDULE_PNLLayout);
        SCHEDULE_PNLLayout.setHorizontalGroup(
            SCHEDULE_PNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SCHEDULE_PNLLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(SCHEDULE_PNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SCHEDULE_PNLLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(SCHEDULE_PNLLayout.createSequentialGroup()
                        .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        SCHEDULE_PNLLayout.setVerticalGroup(
            SCHEDULE_PNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SCHEDULE_PNLLayout.createSequentialGroup()
                .addGroup(SCHEDULE_PNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                    .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        getContentPane().add(SCHEDULE_PNL, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        sts_pnl.setBackground(new java.awt.Color(0, 153, 153));
        sts_pnl.setPreferredSize(new java.awt.Dimension(500, 500));

        jButton14.setText("BACK");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        search_handle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_handleActionPerformed(evt);
            }
        });

        jLabel9.setText("handle");

        jButton24.setText("SUBMIT");
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout sts_pnlLayout = new javax.swing.GroupLayout(sts_pnl);
        sts_pnl.setLayout(sts_pnlLayout);
        sts_pnlLayout.setHorizontalGroup(
            sts_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sts_pnlLayout.createSequentialGroup()
                .addGroup(sts_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sts_pnlLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jButton14))
                    .addGroup(sts_pnlLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(search_handle, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(sts_pnlLayout.createSequentialGroup()
                        .addGap(201, 201, 201)
                        .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(128, Short.MAX_VALUE))
        );
        sts_pnlLayout.setVerticalGroup(
            sts_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sts_pnlLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(104, 104, 104)
                .addGroup(sts_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(search_handle, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(76, 76, 76)
                .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(178, Short.MAX_VALUE))
        );

        getContentPane().add(sts_pnl, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        front.setVisible(false);
        member_prnt.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        member_prnt.setVisible(false);
        front.setVisible(false);
        contest.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            String url = "http://computer-club.mist.ac.bd/category/notice/";
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        } catch (IOException ex) {
            Logger.getLogger(front.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        member_prnt.setVisible(false);
        front.setVisible(true);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        member_prnt.setVisible(false);
        ADDMEM.setVisible(false);
        INFO.setVisible(true);
        DefaultTableModel mdt = (DefaultTableModel) jTable1_mem.getModel();
        while (mdt.getRowCount() > 0) {
            for (int i = 0; i < mdt.getRowCount(); i++) {
                mdt.removeRow(i);

            }
        }
        show_member_jtable();
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            String url = "http://computer-club.mist.ac.bd/welcome-to-mist/";
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        } catch (IOException ex) {
            Logger.getLogger(front.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        contest.setVisible(false);
        front.setVisible(true);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        contest.setVisible(false);
        con_tbl_pnl.setVisible(true);
        DefaultTableModel mdt = (DefaultTableModel) con_tbl.getModel();
        while (mdt.getRowCount() > 0) {
            for (int i = 0; i < mdt.getRowCount(); i++) {
                mdt.removeRow(i);

            }
        }
        show_contest_jtable(1);


    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:

        contest.setVisible(false);
        SCHEDULE_PNL.setVisible(true);
        DefaultTableModel mdt = (DefaultTableModel) con_tbl3.getModel();
        while (mdt.getRowCount() > 0) {
            for (int i = 0; i < mdt.getRowCount(); i++) {
                mdt.removeRow(i);

            }
        }
        show_contest_jtable(3);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void member_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_member_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_member_nameActionPerformed

    private void member_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_member_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_member_idActionPerformed

    private void member_deptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_member_deptActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_member_deptActionPerformed

    private void member_add_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_member_add_btnActionPerformed
        if (check_input()) {
            try {
                Connection con = getconnection1();
                PreparedStatement ps = con.prepareStatement("INSERT INTO member(name,id,batch,dept,mail,handle)" + "values(?,?,?,?,?,?)");
                ps.setString(1, member_name.getText());
                ps.setString(2, member_id.getText());
                ps.setString(3, member_batch.getText());
                ps.setString(4, member_dept.getText());
                ps.setString(5, member_mail.getText());
                ps.setString(6, member_user.getText());

                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "Data Inserted");

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "one or more info missing");
        }

        member_batch.setText(null);
        member_dept.setText(null);
        member_id.setText(null);
        member_mail.setText(null);
        member_name.setText(null);
        member_user.setText(null);
//        System.out.println(member_name.getText());
//        System.out.println(member_id.getText());
//        System.out.println(member_batch.getText());
//        System.out.println(member_dept.getText());


    }//GEN-LAST:event_member_add_btnActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed

        sts_pnl.setVisible(true);
        member_prnt.setVisible(false);


    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        member_prnt.setVisible(false);
        ADDMEM.setVisible(true);
        INFO.setVisible(false);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        ADDMEM.setVisible(false);
        member_prnt.setVisible(true);
        member_id.setText(null);
        member_name.setText(null);
        member_batch.setText(null);
        member_dept.setText(null);


    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        INFO.setVisible(false);
        member_prnt.setVisible(true);


    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        //String query= "DELETE FROM `member` WHERE 1"+;

        INFO.setVisible(false);
        remove_pnl.setVisible(true);
        rem_id.setText(null);

    }//GEN-LAST:event_jButton16ActionPerformed

    private void jTable1_memMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1_memMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1_memMouseClicked

    private void con_add_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_con_add_btnActionPerformed

        if (contest_name.getText() == null
                || contest_start.getText() == null
                || contest_end.getText() == null
                || date_chs.getDate() == null) {
            JOptionPane.showMessageDialog(null, "PLEASE INSERT DATA ????? ");
        } else {
            try {
                Connection con = getconnection2();
                PreparedStatement ps = con.prepareStatement("INSERT INTO contest_dtbs(contest,start,length,DATE)" + "values(?,?,?,?)");
                ps.setString(1, contest_name.getText());
                ps.setString(2, contest_start.getText());
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
                String date = dateformat.format(date_chs.getDate());
                ps.setString(3, contest_end.getText());
                ps.setString(4, date);

                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "Data Inserted");

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
        contest_name.setText(null);
        contest_start.setText(null);
        contest_end.setText(null);
        date_chs.setDate(null);


    }//GEN-LAST:event_con_add_btnActionPerformed

    int secpas = 0;
    Timer timer = new Timer();
    TimerTask timertask = new TimerTask() {

        public void run() {
            secpas++;
            // System.out.println(secpas);
            if (secpas % 60 == 0) {
                //System.out.println();
                time_to_remind();
                //System.out.println("5 mnt hoice");
            }

        }
    };

    public void start() {
        timer.scheduleAtFixedRate(timertask, 1000, 1000);

    }

    public void time_to_remind() {
        //System.out.println("call hoice");
        /// timer start
        int flag = 0;
        ArrayList<Member> list = getmemberlist();

        String temp = new String();

        ArrayList<contest> listt = getcontestList();
        // DefaultTableModel model;
        //   model = (DefaultTableModel) con_tbl.getModel();
        Object[] row = new Object[4];
        String nnn = new String();
        Integer ll = 0;
        for (int i = 0; i < listt.size(); i++) {

            row[1] = listt.get(i).getstart();
            nnn = listt.get(i).getcontest();
            ll = listt.get(i).getend();
            row[3] = listt.get(i).getdate();

            ///
            String make_date = new String();

            make_date = row[3].toString() + 'T' + row[1] + ":00";
            Date now = new Date();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm"); /// need line

            Date startingtime = new Date();
            try {
                startingtime = sdf.parse(make_date); /// need line

            } catch (ParseException ex) {
                System.out.println("Incorrect Date");
            }

            Calendar cal = Calendar.getInstance();// need
            Date current = cal.getTime(); //need

            Date endtime1 = new Date(startingtime.getTime() - 30 * 60 * 1000);
            Date endtime2 = new Date(startingtime.getTime() - 20 * 60 * 1000);

            if (current.compareTo(endtime1) > 0 && current.compareTo(endtime2) < 0) /// jake argument hisebe dicci tar result ta dekhabe
            {
                System.out.println("done");
                flag = 1;

            }
            if (flag == 1) {
                break;
            }

            ///
        }

        /// anci--
        String lll = (String) ll.toString();
        if (flag == 1) {
            for (int i = 0; i < list.size(); i++) {
                temp = list.get(i).getMail();
                String subject = new String("MCC Contest Reminder");
                String message = new String("Dear " + list.get(i).getName() + ", a Contest has been scheduled in between 30 minutes. \nCOntest Name- " + nnn + ". \nIts " + lll + " hours long Contest\nBe prepare\nHappy Coding");
                String user = "mist.cc2744@gmail.com";
                String pass = "201614044";

                if (temp.length() != 0) {
                    SendMail.send(temp, subject, message, user, pass);
                }

            }
        }

        ///timer end
    }


    private void contest_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contest_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_contest_nameActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here:
        con_tbl_pnl.setVisible(false);
        contest.setVisible(true);


    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here:
        set_cont.setVisible(false);
        contest.setVisible(true);
        contest_name.setText(null);
        contest_end.setText(null);
        contest_start.setText(null);
        date_chs.setDate(null);


    }//GEN-LAST:event_jButton18ActionPerformed

    private void rem_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rem_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rem_idActionPerformed

    private void rem_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rem_btnActionPerformed
        // TODO add your handling code here:

        if (rem_id.getText() == null) {
            JOptionPane.showMessageDialog(null, "PLEASE INSERT DATA ?????");
        } else {

            String query = "DELETE FROM `member` WHERE id = " + rem_id.getText();
            thequery(query, "deleted");
            //rem_name.setText(null);
            //rem_batch.setText(null);
            rem_id.setText(null);
            //rem_dept.setText(null);
        }
    }//GEN-LAST:event_rem_btnActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed

        member_prnt.setVisible(false);
        ADDMEM.setVisible(false);
        INFO.setVisible(true);
        remove_pnl.setVisible(false);
        DefaultTableModel mdt = (DefaultTableModel) jTable1_mem.getModel();
        while (mdt.getRowCount() > 0) {
            for (int i = 0; i < mdt.getRowCount(); i++) {
                mdt.removeRow(i);

            }
        }
        show_member_jtable();
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        contest.setVisible(false);
        past_tbl_pnl.setVisible(true);
        DefaultTableModel mdt = (DefaultTableModel) con_tbl2.getModel();
        while (mdt.getRowCount() > 0) {
            for (int i = 0; i < mdt.getRowCount(); i++) {
                mdt.removeRow(i);

            }
        }
        show_contest_jtable(2);


    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        // TODO add your handling code here:

        past_tbl_pnl.setVisible(false);
        contest.setVisible(true);
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        // TODO add your handling code here:
        SCHEDULE_PNL.setVisible(false);
        contest.setVisible(true);
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        contest.setVisible(false);
        set_cont.setVisible(true);
        contest_name.setText(null);
        contest_start.setText(null);
        contest_end.setText(null);
        date_chs.setCalendar(null);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton23ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:

        sts_pnl.setVisible(false);
        member_prnt.setVisible(true);
    }//GEN-LAST:event_jButton14ActionPerformed

    private void search_handleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_handleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_search_handleActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed

        ArrayList<Member> list = getmemberlist();

        String hndletemp = search_handle.getText();
        search_handle.setText("");

        Object[] row = new Object[5];
        for (int i = 0; i < list.size(); i++) {

            String temp = list.get(i).gethandle();

            if (temp.compareTo(hndletemp) == 0) {

                try {
                    UserInfoWindow dd = new UserInfoWindow();
                    String tttt = dd.loadUserInfo(hndletemp);
                    // System.out.println(tttt);
                    // sts_pnl.setVisible(false);
                    //last.setVisible(true);
                    //see_sts.append(tttt);
                    JOptionPane.showMessageDialog(null, tttt);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Wrong handle");
                    Logger.getLogger(front.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
        }
    }//GEN-LAST:event_jButton24ActionPerformed

    private void member_dept1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_member_dept1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_member_dept1ActionPerformed

    private void member_name1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_member_name1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_member_name1ActionPerformed

    private void member_id1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_member_id1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_member_id1ActionPerformed

    private void member_add_btn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_member_add_btn1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_member_add_btn1ActionPerformed

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton25ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws ClassNotFoundException, IllegalAccessException, InstantiationException, UnsupportedLookAndFeelException {
        // UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
//       jButton4.setOpaque(false);
//       jButton4.setContentAreaFilled(false); //to make the content area transparent
//       jButton4.setBorderPainted(false); //to make the borders transparent

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
            java.util.logging.Logger.getLogger(front.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(front.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(front.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(front.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new jPanel2().setVisible(false);
                front ff = new front();
                ff.setVisible(true);

                ff.start();
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ADDMEM;
    private javax.swing.JPanel ADDMEM1;
    private javax.swing.JPanel INFO;
    private javax.swing.JPanel SCHEDULE_PNL;
    private javax.swing.JButton con_add_btn;
    private javax.swing.JTable con_tbl;
    private javax.swing.JTable con_tbl2;
    private javax.swing.JTable con_tbl3;
    private javax.swing.JPanel con_tbl_pnl;
    private javax.swing.JPanel contest;
    private javax.swing.JTextField contest_end;
    private javax.swing.JTextField contest_name;
    private javax.swing.JTextField contest_start;
    private com.toedter.calendar.JDateChooser date_chs;
    private javax.swing.JPanel front;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1_mem;
    private javax.swing.JButton member_add_btn;
    private javax.swing.JButton member_add_btn1;
    private javax.swing.JTextField member_batch;
    private javax.swing.JTextField member_batch1;
    private javax.swing.JTextField member_dept;
    private javax.swing.JTextField member_dept1;
    private javax.swing.JTextField member_id;
    private javax.swing.JTextField member_id1;
    private javax.swing.JTextField member_mail;
    private javax.swing.JTextField member_mail1;
    private javax.swing.JTextField member_name;
    private javax.swing.JTextField member_name1;
    private javax.swing.JPanel member_prnt;
    private javax.swing.JTextField member_user;
    private javax.swing.JTextField member_user1;
    private javax.swing.JPanel past_tbl_pnl;
    private javax.swing.JButton rem_btn;
    private javax.swing.JTextField rem_id;
    private javax.swing.JPanel remove_pnl;
    private javax.swing.JTextField search_handle;
    private javax.swing.JLabel set_con_name;
    private javax.swing.JLabel set_con_strt;
    private javax.swing.JPanel set_cont;
    private javax.swing.JPanel sts_pnl;
    // End of variables declaration//GEN-END:variables
}
