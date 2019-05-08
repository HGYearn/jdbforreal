CREATE TABLE dmp_strategy (
  `id` INT AUTO_INCREMENT,
  `strategy` BLOB COMMENT '序列化的策略',
  `layer_type` TINYINT NOT NULL COMMENT '策略所在的层次',
  `time_create` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY (`strategy`)
)ENGINE=InnoDB DEFAULT CHARSET = utf8 COLLATE = utf8_unicode_ci COMMENT '层次策略';