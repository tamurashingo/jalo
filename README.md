# Jalo version 0.1.0
====================
Jalo is a java bootstrap utility with automatically updater.

java -jar jalo.jar

You can run the application only type above.
And you can download jar files where you set to download.


How to use
----------

1. Edit jalo.xml

See following comment.
```xml
<?xml version='1.0' encoding='utf-8'?>
<jalo>
  <!---
    --- Specify the application name.
    --- It's not used for anything in particular.
    --->
  <application-name></application-name>

  <!---
    --- Specify the directory where stored the app.xml file
    --->
  <application-directory></application-directory>

  <!---
    --- Specify where to get the app.xml file.
    --->
  <url></url>

  <!---
    --- Update automatically?
    ---  1 : Yes, update automatically.
    ---  0 : No, update manually.
    --->
  <auto-update></auto-update>

  <!---
    --- Specify java class to download app.xml and new modules.
    --->
  <update-class></update-class>

  <!---
    --- Specify the temporary directory.
    --->
  <temporary-directory></temporary-directory>
</jalo>
```


2. Edit app.xml

See following comment.

```xml
<?xml version='1.0' encoding='utf-8'?>
<app>
  <!---
    --- Specify the application name.
    --->
  <name></name>
  
  <!---
    --- Specify the application version number.
    --- If this version number greater than local app's version number,
    --- download files and update the application.
    --- >
  <version></version>
  
  <!---
    --- Specify all jar files to run your application.
    --->
  <classpath>
    <path></path>
  </classpath>
  
  <!---
    --- Specify main class to run your application.
    --->
  <mainclass></mainclass>
</app>
```


3. Distribute files

Write shell script or batch file to run jalo.jar.


example
-------
### files

```xml
<?xml version='1.0' encoding='utf-8'?>
<jalo>
  <application-name>TestHelloWorld</application-name>
  <application-directory>app</application-directory>
  <url>http://tamurashingo.github.io/jalo/testhelloworld</url>
  <auto-update>0</auto-update>
  <update-class>com.github.tamurashingo.jalo.autoupdater.impl.HttpAutoUpdater</update-class>
  <temporary-directory>tmp</temporary-directory>
</jalo>
```

```xml
<?xml version='1.0' encoding='utf-8'?>
<app>
  <name>TestHelloWorld</name>
  <version>0.1</version>
  <classpath>
    <path>TestHelloWorld01.jar</path>
    <path>commons-lang3-3.1.jar</path>
  </classpath>
  <mainclass>test.main.Main</mainclass>
</app>
```
    
    
**warning**
The version check uses String.compareTo() method.
So, check the following table.

| local | server | download? |
|:-----:|:------:|:---------:|
| 0.1   | 0.1    | no        |
| 0.1   | 0.2    | yes       |
| 0.8   | 0.9    | yes       |
| 0.9   | 0.10   | no        |


### how to use splash screen

Jalo runs with splash screen that is transparency GIF image.
So, when you want to use splash screen, get and override default splash screen.

here's example.

```java
// show simple image.
public Init {
    public void init() {
        SplashScreen screen = SplashScreen.getSplashScreen();
        if (screen != null) {
            try {
                ClassLoader loader = Init.class.getClassLoader(); 
                screen.setImageURL(loader.getResource("splashscreen.png"));
            }
            catch (NullPointerException|IOException|IllegalStateException ex) {
                ex.printStackTrace();
            }
        }
    }
}
```


Copyright and license
---------------------

The MIT License

Copyright (c) 2013 tamura shingo

Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the
"Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject
to the following conditions:

The above copyright notice and this permission notice shall be included
in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR
OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
OR OTHER DEALINGS IN THE SOFTWARE.

