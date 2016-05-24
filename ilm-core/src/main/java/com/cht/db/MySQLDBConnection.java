package com.cht.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cht.entity.AccountEntity;
import com.cht.entity.EventEntity;
import com.cht.entity.FillinEntity;
import com.cht.entity.LoginLogEntity;
import com.cht.entity.OperationLogEntity;
import com.cht.entity.RegulationEntity;
import com.cht.entity.RelativeEntity;
import com.cht.entity.RuleChainEntity;
import com.cht.entity.RuleEntity;
import com.cht.util.MyPropertyReader;

public class MySQLDBConnection {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Connection con = null; // Database objects
    
    public Connection getConnection() {
        if (con != null) {
            return con;
        }
        return con;
    }
    
    public ResultSet query(Connection con) {
        String sql = "SELECT * FROM AccountReport Order by OwnerUnit";
        
        Statement stmt = null;
        try {
            stmt = con.createStatement();
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        
        return rs;
    }
    
    public List<RegulationEntity> queryRegulation() {
        List<RegulationEntity> list = new ArrayList<>();
        
        String sql = "SELECT * FROM regulation";
        
        Statement stmt = null;
        try {
            stmt = con.createStatement();
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                int index = 1;
                RegulationEntity entity = new RegulationEntity();
                entity.setNo(rs.getString(index++));
                entity.setCriteria(rs.getString(index++));
                entity.setName(rs.getString(index++));
                entity.setDesc(rs.getString(index++));
                entity.setApplicable(rs.getString(index++));
                entity.setPass(rs.getString(index++));
                entity.setNonexec(rs.getString(index++));
                entity.setViolate(rs.getString(index++));
                entity.setProperty(rs.getString(index++));
                
                list.add(entity);
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        } finally {
            close(con);
        }
        
        return list;
    }   
    
    public List<RuleEntity> queryRule(String appliedRegu) {
        List<RuleEntity> list = new ArrayList<>();
        
        String sql = "SELECT no, name, description, appliedRegu, property "
                + "FROM rule ";
        
        if (!StringUtils.isEmpty(appliedRegu)) {
            sql += " WHERE appliedRegu = ?";
        }
        
        System.out.println(sql);
        System.out.println(appliedRegu);
        
        PreparedStatement stmt = null;
        try {
            
            stmt = con.prepareStatement(sql);
            
            if (!StringUtils.isEmpty(appliedRegu)) {
                stmt.setString(1, appliedRegu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        try {
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                int index = 1;
                RuleEntity entity = new RuleEntity();
                entity.setNo(rs.getString(index++));               
                entity.setName(rs.getString(index++));
                entity.setDesc(rs.getString(index++));
                entity.setAppliedRegu(rs.getString(index++));
                entity.setProperty(rs.getString(index++));
                
                list.add(entity);
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        } finally {
            close(con);
        }
        
        return list;
    }
    
    public List<RuleChainEntity> queryRuleChain() {
        List<RuleChainEntity> list = new ArrayList<>();
        
        String sql = "SELECT rule_chain_id, name, source1_id, source1_name, source2_id, source2_name, oper, threshold "
                + "FROM rule_chain ";
        
        PreparedStatement stmt = null;
        try {
            
            stmt = con.prepareStatement(sql);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        try {
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {                
                RuleChainEntity entity = new RuleChainEntity();
                
                entity.setId(rs.getString("rule_chain_id"));               
                entity.setName(rs.getString("name"));
                entity.setOper(rs.getString("oper"));
                entity.setOperand1(rs.getString("source1_id"));
                entity.setOperand1Name(rs.getString("source1_name"));
                entity.setOperand2(rs.getString("source2_id"));
                entity.setOperand2Name(rs.getString("source2_name"));
                entity.setThreshold(rs.getString("threshold"));
                
                list.add(entity);
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        } finally {
            close(con);
        }
        
        return list;
    }
    
    public List<LoginLogEntity> queryLoginLog(Date[] period) {
        List<LoginLogEntity> list = new ArrayList<>();
        
        String sql = "SELECT id, userid, datetime, action, ip "
                + "FROM loginlog ";
        
        if (period.length == 2) {
            sql += " WHERE datetime BETWEEN ? AND ? ";
        }
        
        sql += " order by datetime";
        
        logger.debug("sql: {}", sql);
        logger.debug(period[0] + " " + period[1]);
        
        PreparedStatement stmt = null;
        try {
            
            stmt = con.prepareStatement(sql);
            
            if (period.length == 2) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                stmt.setString(1, sdf.format(period[0]));
                stmt.setString(2, sdf.format(period[1]));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        try {
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                int index = 1;
                LoginLogEntity entity = new LoginLogEntity();
                entity.setId(rs.getString(index++));
                entity.setUserid(rs.getString(index++));
                entity.setDatetime(rs.getString(index++));
                entity.setAction(rs.getString(index++));
                entity.setIp(rs.getLong(index++));
                
                list.add(entity);
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        } finally {
            close(con);
        }
        
        return list;
    }
    
    public List<OperationLogEntity> queryOperationLog(Date[] period) {
        List<OperationLogEntity> list = new ArrayList<>();
        
        String sql = "SELECT userid, datetime, action, ip "
                + "FROM operationlog ";
        
        if (period.length == 2) {
            sql += " WHERE datetime BETWEEN ? AND ? ";
        }
        
        sql += " order by datetime";
        
        logger.debug("sql: {}", sql);
        logger.debug(period[0] + " " + period[1]);
        
        PreparedStatement stmt = null;
        try {
            
            stmt = con.prepareStatement(sql);
            
            if (period.length == 2) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                stmt.setString(1, sdf.format(period[0]));
                stmt.setString(2, sdf.format(period[1]));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        try {
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                int index = 1;
                OperationLogEntity entity = new OperationLogEntity();
                entity.setUserId(rs.getString(index++));
                entity.setDatetime(rs.getString(index++));
                entity.setAction(rs.getString(index++));
                entity.setIp(rs.getLong(index++));
                
                list.add(entity);
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        } finally {
            close(con);
        }
        
        return list;
    }
    
    /**
     * @precondition: has regulations in table.
     * post condition: update result
     * 
     * @param oper : String {0, 1}
     * @param ids array of id 
     * 
     */    
    public void updateRegulationApplicable(String oper, String[] ids) {
        String sql = "UPDATE regulation SET applicable = ? ";
        
        if (ids.length > 0) {
            String strIN = StringUtils.join(ids, "', '");
            sql += " WHERE no IN ('" + strIN + "')";
        }
        
        logger.info("oper: {}", oper);
        logger.info(sql);
        
        PreparedStatement stmt = null;
        try {
            
            stmt = con.prepareStatement(sql);
            
            if (ids.length > 0) {
                if (oper.equals("apply")) {
                    stmt.setInt(1, 1);
                } else {
                    stmt.setInt(1, 0);
                }
            }
            
            int cnt = stmt.executeUpdate();
            
            logger.info(">>>{} rows affected.", cnt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }   
    
    public void updateRegulationPass(int on, String[] ids) {
        String sql = "UPDATE regulation SET pass = ? ";
        
        if (ids.length > 0) {
            String strIN = StringUtils.join(ids, "', '");
            sql += " WHERE no IN ('" + strIN + "')";
        }
                
        logger.info(sql);
        
        PreparedStatement stmt = null;
        try {
            
            stmt = con.prepareStatement(sql);
            
            if (ids.length > 0) {
                stmt.setInt(1, on);
            }            
            
            int cnt = stmt.executeUpdate();
            
            logger.info(">>>{} rows affected.", cnt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }  
    
    public void updateRegulationVio(int on, String[] ids) {
        String sql = "UPDATE regulation SET violate = ? ";
        
        if (ids.length > 0) {
            String strIN = StringUtils.join(ids, "', '");
            sql += " WHERE no IN ('" + strIN + "')";
        }
                
        logger.info(sql);
        
        PreparedStatement stmt = null;
        try {
            
            stmt = con.prepareStatement(sql);
            
            if (ids.length > 0) {
                stmt.setInt(1, on);
            }
            
            int cnt = stmt.executeUpdate();
            
            logger.info(">>>{} rows affected.", cnt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } 
    
    public MySQLDBConnection() {
        MyPropertyReader pr = new MyPropertyReader();
        
        String user = "";
        String adv = "";
        try {
            user = pr.getPropValues(MyPropertyReader.DB_USER);
            adv = pr.getPropValues(MyPropertyReader.DB_ADV);
        } catch (IOException e1) {
             logger.error(e1.getMessage());
        }
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            // 註冊driver
            con = DriverManager.getConnection("jdbc:mysql://localhost/account?useUnicode=true&characterEncoding=UTF-8", user, adv);
            // 取得connection

            // jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=Big5
            // localhost是主機名,test是database名
            // useUnicode=true&characterEncoding=Big5使用的編碼

        } catch (ClassNotFoundException e) {
            logger.error("DriverClassNotFound :" + e.toString());
        }// 有可能會產生sqlexception
        catch (SQLException x) {
            logger.error("Exception :" + x.toString());
        }

    }
    
    
 
    public List<FillinEntity> queryFillin(String rule) {
        List<FillinEntity> datalist = new ArrayList<FillinEntity>();        
        
        String sql = "SELECT submitter, datetime, rule, attachment, action "
                    + "FROM fillin "
                    + "WHERE rule = ? "
                    + "ORDER BY DATETIME";
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sql);
            
            int index = 1;
            stmt.setString(index++, rule);
            
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        
        try {
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                int index = 1;
                FillinEntity entity = new FillinEntity();
                entity.setSubmitter(rs.getString(index++));               
                entity.setDatetime(rs.getString(index++));
                entity.setRule(rs.getString(index++));
                entity.setAttachment(rs.getString(index++));
                entity.setAction(rs.getString(index++));
                
                datalist .add(entity);
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        } finally {
            close(con);
        }
        
        return datalist;
    }

    public void insertFillin(FillinEntity fillin) {        
        
        String sql = "INSERT INTO fillin (submitter, datetime, rule, attachment, action) "
                + "VALUES (?, NOW(), ?, ?, ?) ";
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sql);
            
            int index = 1;
            stmt.setString(index++, fillin.getSubmitter());
            stmt.setString(index++, fillin.getRule());
            stmt.setString(index++, fillin.getAttachment());
            stmt.setString(index++, fillin.getAction());
            
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        
        try {
            stmt.execute();
        } catch (SQLException e) {
            logger.error(e.toString());
        } finally {
            
        }
        
        
        // update regu
        if (fillin.getAction().equals("pass")) {
            sql = "update regulation set pass = 1 and violate = 0 where no = 'A.7.1.1'";
        } else {
            sql = "update regulation set pass = 0 and violate = 1 where no = 'A.7.1.1'";
        }
        Statement stmt2;
        try {
            stmt2 = con.createStatement();
            stmt2.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        close(con);
        
    }
    
    public void insertRegulation(RegulationEntity newItem) {        
        
         String sql = "INSERT INTO regulation (no, name, description, applicable) "
                + "VALUES (?, ?, ?, ?) ";
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sql);
            
            int index = 1; 
            stmt.setString(index++, newItem.getNo());
            stmt.setString(index++, newItem.getName());
            stmt.setString(index++, newItem.getDesc()); // {[o1: data, oper: in, o2: cmp]}
            stmt.setString(index++, "1");
            
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        
        try {
            stmt.execute();
        } catch (SQLException e) {
            logger.error(e.toString());
        } finally {
            
        }        
        
        close(con);
        
    }
    
    public void insertRuleChain(RuleChainEntity newItem) {        
        
        String sql = "INSERT INTO rule_chain (rule_chain_id, name, source1_id, source1_name, source2_id, source2_name, oper, threshold )"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?) ";
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sql);
            
            int index = 1;
            stmt.setString(index++, newItem.getId());
            stmt.setString(index++, newItem.getName());
            stmt.setString(index++, newItem.getOperand1());
            stmt.setString(index++, newItem.getOperand1Name());
            stmt.setString(index++, newItem.getOperand2());
            stmt.setString(index++, newItem.getOperand2Name());
            stmt.setString(index++, newItem.getOper());
            stmt.setString(index++, newItem.getThreshold());            
            
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        
        try {
            stmt.execute();
        } catch (SQLException e) {
            logger.error(e.toString());
        } finally {
            
        }        
        
        // insert {Regulation}
        sql = "INSERT INTO regulation (no, name, description, applicable )"
                + "VALUES (?, ?, ?, ?) ";
        try {
            stmt = con.prepareStatement(sql);
            
            int index = 1;
            stmt.setString(index++, newItem.getId());
            stmt.setString(index++, newItem.getName());
            stmt.setString(index++, newItem.getName());
            stmt.setInt(index++, 1);
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        
        try {
            stmt.execute();
        } catch (SQLException e) {
            logger.error(e.toString());
        } finally {
            
        }
        
        close(con);
        
    }
    
    public void deleteRuleChain(RuleChainEntity newItem) {        
        
        String sql = "DELETE FROM rule_chain WHERE rule_chain_id = ? ";
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sql);
            
            int index = 1;
            stmt.setString(index++, newItem.getId());
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        
        try {
            stmt.execute();
        } catch (SQLException e) {
            logger.error(e.toString());
        }       
        
        // insert {Regulation}
        sql = "DELETE FROM regulation WHERE no = ?";
        try {
            stmt = con.prepareStatement(sql);
            
            int index = 1;
            stmt.setString(index++, newItem.getId());
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        
        try {
            stmt.execute();
        } catch (SQLException e) {
            logger.error(e.toString());
        } finally {
            close(con);
        }        
    }
    
    public void deletePlugin (RegulationEntity item) {        
        
        String sql = "DELETE FROM regulation WHERE no = ? ";
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sql);
            
            int index = 1;
            stmt.setString(index++, item.getNo());
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        
        try {
            stmt.execute();
        } catch (SQLException e) {
            logger.error(e.toString());        
        } finally {
            close(con);
        }        
    }
    
    public void insertEvent(EventEntity newItem) {        
        
        String sql = "INSERT INTO event (name, content, ruleid, severity, userid, username, evt_time, ip) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?) ";
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sql);
            
            int index = 1;
            stmt.setString(index++, newItem.getName());
            stmt.setString(index++, newItem.getContent());
            stmt.setString(index++, newItem.getRuleId());
            stmt.setInt(index++, newItem.getSeverity());
            stmt.setString(index++, newItem.getUserId());
            stmt.setString(index++, newItem.getUserName());
            stmt.setString(index++, newItem.getEvtTime());
            stmt.setString(index++, newItem.getIp());
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        
        try {
            stmt.execute();
        } catch (SQLException e) {
            logger.error(e.toString());
        } finally {
            
        }        
        
//        close(con);
        
    }
    
    public void close(Connection con) {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            logger.error("Exception :" + e.toString());
        }
    }

    /* (non-Javadoc)
     * @see com.cht.db.AuxiliaryQuery#queryAccount()
     */
    public List<String> queryAccount() {
        List<String> datalist = new ArrayList<String>();
        
        String sql = "SELECT accountname "
                    + "FROM accountdata ";
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sql);                   
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        
        try {
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                int index = 1;
                datalist.add(rs.getString(index++));
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        } finally {
            close(con);
        }
        
        return datalist;        
    }
    
    /* (non-Javadoc)
     * @see com.cht.db.AuxiliaryQuery#queryAccount()
     */
    public List<AccountEntity> queryAccountEntity() {
        List<AccountEntity> datalist = new ArrayList<>();
        
        String sql = "SELECT accountname, hostname "
                    + "FROM accountdata ";
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sql);
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        
        try {
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                AccountEntity acc = new AccountEntity();
                int index = 1;
                acc.setAccount(rs.getString(index++));
                acc.setHostName(rs.getString(index++));
                
                datalist.add(acc);
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        } finally {
            close(con);
        }
        
        return datalist;        
    }
    
    public List<RelativeEntity> queryRelative() {
        List<RelativeEntity> datalist = new ArrayList<>();
        
        String sql = "SELECT userid, relatives "
                    + "FROM relative ";
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sql);
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        
        try {
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                RelativeEntity acc = new RelativeEntity();
                int index = 1;
                acc.setUserid(rs.getString(index++));
                acc.setRelatives(rs.getString(index++));
                
                datalist.add(acc);
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        } finally {
            close(con);
        }
        
        return datalist;        
    }
    
    public Set<String> queryHoliday() {
        Set<String> datalist = new HashSet<String>();
        
        String sql = "SELECT holiday "
                    + "FROM holiday ";
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sql);                   
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        
        try {
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                int index = 1;
                datalist.add(rs.getString(index++));
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        } finally {
            close(con);
        }
        
        return datalist;        
    }  
    
    public List<EventEntity> queryEvent(String ruleId, String date1, String date2) {
        List<EventEntity> datalist = new ArrayList<EventEntity>();
        
        String sql = "SELECT userid, username, name, evt_time, severity, ip, content "
                    + "FROM event "
                    + "WHERE ruleid = ? AND evt_time between ? AND ? " 
                    + "ORDER BY evt_time ";
        
        logger.debug(sql);
        
        PreparedStatement stmt = null;
        try {
            int i = 1;
            stmt = con.prepareStatement(sql);
            stmt.setString(i++, ruleId);
            logger.debug(">>>{}", ruleId);
            
            stmt.setString(i++, date1);
            logger.debug(">>>{}", date1);
            
            stmt.setString(i++, date2 + " 23:59:59.9");
            logger.debug(">>>{}", date2);
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        
        try {
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                EventEntity evt = new EventEntity();
                
                int index = 1;
                evt.setUserId(rs.getString(index++));
                evt.setUserName(rs.getString(index++));
                evt.setName(rs.getString(index++));
                evt.setEvtTime(rs.getString(index++));
                evt.setSeverity(rs.getInt(index++));
                evt.setIp(rs.getString(index++));
                evt.setContent(rs.getString(index++));
                
                datalist.add(evt);
                logger.debug("add event: {}", evt.toString());
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        } finally {
            close(con);
        }
        
        return datalist;        
    }  
    
    public List<EventEntity> queryRecentEvent(int limit) {
        List<EventEntity> datalist = new ArrayList<EventEntity>();
        
        String sql = "SELECT userid, username, name, evt_time, severity, ip, content "
                    + "FROM event "
                    + "ORDER BY evt_time DESC " 
                    + "LIMIT ?";
        
        logger.debug(sql);
        
        PreparedStatement stmt = null;
        try {
            int i = 1;
            stmt = con.prepareStatement(sql);
            stmt.setInt(i++, limit);
            logger.debug(">>>{}", limit);
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        
        try {
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                EventEntity evt = new EventEntity();
                
                int index = 1;
                evt.setUserId(rs.getString(index++));
                evt.setUserName(rs.getString(index++));
                evt.setName(rs.getString(index++));
                evt.setEvtTime(rs.getString(index++));
                evt.setSeverity(rs.getInt(index++));
                evt.setIp(rs.getString(index++));
                evt.setContent(rs.getString(index++));
                
                datalist.add(evt);
                logger.debug("add event: {}", evt.toString());
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        } finally {
            close(con);
        }
        
        return datalist;        
    }

}
