import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reader implements IReader {
    public Reader(String filepath) throws FileNotFoundException, URISyntaxException {
        srcFile = new SourceFile(filepath);
        openFile();
        fileReader = new BufferedReader(new FileReader(srcFile.getFile().getPath()));
    }
    public void openFile() throws FileNotFoundException, URISyntaxException {
        srcFile.openInputStream();
        srcFile.canReadFile();
    }

    public void readFile() throws IOException {
        ch_num = fileReader.read();
        //System.out.print(ch =(char)ch_num);
    }

    public void scanCharacters(){

    }

    public int read() throws IOException {
        readFile();
        return ch_num;
    }

    private ISourceFile srcFile;
    private BufferedReader fileReader;
    private int ch_num = 0;
    private char ch;
    private String line;
    private int colPos=0;
    private int rowPos=0;
    private int size = 0;
    private Character [][] charactersList = new Character[50][500];
    private String[] lineStatements = new String[50];
    private String[] instructions = new String[50];
}
