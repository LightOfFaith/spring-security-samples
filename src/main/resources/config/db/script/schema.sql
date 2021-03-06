CREATE DATABASE IF NOT EXISTS `security`
DEFAULT CHARACTER SET UTF8
DEFAULT COLLATE = UTF8_GENERAL_CI;

CREATE TABLE IF NOT EXISTS users(
 id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
 username VARCHAR(50) NOT NULL COMMENT '账号',
 password VARCHAR(50) NOT NULL COMMENT '密码',
 enabled BOOLEAN DEFAULT '0' COMMENT '禁用(1:TRUE,0:FALSE)',
 nickname VARCHAR(10) NULL COMMENT '昵称',
 realname VARCHAR(10) NULL COMMENT '真实姓名',
 email VARCHAR(50) NULL COMMENT '邮箱',
 remark VARCHAR(50) NULL COMMENT '备注',
 gmt_create DATETIME DEFAULT CURRENT_TIMESTAMP,
 gmt_modified DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 UNIQUE KEY uk_users (username)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARACTER SET UTF8 DEFAULT COLLATE UTF8_GENERAL_CI;

CREATE TABLE IF NOT EXISTS authorities (
 id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
 username VARCHAR(50) NOT NULL COMMENT '账号',
 authority VARCHAR(50) NOT NULL COMMENT '权限(格式:ROLE_)，默认值ROLE_USER',
 remark VARCHAR(50) NULL COMMENT '备注',
 gmt_create DATETIME DEFAULT CURRENT_TIMESTAMP,
 gmt_modified DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users (username)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARACTER SET UTF8 DEFAULT COLLATE UTF8_GENERAL_CI;


-- ALTER TABLE authorities DROP FOREIGN KEY fk_authorities_users;

-- ALTER TABLE authorities DROP INDEX ix_auth_username;

-- ALTER TABLE authorities CONSTRAINT fk_authorities_users ADD FOREIGN KEY (username) REFERENCES users (username)

-- CREATE UNIQUE INDEX ix_auth_username ON authorities (username,authority);

CREATE TABLE IF NOT EXISTS groups (
 id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
 group_name VARCHAR(50) NOT NULL COMMENT '组名',
 remark VARCHAR(50) NULL COMMENT '备注',
 gmt_create DATETIME DEFAULT CURRENT_TIMESTAMP,
 gmt_modified DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARACTER SET UTF8 DEFAULT COLLATE UTF8_GENERAL_CI;

CREATE TABLE IF NOT EXISTS group_authorities (
 id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
 group_id BIGINT UNSIGNED NOT NULL,
 authority VARCHAR(50) NOT NULL COMMENT '组权限(格式:ROLE_GROUP)',
 remark VARCHAR(50) NULL COMMENT '备注',
 gmt_create DATETIME DEFAULT CURRENT_TIMESTAMP,
 gmt_modified DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 CONSTRAINT fk_group_authorities_group FOREIGN KEY (group_id) REFERENCES groups(id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARACTER SET UTF8 DEFAULT COLLATE UTF8_GENERAL_CI;

CREATE TABLE IF NOT EXISTS group_members (
 id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
 username VARCHAR(50) NOT NULL COMMENT '账号',
 group_id BIGINT UNSIGNED NOT NULL COMMENT '组ID',
 CONSTRAINT fk_group_members_group FOREIGN KEY (group_id) REFERENCES groups(id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARACTER SET UTF8 DEFAULT COLLATE UTF8_GENERAL_CI;

CREATE TABLE IF NOT EXISTS acl_sid (
 id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
 principal BOOLEAN NOT NULL,
 sid VARCHAR(100) NOT NULL,
 gmt_create DATETIME DEFAULT CURRENT_TIMESTAMP,
 gmt_modified DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 UNIQUE KEY unique_acl_sid (sid, principal)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARACTER SET UTF8 DEFAULT COLLATE UTF8_GENERAL_CI;

CREATE TABLE IF NOT EXISTS acl_class (
 id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
 class VARCHAR(100) NOT NULL,
 gmt_create DATETIME DEFAULT CURRENT_TIMESTAMP,
 gmt_modified DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 UNIQUE KEY uk_acl_class (class)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARACTER SET UTF8 DEFAULT COLLATE UTF8_GENERAL_CI;

CREATE TABLE IF NOT EXISTS acl_object_identity (
 id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
 object_id_class BIGINT UNSIGNED NOT NULL,
 object_id_identity BIGINT NOT NULL,
 parent_object BIGINT UNSIGNED,
 owner_sid BIGINT UNSIGNED,
 entries_inheriting BOOLEAN NOT NULL,
 gmt_create DATETIME DEFAULT CURRENT_TIMESTAMP,
 gmt_modified DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 UNIQUE KEY uk_acl_object_identity (object_id_class, object_id_identity),
 CONSTRAINT fk_acl_object_identity_parent FOREIGN KEY (parent_object) REFERENCES acl_object_identity
 (id),
 CONSTRAINT fk_acl_object_identity_class FOREIGN KEY (object_id_class) REFERENCES acl_class (id),
 CONSTRAINT fk_acl_object_identity_owner FOREIGN KEY (owner_sid) REFERENCES acl_sid (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARACTER SET UTF8 DEFAULT COLLATE UTF8_GENERAL_CI;

CREATE TABLE IF NOT EXISTS acl_entry (
 id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
 acl_object_identity BIGINT UNSIGNED NOT NULL,
 ace_order INTEGER NOT NULL,
 sid BIGINT UNSIGNED NOT NULL,
 mask INTEGER UNSIGNED NOT NULL,
 granting BOOLEAN NOT NULL,
 audit_success BOOLEAN NOT NULL,
 audit_failure BOOLEAN NOT NULL,
 gmt_create DATETIME DEFAULT CURRENT_TIMESTAMP,
 gmt_modified DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 UNIQUE KEY unique_acl_entry (acl_object_identity, ace_order),
 CONSTRAINT fk_acl_entry_object FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity (id),
 CONSTRAINT fk_acl_entry_acl FOREIGN KEY (sid) REFERENCES acl_sid (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARACTER SET UTF8 DEFAULT COLLATE UTF8_GENERAL_CI;