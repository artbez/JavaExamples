
interface prevBox
{
	void write();
}

interface trt
{
	void getString();
}

class Box implements prevBox, trt
{
	int a, b, c;
		
	public Box(int a, int b, int c)
		{
			this.a = a;
			this.b = b;
			this.c = c;
			write();
		}
		
		public void write()
		{
			System.out.println((a - 100) + " " + (b - 100) + " " + (c - 100));
		}

		@Override
		public void getString() {
			// TODO Auto-generated method stub
			System.out.println("String");
		}
	}

	class MyBox extends Box
	{
		int d;
		int c;
		
		public MyBox(int a, int b, int c, int d) {
			super(a, b, c);
			this.d = d; 
			this.c = 100;
			write();
		}
		
		public void write()
		{
		//	super.write();
			System.out.println(a + " " + b + " " + c + " " + d);
			System.out.println(super.c);
		}
	}


public class Test {

	public static void main(String[] args)
	{
		System.out.print("hello");
		MyBox mb = new MyBox(1, 2, 3, 4);
		mb.getString();
//		mb.write();
	}
}

