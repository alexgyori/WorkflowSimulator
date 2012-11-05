

import ro.upt.pcbe.workflowSimulator.Environment;
import ro.upt.pcbe.workflowSimulator.StateAction;

public class Work1 implements StateAction {

	@Override
	public void run(Environment env) {
		env.setVariable("kacsa1", "12");
		env.setVariable("kacsa2", "20");
		env.setVariable("kacsa3", "45");
		System.out.println("Worker 1 working...");
		
	}

}
