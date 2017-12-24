# springmvc_maven
My previous Spring projects experience:
built with Eclipse and STS, maven projects,
since I need to deploy to tomcat and jboss(manually or puppet),
it's easy to config tomcat and jboss server locally with Eclipse or STS.

However this project is created in Intellij,
which cause some unexpected issue.
For src/main/webapp/WEB/INF/jsp/index.jsp
this path was modified, the original path is root/web/WEB-INF
in order to let intellij find the path,
it's necessary to config the Web Resource Directory in File->Project Structures

Conclusion: it's different to test spring project with Eclipse/STS and Intellij
especially on configuring the project and server locally
(more details, please check my personal note, Youdao Yun)

The order to create this project:
1. File->New Project->Spring MVC
2. right click on project -> Add Framework Support

Refer to maven_springmvc, another order of creating maven based spring project:
1. File->New Project->Maven
2. Add Spring dependencies in pom.xml

