package filereadertodb;

import java.io.FileNotFoundException;
import static org.junit.Assert.*;
import org.junit.Test;

public class fileReaderTest {
    
    /**
     * Test of fileReader if file does not exist.
     * @throws java.io.FileNotFoundException
     */
    @Test(expected = FileNotFoundException.class)
    public void testfileReaders() throws FileNotFoundException {
            System.out.println("fileReader");
            fileReader instance = new fileReader("test.tx");
    }
    
    /**
     * Test of formatStrings method, of class fileReader.
     * @throws FileNotFoundException
     */
    @Test
    public void formatString() throws FileNotFoundException{
        System.out.println("formatString");
        fileReader instance = new fileReader("test.txt");
        instance.formatStrings();
        String helper = "1,'Mark','Clattenber','Albany','Cardiff'";
        assertEquals(helper, instance.fileContentArrayList.get(1));
    }
    
}
