package Tests;

import Model.ChessBoard;
import Model.Position;
import Pieces.King;
import View.GUI;
import org.junit.Test;

import javax.swing.*;

public class GUITest {
    ChessBoard chessBoard = new ChessBoard();
    King white_king = new King(true, new Position(0, 4), chessBoard);
    King black_king = new King(false, new Position(7, 4), chessBoard);


    @Test
    public void TestisLoading() throws Exception {

        // I create my panel and frame
        GUI panel = new GUI(chessBoard,
                new ImageIcon("/home/luvsandondov/IdeaProjects/ChessGame/resources/icons/ChessPieces.png").getImage(),
        new ImageIcon("/home/luvsandondov/IdeaProjects/ChessGame/resources/icons/other_img.png").getImage());
        JFrame frame = new JFrame("Test Chess Game View.GUI");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Size of the frame
        frame.setSize(64 + 512, 64 + 512);
        frame.getContentPane().add(panel);
        //frame.pack();
        frame.setVisible(true);
        // Better way?
        //while(true) {}
    }


}