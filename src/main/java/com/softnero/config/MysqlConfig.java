package com.softnero.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@Configuration
@Component
public class MysqlConfig {

  private static final Logger logger = LoggerFactory.getLogger(MysqlConfig.class);

  @Value("${mysql.host}")
  private String url = "jdbc:mysql://localhost:3306/test";

  @Value("${mysql.username}")
  private String username = "root";

  @Value("${mysql.password}")
  private String password = "123456";

  private Connection conn;

  private void initConnection() {
    try {
      String driver = "com.mysql.cj.jdbc.Driver";
      Class.forName(driver);
      conn = DriverManager.getConnection(url, username, password);
      if (!conn.isClosed()) {
        logger.info("---------------------------------------------");
        logger.info("Successfully connected to mysql : {}, user : {}", url, username);
        logger.info("---------------------------------------------");
      }

    } catch (ClassNotFoundException e) {
      logger.error("-----------------------");
      logger.error("Unable to find connect to mysql database!");
      logger.error("mysql url:{}, username: {}", url, username);
      logger.error("-----------------------");
      e.printStackTrace();
    } catch (SQLException e) {
      logger.error("-----------------------");
      logger.error("Can not connect to mysql database!");
      logger.error("mysql url:{}, username: {}", url, username);
      logger.error("-----------------------");
      e.printStackTrace();
    }
  }

  public Connection getConn() {
    if (conn == null) {
      initConnection();
    }
    return conn;
  }


  public void close() {
    if (conn != null) {
      try {
        conn.close();
        conn = null;
      } catch (SQLException e) {
        logger.error("----------------------------");
        logger.error("Connection can not be closed/");
        logger.error("----------------------------");
        e.printStackTrace();
      }
    }
  }


}
