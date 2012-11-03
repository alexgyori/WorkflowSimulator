package ro.upt.pcbe.workflowSimulator;

import java.util.HashMap;
import java.util.Map;

//Immutable
public class Condition {

	private Map<String,String> conditions;
	public Condition(Map<String,String> map){
		this.conditions=new HashMap<String, String>(map);
	}
	
	public boolean evaluate(Environment env)
	{
		for(String str : conditions.keySet())
		{
			if(!conditions.get(str).equals(env.getVariable(str)))
				return false;
		}
		
		return true;
	}
	
	public String toString()
	{
		String s ="Condition: ";
		for(String varName : conditions.keySet())
		{
			s+=varName+"="+conditions.get(varName)+" ";
		}
		return s;
	}
}
