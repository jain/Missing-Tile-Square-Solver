import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Bin extends JPanel{
	private JTextField arrTxt[][];
	private int arr[][];
	private JButton solve;
	private JButton next;
	private JButton gen;
	private JButton test;
	private LinkedList<int[][]> track;
	public Bin(){
		setLayout(new GridLayout(5, 4));
		setBorder(BorderFactory.createLineBorder(Color.black));
		arrTxt = new JTextField[4][4];
		arr = new int[4][4];
		for (int i = 0; i<4; i++){
			for (int j = 0; j<4; j++){
				JTextField txt = new JTextField();
				Font myFont = new Font("Serif", Font.BOLD, 20);
				txt.setHorizontalAlignment(JTextField.CENTER);
				txt.setFont(myFont);
				add(txt);
				arrTxt[i][j] = txt;
			}
		}
		solve = new JButton("solve");
		solve.addActionListener(new SolveListener());
		add(solve);
		next = new JButton("next");
		next.addActionListener(new HintListener());
		add(next);
		gen = new JButton("gen");
		gen.addActionListener(new GenListener());
		add(gen);
		test = new JButton("test");
		test.addActionListener(new TestListener());
		add(test);
	}
	public boolean conver(){
		boolean bool = true;
		for (int i = 0; i<4; i++){
			for (int j = 0; j<4; j++){
				if (arrTxt[i][j].getText().equals("")){
					arr[i][j] = -1;
				} 
				else{
					arr[i][j] = tryParse(i, j);
					if (arr[i][j]<0 || arr[i][j] > 15){
						bool = false;				
					}
				}
			}
		}
		return bool;
	}
	public int tryParse(int i, int j) {
		// TODO Auto-generated method stub
		try {
			return Integer.parseInt(arrTxt[i][j].getText());
		} 
		catch (NumberFormatException e) {
			return 0;
		}
	}
	public LinkedList<int[][]> solve(){
		/*class Comp implements Comparator<Node>{
			@Override
			public int compare(Node a, Node b) {
				// TODO Auto-generated method stub
				int num = a.getCost()-b.getCost();
				if(num>0){
					return 1;
				}else if(num == 0){
					return 0;
				}
				return -1;
			}
		}*/
		Node current = new Node(arr);
		Node previous = new Node(arr);
		int temp[][]; 
		//ArrayList<Long> visited = new ArrayList<Long>(11);
		Set<Integer> visited = new HashSet<Integer>();
		int hash = current.getHash();
		//Comp inst = new Comp();
		LinkedList<Node> q = new LinkedList<Node>();
		int x;
		int y;
		int tmp = 0;
		q.add(current);
		int h;
		int count = 0;
		long start = System.currentTimeMillis();
		while(hash!=438279353&&!q.isEmpty()&&count<50000){
			count++;
			previous = q.poll();
			hash = previous.getHash();
			visited.add(hash);
			//visited.add(hash);
			x = previous.getX();
			y = previous.getY();
			if(x<3){
				temp = copy(previous.getTarr());
				tmp = temp[x][y];
				temp[x][y] = temp[x+1][y];
				temp[x+1][y] = tmp;
				current = new Node(temp);
				h = current.getHash();
				current.setPrevious(previous);
				if(!visited.contains(h)){
					q.add(current);
				}
			}
			if(y<3){
				temp = copy(previous.getTarr());
				tmp = temp[x][y];
				temp[x][y] = temp[x][y+1];
				temp[x][y+1] = tmp;
				current = new Node(temp);
				h = current.getHash();
				current.setPrevious(previous);
				if(!visited.contains(h)){
					q.add(current);
				}
			}
			if(x>0){
				temp = copy(previous.getTarr());
				tmp = temp[x][y];
				temp[x][y] = temp[x-1][y];
				temp[x-1][y] = tmp;
				current = new Node(temp);
				h = current.getHash();
				current.setPrevious(previous);
				if(!visited.contains(h)){
					q.add(current);
				}
			}
			if(y>0){
				temp = copy(previous.getTarr());
				tmp = temp[x][y];
				temp[x][y] = temp[x][y-1];
				temp[x][y-1] = tmp;
				current = new Node(temp);
				h = current.getHash();
				current.setPrevious(previous);
				if(!visited.contains(h)){
					q.add(current);
				}
			}
		}
		long stop = System.currentTimeMillis();
		System.out.println(stop-start);
		System.out.println(hash);
		LinkedList<int[][]> list = new LinkedList<int[][]>();
		//previous = previous.getPrevious();
		System.out.println(count);
		while(previous!=null){
			arr = previous.getTarr();
			list.add(arr);
			previous = previous.getPrevious();
		}
		return list;
	}
	public int[][] copy(int arr[][]){
		int temp[][] = new int[4][4];
		for(int i = 0; i<4; i++){
			for(int j = 0; j<4; j++){
				temp[i][j] = arr[i][j];
			}
		}
		return temp;
	}
	public void print(){
		for(int i = 0; i<4; i++){
			System.out.println(Arrays.toString(arr[i]));
		}
	}
	private class GenListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			int count = 0;
			for(int i = 0; i<4; i++){
				for(int j = 0; j<4; j++){
					if(count!=15){
						count++;
						arrTxt[i][j].setText(count + "");
					}else{
						arrTxt[i][j].setText("0");
					}
				}
			}
		}
	}
	private class TestListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			int temp[][] = {{1, 7, 4, 8},
					{5, 3, 2, 15},
					{9, 6, 0, 12},
					{13, 10, 14, 11}};
			for(int i = 0; i<4; i++){
				for(int j = 0; j<4; j++){
					arrTxt[i][j].setText(temp[i][j] + "");
				}
			}
		}
	}
	private class SolveListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			solve.setText("Solve Again");
			boolean possible = conver();
			if(possible){
				print();
				solve();
				print();
			}
		}
	}
	private class HintListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(track==null){
				boolean possible = conver();
				if(possible){
					print();
					LinkedList<int[][]> list = solve();
					list.removeLast();
					int[][] temp = list.removeLast();
					for(int i = 0; i<4; i++){
						for(int j = 0; j<4; j++){
							arrTxt[i][j].setText(temp[i][j] + "");
						}
					}
					track = list;
					print();
				}
			} else{
				if(track.size()!=0){
					int[][] temp = track.removeLast();
					for(int i = 0; i<4; i++){
						for(int j = 0; j<4; j++){
							arrTxt[i][j].setText(temp[i][j] + "");
						}
					}
				}
			}
		}
	}
	public class Node{
		private int[][] tarr;
		private int hash;
		private int x;
		private int y;
		private Node previous;
		public Node(int[][] tarr){
			this.tarr = tarr;
			hash = genHash();
		}
		public void setPrevious(Node pass){
			previous = pass;
		}
		public Node getPrevious(){
			return previous;
		}
		public int genHash(){
			int count = 0;
			int num = 0;
			for(int i = 0; i<4; i++){
				for(int j = 0; j<4; j++){
					//num += (long)(Math.pow(10, count))*tarr[i][j];
					num += tarr[i][j]<<(count*3);
					count++;
					if(tarr[i][j]==0){
						x = i;
						y = j;
					}
				}
			}
			return num;	
		}
		public int getHash(){
			return hash;
		}
		public int[][]getTarr(){
			return tarr;
		}
		/*public boolean equal(Node temp) {
			// TODO Auto-generated method stub
			for(int i = 0; i<3; i++){
				for(int j = 0; j<3; j++){
					if(tarr[i][j]!=temp.getTarr()[i][j]){
						return false;
					}
				}
			}
			return true;
		}*/
		public int getX() {
			return x;
		}
		public int getY() {
			return y;
		}
	}
}
