import java.math.BigInteger;
import java.util.Arrays;

public class OldRSABreaker {
	private int n;
	private BigInteger e;
	private int encryptedMessage;

	public void setN(int n) {
		this.n = n;
	}

	public void setE(BigInteger e) {
		this.e = e;
	}

	public BigInteger getPhi() {
		Pair pair = sieve();
		return new BigInteger(((pair.getQ() - 1) * (pair.getP() - 1)) + "");
	}

	public void setEncryptedMessage(int encryptedMessage) {
		this.encryptedMessage = encryptedMessage;
	}

	public BigInteger getD() {
		return e.modInverse(getPhi());
	}

	public BigInteger decryptMessage() {
		BigInteger x = BigInteger.valueOf(encryptedMessage);
		return x.modPow(getD(), BigInteger.valueOf(n));
	}

	private boolean isPrime(int number) {
		if (number == 2 || number == 3) {
			return true;
		}
		if (number % 2 == 0) {
			return false;
		}
		int sqrt = (int) Math.sqrt(number) + 1;
		for (int i = 3; i < sqrt; i += 2) {
			if (number % i == 0) {
				return false;
			}
		}
		return true;
	}

	private Pair sieve() {
		boolean prime[] = new boolean[n + 1];
		Arrays.fill(prime, true);
		for (int p = 2; p * p <= n; p++) {
			// If prime[p] is not changed, then it is a prime
			if (prime[p] == true) {
				// Update all multiples of p
				for (int i = p * 2; i <= n; i += p)
					prime[i] = false;
			}
		}
		int q;
		for (int i = 2; i <= n; i++) {
			if (prime[i] == true) {
				q = n / i;
				if (isPrime(q) && q * i == n) {
					return new Pair(i, q);
				}
			}
		}
		return null;
	}
}
