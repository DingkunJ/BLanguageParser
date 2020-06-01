import java.awt.EventQueue;

public class Main {
     public static void main(String[] args) {
 		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BParse window = new BParse();
					window.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
