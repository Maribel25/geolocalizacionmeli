CREATE TABLE `configuration_setting` (
  `KEY` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `VALUE` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `statistic` (
  `ID` int NOT NULL,
  `COUNTRY` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `DISTANCE` int DEFAULT NULL,
  `INVOCATIONS` int DEFAULT NULL,
  `COUNTRY_CODE` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`ID`,`COUNTRY_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `geo_schema`.`configuration_setting`(`KEY`,`VALUE`) VALUES('geo.ip','https://api.ip2country.info/ip?');
INSERT INTO `geo_schema`.`configuration_setting`(`KEY`,`VALUE`) values ('contry.code.info','https://restcountries.eu/rest/v2/alpha/');
INSERT INTO `geo_schema`.`configuration_setting`(`KEY`,`VALUE`) values ('data.fixer.latest','http://data.fixer.io/api/latest?');
INSERT INTO `geo_schema`.`configuration_setting`(`KEY`,`VALUE`) values ('access.key','44262cdf6064d216d0da23d7f5fba945');
INSERT INTO `geo_schema`.`configuration_setting`(`KEY`,`VALUE`) values ('lat.arg','-34');
INSERT INTO `geo_schema`.`configuration_setting`(`KEY`,`VALUE`) values ('long.arg','-64')