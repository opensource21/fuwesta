#Template-Engine

##Requirements
A template engine should have the following characteristics:

* Easy to write, this means:
 * compact code
 * code-completion for html and/or java-code.
 * code completion, easy-navigation to tags.
 * support for elvis operator :? and null-safe feature call like .?

* Error proven: There should be a fast feedback if something couldn't work.
The easiest way seems to be a compiler, but this should run once at server-start.
Examples which could go wrong:
 * a tag doesn't exist
 * a model hasn't such method or property.


##Problems
###Keep property-name in sync
If you look at places where the property name is important, you find 4 places:

1. You need it to get the validation errors.
2. You need it for the binding, so the param_name.
3. You need it to get the value.
4. Often you use it to get the label.

The last one is obviously not a problem if you rename a property, because it must
only match with the name in the messages-file. It would be a problem if you
make it automatically sync'ed with the property name, because then you must
synchronize the messages-files too.

Obviously the usage of 1 and 2 can only be made compile-safe if you use some
meta-data-file, which must be generated. This can be done with
[BindGen](http://bindgen.org/). Analog it can be done for message-keys.

Another possibility would be to wrote a special DSL with xText.
Then you could write code-completion and warnings for this.

Another technique is to create a field tag where you insert the field name once
and create your getter names (Using Reflection) and all other names based on it.
The problem is that you will obviously loose compiler safety then.
However you could be at least sure that if the value is shown correctly,
the binding will work and the specific errors will be shown too. And you'll definitely find
the errors when you call the page, which should be a minimal test.
There is only one exception: if the field is part of a conditional part.

BindStatus seems to be a class which tries to solve it at Spring MVC, it
combines the information of value, field name, errors. See
[Form-Taglib](http://static.springsource.org/spring/docs/3.2.0.RELEASE/spring-framework-reference/html/view.html#view-jsp-formtaglib)
The critical point is at which time the error will be thrown.


##Possible Candidates which creates JAVA-Code
Creating java fields from the templates makes the templates fast and many
errors can be found very early.

###Rythm
[Rythm](https://github.com/greenlaw110/rythm) comes from Play but has now even
Spring MVC Support. It's inspired by Razor and has a very clean and good
readable syntax. The only problem is that the @ isn't really easy to type at
German layouts. It's used very often in Asian and Australian companies.

###xTend
[xTend](http://www.eclipse.org/xtend/) is more a general purpose language than
a template-language. However you can very easily create your HTML with it.
The main benefit is the great eclipse support, which lets you write refactoring
safe code. I was really happy with this option till I figured out the problem
I described above.

###StringTemplate
[StringTemplate](http://www.stringtemplate.org/) has a
[SpringMVC integration](http://nickcarroll.me/2009/06/18/using-stringtemplate-as-the-view-engine-for-your-spring-mvc-application/),
but it's the direct opposite to xTend. There only exist conditionals as
control structures. Above List there is a default loop. The approach is very
clean and forces the developer to separate logic and presentation. How ever I
miss extended features and the syntax isn't very elegant.

###Creating an own xText-DSL
Perhaps this would be the solution which will end in an engine which is near
to be perfect, but it's definitely hard work to do which will need several
weeks. So it's out of scope.

###Cambridge
[Cambridge](https://github.com/erdincyilmazel/Cambridge) is a template engine
only for HTML-Generation. It supports Play and Spring MVC (Beta). There is no
editor support. However Cambridge templates are correct HTML files. The
[documentation](http://code.google.com/p/cambridge/wiki/TemplateAuthorsGuide) is
relatively small and not easy to find (it is on the old web site). No idea why it
can't be integrated in github. However it's no help for the problems and I'm
unsure about it's future.

###Thymeleaf
[Thymeleaf](http://www.thymeleaf.org/) is a template engine
only for HTML generation. It supports Play and Spring MVC. There is IDE
support similar to the JSP-Editor of eclipse I guess
(except if you have a good HTML editor, because it is correct HTML).
It is perfect for prototyping and together with an interface builder perhaps a
perfect combination. You can create good layouts with
[Tyhmeleaf-Layout](https://github.com/ultraq/thymeleaf-layout-dialect)
which enables you to include or extend layouts.

###Moulder-J
[moulder-j](https://github.com/jawher/moulder-j). You write normal
HTML-Files. Then you write Java-Code which replaces
blocks from your HTML-code by jQuery expressions.
Could be very attractive with an interface builder.

####JATL
[JATL](http://code.google.com/p/jatl/) is not really a template engine, but
useful to write code which generates HTML. The advantage is obvious, because
you don't have to learn another language.

###Mustache
[Mustache](http://mustache.github.com/) is a general template language which has
2 Java implementations [JMustache](https://github.com/samskivert/jmustache) and
[mustache.java](https://github.com/spullara/mustache.java). The last one
compiles to Java code. However I don't like the typical syntax from mustache
`{{myVar}}`, it's not easy to write.

###JAPID
Comes from Play, but is no longer bundled with Play. It's more inspired from
the Play template engine. It's very similar to
Rythm, but allows to use ` instead of @. It was developed and used
by an Asian company. However at the moment I would prefer to use Rythm.

###JAMON
[JAMON](http://www.jamon.org/index.html) is an interesting candidate with
eclipse support. However there are no changes since 20.11.2012, so I guess the
one developer chose another hobby.

###JSP
Well it's the old school, but I think there is no support for anything.

##Interface-Builder
###Divshot
[Divshot](http://www.divshot.com/)

Pros
 * You can edit HTML directly.

Cons
 * Not really bootstrap specific. This means you must write the field structure
  by yourself.

###Jetstrap
[Jetstrap](http://jetstrap.com/screen-36a41e9c90-overview.html#)
Really nice solution.

###Wire2App
[Wire2App](http://www.wire2app.com/) is a tool which converts
balsamiq-wireframes to HTML code based on twitter bootstrap.
