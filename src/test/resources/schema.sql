CREATE SCHEMA IF NOT EXISTS WKSP_LINKS;

CREATE TABLE IF NOT EXISTS WKSP_LINKS.LINK (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    PATH VARCHAR(255) NOT NULL,
    URL VARCHAR(2000) NOT NULL
);
