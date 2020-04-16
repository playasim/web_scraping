package com.softnero.controller;


import com.softnero.entity.Comments;
import com.softnero.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/comments")
    public List<Comments> getCommentByKeyWords(@RequestParam String keys) throws SQLException {
        return commentService.getCommentsByKeyWords(keys);
    }
}
