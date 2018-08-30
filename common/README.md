# 集成说明
## 本地maven配置
* mac系统下配置文件路径：/Users/leeco/.m2/settings.xml，内容如下:

<?xml version="1.0" encoding="UTF-8"?>  
<settings xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"    
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">  

	<servers>
		<server>
            		<id>levp-snapshot</id>
            		<username>chenlei9</username>
            		<password>!Aa123456/</password>
        	</server>
        	<server>
            		<id>levp-release</id>
            		<username>chenlei9</username>
            		<password>!Aa123456/</password>
        	</server>
	</servers> 

## 编译及调试
* 本地编译：{mas}/sh ./mvn_build.sh common
* 本地pom引用调试，开启scope/system，以及repositories设置具体参考{mas}/router/pom.xml中相关定义
        <dependency>
            <groupId>com.letv.mas</groupId>
            <artifactId>common</artifactId>
            <version>${mas.common}</version>
            <scope>system</scope>
            <systemPath>${project.basedir}/../common/target/common-1.0-SNAPSHOT.jar</systemPath>
        </dependency>
        
* 编译&远程发布：{mas}/sh ./mvn_build.sh common deploy
        