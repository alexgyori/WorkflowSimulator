package ro.upt.pcbe.workflowSimulator;

import java.util.List;

public interface IParser {

	public List<WorkflowState> readStateGraph(String filename);
	
	public Environment readEnv(String filename);
	
}
