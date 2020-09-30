# Monetary Solutions
A simple currency API for Fabric.

## Adding to your mod
To add this to your mod, add this to your `repositories` block in `build.gradle:

```
maven { 
  url 'https://jitpack.io' 
}
```
Then, to include in your mod (JIJing is not required but highly recommended!):
```
dependencies {
  modImplementation 'com.github.TeamVibehub:monetary-solutions:<VERSION>'
  include 'com.github.TeamVibehub:monetary-solutions:<VERSION>'
}
```
Replace <VERSION> with the version you want to use.
Latest is 1.2.0.
