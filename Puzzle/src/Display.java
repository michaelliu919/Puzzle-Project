
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;


import java.awt.BorderLayout;

//display class, displays the puzzle
public class Display extends JComponent {

	//data field
	private BoardComponent board;
	public JPanel puzzle;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Display d = new Display();
		d.GUI();
	}
	
	

	/**
	 * Create the application.
	 */
	public Display() {
		board = new BoardComponent();
	}
	
	//gui creates the frame
	public void GUI() {
		JFrame frame = new JFrame("World's Hardest Jigsaw Puzzle");
		frame.setSize(1150,550);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		puzzle = new JPanel(new GridLayout(2,5));
		board.reset();
		board.repaint();
		puzzle.add(board);

		
		frame.setVisible(true);
		
		JPanel buttonPanel = new JPanel();
		JButton reset = new JButton("Reset");
		buttonPanel.add(reset);
		
		frame.add(puzzle, BorderLayout.CENTER);
		System.out.println(puzzle.getWidth());
		
		
		
		for(int i = 0; i<9; i++) {
			if(i == 7){
				puzzle.add(board);
			}
			puzzle.add(board.getPieceComponents().get(i));
		}
		System.out.println(board);
		System.out.println(board.getX() + " " + board.getY());
		repaint();
		
		/*
		 * Reset ActionListener button
		 */
		reset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			JFrame resetFrame = new JFrame("Do you want to reset?");
			resetFrame.setSize(225,70);
			resetFrame.setLocation(400,300);
			resetFrame.setResizable(false);
			JButton yes = new JButton("Yes");
			
			yes.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					board.reset();
					for(int i = 0; i<9; i++) {
						puzzle.add(board.getPieceComponents().get(i));
					}
					repaint();
					resetFrame.dispose();
				}
			});
			JButton no = new JButton("No");
			no.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					resetFrame.dispose();
				}
			});
			JPanel panel1 = new JPanel();
			panel1.add(yes);
			
			panel1.add(no);
			
			resetFrame.add(panel1);
			resetFrame.setVisible(true);
			}
		});
		
		
		/*
		 * solve button
		 */
		JButton solve = new JButton("Solve");
		solve.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JFrame solveFrame = new JFrame("Are you a cheater?");
				solveFrame.setSize(225,70);
				solveFrame.setLocation(400,300);
				solveFrame.setResizable(false);
				
				
				JButton yes = new JButton("Yes");
				yes.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						board.solve();
						solveFrame.dispose();
						JFrame triedFrame = new JFrame("Ready to do it on your own?");
						triedFrame.setLocation(400,300);
						triedFrame.setResizable(false);
						triedFrame.setSize(260,70);
						
						JPanel buttons = new JPanel();
						JButton resetSolve = new JButton("Yes");
						JButton quit = new JButton("Quit");
						buttons.add(resetSolve);
						resetSolve.addActionListener(new ActionListener(){
							public void actionPerformed(ActionEvent e){
								board.reset();
								for(int i = 0; i<9; i++) {
									puzzle.add(board.getPieceComponents().get(i));
								}
								repaint();
								triedFrame.dispose();
							}
						});
						
						quit.addActionListener(new ActionListener(){
							public void actionPerformed(ActionEvent e){
								System.exit(0);
							}
						});
						
						buttons.add(quit);
						triedFrame.add(buttons);
						triedFrame.setVisible(true);
					}
				});
				JButton quit = new JButton("No");
				quit.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						solveFrame.dispose();
					}
				});
				JPanel panel1 = new JPanel();
				panel1.add(yes);
				
				panel1.add(quit);
				
				solveFrame.add(panel1);
				solveFrame.setVisible(true);
			}
		});
		buttonPanel.add(solve);
		
		JButton consolePrint = new JButton("Print");
		consolePrint.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println(board);
			}
		});
		buttonPanel.add(consolePrint);
		
		
		frame.add(buttonPanel, BorderLayout.SOUTH);
		

//		frame.add(puzzle, BorderLayout.CENTER);
		frame.setVisible(true);
		for(int i = 0; i<9; i++) {
			board.getPieceComponents().get(i).init();
		}
		board.repaint();
		System.out.println(board);
//		System.out.println(puzzle.getPieceComponents().get(0).getX());
	}

}
