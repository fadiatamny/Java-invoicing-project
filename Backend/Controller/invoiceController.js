
const con = new (require('../Database/connector'))();
const ErrHandler = require('../Utills/errorHandler');

class InvoiceController {
    static async create(req, res) {
        try {
            if (!req.body.UserID || !req.body.amount || !req.body.description || !req.body.date)
                throw { status: 403, message: 'Missing Variables' };
            const query = `INSERT INTO Invoice (UserID, amount, description, date) VALUES (?, ?, ?, ?);`;
            const vars = [req.body.UserID, req.body.amount, req.body.description ? req.body.description : '', req.body.date];
            await con.query(query, vars);
            res.status(200).send('Inserted Successfully');
        } catch (err) {
            ErrHandler.handle(res, err);
        }
    };

    static async read(req, res) {
        try {
            const query = `SELECT * FROM Invoice AND id = ?;`;
            const data = await con.getData(query, [req.params.id]);
            if (data.errno) {
                throw {
                    'status': 500,
                    'message': 'Error performing query',
                };
            }
            res.status(200).send(data);
        } catch (err) {
            ErrHandler.handle(res, err);
        }
    };

    static async readAll(req, res) {
        try {
            const query = `SELECT * FROM Invoice WHERE UserID = ?;`;
            const data = await con.getData(query, [req.params.UserID]);
            if (data.errno) {
                throw {
                    'status': 500,
                    'message': 'Error performing query',
                };
            }
            res.status(200).send(data);
        } catch (err) {
            ErrHandler.handle(res, err);
        }
    };

    static async selectstar(req, res) {
        try {
            const query = `SELECT * FROM Invoice;`;
            const data = await con.getData(query);
            if (data.errno) {
                throw {
                    'status': 500,
                    'message': 'Error performing query',
                };
            }
            res.status(200).send(data);
        } catch (err) {
            ErrHandler.handle(res, err);
        }
    };

    static async update(req, res) {
        try {
            if (!req.body.amount || !req.body.description )
                throw { status: 403, message: 'Missing Variables' };
            const query = `UPDATE Invoice SET amount = ?, description = ? WHERE id = ?;`;
            const data = await con.query(query, [req.body.amount, req.body.description, req.params.id]);
            res.status(200).json(JSON.stringify(data));
        } catch (err) {
            ErrHandler.handle(res, err);
        }
    };

    static async delete(req, res) {
        try {
            const query = `DELETE FROM Invoice WHERE id = ?`;
            const data = await con.query(query, [req.params.id]);
            res.status(200).json(JSON.stringify(data));
        } catch (err) {
            ErrHandler.handle(res, err);
        }
    };
};

module.exports = InvoiceController;