

import ro.upt.pcbe.workflowSimulator.Environment;
import ro.upt.pcbe.workflowSimulator.StateAction;

public class Work1 implements StateAction {

	@Override
	public void run(Environment env) {
		
		System.out.println("Worker 1 working...");
		
	}

}
