CREATE TABLE Requests
(
    id           INT auto_increment NOT NULL,
    name         VARCHAR(50) NOT NULL,
    lastName     VARCHAR(50),
    nationalCode VARCHAR(50),
    trackingCode INT,
    verified     BIT
--                                author VARCHAR(20) NOT NULL,
--                                submission_date DATE
);

-- INSERT INTO Requests (name)
-- VALUES ('USA');
