const router = require('express').Router();
const InvoiceController = require('../Controller/invoiceController');

router.get('/star', (req, res) => {
    InvoiceController.selectstar(req, res);
});

router.get('/all/:UserID', (req, res) => {
    InvoiceController.readAll(req, res);
});

router.get('/:id', (req, res) => {
    InvoiceController.read(req, res);
});

router.post('/create', (req, res) => {
    InvoiceController.create(req, res);
});

router.post('/update/:id', (req, res) => {
    InvoiceController.update(req, res);
});

router.post('/delete/:id', (req, res) => {
    InvoiceController.delete(req, res);
});

module.exports = router;