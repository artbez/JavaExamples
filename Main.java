package t1;

public class Main {
	
	static double a = 0;
	static double b = 10;
	static double eps = 0.000001;
	static double res = 1.896651;
	
	public static double exec(double x) {
		return Math.log10(x) - 1 / (x * x);
	}
	public static double execF1(double x) {
		return ((x * x / Math.log(10)) + 2) / (x * x * x); 
	}
	
	public static void narrow(double a, double b, int time) {
		Main.a = a;
		Main.b = b;
		if (time == 0)
		{
			System.out.println("Итоговый интервал - [" + a + "," + b + "]");
			return;
		}
		double c = (a + b) / 2;
		double f1 = exec(a);
		double f2 = exec(b);
		double f3 = exec(c);
		if (f1 * f3 < 0)
			narrow(a, c, time - 1);
		else if (f2 * f3 < 0)
			narrow(c, b, time - 1);
	}
	
	public static int newton(double ll, double eps, int kmax, double res) {
		double current = 0;
		int i = 0;
		for (i = 0; i < kmax; ++i) {
			current = ll - exec(ll) / execF1(ll);
			System.out.println("Шаг " + (i + 1) + " точка " + current);
			System.out.println("Разность с точным: " + Math.abs(res - current));
			System.out.println("Разность двух соседних: " + Math.abs(current - ll) + "\n");
			if (Math.abs(current - ll) < eps) {
				break;
			}
			ll = current;
		}
		System.out.println("Корень " + res);
	//	System.out.println("Разность с точным: " + Math.abs(res - current));
		return i + 1; 
	}
	
	public static int secant(double x0, double x1, double eps, int kmax, double res) {
		double current = 0;
		int i = 0;
		for (i = 0; i < kmax; ++i) {
			current = x1 - exec(x1) * (x1 - x0) / (exec(x1) - exec(x0));
			System.out.println("Шаг " + (i + 1) + " точка " + current);
			System.out.println("Разность с точным: " + Math.abs(res - current));
			System.out.println("Разность двух соседних: " + Math.abs(current - x1) + "\n");
			if (Math.abs(current - x1) < eps) {
				break;
			}
			x0 = x1;
			x1 = current;
		}
		System.out.println("Корень " + res);
	//	System.out.println("Разность с точным: " + Math.abs(res - current));
		return i + 1;
	}
	public static void main(String args[]) {
		System.out.println("Исходный интервал - [" + a + "," + b + "]");
		narrow(a, b, 5);
		double x0 = (Main.a + Main.b) / 2;
		System.out.println("Метод Ньютона");
		int res1 = newton(x0, eps, 10, res);
		System.out.println("Конец метода Ньютона");
		System.out.println("Метод секущих");
		int res2 = secant(x0, Main.b, eps, 10, res);
		System.out.println("Конец метода секущих");
		System.out.println("Метод Ньютона потребовал " + res1 
				+ " шага, а метод секущих " + res2 + " шагов");
	}
}
