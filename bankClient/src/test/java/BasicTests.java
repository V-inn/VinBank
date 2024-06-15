import com.viniciusfk.client.services.Validate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BasicTests {

    @Test
    @DisplayName("test1")
    public void test1() {
        int sum = 222;
        int firstDigit = ((sum%11) <= 1) ? 0 : 11-(sum%11);
        Assertions.assertEquals(0, firstDigit);
    }
}
