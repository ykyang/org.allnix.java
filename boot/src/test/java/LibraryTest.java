import org.testng.annotations.*;
import static org.testng.Assert.assertTrue;

/*
 * This Java source file was auto generated by running 'gradle init --type java-library --with testng'
 * by 'ykyang' at '12/6/16 7:16 PM' with Gradle 3.2.1
 *
 * @author ykyang, @date 12/6/16 7:16 PM
 */
public class LibraryTest {
    @Test public void someLibraryMethodReturnsTrue() {
        Library classUnderTest = new Library();
        assertTrue(classUnderTest.someLibraryMethod(), "someLibraryMethod should return 'true'");
    }
}
