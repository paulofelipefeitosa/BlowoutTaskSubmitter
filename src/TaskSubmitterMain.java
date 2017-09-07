import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Properties;

import org.fogbowcloud.blowout.core.model.Task;

public class TaskSubmitterMain { 
	
	public static void main(String[] args) throws Exception
	{	
		final String BLOWOUT_CONF = args[0];
		final String TASK_OBJECT = args[1];
		
		Properties properties = new Properties();
		properties.load(new FileInputStream(BLOWOUT_CONF));
		
		System.out.println(properties.getProperty("auth_token_prop_ldap_base"));
		
		TaskSubmitter taskSubmitter = new TaskSubmitter(properties);
		
		List<Task>tasks = getTaskList(TASK_OBJECT);
		
		taskSubmitter.submitTaskList(tasks, 1);
		
		taskSubmitter.stop();
	}
	
	@SuppressWarnings("unchecked")
	private static List<Task> getTaskList(final String TASK_OBJECT) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(TASK_OBJECT));
		
		List<Task>tasks = (List<Task>) ois.readObject();
		
		ois.close();
		
		return tasks;
	}
}
