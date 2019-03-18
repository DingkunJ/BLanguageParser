import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JOptionPane;

import Exception.MarkDoNotExsitException;
import Exception.NotAVarException;
import Exception.NotSuchVarException;
import Exception.WrongGrammaticalException;
import net.sourceforge.jeval.Evaluator;
import net.sourceforge.jeval.function.FunctionException;
import net.sourceforge.jeval.function.string.Eval;

public class Parser {

	// 初始化一个词法分析器
	Compile compile = new Compile();

	// 变量的map，存储变量名和变量值，保存数值型变量
	Map<String, Integer> varmap = new HashMap<String, Integer>();
	// 保存字符串型变量的map
	Map<String, String> varmap2 = new HashMap<String, String>();
	// 保存数组型变量的map
	Map<String, array> varmap3 = new HashMap<String, array>();
	// goto标签的map，存储标签名，和序列下标
	Map<String, Integer> markmap = Compile.markmap;

	// 初始化一个array类数组，用于保存数组类型变量
	array a[] = new array[10];
	// 作为array类数组的下标
	int a_i = 0;

	// 设置是否break的标志
	boolean while_or = true;
	
	// 作为遍历词法分析器生成的序列的下标
	public int mtt_i = 0;
	// 解释结果
	public String parseresult = "编译结果\n";

	// 遍历词法分析器序列，对于不同类型的关键字，跳转到特殊函数执行
	public void BParser(MyTokenType[] mtt) {
		try {
			// 当词法分析器序列mtt没有读尽，就继续处理
			while (mtt[mtt_i] != null) {

				// 读取到auto关键字,跳转到auto函数处理
				if (mtt[mtt_i].type.equals("auto")) {
					auto(mtt);

					// 读取到putnumb关键字,跳转到putnumb函数,输出数值型变量值
				} else if (mtt[mtt_i].type.equals("putnumb")) {
					putnumb(mtt);

					// 读取到putchar关键字,跳转到putchar函数,输出字符型型变量值
				} else if (mtt[mtt_i].type.equals("putchar")) {
					putchar(mtt);

					// 单目运算，自增自减
				} else if (mtt[mtt_i].code.equals("++") || mtt[mtt_i].code.equals("--")) {
					unaryOperation(mtt);

					// 读取到类型为operator,即操作符类型，跳转到operator函数
				} else if (mtt[mtt_i].type.equals("operator")) {
					operator(mtt);

					// 读取到关键字getchar,跳转到getchar函数进行读入字符串操作
				} else if (mtt[mtt_i].type.equals("getchar")) {
					getchar(mtt);

					// 读取到关键字getnumb,跳转到getnumb函数执行读入数值操作
				} else if (mtt[mtt_i].type.equals("getnumb")) {
					getnumb(mtt);

					// 读取到关键字goto,跳转到Bgoto函数处理goto语句
				} else if (mtt[mtt_i].type.equals("goto")) {
					Bgoto(mtt);

					// 读取到if关键字,跳转到Bif函数处理if语句
				} else if (mtt[mtt_i].type.equals("if")) {
					Bif(mtt);

					// 读取到while关键字, 跳转到BWhile函数处理while循环语句
				} else if (mtt[mtt_i].type.equals("while")) {
					BWhile(mtt);

					// 读取到do关键字,跳转到BDo_while函数执行do while函数执行
				} else if (mtt[mtt_i].type.equals("do")) {
					BDo_while(mtt);

					// 读取到for关键字，执行for循环
				} else if (mtt[mtt_i].type.equals("for")) {
					Bfor(mtt);
					// 读取到note关键字, 跳转到Note函数处理注释
				} else if (mtt[mtt_i].type.equals("note")) {
					Note(mtt);

					// 读取到putstr函数，输出数组
				} else if (mtt[mtt_i].type.equals("putstr")) {
					Putstr(mtt);
                
				// 读取到switch
				} else if(mtt[mtt_i].type.equals("switch")) {
					Bswitch(mtt);
				// 都不满足就将mtt_i下标加1,不作处理
				} else
					mtt_i++;
			}
		} catch (NotSuchVarException e) {
			e.printStackTrace();
		} catch (NotAVarException e) {
			e.printStackTrace();
		} catch (WrongGrammaticalException e) {
			e.printStackTrace();
		} catch (MarkDoNotExsitException e) {
			e.printStackTrace();
		}
	}

