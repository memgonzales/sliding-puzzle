# 8-Puzzle (Sliding Puzzle)
***--- THIS IS A WORK IN PROGRESS ---***

**8-Puzzle** is a **sliding puzzle game** that features a walkthrough of the optimal solution and allows users to customize the tiles using photos selected from their Gallery. In finding the optimal path from the current puzzle state to the goal state, the system employs the informed search algorithm A* and uses Manhattan distance as an admissible and consistent heuristic to estimate the cost. 

*Inspired by the process management exercise in our Mobile Development class, I started creating this app during our university break, after finishing my second year of undergraduate studies. As one of my first personal projects, working on this served as an opportunity to synthesize concepts and practices that I have learned from our second-year courses, such as:*
- *Exploration of language features (from Advanced Programming and Theory of Programming Languages)*
- *Program optimization (from Algorithms and Complexity)*
- *Artificial intelligence and search algorithms (from Introduction to Intelligent Systems)*
- *Quality assurance and unit testing (from Introduction to Software Engineering)*

The **source code documentation** can be accessed through the following website: [https://memgonzales.github.io/sliding-puzzle/](https://memgonzales.github.io/sliding-puzzle/).

## Project Structure
The project consists of the following folders:

Folder | Description
-- | --
<a href = "https://github.com/memgonzales/sliding-puzzle/tree/master/.idea">`.idea`</a> | Contains files used by Android Studio to load project-specific configurations
<a href = "https://github.com/memgonzales/sliding-puzzle/tree/master/app">`app`</a> | Contains all the source code and resource files, the markdown file for the app- and package-level documentation, and files defining the module-specific dependencies, project-specific ProGuard rules, and developer credentials related to Google services
<a href = "https://github.com/memgonzales/sliding-puzzle/tree/master/docs">`docs`</a> | Contains the HTML documentation of the project, generated from KDoc comments via Dokka
<a href = "https://github.com/memgonzales/sliding-puzzle/tree/master/gradle/wrapper">`gradle`</a> | Contains files used by Gradle to run build automation tasks

Several Gradle-related files are also included in its root directory.

## Running the Application
***The minimum SDK supported is Android Lollipop (API Level 21), and the target SDK is Android 11 (API Level 30).***

1. Before running the application locally, the following software are recommended (albeit not required) to be installed:

   | Software | Description | License |
   | --- | --- | --- |
   | <a href = "https://git-scm.com/downloads">git</a> | Distributed version control system | GNU General Public License v2.0 |
   | <a href = "https://developer.android.com/studio">Android Studio</a> | Official integrated development environment (IDE) for Android development | Apache License 2.0
   
2. Create a copy of this repository:
   - If git is installed, type the following command on the terminal:
   
     ```
     git clone https://github.com/memgonzales/sliding-puzzle
     ```
      
   - If git is not installed, click the green <code>Code</code> button near the top right of the repository and choose <code>Download ZIP</code>. Once the zipped folder has been downloaded, extract its contents.

3. Run the app using Android Studio (or any IDE that supports Android development). Alternatively, Android also provides a <a href = "https://developer.android.com/studio/build/building-cmdline">guide</a> on how to build the app from the command line.

## Testing the Application
The project includes unit tests, which are located inside the [`app/src/test/java`](https://github.com/memgonzales/sliding-puzzle/tree/master/app/src/test/java/com/gonzales/mark/n_puzzle) folder. These are JUnit tests that run on the machine's local Java Virtual Machine.

## Dependencies

## Built Using

## Product Backlog
- Create launcher icon
- Add splash screen
- Collect 8-puzzle trivia
- Continue documentation (including KDoc)

## Author
- <b>Mark Edward M. Gonzales</b> <br/>
  mark_gonzales@dlsu.edu.ph <br/>
  gonzales.markedward@gmail.com <br/>
  
Assets (such as images and XML resource files) are properties of their respective owners. Attribution is found in the file <a href = "https://github.com/memgonzales/sliding-puzzle/blob/master/credits.txt">`credits.txt`</a>. Technical references used in the implementation of algorithms are cited in the [code documentation](https://memgonzales.github.io/sliding-puzzle/) of the pertinent methods.
