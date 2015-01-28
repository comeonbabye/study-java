maven仓库汇总
http://tianya23.blog.51cto.com/1081650/386908/
maven依赖查看，maven仓库
http://mvnrepository.com/
源代码查看，并且可以下载jar、source，各种版本
http://grepcode.com/


改成Maven项目，减少jar上传

mvn install:install-file -Dfile=study-parent.xml -DgroupId=com.study -DartifactId=study-parent -Dversion=1.5 -Dpackaging=pom 
mvn install:install-file -Dfile=study-logs.xml -DgroupId=com.study -DartifactId=study-logs -Dversion=1.5.8 -Dpackaging=pom 
http://mark-ztw.iteye.com/blog/1823677    maven 单独部署pom文件
mvn install:install-file -Dfile=[your file] -DgroupId=[xxxx] -DartifactId=[xxxx] -Dversion=[xxxx] -Dpackaging=[pom|jar|other]  
mvn deploy:deploy-file -Dfile=[your file] -DgroupId=[xxxx] -DartifactId=[xxxx] -Dversion=[xxxx] -Dpackaging=[pom|jar|other] -DrepositoryId=[id] -Durl=[repo url]  