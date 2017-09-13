import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Properties;

import org.fogbowcloud.blowout.core.model.Task;

public class TaskSubmitterMain {

	public static void main(String[] args) throws Exception {
		final String BLOWOUT_CONF = args[0];
		final String TASK_OBJECT = args[1];

		Properties properties = new Properties();
		properties.load(new FileInputStream(BLOWOUT_CONF));

		TaskSubmitter taskSubmitter = new TaskSubmitter(properties);

		List<Task> tasks = getTaskList(TASK_OBJECT);

		taskSubmitter.submitTaskList(tasks, 1);

		Long startedTimestamp = System.currentTimeMillis();
		
		boolean allCompleted;
		
		do {
			
			Thread.sleep(10000);
			
			allCompleted = true;

			for(Task task : tasks) {

				System.out.println(taskSubmitter.getTaskState(task.getId()));

				if (!taskSubmitter.getTaskState(task.getId()).equals("Completed")) {
					allCompleted = false;
				}

			}

		} while (!allCompleted);

		System.out.println("Execution time: " + (System.currentTimeMillis() - startedTimestamp)/1000);
		
		try {
			taskSubmitter.stop();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			System.out.println("Has it stopped running? " + taskSubmitter.isStopped());
		}

	}

	@SuppressWarnings("unchecked")
	private static List<Task> getTaskList(final String TASK_OBJECT)
			throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(TASK_OBJECT));

		List<Task> tasks = (List<Task>) ois.readObject();

		ois.close();

		return tasks;
	}

	private static void saveTaskList(List<Task> tasks, final String TASK_OBJECT)
			throws FileNotFoundException, IOException {
		ObjectOutputStream oss = new ObjectOutputStream(new FileOutputStream(TASK_OBJECT));

		oss.writeObject(tasks);

		oss.close();
	}
}
