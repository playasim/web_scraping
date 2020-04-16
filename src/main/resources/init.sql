drop schema if exists `test`;
create schema `test`;
use `test`;
DROP TABLE IF EXISTS `tb_comment`;
CREATE TABLE `tb_comment`
(
    `id`   int(10) unsigned NOT NULL AUTO_INCREMENT,
    `nickname` varchar(20)    NOT NULL,
    `content`       varchar(1024)       NOT NULL,
    `update_time`          varchar(1024)           NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;