class MyThread extends Thread {
	public void run() {
		for (int i = 0; i < 5; ++i)
		{
			System.out.println("World");
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

public class ThreadExample {
	public static void main(String[] args)
	{
		MyThread myThread = new MyThread();
		myThread.setDaemon(true);
		myThread.start();
		for (int i = 0; i < 5; ++i)
		{
			System.out.println("Hello");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
}
