package structure;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ProcessScreen {
	private ProcessScreen nextProcessScreen = null;
	private structure.Process process;
	private JPanel processPanel;
	private boolean newProcess;
	private JLabel lblP;
	private JLabel lblPid;
	private JLabel lblSpace;
	private JLabel lblTempoTotal;
	private JLabel lblTempoRestante;
	
	public ProcessScreen(JPanel mainPanel, boolean newProcess, structure.Process process) {
		this.nextProcessScreen = null;
		this.process = process;
		this.newProcess = newProcess;
		this.processPanel = new JPanel();
		if (newProcess) {
			this.processPanel.setBackground(Color.CYAN);
		} else {
			this.processPanel.setBackground(Color.GREEN);
		}
		mainPanel.add(this.processPanel);
		this.processPanel.setBounds(0, 0, 65, 80);
		this.processPanel.setLayout(null);

		lblP = new JLabel("P" + this.process.getPid());
		lblP.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblP.setBounds(10, 25, 50, 27);
		this.processPanel.add(lblP);
		/*
		lblPid = new JLabel("" + this.process.getPid());
		lblPid.setBounds(20, 35, 24, 14);
		this.processPanel.add(lblPid);
		 */
		lblSpace = new JLabel(this.process.getSpaceRequired() + "B");
		lblSpace.setBounds(28, 10, 40, 14);
		this.processPanel.add(lblSpace);

		lblTempoTotal = new JLabel("/" + this.process.getTotalTime());
		lblTempoTotal.setBounds(23, 60, 17, 14);
		this.processPanel.add(lblTempoTotal);

		lblTempoRestante = new JLabel("" + this.process.getRemainingTime());
		lblTempoRestante.setBounds(10, 60, 17, 14);
		this.processPanel.add(lblTempoRestante);

		/*
		processPositionY += 85;
		if ((processPositionY + 80) >= 640) {
			processPositionY = 45;
			processPositionX += 60;
		}
		*/
		
	}
	
	public void AddNextProcessScreen(ProcessScreen ps) {
		this.nextProcessScreen = ps;
	}
	
	public void changeLocation(int x, int y) {
		this.processPanel.setBounds(x, y, this.processPanel.getWidth(), this.processPanel.getHeight());
	}
	
	public structure.Process getProcess() {
		return this.process;
	}
	
	public ProcessScreen getNextProcessScreen() {
		return this.nextProcessScreen;
	}

	public void setNextProcessScreen(ProcessScreen nextProcessScreen) {
		this.nextProcessScreen = nextProcessScreen;
	}
	
	public int getLblTempoRestante() {
		return Integer.parseInt(lblTempoRestante.getText());
	}
	
	public void LowerTempoRestante() {
		this.lblTempoRestante.setText((this.getLblTempoRestante()-1) + "");
	}
	
	public void destroyPanel() {
		this.processPanel.removeAll();
		this.processPanel.setVisible(false);
	}
	
	public boolean getNewProcess() {
		return this.newProcess;
	}
	
}
