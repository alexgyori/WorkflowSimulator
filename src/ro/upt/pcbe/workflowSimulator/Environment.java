package ro.upt.pcbe.workflowSimulator;


import java.util.AbstractMap;
import java.util.concurrent.ConcurrentHashMap;


public class Environment {

	private AbstractMap<String,String> variablesMap;

	private static volatile Environment env;

	private Environment(){
		variablesMap=new ConcurrentHashMap<String, String>();
	}

	public static Environment getInstance()
	{
		if(env == null)
		{
			synchronized(Environment.class)
			{
				if(env == null)
					env=new Environment();
			}
		}
		return env;
	}


	public String getVariable(String variableName) {
		return variablesMap.get(variableName);

	}

	public void setVariable(String variableName, String variableValue) {
		variablesMap.put(variableName, variableValue);
	}

}
