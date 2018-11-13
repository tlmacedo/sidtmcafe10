CREATE DATABASE  IF NOT EXISTS `cafeperfeito` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `cafeperfeito`;
-- MySQL dump 10.13  Distrib 8.0.12, for macos10.13 (x86_64)
--
-- Host: 127.0.0.1    Database: cafeperfeito
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cargo`
--

DROP TABLE IF EXISTS `cargo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `cargo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cargo`
--

LOCK TABLES `cargo` WRITE;
/*!40000 ALTER TABLE `cargo` DISABLE KEYS */;
INSERT INTO `cargo` VALUES (1,'PROGRAMADOR'),(2,'PROPRIETARIO'),(3,'GERENTE'),(4,'VENDEDOR INTERNO'),(5,'VENDEDOR EXTERNO'),(6,'CONSULTOR'),(7,'TECNICO'),(8,'CONTADOR'),(9,'MONTADOR'),(10,'ENTREGADOR'),(11,'ATENDENTE'),(12,'TELEFONISTA'),(13,'MOTORISTA'),(14,'CONFERENTE'),(15,'COMPRADOR'),(16,'FISCAL'),(17,'ASSISTENTE');
/*!40000 ALTER TABLE `cargo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `colaborador`
--

DROP TABLE IF EXISTS `colaborador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `colaborador` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `apelido` varchar(30) NOT NULL,
  `ativo` bit(1) NOT NULL,
  `ctps` varchar(30) NOT NULL,
  `dataAdmisao` timestamp NOT NULL,
  `nome` varchar(80) NOT NULL,
  `salario` decimal(19,2) NOT NULL,
  `cargo_id` bigint(20) DEFAULT NULL,
  `empresa_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_colaborador_cargo_id` (`cargo_id`),
  KEY `fk_colaborador_empresa_id` (`empresa_id`),
  CONSTRAINT `fk_colaborador_cargo_id` FOREIGN KEY (`cargo_id`) REFERENCES `cargo` (`id`),
  CONSTRAINT `fk_colaborador_empresa_id` FOREIGN KEY (`empresa_id`) REFERENCES `empresa` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `colaborador`
--

LOCK TABLES `colaborador` WRITE;
/*!40000 ALTER TABLE `colaborador` DISABLE KEYS */;
INSERT INTO `colaborador` VALUES (1,'thiago',_binary '','123456','2006-04-28 04:00:00','Thiago Macedo',8000.00,1,1),(2,'carla',_binary '','567890','2013-05-18 13:00:00','Carla Macedo',5000.00,2,1);
/*!40000 ALTER TABLE `colaborador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `colaborador_telefone`
--

DROP TABLE IF EXISTS `colaborador_telefone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `colaborador_telefone` (
  `Colaborador_VO_id` bigint(20) NOT NULL,
  `telefoneVOS_id` bigint(20) NOT NULL,
  `ColaboradorVO_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_qfy0co9fpy1p40fce3dm2eku3` (`telefoneVOS_id`),
  KEY `FKmxcdgm3jbf5vgn8b4dat2gs9k` (`Colaborador_VO_id`),
  CONSTRAINT `FKmxcdgm3jbf5vgn8b4dat2gs9k` FOREIGN KEY (`Colaborador_VO_id`) REFERENCES `colaborador` (`id`),
  CONSTRAINT `FKoulw7omdcl8rrorhynotvbyaa` FOREIGN KEY (`telefoneVOS_id`) REFERENCES `telefone` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `colaborador_telefone`
--

LOCK TABLES `colaborador_telefone` WRITE;
/*!40000 ALTER TABLE `colaborador_telefone` DISABLE KEYS */;
INSERT INTO `colaborador_telefone` VALUES (1,1,0),(1,2,0),(2,3,0);
/*!40000 ALTER TABLE `colaborador_telefone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empresa`
--

DROP TABLE IF EXISTS `empresa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `empresa` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cnpjCpf` varchar(14) NOT NULL,
  `dataAbetura` datetime DEFAULT NULL,
  `dataAtualizacao` timestamp NULL DEFAULT NULL,
  `dataCadastro` timestamp NOT NULL,
  `fantasia` varchar(80) NOT NULL,
  `ieRg` varchar(14) NOT NULL,
  `isCliente` bit(1) NOT NULL,
  `isFornecedor` bit(1) NOT NULL,
  `isIsento` bit(1) NOT NULL,
  `isLojaSistema` bit(1) NOT NULL,
  `isTransportadora` bit(1) NOT NULL,
  `naturezaJuridica` varchar(150) NOT NULL,
  `razao` varchar(80) NOT NULL,
  `situacao` int(11) NOT NULL,
  `tipo` int(11) NOT NULL,
  `usuarioAtualizacao_id` bigint(20) DEFAULT NULL,
  `usuarioCadastro_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_empresa_usuarioAtualizacao_id` (`usuarioAtualizacao_id`),
  KEY `fk_empresa_usuarioCadastro_id` (`usuarioCadastro_id`),
  CONSTRAINT `fk_empresa_usuarioAtualizacao_id` FOREIGN KEY (`usuarioAtualizacao_id`) REFERENCES `usuario` (`id`),
  CONSTRAINT `fk_empresa_usuarioCadastro_id` FOREIGN KEY (`usuarioCadastro_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empresa`
--

LOCK TABLES `empresa` WRITE;
/*!40000 ALTER TABLE `empresa` DISABLE KEYS */;
INSERT INTO `empresa` VALUES (1,'08009246000136','2006-04-28 00:00:00','2018-11-12 15:08:47','2018-11-12 15:08:46','CAFE PERFEITO','042171865',_binary '',_binary '',_binary '\0',_binary '',_binary '','213-5 - EMPRESÁRIO (INDIVIDUAL)','T. L. MACEDO',1,2,1,1),(2,'01703285000190','1997-02-13 00:00:00','2018-11-12 15:08:47','2018-11-12 15:08:47','BRANCO PERES COMERCIO ATACADISTA LTDA','115298039114',_binary '\0',_binary '',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','BRASIL ESPRESSO COMERCIO ATACADISTA LTDA.',1,2,NULL,1),(3,'05694248000113','2003-06-02 00:00:00','2018-11-12 15:08:47','2018-11-12 15:08:47','***','635485405115',_binary '\0',_binary '',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','SUPREMO ARABICA COMERCIO IMPORTACAO E EXPORTACAO DE MAQUINAS LTDA',1,2,NULL,1),(4,'04561379000160','1966-08-08 00:00:00','2018-11-12 15:08:47','2018-11-12 15:08:47','ARMAZEM SANTA CLARA','041008057',_binary '\0',_binary '',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','J J R COMERCIO DE PRODUTOS ALIMENTICIOS LTDA',1,2,NULL,1),(5,'18466212000124','2013-07-11 00:00:00','2018-11-12 15:08:47','2018-11-12 15:08:47','D R DAMASCENO','053402766',_binary '\0',_binary '',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','MENDES E DAMASCENO LTDA',1,2,NULL,1),(6,'84509264000165','1994-01-05 23:00:00','2018-11-12 15:08:47','2018-11-12 15:08:47','***','041278321',_binary '\0',_binary '',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','MAXPEL COMERCIAL LTDA',1,2,NULL,1),(7,'01487193000110','1996-10-14 00:00:00','2018-11-12 15:08:47','2018-11-12 15:08:47','***','041096282',_binary '\0',_binary '',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','B S DISTRIBUICAO E REPRESENTACAO LTDA',1,2,NULL,1),(8,'47427653000468','2001-09-04 00:00:00','2018-11-12 15:08:47','2018-11-12 15:08:47','***','041484452',_binary '\0',_binary '',_binary '\0',_binary '\0',_binary '\0','205-4 - SOCIEDADE ANÔNIMA FECHADA)','MAKRO ATACADISTA SOCIEDADE ANONIMA',1,2,NULL,1),(9,'04121789000190','2000-10-09 00:00:00','2018-11-12 15:08:47','2018-11-12 15:08:47','REDYAR TRANSPORTES','336826969110',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','REDYAR - OTM TRANSPORTES LTDA',1,2,NULL,1),(10,'06186733000220','2006-06-14 00:00:00','2018-11-12 15:08:47','2018-11-12 15:08:47','EXATA CARGO','336942124118',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','EXATA CARGO LTDA',1,2,NULL,1),(11,'60541240000125','1989-04-27 00:00:00','2018-11-12 15:08:47','2018-11-12 15:08:47','TECNOLOG EXPRESS CARGO','113551215114',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','TECNOLOG TRANSPORTE RODO-AEREO E LOGISTICA LTDA.',1,2,NULL,1),(12,'05518915000107','1985-10-04 00:00:00','2018-11-12 15:08:47','2018-11-12 15:08:47','ALEMA','041775260',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','ALEMA RESTAURANTES LTDA',1,2,NULL,1),(13,'09812871000184','2008-04-28 00:00:00','2018-11-12 15:08:47','2018-11-12 15:08:47','CAFERETTO','',_binary '',_binary '\0',_binary '',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','CAFERETTO SERVICO DE ALIMENTACAO E COMERCIO LTDA',7,2,NULL,1),(14,'63702476000194','1991-06-11 00:00:00','2018-11-12 15:08:47','2018-11-12 15:08:47','LIVRARIA AJURICABA','042966829',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','H L PUBLICACOES LTDA',2,2,1,1),(15,'10701631000191','2009-03-19 00:00:00','2018-11-12 15:08:47','2018-11-12 15:08:47','PICANHA MANIA','042936829',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','HR RESTAURANTE LTDA',1,2,NULL,1),(16,'04336889000133','1978-08-29 00:00:00','2018-11-12 15:08:47','2018-11-12 15:08:47','AMANDA BEAUTY CENTER','041143310',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','230-5 - EMPRESA INDIVIDUAL DE RESPONSABILIDADE LIMITADA (DE NATUREZA EMPRESÁRIA))','AMANDA CABELEIREIROS EIRELI',1,2,NULL,1),(17,'05073167000104','2002-05-17 00:00:00','2018-11-12 15:08:47','2018-11-12 15:08:47','NICE VEICULOS','041526066',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','NICE VEICULOS LTDA',1,2,NULL,1),(18,'11371389000106','2009-12-04 00:00:00','2018-11-12 15:08:47','2018-11-12 15:08:47','X PICANHA','042966400',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','X-MANAUS RESTAURANTE LTDA',1,2,NULL,1),(19,'06522265000306','2008-07-23 00:00:00','2018-11-12 15:08:47','2018-11-12 15:08:47','PONTA NEGRA IMPORT','042904510',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','PONTA NEGRA SOLUCOES, LOGISTICAS E TRANSPORTES LTDA',1,2,NULL,1),(20,'07819538000171','2006-01-25 00:00:00','2018-11-12 15:08:47','2018-11-12 15:08:47','X PICANHA','042168295',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','LUTRA COMERCIO DE ALIMENTOS LTDA',1,2,NULL,1),(21,'09497477000107','2008-03-06 00:00:00','2018-11-12 15:08:47','2018-11-12 15:08:47','NAJUA RESTAURANTE','042249333',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','M M RESTAURANTE LTDA',1,2,NULL,1),(22,'63740237000129','1991-10-04 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','DELIVERY DO DEDE','041189663',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','213-5 - EMPRESÁRIO (INDIVIDUAL))','A L PARENTE',2,2,1,1),(23,'10868702000145','2009-05-21 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','PANIFICADORA PAO DA VILLA','042938856',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','F L - COMERCIO DE ALIMENTOS LTDA',2,2,1,1),(24,'10189959000170','1986-01-06 23:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','HOTEL ARIAU','041168267',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','RIVER JUNGLE HOTEL LTDA',1,2,NULL,1),(25,'09449408000110','2008-03-07 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','SPEED A. M. EMPRESARIAL LTDA','042240646',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','SPEED A M EQUIPAMENTOS DE COMUNICACAO LTDA',1,2,NULL,1),(26,'84489269000173','1993-06-14 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','CAESAR BUSINESS MANAUS AMAZONAS','041253477',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','HABIPAR PARTICIPACOES LTDA.',1,2,NULL,1),(27,'10818339000153','2009-05-13 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','PONTO DOS ESPORTES','042935229',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','VIEIRA E VIEIRA COMERCIO DE ARTIGOS ESPORTIVOS LTDA',7,2,NULL,1),(28,'02651424000141','1998-07-16 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','O LENHADOR','041395433',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','CALDAS BUFFET LTDA',2,2,1,1),(29,'11303271000132','2009-11-10 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','CR CONVENIENCIA','042966760',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','CR LOJA DE CONVENIENCIA LTDA',2,2,1,1),(30,'84496694000190','1993-10-21 23:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','***','041302567',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','IMA COMERCIO DE ALIMENTOS LTDA',1,2,NULL,1),(31,'84496694000432','2009-01-08 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','***','042940893',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','IMA COMERCIO DE ALIMENTOS LTDA',1,2,NULL,1),(32,'66542002003055','2010-06-29 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','BLUE TREE PREMIUM MANAUS','042281687',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','205-4 - SOCIEDADE ANÔNIMA FECHADA)','BLUE TREE HOTELS & RESORTS DO BRASIL S/A.',2,2,1,1),(33,'04920058000104','2001-10-31 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','PADARIA TRIGO\'S','240105525',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','PINHEIRO & CIA LTDA',6,2,NULL,1),(34,'04082624000156','1977-04-20 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','SUPERMERCADO IRMAOS GONCALVES','000010197',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','IRMAOS GONCALVES COMERCIO E INDUSTRIA LTDA.',6,2,NULL,1),(35,'15775273000185','1986-12-08 23:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','CHURRASCARIA BUFALO','041818911',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','CHURRASCARIA BUFALO LTDA',2,2,1,1),(36,'84496694000351','2005-01-04 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','***','044005377',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','IMA COMERCIO DE ALIMENTOS LTDA',1,2,NULL,1),(37,'00512663000195','1995-03-29 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','WAY','041017323',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','VIA MARCONI VEICULOS LTDA',1,2,NULL,1),(38,'02584924002151','2011-04-18 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','***','053395786',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','205-4 - SOCIEDADE ANÔNIMA FECHADA)','ICH ADMINISTRACAO DE HOTEIS S.A.',1,2,NULL,1),(39,'09137053000123','2007-10-18 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','CAFE DO PONTO','042290384',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','213-5 - EMPRESÁRIO (INDIVIDUAL))','C C DE OMENA MICHILES',2,2,1,1),(40,'10939609000346','2011-11-03 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','SUBWAY','053209818',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','213-5 - EMPRESÁRIO (INDIVIDUAL))','DIEGO BRINGEL AVELINO',6,2,1,1),(41,'10939609000265','2009-11-05 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','SUBWAY','04966221',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','213-5 - EMPRESÁRIO (INDIVIDUAL))','DIEGO BRINGEL AVELINO',6,2,1,1),(42,'10939609000184','2009-06-30 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','SUBWAY','042942519',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','213-5 - EMPRESÁRIO (INDIVIDUAL))','DIEGO BRINGEL AVELINO',6,2,1,1),(43,'10964541000193','2009-07-15 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','SABOR A MI BISTRO','042949157',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','MENEZES & MENEZES, SERVICOS DE ALIMENTACAO LTDA',1,2,NULL,1),(44,'67894360000317','1997-05-28 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','MODELLI','',_binary '',_binary '\0',_binary '',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','GRAO FRANCHISING E PARTICIPACOES LTDA',7,2,NULL,1),(45,'11875229000196','2010-04-29 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','YOGU MANIA','042274303',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','MT COMERCIO DE ALIMENTOS LTDA',1,2,NULL,1),(46,'60701190411589','2009-04-29 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','***','',_binary '',_binary '\0',_binary '',_binary '\0',_binary '\0','205-4 - SOCIEDADE ANÔNIMA FECHADA)','ITAU UNIBANCO S.A.',1,2,NULL,1),(47,'05424423000152','2002-12-10 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','COMFORT HOTEL','042154316',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','DE PASQUAL HOTEIS E TURISMO LTDA.',2,2,1,1),(48,'09256342000141','2007-12-12 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','CACHACARIA DO DEDE & EMPORIO','042902363',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','RAL EMPREENDIMENTOS LTDA',1,2,NULL,1),(49,'16799056000198','2012-09-04 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','VIGOR CAFE','053299760',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','213-5 - EMPRESÁRIO (INDIVIDUAL))','IGOR DA S.MENDONCA - LANCHONETE',1,2,NULL,1),(50,'08975700000294','2007-08-08 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','YOGO MANIA','042960606',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','VIFOUR COMERCIO DE CONFECCOES E ALIMENTOS LTDA',1,2,NULL,1),(51,'02844344000102','1998-11-04 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','FPF TECH','',_binary '',_binary '\0',_binary '',_binary '\0',_binary '\0','306-9 - FUNDAÇÃO PRIVADA)','FUNDACAO AMAZONICA DE AMPARO A PESQUISA E DESENVOLVIMENTO TECNOLOGICO DESEMBARGA',1,2,NULL,1),(52,'84467307000197','1992-12-22 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','***','041233786',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','DINAMICA DISTRIBUIDORA LTDA',2,2,1,1),(53,'42289025000440','2009-08-06 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','HOTEL HOLIDAY INN MANAUS','042962943',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','INTERCONTINENTAL HOTELS GROUP DO BRASIL LTDA.',1,2,NULL,1),(54,'14629567000136','2011-11-17 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','QUALITE ALIMENTOS','053247264',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','QUALITE ALIMENTOS PREPARADOS LTDA EPP',1,2,NULL,1),(55,'20069719000196','2014-04-11 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','LA FINESTRA','053522249',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','FUTURA COMERCIO DE ALIMENTOS E BEBIDAS LTDA',2,2,1,1),(56,'23033327000408','2004-10-20 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','***','053522249',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','HTS SERVICOS DE HOTELARIA E TURISMO LTDA',1,2,NULL,1),(57,'60701190134305','1991-12-09 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','***','',_binary '',_binary '\0',_binary '',_binary '\0',_binary '\0','205-4 - SOCIEDADE ANÔNIMA FECHADA)','ITAU UNIBANCO S.A.',1,2,NULL,1),(58,'23033327000327','2001-05-03 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','HTS - ED.ADRIANOPOLIS - FILIAL I','053522230',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','HTS SERVICOS DE HOTELARIA E TURISMO LTDA',1,2,NULL,1),(59,'19380220000116','2013-12-09 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','***','053495357',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','213-5 - EMPRESÁRIO (INDIVIDUAL))','IVANEIDE CARACAS BEZERRA',1,2,NULL,1),(60,'10865719000149','2009-05-12 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','B.T.A REFEICOES','042969271',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','B. THOME ARAUJO REFEICOES LTDA-ME',2,2,1,1),(61,'10013843000186','2008-06-30 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','AMANDA BEAUTY CENTER','042902509',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','230-5 - EMPRESA INDIVIDUAL DE RESPONSABILIDADE LIMITADA (DE NATUREZA EMPRESÁRIA))','AMANDA BEAUTY CENTER EIRELI',1,2,NULL,1),(62,'60701190159200','1996-10-30 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','***','',_binary '',_binary '\0',_binary '',_binary '\0',_binary '\0','205-4 - SOCIEDADE ANÔNIMA FECHADA)','ITAU UNIBANCO S.A.',1,2,NULL,1),(63,'08941325000180','2007-06-11 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','***','407521997117',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','SL CAFES DO BRASIL PROFESSIONAL LTDA',1,2,NULL,1),(64,'04336889000214','2009-10-23 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','AMANDA BEAUTY CENTER','042960126',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','AMANDA CABELEIREIROS LTDA',1,2,NULL,1),(65,'16684606000123','2012-08-13 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','CASA DO PAO DE QUEIJO AMAZONAS SHOPPING','053280520',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','RODRIGUES E SMITH COMERCIO DE ALIMENTOS LTDA',1,2,NULL,1),(66,'60701190057637','1975-01-08 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','***','',_binary '',_binary '\0',_binary '',_binary '\0',_binary '\0','205-4 - SOCIEDADE ANÔNIMA FECHADA)','ITAU UNIBANCO S.A.',1,2,NULL,1),(67,'07414941000110','2005-06-01 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','PAOZINHO','042172342',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','PAOZINHO COMERCIO LTDA',1,2,NULL,1),(68,'19783732000123','2014-02-21 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','CASA DO PAO DE QUEIJO','053498771',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','NUTRI CASAS DE CHA LTDA.',1,2,NULL,1),(69,'08038545001413','2015-10-22 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','QUEIROZ','053727290',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','NATUREZA COMERCIO DE DESCARTAVEIS LTDA',1,2,NULL,1),(70,'06981833000248','2015-10-15 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','***','63585819611',_binary '\0',_binary '',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','TORREFACAO NISHIDA SAN LTDA',1,2,1,1),(71,'04802134000268','2002-11-01 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','INDT','',_binary '',_binary '\0',_binary '',_binary '\0',_binary '\0','399-9 - ASSOCIAÇÃO PRIVADA)','INDT - INSTITUTO DE DESENVOLVIMENTO TECNOLOGICO',1,2,NULL,1),(72,'29260745000171','2017-12-13 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','PANIFICADORA ALDEIA DOS PAES','053984722',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','230-5 - EMPRESA INDIVIDUAL DE RESPONSABILIDADE LIMITADA (DE NATUREZA EMPRESÁRIA))','PANIFICADORA ALDEIA DOS PAES EIRELI',1,2,NULL,1),(73,'04012611000100','2000-08-25 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','DISTRIBUIDORA LONDRINA','041452097',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0','230-5 - EMPRESA INDIVIDUAL DE RESPONSABILIDADE LIMITADA (DE NATUREZA EMPRESÁRIA))','RONALDO MAIA BARBOSA EIRELI',1,2,NULL,1),(74,'12564461000176','2010-09-22 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','SHALOM FESTAS','042295513',_binary '\0',_binary '',_binary '\0',_binary '\0',_binary '\0','213-5 - EMPRESÁRIO (INDIVIDUAL))','JANAINA F B MATOS',1,2,NULL,1),(75,'02392576000177','1998-02-04 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','ATACADAO TROPICAL','041395808',_binary '\0',_binary '',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','BANDEIRA DE MELO E FILHOS LTDA',1,2,NULL,1),(76,'00835261000393','2013-12-16 00:00:00','2018-11-12 15:08:48','2018-11-12 15:08:48','SUPERMERCADO RODRIGUES','053583574',_binary '\0',_binary '',_binary '\0',_binary '\0',_binary '\0','206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)','A.M. DA S RODRIGUES & CIA LTDA',1,2,NULL,1);
/*!40000 ALTER TABLE `empresa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `telefone`
--

DROP TABLE IF EXISTS `telefone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `telefone` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(11) NOT NULL,
  `telefoneOperadora_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_telefone_telefoneOperadora_id` (`telefoneOperadora_id`),
  CONSTRAINT `fk_telefone_telefoneOperadora_id` FOREIGN KEY (`telefoneOperadora_id`) REFERENCES `telefoneoperadora` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `telefone`
--

LOCK TABLES `telefone` WRITE;
/*!40000 ALTER TABLE `telefone` DISABLE KEYS */;
INSERT INTO `telefone` VALUES (1,'981686148',1),(2,'38776148',1),(3,'992412974',1);
/*!40000 ALTER TABLE `telefone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `telefoneOperadora`
--

DROP TABLE IF EXISTS `telefoneOperadora`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `telefoneOperadora` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `codWsPortabiliadadeCeluar` varchar(30) DEFAULT NULL,
  `ddd` int(11) NOT NULL,
  `descricao` varchar(30) NOT NULL,
  `tipo` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `telefoneOperadora`
--

LOCK TABLES `telefoneOperadora` WRITE;
/*!40000 ALTER TABLE `telefoneOperadora` DISABLE KEYS */;
INSERT INTO `telefoneOperadora` VALUES (1,'55341;55141',41,'TIM',3),(2,'55321;55121',21,'CLARO / EMBRATEL / NET',3),(3,'55312',12,'CTBC TELECOM',3),(4,'55314;55331;55114;55131',31,'OI',3),(5,'55320;55323;55115;55215',15,'VIVO',3),(6,'55343;55143',43,'SERCOMTEL',3),(7,'55351;55377',0,'NEXTEL',3),(8,'55277',10,'GENTE TELECOM',1),(9,NULL,11,'LIGUEMAX',1),(10,'55113',13,'FONAR',1),(11,'55274',14,'BRASIL TELECOM',1),(12,'55192',16,'VIACOM',1),(13,'55117',17,'TRANSIT TELECOM',1),(14,'55118',18,'SPIN',1),(15,NULL,19,'EPSILON',1),(16,'55123',23,'INTELIG',1),(17,NULL,24,'PRIMEIRA ESCOLHA',1),(18,'55125',25,'GVT',1),(19,'55126',26,'IDT',1),(20,'55127',27,'AEROTECH',1),(21,NULL,28,'ALPAMAYO',1),(22,'55132',32,'CONVERGIA',1),(23,NULL,34,'TELEDADOS',1),(24,'55135',35,'EASYTONE',1),(25,'55136',36,'DSLI',1),(26,'55137',37,'GOLDEN LINE',1),(27,NULL,38,'VIPER',1),(28,'55142',42,'GT GROUP',1),(29,NULL,45,'GLOBAL CROSSING (IMPSAT)',1),(30,'55140',46,'HOJE',1),(31,NULL,47,'BT COMMUNICATIONS',1),(32,NULL,48,'PLENNA',1),(33,'55150',49,'CAMBRIDGE',1),(34,'55197',51,'51 BRASIL',1),(35,NULL,52,'LINKNET',1),(36,NULL,54,'TELEBIT',1),(37,NULL,56,'ESPAS',1),(38,'55158',58,'VOITEL',1),(39,'55161',61,'NEXUS',1),(40,'55163',63,'HELLO BRAZIL',1),(41,'55235',64,'NEOTELECOM',1),(42,NULL,65,'CGB VOIP',1),(43,NULL,69,'REDEVOX',1),(44,'55170',72,'LOCAWEB',1),(45,NULL,81,'SERMATEL',1),(46,'55184',84,'BBT BRASIL',1),(47,'55106',85,'AMERICA NET',1),(48,NULL,89,'KONECTA',1),(49,NULL,95,'NEBRACAM',1),(50,'55146',96,'AMIGO',1),(51,'55999',0,'NAO IDENTIFICADO',1),(52,'55173',92,'BRASTEL',1),(53,'55191',92,'IPCORPTELECOM',1),(54,'55112',92,'ALGAR',1);
/*!40000 ALTER TABLE `telefoneOperadora` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `usuario` (
  `senha` varchar(60) DEFAULT NULL,
  `situacao` int(11) DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKiratgo9u85ej8cx9m35knt0ko` FOREIGN KEY (`id`) REFERENCES `colaborador` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES ('$2a$10$tT73rnoTWM9OpHypmWrbZ.5lqtOdMfLhAVOY79eGACX0e6yxSrlqa',1,1),('$2a$10$CZqx5mmOAb8lQCC9wcm/S.CF3QjfVzNYqplmSFlx7RLUPcRr.YJm6',1,2);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-11-12 16:05:41
