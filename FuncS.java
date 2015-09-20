interface WorkInterface {
	
	public void doWork();
}

public class FuncS {
	public static void execute(WorkInterface worker)
	{
		worker.doWork();
	}
	public static void main(String[] args)
	{
		execute(new WorkInterface() {
			public void doWork()
			{
				System.out.println("1");
			}
		});
		
		execute( () -> System.out.println("2"));
	}
}