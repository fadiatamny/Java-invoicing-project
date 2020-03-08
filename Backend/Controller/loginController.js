const con = require('../Database/connector');
const ErrHandler = require('../Utills/errorHandler');

class LoginController {
    static async signUp(req, res) {
        try {
            let query = `INSERT INTO Users (id, name, password, budget) VALUES (?, ?, ?, ?);`;
            await con.query(query, [req.body.id, req.body.name ? req.body.name : req.body.id, req.body.password, req.body.budget ? req.body.budget : 0]);
            res.status(200).send('Inserted Successfully');
        } catch (err) {
            ErrHandler.handle(res, err);
        }
    };

    static async login(req, res) {
        try {
            let query = `SELECT * FROM Users WHERE Users.id = ?;`;
            let result = await con.query(query, [req.body.id]);
            if (result.length == 0) throw {
                status: 409,
                message: 'User doesnt exist'
            };
            result = result[0];
            if (result.password != req.body.password) throw {
                status: 409,
                message: 'Incorrect Password'
            };
            res.status(200).json(result);
        } catch (err) {
            ErrHandler.handle(res, err);
        }
    };
}

module.exports = LoginController;