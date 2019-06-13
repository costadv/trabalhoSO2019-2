package structure;

import main.MainGUI;

public class Execute extends Thread {
	CpuRobin cpu;
	
	public Execute(CpuRobin cpu) {
		this.cpu = cpu;
	}
	
	public void run() {
		while (this.cpu.getQueueHead() != null || this.cpu.getCoreFirstProcess() != null) {

			
			//this.cpu.listCores();			
			this.cpu.fillCores();
			this.cpu.tick();
			this.cpu.organizePanels();
			try {
				sleep(300); 
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				return;
			}
			this.cpu.organizePanels();
			this.cpu.tickVerification();
			
		}
		MainGUI.executando = false;
	}
	
}
