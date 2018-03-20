// This part is added as-it-is on top of the generated scanner
//
import java_cup.runtime.*; // defines the Symbol class
// The generated scanner will return a Symbol for each token that it finds.
// A Symbol contains an Object field named value; that field will be of type
// TokenVal, defined below.
//
// A TokenVal object contains the line number on which the token occurs as
// well as the number of the character on that line that starts the token.
// Some tokens (literals and IDs) also include the value of the token.
class TokenVal {
      private int lineNum;
      private int charPos;
      TokenVal(int lineNum, int charPos){
            this.lineNum = lineNum;
            this.charPos = charPos;
      }
}
class IntLitTokenVal extends TokenVal {
      public int intVal;
      IntLitTokenVal(int lineNum, int charPos, int intVal){
            super(lineNum, charPos);
            this.intVal = intVal;
      }
}
class IdTokenVal extends TokenVal {
      public String idVal;
      IdTokenVal(int lineNum, int charPos, String idVal){
            super(lineNum, charPos);
            this.idVal = idVal;
      }
}
class StrLitTokenVal extends TokenVal {
      public String strVal;
      StrLitTokenVal(int lineNum, int charPos, String strVal){
            super(lineNum, charPos);
            this.strVal = strVal;
      }
}
// The following class is used to keep track of the character number at which
// the current token starts on its line.
class CharNum {
      static int lineNum = 1;
      static int colNum = 1;
      static int line = 1;
      static int col = 1;
      static void setPos() {
            lineNum = line;
            colNum = col;
      }
}


