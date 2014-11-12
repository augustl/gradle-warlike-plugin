# gradle-warlike-plugin

Auto-reloading WAR-like environment for local development.

## Installing

```groovy
buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath "com.augustl:gradle-warlike-plugin:0.1.0"
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
