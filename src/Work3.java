

import ro.upt.pcbe.workflowSimulator.Environment;
import ro.upt.pcbe.workflowSimulator.StateAction;

public class Work3 implements StateAction {

	@Override
	public void run(Environment env) {
		env.setVariable("kacsa3", "40");
		System.out.println("Worker 3 working...");
		
	}

}
