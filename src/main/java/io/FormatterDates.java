import java.util.Formatter;
import java.util.Date;
import java.util.Calendar;

/** Demonstrate some usage patterns and format-code examples 
 * of the Formatter class (new in J2SE 1.5).
 */
public class FormatterDemo {
	public static void main(String[] args) {

		// The arguments to all these format methods consist of
		// a format code String and 1 or more arguments.
		// Each format code consists of the following:
		// % - code lead-in
		// N$ - which parameter number (1-based) after the code
		// N - field width
		// L - format letter (d: decimal(int); f: float; s: general; many more)
		// For the full(!) story, see javadoc for java.util.Formatter.

		Formatter fmtr = new Formatter();
		System.out.println(fmtr.format("%1$4d-%2$02d-%3$2d", 2004, 6, 28));

		// This is short for the above, except that you
		// need explicitly to add the newline delimiter
		System.out.format("%1$4d-%2$02d-%3$02d%n", 2004, 6, 28);

		// So is this
		System.out.printf("%1$4d-%2$02d-%3$2d%n", 2004, 6, 28);

		// Format fields from a Date object: multiple fields from "1$"
		// (hard-coded formatting for Date not advisable; see I18N chapter)
		Date today = Calendar.getInstance().getTime();
		System.out.printf("%1$tB %1$td, %1$tY%n", today);	// e.g., July 4, 2004

		// Format floating point numbers
		System.out.printf("%1$7.2f%n", Math.PI);
	}
}
