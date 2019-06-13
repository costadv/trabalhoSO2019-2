package structure;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class CpuRobin {
	
	private int totalCores;
	private int totalProcessInCpu = 0;
	private int quantum;
	private ProcessScreen coreFirstProcess = null;
	private ProcessScreen coreCompleteProcess = null;
	private ProcessScreen queueHead = null;
	private int memorySpace;
	private int memoryUsed = 0;
	private Block memoryBlocks = null;
	private JPanel mainPanel;
	private static int corePositionX = 50;
	private static int corePositionY = 75;
	private static int processPositionX = 50;
	private static int processPositionY = 185;
	private static int blockPositionX = 50;
	private static int blockPositionY = 730;
	
	public CpuRobin(int totalCores, int memorySpace, JPanel mainPanel) {
		this.mainPanel = mainPanel;
		this.totalCores = totalCores;
		this.memorySpace = memorySpace;
		int rn;
		do {
			rn = (int)(Math.random()*100);
		}while(rn < 2 || rn > 20);
		this.quantum = rn;
		//System.out.println("Quantum = " + quantum);
	}

	public ProcessScreen getCoreFirstProcess() {
		return coreFirstProcess;
	}

	public ProcessScreen getQueueHead() {
		return queueHead;
	}

	public boolean staggerProcess() {
		if((totalProcessInCpu < totalCores)&&queueHead!=null) {
			//int staggeredProcessID = queueHead.getProcess().getPid();
			
			//verificar memoria
			if(!(checkForProcessBlock(queueHead))) {
				if((memorySpace - memoryUsed) > queueHead.getProcess().getSpaceRequired()) {
					createMemoryBlock(queueHead.getProcess().getSpaceRequired());
					this.memoryUsed += queueHead.getProcess().getSpaceRequired();
				}
			}
			
			if(coreFirstProcess==null) {
				coreFirstProcess = queueHead;
				queueHead = queueHead.getNextProcessScreen();
				coreFirstProcess.setNextProcessScreen(null);
				
				if(!(checkForProcessBlock(coreFirstProcess))) {
					if(bestFit(coreFirstProcess) != null) {
						bestFit(coreFirstProcess).occupyBlock(coreFirstProcess.getProcess().getPid(), coreFirstProcess.getNewProcess(), coreFirstProcess.getProcess().getSpaceRequired());
						this.totalProcessInCpu++;
					} else {
						freeOccupiedBlock(coreFirstProcess);
						coreFirstProcess.destroyPanel();
						coreFirstProcess = null;
					}
				} else {
					this.totalProcessInCpu++;
				}
				
			}else {
				staggerProcessRecursive(coreFirstProcess);
			}
			//System.out.println("Processo " + staggeredProcessID + " escalonado!");
			return true;
		}else {
			if(queueHead==null) {
				//System.out.println("Nao ha mais processos na fila, favor aguardar execucao dos processos");
				return false;
			}else {
				//System.out.println("Favor aguardar liberação da CPU");
				return false;
			}
		}
	
	}
	
	public void staggerProcessRecursive(ProcessScreen next) {
		if(next.getNextProcessScreen() == null) {
			next.setNextProcessScreen(queueHead);
			queueHead = queueHead.getNextProcessScreen();
			next.getNextProcessScreen().setNextProcessScreen(null);
			
			if(!(checkForProcessBlock(next.getNextProcessScreen()))) {
				if(bestFit(next.getNextProcessScreen()) != null) {
					bestFit(next.getNextProcessScreen()).occupyBlock(next.getNextProcessScreen().getProcess().getPid(), next.getNextProcessScreen().getNewProcess(), next.getNextProcessScreen().getProcess().getSpaceRequired());
					this.totalProcessInCpu++;
				} else {
					freeOccupiedBlock(next.getNextProcessScreen());
					next.getNextProcessScreen().destroyPanel();
					next.setNextProcessScreen(null);
				}
			} else {
				this.totalProcessInCpu++;
			}
			
		}else {
			staggerProcessRecursive(next.getNextProcessScreen());
		}
	}
	
	public void fillCores() {
		while(staggerProcess());
	}
	
	public void listCores() {
		ProcessScreen p = coreFirstProcess;
		while(p!=null) {
			System.out.print(p.getProcess().getPid() + "(" + p.getProcess().getRemainingTime() + ")" + "[" + p.getProcess().getPriority() + "]" + "->");
			p = p.getNextProcessScreen();
		}
		System.out.println("");
	}
	
	public void listCompleted() {
		structure.Process p = coreCompleteProcess.getProcess();
		while(p!=null) {
			System.out.print(p.getPid() + "(" + p.getRemainingTime() + ")" + "[" + p.getPriority() + "]" + "->");
			p = p.getNextProcess();
		}
		System.out.println("");
	}
	
	public void tick() {
		ProcessScreen p = coreFirstProcess;
		while(p!=null) {
			p.getProcess().setRemainingTime((p.getProcess().getRemainingTime()-1));
			p.LowerTempoRestante();
			p = p.getNextProcessScreen();
		}
		//System.out.println("tick");
		
	}
	
	public void tickVerification() {
			if(coreFirstProcess!=null) {
				if(coreFirstProcess.getProcess().getRemainingTime() <= (coreFirstProcess.getProcess().getTotalTime()-quantum)||coreFirstProcess.getProcess().getRemainingTime() <= 0) {
					ProcessScreen aux = coreFirstProcess;
					coreFirstProcess = coreFirstProcess.getNextProcessScreen();
					aux.setNextProcessScreen(null);
					completeProcess(aux);
				}
				if(coreFirstProcess!=null) {
					tickVerificationRecursive(coreFirstProcess);
				}
			}
	}
	
	public void tickVerificationRecursive(ProcessScreen p) {
		while(p.getNextProcessScreen()!=null) {
			if(p.getNextProcessScreen().getProcess().getRemainingTime() <= (p.getNextProcessScreen().getProcess().getTotalTime()-quantum)||p.getNextProcessScreen().getProcess().getRemainingTime() <= 0) {
				ProcessScreen aux = p.getNextProcessScreen();
				p.setNextProcessScreen(aux.getNextProcessScreen());
				aux.setNextProcessScreen(null);
				completeProcess(aux);
			}else {
				p = p.getNextProcessScreen();
			}
		}
	}
	
	public void completeProcess(ProcessScreen p) {
		this.totalProcessInCpu--;
		p.getProcess().setTotalTime(p.getProcess().getRemainingTime());
		if(p.getProcess().getRemainingTime() <= 0) {
			//liberar memory block
			if(coreCompleteProcess==null) {
				coreCompleteProcess = p;
			}else {
				completeProcessRecursive(coreCompleteProcess, p);
			}
			p.destroyPanel();
			freeOccupiedBlock(p);
		}else {
			if(queueHead==null) {
				queueHead = p;
			}else {
				completeProcessRecursive(queueHead, p);
			}
		}
	}
	
	public void completeProcessRecursive(ProcessScreen next, ProcessScreen insert) {
		if(next.getNextProcessScreen() == null) {
			next.setNextProcessScreen(insert);
		}else {
			completeProcessRecursive(next.getNextProcessScreen(), insert);
		}
	}
	
	//Queue functions
	
	public void queueProcess(ProcessScreen p) {
		if(queueHead==null) {
			queueHead = p;
		}else {
			queueProcessRecursive(queueHead, p);
		}
		//System.out.println("Processo " + p.getProcess().getPid() + " adicionado!");
	}
	
	public void queueProcessRecursive(ProcessScreen next, ProcessScreen insert) {
		if(next.getNextProcessScreen() == null) {
			next.setNextProcessScreen(insert);
		}else {
			queueProcessRecursive(next.getNextProcessScreen(), insert);
		}
	}
	
	public void listQueue() {
		ProcessScreen p = queueHead;
		while(p!=null) {
			System.out.print(p.getProcess().getPid() + "(" + p.getProcess().getTotalTime() + ")" + "[" + p.getProcess().getPriority() + "]" + "->");
			p = p.getNextProcessScreen();
		}
		System.out.println("");
	}

	public int getQuantum() {
		return quantum;
	}

	public void setQuantum(int quantum) {
		this.quantum = quantum;
	}
	
	public void organizePanels() {

		processPositionX = 50;
		processPositionY = 185;
		ProcessScreen ps = queueHead;
		while(ps != null) {
			ps.changeLocation(processPositionX, processPositionY);
			processPositionX += 70;
			if ((processPositionX + 70) >= 1919) {
				processPositionY += 85;
				processPositionX = 50;
			}
			ps = ps.getNextProcessScreen();
		}
		
		corePositionX = 50;
		corePositionY = 75;
		ProcessScreen p = coreFirstProcess;
		while(p != null) {
			p.changeLocation(corePositionX, corePositionY);
			corePositionX += 70;
			if ((corePositionX + 70) >= 1919) {
				corePositionY += 85;
				corePositionX = 50;
			}
			p = p.getNextProcessScreen();
		}
	}
	
	public void createMemoryBlock(int blockSpace) {
		if (memoryBlocks == null) {
			memoryBlocks = new Block(mainPanel, blockSpace, blockPositionX, blockPositionY);
			blockPositionX += 70;
			if ((blockPositionX + 70) >= 1919) {
				blockPositionY += 215;
				blockPositionX = 50;
			}
		} else {
			createMemoryBlockRecursive(memoryBlocks, blockSpace);
		}
	}
	
	public void createMemoryBlockRecursive(Block b, int blockSpace) {
		if (b.getNextBlock() == null) {
			b.setNextBlock(new Block(mainPanel, blockSpace, blockPositionX, blockPositionY));
			blockPositionX += 70;
			if ((blockPositionX + 70) >= 1919) {
				blockPositionY += 95;
				blockPositionX = 50;
			}
		} else {
			createMemoryBlockRecursive(b.getNextBlock(), blockSpace);
		}
	}
	
	public Block bestFit (ProcessScreen ps) {
		Block b = memoryBlocks;
		int internalFrag = 99999;
		Block freeBlock = null;
		if (memoryBlocks != null) {
			boolean foundFreeBlock = false;
			while(!foundFreeBlock&&(b!=null)) {
				if((b.getTotalSpace() - ps.getProcess().getSpaceRequired() == 0) && b.getBlockStatus()) {
					freeBlock = b;
					foundFreeBlock = true;
				} else {
					if (b.getBlockStatus() && (internalFrag > (b.getTotalSpace()-ps.getProcess().getSpaceRequired())) && (b.getTotalSpace() >= ps.getProcess().getSpaceRequired())) {
						internalFrag = b.getTotalSpace()-ps.getProcess().getSpaceRequired();
						freeBlock = b;						
					}
				}
				b = b.getNextBlock();
			}
			if(freeBlock != null) {
				return freeBlock;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	public int getMemoryUsed() {
		return this.memoryUsed;
	}
	
	public void freeOccupiedBlock(ProcessScreen ps) {
		Block b = memoryBlocks;
		while(b != null) {
			if(b.getProcessid() == ps.getProcess().getPid()) {
				b.freeBlock();
			}
			b = b.getNextBlock();
		}
	}
	
	public boolean checkForProcessBlock(ProcessScreen ps) {
		Block b = memoryBlocks;
		boolean processHasBlock = false;
		while(b != null) {
			if(b.getProcessid() == ps.getProcess().getPid()) {
				if(b.getUsedSpace() == ps.getProcess().getSpaceRequired()) {
					processHasBlock = true;
					System.out.println("entrou!!");
					return processHasBlock;
				}
			}
			b = b.getNextBlock();
		}
		return processHasBlock;
	}
	
}
