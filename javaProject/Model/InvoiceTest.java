package Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceTest {

    @Test
    void getID() {
        Invoice inv = new Invoice(10,4000,"new shoes" , "2020-02-03");
        int res = inv.getID();
        assertEquals(inv,res);
    }

    @Test
    void getAmount() {
        Invoice inv = new Invoice(10,4000,"new shoes" , "2020-02-03");
        double res = inv.getAmount();
        assertEquals(inv,res);
    }

    @Test
    void getDescription() {
        Invoice inv = new Invoice(10,4000,"new shoes" , "2020-02-03");
        String res = inv.getDescription();
        assertNotNull(res);
    }

    @Test
    void getDate() {
        Invoice inv = new Invoice(10,4000,"new shoes" , "2020-02-03");
        String res = inv.getDate();
        assertNotNull(res);
    }
}