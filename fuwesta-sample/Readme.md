#Readme
This is an example setup for easy use of Spring MVC. It's designed for small
projects and tries to get a solution which is comparable with Play or Grails.
There are following goals:

 1. Make the database access as easy as possible.
 2. Use a template engine which has a good productivity.
 3. Make as much functionality as possible compiler-safe.
 4. Good testability.
 5. Should work smartly together with prototyping.

__Important__ For test this sample use [lombok](http://jnb.ociweb.com/jnb/jnbJan2010.html#installation), 
so you must install it for your preferred ide or only compile with maven. 
 
##Architecture

The general structure is front end -> services -> persistence. This means:

 * Only the persistence layer should deal with the database. However if you
  change property-names this will affect the front end too, especially the
  view, because reflection is heavily used there.
 * The business logic should always be built in the services. The services
 assume that they always get valid objects, there is no further validation.
 * The front-end only binds the data, validates the incoming data, decides upon the
 flow and renders the view.

###Spring-Data
This solution uses Spring-Data, because it makes the binding of entities very
easy, by automatically registering a converter for String-Id to Entity.
Furthermore, in most cases you only need to just declare a function, Spring-Data
does the rest for you. However in case of trouble you can easily fall back to your own
implementation.

###Open-Session in View
This is a really common pattern, but it's not generally recommended. The idea is
that you open the Hibernate session with the incoming request and close it when
the response is sent. The problems are described in
[Should I use open session in view](http://heapdump.wordpress.com/2010/04/04/should-i-use-open-session-in-view/)
 or [Antipattern open session in view](http://java.dzone.com/articles/opensessioninview-antipattern).
It is used here, because it makes the usage easier for a beginner:
 * No problems with detached objects when rendering a view.
 * Spring-Data doesn't work well if you try to create new objects which
 refer other data when the referred objects are detached. To store the
 new object, EntityManager.persist() is called, which ends in an exception.

However in a controller there is no transaction, so it's **strongly recommended**
not to change the entities in the controller, especially not in views. All
changes should be made in the services.

### OVal
This example uses [OVal](http://oval.sourceforge.net/) for data validation.
This has the following advantages:

 * It's easy to write checks. Especially for prototyping you can use an
  [expression-language](http://oval.sourceforge.net/userguide.html#declaring-conditional-constraints).
 * You have access to the complete bean at validation time, not only to the value of the field being validated.
 This more convenient in many cases. For example, it makes the UniqueCheck possible.
 * You can add additional information to the message. For example
  "Your text has 205 characters, but only 100 are allowed. You must delete
  105 characters."
 * OVal interprets the information which is given with the
  [JPA-Annotation](http://oval.sourceforge.net/userguide.html#interpreting-ejb3-jpa-annotations)
  and adds checks automatically.

However there are some disadvantages:

 * It's not JSR-303 standard, so you can't use the Hibernate validator or
  any other framework which uses JSR-303.
 * There are two different styles of message formatting. Spring: "The value is {0}." and OVal:
  "The value is {value}.". (BTW SLF4J needs "The value is {}.").

###Template-Engine

##Documentation
 * [Getting started with Spring-Data](http://blog.springsource.org/2011/02/10/getting-started-with-spring-data-jpa/)
 * [Thymeleaf](http://www.thymeleaf.org/)

### Thymeleaf and layouts
You often have a standard way your pages are build. Thymeleaf provides 4
solutions for the problem. At the moment I don't have a favorite.

 * Official:
   * Using fragments and includes: You can use th:include or th:replace and
   include so fragments which are declared in a central file.
   * Using [Apache Tiles 2](https://github.com/thymeleaf/thymeleaf-extras-tiles2)
 * Inofficial:
   * There exists a [Layout-Dialect](https://github.com/ultraq/thymeleaf-layout-dialect)
   which is written from one of the thymeleaf committer. How ever it has
   different version mechanism and if you try beta-version you can come in trouble.
   * Do it manually with Spring interceptor. This is described in a
   [Blog](http://blog.codeleak.pl/2013/11/thymeleaf-template-layouts-in-spring.html).
   It's well documented and easy to understand. The main drawback are that
   it will not work outside spring and that in the post your declare the
   layout in the controller. This is from architectural point of view not so
   nice.

###About the example
The example shows the possibilities you have with Spring-Data. It contains all
possible bidirectional relations between entities.
You'll find a lot of tests for UserDao which were written to play around with
Spring-Data. Normally I wouldn't test generated methods.

You can start with it. If you don't need the example, just remove everything
under `*/samples/` except `*/samples/fuwesta/config`
and the class
`/src/main/java/de/ppi/samples/fuwesta/frontend/URL.java`
You must then adjust `*Config`, `web.xml` and `URL`.

##Eclipse
Standard Java-Edition for test it needs [lombok](http://jnb.ociweb.com/jnb/jnbJan2010.html#installation).
###Config
####Content Assist
Spring recommends that Eclipse users should add
"MockRestRequestMatchers", "MockRestResponseCreators",
"MockMvcRequestBuilders", "MockMvcResultMatchers" and "MockMvcBuilders"
as `favorite static members` in the Eclipse preferences
under `Java -> Editor -> Content Assist -> Favorites`.
I recommend "org.assertj.core.api.Assertions", "org.mockito.Mockito" and "org.mockito.Matchers".

####Eclemma
In preferences, choose `Java -> Code Coverage` and set
"Only path entries matching" to "src/main/java"

####Maven
Make sure your Maven settings are correct!
In preferences change `Maven -> Lifecycle Settings` make sure that it contains the content of
`/fuwesta-sample/config/eclipse/lifecycle-mapping-metadata.xml`.

###Plugins
You can find the sources /config/eclipse/bookmarks.xml
####Web
 * [Thymeleaf](https://github.com/thymeleaf/thymeleaf-extras-eclipse-plugin)  http://www.thymeleaf.org/eclipse-plugin-update-site/
    (+Eclipse Web Developer Tools)  I don't use Eclipse Java Web Developer Tools
 * [AnyEdit](http://andrei.gmxhome.de/anyedit/) http://andrei.gmxhome.de/eclipse/
 * [Jetty and Eclipse](http://code.google.com/p/run-jetty-run/) http://run-jetty-run.googlecode.com/svn/trunk/updatesite

####Test
 * [More Unit](http://moreunit.sourceforge.net/#overview) http://moreunit.sourceforge.net/update-site/
 * [Eclemma](http://www.eclemma.org/installation.html) http://update.eclemma.org/ Eclipse Plugin for JaCoCo.
 * [Infinitest](http://infinitest.github.io/) http://infinitest.github.com

####Others
 * [JAutoDoc](http://jautodoc.sourceforge.net/) http://jautodoc.sourceforge.net/update/
 * [Checkstyle](http://eclipse-cs.sourceforge.net/) http://eclipse-cs.sf.net/update/
 * [ColorEditor](http://gstaff.org/colorEditor/) Jar put into dropins.
 * [Markdown Editor](http://www.winterwell.com/software/markdown-editor.php) http://winterwell.com/software/updatesite/
 * [EGit](http://www.eclipse.org/egit/) http://download.eclipse.org/egit/updates to be sure to always have the newest version.

####Not tested
 * [ResourceBundleEditor](http://eclipse-rbe.sourceforge.net/)

###Useful Resources
 * [Spring-Loaded](https://github.com/spring-projects/spring-loaded) Automatic class reloading.
