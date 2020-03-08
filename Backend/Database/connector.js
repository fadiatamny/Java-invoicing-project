const mysql = require('mysql');

class Connector {

    static async initiate(){
        await Connector.query(
            "CREATE TABLE IF NOT EXISTS `Users` (\
            `id` varchar(64) UNIQUE PRIMARY KEY,\
            `name` varchar(256),\
            `password` varchar(256),\
            `budget` INTEGER\
            )"
        );
        await Connector.query(
            "CREATE TABLE IF NOT EXISTS `Invoice` (\
            `id` INTEGER PRIMARY KEY AUTO_INCREMENT,\
            `UserID` varchar(64),\
            `amount` INTEGER,\
            `description` varchar(256),\
            `date` DATE NOT NULL,\
            FOREIGN KEY (`UserID`) REFERENCES `Users`(`id`)\
            )"
        );
    }

    static _connect() {
        return mysql.createConnection({
            host: process.env.APPSETTING_MYSQL_HOST,
            user: process.env.APPSETTING_MYSQL_USER,
            password: process.env.APPSETTING_MYSQL_PASSWORD,
            database: process.env.APPSETTING_MYSQL_DB
        });
    };

    static async query(sql, args) {
        let con = Connector._connect();

        return new Promise((resolve, reject) => {
            /* Connect to the database */
            con.query(sql, args, (err, rows) => {
                con.destroy();
                if (err) return reject(err);
                resolve(rows);
            });
        });
    }
};

module.exports = Connector;