package structure;
import main.MainGUI;

public class Process {
	private int pid;
	private int remainingTime;
	private int totalTime;
	private int priority;
	private int spaceRequired;
	private Process nextProcess;
	
	public Process() {
		this.pid = MainGUI.globalPid;
		MainGUI.globalPid += 1;
		int rn;
		do {
			rn = (int)(Math.random()*100);
		}while(rn < 10 || rn > 30);
		this.remainingTime = rn;
		this.totalTime = rn;
		do {
			rn = (int)(Math.random()*10);
		}while(rn > 3);
		this.priority = rn;
		this.nextProcess = null;
		do {
			rn = (int)(Math.random()*10000);
		}while(rn < 32 || rn > 1024);
		this.spaceRequired = rn;
	}

	public int getPid() {
		return pid;
	}

	public int getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(int remainingTime) {
		this.remainingTime = remainingTime;
	}

	public int getTotalTime() {
		return totalTime;
	}

	public int getPriority() {
		return priority;
	}
	
	/*public boolean getState() {
		return state;
	}
	
	public void alterState() {
		state = !state;
	}*/

	public Process getNextProcess() {
		return nextProcess;
	}

	public void setNextProcess(structure.Process nextProcess) {
		this.nextProcess = nextProcess;
	}

	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}
	
	public int getSpaceRequired() {
		return this.spaceRequired;
	}
	
}
