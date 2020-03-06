const router = require('express').Router();
const InvoiceController = require('../Controller/invoiceController');

router.get('/all/:UserID', (req, res) => {
    InvoiceController.readAll(req, res);
});

router.get('/:id', (req, res) => {
    InvoiceController.read(req, res);
});

router.post('/', (req, res) => {
    InvoiceController.create(req, res);
});

router.update('/:id', (req, res) => {
    InvoiceController.update(req, res);
});

router.delete('/:id', (req, res) => {
    InvoiceController.delete(req, res);
});

module.exports = router;