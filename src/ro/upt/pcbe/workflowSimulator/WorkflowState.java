package ro.upt.pcbe.workflowSimulator;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;


public class WorkflowState extends Thread {
	private final StateAction action;
	private final String stateName;

	private List<WorkflowState> preconditions;
	private Map<Condition,WorkflowState> conditionToState;

	private volatile Boolean isDone=false;
	private CountDownLatch latch;

	public WorkflowState(String stateName, String classPath, CountDownLatch startLatch) throws InstantiationException, IllegalAccessException{
		this.stateName=stateName;
		this.preconditions = new ArrayList<WorkflowState>();
		this.conditionToState = new HashMap<Condition, WorkflowState>();
		this.latch = startLatch;

		Class clas = null;

		//Load and construct an object of the class given and assign it to action
		try{
			String newClassPath = System.getProperty("user.dir")+System.getProperty("file.separator")+"bin"+System.getProperty("file.separator")+classPath;

			File file = new File(newClassPath);
			URLClassLoader clazzLoader = URLClassLoader.newInstance(new URL[]{file.toURI().toURL()});
			String className = classPath.substring(0, classPath.indexOf("."));
			clas = clazzLoader.loadClass(className);
			System.out.println("Class "+className+" loaded.");

		}
		catch(Exception e){
			System.out.println("Loading class exception!");
			e.printStackTrace();
		}


		this.action = (StateAction) clas.newInstance();

	}

	public void setPreconditions(List<WorkflowState> ls){
		this.preconditions = new ArrayList<WorkflowState>(ls);
	}

	public void addPrecondition(WorkflowState s){
		this.preconditions.add(s);
	}

	public void setConditionsToStates(Map<Condition, WorkflowState> cond2state)
	{
		conditionToState=new HashMap<Condition, WorkflowState>(cond2state);
	}

	public void addConditionToState(Condition c, WorkflowState s){
		this.conditionToState.put(c,s);
	}

	private boolean isDone(WorkflowState wfState){
		return isDone && preconditionsAreMet(wfState);
	}

	private boolean preconditionsAreMet(WorkflowState wfState) {
		// TODO Auto-generated method stub
		for(Entry<Condition, WorkflowState> entry :this.conditionToState.entrySet())
		{
			if(entry.getValue().equals(wfState))
				if(entry.getKey().evaluate(Environment.getInstance()))
					return true;
		}
		return false;
	}

	private boolean isStartable(){

		for(WorkflowState s : preconditions)
		{
			if(!s.isDone(this))
				return false;
		}
		return true;
	}

	@Override
	public synchronized void run(){
		//while(true){
		/*try {
			this.latch.await();//wait until main thread says it's ok to go
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}*/


		while(!this.isStartable())
			try {
				System.out.println("["+stateName+"] waiting..");
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		System.out.println("["+stateName+"] all starting conditions met..");
		action.run(Environment.getInstance());
		isDone=true;
		System.out.println(Environment.getInstance().toString());
		for(Condition cond : this.conditionToState.keySet())
		{
			//if(cond.evaluate(Environment.getInstance())){
			//TODO: Add call to state to signal true conditions
			synchronized(this.conditionToState.get(cond))
			{
				this.conditionToState.get(cond).notifyAll();
			}
			//}
		}
		System.out.println("["+stateName+"] worker retiring..");
		//}	
	}



	@Override
	public String toString() {
		String s="State:"+stateName+"\n\tPreconditions:";
		if(preconditions != null)
			for(WorkflowState w : preconditions)
			{
				s+=w.stateName+", ";
			}
		s+="\n\tTransitions:\n";
		for(Condition c: conditionToState.keySet())
		{
			s+="\t\tif "+c+" goto "+conditionToState.get(c).stateName+"\n";
		}
		return s;		
	}



}
