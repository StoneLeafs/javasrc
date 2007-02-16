package threads;

/*
 * Copyright (c) Ian F. Darwin, http://www.darwinsys.com/, 1996-2007.
 * All rights reserved. Software written by Ian F. Darwin and others.
 * $Id$
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *        This product includes software developed by Ian F. Darwin.
 * 4. Neither the name of the author nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS''
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * Java, the Duke mascot, and all variants of Sun's Java "steaming coffee
 * cup" logo are trademarks of Sun Microsystems. Sun's, and James Gosling's,
 * pioneering role in inventing and promulgating (and standardizing) the Java
 * language and environment is gratefully acknowledged.
 *
 * The pioneering role of Dennis Ritchie and Bjarne Stroustrup, of AT&T, for
 * inventing predecessor languages C and C++ is also gratefully acknowledged.
 */

import java.io.IOException;

/** Stop a thread by killing an executed program.
 */
public class StopExec extends Thread {

	private static final int MAX_TIMEOUT = 10;
	private Process proc;
	private int timeToRunProcess;

	public StopExec(int timeToRunProcess) {
		System.out.printf("Creating a %d-second process%n", timeToRunProcess);
		this.timeToRunProcess = timeToRunProcess;
	}

	/** Thread.run() method: run the process, which will take "timeToRunProcess" seconds.
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		try {
			System.out.println("StopExec.run(): starting process");
			// Use of sleep with a number is to simulate different run times of a process
			proc = Runtime.getRuntime().exec("sleep " + timeToRunProcess);
			proc.waitFor();
			System.out.println("StopExec.run(): process terminated, exit status " + proc.waitFor());
		} catch (IOException ex) {
			System.out.println("StopExec terminating: " + ex);
		} catch (InterruptedException e) {
			System.out.println("StopExec.run(): Was interrupted");
		}
	}

	/**
	 * Kill the process, if it was taking too long.
	 */
	public void shutDown() {
		if (proc != null) {
			System.out.println("StopExec.shutDown(): destroying process " + proc);
			proc.destroy();
		}
		System.out.println("StopExec.shutDown() completed");
	}

	/**
	 * Create a series of instances with runtimes less than, equal to, and greater than, MAX_TIMEOUT.
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		for (int timeToRunProcess : new int[]{5,10,15}) {
			StopExec t = new StopExec(timeToRunProcess);
			t.start();
			Thread.sleep(1000*MAX_TIMEOUT);
			if (t.isAlive()) {
				System.err.println("HUNG PROCESS, killing it.");
				t.shutDown();
			}
		}
	}
}