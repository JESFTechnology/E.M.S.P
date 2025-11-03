-- --------------------------------------------------------
-- Servidor:                     127.0.0.1
-- Versão do servidor:           10.4.32-MariaDB - mariadb.org binary distribution
-- OS do Servidor:               Win64
-- HeidiSQL Versão:              12.1.0.6537
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Copiando estrutura do banco de dados para emsp
DROP DATABASE IF EXISTS `emsp`;
CREATE DATABASE IF NOT EXISTS `emsp` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `emsp`;

-- Copiando estrutura para tabela emsp.alerts
DROP TABLE IF EXISTS `alerts`;
CREATE TABLE IF NOT EXISTS `alerts` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `sensorId` bigint(20) NOT NULL DEFAULT 0,
  `message` text NOT NULL,
  `level` varchar(10) NOT NULL DEFAULT 'médio',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `resolved` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `sensor_id` (`sensorId`) USING BTREE,
  CONSTRAINT `FK_alerts_sensors` FOREIGN KEY (`sensorId`) REFERENCES `sensors` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Copiando dados para a tabela emsp.alerts: ~1 rows (aproximadamente)
DELETE FROM `alerts`;
INSERT INTO `alerts` (`id`, `sensorId`, `message`, `level`, `created_at`, `resolved`) VALUES
	(2, 5, 'Sensor relatou -400, possívelmente não está conectado a rede ou está danificado.', 'Baixo', '2025-10-31 13:15:03', 1);

-- Copiando estrutura para tabela emsp.farm
DROP TABLE IF EXISTS `farm`;
CREATE TABLE IF NOT EXISTS `farm` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `cnpj` varchar(50) NOT NULL,
  `location` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Copiando dados para a tabela emsp.farm: ~0 rows (aproximadamente)
DELETE FROM `farm`;
INSERT INTO `farm` (`id`, `name`, `cnpj`, `location`) VALUES
	(1, 'Costeira De Fogo', '09.902.058/0001-03', 'Itabaianinha - Sergipe'),
	(2, 'Fazenda do Pica-Pau Dourado', '50.580.085/0001-00', 'Anta Gorda - Rio Grande do Sul');

-- Copiando estrutura para tabela emsp.sensors
DROP TABLE IF EXISTS `sensors`;
CREATE TABLE IF NOT EXISTS `sensors` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `typeSensor` int(11) NOT NULL DEFAULT 1,
  `last_value` float NOT NULL DEFAULT -400,
  `last_update` datetime NOT NULL,
  `device_model` varchar(50) NOT NULL,
  `battery_level` smallint(6) NOT NULL DEFAULT 100,
  `status` varchar(20) NOT NULL DEFAULT 'ativo',
  `installed_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `farmId` int(11) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  KEY `farmId` (`farmId`),
  KEY `typeSensor` (`typeSensor`),
  CONSTRAINT `FK_sensors_farm` FOREIGN KEY (`farmId`) REFERENCES `farm` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_sensors_sensor_type` FOREIGN KEY (`typeSensor`) REFERENCES `sensor_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Copiando dados para a tabela emsp.sensors: ~6 rows (aproximadamente)
DELETE FROM `sensors`;
INSERT INTO `sensors` (`id`, `name`, `typeSensor`, `last_value`, `last_update`, `device_model`, `battery_level`, `status`, `installed_at`, `farmId`) VALUES
	(1, 'Estação Meteorologica 1', 1, 18, '2025-10-23 07:49:10', '726NS', 100, 'Ativo', '2025-10-23 10:49:10', 1),
	(2, 'Estação Meteorologica 1', 2, 90, '2025-10-23 07:49:10', '725NS', 100, 'Ativo', '2025-10-23 10:50:01', 1),
	(3, 'Estação Meteorologica 1', 3, 1019, '2025-10-23 07:49:10', '724NS', 100, 'Em manutenção', '2025-10-23 10:50:29', 1),
	(4, 'Estação Meteorologica 1', 4, 80, '2025-10-23 07:49:10', '723NS', 100, 'Ativo', '2025-10-23 10:51:06', 1),
	(5, 'Controle de fluxo da irrigação', 6, -400, '2025-10-31 10:06:26', '645JJ', 100, 'Desativado', '2025-10-31 13:06:26', 2);

-- Copiando estrutura para tabela emsp.sensor_type
DROP TABLE IF EXISTS `sensor_type`;
CREATE TABLE IF NOT EXISTS `sensor_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `unit` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Copiando dados para a tabela emsp.sensor_type: ~4 rows (aproximadamente)
DELETE FROM `sensor_type`;
INSERT INTO `sensor_type` (`id`, `name`, `unit`) VALUES
	(1, 'Temperature', 'ºc'),
	(2, 'Humidity', '%'),
	(3, 'Air pressure', 'mb'),
	(4, 'Wind', 'km/h'),
	(6, 'Flow', 'gpm');

-- Copiando estrutura para tabela emsp.users
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `gender` varchar(50) NOT NULL,
  `email` varchar(120) NOT NULL,
  `password` text NOT NULL,
  `cpf` varchar(50) NOT NULL,
  `role` varchar(20) NOT NULL DEFAULT 'operator',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `farmId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  KEY `farmId` (`farmId`),
  CONSTRAINT `FK_users_farm` FOREIGN KEY (`farmId`) REFERENCES `farm` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Copiando dados para a tabela emsp.users: ~2 rows (aproximadamente)
DELETE FROM `users`;
INSERT INTO `users` (`id`, `name`, `gender`, `email`, `password`, `cpf`, `role`, `created_at`, `farmId`) VALUES
	(1, 'Johann Estevão Sacconi Ferreira', 'M', 'johannsacconi@gmail.com', 'gjs1234', '13642344674', 'operator', '2025-11-03 12:42:09', 1),
	(2, 'Lucas Silva Rosa', 'M', 'lucas@gmail.com', 'gjs1234', '12345678901', 'Operador', '2025-11-03 12:43:24', 2);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
