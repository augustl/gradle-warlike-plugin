# gradle-warlike-plugin

Gives you auto reloading of code when developing WAR based web apps with Gradle.

The plugin has one task:

`gradle runWarlike`

This starts a web server that assumes a WAR-like setup, the same as Gradle's built in "war" plugin.

Make your web app just the way you would normally with the "war" plugin, and gradle-warlike-plugin will auto detect everything just like a real webapp container, and auto reload the code when you recompile it.

## Installing

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

`gradle runWarlike`

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


## Webapp files

Mirroring the "war" plugin, the folder src/main/webapp will be included as part of the container setup.

For example, add the file src/main/webapp/WEB-INF/web.xml to configure the container via the traditinal web.xml format.
