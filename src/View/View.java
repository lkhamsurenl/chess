package View;

import Controller.MyButtonListener;
import Controller.MyListener;
import Controller.Player;
import Model.ChessBoard;
import Model.PlayGame;
import Model.Position;
import Pieces.ChessPiece;
import Pieces.Pawn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by luvsandondov on 9/20/14.
 */
public class View extends JFrame {
    /*
    * This class includes all frames. buttons, and panels for a game
    * */
    private Player player1;
    private Player player2;

    // Some static constants on showing UI pretty.
    public static int frame_margin_width = 32;
    public static int frame_margin_height = 32;

    // Actual chessboard sizes.
    public static int board_height = 512;
    public static int board_width = 512;

    static int playerPanel_width = 64 * 3;

    // Keeping track of last movement.
    public static ChessPiece lastMovedPiece = null;
    public static ChessPiece lastRemovedPiece = null;
    public static int previous_row;
    public static int previous_col = -1;

    /**
     * ***********************************************************************************************************
     * Member variables
     * *************************************************************************************************************
     */
    public static ChessBoard chessBoard;

    /**
     * ***********************************************************************************************************
     * Constructors
     * *************************************************************************************************************
     */

    /*
    * Frame is always fixed no matter what the players are
    * @param name -- Name of the Frame
    * @param chessBoard -- chessBoard players are playing
    * */
    public View(String name, ChessBoard chessBoard) {
        super(name);
        this.chessBoard = chessBoard;

        // container for game and Playes
        JPanel bigContainer = new JPanel();
        bigContainer.setLayout(new BoxLayout(bigContainer, BoxLayout.X_AXIS));
        bigContainer.setPreferredSize(new Dimension(board_width + frame_margin_width + playerPanel_width, board_height));
        // add the game Panel
        JPanel mainPanel = getMainGamePanel();
        bigContainer.add(mainPanel);
        bigContainer.add(getSmallContainer(mainPanel));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Size of the frame
        this.setSize(2 * frame_margin_width + board_width + GUI.squareSize * 3, 2 * frame_margin_height + board_height);
        this.getContentPane().add(bigContainer);
        this.setVisible(true);
        /*
        * Showing marginal alphabet and numbers
        * */
    }

    /**************************************************************************************************************
     * Public Functions
     * **************************************************************************************************************/
    /*
    * Keep track of teh last movement, so that can do Undo operation
    * @param row -- row the piece was previously on
    * @param col -- column the piece was previously on
    * */
    public static void queryRemovedPiece(ChessPiece lastMovedPiece1, ChessPiece lastRemovedPiece1, int row, int col) {
        lastMovedPiece = lastMovedPiece1;
        lastRemovedPiece = lastRemovedPiece1;
        previous_row = row;
        previous_col = col;
    }

    // Get player
    public Player getPlayer(boolean isPlayerOne) {
        if (isPlayerOne) {
            return player1;
        } else
            return player2;
    }

    /*
    * Reset the variables to teh initial value
    * */
    public static void reset() {
        PlayGame.removeAllPieces(chessBoard);
        PlayGame.turn = true;
        PlayGame.START = false;
        lastMovedPiece = null;
        lastRemovedPiece = null;
    }

    /*
    * Increment winner score by 1 in the system(Hashmap of Players)
    * */
    public static void incrementWinnerScore(Player winner) {
        Integer score = PlayGame.playersList.get(winner.getName());
        score = score + 1;
        PlayGame.playersList.put(winner.getName(), score);
        // set score so that players can see it
        winner.setScore(score);
        winner.repaint();
    }

    /**
     * ***********************************************************************************************************
     * Helper functions
     * *************************************************************************************************************
     */
    /*
    * Get the main chess board interface
    * */
    private GUI getMainGamePanel() {
        /***********************************************************************************************************
         * Following panel is main game display
         * *********************************************************************************************************/
        // TODO(luvsandondov): Change the static file path.
        GUI panel = new GUI(chessBoard,
                new ImageIcon("/Users/lkhamsurenl/Development/ChessGame/resources/icons/ChessPieces.png").getImage(),
                new ImageIcon("/Users/lkhamsurenl/Development/ChessGame/resources/icons/other_img.png").getImage());
        panel.setPreferredSize(new Dimension(board_width + 2 * frame_margin_width, board_height + 2 * frame_margin_height));
        // Pass both JPanel and chessboard ot perform necessary action when mouse events happen
        MyListener myListener = new MyListener(this, panel, chessBoard);
        panel.addMouseListener(myListener);
        panel.addMouseMotionListener(myListener);
        return panel;
    }
    /***********************************************************************************************************
     * Displaying two Players Panel
     * *********************************************************************************************************/
    // Shows Controller.Player information and Buttons
    // @param Main Chess Game panel
    private JPanel getSmallContainer(JPanel mainPanel) {
        // container for game and Players
        JPanel smallContainer = new JPanel();
        smallContainer.setLayout(new BoxLayout(smallContainer, BoxLayout.Y_AXIS));
        smallContainer.setPreferredSize(new Dimension(playerPanel_width, board_height));
        // Those two represents the Controller.Player container
        player1 = createPlayers(mainPanel, true);
        player2 = createPlayers(mainPanel, false);

        // Buttons
        JButton undoButton = getUndoButton();
        JButton startButton = getStartButton();
        JButton restartButton = getRestartButton();

        smallContainer.add(player1);
        smallContainer.add(player2);
        smallContainer.add(undoButton);
        smallContainer.add(Box.createRigidArea(new Dimension(0, 40)));
        smallContainer.add(startButton);
        smallContainer.add(Box.createRigidArea(new Dimension(0, 5)));
        smallContainer.add(restartButton);
        smallContainer.add(Box.createRigidArea(new Dimension(0, 40)));

        return smallContainer;
    }
    /*
    *   Creates two separate Panels for each player's info
    *   @param mainPanel -- Chess Game panel
    *   @return -- Whichever Panel for player
    *
    * */
    private Player createPlayers(JPanel mainPanel, boolean isWhitePlayer) {
        Player panel = new Player(isWhitePlayer);
        panel.setPreferredSize(new Dimension(GUI.squareSize * 3, GUI.squareSize * 3));
        // Add button
        JButton newNameButton = new JButton("Change player");
        newNameButton.setLocation(75, 125);
        panel.add(newNameButton);
        MyButtonListener listener = new MyButtonListener(this, mainPanel, panel, chessBoard);
        newNameButton.addMouseListener(listener);
        //add forfeit button
        JButton forfeitButton = getForfeitButton(panel);
        forfeitButton.setLocation(75, 150);
        panel.add(forfeitButton);
        return panel;
    }

