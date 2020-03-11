package Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void getName() {
        User user = new User("1", "eilon" ,"123456", 5000 );
        String name =  user.getName();
        assertNotNull(name);
    }

    @Test
    void getID() {
        User user = new User("1", "eilon" ,"123456", 5000);
        String id =  user.getID();
        assertNotNull(id);
    }

    @Test
    void getPassword() {
        User user = new User("1", "eilon" ,"123456", 5000 );
        String pass =  user.getPassword();
        assertNotNull(pass);
    }

    @Test
    void getBudget() {
        User user = new User("1", "eilon" ,"123456", 5000 );
        double res = 5000;
        double budget =  user.getBudget();
        assertEquals(res , budget);
    }

}