
CREATE TABLE Donors ( donor_id INT auto_increment NOT NULL, donor_name VARCHAR(100) NOT NULL, date_started DATE, PRIMARY KEY (donor_id), UNIQUE (donor_name)

);
