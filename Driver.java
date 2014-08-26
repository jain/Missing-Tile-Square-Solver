import javax.swing.JFrame;

public class Driver {

	public static void main(String[] args){
		JFrame frame = new JFrame("Missing Square");
		//Bin panel = new Bin();
		Optim panel = new Optim();
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
