fuwesta
=======

A full web stack based on spring.

If you want to use it add the following dependency to your pom.

    <dependency>
        <groupId>de.ppi.oss</groupId>
        <artifactId>fuwesta-core</artifactId>
        <version>0.9</version>
    </dependency>
    
and add the following repository

    <repositories>
        <repository>
            <id>opensource21</id>
            <url> http://opensource21.github.com/releases</url>
        </repository>
    </repositories>

You can use the sample-app as a starting point. There you
find a Readme.md which explains how to setup eclipse and
some of the main-ideas. You can take the sample-app and remove
all under the package example except the config. A clean 
maven archetype is on the todo-list.

If you want to see FuWeSta in action you can checkout 
[SZE](https://github.com/opensource21/sze).
