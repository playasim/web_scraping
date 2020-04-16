package com.softnero.config;

import com.softnero.crwaler.Crawler;
import com.softnero.entity.Comments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;


@Component
public class ConditionRunner implements CommandLineRunner {

    @Autowired
    private MysqlConfig mysqlConfig;
    @Override
    public void run(String... args) throws Exception {
        Connection conn = mysqlConfig.getConn();
        PreparedStatement deleteStatement = conn.prepareStatement("delete from tb_comment");
        deleteStatement.execute();
        deleteStatement.close();
        List<Comments> list = Crawler.getCommentInfo(10, 1384071);
        PreparedStatement psmt = null;
        psmt = conn.prepareStatement("insert into tb_comment(id,nickname,content,update_time)" +
                "values(?,?,?,?)");
        for (int i = 0; i < list.size(); i++) {
            Comments comments = list.get(i);
            psmt.setInt(1, comments.getId());
            psmt.setString(2, comments.getNickname());
            psmt.setString(3, comments.getComment());
            psmt.setString(4, comments.getUpdateTime());
            psmt.addBatch();
        }
        psmt.executeBatch();
        //conn.commit();
        psmt.close();
        conn.close();
    }
}
