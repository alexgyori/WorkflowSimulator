package ro.upt.pcbe.workflowSimulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WorkflowState extends Thread {
	private final StateAction action;
	private final String stateName;

	private List<WorkflowState> preconditions;
	private Map<Condition,WorkflowState> conditionToState;

	private volatile Boolean isDone=false;

	public WorkflowState(String stateName, String classPath){
		this.stateName=stateName;
		this.action=null;

		//TODO: Load and construct an object of the class given and assign it to action

	}

	public void setPreconditions(List<WorkflowState> ls){
		this.preconditions = new ArrayList<WorkflowState>(ls);
	}

	public void setConditionToState(Map<Condition, WorkflowState> cond2state)
	{
		conditionToState=new HashMap<Condition, WorkflowState>(cond2state);
	}


	private boolean isDone(){
		return isDone;
	}

	private boolean isStartable(){
		for(WorkflowState s : preconditions)
		{
			if(!s.isDone())
				return false;
		}
		return true;
	}

	@Override
	public void run(){
		while(true){
			while(!this.isStartable())
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
			action.run(Environment.getInstance());
			isDone=true;
			for(Condition cond : this.conditionToState.keySet())
			{
				if(cond.evaluate(Environment.getInstance())){
					//TODO: Add call to state to signal true conditions
					this.conditionToState.get(cond).notify();
				}
			}
		}
	}

}
