package structure;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.MainGUI;

public class Block {
	private static int bid;
	private int totalSpace;
	private int usedSpace = 0;
	private int processid;
	private boolean freeBlock = true;
	private Block nextBlock = null;
	private JPanel blockPanel;
	private JPanel pidPanel;
	private JLabel lblB;
	private JLabel lblBid;
	private JLabel lblProcessId;
	private JLabel lblTotalSpace;
	private JLabel lblUsedSpace;
	
	public Block(JPanel mainPanel, int totalSpace, int x, int y) {
		this.bid = MainGUI.globalBid;
		MainGUI.globalBid += 1;
		this.totalSpace = totalSpace;
		
		this.blockPanel = new JPanel();
		this.blockPanel.setBounds(x, y, 65, 90);
		this.blockPanel.setLayout(null);
		mainPanel.add(this.blockPanel);

		lblB = new JLabel("B" + this.bid);
		lblB.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblB.setBounds(2, 2, 60, 27);
		this.blockPanel.add(lblB);

		lblTotalSpace = new JLabel("T: " + this.getTotalSpace() + "B");
		lblTotalSpace.setBounds(5, 58, 55, 15);
		this.blockPanel.add(lblTotalSpace);
		
		lblUsedSpace = new JLabel("U: " + this.usedSpace + "B");
		lblUsedSpace.setBounds(5, 70, 55, 15);
		this.blockPanel.add(lblUsedSpace);
		
	}
	
	public void setNextBlock(Block b) {
		this.nextBlock = b;
	}
	
	public Block getNextBlock() {
		return nextBlock;
	}
	
	public void setTotalSpace(int totalSpace) {
		this.totalSpace = totalSpace;
	}
	
	public int getTotalSpace() {
		return totalSpace;
	}
	
	public void setUsedSpace(int usedSpace) {
		this.usedSpace = usedSpace;
	}
	
	public int getUsedSpace() {
		return usedSpace;
	}
	
	public void occupyBlock(int processid, boolean newProcess, int us) {
		this.freeBlock = false;
		this.processid = processid;
		this.usedSpace = us;
		this.lblUsedSpace.setText("U: " + us + "B");
		this.pidPanel = new JPanel();
		pidPanel.setBounds(10, 30, 45, 25);
		this.blockPanel.add(pidPanel);
		lblProcessId = new JLabel("P"+processid);
		lblProcessId.setFont(new Font("Tahoma", Font.PLAIN, 16));
		if (newProcess) {
			this.pidPanel.setBackground(Color.CYAN);
		} else {
			this.pidPanel.setBackground(Color.GREEN);
		}
		lblProcessId.setBounds(2, 2, 41, 23);
		this.pidPanel.add(lblProcessId);
		this.blockPanel.setBackground(Color.RED);
		
	}
	
	public boolean getBlockStatus() {
		return this.freeBlock;
	}
	
	public void freeBlock() {
		this.freeBlock = true;
		this.usedSpace = 0;
		this.lblUsedSpace.setText("U: " + 0 + "B");
		this.blockPanel.remove(pidPanel);
		this.blockPanel.setBackground(Color.GREEN);
		this.processid = -1;
	}
	
	public int getProcessid() {
		return this.processid;
	}
	
}
