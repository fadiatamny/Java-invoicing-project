const router = require('express').Router();
const LoginController = require('../Controller/loginController');
const Validator = require('../Middleware/validate');

router.post('/signup', Validator.validateLoginRequest, (req, res) => {
    LoginController.signUp(req, res);
});
router.post('/login', Validator.validateLoginRequest, (req, res) => {
    LoginController.login(req, res);
});

module.exports = router;