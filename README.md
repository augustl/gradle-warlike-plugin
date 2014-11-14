# gradle-warlike-plugin

A local webserver with auto reloading of code, when developing WAR based web apps with Gradle.

The plugin has one task:

`gradle runWarlike`

This starts a web server that assumes a WAR-like setup, the same as Gradle's built in "war" plugin.

Make your web app just the way you would normally with the "war" plugin, and gradle-warlike-plugin will auto detect everything just like a real webapp container, and auto reload the code when you recompile it.

## Installing

Install as any Gradle plugin. The package is deployed to bintray/jcenter.

```groovy
buildscript {
    repositories {
        jcenter()
    }

    dependencies {
        classpath "com.augustl:gradle-warlike-plugin:0.1.4"
    }
}
apply plugin: 'com.augustl.warlike'
```

## Running

To start the local server with hot code reloading, run the task provided by the plugin:

`gradle runWarlike`

This task will start the server.

## Manually reloading server instance

**NOT IMPLEMENTED YET**

Hit the enter key in the terminal where you're running the server.

This is faster than restarting the entire Gradle process.

You need this when you make changes that hot code reloading doesn't affect, such as changing web.xml.

Reloading means shutting down the embedded WAR container server. Any servlets you have will have their shutdown hooks called. If you have global state, you're responsible for tearing that down yourself if you want to. The plugin doesn't provide any hooks. Global state is bad, m'kay! One recommendation is to create a spring-context that contains all your state, and avoid global state entirely. Ensure that all your stateful beans implement teardown hooks properly, and this method o

## Configuring

You can configure the `runWarlike` task:

```groovy
runWarlike {
    port = 1234
}
```

<table>
  <tr>
    <td>port</td>
    <td>The port number to start the local web server at.</td>
  </tr>
</table>


## web.xml

This plugin follows the conventions of the "war" plugin. This is where you'll put web.xml:

`src/main/webapp/WEB-INF/web.xml`

Note that web.xml is not required. We implement Servlet 3.0 annotation based web listeners as well.

## Internals

springloaded is used for hot code reloading.

Jetty is used for the embedded container.

## Todo

* Make more stuff configurable. Perhaps the IP to bind to, now we just use Jetty defaults.
* [your feature request here]
