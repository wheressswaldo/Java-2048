/*
 * Kong Huang
 * CS1213
 * 2048 in swing
 * This file is for creating the grid, painting it, as well as containing all the algorithms needed for
 * movement after keypresses (moving and merging lines)
 */

package Final;

import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class Grid extends JPanel{
	// serialVersionUID for supressing warning
	private static final long serialVersionUID = 1L;
	// number of rows
	public static final int ROW = 4;
	// define the main as the host
    final main2048 host;
    
    // array of tiles used for drawing the grid
    private Tile[] tiles;
    // set goal as 2048 value from NumbersAndColors
    public static NumbersAndColors goal = NumbersAndColors._2048;

    private static final Color BG_COLOR = new Color(0xbbada0);
    // set the font of the numbers, the size and font style can be changed here
    private static final Font STR_FONT = new Font(Font.SERIF, Font.BOLD, 16);
    private static final int SIDE = 64;
    private static final int MARGIN = 16;
    
    // constructor
    public Grid (main2048 main){
    	host = main;
    	setFocusable(true);
    	initializeTiles();
    }
    
    // addNewTile finds a random spot on the grid that is empty and initializes a new random tile on it
    private void addNewTile(){
    	// list of all empty tiles
    	List<Integer> list = findEmptyIndex();
    	// choose randomly from the list
    	int index = list.get((int) (Math.random() * list.size()));
    	tiles[index] = Tile.randomTile();
    }
    
    // initializeTiles setups the board on first run, setting all the tiles to 0 then adding two new tiles
    // also used to reset the board    
    public void initializeTiles() {
        tiles = new Tile[ROW * ROW];
        // set all tiles to 0
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = Tile.ZERO;
        }
        // add two tiles to start off
        addNewTile();
        addNewTile();
        host.statusBar.setText("");
    }
    
    // findEmptyIndex returns a list of empty tiles
    private List<Integer> findEmptyIndex() {
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i].empty())
                list.add(i);
        }
        return list;
    }
    
    // check if the grid is full (as in no empty tiles)
    private boolean isGridFull() {
        return findEmptyIndex().size() == 0;
    }
	
    // return tile at x, y, where x is the columns and y is the rows, grid shown below to help visualize
    // [0]  [1]  [2]  [3]
    // [4]  [5]  [6]  [7]
    // [8]  [9]  [10] [11]
    // [12] [13] [14] [15]
    // 1,0 would be [1], 1,1 would be [5], 2,1 would be [6], etc.
    private Tile tileAt(int x, int y) {
        return tiles[x + y * ROW];
    }
    
    // checkIfCanMove checks if there are tiles blocking a certain direction
    boolean checkIfCanMove() {
        if (!isGridFull()) {
            return true;
        }
        for (int x = 0; x < 4; x++){
        	for (int y = 0; y < 4; y++){
                Tile t = tileAt(x, y);
                if ((x < ROW - 1 && t.equals(tileAt(x + 1, y))) || (y < ROW - 1 && t.equals(tileAt(x, y + 1)))) {
                    return true;
                }
            }
        }
        return false;
    }
    
    // rotate returns an array of Tiles which is rotated the degrees its given
    // only works with 90, 180, 270, look at code for why
    // this is used to literally rotate the board, used for cheating later
    private Tile[] rotate(int degree) {
        Tile[] newTiles = new Tile[ROW * ROW];
        int offsetX = 3, offsetY = 3;
        if (degree == 90) {
            offsetY = 0;
        } else if (degree == 180) {
        } else if (degree == 270) {
            offsetX = 0;
        }
        double radians = Math.toRadians(degree);
        int cos = (int) Math.cos(radians);
        int sin = (int) Math.sin(radians);
        for (int x = 0; x < 4; x++){
        	for (int y = 0; y < 4; y++){
                int newX = (x * cos) - (y * sin) + offsetX;
                int newY = (x * sin) + (y * cos) + offsetY;
                newTiles[(newX) + (newY) * ROW] = tileAt(x, y);
            }
        }
        return newTiles;
    }
    
    // the next 5 functions are functions used to make writing the directional code easier
    // well, not easier but for code reuse
    // getLine returns the selected line as an array
    private Tile[] getLine(int index) {
        Tile[] result = new Tile[4];
        for (int i = 0; i < 4; i++){
            result[i] = tileAt(i, index);
        }
        return result;
    }
    
    // makes sure size is correct, add 0s if it isn't, as when you shift
    // the tiles over, you're left with empty spots
    private static void ensureSize(List<Tile> l, int s) {
        while (l.size() < s) {
            l.add(Tile.ZERO);
        }
    }
    
    // move the lines, return a new line
    private Tile[] moveLine(Tile[] oldLine) {
        LinkedList<Tile> l = new LinkedList<>();
        for (int i = 0; i < 4; i++){
            if (!oldLine[i].empty())
                l.addLast(oldLine[i]);
        }
        if (l.size() == 0) {
            return oldLine;
        } else {
            Tile[] newLine = new Tile[4];
            ensureSize(l, 4);
            for (int i = 0; i < 4; i++){
                newLine[i] = l.removeFirst();
            }
            return newLine;
        }
    }
    
    // merges the lines
    private Tile[] mergeLine(Tile[] oldLine) {
        LinkedList<Tile> list = new LinkedList<Tile>();
        for (int i = 0; i < ROW; i++) {
            if (i < ROW - 1 && !oldLine[i].empty()
                    && oldLine[i].equals(oldLine[i + 1])) {
                Tile merged = oldLine[i].getDouble();
                i++;
                list.add(merged);
                if (merged.value() == goal) {
                    host.win();
                }
            } else {
                list.add(oldLine[i]);
            }
        }
        ensureSize(list, 4);
        return list.toArray(new Tile[4]);
    }
    
    // set the line
    private void setLine(int index, Tile[] re) {
    	for (int i = 0; i < 4; i++){
            tiles[i + index * ROW] = re[i];
        }
    }
    
    // use getLine, moveLine, mergeLine, setLine to move everything to the left, merging accordingly
    public void left(){
        boolean needAddTile = false;
        for (int i = 0; i < 4; i++){
            Tile[] origin = getLine(i);
            Tile[] afterMove = moveLine(origin);
            Tile[] merged = mergeLine(afterMove);
            setLine(i, merged);
            if (!needAddTile && !Arrays.equals(origin, merged)) {
                needAddTile = true;
            }
        }
        if (needAddTile) {
            addNewTile();
        }
    }
    // #codereuse woo, super cheating style
    // since moving everything to the left is already written
    // you rotate the board 180 degrees, move everything to the right, then rotate 180 degrees
    // that's the same as moving it to the right
    public void right() {
    	// ROTATIONS THO
        tiles = rotate(180);
        left();
        tiles = rotate(180);
    }
    // moving up, same logic as before
    public void up() {
        tiles = rotate(270);
        left();
        tiles = rotate(90);
    }
    // moving down, same logic as before
    public void down() {
        tiles = rotate(90);
        left();
        tiles = rotate(270);
    }
 
    // function used for drawing
    private static int offsetCoors(int arg) {
        return arg * (MARGIN + SIDE) + MARGIN;
    }
    
    // draws the tile
    private void drawTile(Graphics g, Tile tile, int x, int y) {
        NumbersAndColors val = tile.value();
        int xOffset = offsetCoors(x);
        int yOffset = offsetCoors(y);
        g.setColor(val.color());
        g.fillRect(xOffset, yOffset, SIDE, SIDE);
        g.setColor(val.fontColor());
        
        if (val.value() != 0)
            g.drawString(tile.toString(), xOffset
                    + (SIDE >> 1) - MARGIN, yOffset + (SIDE >> 1));
    }
    
    // paints the grid, called whenever there's a key event to update the board
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(BG_COLOR);
        g.setFont(STR_FONT);
        g.fillRect(0, 0, this.getSize().width, this.getSize().height);
        for (int x = 0; x < 4; x++){
        	for (int y = 0; y < 4; y++){
                drawTile(g, tiles[x + y * ROW], x, y);
            }
        }
    }

}
