import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Compile {
	// 代码所属类别的下标
	int ch;
	// 解析后的码
	int code;
	// 作为存入MyTokenType对象 mtt的下标
	int mtt_i = 0;

	// 标签的map，存储标签名，和序列下标
	static Map<String, Integer> markmap = new HashMap<String, Integer>();

	// 声明一个MyTokenType类数组，用来保存解析出的<类型，码字>序列
	static MyTokenType mtt[] = new MyTokenType[300];

	// 存放构成单词符号的字符串
	public StringBuffer strToken = new StringBuffer();

	// 处理关键字,operator表示运算符 quotation表示单引号，分号 brackets表示括号等
	public String[] retainWord = new String[] { "auto", "putnumb", "putchar", "operator", "quotation", "extern",
			"getchar", "mark", "goto", "brackets", "if", "else", "while", "var", "number", "getnumb", "for", "do",
			"note", "putstr","switch", "break", "continue" };

	// 判断是否是字母
	public boolean IsLetter() {
		if ((ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122)) {
			return true;
		}
		return false;
	}

	// 判断是否是数字
	public boolean IsDigit() {
		if (ch >= 48 && ch <= 57) {
			return true;
		}
		return false;
	}

	// 判断是否是空格
	public boolean IsBC(int ch) {
		if (ch == 32) {
			return true;
		}
		return false;
	}

	// 连接字符
	public void Contact(char ch) {
		strToken.append(ch);
	}

	// 判断是否是保留字
	public int Reserve() {
		for (int i = 0; i < retainWord.length; i++) {
			if (strToken.toString().equals(retainWord[i])) {
				return i;
			}
		}
		if (strToken.length() != 0) {
			if (strToken.charAt(0) >= '0' && strToken.charAt(0) <= '9') {
				return 14;
			}
		}
		if (strToken.length() != 0) {
			if (strToken.charAt(0) == '<' || strToken.charAt(0) == '>' || strToken.charAt(0) == '='
					|| strToken.charAt(0) == '+' || strToken.charAt(0) == '-' || strToken.charAt(0) == '*'
					|| strToken.charAt(0) == '/') {
				return 3;
			}
		}
		return 13;
	}

	public void Retract() {
		// TODO Auto-generated method stub
		code = Reserve();
		if (strToken.length() != 0) {
			System.out.println("('" + code + "','" + strToken + "')");
			mtt[mtt_i++] = new MyTokenType(retainWord[code], strToken.toString());
			strToken.delete(0, strToken.length());
		}
	}

	public void scanner(String txt) {
		BufferedReader br;
		// System.out.println("请输入文件位置：");
		// Scanner sc = new Scanner(System.in);
		// txt = sc.next();
		try {
			br = new BufferedReader(new FileReader(txt));
			while ((ch = br.read()) != -1) {
				// System.out.println("======="+(char)ch);
				if (IsBC(ch) == false) {
					if (IsLetter()) {
						if((strToken.length() != 0) &&((strToken.charAt(0) == '+') || (strToken.charAt(0) == '-') || (strToken.charAt(0) == '=') || 
								(strToken.charAt(0) == '<') || (strToken.charAt(0) == '>') || (strToken.charAt(0) == '*') || (strToken.charAt(0) == '/'))) {
							Retract();
							Contact((char) ch);
						} else {
							Contact((char) ch);
						}
					} else if (IsDigit()) {
						if((strToken.length() != 0) && ((strToken.charAt(0) == '+') || (strToken.charAt(0) == '-') || (strToken.charAt(0) == '=') || 
								(strToken.charAt(0) == '<') || (strToken.charAt(0) == '>') || (strToken.charAt(0) == '*') || (strToken.charAt(0) == '/'))) {
							Retract();
							Contact((char) ch);
						} else {
							Contact((char) ch);
						}
					} else if (ch == 61) {
						if ((strToken.length() != 0) && ((strToken.charAt(0) == '=') || (strToken.charAt(0) == '>')
								|| (strToken.charAt(0) == '<') || (strToken.charAt(0) == '!'))) {
							strToken.append((char) ch);
							System.out.println("('" + 4 + "','" + strToken + "')");
							mtt[mtt_i++] = new MyTokenType("operator", strToken.toString());
							strToken.delete(0, strToken.length());
						} else if (strToken.length() != 0) {
							Retract();
							strToken.append((char) ch);
						} else {
							strToken.append((char) ch);
						}
					} else if (ch == 60 || ch == 62 || ch == 33) {
						strToken.append((char) ch);
					} else if (ch == 42 || ch == 47) { // *、/
						if (ch == 47 && (strToken.length() != 0) && (strToken.charAt(0) == '*')) { // 判断是否是
							// /*
							Contact((char) ch);
							System.out.println("('" + 18 + "','" + strToken + "')");
							mtt[mtt_i++] = new MyTokenType("note", strToken.toString());
							strToken.delete(0, strToken.length());
						} else if (ch == 42 && (strToken.length() != 0) && (strToken.charAt(0) == '/')) { // 判断是否是
							// */
							Contact((char) ch);
							System.out.println("('" + 18 + "','" + strToken + "')");
							mtt[mtt_i++] = new MyTokenType("note", strToken.toString());
							strToken.delete(0, strToken.length());
						} else if (strToken.length() != 0) {
							Retract();
							strToken.append((char) ch);
						} else {
							strToken.append((char) ch);
						}
					} else if (ch == 43) {
						if ((strToken.length() != 0) && (strToken.charAt(0) == '+')) {
							strToken.append((char) ch);
							System.out.println("('" + 4 + "','" + strToken + "')");
							mtt[mtt_i++] = new MyTokenType("operator", strToken.toString());
							strToken.delete(0, strToken.length());
						} else if (strToken.length() != 0) {
							Retract();
							strToken.append((char) ch);
						} else {
							strToken.append((char) ch);
						}

					} else if (ch == '&') {
						if ((strToken.length() != 0) && (strToken.charAt(0) == '&')) {
							strToken.append((char) ch);
							System.out.println("('" + 4 + "','" + strToken + "')");
							mtt[mtt_i++] = new MyTokenType("operator", strToken.toString());
							strToken.delete(0, strToken.length());
						} else {
							strToken.append((char) ch);
						}

					} else if (ch == '|') {
						if ((strToken.length() != 0) && (strToken.charAt(0) == '|')) {
							strToken.append((char) ch);
							System.out.println("('" + 4 + "','" + strToken + "')");
							mtt[mtt_i++] = new MyTokenType("operator", strToken.toString());
							strToken.delete(0, strToken.length());
						} else {
							strToken.append((char) ch);
						}

					} else if (ch == 45) {
						if ((strToken.length() != 0) && (strToken.charAt(0) == '-')) {
							strToken.append((char) ch);
							System.out.println("('" + 4 + "','" + strToken + "')");
							mtt[mtt_i++] = new MyTokenType("operator", strToken.toString());
							strToken.delete(0, strToken.length());
						} else if (strToken.length() != 0) {
							Retract();
							strToken.append((char) ch);
						} else {
							strToken.append((char) ch);
						}
					} else if (ch == ',') {
						Retract();
						System.out.println("('" + 5 + "','" + (char) ch + "')");
						mtt[mtt_i++] = new MyTokenType("quotation", ",");
					} else if (ch == ';') {
						Retract();
						System.out.println("('" + 5 + "','" + (char) ch + "')");
						mtt[mtt_i++] = new MyTokenType("quotation", ";");
					} else if (ch == ')') {
						Retract();
						System.out.println("('" + 5 + "','" + (char) ch + "')");
						mtt[mtt_i++] = new MyTokenType("brackets", ")");
					} else if (ch == '(') {
						Retract();
						System.out.println("('" + 5 + "','" + (char) ch + "')");
						mtt[mtt_i++] = new MyTokenType("brackets", "(");
					} else if (ch == '{') {
						Retract();
						System.out.println("('" + 5 + "','" + (char) ch + "')");
						mtt[mtt_i++] = new MyTokenType("brackets", "{");
					} else if (ch == '[') {
						Retract();
						System.out.println("('" + 5 + "','" + (char) ch + "')");
						mtt[mtt_i++] = new MyTokenType("brackets", "[");
					} else if (ch == ']') {
						Retract();
						System.out.println("('" + 5 + "','" + (char) ch + "')");
						mtt[mtt_i++] = new MyTokenType("brackets", "]");
					} else if (ch == '}') {
						Retract();
						System.out.println("('" + 5 + "','" + (char) ch + "')");
						mtt[mtt_i++] = new MyTokenType("brackets", "}");
					} else if (ch == ':') {
						if ((strToken.length() != 0)) {
							char temp = strToken.charAt(0);
							if ((temp >= 65 && temp <= 90) || (temp >= 97 && temp <= 122)) {
								System.out.println("('" + 7 + "','" + strToken + "')");
								markmap.put(strToken.toString(), mtt_i);
								mtt[mtt_i++] = new MyTokenType("mark", strToken.toString());
								strToken.delete(0, strToken.length());
							} else {
								System.out.println("wrong!");
								System.exit(0);
							}
						} else {
							System.out.println("wrong!");
							System.exit(0);
						}
					} else if (ch == '\'') {
						Retract();
						System.out.println("('" + 5 + "','" + (char) ch + "')");
						mtt[mtt_i++] = new MyTokenType("quotation", "'");
					}

				} else {
					Retract();
				}
			}

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
