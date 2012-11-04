package ro.upt.pcbe.workflowSimulator;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public interface IParser {

	List<WorkflowState> readStateGraph(String filename,
			CountDownLatch startLatch) throws Exception;
	
	
}
