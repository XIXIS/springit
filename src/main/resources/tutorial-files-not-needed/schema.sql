CREATE DATABASE IF NOT EXISTS `springit`;
USE `springit`;

--
-- Table structure for table `comment`
--
DROP table IF EXISTS `comment`;

CREATE TABLE `comment` (
   `id`                 bigint(20)       NOT NULL,
   `created_by`         varchar(255) DEFAULT NULL,
   `creation_date`      datetime     DEFAULT NULL,
   `last_modified_by`   varchar(255) DEFAULT NULL,
   `last_modified_date` datetime     DEFAULT NULL,
   `body`               varchar(255) DEFAULT NULL,
   `link_id`            bigint(20)   DEFAULT NULL,
   PRIMARY KEY (`id`),
   KEY `FKoutxw6g1ndh1t6282y0fwvami` (`link_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Table structure for table `link`
--
DROP table IF EXISTS `link`;

CREATE TABLE `link` (
    `id`                 bigint(20)       NOT NULL,
    `created_by`         varchar(255) DEFAULT NULL,
    `creation_date`      datetime     DEFAULT NULL,
    `last_modified_by`   varchar(255) DEFAULT NULL,
    `last_modified_date` datetime     DEFAULT NULL,
    `title`              varchar(255) DEFAULT NULL,
    `url`                varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

