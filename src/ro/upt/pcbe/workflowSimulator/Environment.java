package ro.upt.pcbe.workflowSimulator;

import java.util.concurrent.ConcurrentHashMap;


public class Environment {
	
	private ConcurrentHashMap<String,String> variablesMap;
	
   public String getVariable(String variableName) {
	   return variablesMap.get(variableName);

   }
   
   public void setVariable(String variableName, String variableValue) {
	  
   }
   
   }
