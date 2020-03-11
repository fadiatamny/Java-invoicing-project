package Controller;

import Model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ControllerTest {

    @Test
    void signUp() {
        Controller c = new Controller();
        User user = c.signUp("1", "eilon" ,"123456", 5000 );
        assertNotNull(user);
    }

    @Test
    void getUserDetails() {
        Controller c = new Controller();
        User user = c.getUserDetails( "eilon" ,"123456");
        assertNotNull(user);
    }

}