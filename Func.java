import java.util.Arrays;
import java.io.*;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@FunctionalInterface
interface WorkAr {
	void doWork(int a, int b);
}

public class Func {
	public static void execute(WorkAr worker) {
		worker.doWork(4, 3);
	}
	
	public static void main(String[] args)
	{
		execute(new WorkAr() {
			public void doWork(int a, int b)
			{
				System.out.println("doWork");
			}
		});
		
		execute((s,b) -> System.out.println("Example"));
		
		new Thread(new Runnable() {
			public void run()
			{
				System.out.print("run");
			}
		}).start();
		
		new Thread(() -> System.out.print("Sel")).start();
		
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
		list.forEach(n -> System.out.print(n + " "));
		int result = list.stream().mapToInt(e -> e).sum();
		List<String> myList = Arrays.asList( "a1", "b1", "c1", "c2");
		myList.stream().filter(f -> f.startsWith("c")).map(s -> s.toUpperCase())
			.sorted().forEach(s -> System.out.println(s));
		IntStream.range(1, 4).forEach(System.out::println);
		Arrays.stream(new int[] {1, 2, 3}).map(s -> s * 2).average().ifPresent(System.out::println);
		Stream.of("a1", "a2", "a3").filter(s -> {System.out.println("Filter: " + s); return true;})
			.forEach(s -> System.out.println("forEach: " + s));
		int a = Stream.of(1, 2, 3, 4, 5).reduce(0, (p1, p2) -> p1 + p2);
		System.out.println(a);
	}
}