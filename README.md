[![progress-banner](https://backend.codecrafters.io/progress/shell/6b69d862-a5b9-4c60-9159-2a8ba8a81815)](https://app.codecrafters.io/users/codecrafters-bot?r=2qF)

This repository contains my solution for the ["Build Your Own Shell" Challenge](https://app.codecrafters.io/courses/shell/overview).

# Overview

In this challenge, I built a POSIX-compliant shell capable of interpreting shell commands, running external programs, and handling builtin commands like cd, pwd, echo, and more. Along the way, I learned about shell command parsing, REPLs, and the implementation of builtin commands.

### Core Shell Implementation

The base stages required building the core of the shell to support basic commands and functionality. <br>

### Features Implemented

I have successfully completed the implementation of the core functionality of the shell across multiple stages. This includes:

1. **Command Parsing**:
   - Parses and interprets user input effectively, supporting arguments and options.
   
2. **Builtin Commands**:
   - `exit`: Exit the shell with a specified status code.
   - `echo`: Print text to the terminal.
   - `type`: Identify commands as built-in or external.

3. **External Program Execution**:
   - Runs programs located in directories specified by the `PATH` environment variable.

<br>

## Usage Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/Raghavv-gupta/codecrafters-shell-java.git 
2. Navigate to the project directory:
   ```bash
   cd codecrafters-shell-java
3. Run the shell program:
   ```bash
   ./your_program.sh
4. Test with commands:
   - Try `cd`, `echo Hello`, or `type ls`

<br><hr>
 You can view the entry point of the implementation in `src/main/java/Main.java`.