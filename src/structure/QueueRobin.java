package structure;

public class QueueRobin {
	
	private structure.Process head = null;
	
	public QueueRobin() {
		
	}

	public Process getHead() {
		return head;
	}

	public void addProcess(Process process) {
		if(head==null) {
			head = process;
		}else {
			if(head.getTotalTime() > process.getTotalTime()) {
				Process aux = head;
				head = process;
				process.setNextProcess(aux);
			}else {
				insertSort(head, process);
			}
		}
		System.out.println("Processo " + process.getPid() + " adicionado!");
	}
	
	public void insertSort(Process next, Process insert) {
		if(next.getNextProcess() == null) {
			next.setNextProcess(insert);
		}else {
			if(next.getNextProcess().getTotalTime() > insert.getTotalTime()) {
				Process aux = next.getNextProcess();
				next.setNextProcess(insert);
				insert.setNextProcess(aux);
			}else {
				insertSort(next.getNextProcess(), insert);
			}
		}
	}
	
	public void listQueue() {
		structure.Process p = head;
		while(p!=null) {
			System.out.print(p.getPid() + "(" + p.getTotalTime() + ")" + "[" + p.getPriority() + "]" + "->");
			p = p.getNextProcess();
		}
	}

	public structure.Process StaggerProcess() {
		Process aux = head;
		head = head.getNextProcess();
		aux.setNextProcess(null);
		return aux;
	}
	
}
