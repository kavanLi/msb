package class01;

public class Code02_SumOfFactorial {

	/**
	 * 给定一个参数N，
	 * 返回：  1! + 2! + 3! + 4! + … + N!   的结果
	 * @param N
	 * @return
	 */

	public static long f1(int N) {
		long ans = 0;
		for (int i = 1; i <= N; i++) {
			ans += factorial(i);
		}
		return ans;
	}

	public static long factorial(int N) {
		long ans = 1;
		for (int i = 1; i <= N; i++) {
			ans *= i;
		}
		return ans;
	}

	public static long f2(int N) {
		long ans = 0;
		long cur = 1;
		for (int i = 1; i <= N; i++) {
			cur = cur * i;
			ans += cur;
		}
		return ans;
	}


	public static long f2Recursive(int N) {
		if (N == 0) {
			return 0;
		}
		if (N == 1) {
			return 1;
		}
		return factorial1(N) + f2Recursive(N - 1);
	}

	public static long factorial1(int n) {
		if (n == 0) {
			return 1;
		}
		return n * factorial1(n - 1);
	}

	public static long f2Dynamic(int N) {
		if (N == 0) {
			return 0;
		}

		long[] dp = new long[N + 1];
		dp[0] = 0;
		dp[1] = 1;

		for (int i = 2; i <= N; i++) {
			dp[i] = dp[i - 1] * i + dp[i - 1];
		}

		return dp[N];
	}

	public static long f3(int N) {
		if (1 == N) {
			return 1;
		}
		long cur = f3(N - 1);
		return N * (N - 1);
	}
	public static void main(String[] args) {
		int N = 3;
		System.out.println(f1(N));
		System.out.println(f2(N));
		System.out.println(f3(N));
		System.out.println(f2Recursive(N));
		System.out.println(f2Dynamic(N));
	}

}
