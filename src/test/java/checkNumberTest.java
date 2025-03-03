import org.testng.Assert;
import org.testng.annotations.Test;

import static olenzing.Main.checkNumberIsSquare;

public class checkNumberTest {
    @Test (groups = {"positive"})
    void positiveTest() {
        Assert.assertTrue(checkNumberIsSquare(4));
        Assert.assertFalse(checkNumberIsSquare(5));
    }

    @Test(groups = {"negative"}, expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Number should be grater than 0")
    void negativeTest() {
        checkNumberIsSquare(-1);
    }
}
