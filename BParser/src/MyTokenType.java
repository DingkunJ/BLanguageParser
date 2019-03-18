public class MyTokenType {
    String type;
    String code;
	public MyTokenType() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MyTokenType(String type, String code) {
		this.type = type;
		this.code = code;
	}
	@Override
	public String toString() {
		return "MyTokenType [type=" + type + ", code=" + code + "]";
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
