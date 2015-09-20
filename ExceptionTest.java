class MyException extends Exception
{
	int number = 111;
	public String toString()
	{
		return ("Error №" + number);
	}
}

class MyOtherException extends Exception
{
}

public class ExceptionTest {
	static void example(int a) throws MyException, MyOtherException {
		if (a > 10)
			throw new MyException();
		if (a > 5)
			throw new MyOtherException();
		System.out.println("exit");
	}
	
	public static void main(String[] args)
	{
		try {
			example(4);
			example(20);
			example(6);
		} catch (MyException e)
		{
			System.out.println(e.toString());
		} catch (MyOtherException e) {
			System.out.println("Error 2");
		}
		finally {
			System.out.println("End");
		}
	}
}
