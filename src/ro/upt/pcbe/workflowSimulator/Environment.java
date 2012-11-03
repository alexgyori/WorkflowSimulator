package ro.upt.pcbe.workflowSimulator;


import java.io.BufferedReader;
import java.io.FileReader;
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


	public void loadEnv(String filename) throws Exception
	{
		BufferedReader br;
		
		br = new BufferedReader(new FileReader(filename));

		variablesMap.clear();
		
		String line;
		while ((line = br.readLine()) != null) {
			
		String[] e =	line.split("=");
		if(e.length != 2)
			throw new Exception("no environment entry found!");
		
			setVariable(e[0], e[1]);
		}
		br.close();
	}
	
	public String toString()
	{
		String s="";
		for(String key : variablesMap.keySet())
		{
			s+=key+"="+variablesMap.get(key)+"\n";
		}
		return s;
	}
}
