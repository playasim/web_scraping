package com.softnero.service;

import com.softnero.config.MysqlConfig;
import com.softnero.entity.Comments;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CommentServiceImpl implements CommentService {
    MysqlConfig mysqlConfig = new MysqlConfig();
    @Override
    public List<Comments> getCommentsByKeyWords(String key) throws SQLException {
        String sql = "select * from tb_comment where content like '%" + key + "%'";
        Connection con = mysqlConfig.getConn();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Comments> result = new ArrayList();
        while (rs.next()) {
            Comments comments = new Comments();
            comments.setId(rs.getInt(1));
            comments.setNickname(rs.getString(2));
            comments.setComment(rs.getString(3));
            comments.setUpdateTime(rs.getString(4));
            result.add(comments);
        }
        rs.close();
        ps.close();
        return result;

    }
}
