package com.mw.spike;


import com.mw.spike.domain.SpikeOrder;
//import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.PreparedStatement;
import org.junit.Test;


import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author WangCH
 * @create 2018-03-07 21:12
 */
public class BatchInsertTest {

    /**
     * 生成订单列表
     * @param count
     * @return
     */
    public static List<SpikeOrder> buildSpikeOrder(int count) {
        List<SpikeOrder> spikeOrders = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            SpikeOrder spikeOrder = new SpikeOrder();
            spikeOrder.setUserId(20000000000L + i);
            spikeOrder.setOrderId(30000000000L + i);
            spikeOrder.setGoodsId(40000000000L + i);
            spikeOrders.add(spikeOrder);
        }
        System.out.println("create spikeOrders count: " + count);
        return spikeOrders;
    }

    private String url = "jdbc:mysql://127.0.0.1:3306/spike?rewriteBatchedStatements=true&useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false";
    private String user = "com/mw";
    private String password = "wc654321";


    /**
     * jdbc 测试批量插入,最快的
     * 批量操作+事务
     * @throws Exception
     */
    @Test
    public void test() throws Exception {

        List<SpikeOrder> spikeOrders = buildSpikeOrder(100000);

        Long begin = new Date().getTime();
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false);
            String sql = "insert into spike_order (user_id, order_id, goods_id) values (?,?,?)";
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            if(spikeOrders.size() <= 10000 && spikeOrders.size() > 1000 ){
                for (int i = 0; i < spikeOrders.size(); i++) {
                    SpikeOrder order = spikeOrders.get(i);
                    pstmt.setLong(1, order.getUserId());
                    pstmt.setLong(2, order.getOrderId());
                    pstmt.setLong(3, order.getGoodsId());
                    pstmt.addBatch();
                    if((i+1) % 1000 == 0 ){
                        pstmt.executeBatch();
                    }
                }
            }else if(spikeOrders.size() <= 1000 ){
                for (int i = 0; i < spikeOrders.size(); i++) {
                    SpikeOrder order = spikeOrders.get(i);
                    pstmt.setLong(1, order.getUserId());
                    pstmt.setLong(2, order.getOrderId());
                    pstmt.setLong(3, order.getGoodsId());
                    pstmt.addBatch();
                    pstmt.executeBatch();
                }
            }else{// 大于10000
                for (int i = 0; i < spikeOrders.size(); i++) {
                    SpikeOrder order = spikeOrders.get(i);
                    pstmt.setLong(1, order.getUserId());
                    pstmt.setLong(2, order.getOrderId());
                    pstmt.setLong(3, order.getGoodsId());
                    pstmt.addBatch();
                    if((i+1) % 10000 == 0 ){
                        pstmt.executeBatch();
                    }
                }
            }
            conn.commit();
            Long end = new Date().getTime();
            System.out.println("cast : " + (end - begin) + " ms");
            System.out.println("cast : " + (end - begin) / 1000 + " s");
            System.out.println("Finish");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
    }


}
