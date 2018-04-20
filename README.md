# Slicer Menu Library

[![](https://jitpack.io/v/towcar/SlicerMenuLibrary.svg)](https://jitpack.io/#towcar/SlicerMenuLibrary)
[![Build Status](https://travis-ci.org/alipay/sofa-rpc.svg?branch=master)](https://www.carsonskjerdal.com)


Originally found this library on github, it seemed to me to no longer work due to being outdated. Perhaps I missed something during implementatio but anyway I decided to revive it for personal library building experience and because I love menu animation.

[Original Library by Yalantis](https://github.com/Yalantis/GuillotineMenu-Android)

Feel free to contribute and suggest updates.

<img src="https://d13yacurqjgara.cloudfront.net/users/495792/screenshots/2113314/draft-03.gif" alt="Slicer animation gif" style="width:800;height:600">


# Usage

*For a working implementation, have a look at the app module*
1. Add JitPack repository in your root build.gradle at the end of repositories:

    ~~~
    allprojects {
        repositories {
            ...
            maven { url "https://jitpack.io" }
        }
    }

    ~~~

2. Add the dependency to your app build.gradle

~~~
   	dependencies {
	        compile 'com.github.towcar:SlicerMenuLibrary:acb8bd8217'
	}

~~~

3. I found setting matching configuration fallbacks to your app build.gradle also fixed a common issue for users
~~~
android {
  buildTypes {
      release {
          ...
      }
      dexOptions {
          ...
        // release & debug is in project animators
        matchingFallbacks = ['release', 'debug']
      }
      debug {
          ...
      }
    }
}
~~~

# Compatibility
  
  * Android 6.0 Marshmallow (API level 23)
  
# Changelog

### Version: 1.0

  * Initial Build
 