class Yylex implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yyline;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	Yylex (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	Yylex (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private Yylex () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yyline = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;
	}

	private boolean yy_eof_done = false;
	private final int YYINITIAL = 0;
	private final int yy_state_dtrans[] = {
		0
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		int i;
		for (i = yy_buffer_start; i < yy_buffer_index; ++i) {
			if ('\n' == yy_buffer[i] && !yy_last_was_cr) {
				++yyline;
			}
			if ('\r' == yy_buffer[i]) {
				++yyline;
				yy_last_was_cr=true;
			} else yy_last_was_cr=false;
		}
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NOT_ACCEPT,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NOT_ACCEPT,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NOT_ACCEPT,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NOT_ACCEPT,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NOT_ACCEPT,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NOT_ACCEPT,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NOT_ACCEPT,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NOT_ACCEPT,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NOT_ACCEPT,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NOT_ACCEPT,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NOT_ACCEPT,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NOT_ACCEPT,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NOT_ACCEPT,
		/* 83 */ YY_NOT_ACCEPT,
		/* 84 */ YY_NOT_ACCEPT,
		/* 85 */ YY_NO_ANCHOR,
		/* 86 */ YY_NOT_ACCEPT,
		/* 87 */ YY_NOT_ACCEPT,
		/* 88 */ YY_NOT_ACCEPT,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NO_ANCHOR,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NO_ANCHOR,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NO_ANCHOR,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NO_ANCHOR,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"45:9,36,39,45:2,43,45:18,36,12,41,37,45:2,14,42,5,11,31,1,19,10,21,2,30:10," +
"45,13,9,3,4,42,45,40:4,44,40:21,45,38,45:2,40,45,35,16,26,28,23,20,40,34,6," +
"40:2,18,40,7,17,40:2,22,32,8,24,27,33,40:3,25,15,29,45:2,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,112,
"0,1,2,3,4,5,1,6,7,8,1,9,1,10,1:4,11,1:2,3,1:5,12,1:6,13,1,12:2,14,12:5,1,12" +
":4,15,12,16,1:2,17,18,1,19,20:2,21,15,22:2,23,24,25,26,27,28,29,30,24,31,32" +
",33,14,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56" +
",57,58,59,60,61,62,63,64,65,66,67,68")[0];

	private int yy_nxt[][] = unpackFromString(69,46,
"1,2,3,4,5,6,7,50,98,8,9,10,11,12,13,51,99,50:2,14,106,15,110,100,50,16,85,1" +
"01,50,17,18,19,111,107,50:2,20,21,56,22,50,61,56,-1,50,56,-1:47,23,-1:45,21" +
":38,-1,21:3,-1,21:2,-1:3,24,-1:45,25,26,-1:47,50,55,50,-1:7,50:3,-1,27,-1,5" +
"0:3,-1,50:3,-1,50,-1,50:4,-1:4,50,-1:3,50,-1:4,28,-1:5,29,-1:46,30,-1:38,31" +
",-1:56,32,-1:61,18,-1:21,50:3,-1:7,50:3,-1,50,-1,50:3,-1,50:3,-1,50,-1,50:4" +
",-1:4,50,-1:3,50,-1:2,34:37,68,34:2,52,34:4,-1,76:37,-1,76:2,44,76:4,-1,49:" +
"37,54,34,49,35,49:2,84,49,-1:15,33,-1:31,59:6,63:2,59:29,63,38,59,66,63,59," +
"86,59,-1:6,50:2,36,-1:7,50:3,-1,50,-1,50:3,-1,50:3,-1,50,-1,50:4,-1:4,50,-1" +
":3,50,-1:2,57:37,54,34,57,52,57:4,-1,59:37,72,38,59,44,59:2,86,59,-1:6,50,3" +
"7,50,-1:7,50:3,-1,50,-1,50:3,-1,50:3,-1,50,-1,50:4,-1:4,50,-1:3,50,-1:2,63:" +
"37,74,53,63,35,63:2,87,63,-1:6,50:3,-1:7,50:3,-1,50,-1,50,39,50,-1,50:3,-1," +
"50,-1,50:4,-1:4,50,-1:3,50,-1:2,72:38,53,72,-1,72:2,88,72,-1,66:37,78,-1,66" +
",35,66:4,-1:6,50:3,-1:7,50:2,40,-1,50,-1,50:3,-1,50:3,-1,50,-1,50:4,-1:4,50" +
",-1:3,50,-1:2,76:6,-1:2,76:29,-1,76:2,-1:2,76:3,-1:6,50:3,-1:7,50:3,-1,50,-" +
"1,50,41,50,-1,50:3,-1,50,-1,50:4,-1:4,50,-1:3,50,-1:2,49:19,57,49:17,54,34," +
"49,35,49:2,84,49,-1:6,50:2,42,-1:7,50:3,-1,50,-1,50:3,-1,50:3,-1,50,-1,50:4" +
",-1:4,50,-1:3,50,-1:7,50:3,-1:7,50:3,-1,50,-1,50:3,-1,50:2,43,-1,50,-1,50:4" +
",-1:4,50,-1:3,50,-1:2,72:6,63:2,72:29,63,53,72,66,63,72,88,72,-1:6,50:3,-1:" +
"7,50:3,-1,50,-1,50,45,50,-1,50:3,-1,50,-1,50:4,-1:4,50,-1:3,50,-1:7,50:3,-1" +
":7,50:3,-1,50,-1,50,46,50,-1,50:3,-1,50,-1,50:4,-1:4,50,-1:3,50,-1:8,66:2,-" +
"1:29,66,-1:2,66:2,-1:9,50,47,50,-1:7,50:3,-1,50,-1,50:3,-1,50:3,-1,50,-1,50" +
":4,-1:4,50,-1:3,50,-1:2,59:19,58,59:17,72,38,59,44,59:2,86,59,-1:6,50:2,48," +
"-1:7,50:3,-1,50,-1,50:3,-1,50:3,-1,50,-1,50:4,-1:4,50,-1:3,50,-1:2,63:19,62" +
",63:17,74,53,63,35,63:2,87,63,-1,72:19,65,72:18,53,72,-1,72:2,88,72,-1,49:1" +
"6,70,49:20,54,34,49,35,49:2,84,49,-1:6,60,50:2,-1:7,50,92,50,-1,50,-1,50:3," +
"-1,50:3,-1,50,-1,50:4,-1:4,50,-1:3,50,-1:2,59:16,80,59:20,72,38,59,44,59:2," +
"86,59,-1,63:16,82,63:20,74,53,63,35,63:2,87,63,-1,72:16,83,72:21,53,72,-1,7" +
"2:2,88,72,-1:6,50:3,-1:7,50:3,-1,50,-1,50:2,64,-1,50:3,-1,50,-1,50:4,-1:4,5" +
"0,-1:3,50,-1:7,50:3,-1:7,50,67,50,-1,50,-1,50:3,-1,50:3,-1,50,-1,50:4,-1:4," +
"50,-1:3,50,-1:7,50:3,-1:7,50:3,-1,50,-1,50:3,-1,50:3,-1,50,-1,69,50:3,-1:4," +
"50,-1:3,50,-1:7,50:3,-1:7,50:3,-1,50,-1,50:2,71,-1,50:3,-1,50,-1,50:4,-1:4," +
"50,-1:3,50,-1:7,73,50:2,-1:7,50:3,-1,50,-1,50:3,-1,50:3,-1,50,-1,50:4,-1:4," +
"50,-1:3,50,-1:7,50:3,-1:7,50:3,-1,50,-1,50:3,-1,50:3,-1,50,-1,75,50:3,-1:4," +
"50,-1:3,50,-1:7,50:3,-1:7,50:2,77,-1,50,-1,50:3,-1,50:3,-1,50,-1,50:4,-1:4," +
"50,-1:3,50,-1:7,50:3,-1:7,50:3,-1,50,-1,79,50:2,-1,50:3,-1,50,-1,50:4,-1:4," +
"50,-1:3,50,-1:7,50:3,-1:7,50:3,-1,50,-1,50:3,-1,81,50:2,-1,50,-1,50:4,-1:4," +
"50,-1:3,50,-1:7,50:3,-1:7,50:3,-1,50,-1,89,50:2,-1,50:3,-1,50,-1,50:4,-1:4," +
"50,-1:3,50,-1:7,50:3,-1:7,50,90,50,-1,50,-1,50:3,-1,50:3,-1,50,-1,50:4,-1:4" +
",50,-1:3,50,-1:7,50:3,-1:7,50:2,91,-1,50,-1,50:3,-1,50:3,-1,50,-1,50:4,-1:4" +
",50,-1:3,50,-1:7,50:3,-1:7,50,93,50,-1,50,-1,50:3,-1,50:3,-1,50,-1,50:4,-1:" +
"4,50,-1:3,50,-1:7,50:3,-1:7,50:2,94,-1,50,-1,50:3,-1,50:3,-1,50,-1,50:4,-1:" +
"4,50,-1:3,50,-1:7,95,50:2,-1:7,50:3,-1,50,-1,50:3,-1,50:3,-1,50,-1,50:4,-1:" +
"4,50,-1:3,50,-1:7,50:3,-1:7,50:3,-1,50,-1,50:2,96,-1,50:3,-1,50,-1,50:4,-1:" +
"4,50,-1:3,50,-1:7,50:3,-1:7,50:3,-1,50,-1,50:2,97,-1,50:3,-1,50,-1,50:4,-1:" +
"4,50,-1:3,50,-1:7,50:3,-1:7,50:3,-1,50,-1,50:3,-1,50:3,-1,50,-1,50:3,102,-1" +
":4,50,-1:3,50,-1:7,50:3,-1:7,50:3,-1,50,-1,50:3,-1,50:3,-1,50,-1,50:2,103,5" +
"0,-1:4,50,-1:3,50,-1:7,50:2,104,-1:7,50:3,-1,50,-1,50:3,-1,50:3,-1,50,-1,50" +
":4,-1:4,50,-1:3,50,-1:7,50:3,-1:7,50:3,-1,50,-1,105,50:2,-1,50:3,-1,50,-1,5" +
"0:4,-1:4,50,-1:3,50,-1:7,50:3,-1:7,50:3,-1,50,-1,50,108,50,-1,50:3,-1,50,-1" +
",50:4,-1:4,50,-1:3,50,-1:7,50:2,109,-1:7,50:3,-1,50,-1,50:3,-1,50:3,-1,50,-" +
"1,50:4,-1:4,50,-1:3,50,-1");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

return new Symbol(sym.EOF);
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.PLUS, new TokenVal(CharNum.lineNum, CharNum.colNum));
}
					case -3:
						break;
					case 3:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.DIVIDE, new TokenVal(CharNum.lineNum, CharNum.colNum));
}
					case -4:
						break;
					case 4:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ASSIGN, new TokenVal(CharNum.lineNum, CharNum.colNum)); 
}
					case -5:
						break;
					case 5:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.GREATER, new TokenVal(CharNum.lineNum, CharNum.colNum));
}
					case -6:
						break;
					case 6:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.LPAREN, new TokenVal(CharNum.lineNum, CharNum.colNum));
}
					case -7:
						break;
					case 7:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ID, new IdTokenVal(CharNum.lineNum, CharNum.colNum, yytext()));
}
					case -8:
						break;
					case 8:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.LESS, new TokenVal(CharNum.lineNum, CharNum.colNum));
}
					case -9:
						break;
					case 9:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.MINUS, new TokenVal(CharNum.lineNum, CharNum.colNum));
}
					case -10:
						break;
					case 10:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.RPAREN, new TokenVal(CharNum.lineNum, CharNum.colNum));
}
					case -11:
						break;
					case 11:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.NOT, new TokenVal(CharNum.lineNum, CharNum.colNum));
}
					case -12:
						break;
					case 12:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.SEMICOLON, new TokenVal(CharNum.lineNum, CharNum.colNum));
}
					case -13:
						break;
					case 13:
						{
      System.out.println("illegal character ignored: " + yytext());
}
					case -14:
						break;
					case 14:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.COMMA, new TokenVal(CharNum.lineNum, CharNum.colNum));
}
					case -15:
						break;
					case 15:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.DOT, new TokenVal(CharNum.lineNum, CharNum.colNum));
}
					case -16:
						break;
					case 16:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.LCURLY, new TokenVal(CharNum.lineNum, CharNum.colNum));
}
					case -17:
						break;
					case 17:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.RCURLY, new TokenVal(CharNum.lineNum, CharNum.colNum));
}
					case -18:
						break;
					case 18:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      int tmp = 0;
      try{
            tmp = new Integer(yytext()).intValue();
      }catch(Exception e){
            System.out.println("integer literal too large;");
            tmp = Integer.MAX_VALUE;
      }
      return new Symbol(sym.INTLITERAL, new IntLitTokenVal(CharNum.lineNum, CharNum.colNum, tmp));
}
					case -19:
						break;
					case 19:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.TIMES, new TokenVal(CharNum.lineNum, CharNum.colNum)); 
}
					case -20:
						break;
					case 20:
						{
      CharNum.setPos();
      CharNum.col++;
}
					case -21:
						break;
					case 21:
						{
      CharNum.line += 1;
      CharNum.col = 1;
      CharNum.setPos();
}
					case -22:
						break;
					case 22:
						{
      CharNum.line += 1;
      CharNum.col = 1;
      CharNum.setPos();
}
					case -23:
						break;
					case 23:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.PLUSPLUS, new TokenVal(CharNum.lineNum, CharNum.colNum));
}
					case -24:
						break;
					case 24:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.EQUALS, new TokenVal(CharNum.lineNum, CharNum.colNum));
}
					case -25:
						break;
					case 25:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.GREATEREQ, new TokenVal(CharNum.lineNum, CharNum.colNum));
}
					case -26:
						break;
					case 26:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.READ, new TokenVal(CharNum.lineNum, CharNum.colNum));
}
					case -27:
						break;
					case 27:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.IF, new TokenVal(CharNum.lineNum, CharNum.colNum));
}
					case -28:
						break;
					case 28:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.LESSEQ, new TokenVal(CharNum.lineNum, CharNum.colNum));
}
					case -29:
						break;
					case 29:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.WRITE, new TokenVal(CharNum.lineNum, CharNum.colNum));
}
					case -30:
						break;
					case 30:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.MINUSMINUS, new TokenVal(CharNum.lineNum, CharNum.colNum));
}
					case -31:
						break;
					case 31:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.NOTEQUALS, new TokenVal(CharNum.lineNum, CharNum.colNum));
}
					case -32:
						break;
					case 32:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.AND, new TokenVal(CharNum.lineNum, CharNum.colNum));
}
					case -33:
						break;
					case 33:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.OR, new TokenVal(CharNum.lineNum, CharNum.colNum));
}
					case -34:
						break;
					case 34:
						{
      System.out.println("unterminated string literal ignored: "+yytext());
}
					case -35:
						break;
					case 35:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.STRINGLITERAL,new StrLitTokenVal(CharNum.lineNum, CharNum.colNum, yytext()));
}
					case -36:
						break;
					case 36:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.INT, new TokenVal(CharNum.lineNum, CharNum.colNum));
}
					case -37:
						break;
					case 37:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.CIN, new TokenVal(CharNum.lineNum, CharNum.colNum));
}
					case -38:
						break;
					case 38:
						{
      System.out.println("unterminated string literal with bad escaped character ignored: "+yytext());
}
					case -39:
						break;
					case 39:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.TRUE, new TokenVal(CharNum.lineNum, CharNum.colNum));
}
					case -40:
						break;
					case 40:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.BOOL, new TokenVal(CharNum.lineNum, CharNum.colNum));
}
					case -41:
						break;
					case 41:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ELSE, new TokenVal(CharNum.lineNum, CharNum.colNum));
}
					case -42:
						break;
					case 42:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.COUT, new TokenVal(CharNum.lineNum, CharNum.colNum));
}
					case -43:
						break;
					case 43:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.VOID, new TokenVal(CharNum.lineNum, CharNum.colNum));
}
					case -44:
						break;
					case 44:
						{
      System.out.println("string literal with bad escaped character ignored: "+yytext());
}
					case -45:
						break;
					case 45:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.FALSE, new TokenVal(CharNum.lineNum, CharNum.colNum));
}
					case -46:
						break;
					case 46:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.WHILE, new TokenVal(CharNum.lineNum, CharNum.colNum));
}
					case -47:
						break;
					case 47:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.RETURN, new TokenVal(CharNum.lineNum, CharNum.colNum));
}
					case -48:
						break;
					case 48:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.STRUCT, new TokenVal(CharNum.lineNum, CharNum.colNum));
}
					case -49:
						break;
					case 50:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ID, new IdTokenVal(CharNum.lineNum, CharNum.colNum, yytext()));
}
					case -50:
						break;
					case 51:
						{
      System.out.println("illegal character ignored: " + yytext());
}
					case -51:
						break;
					case 52:
						{
      System.out.println("unterminated string literal ignored: "+yytext());
}
					case -52:
						break;
					case 53:
						{
      System.out.println("unterminated string literal with bad escaped character ignored: "+yytext());
}
					case -53:
						break;
					case 55:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ID, new IdTokenVal(CharNum.lineNum, CharNum.colNum, yytext()));
}
					case -54:
						break;
					case 56:
						{
      System.out.println("illegal character ignored: " + yytext());
}
					case -55:
						break;
					case 57:
						{
      System.out.println("unterminated string literal ignored: "+yytext());
}
					case -56:
						break;
					case 58:
						{
      System.out.println("unterminated string literal with bad escaped character ignored: "+yytext());
}
					case -57:
						break;
					case 60:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ID, new IdTokenVal(CharNum.lineNum, CharNum.colNum, yytext()));
}
					case -58:
						break;
					case 61:
						{
      System.out.println("illegal character ignored: " + yytext());
}
					case -59:
						break;
					case 62:
						{
      System.out.println("unterminated string literal with bad escaped character ignored: "+yytext());
}
					case -60:
						break;
					case 64:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ID, new IdTokenVal(CharNum.lineNum, CharNum.colNum, yytext()));
}
					case -61:
						break;
					case 65:
						{
      System.out.println("unterminated string literal with bad escaped character ignored: "+yytext());
}
					case -62:
						break;
					case 67:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ID, new IdTokenVal(CharNum.lineNum, CharNum.colNum, yytext()));
}
					case -63:
						break;
					case 69:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ID, new IdTokenVal(CharNum.lineNum, CharNum.colNum, yytext()));
}
					case -64:
						break;
					case 71:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ID, new IdTokenVal(CharNum.lineNum, CharNum.colNum, yytext()));
}
					case -65:
						break;
					case 73:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ID, new IdTokenVal(CharNum.lineNum, CharNum.colNum, yytext()));
}
					case -66:
						break;
					case 75:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ID, new IdTokenVal(CharNum.lineNum, CharNum.colNum, yytext()));
}
					case -67:
						break;
					case 77:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ID, new IdTokenVal(CharNum.lineNum, CharNum.colNum, yytext()));
}
					case -68:
						break;
					case 79:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ID, new IdTokenVal(CharNum.lineNum, CharNum.colNum, yytext()));
}
					case -69:
						break;
					case 81:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ID, new IdTokenVal(CharNum.lineNum, CharNum.colNum, yytext()));
}
					case -70:
						break;
					case 85:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ID, new IdTokenVal(CharNum.lineNum, CharNum.colNum, yytext()));
}
					case -71:
						break;
					case 89:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ID, new IdTokenVal(CharNum.lineNum, CharNum.colNum, yytext()));
}
					case -72:
						break;
					case 90:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ID, new IdTokenVal(CharNum.lineNum, CharNum.colNum, yytext()));
}
					case -73:
						break;
					case 91:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ID, new IdTokenVal(CharNum.lineNum, CharNum.colNum, yytext()));
}
					case -74:
						break;
					case 92:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ID, new IdTokenVal(CharNum.lineNum, CharNum.colNum, yytext()));
}
					case -75:
						break;
					case 93:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ID, new IdTokenVal(CharNum.lineNum, CharNum.colNum, yytext()));
}
					case -76:
						break;
					case 94:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ID, new IdTokenVal(CharNum.lineNum, CharNum.colNum, yytext()));
}
					case -77:
						break;
					case 95:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ID, new IdTokenVal(CharNum.lineNum, CharNum.colNum, yytext()));
}
					case -78:
						break;
					case 96:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ID, new IdTokenVal(CharNum.lineNum, CharNum.colNum, yytext()));
}
					case -79:
						break;
					case 97:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ID, new IdTokenVal(CharNum.lineNum, CharNum.colNum, yytext()));
}
					case -80:
						break;
					case 98:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ID, new IdTokenVal(CharNum.lineNum, CharNum.colNum, yytext()));
}
					case -81:
						break;
					case 99:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ID, new IdTokenVal(CharNum.lineNum, CharNum.colNum, yytext()));
}
					case -82:
						break;
					case 100:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ID, new IdTokenVal(CharNum.lineNum, CharNum.colNum, yytext()));
}
					case -83:
						break;
					case 101:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ID, new IdTokenVal(CharNum.lineNum, CharNum.colNum, yytext()));
}
					case -84:
						break;
					case 102:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ID, new IdTokenVal(CharNum.lineNum, CharNum.colNum, yytext()));
}
					case -85:
						break;
					case 103:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ID, new IdTokenVal(CharNum.lineNum, CharNum.colNum, yytext()));
}
					case -86:
						break;
					case 104:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ID, new IdTokenVal(CharNum.lineNum, CharNum.colNum, yytext()));
}
					case -87:
						break;
					case 105:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ID, new IdTokenVal(CharNum.lineNum, CharNum.colNum, yytext()));
}
					case -88:
						break;
					case 106:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ID, new IdTokenVal(CharNum.lineNum, CharNum.colNum, yytext()));
}
					case -89:
						break;
					case 107:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ID, new IdTokenVal(CharNum.lineNum, CharNum.colNum, yytext()));
}
					case -90:
						break;
					case 108:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ID, new IdTokenVal(CharNum.lineNum, CharNum.colNum, yytext()));
}
					case -91:
						break;
					case 109:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ID, new IdTokenVal(CharNum.lineNum, CharNum.colNum, yytext()));
}
					case -92:
						break;
					case 110:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ID, new IdTokenVal(CharNum.lineNum, CharNum.colNum, yytext()));
}
					case -93:
						break;
					case 111:
						{
      CharNum.setPos();
      CharNum.col += yytext().length();
      return new Symbol(sym.ID, new IdTokenVal(CharNum.lineNum, CharNum.colNum, yytext()));
}
					case -94:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
