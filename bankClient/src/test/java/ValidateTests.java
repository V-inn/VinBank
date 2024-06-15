import com.viniciusfk.client.services.Validate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ValidateTests {
    @Test
    @DisplayName("test1")
    public void test1() {
        Validate validate = new Validate();
        validate.validateCPF("23554152005");
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

    @Test
    @DisplayName("test3")
    public void test3(){
        Validate validate = new Validate();
        validate.validateCPF("");
        Assertions.assertFalse(validate.isValid());
        Assertions.assertEquals("CPF é um campo obrigatório.", validate.getMessage());
    }

    @Test
    @DisplayName("test4")
    public void test4(){
        Validate validate = new Validate();
        validate.validateCPF("o3953819o89");
        Assertions.assertFalse(validate.isValid());
        Assertions.assertEquals("CPF deve conter apenas números.", validate.getMessage());
    }

    @Test
    @DisplayName("test5")
    public void test5(){
        Validate validate = new Validate();
        validate.validateCPF("123457890");
        Assertions.assertFalse(validate.isValid());
        Assertions.assertEquals("CPF deve conter 11 digitos.", validate.getMessage());
    }

    @Test
    @DisplayName("test6")
    public void test6(){
        Validate validate = new Validate();
        validate.validateCPF("00000000000");
        Assertions.assertFalse(validate.isValid());
        Assertions.assertEquals("CPF inválido.", validate.getMessage());
    }
}

