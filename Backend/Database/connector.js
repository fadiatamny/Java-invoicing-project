const sqlite = require('sqlite3');

class Connector {
    constructor(fileName = process.env.SQLITE_FILE || "./database/database.sqlite") {
        this.db = new sqlite.Database(fileName, (err) => {
            if (err) {
                throw {
                    'status': 500,
                    'message': 'Error performing connection',
                    'err': err
                };
            } else {
                this.createTables();
            }
        });

        this.db.configure("busyTimeout", 1000);
    }

    createTables() {
        this.db.run(
            "CREATE TABLE IF NOT EXISTS `Users` (\
            `id` varchar(64) UNIQUE PRIMARY KEY,\
            `password` varchar(256)\
            )"
        );
        this.db.run(
            "CREATE TABLE IF NOT EXISTS `Invoice` (\
            `id` INTEGER PRIMARY KEY AUTOINCREMENT,\
            `UserID` varchar(64),\
            `amount` INTEGER,\
            `description` varchar(256),\
            `date` DATE NOT NULL,\
            FOREIGN KEY (`UserID`) REFERENCES `Users`(`id`)\
            )"
        );
    }

    async getData(sql, params) {
        return new Promise((resolve, reject) => {
            this.db.serialize(() => {
                this.db.all(sql, params, (err, rows) => {
                    if (err) {
                        reject(err);
                        return;
                    }
                    if (rows) {
                        resolve(rows);
                        return;
                    }
                });
            })
        });
    }

    async query(sql, params) {
        return new Promise((resolve, reject) => {
            this.db.run(sql, params, (err) => {
                if (err)
                    reject(err);
                else
                    resolve(undefined)
            });
        });
    }

}

module.exports = Connector;