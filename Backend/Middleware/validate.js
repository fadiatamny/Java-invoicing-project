const ErrHandler = require('../Utills/errorHandler');

class Validator{
    static validateLoginRequest(req, res, next) {
        if (!req.body.id || !req.body.password) ErrHandler.handle(res, { status: 403, message: 'Missing Variables' });
        else { if(next)next(); }
    };
}

module.exports = Validator;