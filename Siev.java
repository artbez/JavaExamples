
import java.util.function.IntPredicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class FPair {
	int p;
	IntPredicate filter;
	FPair(int i, IntPredicate f) {p=i; filter = f;}
}


public class Siev {
	
	int currentPrime;
	IntSet filter;
	
	Siev () {
	  currentPrime = 2;
	  filter = x ->x%currentPrime!=0;	
	}
	
	Siev (int p, IntSet f) {
		currentPrime = p;
		filter = f.conj ( x ->x%currentPrime!=0);
	}
	public int head () {
		return currentPrime;
	}
	
	public Siev tail() {
		int next = IntStream.range(currentPrime, Integer.MAX_VALUE).filter(filter::contains).findFirst().getAsInt();
		//for (next = currentPrime+1; !filter.contains(next);next++);
		return new Siev (next, filter);
	}

	public static void main(String[] args) {
		Siev s = new Siev();
		System.out.println(s.tail().tail().tail().head());
	}

}

@FunctionalInterface
interface IntSet {
    boolean contains (int i);

	default IntSet union (final IntSet o) {
		return x-> o.contains(x) || contains(x);
	}
	
	default IntSet conj (final IntSet o) {
		return x-> o.contains(x) && contains(x);
	}
	
}
