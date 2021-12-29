import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TestSearch2 {

	private static int[] readTests(String[] args, int numTests) {
		int[] tests = new int[numTests];
		int value;

		if (args.length == 0)
			for (int i = 0; i < numTests; ++i)
				tests[i] = i + 1;
		else
			for (int i = 0; i < args.length; ++i) {
				value = Integer.parseInt(args[i]);
				if (value >= 1 && value <= numTests)
					tests[value - 1] = value;
				else
					System.out.println("ERROR: Test " + value + "does not exist");
			}
		return tests;
	}

	public static void main(String[] args) throws Exception {
		boolean testPassed;
		int[] tests = readTests(args, 7);
		
		//If you need to run a specific test, set them manually in here and uncomment these lines to only run specific tests.
		//For example, this only runs test 5. To run tests 1,2 it would be {1,2,0,0,0,0,0};
		//int[] tests2 = {0,0,0,0,5,0,0};
		//tests = tests2;
		
		String[] param = { "xmap1.txt", "5" }; // Name starts with x to avoid displaying map
		String correct;

		System.out.println("\nTESTS FOR CLASS StartSearch");
		System.out.println("==============================\n");

		
		// This test checks to see whether the algorithm can handle a larger map.
		
		param[0] = "xmap5.txt";
		param[1] = "250";

		if (tests[4] == 5) {

			StartSearch.main(param);
			correct = "push0push37push49push63push76push83push93push104push115pop115pop104pop93pop83pop76pop63pop49pop37pop0push0push48push62push75push82push92push103pop103pop92pop82pop75pop62pop48pop0push0push36push35push34push33pop33pop34pop35pop36pop0push0push27push16push7push6push5push4push3push2push1pop1pop2pop3pop4pop5pop6pop7pop16pop27pop0";
			if (ArrayStack.sequence.equals(correct)) {
				System.out.println("SequenceL: " + ArrayStack.sequence.length() + ", CorrectL: " + correct.length());

				testPassed = true;

			} // end if

			else {

				System.out.println("SequenceL: " + ArrayStack.sequence.length() + ", CorrectL: " + correct.length());
				System.out.println(ArrayStack.sequence);
				testPassed = false;

			} // end else

			if (testPassed) {
				
				System.out.println("==============================\n");
				System.out.println("TEST 5 PASSED");
				System.out.println("==============================\n");
				
			} //end if

			else {
				
				System.out.println("==============================\n");
				System.out.println("TEST 5 FAILED");
				System.out.println("==============================\n");
				
			} //end else
			
		} // end if
		
		

		System.exit(0);

	} // end main

} // end TestSearch (class)