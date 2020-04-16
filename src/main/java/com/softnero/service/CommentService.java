package com.softnero.service;

import com.softnero.entity.Comments;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public interface CommentService {

    List<Comments> getCommentsByKeyWords(String key) throws SQLException;

}
