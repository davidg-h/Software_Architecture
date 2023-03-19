_This is an assignment to the [Software Architecture](https://ohm-softa.github.io) class at the [Technische Hochschule Nürnberg](http://www.th-nuernberg.de)._

# Assignment 1: A (Java) software engineers toolbox


## Get familiar with Git

1. Familiarize yourself with [git](https://git-scm.com) by completing this [interactive tutorial](https://learngitbranching.js.org/) in your browser.
2. Install git on your machine.
	- Mac: Open `Terminal.app` and install the Apple Developer Tools by executing `xcode-select --install`.
	- Linux: Use your distribution's package manager to install git; on Ubuntu, you'll run something like 
		```bash
		$ sudo apt-get update
		$ sudo apt-get install git
		```
	- Windows: ~~You should not use Windows, use Ubuntu on VirtualBox instead.~~ Download and install the [Windows installation package](https://git-scm.com/download/win); make sure to install (and use) git bash.
3. Try git in the real world:
	- Sign in or create an account on [Github](https://github.com) (it's free, and you should already have one...).
	- Create a new repository; select __Initialize this repository with a README__ to add a `README.md` file.
	- On your computer, clone your new repository, edit the `README.md` to your choice, using the [Github markdown cheatsheet](https://guides.github.com/features/mastering-markdown/).
	- Add/stage and commit the changes, and push them to your repository.
	- Create and switch to a new branch (eg. `feature/add-license`), and add a file `LICENSE` to it, containing the MIT open source software license. Not sure where to find it? Try [this link](http://bfy.tw/EF8t). For a real project, you may want to chose a different license (read more about licenses here: [https://opensource.org/licenses](https://opensource.org/licenses)).
	- Push the branch; you should now be able to see it on Github.
	- On Github, create a pull request and merge it to master.
	- Back on your computer, checkout `master`, and pull the changes to update your local copy.

> Note: Since you'll be working on assignments together, it's a good idea to fork the official assignment repository, add your friend as a collaborator, and take it from there!

> You should now know what it means if someone asks you: Can you please merge my PR into your master branch?

## Get familiar with Gradle

0. Install the [Gradle build tool](https://gradle.org/); make sure you have a recent [Java JDK](https://jdk.java.net/11/) installed and configured for command line use.
1. Familiarize yourself by completing the [Java application tutorial](https://guides.gradle.org/building-java-applications/). Note: This requires a command line shell, such as bash. If you're running Windows, you can use git's bash, try Cygwin, or use Ubuntu on VirtualBox.


> Note: Using the gradle wrapper is generally advised.

> You should now know how to read a basic `build.gradle` file, and what `gradle build` means.


## Get familiar with IntelliJ

1. Make sure you have the latest [IntelliJ IDEA](https://www.jetbrains.com/idea/) installed (it's [free for educational use](https://www.jetbrains.com/student/)!).
2. On first launch, you may have to configure the default project structure and check your installed/configured JDKs:
	![Configure default project structure](/assets/idea-01-ps.png)
	![Verify installed/configured JDKs](/assets/idea-02-jdk.png)
	![Check default JDK for projects](/assets/idea-03-project-jdk.png)
	You may also have to [configure the location of Gradle](https://www.jetbrains.com/help/idea/gradle.html), if not automatically configured.
3. Select _Check out from Version Control: git_ and provide the repository URL; note that you can use either `https://github.com/ohm-softa/...` (and provide your Github username and password), or `git@github.com:ohm-softa/...` (with [SSH credentials as configured with Github](https://help.github.com/articles/connecting-to-github-with-ssh/)).
	![Check out from VCS](/assets/idea-04-from-git.png)
4. After checkout, it may take a while for Gradle to build and index the project.
5. Look at the project's `build.gradle` and see how it configures for the use of [JUnit 5](http://junit.org/junit5/docs/current/user-guide/#running-tests-build-gradle).
6. Look at the class `MyExampleClass` and read the javadoc comments. What is it's (very simple) purpopse?
7. Look at the test class `MyExampleClassTest` and run the tests.
8. **Fix `MyExampleClass` so that all test cases pass.**

> Note: We will go into details with `import static` and `@...` later, but go ahead and google what they do!


## For the future expert

The [original repository](https://ohm-softa.github.com/01a-tools) has [Travis CI](https://travis-ci.org) integration enabled (see `.travis.yml`).
On each push event, it will run a build process, which includes executing all tests.
If one test fails, the build fails, which can help prevent merging in bugs from other branches or forks.
