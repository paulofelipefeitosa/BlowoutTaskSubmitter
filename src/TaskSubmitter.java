import java.util.List;
import java.util.Properties;

import org.fogbowcloud.blowout.core.BlowoutController;
import org.fogbowcloud.blowout.core.exception.BlowoutException;
import org.fogbowcloud.blowout.core.model.Task;

public class TaskSubmitter {

	private BlowoutController blowout;

	public TaskSubmitter(Properties properties) throws Exception {
		this.blowout = new BlowoutController(properties);
		
		this.blowout.start(false);
	}

	public void submitTask(Task task, int times) throws BlowoutException {
		for (int i = 0; i < times; i++)
			this.blowout.addTask(task);
	}

	public void submitTaskList(List<Task> tasks, int times) throws BlowoutException {
		for (int i = 0; i < times; i++)
			this.blowout.addTaskList(tasks);
	}
	
	public void stop() throws Exception
	{
		this.blowout.stop();
	}
}
