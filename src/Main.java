
import java.util.List;

import ro.upt.pcbe.workflowSimulator.Environment;
import ro.upt.pcbe.workflowSimulator.IParser;
import ro.upt.pcbe.workflowSimulator.Parser;
import ro.upt.pcbe.workflowSimulator.WorkflowState;

public class Main
{
	public static void main(String[] args)
	{
		System.out.println("Runnning....");
		
		IParser parser = new Parser();
		try {
			List<WorkflowState>list = parser.readStateGraph("states.states");
			//System.out.println(list);
			
			Environment e = Environment.getInstance();
			e.loadEnv("env.env");
			//System.out.println(e);
			
			
			for(WorkflowState w : list)
				w.start();
			System.out.println("All workers armed!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Stopped.....");
	}
}