	// B语言for循环
	// 规定for语句一定要有左右花括号
	private void Bfor(MyTokenType[] mtt) {
		System.out.println("----------------*****");
		// TODO Auto-generated method stub
		// 圆括号中分号的数目
		int num = 0;
		// 第一部分开始与结束指针
		int exp1_start;
		int exp1_end;
		// 第二部分开始与结束指针
		int exp2_start;
		int exp2_end;
		// 第三部分开始与结束指针
		int exp3_start;
		int exp3_end = 0;
		// 循环部分开始与结束指针
		int start;
		int end = 0;
		// 判断for语句中分号的数目
		for (int temp = mtt_i; !mtt[temp].code.equals(")"); temp++) {
			if (mtt[temp].code.equals(";")) {
				num++;
			}
		}
		// 对于for语句的检查
		if (num != 2) {
			System.out.println("wrong!5 -- for语句不正确");
			System.exit(0);
		} else {
			if (!mtt[mtt_i + 1].code.equals("(")) {
				System.out.println("wrong!4 -- 没有左括号");
				System.exit(0);
			} else {
				// 第一部分
				exp1_start = mtt_i + 2;
				for (; !mtt[mtt_i].code.equals(";"); mtt_i++) {
				}
				exp1_end = mtt_i;
				// for(int i = exp1_start;i <= exp1_end;i++){
				// System.out.println("111111111"+mtt[i].code);
				// }
				// 判断语句
				exp2_start = mtt_i + 1;
				mtt_i++;
				for (; !mtt[mtt_i].code.equals(";"); mtt_i++) {
				}
				exp2_end = mtt_i;
				// mtt[mtt_i].code = "$";
				// for(int i = exp2_start;i <= exp2_end;i++){
				// System.out.println("2222222222"+mtt[i].code);
				// }
				// 第三部分
				exp3_start = mtt_i + 1;
				for (; !mtt[mtt_i].code.equals(")"); mtt_i++) {
				}
				exp3_end = mtt_i;
				// 为了使语句完整，把最后一条语句后右括号变成分号
				mtt[mtt_i].code = ";";
				mtt[mtt_i].type = "quotation";
				// for(int i = exp3_start;i <= exp3_end;i++){
				// System.out.println("33333333333"+mtt[i].code);
				// }
				// 对于for语句的检查
				if (!mtt[mtt_i + 1].code.equals("{")) {
					System.out.println("wrong!6 -- 没有左花括号");
					System.exit(0);
				} else {
					// 循环部分
					start = mtt_i + 2;
					for (; !mtt[mtt_i].code.equals("}"); mtt_i++) {
						end = mtt_i;
					}
					// System.out.println("suc");
					// 先执行第一部分
					FParser(mtt, exp1_start, exp1_end);
					try {
						if (WCheck(mtt, exp2_start, exp2_end, end) == true) {
							// 进入循环语句
							while (WCheck(mtt, exp2_start, exp2_end, end)) {
								// 调用FParser，进行循环语句的执行
								FParser(mtt, start, end);
								FParser(mtt, exp3_start, exp3_end);
							}
						} else {
							// 当判断语句为假时直接将全局指针指向do-while语句末尾
							mtt_i = end + 1;
							return;
						}
					} catch (NotSuchVarException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NotAVarException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// 将全局指针指向do-while语句末尾
					mtt_i = end + 1;
					return;
				}
			}

		}
	}

	// 与Parser不同之处在于循环结束后全局指针不会后移
	private void FParser(MyTokenType[] mtt, int start_num, int end_num) {
		for (mtt_i = start_num; mtt_i < end_num && mtt[mtt_i] != null;) {
			if (mtt[mtt_i].type.equals("auto"))
				try {
					auto(mtt);
				} catch (WrongGrammaticalException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotAVarException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			else if (mtt[mtt_i].type.equals("putnumb"))
				try {
					putnumb(mtt);
				} catch (NotSuchVarException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (WrongGrammaticalException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotAVarException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			else if (mtt[mtt_i].type.equals("putchar"))
				try {
					putchar(mtt);
				} catch (NotSuchVarException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (WrongGrammaticalException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			else if (mtt[mtt_i].code.equals("++") || mtt[mtt_i].code.equals("--"))
				try {
					unaryOperation(mtt);
				} catch (NotSuchVarException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (WrongGrammaticalException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			else if (mtt[mtt_i].type.equals("operator"))
				try {
					operator(mtt);
				} catch (NotAVarException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotSuchVarException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			else if (mtt[mtt_i].type.equals("getchar"))
				try {
					getchar(mtt);
				} catch (NotSuchVarException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (WrongGrammaticalException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			else
				mtt_i++;
		}
		// System.out.println("rrrrrrrrrrrrrr"+mtt[mtt_i].code);
		return;
	}

	// 处理B语言注释体的函数
	private void Note(MyTokenType[] mtt) throws WrongGrammaticalException {
		// TODO Auto-generated method stub
		// 是否有左注释的标志
		boolean hasLeft = false;

		for (; mtt[mtt_i + 1] != null; mtt_i++) {
			if (mtt[mtt_i].code.equals("/*")) {
				hasLeft = true;
				continue;
			} else if (mtt[mtt_i].code.equals("*/")) {
				if (hasLeft == true) {
					hasLeft = false;
				} else {
					parseresult += "请检查注释语句\n";
					throw new WrongGrammaticalException("请检查注释语句");
				}
				mtt_i += 1;
				return;
			} else {
				continue;
			}
		}
		if (mtt[mtt_i + 1] == null || hasLeft == true) {
			parseresult += "请检查注释语句\n";
			throw new WrongGrammaticalException("请检查注释语句");
		}
	}

	// 处理自增自减符号
	private int unaryOperation(MyTokenType[] mtt) throws NotSuchVarException, WrongGrammaticalException {
		// TODO Auto-generated method stub

		// ++情况，分为在变量之前和在变量之后两种情况
		if (mtt[mtt_i].code.equals("++")) {

			// 判断是不是变量++; 或者变量++)的情况
			if (mtt[mtt_i - 1].type.equals("var")
					&& (mtt[mtt_i + 1].code.equals(";") || mtt[mtt_i + 1].code.equals(")"))) {

				// 判断变量是否被声明
				if (varmap.get(mtt[mtt_i - 1].code) != null) {
					int temp = varmap.get(mtt[mtt_i - 1].code);
					varmap.put(mtt[mtt_i - 1].code, temp + 1);
					mtt_i++;
					return temp;
				} else {
					parseresult += "变量" + mtt[mtt_i - 1].code + "未定义\n";
					throw new NotSuchVarException("变量未定义");
				}

				// 判断是不是++变量; 或者++变量)的情况
			} else if (mtt[mtt_i + 1].type.equals("var")
					&& (mtt[mtt_i + 2].code.equals(";") || mtt[mtt_i + 2].code.equals(")"))) {

				// 判断变量是否被声明
				if (varmap.get(mtt[mtt_i + 1].code) != null) {
					int temp = varmap.get(mtt[mtt_i + 1].code);
					varmap.put(mtt[mtt_i + 1].code, temp + 1);
					mtt_i += 2;
					return temp + 1;
				} else {
					parseresult += "变量" + mtt[mtt_i + 1].code + "未定义\n";
					throw new NotSuchVarException("变量未定义");
				}
			} else {
				// 抛出语句错误
				parseresult += "语法错误 \n";
				throw new WrongGrammaticalException("语法错误\n");
			}

			// --情况，分为在变量之前和在变量之后两种情况
		} else {
			// 判断是不是变量--; 或者变量--)的情况
			if (mtt[mtt_i - 1].type.equals("var")
					&& (mtt[mtt_i + 1].code.equals(";") || mtt[mtt_i + 1].code.equals(")"))) {

				// 判断变量是否被声明
				if (varmap.get(mtt[mtt_i - 1].code) != null) {
					int temp = varmap.get(mtt[mtt_i - 1].code);
					varmap.put(mtt[mtt_i - 1].code, temp - 1);
					mtt_i++;
					return temp;
				} else {
					parseresult += "变量" + mtt[mtt_i - 1].code + "未定义\n";
					throw new NotSuchVarException("变量未定义");
				}

				// 判断是不是++变量; 或者++变量)的情况
			} else if (mtt[mtt_i + 1].type.equals("var")
					&& (mtt[mtt_i + 2].code.equals(";") || mtt[mtt_i + 2].code.equals(")"))) {

				// 判断变量是否被声明
				if (varmap.get(mtt[mtt_i + 1].code) != null) {
					int temp = varmap.get(mtt[mtt_i + 1].code);
					varmap.put(mtt[mtt_i + 1].code, temp - 1);
					mtt_i += 2;
					return temp - 1;
				} else {
					parseresult += "变量" + mtt[mtt_i + 1].code + "未定义\n";
					throw new NotSuchVarException("变量未定义");
				}
			} else {
				// 抛出语句错误
				parseresult += "语法错误 \n";
				throw new WrongGrammaticalException("语法错误\n");
			}

		}
	}

	// 循环体执行函数 原理和主函数BParser相似，规定了开头和结束下标
	private void Parser(MyTokenType[] mtt, int start_num, int end_num) {
		try {
			for (mtt_i = start_num; mtt_i < end_num && mtt[mtt_i] != null;) {
                // 遇到break，跳出本次parser执行
				if(while_or == false){
                	return;
                }
				// 读取到auto关键字,跳转到auto函数处理
				if (mtt[mtt_i].type.equals("auto"))
					auto(mtt);

				// 读取到putnumb关键字,跳转到putnumb函数,输出数值型变量值
				else if (mtt[mtt_i].type.equals("putnumb"))
					putnumb(mtt);

				// 读取到putchar关键字,跳转到putchar函数,输出字符型型变量值
				else if (mtt[mtt_i].type.equals("putchar"))
					putchar(mtt);

				// 读取到类型为++ --,即自增自减，跳转到unaryOperation函数
				else if (mtt[mtt_i].code.equals("++") || mtt[mtt_i].code.equals("--"))
					unaryOperation(mtt);

				// 读取到类型为operator,即操作符类型，跳转到operator函数
				else if (mtt[mtt_i].type.equals("operator"))
					operator(mtt);

				// 读取到关键字getchar,跳转到getchar函数进行获取字符串输入操作
				else if (mtt[mtt_i].type.equals("getchar"))
					getchar(mtt);

				// 读取到关键字getnumb,跳转到getnumb函数进行获取数值输入操作
				else if (mtt[mtt_i].type.equals("getnumb"))
					getnumb(mtt);
				// 读取到if关键字,跳转到if函数,执行if语句
				else if (mtt[mtt_i].type.equals("if"))
					Bif(mtt);
				
				// 读取到if关键字,跳转到if函数,执行if语句
				else if (mtt[mtt_i].type.equals("goto"))
					Bgoto(mtt);
				
				// 读取到note关键字, 跳转到Note函数处理注释
				else if (mtt[mtt_i].type.equals("note"))
					Note(mtt);

				// 读取到putstr函数，输出数组
				else if (mtt[mtt_i].type.equals("putstr"))
					Putstr(mtt);
                
				// 读取到break关键字
				else if (mtt[mtt_i].type.equals("break")){
					System.out.println("break开始运行");
					Break(mtt);
				}
				// 读取到continue
				else if (mtt[mtt_i].type.equals("continue"))
					return;			
				// 都不满足就将mtt_i下标加1,不作处理
				else
					mtt_i++;
			}
		} catch (NotSuchVarException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (NotAVarException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (WrongGrammaticalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MarkDoNotExsitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mtt_i = end_num + 2;
		return;
	}

	private void Break(MyTokenType[] mtt) {
		// TODO Auto-generated method stub
		while_or = false;
		mtt_i += 1;
	}

	// 循环体执行函数 原理和主函数BParser相似 是一个重载函数
	private void Parser(MyTokenType[] mtt, int start_num, int end_num, int eend_num) {
		try {
			for (mtt_i = start_num; mtt_i < end_num && mtt[mtt_i] != null;) {

				// 读取到auto关键字,跳转到auto函数处理
				if (mtt[mtt_i].type.equals("auto"))
					auto(mtt);

				// 读取到putnumb关键字,跳转到putnumb函数,输出数值型变量值
				else if (mtt[mtt_i].type.equals("putnumb"))
					putnumb(mtt);

				// 读取到putchar关键字,跳转到putchar函数,输出字符型型变量值
				else if (mtt[mtt_i].type.equals("putchar"))
					putchar(mtt);

				// 单目运算，自增自减
				else if (mtt[mtt_i].code.equals("++") || mtt[mtt_i].code.equals("--"))
					unaryOperation(mtt);

				// 读取到类型为operator,即操作符类型，跳转到operator函数
				else if (mtt[mtt_i].type.equals("operator"))
					operator(mtt);

				// 读取到关键字getchar,跳转到getchar函数进行获取字符串输入操作
				else if (mtt[mtt_i].type.equals("getchar"))
					getchar(mtt);

				// 读取到关键字getnumb,跳转到getnumb函数进行获取数值输入操作
				else if (mtt[mtt_i].type.equals("getnumb"))
					getnumb(mtt);

				// 读取到note关键字, 跳转到Note函数处理注释
				else if (mtt[mtt_i].type.equals("note"))
					Note(mtt);

				// 读取到putstr函数，输出数组
				else if (mtt[mtt_i].type.equals("putstr"))
					Putstr(mtt);

				// 都不满足就将mtt_i下标加1,不作处理
				else
					mtt_i++;
			}
		} catch (NotSuchVarException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (NotAVarException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (WrongGrammaticalException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		mtt_i = eend_num;
		return;
	}

	// while语句执行条件判断函数
	private boolean WCheck(MyTokenType[] mtt, int start, int end, int end_num)
			throws NotSuchVarException, NotAVarException {

		// 将初始下标指向传过来的while语句判断条件开始的地方
		mtt_i = start;

		// 遍历 直到下标mtt_i指向传过来的 while语句判断语句结尾标志 end
		for (; mtt_i < end; mtt_i++) {

			// 当读取到操作符时 跳转执行 operator函数
			if (mtt[mtt_i].type.equals("operator")) {
				// 定义一个变量来存储返回类型
				int temp = operator(mtt);
				// 如果变量temp是0 返回false 同时让下标mtt_i指向while语句的结尾
				// 如果变量temp是非0 返回 true
				if (temp == 0) {
					mtt_i = end_num;
					return false;
				} else {
					return true;
				}
				// 当读取到类型是数字 并且下一位是右括号时
			} else if (mtt[mtt_i].type.equals("number") && mtt[mtt_i + 1].code.equals(")")) {

				// 将对应的code转换为int型赋值给temp
				int temp = Integer.parseInt(mtt[mtt_i].code);

				// 如果变量temp是0 返回false 同时让下标mtt_i指向while语句的结尾
				// 如果变量temp是非0 返回 true
				if (temp == 0) {
					mtt_i = end_num;
					return false;
				} else {
					return true;
				}
				// 当读取到类型是变量 并且下一位是右括号时
			} else if (mtt[mtt_i].type.equals("var") && mtt[mtt_i + 1].code.equals(")")) {
				// 如果变量map里存在该变量 进行下一步判断
				if ((varmap.get(mtt[mtt_i].code)) != null) {
					// 将map里变量对应的值赋值给临时变量temp
					int temp = varmap.get(mtt[mtt_i].code);

					// 如果变量temp是0 返回false 同时让下标mtt_i指向while语句的结尾
					// 如果变量temp是非0 返回 true
					if (temp == 0) {
						mtt_i = end_num;
						return false;
					} else {
						return true;
					}
					// 如果变量map里不存在该变量 抛出异常
				} else {
					parseresult += "变量" + mtt[mtt_i].code + "未定义\n";
					throw new NotSuchVarException("变量未定义");
				}
			}
		}
		return true;
	}

	// do_while循环
	// 规定do后面语句用花括号括起来
	private void BDo_while(MyTokenType[] mtt) throws NotSuchVarException, NotAVarException, WrongGrammaticalException {
		// if 判断条件开始的地方
		int start = 0;
		// if 判断条件结束的地方
		int end = 0;
		// if 循环体开始的地方
		int start_num = 0;
		// if 循环体结束的地方
		int end_num = 0;
		// 判断是否有while语句
		int has_while = mtt_i;

		// 判断do后面是否是左花括号
		if (!mtt[mtt_i + 1].code.equals("{")) {

			// 如果不是报出异常并停止程序
			parseresult += "语法错误 do_while语句左花括号不存在\n";
			throw new WrongGrammaticalException("语法错误 do_while语句左花括号不存在");
		} else {
			// 当花括号输入正常时
			// 全局指针后移至do需要执行的语句
			mtt_i += 2;
			// 将全局指针赋值给循环开始标志
			start_num = mtt_i;

			// 指针后移，直到寻找到对应右花括号
			for (; !mtt[mtt_i].code.equals("}"); mtt_i++) {
				// 将全局指针赋值给循环结束变量
				// 将判断语句传入Parser时包括判断语句前后的圆括号
				end_num = mtt_i;
			}

			// 判断右花括号后面是否存在while语句
			// 当存在while语句时
			if (mtt[mtt_i + 1].code.equals("while")) {

				// 当while关键字后没有左括号时
				if (!mtt[mtt_i + 2].code.equals("(")) {

					// 报出异常并停止程序
					parseresult += "语法错误 do_while语句左括号不存在\n";
					throw new WrongGrammaticalException("语法错误 do_while语句左括号不存在");
				} else {

					// while后括号正常
					// 全局指针移至判断语句
					mtt_i += 3;
					// 将左括号下标赋给判断开始语句
					start = mtt_i - 1;

					// 指针后移，直到寻找到对应右括号
					for (; !mtt[mtt_i].code.equals(")"); mtt_i++) {

						// 将全局指针赋值给判断结束变量
						end = mtt_i + 1;
					}
					// 判断结束变量的值最终为右括号
				}
			} else {
				// 当循环语句结束后没有找到while关键字时
				// 报出异常并停止程序
				parseresult += "语法错误 do_while语句while关键字不存在\n";
				throw new WrongGrammaticalException("语法错误 do_while语句while关键字不存在");
			}
		}

		// 先将循环语句执行一次
		Parser(mtt, start_num, end_num);

		// 调用WCheck方法，进行判断语句的判断
		// 判断语句为真时
		if (WCheck(mtt, start, end, end_num) == true) {

			// 进入循环语句
			while (WCheck(mtt, start, end, end_num)) {

				// 调用Parser，进行循环语句的执行
				Parser(mtt, start_num, end_num);
			}
		} else {

			// 当判断语句为假时直接将全局指针指向do-while语句末尾
			mtt_i = end + 1;
			return;
		}

		// 将全局指针指向do-while语句末尾
		mtt_i = end + 1;
		return;
	}

	// B语言while循环
	// 规定 while语句一定要具有左右花括号
	private void BWhile(MyTokenType[] mtt) throws WrongGrammaticalException, NotSuchVarException, NotAVarException {

		// while 判断条件开始的地方
		int start = 0;
		// while 判断条件结束的地方
		int end = 0;
		// while 循环体开始的地方
		int start_num = 0;
		// while 循环体结束的地方
		int end_num = 0;

		// 存储左花括号的个数
		int hasQH = 0;

		// 如果在while关键字后不是左括号，则报出语法错误
		if ((!mtt[mtt_i + 1].code.equals("("))) {
			parseresult += "语法错误 while语句左括号不存在\n";
			throw new WrongGrammaticalException("语法错误 while语句左括号不存在");

			// 否则将左括号下一位的下标值赋值给判断条件开始处start的值
		} else {
			start = mtt_i + 1;
		}
		// 找到第一个左花括号
		for (; !(mtt[mtt_i].code.equals("{")); mtt_i++) {
			if (mtt[mtt_i + 1].code.equals("{")) {

				// 读到左花括号，将hasQH值加1
				hasQH += 1;

				// 判断左花括号前是否为右括号
				// 如果是，则将右括号前一位的下标赋值给判断条件结束end的值
				// 并且将左花括号下一位的下标赋值给标志循环体开始的地方start_num的值
				// 如果不是，则报错并终止程序
				if ((mtt[mtt_i].code.equals(")"))) {
					start_num = mtt_i + 2;
					end = mtt_i;
				} else {
					parseresult += "语法错误 左右括号不匹配\n";
					throw new WrongGrammaticalException("语法错误 左右括号不匹配");
				}
			}
		}

		mtt_i = mtt_i + 1;
		// 判断while语句是否正确
		for (; mtt[mtt_i + 1] != null; mtt_i++) {

			// 当读到左花括号时 判断 前面一个是不是右括号
			// 如果不是就报错并终止程序
			if (mtt[mtt_i].code.equals("{")) {

				// 读到左花括号，将hasQH值加1
				hasQH += 1;

				// 判断左花括号前是否为右括号
				// 如果不是，则报错并终止程序
				if ((mtt[mtt_i - 1].code.equals(")"))) {
					continue;
				} else {
					parseresult += "语法错误 左右括号不匹配\n";
					throw new WrongGrammaticalException("语法错误 左右括号不匹配");
				}

				// 如果没有读取到左花括号而是读取到了右花括号 报错并终止程序
				// 并且如果右花括号前面不是分号 报错并终止程序
			}
			if (mtt[mtt_i + 1].code.equals("}")) {
				hasQH -= 1;
				System.out.println(hasQH + " .............");
				if (hasQH == 0) {
					end_num = mtt_i;
					break;
				} else {
					continue;
				}
			}
		}
		if (hasQH != 0) {
			parseresult += "语法错误\n";
			throw new WrongGrammaticalException("语法错误");
		}

		/*
		 * 将判断条件之间的语句传入WCheck 并且传入while语句循环体的结尾标志end_num 首先检查经过处理之后的判断条件是否为真
		 * 如果为真则进入循环体执行 --调用Parser函数 否则直接将全局下标 mtt_i指向while循环体的末尾
		 */
		// 进行while循环语句
		if (WCheck(mtt, start, end, end_num) == true) {
			while (WCheck(mtt, start, end, end_num) && while_or) {
				Parser(mtt, start_num, end_num);
			}
			while_or = true;
		} else {
			mtt_i = end_num;
			return;
		}
		mtt_i = end_num + 1;
		return;
	}

	// B语言的if
	// 规定 if-else语句一定要具有左右花括号
	private void Bif(MyTokenType[] mtt) throws NotSuchVarException, WrongGrammaticalException, NotAVarException {
		// if 判断条件开始的地方
		int start = 0; 
		// if 判断条件结束的地方
		int end = 0;
		// if 循环体开始的地方
		int start_num = 0;
		// if 循环体结束的地方
		int end_num = 0;

		// else 循环体开始的地方
		int estart_num = 0;
		// else 循环体结束的地方
		int eend_num = 0;

		// 是否有左花括号
		boolean hasQH = false;

		// 是否有else语句
		boolean hasElse = false;

		if ((!mtt[mtt_i + 1].code.equals("("))) {
			parseresult += "语法错误 if语句左括号不存在\n";
			throw new WrongGrammaticalException("语法错误 if语句左括号不存在");
		} else {
			start = mtt_i + 1;
		}

		// 判断if语句是否正确
		for (; !(mtt[mtt_i].code.equals("}")); mtt_i++) {

			// 当读到左花括号时 判断 前面一个是不是右括号
			// 如果不是就报错并终止程序
			if (mtt[mtt_i].code.equals("{")) {
				hasQH = true;
				if ((mtt[mtt_i - 1].code.equals(")"))) {
					start_num = mtt_i + 1;
					end = mtt_i - 1;
				} else {
					parseresult += "语法错误 if语句左右括号不匹配\n";
					throw new WrongGrammaticalException("语法错误 if语句左右括号不匹配");
				}

				// 如果没有读取到左花括号而是读取到了右花括号 报错并终止程序
				// 并且如果右花括号前面不是分号 报错并终止程序
				// 在判断结束后，将左花括号是否存在的标志设为错误
				// 以便于进行else语句的判断
			}
			if (mtt[mtt_i + 1].code.equals("}")) {
				end_num = mtt_i;
				if (hasQH == false || !(mtt[mtt_i].code.equals(";"))) {
					parseresult += "语法错误 if语句左右花括号不匹配\n";
					throw new WrongGrammaticalException("语法错误 if语句左右花括号不匹配");
				} else {
					hasQH = false;
				}
			}
		}

		// 判断是否存在else语句
		if (mtt[mtt_i + 1] != null) {
			mtt_i += 1;
			if (mtt[mtt_i].code.equals("else")) {
				hasElse = true;

				// 判断else语句是否正确
				for (; !(mtt[mtt_i].code.equals("}")); mtt_i++) {
					if (mtt[mtt_i].code.equals("{")) {
						hasQH = true;
						estart_num = mtt_i + 1;
					}
					if (mtt[mtt_i + 1].code.equals("}")) {
						eend_num = mtt_i;
						if (hasQH == false || !(mtt[mtt_i].code.equals(";"))) {
							parseresult += "语法错误 else语句异常\n";
							throw new WrongGrammaticalException("语法错误 else语句异常");
						}
					}
				}
			} else {
				hasElse = false;
			}
		}

		// 执行if-else语句
		// 当else语句存在 并且if语句的判断条件错误
		if (hasElse == true && WCheck(mtt, start, end, end_num) == false) {
			// 传入的结尾标志为 else语句的结尾
			Parser(mtt, estart_num, eend_num);
			return;

			// 当else语句不存在 并且if语句的判断条件为真
		} else if (hasElse == false && WCheck(mtt, start, end, end_num) == true) {
			// 传入的结尾标志为 if语句的结尾
			Parser(mtt, start_num, end_num);
			return;

			// 当else语句不存在 并且if语句的判断条件为真
		} else if (hasElse == false && WCheck(mtt, start, end, end_num) == false) {
			// 直接返回
			return;

			// 当else语句存在 并且if语句的判断条件为真
		} else if (hasElse == true && WCheck(mtt, start, end, end_num) == true) {
			// 传入的结尾标志为 else语句的结尾
			Parser(mtt, start_num, end_num, eend_num);
			return;
		}
	}

	// goto函数
	private void Bgoto(MyTokenType[] mtt) throws MarkDoNotExsitException {
		// TODO Auto-generated method stub

		// 判断goto后是否跟着标签变量和分号
		if (mtt[mtt_i + 1].type.equals("var") && mtt[mtt_i + 2].code.equals(";")) {

			// 将下标右移一位
			mtt_i++;

			// 判断hash表中是否存有该标签
			if ((markmap.get(mtt[mtt_i].code)) != null) {

				// 将标签对应的指向跳转的下标赋值给mtt_i
				mtt_i = markmap.get(mtt[mtt_i].code);
				return;

				// 如果不存在该标签,则抛出异常
			} else {
				parseresult += "goto语句后的指定标签不存在\n";
				throw new MarkDoNotExsitException("goto语句后的指定标签不存在");
			}

			// 如果goto之后不是标签,则报错并终止程序
		} else {
			parseresult += "goto语句后的指定标签格式不正确\n";
			throw new MarkDoNotExsitException("goto语句后的指定标签格式不正确");
		}

	}

	// 输入数值的函数
	private void getnumb(MyTokenType[] mtt) throws NotSuchVarException, WrongGrammaticalException {
		// TODO Auto-generated method stub

		// 判断下一位是否是左括号,下下一位是否是变量，下下下一位是否是右括号，下下下一位是否是分号
		if (mtt[mtt_i + 1].code.equals("(") && mtt[mtt_i + 2].type.equals("var") && mtt[mtt_i + 3].code.equals(")")
				&& mtt[mtt_i + 4].code.equals(";")) {

			// 右移一位
			mtt_i += 2;

			// 如果在数值型变量中存在，则可以进行输入赋值，赋值给对应字符串型变量
			if ((varmap.get(mtt[mtt_i].code)) != null) {
				String stringInput = JOptionPane.showInputDialog("请输入变量 " + mtt[mtt_i].code + " 的值：");
				if (stringInput == null || stringInput.trim().equals("")) {
					parseresult += "不可输入空";
					throw new NotSuchVarException("不可输入空");
				}
				parseresult += "输入完成：\n";
				varmap.put(mtt[mtt_i].code, Integer.parseInt(stringInput));

				// 将mtt_i加2,下标位于分号，返回Parser函数
				mtt_i += 2;
				return;
			} else {
				parseresult += "变量" + mtt[mtt_i].code + "未定义\n";
				throw new NotSuchVarException("变量未定义");
			}

		} else {
			parseresult += "语法错误 输入数字型变量语句格式错误\n";
			throw new WrongGrammaticalException("语法错误 输入数字型变量语句格式错误");
		}
	}

	// 输入字符串
	private void getchar(MyTokenType[] mtt) throws NotSuchVarException, WrongGrammaticalException {
		// TODO Auto-generated method stub

		// 判断下一位是否是左括号,下下一位是否是变量,下下下一位是否是右括号,下下下一位是否是分号
		if (mtt[mtt_i + 1].code.equals("(") && mtt[mtt_i + 2].type.equals("var") && mtt[mtt_i + 3].code.equals(")")
				&& mtt[mtt_i + 4].code.equals(";")) {

			// 右移一位
			mtt_i += 2;

			// 如果在数值型变量中存在，则可以进行输入赋值，赋值给对应字符串型变量
			if ((varmap.get(mtt[mtt_i].code)) != null) {
				// System.out.println("请输入：");
				// parseresult+="请输入：\n";
				// Scanner sc = new Scanner(System.in);
				String stringInput = JOptionPane.showInputDialog("请输入变量 " + mtt[mtt_i].code + " 的值：");
				if (stringInput == null || stringInput.trim().equals("")) {
					parseresult += "不可输入空";
					throw new NotSuchVarException("不可输入空");
				}
				parseresult += "输入完成：\n";
				varmap2.put(mtt[mtt_i].code, stringInput);

				// 将mtt_i加2,下标位于分号，返回Parser函数
				mtt_i += 2;
				return;
			} else {
				parseresult += "变量" + mtt[mtt_i].code + "未定义\n";
				throw new NotSuchVarException("变量未定义");
			}

		} else {
			parseresult += "语法错误 输入数字型变量语句格式错误\n";
			throw new WrongGrammaticalException("语法错误 输入数字型变量语句格式错误");
		}
	}

	// 处理操作符的函数
	private int operator(MyTokenType[] mtt) throws NotAVarException, NotSuchVarException {
		// TODO Auto-generated method stub
		Eval eval = new Eval();
		String string = new String();
		if (mtt[mtt_i].code.equals("=")) {
			if (mtt[mtt_i - 1].type.equals("var") && mtt[mtt_i + 1].code.equals("'")
					&& mtt[mtt_i + 2].type.equals("var") && mtt[mtt_i + 3].code.equals("'")
					&& mtt[mtt_i + 4].code.equals(";")) {
				if (varmap.get(mtt[mtt_i - 1].code) != null) {
					varmap2.put(mtt[mtt_i - 1].code, mtt[mtt_i + 2].code);
					mtt_i += 4;
					return 1;
				}
			}
			/**
			 * 
			 */
			if (mtt[mtt_i - 1].code.equals("]") && mtt[mtt_i - 3].code.equals("[")
					&& mtt[mtt_i - 4].type.equals("var")) {
				// 读取数组变量位置
				String arrName = mtt[mtt_i - 4].getCode();

				int placeNumber = 0;
				if (mtt[mtt_i - 2].type.equals("var")) {
					// 变量
					placeNumber = varmap.get(mtt[mtt_i - 2].getCode());
				} else {
					placeNumber = Integer.parseInt(mtt[mtt_i - 2].getCode());
				}
				// 等号右边运算式获取
				mtt_i++;
				while (!mtt[mtt_i].code.equals(";")) {
					if (mtt[mtt_i].type.equals("var")) {
						if (mtt[mtt_i + 1].code.equals("[") && mtt[mtt_i + 3].code.equals("]")) {
							boolean varExistFlag = false;
							for (int i = 0; i < a_i; i++) {
								if (a[i].name.equals(mtt[mtt_i].getCode())) {
									varExistFlag = true;
									break;
								}
							}
							if (!varExistFlag) {
								parseresult += "变量" + mtt[mtt_i].code + "未定义\n";
								throw new NotSuchVarException("变量未定义");
							}
							int placeNumnbertemp = 0;
							if (mtt[mtt_i + 2].type.equals("var")) {
								// 变量
								placeNumnbertemp = varmap.get(mtt[mtt_i + 2].getCode());
							} else {
								placeNumnbertemp = Integer.parseInt(mtt[mtt_i + 2].getCode());
							}
							for (int i = 0; i < a_i; i++) {
								if (a[i].name.equals(mtt[mtt_i].code)) {
									int vartemp = a[i].getArray()[placeNumnbertemp];
									string += vartemp;
									break;
								}
							}
							mtt_i += 3;
						} else {
							string += varmap.get(mtt[mtt_i].code);
						}

					} else {
						string += mtt[mtt_i].code;
					}
					mtt_i++;
				}
				try {
					String res = eval.execute(new Evaluator(), string).getResult();
					// varmap.put(temp, (int) Double.parseDouble(res));
					// 遍历array数组，查找数组变量名是否被定义
					for (int i = 0; i < a_i; i++) {
						if (a[i].name.equals(arrName)) {
							a[i].setBarray(placeNumber, (int) Double.parseDouble(res));
							return 1;
						}
					}
					parseresult += "变量未定义\n";
					throw new NotSuchVarException("变量未定义");
				} catch (FunctionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			if (mtt[mtt_i - 1].type.equals("var")) {

				String temp = mtt[mtt_i - 1].getCode();
				if (varmap.get(temp) == null) {
					System.out.println("wrong!");
					System.exit(0);
				}
				mtt_i++;
				while (!mtt[mtt_i].code.equals(";")) {
					if (mtt[mtt_i].type.equals("var")) {
						if (mtt[mtt_i + 1].code.equals("[") && mtt[mtt_i + 3].code.equals("]")) {
							boolean varExistFlag = false;
							for (int i = 0; i < a_i; i++) {
								if (a[i].name.equals(mtt[mtt_i].getCode())) {
									varExistFlag = true;
									break;
								}
							}
							if (!varExistFlag) {
								parseresult += "变量未定义\n";
								throw new NotSuchVarException("变量未定义");
							}
							int placeNumnbertemp = 0;
							if (mtt[mtt_i + 2].type.equals("var")) {
								// 变量
								placeNumnbertemp = varmap.get(mtt[mtt_i + 2].getCode());
							} else {
								placeNumnbertemp = Integer.parseInt(mtt[mtt_i + 2].getCode());
							}
							for (int i = 0; i < a_i; i++) {
								if (a[i].name.equals(mtt[mtt_i].code)) {
									int vartemp = a[i].getArray()[placeNumnbertemp];
									string += vartemp;
									break;
								}
							}
							mtt_i += 3;
						} else {
							string += varmap.get(mtt[mtt_i].code);
						}

					} else {
						string += mtt[mtt_i].code;
					}
					mtt_i++;
				}
				try {
					System.out.println(string);
					String res = eval.execute(new Evaluator(), string).getResult();
					varmap.put(temp, (int) Double.parseDouble(res));
					return 1;

				} catch (FunctionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				parseresult += "语句错误 执行的参数不是变量\n";
				throw new NotAVarException("语句错误 执行的参数不是变量");
			}
		} else {
			if (mtt[mtt_i - 1].type.equals("var")) {
				string += varmap.get(mtt[mtt_i - 1].code);
			} else {
				string += mtt[mtt_i - 1].code;
			}
			while (!(mtt[mtt_i].code.equals(";")) && (!(mtt[mtt_i].code.equals(")")
					&& (mtt[mtt_i + 1].code.equals("{") || mtt[mtt_i + 1].code.equals(";"))))) {

				if (mtt[mtt_i].type.equals("var")) {
					if (varmap.get(mtt[mtt_i].code) != null) {
						string += varmap.get(mtt[mtt_i].code);
					} else {
						System.out.println("wrong!");
						System.exit(0);
					}

				} else {
					string += mtt[mtt_i].code;
				}
				mtt_i++;
			}
			try {
				System.out.println(string);
				String res = eval.execute(new Evaluator(), string).getResult();
				return (int) Double.parseDouble(res);

			} catch (FunctionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}

	// 输出字符串
	private void putchar(MyTokenType[] mtt) throws NotSuchVarException, WrongGrammaticalException {

		// 计算单引号的个数
		int count = 0;

		// 判断下一位是不是左括号
		// 如果不是左括号 报错并终止程序
		if ((!mtt[mtt_i + 1].code.equals("("))) {
			parseresult += "语法错误 输出字符串语句左括号不存在\n";
			throw new WrongGrammaticalException("语法错误 输出字符串语句左括号不存在");

			// 如果是左括号，将当前全局下标mtt_i加一
		} else {
			mtt_i += 2;
		}

		// 判断当前位置是不是单引号 如过是单引号
		// 就将count赋值为1 并且下标加一、进入循环 输出两个单引号里的内容
		if (mtt[mtt_i].code.equals("'")) {
			mtt_i = mtt_i + 1;
			count = 1;
			for (; count < 2; mtt_i++) {
				if (mtt[mtt_i + 1].code.equals("'")) {
					count += 1;
				}

				// 判断是否读取到了换行标志 *n
				// 如果没有读到 将当前mtt_i指向的code打印输出
				if (!mtt[mtt_i].code.equals("*n")) {
					System.out.print(mtt[mtt_i].code);
					parseresult += mtt[mtt_i].code;
					System.out.print(" ");
					parseresult += " ";
					// 如果读到了 就打印输出换行，
					// 并且将下标mtt_i加1
				} else {
					System.out.println();
					parseresult += "\n";
					continue;
				}
			}
			return;

			// 判断下一位是否是变量
		} else if (mtt[mtt_i].type.equals("var")) {

			// 判断变量之后是否跟着右括号和分号
			if (mtt[mtt_i + 1].code.equals(")") && mtt[mtt_i + 2].code.equals(";")) {

				// 如果对应的数值型变量不为空，且对应的字符型变量也不为空，则输出字符型变量值
				if ((varmap.get(mtt[mtt_i].code)) != null && varmap2.get(mtt[mtt_i].code) != null) {
					System.out.println(varmap2.get(mtt[mtt_i].code));
					parseresult += varmap2.get(mtt[mtt_i].code) + "\n";
					mtt_i += 2;
					return;

					// 如果对应的数值型变量不为空，而对应的字符型变量为空，则输出数字型变量值
				} else if ((varmap.get(mtt[mtt_i].code)) != null && varmap2.get(mtt[mtt_i].code) == null) {
					System.out.println(varmap.get(mtt[mtt_i].code));
					parseresult += varmap.get(mtt[mtt_i].code) + "\n";
					mtt_i += 2;
					return;

					// 如果找不到对应的变量值 则报错
				} else {
					parseresult += "变量未定义\n";
					throw new NotSuchVarException("变量未定义");
				}
			}
			// 如果读到的不是单引号 也不是变量名 则报错
		} else {
			parseresult += "语法错误 输出数字语句格式错误\n";
			throw new WrongGrammaticalException("语法错误 输出字符串语句格式错误");
		}
	}

	// 输出数字函数
	private void putnumb(MyTokenType[] mtt) throws NotSuchVarException, WrongGrammaticalException, NotAVarException {
		// TODO Auto-generated method stub

		// 判断下一位是否是左括号
		if (mtt[mtt_i + 1].code.equals("(")) {

			// 右移一位至左括号处
			mtt_i++;

			// 判断括号后是否是变量
			if (mtt[mtt_i + 1].type.equals("var")) {

				// 右移一位至变量处
				mtt_i++;

				// 判断变量后是否跟着右括号和分号
				if (mtt[mtt_i + 1].code.equals(")") && mtt[mtt_i + 2].code.equals(";")) {

					// 判断在数值型变量map中是否存在该变量，存在就输出
					if ((varmap.get(mtt[mtt_i].code)) != null) {
						System.out.println(varmap.get(mtt[mtt_i].code));
						parseresult += varmap.get(mtt[mtt_i].code) + "\n";

						// 下标移至分号处，返回主函数
						mtt_i += 2;
						return;

						// 不存在报出变量未定义，并终止程序
					} else {
						throw new NotSuchVarException("变量未定义");
					}

					// 判断变量后是否跟着[number]);
				} else if (mtt[mtt_i + 1].code.equals("[") && mtt[mtt_i + 3].code.equals("]")
						&& mtt[mtt_i + 4].code.equals(")") && mtt[mtt_i + 5].code.equals(";")) {
					// 如果中间一位是变量
					if (mtt[mtt_i + 2].type.equals("var")) {
						// 判断下标变量是否存在
						if (varmap.get(mtt[mtt_i + 2].getCode()) != null) {
							// 将变量值暂时保存，稍后作为数组下标
							int numbtemp = varmap.get(mtt[mtt_i + 2].getCode());

							// 判断数组变量是否存在
							for (int i = 0; i < a_i; i++) {
								if (a[i].getName().equals(mtt[mtt_i].code)) {
									System.out.println(a[i].getArray()[numbtemp]);
									parseresult += a[i].getArray()[numbtemp] + "\n";
									return;
								}

							}
							// 抛出异常
							parseresult += "找不到要输出的内容\n";
							throw new NotAVarException("找不到要输出的内容");
						} else {
							// 变量未定义
							parseresult += "变量未定义\n";
							throw new NotSuchVarException("变量未定义");
						}
						// 如果中间一位是纯数字
					} else if (mtt[mtt_i + 2].type.equals("number")) {
						// 判断
						int numbtemp = Integer.parseInt(mtt[mtt_i + 2].code);
						// 判断数组变量是否存在
						for (int i = 0; i < a_i; i++) {
							if (a[i].getName().equals(mtt[mtt_i].code)) {
								System.out.println(a[i].getArray()[numbtemp]);
								parseresult += a[i].getArray()[numbtemp] + "\n";
								return;
							}
						}
						// 抛出异常
						parseresult += "找不到要输出的内容\n";
						throw new NotAVarException("找不到要输出的内容");

					} else {

					}
					// 若不满足变量后是否跟着右括号和分号，则报出语法错误，并终止程序
				} else {
					parseresult += "语法错误 输出数字语句格式错误\n";
					throw new WrongGrammaticalException("语法错误 输出数字语句格式错误");
				}

				// 判断括号后是否是数字常量
			} else if (mtt[mtt_i + 1].type.equals("number")) {
				mtt_i++;
				if (mtt[mtt_i + 1].code.equals(")") && mtt[mtt_i + 2].code.equals(";")) {
					System.out.println(mtt[mtt_i].code);
					parseresult += mtt[mtt_i].code + "\n";
					mtt_i += 2;
					return;
				}

				// 若括号后既不是变量也不是数字常量，则报出语法错误并终止程序
			} else {
				parseresult += "找不到要输出的内容\n";
				throw new NotAVarException("找不到要输出的内容");
			}

			// 如果关键字putnumb后没有紧跟着左括号，则报出语法错误并终止程序
		} else {
			parseresult += "语法错误 输出数字语句格式错误\n";
			throw new WrongGrammaticalException("语法错误 输出数字语句格式错误");
		}

	}

	// 输出数组
	private void Putstr(MyTokenType[] mtt) throws WrongGrammaticalException, NotAVarException {
		// 判断下一位是否是左括号
		if (mtt[mtt_i + 1].code.equals("(")) {
			// 右移至左括号
			mtt_i++;
			if (mtt[mtt_i + 1].type.equals("var") && mtt[mtt_i + 2].code.equals(")")
					&& mtt[mtt_i + 3].code.equals(";")) {
				for (int i = 0; i < a_i; i++) {
					if (a[i].name.equals(mtt[mtt_i + 1].code)) {
						parseresult += a[i].putBarray() + "\n";
						mtt_i += 3;
						return;
					}
					parseresult += "数组" + mtt[mtt_i + 1].code + "未定义\n";
					throw new WrongGrammaticalException("语法错误 输出数字语句格式错误");
				}

			} else {
				parseresult += "语法错误 输出数字语句格式错误\n";
				throw new WrongGrammaticalException("语法错误 输出数字语句格式错误");
			}

		} else {
			parseresult += "语法错误 输出数字语句格式错误\n";
			throw new WrongGrammaticalException("语法错误 输出数字语句格式错误");
		}
	}

	// 定义变量
	private void auto(MyTokenType[] mtt) throws WrongGrammaticalException, NotAVarException {
		// TODO Auto-generated method stub

		// 如果是要定义数组，判断是否为变量[数值]结构
		if (mtt[mtt_i + 1].type.equals("var") && mtt[mtt_i + 2].code.equals("[") && mtt[mtt_i + 3].type.equals("number")
				&& mtt[mtt_i + 4].code.equals("]")) {

			// 将B语言规定的数组长度保存为临时变量temp中
			int temp = Integer.parseInt(mtt[mtt_i + 3].code);

			// 创建一个array类保存该数组
			a[a_i++] = new array(mtt[mtt_i + 1].code, temp);

			// 有以下标，至逗号或分号处
			mtt_i += 4;

			// 如果下一位是分号则返回出去
			if (mtt[mtt_i + 1].code.equals(";")) {
				mtt_i++;
				return;

				// 如果下一位是逗号，则递归
			} else if (mtt[mtt_i + 1].code.equals(",")) {
				mtt_i++;
				auto(mtt);

				// 若在他们之后不是逗号和分号，则报出语法错误并终止程序
			} else {
				parseresult += "定义变量语句出错\n";
				throw new WrongGrammaticalException("定义变量语句出错");
			}

			// 如果下一位是变量，则放入变量栈中
		} else if (mtt[mtt_i + 1].type.equals("var")) {

			// 同时放入数值型和字符串型变量map中
			varmap.put(mtt[mtt_i + 1].code, 0);
			varmap2.put(mtt[mtt_i + 1].code, null);

			// 将下标右移至变量处
			mtt_i++;

			// 如果下下一位是分号，则下标加一并返回去
			if ((mtt[mtt_i + 1].code.equals(";"))) {
				mtt_i++;
				return;

				// 如果下一位是等号，则进行赋值处理后返回主函数
			} else if ((mtt[mtt_i + 1].code.equals("="))) {
				mtt_i++;
				try {
					operator(mtt);
				} catch (NotSuchVarException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;

				// 如果下一位是逗号，则递归
			} else if (mtt[mtt_i + 1].code.equals(",")) {
				mtt_i++;
				auto(mtt);

				// 如果不是上述情况，则出现语法错误，报错并终止程序
			} else {
				parseresult += "定义变量语句出错\n";
				throw new WrongGrammaticalException("定义变量语句出错");
			}

			// 如果不是上述情况，则出现语法错误，报错并终止程序
		} else {
			parseresult += "定义变量语句出错\n";
			throw new WrongGrammaticalException("定义变量语句出错");
		}

	}
	//判断switch语句的条件
	private int Scheck1(MyTokenType[] mtt, int start){
		//数组下标指向判定条件开始的地方
		mtt_i = start;
		//用于存储条件的值
		int temp;
		
		//返回条件变量的值
		
			temp = varmap.get(mtt[mtt_i].code);
			return temp;
		
	}
	
	
	private String Scheck2(MyTokenType[] mtt, int start){
		
		        //数组下标指向判定条件开始的地方
				mtt_i = start;
				//用于存储条件的值
				String temp;
				
				//返回条件变量的值
				
					temp = varmap2.get(mtt[mtt_i].code);
					return temp;
	}
	
	
	
	
	//switch语句的使用
	private void Bswitch(MyTokenType[] mtt) {
		// switch判断条件开始的地方
		int start = 0;
		// switch判断条件结束的地方
		int end = 0;
		// switch 循环体开始的地方
		int start_num = 0;
		// switch 循环体结束的地方
	    int end_num = 0;
	    // case语句开始的地方
	    int cstart_num = 0;
	    // case语句结束的地方
	    int cend_num = 0;
	    //用来存储mtt_i的中间取值，回到switch开始的地方
	    int Stemp = 0;


		boolean hasQH = false;
		// 是否有左花括号
		boolean hasCase = false;
		// 是否有case语句
		boolean hasMH = false;
		//是否有冒号
		
		if ((!mtt[mtt_i + 1].code.equals("("))) {
			System.out.println("wrong!173 -- 找不到左括号");
			System.exit(0);
		} else {
			start = mtt_i + 1;
		}
		
		//判断switch语句的条件是否存在
		
		//保留当前mtt_i的取值
		Stemp = mtt_i;
		
		for (; !(mtt[mtt_i].code.equals("}")); mtt_i++) {
			// 当读到左花括号时 判断 前面一个是不是右括号
			// 如果不是就报错并终止程序
			if (mtt[mtt_i].code.equals("{")) {
				hasQH = true;
				if ((mtt[mtt_i - 1].code.equals(")"))) {
					start_num = mtt_i + 1;
					end = mtt_i - 1;
				} else {
					System.out.println("wrong!201 -- 请检查switch语句是否正确");
					System.exit(0);
				}
				
			}
			// 如果没有读取到左花括号而是读取到了右花括号 报错并终止程序
			// 并且如果右花括号前面不是分号 报错并终止程序
			// 在判断结束后，将左花括号是否存在的标志设为错误
			if (mtt[mtt_i + 1].code.equals("}")) {
				end_num = mtt_i;
				if (hasQH == false || !(mtt[mtt_i].code.equals(";"))) {
					System.out.println("wrong!201 -- 请检查switch语句是否正确");
					System.exit(0);
				} else {
					hasQH = false;
				}
			}
		}
		
		
		//回到当初的mtt_i取值
		mtt_i = Stemp;
				if (mtt[mtt_i + 1] != null) {
					mtt_i += 1;
					// 判断是否存在case语句
					if (mtt[mtt_i].code.equals("case")) {
						hasCase = true;
						
						// 判断case语句是否正确
						for (; !(mtt[mtt_i].code.equals("break")); mtt_i++) {
							if (mtt[mtt_i].code.equals(":")) {
								hasMH = true;
								cstart_num = mtt_i + 1;
							}
							//判断单个case语句何时结束
							if (mtt[mtt_i + 1].code.equals("break")) {
								cend_num = mtt_i;
								if (!(mtt[mtt_i].code.equals(";"))) {
									System.out.println("wrong!233 -- 请检查case语句是否正确");
									System.exit(0);
								}
							}
						}
					} else {
						hasCase = false;
					}
				}
		
		
				
				//执行switch语句
				mtt_i = Stemp;
				
				//创建一个整数型数组，用于存储每次出现case时的位置
				int case_position[] = new int[100];
				//case_position数组的下标
				int cposition = 0;
				
				//循环记录每次出现case的地方
				for(;!mtt[mtt_i].code.equals("}");mtt_i++){
					if(mtt[mtt_i].code.equals("case")){
						case_position[cposition] = mtt_i;
						cposition++;
					}
				}
				
				
				
				//调取条件变量的取值

				
				//如果条件变量是数字型
				if(mtt[start].type.equals("number")){
					for(int i = 0;i <= cposition;cposition++){
						if(Scheck1(mtt,start) == mtt[case_position[i]+1].hashCode()){
							Parser(mtt,case_position[i]+3,case_position[i+1]-1);
						}
					}
				}else{
					//如果条件变量是字符型
					for(int i = 0;i<=cposition;cposition++){
						if(Scheck2(mtt,start) == mtt[case_position[i]+2].code){
							Parser(mtt,case_position[i]+4,case_position[i+1]-1);
						}
					}
				}
											
	}
}
