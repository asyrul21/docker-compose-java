package main;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.lang.Math;

public class Chatbot extends JFrame implements KeyListener {
	
	JPanel p = new JPanel();
	JTextArea dialog = new JTextArea(20, 50);
	JTextArea input=new JTextArea(1,50);
	JScrollPane scroll = new JScrollPane(
			dialog,
			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
			);
	
	String[][] chatbot= {
			// standard greetings
			{"hi", "hello", "ola", "howdy"},
			{"hi", "hello", "hey"},
			// question greetings
			{"how are you", "how r you", "how r u"},
			{"good", "doing well"},
			// default
			{"shut up", "I dont understand", "stop talking"}
	};
	
	// constructor
	public Chatbot() {
		super("Chatbot");
		
		// JPanel props
		setSize(800, 400);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		dialog.setEditable(false);
		input.addKeyListener(this);
		
		p.add(scroll);
		p.add(input);
		p.setBackground(new Color(255,200, 0));
		add(p);
		
		setVisible(true);
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			input.setEditable(false);
			
			// grab input
			String quote = input.getText().toLowerCase();
			input.setText("");
			addText("--> YOU:\t" + quote);
			quote = quote.trim();
			
			// remove punctuation
			while(
				quote.charAt(quote.length() - 1) == '!' ||
				quote.charAt(quote.length() - 1) == '.' ||
				quote.charAt(quote.length() - 1) == '?'
				
			) {
				// take away punctuation
				quote = quote.substring(0, quote.length() - 1);
			}
			quote = quote.trim();
			byte response = 0;
			/*
			 * 0 means searching
			 * 1 means dont find anything
			 * 2 means found a match
			 * */
			
			// check for matches
			int j = 0; //which group we are checking
			
			while(response == 0) {
				if(inArray(quote, chatbot[j*2])) {
					response  =2;
					
					int r = (int)Math.floor(Math.random() * chatbot[(j*2) + 1].length);
					addText("\n-->Michael\t" + chatbot[(j*2) + 1][r]);
				}
				
				j++;
				
				if(j * 2 == chatbot.length - 1 && response == 0) {
					response = 1;
				}
			}
			
			// default
			if(response==1) {
				int r = (int)Math.floor(Math.random() * chatbot[chatbot.length-1].length);
				addText("\n-->Michael\t" + chatbot[chatbot.length-1][r]);
			}
			addText("\n");
		};
	}
	
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			input.setEditable(true);
		};
	}
	
	public void keyTyped(KeyEvent e) {}
	
	public void addText(String str) {
		dialog.setText(dialog.getText() + str);
	}
	
	public boolean inArray(String input, String[] str) {
		boolean match = false;
		for(int i = 0; i< str.length; i++) {
			if(str[i].equals(input)) {
				match = true;
			}
		}
		return match;
	}
}

// RUN
// java Chatbot.java
// https://www.youtube.com/watch?v=a8RUmnPL8aQ&t=321s
// 2012
