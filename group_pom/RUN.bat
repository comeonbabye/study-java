call mvn install:install-file -Dfile=study-parent.xml -DgroupId=com.study -DartifactId=study-parent -Dversion=1.5 -Dpackaging=pom 
call mvn install:install-file -Dfile=study-logs.xml -DgroupId=com.study -DartifactId=study-logs -Dversion=1.5.8 -Dpackaging=pom 
call mvn install:install-file -Dfile=study-common.xml -DgroupId=com.study -DartifactId=study-common -Dversion=1.5.8 -Dpackaging=pom 