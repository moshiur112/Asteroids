CREATE TABLE `User` (
    `username` varchar(45) NOT NULL,
    `password` varchar(255) NOT NULL,
    `highScore` int(11) NOT NULL DEFAULT '0',
    PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8