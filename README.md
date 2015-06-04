# fuwesta

A full web stack based on spring.

If you want to use it add the following dependency to your pom.

For Spring 3

    <dependency>
        <groupId>de.ppi.oss</groupId>
        <artifactId>fuwesta-core</artifactId>
        <version>0.22</version>
    </dependency>

For Spring 4

    <dependency>
        <groupId>de.ppi.oss</groupId>
        <artifactId>fuwesta-core</artifactId>
        <version>1.0</version>
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

## Advantages
The advantages of FuWeSta are the following:

- Get a set of jars which works together and well definied configuration (with Spring 4 not so important).
- A clean integration of [OVal Object Validation](http://oval.sourceforge.net/)
- A lot of validations (unique and optimistic-locking) see `de.ppi.fuwesta.oval.validation`.
- MessageSource-Handling, specially to add information into the messages and recursive usage, see

    - `de.ppi.fuwesta.spring.mvc.util.UrlDefinitionsToMessages`, 
    - `de.ppi.fuwesta.spring.mvc.util.EntityPropertiesToMessages`
    - `de.ppi.fuwesta.spring.mvc.util.RecursivePropertiesPersister`.   
- FileContentView
- ResourceNotFoundException
- Paginating-Support see `de.ppi.fuwesta.spring.mvc.util.PageWrapper<T>`
- Good Exception-Handling `de.ppi.fuwesta.spring.mvc.exception.BasicGlobalExceptionHandler`
- Enum-Converter `de.ppi.fuwesta.spring.mvc.formatter.EnumConverter`
- Partial-Update, this means that you don't need to put all fields of
  an entity into hidden-field of a view. `de.ppi.fuwesta.spring.mvc.bind.ServletBindingService`
- Thymeleaf Dialect for Bootstrap2
- Thymeleaf Dialect for create mailto-urls.

I created small [presentation](http://opensource21.github.io/presentation/fuwesta/#/).

