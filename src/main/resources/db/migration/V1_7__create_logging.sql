CREATE TABLE IF NOT EXISTS logging (
       `EVENT_ID` int(11) PRIMARY KEY AUTO_INCREMENT,
	    USER varchar(50) DEFAULT NULL,
       `EVENT_DATE` datetime DEFAULT NULL,
       `LEVEL` varchar(45) DEFAULT NULL,
       `LOGGER` varchar(45) DEFAULT NULL,
       `MSG` varchar(255) DEFAULT NULL,
       `THROWABLE` varchar(45) DEFAULT NULL
)