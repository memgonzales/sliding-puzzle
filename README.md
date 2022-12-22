# 8-Puzzle (Sliding Puzzle)
![badge][badge-kotlin]
![badge][badge-android] 
![badge][badge-gradle]
![badge][badge-junit] <br>
![badge][badge-github-actions]
[![Actions Status](https://github.com/memgonzales/sliding-puzzle/workflows/CI%2FCD/badge.svg)](https://github.com/memgonzales/sliding-puzzle/actions)
[![Actions Status](https://github.com/memgonzales/sliding-puzzle/workflows/Dokka%20Documentation/badge.svg)](https://github.com/memgonzales/sliding-puzzle/actions)

**8-Puzzle** is a **sliding puzzle game** that features a walkthrough of the optimal solution and allows users to customize the tiles using photos selected from their Gallery. In finding the optimal path from the current puzzle state to the goal state, the system employs the informed search algorithm A* and uses Manhattan distance as an admissible and consistent heuristic to estimate the cost. 

Inspired by an exercise in our Mobile Development class, I started creating this app during our university break, after finishing my second year of undergraduate studies. As one of my first personal projects, working on this was an opportunity to synthesize concepts and practices that I learned from our sophomore courses, such as:
- Exploration of language features (Advanced Programming and Theory of Programming Languages)
- Program optimization (Algorithms and Complexity)
- Artificial intelligence and search algorithms (Introduction to Intelligent Systems)
- Quality assurance and unit testing (Introduction to Software Engineering)

This project also utilizes a continuous integration/continuous delivery (CI/CD) pipeline — which is the focus of our third-year class Advanced Software Engineering.

The API documentation can be viewed at [https://memgonzales.github.io/sliding-puzzle/](https://memgonzales.github.io/sliding-puzzle/)

## Project Structure
The project consists of the following folders:

Folder | Description
-- | --
<a href = "https://github.com/memgonzales/sliding-puzzle/tree/master/.github/workflows">`.github` | Contains the YML files defining the workflows that are included in the CI/CD pipeline
<a href = "https://github.com/memgonzales/sliding-puzzle/tree/master/.idea">`.idea`</a> | Contains files used by Android Studio to load project-specific configurations
<a href = "https://github.com/memgonzales/sliding-puzzle/tree/master/app">`app`</a> | Contains all the source code and resource files, the markdown file for the app- and package-level documentation, and files defining the module-specific dependencies, project-specific ProGuard rules, and developer credentials related to Google services
<a href = "https://github.com/memgonzales/sliding-puzzle/tree/master/gradle/wrapper">`gradle`</a> | Contains files used by Gradle to run build automation tasks

Several Gradle-related files are also included in its root directory.

## Running the Application
***The minimum SDK supported is Android Lollipop (API Level 21), and the target SDK is Android 11 (API Level 30).***

### Using the APK
Download and install the APK file from the [latest release](https://github.com/memgonzales/sliding-puzzle/releases/tag/v1.0.0). You may have to modify the security settings of your device to allow the installation of third-party apps from unknown sources.
   
<img src="https://github.com/memgonzales/sliding-puzzle/blob/master/screenshots/screenshots.png?raw=True" alt="App Screenshots" width = 750> 


### Using the Source Code  
1. Create a copy of this repository:
   - If [git](https://git-scm.com/downloads) is installed, type the following command on the terminal:
   
     ```
     git clone https://github.com/memgonzales/sliding-puzzle
     ```
      
   - If git is not installed, click the green <code>Code</code> button near the top right of the repository and choose [<code>Download ZIP</code>](https://github.com/memgonzales/sliding-puzzle/archive/refs/heads/master.zip). Once the zipped folder has been downloaded, extract its contents.

2. Run the app using [Android Studio](https://developer.android.com/studio) (or any IDE that supports Android development). Alternatively, Android also provides a <a href = "https://developer.android.com/studio/build/building-cmdline">guide</a> on how to build the app from the command line.
   
   <img src="https://github.com/memgonzales/sliding-puzzle/blob/master/screenshots/comp_solve.gif?raw=True" alt="Walkthrough of Optimal Solution" width = 206> 
   
## Dependencies
This project uses the following project dependencies:

Dependency | Version | Description | License
-- | -- | -- | --
`com.android.tools.build:gradle` | 4.2.2 | Gradle build automation tool | Apache License 2.0
`org.jetbrains.kotlin:kotlin-gradle-plugin` | 1.5.0 | Gradle plugin for Kotlin/JVM compilation tasks | Apache License 2.0
`org.jetbrains.dokka:dokka-gradle-plugin` | 1.5.0 | Gradle plugin for Dokka documentation engine | Apache License 2.0
`com.google.gms:google-services` | 4.3.10 | Plugin for processing the `google-servies.json` file | Apache License 2.0

It also uses the following module-specific dependencies:

Dependency | Version | Description | License
-- | -- | -- | --
`org.jetbrains.kotlin:kotlin-stdlib` | 1.5.0 | Kotlin standard library for JVM | Apache License 2.0
`androidx.core:core-ktx` | 1.6.0 | Core module providing Kotlin extensions for common framework APIs and several domain-specific extensions | Apache License 2.0
`androidx.appcompat:appcompat` | 1.3.1. | Library allowing access to new APIs on older API versions of the platform | Apache License 2.0
`com.google.android.material:material` | 1.4.0 | Library for using APIs that provide implementations of the Material Design specification | Apache License 2.0
`androidx.constraintlayout:constraintlayout` | 2.1.1 | Library for positioning and sizing widgets in a flexible way with relative positioning | Apache License 2.0
`junit:junit` | 4.13.2 | Unit testing framework for Java | Eclipse Public License 1.0
`androidx.test.ext:junit` | 1.1.3 | AndroidX unit testing framework for Java | Eclipse Public License 1.0 <br/> Apache License 2.0
`androidx.test.espresso:espresso-core` | 3.4.0 | Framework for writing Android user interface tests | Apache License 2.0

*The descriptions are taken from their respective websites.*

## Built Using
This project uses the following languages and technologies:
- **Logic**: <a href = "https://kotlinlang.org/">Kotlin</a>, a statically typed language endorsed by Google as the preferred language for Android development
- **Layouts**: <a href = "https://developer.android.com/guide/topics/ui/declaring-layout">XML</a>, a lightweight markup language that is both human- and machine-readable

[GitHub Actions](https://github.com/features/actions) serves as the **CI/CD platform** for automating these workflows (triggered by a push or pull request):
- Linting, testing, and building the APK
- Generating the HTML version of the API documentation from [KDoc](https://kotlinlang.org/docs/kotlin-doc.html) comments via [Dokka](https://github.com/Kotlin/dokka)
- Publishing the API documentation with GitHub Pages

## Author
- <b>Mark Edward M. Gonzales</b> <br/>
  mark_gonzales@dlsu.edu.ph <br/>
  gonzales.markedward@gmail.com <br/>
  
Assets (such as images and XML resource files) are properties of their respective owners. Attribution is found in the [credits](https://github.com/memgonzales/sliding-puzzle/blob/master/CREDITS.md) file. Technical references used in the implementation of algorithms are cited in the [API documentation](https://memgonzales.github.io/sliding-puzzle/) of the pertinent methods.


[badge-android]: http://img.shields.io/badge/platform-android-6EDB8D.svg?style=flat
[badge-kotlin]: https://img.shields.io/badge/kotlin-%230095D5.svg?&style=flat&logo=kotlin&logoColor=white
[badge-gradle]: https://img.shields.io/badge/Gradle-02303A.svg?style=flate&logo=Gradle&logoColor=white
[badge-github-actions]: https://img.shields.io/badge/GitHub_Actions-2088FF?style=flat&logo=github-actions&logoColor=white
[badge-junit]: https://img.shields.io/badge/Junit5-25A162?style=flat&logo=junit5&logoColor=white
