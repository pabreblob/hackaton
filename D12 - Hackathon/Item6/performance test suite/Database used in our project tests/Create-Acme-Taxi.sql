start transaction;
create database `Acme-Taxi`;

use `Acme-Taxi`;

create user 'acme-user'@'%' identified by password '*4F10007AADA9EE3DBB2CC36575DFC6F4FDE27577';
create user 'acme-manager'@'%' identified by password '*FDB8CD304EB2317D10C95D797A4BD7492560F55F';

grant select, insert, update, delete 
	on `Acme-Taxi`.* to 'acme-user'@'%';
grant select, insert, update, delete, create, drop, references, index, alter, 
        create temporary tables, lock tables, create view, create routine, 
        alter routine, execute, trigger, show view
    on `Acme-Taxi`.* to 'acme-manager'@'%';

-- MySQL dump 10.13  Distrib 5.5.29, for Win64 (x86)
--
-- Host: localhost    Database: Acme-Taxi
-- ------------------------------------------------------
-- Server version	5.5.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `actor_actor`
--

DROP TABLE IF EXISTS `actor_actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor_actor` (
  `Actor_id` int(11) NOT NULL,
  `blockedUsers_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor_actor`
--

LOCK TABLES `actor_actor` WRITE;
/*!40000 ALTER TABLE `actor_actor` DISABLE KEYS */;
/*!40000 ALTER TABLE `actor_actor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `actor_folder`
--

DROP TABLE IF EXISTS `actor_folder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor_folder` (
  `Actor_id` int(11) NOT NULL,
  `folders_id` int(11) NOT NULL,
  UNIQUE KEY `UK_dp573h40udupcm5m1kgn2bc2r` (`folders_id`),
  CONSTRAINT `FK_dp573h40udupcm5m1kgn2bc2r` FOREIGN KEY (`folders_id`) REFERENCES `folder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor_folder`
--

LOCK TABLES `actor_folder` WRITE;
/*!40000 ALTER TABLE `actor_folder` DISABLE KEYS */;
INSERT INTO `actor_folder` VALUES (238,257),(238,258),(238,259),(238,260),(238,261),(239,262),(239,263),(239,264),(239,265),(239,266),(239,267),(240,268),(240,269),(240,270),(240,271),(240,272),(240,273),(241,274),(241,275),(241,276),(241,277),(241,278),(242,279),(242,280),(242,281),(242,282),(242,283),(243,284),(243,285),(243,286),(243,287),(243,288),(244,289),(244,290),(244,291),(244,292),(244,293),(245,294),(245,295),(245,296),(245,297),(245,298),(246,299),(246,300),(246,301),(246,302),(246,303),(247,304),(247,305),(247,306),(247,307),(247,308),(248,309),(248,310),(248,311),(248,312),(248,313),(249,314),(249,315),(249,316),(249,317),(249,318),(250,319),(250,320),(250,321),(250,322),(250,323),(251,324),(251,325),(251,326),(251,327),(251,328),(252,329),(252,330),(252,331),(252,332),(252,333),(253,334),(253,335),(253,336),(253,337),(253,338),(254,339),(254,340),(254,341),(254,342),(254,343);
/*!40000 ALTER TABLE `actor_folder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `birthdate` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `suspicious` bit(1) NOT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_gfgqmtp2f9i5wsojt33xm0uth` (`userAccount_id`),
  KEY `AdminUK_229satl2waaflnwi4g4u05kfn` (`suspicious`),
  CONSTRAINT `FK_gfgqmtp2f9i5wsojt33xm0uth` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (238,0,'1997-04-28','admin@gmail.com','admin','+34 681331066','admsur','\0',209);
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `announcement`
--

DROP TABLE IF EXISTS `announcement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `announcement` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `cancelled` bit(1) NOT NULL,
  `description` longtext,
  `destination` varchar(255) DEFAULT NULL,
  `marked` bit(1) NOT NULL,
  `moment` datetime DEFAULT NULL,
  `origin` varchar(255) DEFAULT NULL,
  `petsAllowed` bit(1) NOT NULL,
  `pricePerPerson` double NOT NULL,
  `seats` int(11) NOT NULL,
  `smokingAllowed` bit(1) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `creator_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_cb2jqpwq1iw6s35dn4m0qlfnw` (`creator_id`),
  KEY `UK_ft302xb1b7mw4x0ilisk99iba` (`moment`),
  KEY `UK_7u2t8vftqe89fqagumtqmevlu` (`cancelled`),
  KEY `UK_c6y0rx1kc82k1xaldh72cn6x4` (`title`,`origin`,`destination`),
  CONSTRAINT `FK_cb2jqpwq1iw6s35dn4m0qlfnw` FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `announcement`
--

LOCK TABLES `announcement` WRITE;
/*!40000 ALTER TABLE `announcement` DISABLE KEYS */;
INSERT INTO `announcement` VALUES (401,0,'\0','testdescription','Málaga','\0','2019-01-25 12:10:00','Sevilla','',15,3,'\0','announcement1',239),(402,0,'\0','testdescription','Cádiz','\0','2017-01-25 12:10:00','Sevilla','',20,1,'\0','announcement2',239),(403,0,'','testdescription','Cádiz','\0','2017-04-25 12:10:00','Sevilla','',20,1,'\0','announcement3',239),(404,0,'\0','testdescription','Valencia','\0','2017-04-27 17:10:00','Sevilla','',22,2,'\0','announcement4',239),(405,0,'\0','testdescription','Madrid','\0','2018-08-27 17:10:00','Sevilla','',20,3,'\0','announcement5',239),(406,0,'\0','testdescription','Huelva','\0','2019-08-27 17:10:00','Sevilla','',10.5,3,'\0','announcement6',239),(407,0,'\0','sex','Huelva','\0','2018-09-27 17:10:00','Sevilla','',11.5,3,'\0','announcement7',240);
/*!40000 ALTER TABLE `announcement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `announcement_user`
--

DROP TABLE IF EXISTS `announcement_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `announcement_user` (
  `announcements_id` int(11) NOT NULL,
  `attendants_id` int(11) NOT NULL,
  KEY `FK_6vv50xxxiun4mqgmevrxkjpd5` (`attendants_id`),
  KEY `FK_qwwaj1e6qt9q7xavk7fttkkv3` (`announcements_id`),
  CONSTRAINT `FK_qwwaj1e6qt9q7xavk7fttkkv3` FOREIGN KEY (`announcements_id`) REFERENCES `announcement` (`id`),
  CONSTRAINT `FK_6vv50xxxiun4mqgmevrxkjpd5` FOREIGN KEY (`attendants_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `announcement_user`
--

LOCK TABLES `announcement_user` WRITE;
/*!40000 ALTER TABLE `announcement_user` DISABLE KEYS */;
INSERT INTO `announcement_user` VALUES (401,240),(401,241),(401,242),(402,240),(403,241),(404,241),(405,241),(405,240),(406,241),(406,240);
/*!40000 ALTER TABLE `announcement_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `car`
--

DROP TABLE IF EXISTS `car`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `car` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `carModel` varchar(255) DEFAULT NULL,
  `maxPassengers` int(11) NOT NULL,
  `numberPlate` varchar(255) DEFAULT NULL,
  `repairShop_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_8qmhyw3m12ge9349vbhsjunlf` (`repairShop_id`),
  CONSTRAINT `FK_8qmhyw3m12ge9349vbhsjunlf` FOREIGN KEY (`repairShop_id`) REFERENCES `repairshop` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `car`
--

LOCK TABLES `car` WRITE;
/*!40000 ALTER TABLE `car` DISABLE KEYS */;
INSERT INTO `car` VALUES (255,1,'Seat Ibiza',3,'0112DCB',371),(256,1,'Honda Civic',4,'0145FFK',372);
/*!40000 ALTER TABLE `car` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `body` longtext,
  `marked` bit(1) NOT NULL,
  `moment` datetime DEFAULT NULL,
  `announcement_id` int(11) NOT NULL,
  `comment_id` int(11) DEFAULT NULL,
  `creator_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_mwp0pjgvlrr8kabjxirhwl372` (`marked`,`creator_id`,`moment`),
  KEY `UK_k4w36kyhvj9a3pv5mr9q6f1x1` (`announcement_id`,`comment_id`,`moment`),
  KEY `FK_jj21meecc6rep4mlv83ftqft6` (`comment_id`),
  KEY `FK_rqb1hyr2sw3xgojh4lutrler` (`creator_id`),
  CONSTRAINT `FK_rqb1hyr2sw3xgojh4lutrler` FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_jj21meecc6rep4mlv83ftqft6` FOREIGN KEY (`comment_id`) REFERENCES `comment` (`id`),
  CONSTRAINT `FK_p0dokqibs87a4rqmbppwk9c0i` FOREIGN KEY (`announcement_id`) REFERENCES `announcement` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (408,0,'comment1','\0','2017-07-29 12:30:00',401,NULL,239),(409,0,'reply1','\0','2017-07-29 17:30:00',401,408,240),(410,0,'reply2','\0','2017-07-29 18:30:00',401,408,241),(411,0,'comment4','\0','2017-08-29 12:30:00',402,NULL,239),(412,0,'comment5','\0','2017-09-30 12:30:00',402,NULL,241),(413,0,'sex','','2017-08-29 12:30:00',403,NULL,240);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuration`
--

DROP TABLE IF EXISTS `configuration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuration` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `acceptCookiesEng` longtext,
  `acceptCookiesEsp` longtext,
  `advertisementPrice` double NOT NULL,
  `bannerUrl` varchar(255) DEFAULT NULL,
  `contactEng` longtext,
  `contactEsp` longtext,
  `cookiesPolicyEng` longtext,
  `cookiesPolicyEsp` longtext,
  `countryCode` varchar(255) DEFAULT NULL,
  `currency` varchar(255) DEFAULT NULL,
  `footer` longtext,
  `legalTextEng` longtext,
  `legalTextEsp` longtext,
  `limitReportsWeek` int(11) NOT NULL,
  `minimumFee` double NOT NULL,
  `pricePerKm` double NOT NULL,
  `useApi` bit(1) NOT NULL,
  `vat` double NOT NULL,
  `welcomeEng` longtext,
  `welcomeEsp` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuration`
--

LOCK TABLES `configuration` WRITE;
/*!40000 ALTER TABLE `configuration` DISABLE KEYS */;
INSERT INTO `configuration` VALUES (226,0,'By using this site, you accept our cookie policy','Usar este sitio implica aceptar nuestra política de cookies',20,'https://i.imgur.com/rpE1yjp.png','In accord to the article 10 in the law 34/2002 (Law of Information Society Services and Electronic Commerce), dated July 11, we provide you with the following data:<br> ACME,Inc is located in Calle S. Fernando, 4 (C.P. 41004) Seville,Spain.<br> Phone contact available: 954 551 000<br> Email: acme.contact@acme.com<br> In the webpage www.acme.com , there is an amount of information related to the announcements made by the system users, as well as any information they publish or display in their profiles. This information ranges from personal data of the registered users to information related to the announcements, such as geographic location and date.<br> The goal of this data is to make easier for our clients and the general public the access to information related to this company and the services that are provided.<br>','De acuerdo con el artículo 10 de la Ley 34/2002, de 11 de julio, de Servicios de la Sociedad de la Información y de Comercio Electrónico ponemos a su disposición los siguientes datos:<br> ACME,Inc está domiciliada en la calle S. Fernando, 4 (C.P. 41004) Sevilla, España.<br> Teléfono de contacto disponible: 954 551 000<br> Correo electrónico: acme.contact@acme.com<br> En la web www.acme.com hay una serie de contenidos de carácter informativo sobre anuncios publicados por los usuarios del sistema. Estos contenidos abarcan desde información personal de los usuarios registrados hasta información relativa a los anuncios publicado, tales como su localización geográfica y su fecha de realización.<br> Su principal objetivo es facilitar a los clientes y al público en general, la información relativa a la empresa y servicios que se ofrecen.<br>','ACME,Inc on its own or through a third party in charge of measurement services can use cookies when the user browses the website. Cookies are files sent to the internet navigator through a web service with the goal of registering the user\'s activities while browsing.<br> The cookies employed are only associated with an anonymous user and his/her computer, and do not provide on their own personal data related to the user.<br> Through the usage of cookies, the server where the website is hosted is able to recognize the user\'s internet browser with the objective of making the navigation easier.<br> Up to this date, Acme-Taxi use cookies with the objective of remembering the user\'s language preference, whether or not they have accepted the cookie policy and keeping open the user\'s session while they are browsing the site.<br> The user has the choice of configurating their browser in order to be notified of the reception of cookies and to forbid their installation in the computer. Please check your browser\'s use guide in order to expand this information.<br> In any case, cookies are of temporal nature and under no circumstances they will be used to obtain personal data.','ACME,Inc por su propia cuenta o la de un tercero contratado para prestación de servicios de medición, pueden utilizar cookies cuando el usuario navega por el sitio web. Las cookies son ficheros enviados al navegador por medio de un servicio web con la finalidad de registrar las actividades del usuario durante su tiempo de navegación.<br> Las cookies utilizadas se asocian únicamente con un usuario anónimo y su ordenador, y no proporcionan por sí mismas los datos personales del usuario.<br> Mediante el uso de las cookies resulta posible que el servidor donde se encuentra la web reconozca el navegador web utilizado por el usuario con la finalidad de que la navegación sea más sencilla.<br> Actualmente, Acme-Taxi usa cookies con la finalidad de recordar la preferencia de los usuarios acerca del idioma en el que mostrará esta página, para recodar si aceptó la política de cookies y para mantener abierta la sesión de los usuarios mientras navegan esta página web.<br> El usuario tiene la posibilidad de configurar su navegador para ser avisado de la recepción de cookies y para impedir su instalación en su equipo. Por favor consulte las instrucciones y manuales de su navegador para ampliar esta información.<br> En todo caso las cookies tienen un carácter temporal con la única finalidad de hacer más eficaz su transmisión ulterior. En ningún caso se utilizará cookies para recoger información de carácter personal','034','€','Copyright 2018 Acme,Inc','These are the conditions and terms that regulate the use of the site Acme-Taxi, which can be accessed from the URL www.acme.com.<br>   <h1>GENERAL INFORMATION</h1>  In accord to the article 10 in the law 34/2002 (Law of Information Society Services and Electronic Commerce), dated July 11, we provide you with the following data:<br> ACME,Inc is located in Calle S. Fernando, 4 (C.P. 41004) Seville,Spain.<br> Phone contact available: 954 551 000<br> Email: acme.contact@acme.com<br> In the webpage www.acme.com , there is an amount of information related to the announcements made by the system users, as well as any information they publish or display in their profiles. This information ranges from personal data of the registered users to information related to the announcements, such as geographic location and date.<br> The goal of this data is to make easier for our clients and the general public the access to information related to this company and the services that are provided.<br> <h1>USE CONDITIONS</h1>  Access and use conditions are bound by the current law and the trust placed in the good will of our users. Behaviours that go against the law and the rights of third parties are not allowed.<br> Using the webpage www.acme.com implies having read and accepted these use conditions and those that are implied by laws that can be applied to this matter. If by any reason you do not agree with these conditions, please stop using this webpage.<br> <h1>RESPONSIBILITIES</h1>  ACME,Inc does not take any responsibility for the information and contents stored in forums, social networks or any other media that allows third parties to publish contents independently from this webpage.<br> However, taking into account the articles 11 and 16 from LSSI-CE, ACME,Inc makes the compromise of retiring or blocking those contents that may affect or go against national or international laws and third parties\' rights.<br> In addition to that, the company will not take any responsibility for any harm or prejudice caused by errors or mishandling of the software installed in our users\' computers. All responsibility derived from a technical issue or failure originated when the user accesses to the Internet. In the same way, the inexistence of interruptions or errors in the access to the website are not guaranteed.<br> ACME,Inc holds the right of updating, modifying or deleting information contained in its webpage, as well as the configuration and presentation of the aforementioned at any time and without taking any responsibility for doing so.<br> System administrators hold the rights of deleting any publication that may contain sensible material, incites hate or encourages any illegal activity within the context of Spanish Laws.<br> Users who wish their data to be modified or deleted will have to get in contact with ACME,Inc through the channels mentioned in the company\'s general data.<br> <h1>INTELLECTUAL AND INDUSTRIAL PROPERTY</h1>  ACME,Inc owns all the rights related to the digital publication software, in addition to the intellectual and industrial rights about the contents included in the webpage, with the rights of products and services of public nature that are not property of this company being the exception.<br> No content published in this webpage is allowed to be reproduced, copied or published without ACME\'s written consent.<br> All the information received in this site, such as comments, suggestions and ideas will be considered as given to ACME,Inc for free. Information that can NOT be treated this way must not be sent.<br> All the products and services included in this site that are NOT owned by ACME,Inc are registered trademarks belonging to their respective owners, and are recognized as such by our company. They only appear in this site as a way of promotion and information recompilation. Their respective owners can ask for the modification and deletion of the information that belongs to them.<br> <h1>APPLICABLE LAWS AND JURISDICTION</h1>  The conditions this document is composed by are bound by the Spanish Law. Seville Court will hold the competences related to any lawsuit that may arise related to the webpage or the activity carried out through it.<br> <h1>COOKIES POLICY</h1>  ACME,Inc on its own or through a third party in charge of measurement services can use cookies when the user browses the website. Cookies are files sent to the internet navigator through a web service with the goal of registering the user\'s activities while browsing.<br> The cookies employed are only associated with an anonymous user and his/her computer, and do not provide on their own personal data related to the user.<br> Through the usage of cookies, the server where the website is hosted is able to recognize the user\'s internet browser with the objective of making the navigation easier.<br> Up to this date, Acme-Taxi use cookies with the objective of remembering the user\'s language preference, whether or not they have accepted the cookie policy and keeping open the user\'s session while they are browsing the site.<br> The user has the choice of configurating their browser in order to be notified of the reception of cookies and to forbid their installation in the computer. Please check your browser\'s use guide in order to expand this information.<br> In any case, cookies are of temporal nature and under no circumstances they will be used to obtain personal data.',' Estas Condiciones Generales regulan el uso del sitio web de Acme Taxi, al que se accede mediante la dirección URL www.acme.com.<br> <h1>DATOS GENERALES</h1>  De acuerdo con el artículo 10 de la Ley 34/2002, de 11 de julio, de Servicios de la Sociedad de la Información y de Comercio Electrónico ponemos a su disposición los siguientes datos:<br> ACME,Inc está domiciliada en la calle S. Fernando, 4 (C.P. 41004) Sevilla, España.<br> Teléfono de contacto disponible: 954 551 000<br> Correo electrónico: acme.contact@acme.com<br> En la web www.acme.com hay una serie de contenidos de carácter informativo sobre anuncios publicados por los usuarios del sistema. Estos contenidos abarcan desde información personal de los usuarios registrados hasta información relativa a los anuncios publicado, tales como su localización geográfica y su fecha de realización.<br> Su principal objetivo es facilitar a los clientes y al público en general, la información relativa a la empresa y servicios que se ofrecen.<br> <h1>CONDICIONES DE USO</h1>  Las condiciones de acceso y uso del presente sitio web se rigen por la legalidad vigente y por el principio de buena fe comprometiéndose el usuario a realizar un buen uso de la web. No se permiten conductas que vayan contra la ley, los derechos o intereses de terceros.<br> Ser usuario de la web www.acme.com implica que reconoce haber leído y aceptado las presentes condiciones y lo que las extienda la normativa legal aplicable en esta materia. Si por el motivo que fuere no está de acuerdo con estas condiciones no continúe usando esta web.<br> <h1>RESPONSABILIDADES</h1>  ACME,Inc no se hace responsable de la información y contenidos almacenados en foros, redes sociales o cualesquier otro medio que permita a terceros publicar contenidos de forma independiente en la página web del prestador.<br> Sin embargo, teniendo en cuenta los art. 11 y 16 de la LSSI-CE, ACME,Inc se compromete a la retirada o en su caso bloqueo de aquellos contenidos que pudieran afectar o contravenir la legislación nacional o internacional, derechos de terceros o la moral y el orden público.<br> Tampoco la empresa se responsabilizará de los daños y perjuicios que se produzcan por fallos o malas configuraciones del software instalado en el ordenador del internauta. Se excluye toda responsabilidad por alguna incidencia técnica o fallo que se produzca cuando el usuario se conecte a internet. Igualmente no se garantiza la inexistencia de interrupciones o errores en el acceso al sitio web.<br> Así mismo, ACME,Inc se reserva el derecho a actualizar, modificar o eliminar la información contenida en su página web, así como la configuración o presentación del mismo, en cualquier momento sin asumir alguna responsabilidad por ello.<br> Los administradores del sistema se reservan el derecho a eliminar cualquier publicación que contenga material que pueda herir la sensibilidad de los usuarios, incite al odio o promueva cualquier actividad ilegal dentro del marco de la legislación de España.<br> Los usuarios que deseen que sus datos sean modificados o eliminados, deberán ponerse en contacto con ACME,Inc mediante las vías de comunicación descritas en los datos generales de la compañía.<br> <h1>PROPIEDAD INTELECTUAL E INDUSTRIAL</h1>  ACME,Inc es titular de todos los derechos sobre el software de la publicación digital así como de los derechos de propiedad industrial e intelectual referidos a los contenidos que se incluyan, a excepción de los derechos sobre productos y servicios de carácter público que no son propiedad de esta empresa.<br> Ningún material publicado en esta web podrá ser reproducido, copiado o publicado sin el consentimiento por escrito de ACME,Inc. Toda la información que se reciba en la web, como comentarios, sugerencias o ideas, se considerará cedida a ACME,Inc de manera gratuita. No debe enviarse información que NO pueda ser tratada de este modo.<br> Todos los productos y servicios de estas páginas que NO son propiedad ACME,Inc son marcas registradas de sus respectivos propietarios y son reconocidas como tales por nuestra empresa. Solamente aparecen en la web de ACME,Inc a efectos de promoción y de recopilación de información. Estos propietarios pueden solicitar la modificación o eliminación de la información que les pertenece.<br> <h1>LEY APLICABLE Y JURISDICCIÓN</h1>  Las presentes condiciones generales se rigen por la legislación española. Para cualquier litigio que pudiera surgir relacionado con el sitio web o la actividad que en él se desarrolla serán competentes Juzgados de Sevilla, renunciando expresamente el usuario a cualquier otro fuero que pudiera corresponderle. <h1>POLÍTICA DE COOKIES</h1>  ACME,Inc por su propia cuenta o la de un tercero contratado para prestación de servicios de medición, pueden utilizar cookies cuando el usuario navega por el sitio web. Las cookies son ficheros enviados al navegador por medio de un servicio web con la finalidad de registrar las actividades del usuario durante su tiempo de navegación.<br> Las cookies utilizadas se asocian únicamente con un usuario anónimo y su ordenador, y no proporcionan por sí mismas los datos personales del usuario.<br> Mediante el uso de las cookies resulta posible que el servidor donde se encuentra la web reconozca el navegador web utilizado por el usuario con la finalidad de que la navegación sea más sencilla.<br> Actualmente, Acme-Taxi usa cookies con la finalidad de recordar la preferencia de los usuarios acerca del idioma en el que mostrará esta página, para recodar si aceptó la política de cookies y para mantener abierta la sesión de los usuarios mientras navegan esta página web.<br> El usuario tiene la posibilidad de configurar su navegador para ser avisado de la recepción de cookies y para impedir su instalación en su equipo. Por favor consulte las instrucciones y manuales de su navegador para ampliar esta información.<br> En todo caso las cookies tienen un carácter temporal con la única finalidad de hacer más eficaz su transmisión ulterior. En ningún caso se utilizará cookies para recoger información de carácter personal.<br> ',50,5,1.5,'',0.21,'Need a ride?','¿Necesitas dar una vuelta?');
/*!40000 ALTER TABLE `configuration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuration_nationalities`
--

DROP TABLE IF EXISTS `configuration_nationalities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuration_nationalities` (
  `Configuration_id` int(11) NOT NULL,
  `nationalities` varchar(255) DEFAULT NULL,
  KEY `FK_irvo58jisal8hnqfo34kq19t7` (`Configuration_id`),
  CONSTRAINT `FK_irvo58jisal8hnqfo34kq19t7` FOREIGN KEY (`Configuration_id`) REFERENCES `configuration` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuration_nationalities`
--

LOCK TABLES `configuration_nationalities` WRITE;
/*!40000 ALTER TABLE `configuration_nationalities` DISABLE KEYS */;
INSERT INTO `configuration_nationalities` VALUES (226,'Spanish'),(226,'British');
/*!40000 ALTER TABLE `configuration_nationalities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `driver`
--

DROP TABLE IF EXISTS `driver`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `driver` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `birthdate` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `suspicious` bit(1) NOT NULL,
  `userAccount_id` int(11) NOT NULL,
  `idNumber` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `meanRating` double NOT NULL,
  `nationality` varchar(255) DEFAULT NULL,
  `photoUrl` varchar(255) DEFAULT NULL,
  `car_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_nasftgk5fhumafe2s1nem4fr0` (`userAccount_id`),
  KEY `DriverUK_229satl2waaflnwi4g4u05kfn` (`suspicious`),
  KEY `FK_7dwy4l2oh26uwheniod95ipsv` (`car_id`),
  CONSTRAINT `FK_nasftgk5fhumafe2s1nem4fr0` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`),
  CONSTRAINT `FK_7dwy4l2oh26uwheniod95ipsv` FOREIGN KEY (`car_id`) REFERENCES `car` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `driver`
--

LOCK TABLES `driver` WRITE;
/*!40000 ALTER TABLE `driver` DISABLE KEYS */;
INSERT INTO `driver` VALUES (243,1,'1998-01-28','driver1@gmail.com','driver1','+34 681131066','driver1sur','\0',214,'44394959Z','Sevilla',0,'Spanish','https://cdn.business2community.com/wp-content/uploads/2014/04/profile-picture-300x300.jpg',255),(244,1,'1978-01-28','driver2@gmail.com','driver2','+34 681131022','driver2sur','\0',215,'71808857M','Sevilla',4,'Spanish','https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__180.png',256),(245,0,'1989-11-28','driver3@gmail.com','driver3','+34 681144066','driver3sur','\0',216,'20668400W','Sevilla',0,'Spanish','https://www.cheme.cornell.edu/engineering/customcf/iws_ai_faculty_display/ai_images/mjp31-profile.jpg',NULL),(246,0,'1968-09-18','driver4@gmail.com','driver4','+34 681156066','driver4sur','\0',217,'56228561R','Sevilla',0,'Spanish','http://www.eas.cornell.edu/engineering/customcf/iws_ai_faculty_display/ai_images/nmm63-profile.jpg',NULL);
/*!40000 ALTER TABLE `driver` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `driver_review`
--

DROP TABLE IF EXISTS `driver_review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `driver_review` (
  `Driver_id` int(11) NOT NULL,
  `reviews_id` int(11) NOT NULL,
  UNIQUE KEY `UK_afut5dhgnbok9o0jqx79o8ku9` (`reviews_id`),
  KEY `FK_8dc3b4r4nir7duy04rfaxnxa8` (`Driver_id`),
  CONSTRAINT `FK_8dc3b4r4nir7duy04rfaxnxa8` FOREIGN KEY (`Driver_id`) REFERENCES `driver` (`id`),
  CONSTRAINT `FK_afut5dhgnbok9o0jqx79o8ku9` FOREIGN KEY (`reviews_id`) REFERENCES `review` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `driver_review`
--

LOCK TABLES `driver_review` WRITE;
/*!40000 ALTER TABLE `driver_review` DISABLE KEYS */;
INSERT INTO `driver_review` VALUES (244,416);
/*!40000 ALTER TABLE `driver_review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `folder`
--

DROP TABLE IF EXISTS `folder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `folder` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_l1kp977466ddsv762wign7kdh` (`name`),
  KEY `UK_e6lcmpm09goh6x4n16fbq5uka` (`parent_id`),
  CONSTRAINT `FK_e6lcmpm09goh6x4n16fbq5uka` FOREIGN KEY (`parent_id`) REFERENCES `folder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `folder`
--

LOCK TABLES `folder` WRITE;
/*!40000 ALTER TABLE `folder` DISABLE KEYS */;
INSERT INTO `folder` VALUES (257,0,'In box',NULL),(258,0,'Out box',NULL),(259,0,'Notification box',NULL),(260,0,'Trash box',NULL),(261,0,'Spam box',NULL),(262,0,'In box',NULL),(263,0,'Out box',NULL),(264,0,'Notification box',NULL),(265,0,'Trash box',NULL),(266,0,'Spam box',NULL),(267,0,'custom',NULL),(268,0,'In box',NULL),(269,0,'inBoxChild',268),(270,0,'Out box',NULL),(271,0,'Notification box',NULL),(272,0,'Trash box',NULL),(273,0,'Spam box',NULL),(274,0,'In box',NULL),(275,0,'Out box',NULL),(276,0,'Notification box',NULL),(277,0,'Trash box',NULL),(278,0,'Spam box',NULL),(279,0,'In box',NULL),(280,0,'Out box',NULL),(281,0,'Notification box',NULL),(282,0,'Trash box',NULL),(283,0,'Spam box',NULL),(284,0,'In box',NULL),(285,0,'Out box',NULL),(286,0,'Notification box',NULL),(287,0,'Trash box',NULL),(288,0,'Spam box',NULL),(289,0,'In box',NULL),(290,0,'Out box',NULL),(291,0,'Notification box',NULL),(292,0,'Trash box',NULL),(293,0,'Spam box',NULL),(294,0,'In box',NULL),(295,0,'Out box',NULL),(296,0,'Notification box',NULL),(297,0,'Trash box',NULL),(298,0,'Spam box',NULL),(299,0,'In box',NULL),(300,0,'Out box',NULL),(301,0,'Notification box',NULL),(302,0,'Trash box',NULL),(303,0,'Spam box',NULL),(304,0,'In box',NULL),(305,0,'Out box',NULL),(306,0,'Notification box',NULL),(307,0,'Trash box',NULL),(308,0,'Spam box',NULL),(309,0,'In box',NULL),(310,0,'Out box',NULL),(311,0,'Notification box',NULL),(312,0,'Trash box',NULL),(313,0,'Spam box',NULL),(314,0,'In box',NULL),(315,0,'Out box',NULL),(316,0,'Notification box',NULL),(317,0,'Trash box',NULL),(318,0,'Spam box',NULL),(319,0,'In box',NULL),(320,0,'Out box',NULL),(321,0,'Notification box',NULL),(322,0,'Trash box',NULL),(323,0,'Spam box',NULL),(324,0,'In box',NULL),(325,0,'Out box',NULL),(326,0,'Notification box',NULL),(327,0,'Trash box',NULL),(328,0,'Spam box',NULL),(329,0,'In box',NULL),(330,0,'Out box',NULL),(331,0,'Notification box',NULL),(332,0,'Trash box',NULL),(333,0,'Spam box',NULL),(334,0,'In box',NULL),(335,0,'Out box',NULL),(336,0,'Notification box',NULL),(337,0,'Trash box',NULL),(338,0,'Spam box',NULL),(339,0,'In box',NULL),(340,0,'Out box',NULL),(341,0,'Notification box',NULL),(342,0,'Trash box',NULL),(343,0,'Spam box',NULL);
/*!40000 ALTER TABLE `folder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequences`
--

DROP TABLE IF EXISTS `hibernate_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequences`
--

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('DomainEntity',1);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `idnumberpattern`
--

DROP TABLE IF EXISTS `idnumberpattern`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `idnumberpattern` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `nationality` varchar(255) DEFAULT NULL,
  `pattern` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_ccohljfbekk68swecjhpavwbi` (`nationality`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `idnumberpattern`
--

LOCK TABLES `idnumberpattern` WRITE;
/*!40000 ALTER TABLE `idnumberpattern` DISABLE KEYS */;
INSERT INTO `idnumberpattern` VALUES (236,0,'Spanish','^[0-9]{8,8}[A-Za-z]$'),(237,0,'British','^[A-CEGHJ-PR-TW-Z]{1}[A-CEGHJ-NPR-TW-Z]{1}[0-9]{6}[A-DFM]{0,1}$');
/*!40000 ALTER TABLE `idnumberpattern` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mechanic`
--

DROP TABLE IF EXISTS `mechanic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mechanic` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `birthdate` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `suspicious` bit(1) NOT NULL,
  `userAccount_id` int(11) NOT NULL,
  `idNumber` varchar(255) DEFAULT NULL,
  `nationality` varchar(255) DEFAULT NULL,
  `photoUrl` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1u242cht3thx8eaccgbs2puqc` (`userAccount_id`),
  KEY `MechanicUK_229satl2waaflnwi4g4u05kfn` (`suspicious`),
  CONSTRAINT `FK_1u242cht3thx8eaccgbs2puqc` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mechanic`
--

LOCK TABLES `mechanic` WRITE;
/*!40000 ALTER TABLE `mechanic` DISABLE KEYS */;
INSERT INTO `mechanic` VALUES (247,0,'1965-11-18','mechanic1@gmail.com','mechanic1','+34 681116066','mechanic1sur','\0',218,'73028767H','Spanish','https://uttarauniversity.edu.bd/department/assets/eee/assets/pages/media/profile/profile-img.jpg'),(248,0,'1988-06-17','mechanic2@gmail.com','mechanic2','+34 681346066','mechanic2sur','',219,'66296704D','Spanish','https://www.planetdance.com.au/wp-content/uploads/2017/12/jade-profile.jpg'),(249,0,'1987-07-17','mechanic3@gmail.com','mechanic3','+34 681343066','mechanic3sur','\0',220,'92976051T','Spanish','https://www.planetdance.com.au/wp-content/uploads/2017/12/clive-smither-profile.jpg'),(250,0,'1987-08-27','mechanic4@gmail.com','mechanic4','+34 681743066','mechanic4sur','\0',221,'22102911A','Spanish','https://www.planetdance.com.au/wp-content/uploads/2017/12/robert-jeffrey-profile.jpg');
/*!40000 ALTER TABLE `mechanic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `body` longtext,
  `checked` bit(1) NOT NULL,
  `moment` datetime DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `folder_id` int(11) NOT NULL,
  `sender_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_7t1ls63lqb52igs4ms20cf94t` (`folder_id`),
  KEY `UK_5p7v56gx9xgyngxjlo1hfxeri` (`checked`),
  CONSTRAINT `FK_7t1ls63lqb52igs4ms20cf94t` FOREIGN KEY (`folder_id`) REFERENCES `folder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (344,0,'bodymessage1','','2017-02-24 23:00:00',2,'message1',258,238),(345,0,'bodymessage1','\0','2017-02-24 23:00:00',2,'message1',262,238),(346,0,'bodymessage2','','2017-02-24 23:02:00',2,'message2',258,238),(347,0,'bodymessage2','','2017-02-24 23:02:00',2,'message2',262,238),(348,0,'bodymessage3','','2017-02-24 23:03:00',2,'message3',258,238),(349,0,'bodymessage3','','2017-02-24 23:03:00',2,'message3',262,238),(350,0,'bodymessage4','','2017-02-24 23:04:00',2,'message4',258,238),(351,0,'bodymessage4','\0','2017-02-24 23:04:00',2,'message4',262,238),(352,0,'bodymessage5','','2017-02-24 23:05:00',2,'message5',258,238),(353,0,'bodymessage5','','2017-02-24 23:05:00',2,'message5',262,238),(354,0,'bodymessage6','','2017-02-24 23:06:00',2,'message6',258,238),(355,0,'bodymessage6','\0','2017-02-24 23:06:00',2,'message6',262,238),(356,0,'viagra','','2017-02-24 23:07:00',2,'message7',270,240),(357,0,'viagra','','2017-02-24 23:07:00',2,'message7',278,240);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_actor`
--

DROP TABLE IF EXISTS `message_actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_actor` (
  `Message_id` int(11) NOT NULL,
  `recipients_id` int(11) NOT NULL,
  KEY `FK_2340xdahcha0b5cyr6bxhq6ji` (`Message_id`),
  CONSTRAINT `FK_2340xdahcha0b5cyr6bxhq6ji` FOREIGN KEY (`Message_id`) REFERENCES `message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_actor`
--

LOCK TABLES `message_actor` WRITE;
/*!40000 ALTER TABLE `message_actor` DISABLE KEYS */;
INSERT INTO `message_actor` VALUES (344,239),(345,239),(346,239),(347,239),(348,239),(349,239),(350,239),(351,239),(352,239),(353,239),(354,239),(355,239),(356,241),(357,241);
/*!40000 ALTER TABLE `message_actor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repairshop`
--

DROP TABLE IF EXISTS `repairshop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repairshop` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` longtext,
  `location` varchar(255) DEFAULT NULL,
  `marked` bit(1) NOT NULL,
  `meanRating` double NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photoUrl` varchar(255) DEFAULT NULL,
  `mechanic_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_lpo6k9y8pt600ww4jtas5x1cb` (`mechanic_id`),
  KEY `UK_fom2kfiujrim4vk50jc76rx5b` (`marked`),
  KEY `UK_ktxd0j8dans4kjry49c8hy3u7` (`meanRating`),
  CONSTRAINT `FK_lpo6k9y8pt600ww4jtas5x1cb` FOREIGN KEY (`mechanic_id`) REFERENCES `mechanic` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repairshop`
--

LOCK TABLES `repairshop` WRITE;
/*!40000 ALTER TABLE `repairshop` DISABLE KEYS */;
INSERT INTO `repairshop` VALUES (371,0,'desc1','Testloc1','\0',4,'repairshop1','691331099','http://www.camiondedesatranques.es/images/Desatascos_01.jpg',247),(372,0,'desc2','Testloc2','\0',0,'repairshop2','691336799','https://fotos00.autofacil.es/2014/10/20/646x260/taller.jpg',247),(373,0,'desc3','Testloc3','\0',0,'repairshop3','691397099','https://fotos00.autofacil.es/2014/10/20/646x260/taller.jpg',247),(374,0,'desc4','Testloc4','\0',0,'repairshop4','691551099','https://fotos00.autofacil.es/2014/10/20/646x260/taller.jpg',247),(375,0,'desc5','Testloc5','\0',0,'repairshop5','691301099','https://fotos00.autofacil.es/2014/10/20/646x260/taller.jpg',247),(376,0,'desc6','Testloc6','\0',0,'repairshop6','691331099','https://fotos00.autofacil.es/2014/10/20/646x260/taller.jpg',247),(377,0,'sex','Testloc7','',0,'repairshop7','691312099','https://fotos00.autofacil.es/2014/10/20/646x260/taller.jpg',248);
/*!40000 ALTER TABLE `repairshop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repairshop_review`
--

DROP TABLE IF EXISTS `repairshop_review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repairshop_review` (
  `RepairShop_id` int(11) NOT NULL,
  `reviews_id` int(11) NOT NULL,
  UNIQUE KEY `UK_5e7w4qsxp9ul13lujextk8kcq` (`reviews_id`),
  KEY `FK_tae1fhqk3nhmpnmb8lx5egnae` (`RepairShop_id`),
  CONSTRAINT `FK_tae1fhqk3nhmpnmb8lx5egnae` FOREIGN KEY (`RepairShop_id`) REFERENCES `repairshop` (`id`),
  CONSTRAINT `FK_5e7w4qsxp9ul13lujextk8kcq` FOREIGN KEY (`reviews_id`) REFERENCES `review` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repairshop_review`
--

LOCK TABLES `repairshop_review` WRITE;
/*!40000 ALTER TABLE `repairshop_review` DISABLE KEYS */;
INSERT INTO `repairshop_review` VALUES (371,415);
/*!40000 ALTER TABLE `repairshop_review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `report` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `checked` bit(1) NOT NULL,
  `imageUrl` varchar(255) DEFAULT NULL,
  `moment` datetime DEFAULT NULL,
  `reason` longtext,
  `creator_id` int(11) NOT NULL,
  `reported_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_l87b1o3wweh63bop9hn7ge5il` (`checked`),
  KEY `UK_n6ofus7flsotln2q8c4dyy8bi` (`creator_id`,`moment`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report`
--

LOCK TABLES `report` WRITE;
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
INSERT INTO `report` VALUES (358,0,'',NULL,'2018-02-20 23:00:00','report1',239,240),(359,0,'','https://liquipedia.net/commons/images/f/fd/Team_Random_logo.png','2018-02-20 23:50:00','report2',241,248),(360,0,'',NULL,'2018-02-20 23:40:00','report3',239,243),(361,0,'',NULL,'2018-02-20 23:30:00','report4',242,240),(362,0,'\0',NULL,'2018-02-20 23:20:00','report5',244,240),(363,0,'',NULL,'2018-02-20 23:10:00','report6',247,240);
/*!40000 ALTER TABLE `report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `request`
--

DROP TABLE IF EXISTS `request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `request` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `cancelled` bit(1) NOT NULL,
  `comment` longtext,
  `destination` varchar(255) DEFAULT NULL,
  `distance` double NOT NULL,
  `estimatedTime` double NOT NULL,
  `marked` bit(1) NOT NULL,
  `moment` datetime DEFAULT NULL,
  `origin` varchar(255) DEFAULT NULL,
  `passengersNumber` int(11) NOT NULL,
  `price` double NOT NULL,
  `driver_id` int(11) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_m538jbtxikfbwt6f4kh95ypw4` (`driver_id`),
  KEY `UK_s5eus487wpn21f11374pfpl9x` (`moment`,`passengersNumber`),
  KEY `UK_q4mmr2802onk8qkgjot6poi9i` (`marked`),
  KEY `FK_lsq6snl5hdbnrqo9cm8n40ssh` (`user_id`),
  CONSTRAINT `FK_lsq6snl5hdbnrqo9cm8n40ssh` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_m538jbtxikfbwt6f4kh95ypw4` FOREIGN KEY (`driver_id`) REFERENCES `driver` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `request`
--

LOCK TABLES `request` WRITE;
/*!40000 ALTER TABLE `request` DISABLE KEYS */;
INSERT INTO `request` VALUES (392,0,'\0','testcomment','Greco, Sevilla, Sevilla, España',5100,995,'\0','2018-09-25 12:10:00','Reina Mercedes, Sevilla, Sevilla, España',1,15,NULL,239),(393,0,'\0','testcomment','Avenida de España, Ubrique, Cadiz, España',876,301,'\0','2017-09-25 12:10:00','San Pablo, Ubrique, Cadiz, España',2,7,244,239),(394,0,'','testcomment','Jabugo, Sevilla, Sevilla, España',5184,1035,'\0','2018-09-25 12:10:00','Bami, Sevilla, Sevilla, España',1,15,243,239),(395,0,'\0','testcomment','Jabugo, Sevilla, Sevilla, España',1823,476,'\0','2018-10-25 12:10:00','Greco, Sevilla, Sevilla, España',2,9,244,239),(396,0,'\0','testcomment','Jabugo, Sevilla, Sevilla, España',5184,1035,'\0','2017-09-15 12:10:00','Bami, Sevilla, Sevilla, España',1,15,245,239),(397,0,'\0','testcomment','Avenida de España, Ubrique, Cadiz, España',876,301,'\0','2017-03-15 12:10:00','San Pablo, Ubrique, Cadiz, España',1,7,NULL,239),(398,0,'\0','testcomment','Greco, Sevilla, Sevilla, España',5100,995,'\0','2018-09-15 22:10:00','Reina Mercedes, Sevilla, Sevilla, España',1,15,244,239),(399,0,'\0','testcomment','Greco, Sevilla, Sevilla, España',5100,995,'\0','2015-10-05 12:10:00','Reina Mercedes, Sevilla, Sevilla, España',2,15,NULL,241),(400,0,'\0','sex','Greco, Sevilla, Sevilla, España',5100,995,'','2019-01-25 12:10:00','Reina Mercedes, Sevilla, Sevilla, España',1,15,NULL,240);
/*!40000 ALTER TABLE `request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reservation` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `cancelled` bit(1) NOT NULL,
  `comment` longtext,
  `moment` datetime DEFAULT NULL,
  `service_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_4ti5w6pa3dov5bg0e2tbrxro2` (`service_id`,`moment`),
  KEY `UK_gstjxxhe8016e8unq5bpp4itt` (`service_id`),
  KEY `UK_46ntgteqjumc4bxn8g87dhpqd` (`moment`),
  KEY `FK_d6fljaihtws3py1326ie6863d` (`user_id`),
  CONSTRAINT `FK_d6fljaihtws3py1326ie6863d` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_gstjxxhe8016e8unq5bpp4itt` FOREIGN KEY (`service_id`) REFERENCES `service` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation`
--

LOCK TABLES `reservation` WRITE;
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
INSERT INTO `reservation` VALUES (385,0,'\0','testcomment','2019-02-20 15:10:00',378,239),(386,0,'\0','testcomment','2016-02-21 17:10:00',379,239),(387,0,'','testcomment','2019-09-20 18:10:00',380,239),(388,0,'\0','testcomment','2019-10-20 18:10:00',381,239),(389,0,'\0','testcomment','2019-09-20 19:10:00',382,239),(390,0,'\0','testcomment','2019-09-25 13:10:00',378,240),(391,0,'\0','testcomment','2019-09-25 13:10:00',378,241);
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `body` longtext,
  `marked` bit(1) NOT NULL,
  `moment` datetime DEFAULT NULL,
  `rating` int(11) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `creator_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_6v3omk12r0xdpayiw8qgsdra0` (`creator_id`),
  CONSTRAINT `FK_6v3omk12r0xdpayiw8qgsdra0` FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` VALUES (414,0,'review1','\0','2018-01-30 20:00:00',4,'review1',240),(415,0,'review2','\0','2018-01-30 20:03:00',4,'review2',239),(416,0,'review3','\0','2018-01-30 20:05:00',4,'review3',239);
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `service` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `price` double NOT NULL,
  `suspended` bit(1) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `repairShop_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_d8kd9e42oylsipt3pufujwff4` (`repairShop_id`),
  CONSTRAINT `FK_d8kd9e42oylsipt3pufujwff4` FOREIGN KEY (`repairShop_id`) REFERENCES `repairshop` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service`
--

LOCK TABLES `service` WRITE;
/*!40000 ALTER TABLE `service` DISABLE KEYS */;
INSERT INTO `service` VALUES (378,0,12,'\0','service1',371),(379,0,20,'\0','service2',371),(380,0,14,'','service3',371),(381,0,12.5,'\0','service4',371),(382,0,70.5,'\0','service5',371),(383,0,25,'\0','service6',371),(384,0,22.5,'\0','service7',372);
/*!40000 ALTER TABLE `service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spamword`
--

DROP TABLE IF EXISTS `spamword`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `spamword` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `word` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_hw2ujbfk0k77o2np1stk65lv2` (`word`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spamword`
--

LOCK TABLES `spamword` WRITE;
/*!40000 ALTER TABLE `spamword` DISABLE KEYS */;
INSERT INTO `spamword` VALUES (227,0,'viagra'),(228,0,'cialis'),(229,0,'jes extender'),(230,0,'sex'),(231,0,'sexo'),(232,0,'fuck'),(233,0,'pills'),(234,0,'pene'),(235,0,'cock');
/*!40000 ALTER TABLE `spamword` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sponsor`
--

DROP TABLE IF EXISTS `sponsor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sponsor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `birthdate` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `suspicious` bit(1) NOT NULL,
  `userAccount_id` int(11) NOT NULL,
  `idNumber` varchar(255) DEFAULT NULL,
  `nationality` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_okfx8h0cn4eidh8ng563vowjc` (`userAccount_id`),
  KEY `SponsorUK_229satl2waaflnwi4g4u05kfn` (`suspicious`),
  CONSTRAINT `FK_okfx8h0cn4eidh8ng563vowjc` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sponsor`
--

LOCK TABLES `sponsor` WRITE;
/*!40000 ALTER TABLE `sponsor` DISABLE KEYS */;
INSERT INTO `sponsor` VALUES (251,0,'1977-09-27','sponsor1@gmail.com','sponsor1','+34 683743066','sponsor1sur','\0',222,'93277207V','Spanish'),(252,0,'1987-03-27','sponsor2@gmail.com','sponsor2','+34 683843096','sponsor2sur','\0',223,'45693368G','Spanish'),(253,0,'1989-03-22','sponsor3@gmail.com','sponsor3','+34 683847796','sponsor3sur','\0',224,'07092078M','Spanish'),(254,0,'1979-12-22','sponsor4@gmail.com','sponsor4','+34 688846796','sponsor4sur','\0',225,'88547463Q','Spanish');
/*!40000 ALTER TABLE `sponsor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sponsorship`
--

DROP TABLE IF EXISTS `sponsorship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sponsorship` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `accepted` bit(1) NOT NULL,
  `cancelled` bit(1) NOT NULL,
  `brandName` varchar(255) DEFAULT NULL,
  `cvv` int(11) NOT NULL,
  `expMonth` int(11) NOT NULL,
  `expYear` int(11) NOT NULL,
  `holderName` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `moment` datetime DEFAULT NULL,
  `pictureUrl` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `sponsor_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_e3idyfyffpufog3sjl3c2ulun` (`sponsor_id`),
  KEY `UK_4pv223bcc848ch800cu9v3glf` (`accepted`),
  CONSTRAINT `FK_e3idyfyffpufog3sjl3c2ulun` FOREIGN KEY (`sponsor_id`) REFERENCES `sponsor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sponsorship`
--

LOCK TABLES `sponsorship` WRITE;
/*!40000 ALTER TABLE `sponsorship` DISABLE KEYS */;
INSERT INTO `sponsorship` VALUES (364,0,'','\0','brand1',134,11,18,'sponsor1','4024007154986433','2017-02-20 23:10:00','https://www.racefoxx.com/media/image/product/2242/md/yamaha-r1-carbonfibre-engine-cover-clutch-glossy~3.jpg',20,251),(365,0,'','\0','brand1',134,11,18,'sponsor1','4024007154986433','2017-02-20 23:10:00','https://www.racefoxx.com/media/image/product/2242/md/yamaha-r1-carbonfibre-engine-cover-clutch-glossy~3.jpg',20,251),(366,0,'','\0','brand1',134,11,18,'sponsor1','4024007154986433','2017-02-20 23:10:00','https://www.coches.com/fotos_historicas/honda/Civic/high_b04018aad396f2110939e61555d59fe4.jpg',20,251),(367,0,'','\0','brand1',134,11,18,'sponsor1','4024007154986433','2017-02-20 23:10:00','https://wordlesstech.com/wp-content/uploads/2012/08/Creative-Advertisements-by-Asile-1.jpg',20,251),(368,0,'\0','\0','brand1',134,11,18,'sponsor1','4024007154986433','2017-02-20 23:10:00','http://creativecriminals.com/pictures/post/2470/christmasadvertisements17.jpg',20,251),(369,0,'','\0','brand1',144,11,20,'agent2','4716213778910154','2017-02-20 23:10:00','http://creativecriminals.com/pictures/post/2470/christmasadvertisements24.jpg',20,252),(370,0,'\0','\0','brand1',144,11,20,'agent2','4716213778910154','2017-02-20 23:16:00','https://cdn.pixabay.com/photo/2016/09/08/18/45/cube-1655118_1280.jpg',20,252);
/*!40000 ALTER TABLE `sponsorship` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `birthdate` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `suspicious` bit(1) NOT NULL,
  `userAccount_id` int(11) NOT NULL,
  `location` varchar(255) DEFAULT NULL,
  `meanRating` double NOT NULL,
  `photoUrl` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_o6s94d43co03sx067ili5760c` (`userAccount_id`),
  KEY `UserUK_229satl2waaflnwi4g4u05kfn` (`suspicious`),
  CONSTRAINT `FK_o6s94d43co03sx067ili5760c` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (239,0,'1992-05-27','user1@gmail.com','user1','+34 681231086','user1sur','\0',210,'Sevilla',4,'https://assets.entrepreneur.com/content/3x2/1300/20150406145944-dos-donts-taking-perfect-linkedin-profile-picture-selfie-mobile-camera-2.jpeg'),(240,0,'1994-05-27','user2@gmail.com','user2','+34 689231086','user2sur','',211,'Sevilla',0,'https://i2.cdn.turner.com/cnnnext/dam/assets/140926165711-john-sutter-profile-image-large-169.jpg'),(241,0,'1992-09-17','user3@gmail.com','user3','+34 689231081','user3sur','\0',212,'Sevilla',0,'http://keenthemes.com/preview/metronic/theme/assets/pages/media/profile/profile_user.jpg'),(242,0,'1993-09-17','user4@gmail.com','user4','+34 689234081','user4sur','\0',213,'Sevilla',0,'https://i.pinimg.com/236x/c6/a4/64/c6a4645d9f9af45a9c9d7b094c18a47a--portrait-ideas-girl-photos.jpg');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_review`
--

DROP TABLE IF EXISTS `user_review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_review` (
  `User_id` int(11) NOT NULL,
  `reviews_id` int(11) NOT NULL,
  UNIQUE KEY `UK_iujqosqp0i596709y5dp7ccms` (`reviews_id`),
  KEY `FK_51t09j0nqts9nxii50v5teiq8` (`User_id`),
  CONSTRAINT `FK_51t09j0nqts9nxii50v5teiq8` FOREIGN KEY (`User_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_iujqosqp0i596709y5dp7ccms` FOREIGN KEY (`reviews_id`) REFERENCES `review` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_review`
--

LOCK TABLES `user_review` WRITE;
/*!40000 ALTER TABLE `user_review` DISABLE KEYS */;
INSERT INTO `user_review` VALUES (239,414);
/*!40000 ALTER TABLE `user_review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount`
--

DROP TABLE IF EXISTS `useraccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `banned` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_csivo9yqa08nrbkog71ycilh5` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount`
--

LOCK TABLES `useraccount` WRITE;
/*!40000 ALTER TABLE `useraccount` DISABLE KEYS */;
INSERT INTO `useraccount` VALUES (209,0,'\0','21232f297a57a5a743894a0e4a801fc3','admin'),(210,0,'\0','24c9e15e52afc47c225b757e7bee1f9d','user1'),(211,0,'\0','7e58d63b60197ceb55a1c487989a3720','user2'),(212,0,'\0','92877af70a45fd6a2ed7fe81e1236b78','user3'),(213,0,'\0','3f02ebe3d7929b091e3d8ccfde2f3bc6','user4'),(214,0,'\0','b85aef08608180db9d4ddad38ae40545','driver1'),(215,0,'\0','d95784faa6597a0253e483e500ced3ee','driver2'),(216,0,'\0','e44435627359f4c3de71b538cdefdc14','driver3'),(217,0,'\0','a727098b2711281f6bee057b613ccd5e','driver4'),(218,0,'\0','e4b4a923a7b5464851a9de1eb0edcc5c','mechanic1'),(219,0,'\0','ca9e72a6be452c0e6531e885234bea5a','mechanic2'),(220,0,'\0','67384e2ed316bbcdf2d74b9ac29681e7','mechanic3'),(221,0,'\0','7ae311e9979768b5070158bc737ec061','mechanic4'),(222,0,'\0','42c63ad66d4dc07ed17753772bef96d6','sponsor1'),(223,0,'\0','3dc67f80a03324e01b1640f45d107485','sponsor2'),(224,0,'\0','857a54956061fdc1b88d7722cafe6519','sponsor3'),(225,0,'\0','a8be034fe44a453e912feadc414dc803','sponsor4');
/*!40000 ALTER TABLE `useraccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount_authorities`
--

DROP TABLE IF EXISTS `useraccount_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount_authorities` (
  `UserAccount_id` int(11) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  KEY `FK_b63ua47r0u1m7ccc9lte2ui4r` (`UserAccount_id`),
  CONSTRAINT `FK_b63ua47r0u1m7ccc9lte2ui4r` FOREIGN KEY (`UserAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount_authorities`
--

LOCK TABLES `useraccount_authorities` WRITE;
/*!40000 ALTER TABLE `useraccount_authorities` DISABLE KEYS */;
INSERT INTO `useraccount_authorities` VALUES (209,'ADMIN'),(210,'USER'),(211,'USER'),(212,'USER'),(213,'USER'),(214,'DRIVER'),(215,'DRIVER'),(216,'DRIVER'),(217,'DRIVER'),(218,'MECHANIC'),(219,'MECHANIC'),(220,'MECHANIC'),(221,'MECHANIC'),(222,'SPONSOR'),(223,'SPONSOR'),(224,'SPONSOR'),(225,'SPONSOR');
/*!40000 ALTER TABLE `useraccount_authorities` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-30 21:16:33
commit;