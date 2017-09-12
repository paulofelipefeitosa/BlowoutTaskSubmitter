import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

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

		System.out.println(taskSubmitter.getTaskState(tasks.get(0).getId()));

		Scanner sc = new Scanner(System.in);

		Long ts1 = System.currentTimeMillis();
		int cur = 0, last = 0;
		while (!taskSubmitter.getTaskState(tasks.get(0).getId()).equals("Completed")) {
			cur = (int) (System.currentTimeMillis() - ts1) / 1000;
			if (cur > last) {
				System.out.println(taskSubmitter.getTaskState(tasks.get(0).getId()));
				// System.out.println((System.currentTimeMillis() - ts1)/1000);
				last = cur + 10;
			}
			if (sc.hasNext())
				break;
		}

		sc.close();

		System.out.println("SAiu");

		taskSubmitter.stop();
	}

	@SuppressWarnings("unchecked")
	private static List<Task> getTaskList(final String TASK_OBJECT)
			throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(TASK_OBJECT));

		List<Task> tasks = (List<Task>) ois.readObject();

		ois.close();

		return tasks;
	}
}