    /*
    * Undo last movement
    * @return -- Undo Button
    * */
    private JButton getUndoButton() {
        JButton undoButton = new JButton("Undo");
        undoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        //Add action listener to button
        undoButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                // Start of the game, there is no undo
                if (lastMovedPiece != null) {
                    int curr_row = lastMovedPiece.getRow();
                    int curr_col = lastMovedPiece.getCol();
                    // Find the last movement

                    lastMovedPiece.setPosition(new Position(previous_row, previous_col));
                    if (lastRemovedPiece != null) {
                        lastRemovedPiece.setPosition(new Position(curr_row, curr_col));
                    }
                    // Give the turn back
                    PlayGame.turn = !PlayGame.turn;
                    // Pieces.Pawn is has to get back to init state if it was init move
                    if (lastMovedPiece instanceof Pawn && Math.abs(curr_row - previous_row) == 2) {
                        // it was initial move,
                        Pawn pawn = (Pawn) lastMovedPiece;
                        pawn.setInitState(true);
                    }
                    lastMovedPiece = null;
                    lastRemovedPiece = null;
                    repaint();
                } else {
                    showErrorDialog("Cannot do Undo operation");
                }
            }
        });
        return undoButton;
    }


    /*
    * Start button
    * @return -- start button created
    * */
    private JButton getStartButton() {
        JButton startButton = new JButton("Start");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        //Add action listener to button
        startButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (player1.getName() == null) {
                    askName(true);
                }
                if (player2.getName() == null) {
                    askName(false);
                } else if (player1.getName().equals(player2.getName())) {
                    // Both players cannot have same name
                    showErrorDialog("Players cannot have same name!");
                } else if(PlayGame.START) {
                    showErrorDialog("The game has already started!");
                }
                else {

                    //GIVE players option to choose which way to play
                    int gameType = getGameType();
                    if (gameType == 0) {
                        PlayGame.initializeChessBoard(chessBoard, true);
                    } else if (gameType == 1) {
                        PlayGame.initializeChessBoard(chessBoard, false);
                    }
                    PlayGame.START = true;
                    // repaint so that pieces can be shown
                    repaint();
                }
            }
        });
        return startButton;
    }

    // Show a dialog message asking to enter name
    private void askName(boolean isPlayerOne) {
        int player = isPlayerOne ? 1 : 2;
        JOptionPane.showMessageDialog(this, "Please enter the name for Controller.Player: " + player);
    }

    // Restart button
    private JButton getRestartButton() {
        JButton restartButton = new JButton("Restart");
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        //Add action listener to button
        restartButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                reset();
                // repaint
                repaint();
            }
        });
        return restartButton;
    }

    /*
    * Forfeit Button
    * */
    private JButton getForfeitButton(final Player player) {
        JButton forfeitButton = new JButton("Forfeit");
        //Add action listener to button
        forfeitButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                boolean isPlayerOne = player == player1?true:false;
                forfeitGame(isPlayerOne);
            }
        });
        return forfeitButton;
    }

    private void forfeitGame(boolean isPlayerOne) {
        Player player = getPlayer(isPlayerOne);
        JOptionPane.showMessageDialog(this, player.getName() + " has forfeited the game!");
        reset();
        incrementWinnerScore(getPlayer(!isPlayerOne));
        // repaint
        repaint();
    }

    // general error Dialog shows message
    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    // Decides if the game is regular and exiting
    private int getGameType() {
        //Custom button text
        Object[] options = {"Regular Chess", "Exciting Chess"};
        int n = JOptionPane.showOptionDialog(this,
                "Choose which type of game to play",
                "Game Type",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);
        return n;
    }
}
