# Jalo - Java loader

自動更新が可能なJavaアプリケーションのブートストラップです。


## jalo.xml

 configuration file to bootstrap the java application.

    <?xml version='1.0' encoding='utf-8'?>
    <jalo>
      <application-name>app name</application-name>
      <application-directory>app</application-directory>
      <url>http://path/to/server/</url>
      <auto-update>true</auto-update>
      <temporary-directory>tmp</temporary-directory>
    </jalo>


## app.xml

  configuration file to update and run the java application.

    <?xml version='1.0' encoding='utf-8'?>
    <app>
      <name>app name</name>
      <version>0.1</version>
      <classpath>
        <path>test01.jar</path>
        <path>test02.jar</path>
      </classpath>
      <mainclass>com.github.tamurashingo.jalo.Test</mainclass>
    </app>



