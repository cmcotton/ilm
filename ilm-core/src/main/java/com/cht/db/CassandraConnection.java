package com.cht.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cht.entity.EventEntity;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;

public class CassandraConnection {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Cluster cls = null; // Database objects
    private Session sess = null; // Database objects

    // public List<LoginLogEntity> queryLoginLog(Date[] period) {
    // Statement select = new QueryBuilder(cls).select().all().from("ilm",
    // "event");
    // ResultSet results = sess.execute(select);
    //
    // List<LoginLogEntity> list = new ArrayList<>();
    // for (Row row : results) {
    // logger.info("{} {} {} \n", row.getString("firstname"),
    // row.getString("lastname"), row.getInt("age"));
    // }
    //
    // return list;
    // }

    public void insert() {
        sess.execute("DROP TABLE ilm.event");
        sess.execute("CREATE TABLE ilm.event ( id int, dept_id VARCHAR, user_name varchar,  PRIMARY KEY (id));");
        // sess.execute("alter table  event add desc varchar");

        sess.execute("INSERT INTO ilm.event (id, dept_id, user_name) " + "VALUES (1, '測試部門', '蔡英文') ");
    }

    public void insertEvent(EventEntity newItem) {
        PreparedStatement ps = sess.prepare(
                "INSERT INTO event2 (id, name, content, ruleid, severity, userid, username, evt_time, ip) "
                + "VALUES (now(), ?, ?, ?, ?, ?, ?, ?, ?) "
             );
        
        try {
            sess.execute(ps.bind(newItem.getName(), newItem.getContent(), newItem.getRuleId(), newItem.getSeverity(),
                newItem.getUserId(), newItem.getUserName(), newItem.getEvtTime(), newItem.getIp()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public CassandraConnection() {
        // Connect to the cluster and keyspace "ilm"
        cls = Cluster.builder().addContactPoint("127.0.0.1").build();
        sess = cls.connect("ilm");

    }

    public static void main(String[] args) {
        new CassandraConnection().insert();

    }
}
