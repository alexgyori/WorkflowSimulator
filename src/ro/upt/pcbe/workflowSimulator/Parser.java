package ro.upt.pcbe.workflowSimulator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser implements IParser {

	@Override
	public List<WorkflowState> readStateGraph(String filename) throws Exception{

		int nr=0;
		BufferedReader br;
		String line;
		Map<String,WorkflowState> stateMap = new HashMap<String,WorkflowState>();
		WorkflowState s1,s2;


		br = new BufferedReader(new FileReader(filename));

		while ((line = br.readLine()) != null) {
			nr++;


			if(line.isEmpty())
				continue;

			String[] e =	line.split(",");
			if((e.length < 2)&&(!line.contains("=")))
				throw new Exception("Invalid state entry at line "+nr);

			if(e.length==1)//line is a state declaration
			{
				e = e[0].split("=");				
				if(stateMap.containsKey(e[0]))
				{
					s1 = stateMap.get(e[0]);
				}
				else
				{
					s1 = new WorkflowState(e[0],e[1]);
					stateMap.put(e[0], s1);
				}
			}
			else//line is a transition definition
			{
				if(stateMap.containsKey(e[0]))
				{
					s1 = stateMap.get(e[0]);
				}
				else
				{
					throw new Exception("State not declared before usage at line "+nr);
				}
				if(stateMap.containsKey(e[1]))
				{
					s2 = stateMap.get(e[1]);
				}
				else
				{
					throw new Exception("State not declared before usage at line "+nr);
				}
				
				Map<String,String> conditionMap = new HashMap<String,String>();
				for(int i=2;i<e.length;i++)
				{
					String[] e2 = e[i].split("=");
					if(e2.length != 2)
						throw new Exception("Condition error at line "+nr);
					conditionMap.put(e2[0],e2[1]);
				}
				
				s1.addConditionToState(new Condition(conditionMap), s2);
				s2.addPrecondition(s1);
			}

		}
		br.close();
		return new ArrayList<WorkflowState>(stateMap.values());
	}

}
