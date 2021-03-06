package objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import other.Config;

public class Cell {
	
	private int posX;
	private int posY;
	private boolean mine;
	private boolean flag;
	private Color color;
	private boolean open;
	
	/**
	 * Cell Constructor, A Game is made of a number of cells that may or may not contain mines
	 * @param posX The horizontal position in units
	 * @param posY The vertical position in units
	 * @param mine True if Cell has a mine, False otherwise
	 */
	public Cell(int posX, int posY, boolean mine) {
		this.mine = mine;
		this.flag = false;//starts out false
		this.posX = posX;
		this.posY = posY;
		this.color = Config.CELL_COLOR_CLOSED;
		open = false;
	}
	
	//Position Getters
	public int getPosX() {
		return this.posX;
	}

	public int getPosY() {
		return this.posY;
	}
	
	//Flag Getters & Setters
	public boolean isFlag() {
		return flag;
	}

	/**
	 * will only set flag to true if not open
	 * @param flag
	 */
	public void setFlag(boolean flag) {
		if(flag == true && this.open == false)
			this.flag = true;
		else if(flag == false)
			this.flag = false;
	}

	//Mine Getter
	public boolean isMine() {
		return mine;
	}
	
	public void setMine(boolean mine) {
		this.mine = mine;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isOpen() {
		return open;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void fillCell(Graphics graphics, Game game) {
		Graphics2D g2D = (Graphics2D) graphics;
        g2D.setStroke(new BasicStroke(2));
        
		graphics.setColor(this.color);
		int newX = this.posX * Config.CELL_DISTANCE;
		int newY = this.posY * Config.CELL_DISTANCE;
		graphics.fill3DRect(newX, 
				newY + Config.Y_OFFSET, Config.CELL_DISTANCE, 
				Config.CELL_DISTANCE, true);
		
		if(!Config.GAME_END) {
			if(this.isFlag()) {
				graphics.setColor(Config.FLAG_COLOR);
				graphics.fillRect(newX + Config.FLAG_DIFF, newY + Config.FLAG_DIFF + Config.Y_OFFSET,
						Config.FLAG_SIZE, Config.FLAG_SIZE);
			}
			if(this.open && !this.mine) {
				graphics.setColor(Config.MINE_NUM_COLOR);
				graphics.setFont(new Font("TimesRoman", Font.PLAIN, Config.FONT_SIZE)); 
				graphics.drawString(Integer.toString(game.getNeighborMineNum(this.posX, this.posY)), 
						newX + (int)(Config.FLAG_DIFF * (Config.CELL_DISTANCE / 20.0)) + 1, 
						newY + Config.CELL_DISTANCE - ((int)(Config.FLAG_DIFF * (Config.CELL_DISTANCE / 20.0)) / 2) - 2 + Config.Y_OFFSET);//-1 to center
			}
		}
		else {
			if(this.isFlag()) {
				if(this.isMine()) {//correct flag placement
					graphics.setColor(Color.GREEN);
					graphics.fillRect(newX + Config.FLAG_DIFF, newY + Config.FLAG_DIFF + Config.Y_OFFSET,
							Config.FLAG_SIZE, Config.FLAG_SIZE);
				}
				else {//Incorrect flag placement
					graphics.setColor(Color.BLUE);
					graphics.fillRect(newX + Config.FLAG_DIFF, newY + Config.FLAG_DIFF + Config.Y_OFFSET,
							Config.FLAG_SIZE, Config.FLAG_SIZE);
					graphics.setColor(Color.RED);
					
					g2D.drawLine(newX + Config.FLAG_DIFF, newY + Config.FLAG_DIFF + Config.Y_OFFSET,
							newX + Config.FLAG_DIFF + Config.FLAG_SIZE, newY + Config.FLAG_DIFF + Config.Y_OFFSET + Config.FLAG_SIZE);
					g2D.drawLine(newX + Config.FLAG_DIFF + Config.FLAG_SIZE, newY + Config.FLAG_DIFF + Config.Y_OFFSET,
							newX + Config.FLAG_DIFF, newY + Config.FLAG_DIFF + Config.Y_OFFSET + Config.FLAG_SIZE);
				}
				
			}
			else {
				if(this.isMine()) {//Mine not marked
//					graphics.setColor(Color.BLACK);
//					graphics.fillRect(newX + Config.FLAG_DIFF, newY + Config.FLAG_DIFF + Config.Y_OFFSET,
//							Config.FLAG_SIZE, Config.FLAG_SIZE);
				}
			}
			if(this.open && !this.mine) {
				graphics.setColor(Config.MINE_NUM_COLOR);
				graphics.setFont(new Font("TimesRoman", Font.PLAIN, Config.FONT_SIZE)); 
				graphics.drawString(Integer.toString(game.getNeighborMineNum(this.posX, this.posY)), 
						newX + (int)(Config.FLAG_DIFF * (Config.CELL_DISTANCE / 20.0)) + 1, 
						newY + Config.CELL_DISTANCE - ((int)(Config.FLAG_DIFF * (Config.CELL_DISTANCE / 20.0)) / 2) - 2 + Config.Y_OFFSET);//-1 to center
			}
		}
	}
	
	/**
	 * Opens this Cell object
	 * @return if the Cell is already open returns false, else true
	 */
	public boolean openCell() {
		if(!this.open && !this.flag) {
			if(this.mine) {
				this.setColor(Config.CELL_COLOR_MINE);
			}
			else {
				this.setColor(Config.CELL_COLOR_OPENED);
			}
			this.open = true;
			return true;
		}
		else {
			return false;
		}
		
	}
	
	public String toString() {
		return "Cell[" + this.posX + "," + this.posY + "]\n" 
	+ "Flag: " + this.flag + " | Mine: " + this.mine;
	}

	public void swapFlagState() {
		this.flag = !this.flag;
	}

}
