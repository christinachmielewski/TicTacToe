package a8;

import java.awt.event.ActionListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

public class TicTacToeWidget extends JPanel implements ActionListener, SpotListener{
	
	/** Enumeration to identify the player **/
	
	private enum Player {BLACK, WHITE};
	
	private JSpotBoard board;
	private JLabel message;
	private boolean gameOver;
	private Player currentPlayer;
	
	public TicTacToeWidget () {
		/** Create SpotBoard and message label **/
		
		board = new JSpotBoard(3,3);
		message = new JLabel();
		
		/** Set layout and place SpotBoard at the center**/
		
		setLayout(new BorderLayout());
		add(board, BorderLayout.CENTER);
		
		for(Spot x: board) {
			x.setBackground(new Color(0.8f, 0.8f, 0.8f));
			x.setSpotColor(Color.RED);
		}
		
		
		/** Create subpanel for message area and reset button **/
		
		JPanel resetMessagePanel = new JPanel();
		resetMessagePanel.setLayout(new BorderLayout());
		
		/** Reset button, add ourselves as action listener**/
		JButton resetButton = new JButton ("Restart");
		resetButton.addActionListener(this);
		resetMessagePanel.add(resetButton, BorderLayout.EAST);
		resetMessagePanel.add(message, BorderLayout.CENTER);
		
		/** Add subpanel in the south area of layout**/
		add(resetMessagePanel, BorderLayout.SOUTH);
		
		board.addSpotListener(this);
		
		/** reset game **/
		resetGame();
	}
	
	/* resetGame()
	 * 
	 * resets game by clearing spots on board, resetting game status fields,
	 * and displaying start message
	 *
	 */
	
	private void resetGame() {
		for( Spot s: board) {
			s.clearSpot();
			s.setSpotColor(Color.RED);
		}
	
		
		message.setText("Welcome to TicTacToe. White to play first");
		
		gameOver = false;
		
		currentPlayer = Player.WHITE;
	}
	
	@Override
	public void actionPerformed (ActionEvent e) {
		resetGame();
	}
	
	@Override
	public void spotClicked(Spot s) {
		if(gameOver) {
			return;
		}
		if(!s.isEmpty()) {
			return;
		}
		
		String playerName = null;
		String nextPlayerName = null;
		Color spotColor = null;
		
		if(currentPlayer == Player.WHITE) {
			spotColor = Color.WHITE;
			playerName = "White";
			nextPlayerName = "Black";
			currentPlayer = Player.BLACK;
			
		} else {
			spotColor = Color.BLACK;
			playerName = "Black";
			nextPlayerName = "White";
			currentPlayer = Player.WHITE;
		}
		
		s.setSpotColor(spotColor);
		s.setSpot();
		
		// Check if spot clicked makes tic tac toe for player
		
		if(isGameWon(spotColor)) {
			message.setText(playerName + " has won the game!");
			gameOver = true;
		}else if(isDraw()) {
			message.setText("Board is full, game is a draw");
			gameOver = true;
		}else {
			message.setText(nextPlayerName + " to play");
		}
	}
	
	
		
	public void spotEntered(Spot s) {
		if(gameOver) {
			return;
		}
		if(s.isEmpty()) {
			s.highlightSpot();
		}

	}
	
	public void spotExited(Spot s) {
		s.unhighlightSpot();
	}
	
	private boolean isGameWon (Color c) {
		for(int i = 0 ; i < 3 ; i++) {
			if(board.getSpotAt(i, 0).getSpotColor()== c &&
				board.getSpotAt(i, 1).getSpotColor() == c &&
				board.getSpotAt(i,  2).getSpotColor() == c) {
				return true;
			}
		}
		for(int i = 0 ; i < 3 ; i++) {
			if(board.getSpotAt(0, i).getSpotColor()== c &&
				board.getSpotAt(1, i).getSpotColor() == c &&
				board.getSpotAt(2, i).getSpotColor() == c) {
				return true;
			}
		}
		if(board.getSpotAt(0, 0).getSpotColor()== c &&
				board.getSpotAt(1, 1).getSpotColor() == c &&
				board.getSpotAt(2, 2).getSpotColor() == c) {
				return true;
		}
		if(board.getSpotAt(0, 2).getSpotColor()== c &&
				board.getSpotAt(1, 1).getSpotColor() == c &&
				board.getSpotAt(2, 0).getSpotColor() == c) {
				return true;
		}
		return false;
		
		
	}
	private boolean isDraw() {
		for(Spot s: board) {
			if(s.isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
	
	
	}
