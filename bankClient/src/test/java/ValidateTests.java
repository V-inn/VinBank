import com.viniciusfk.client.services.Validate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ValidateTests {
    @Test
    @DisplayName("test1")
    public void test1() {
        Validate validate = new Validate();
        validate.validateCPF("03509432061");
        Assertions.assertTrue(validate.isValid());
        Assertions.assertEquals("", validate.getMessage());
    }

    @Test
    @DisplayName("test2")
    public void test2(){
        Validate validate = new Validate();
        validate.validateCPF("11111111111");
        Assertions.assertFalse(validate.isValid());
        Assertions.assertEquals("CPF inválido.", validate.getMessage());
    }
}

