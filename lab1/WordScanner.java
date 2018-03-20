
import java.io.*;

/**
 * This class implements a word (string) scanner
 */
public class WordScanner {

    private FileReader input;
    private int currentLine;
    private int currentPos;
    private int posInLine;
    private char[] content;


    /**
     * Builds a WordScanner object based on the given input
     */
    public WordScanner(FileReader input) throws IOException {
        this.input = input;
        this.currentLine = 1;
        this.currentPos = 0;
        this.posInLine = 1;

        BufferedReader reader = new BufferedReader(this.input);
        String tmpStr = null;
        StringBuffer sb = new StringBuffer();
        while ((tmpStr = reader.readLine()) != null) {
            sb.append(tmpStr);
            sb.append('\n');
        }
        this.content = sb.toString().toCharArray();
    }

    /**
     * Returns the next word from input
     * Precond: there must be at least
     * one word left in the input
     * (i.e. hasNextWord() must evaluate to true)
     */
    public Word nextWord() throws IOException {
        if (hasNextWord()) {

            while (this.currentPos < this.content.length && !Character.isLetter(this.content[this.currentPos])) {
                if (this.content[this.currentPos] == '\n') {
                    this.currentLine++;
                    this.posInLine = 1;
				}else{
					this.posInLine++;
				}
                this.currentPos++;
            }

            Info info = new Info(this.posInLine, this.currentLine);
            StringBuffer sb = new StringBuffer();
            while (Character.isLetter(this.content[this.currentPos]) || isQuoteInWord(this.currentPos)) {
                sb.append(this.content[this.currentPos]);
                this.currentPos++;
                this.posInLine++;
                if (this.content[this.currentPos] == '\n') {
                    this.currentPos++;
                    this.posInLine = 1;
                    this.currentLine++;
                    break;
                }
            }
            Word result = new Word(sb.toString(), info);

            while (this.currentPos < this.content.length && !Character.isLetter(this.content[this.currentPos])) {
                if (this.content[this.currentPos] == '\n') {
                    this.currentLine++;
                    this.posInLine = 1;
                }else{
					this.posInLine++;
				}
                this.currentPos++;
            }

            return result;
        } else {
            return null;
        }
    }

    /**
     * Returns true if there is at least
     * one word left in the input, false otherwise
     */
    public boolean hasNextWord() {
        if (this.currentPos < this.content.length) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns if the given char is a dot in a word
     * true if it is, false if it is at the begin or end of the word
     */
    private boolean isQuoteInWord(int index) {
        if (this.content[index] == '\'') {
            if (index == 1 || index == this.content.length) {
                return false;
            }
            return Character.isLetter(this.content[index - 1]) && Character.isLetter(this.content[index + 1]);
        } else {
            return false;
        }
    }

}
