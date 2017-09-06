import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Properties;

import org.fogbowcloud.blowout.core.exception.BlowoutException;
import org.fogbowcloud.blowout.core.model.Task;

public class TaskSubmitterMain { 
	
	public static void main(String[] args) throws FileNotFoundException, IOException, BlowoutException, ClassNotFoundException
	{	
		final String BLOWOUT_CONF = args[0];
		final String TASK_OBJECT = args[1];
		
		Properties properties = new Properties();
		properties.load(new FileInputStream(BLOWOUT_CONF));
		
		TaskSubmitter taskSubmitter = new TaskSubmitter(properties);
		
		List<Task>tasks = (List<Task>) (new ObjectInputStream(new FileInputStream(TASK_OBJECT))).readObject();
		
		
	}
}
