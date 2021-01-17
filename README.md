# rdwr (“read/write”)

The **rdwr** library enables serial port communication
in the Java programming language.
It's unlike any other such library
in that it is implemented purely in Java
with no external dependencies:
no native components (JNI), no JNA mapping.
It does this by leveraging
the file-based abstractions for serial ports
present in all major operating systems:
`/dev/ttyXXX` on Unix-like systems,
`\\.\COMXXX` on Windows.

## How does this work?

TODO: Explanation of the file-based approach.

* Implements the serial port portion
  of [the Java Communications API 3.0][jca].
* Not technically [100% Pure Java][pure]
  because of the use of `Runtime.exec()`.

[jca]: https://www.oracle.com/java/technologies/java-communications-api.html
[pure]: https://www.oracle.com/technetwork/java/100percentpurejavacookbook-4-1-1-150165.pdf

## How do the other options work?

Most of the dominant serial communications libraries in Java –
[RXTX][rxtx] and its descendents (e.g., [NRJavaSerial][nrjs]),
[jSerialComm][jserialcomm],
[jSSC][jssc],
and [Serialio.com SerialPort][serialport],
to name a few –
take an honest and obvious approach to interfacing with hardware
by writing a [JNI][jni] wrapper around
the host operating system's serial communications API.
This approach means that those libraries can (in theory)
fully support any serial port features offered by the host platform.
The considerable drawback
is that they must also build and ship native libraries
implementing this wrapper
(`.so`, `.dll`, or `.dylib` files).
The process of compiling these libraries is usually arduous,
especially if you want to cross-compile
all of the libraries yourself
for all of the targetted platforms.
(This is largely because
all of the frontrunner libraries of this sort
are ancient and/or barely maintained,
and not because native libraries
are necessarily hard to build.
But having native libraries does raise
the minimum bar for entry.)
And cumulatively,
these native libraries can add hundreds of kilobytes
or even megabytes
to the size of the JAR.
For the application developer,
the complexity and maintenance burden of this approach
makes it difficult to track down bugs
and even harder to patch around them.

[PureJavaComm][purejavacomm] takes a different approach:
it still uses the the host operating system's serial communications API,
but it invokes it through [JNA][jna].
The small amount of native code present in its repository
is not packaged into the Java library,
but compiles into standalone executables
to sniff out the peculiarities
(struct field alignment and constant values)
of the [termios][termios] implementation
on several Unix-like platforms.
On paper, this approach carries a small performance overhead;
but serial port data rates are low enough
for this to be a non-issue.
Unfortunately, JNA itself is a rather large dependency,
weighing in at 4 MB (including platform API mappings).
That relatively minor caveat aside,
a JNA-based approach seems like it could be very effective.
It's a pity that the only current example
of serial port communication with JNA
is largely unmaintained.

[rxtx]: http://rxtx.qbang.org/
[nrjs]: https://github.com/NeuronRobotics/nrjavaserial
[jserialcomm]: https://github.com/Fazecast/jSerialComm
[jssc]: https://github.com/scream3r/java-simple-serial-connector
[serialport]: https://www.serialio.com/faqs/what-package-options-are-available-java-serialport
[jni]: https://en.wikipedia.org/wiki/Java_Native_Interface
[purejavacomm]: https://github.com/nyholku/purejavacomm
[jna]: https://github.com/java-native-access/jna
[termios]: https://en.wikibooks.org/wiki/Serial_Programming/termios

## How does this stack up?

TODO: Feature comparison table; caveats.

* rdwr supports only features
  which are exposed via the `stty`/`mode` commands.
