Instructions

In order to run a JAR file, you must first install the Java Development Kit. 
Linux: https://www.oracle.com/java/technologies/downloads/#jdk18-linux
Mac: https://www.oracle.com/java/technologies/downloads/#jdk18-mac
Windows: https://www.oracle.com/java/technologies/downloads/#jdk18-windows

Follow the appropriate link and install the appropriate development kit to continue.

1. To use the program on Windows, first we open a command prompt. You can do this by searching 'command' in the windows search bar.
2. Then we have to change the current folder we are in in command prompt to the folder containing the TheatreRoyal.jar file.
3. To do this, open a File Explorer window and navigate to the folder containing TheatreRoyal.jar
4. Select the path bar in the File Explorer window. It contains information about the location of the current folder. Copy this using Control + C.
5. Go to command prompt. Type:
	cd /d ctrl+v
6. Then press enter.
7. This will take you to the folder where the TheatreRoyal.jar file is located. 
8. In command prompt, type (or copy paste) the following:
	java -jar TheatreRoyal.jar
9. You have now opened the TheatreRoyal application. Congratulations!

On a non windows device, you will want to do the same thing - Open a command shell, navigate to the folder containing TheatreRoyal.jar, and type java -jar TheatreRoyal.jar