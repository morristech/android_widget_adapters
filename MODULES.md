Modules
===============

Library is also distributed via **separate modules** which may be downloaded as standalone parts of
the library in order to decrease dependencies count in Android projects, so only dependencies really
needed in an Android project are included. **However** some modules may depend on another modules
from this library or on modules from other libraries.

Below are listed modules that are available for download also with theirs dependencies.

## Download ##

### Gradle ###

For **successful resolving** of artifacts for separate modules via **Gradle** add the following snippet
into **build.gradle** script of your desired Android project and use `compile '...'` declaration
as usually.

    repositories {
        maven {
            url  "http://dl.bintray.com/universum-studios/android"
        }
    }

**[Core](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/main)**

    compile 'universum.studios.android:widget-adapters-core:1.0.0@aar'

**[State](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/state)**

    compile 'universum.studios.android:widget-adapters-state:1.0.0@aar'

**[List](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/list)**

    compile 'universum.studios.android:widget-adapters-list:1.0.0@aar'

_depends on:_
[widget-adapters-core](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/main),
[widget-adapters-state](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/state)

**[List-Base](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/list/base)**

    compile 'universum.studios.android:widget-adapters-list-base:1.0.0@aar'

_depends on:_
[widget-adapters-core](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/main),
[widget-adapters-state](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/state)

**[List-Simple](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/list/simple)**

    compile 'universum.studios.android:widget-adapters-list-simple:1.0.0@aar'

_depends on:_
[widget-adapters-core](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/main),
[widget-adapters-state](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/state),
[widget-adapters-list-base](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/list/base)

**[Recycler](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/recycler)**

    compile 'universum.studios.android:widget-adapters-recycler:1.0.0@aar'

_depends on:_
[widget-adapters-core](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/main),
[widget-adapters-state](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/state)

**[Recycler-Base](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/recycler/base)**

    compile 'universum.studios.android:widget-adapters-recycler-base:1.0.0@aar'

_depends on:_
[widget-adapters-core](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/main),
[widget-adapters-state](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/state)

**[Recycler-Simple](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/recycler/simple)**

    compile 'universum.studios.android:widget-adapters-recycler-simple:1.0.0@aar'

_depends on:_
[widget-adapters-core](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/main),
[widget-adapters-state](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/state),
[widget-adapters-recycler-base](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/recycler/base)

**[Spinner](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/spinner)**

    compile 'universum.studios.android:widget-adapters-spinner:1.0.0@aar'

_depends on:_
[widget-adapters-core](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/main),
[widget-adapters-state](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/state)

**[Spinner-Base](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/spinner/base)**

    compile 'universum.studios.android:widget-adapters-spinner-base:1.0.0@aar'

_depends on:_
[widget-adapters-core](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/main),
[widget-adapters-state](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/state)

**[Spinner-Simple](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/spinner/simple)**

    compile 'universum.studios.android:widget-adapters-spinner-simple:1.0.0@aar'

_depends on:_
[widget-adapters-core](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/main),
[widget-adapters-state](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/state),
[widget-adapters-spinner-base](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/spinner/base)

**[Module](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/module)**

    compile 'universum.studios.android:widget-adapters-module:1.0.0@aar'

_depends on:_
[widget-adapters-state](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/state)

**[Module-Core](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/module/core)**

    compile 'universum.studios.android:widget-adapters-module-core:1.0.0@aar'

_depends on:_
[widget-adapters-state](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/state)

**[Module-Header](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/module/header)**

    compile 'universum.studios.android:widget-adapters-module-header:1.0.0@aar'

_depends on:_
[widget-adapters-state](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/state),
[widget-adapters-module-core](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/module/core)

**[Module-Selection](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/module/selection)**

    compile 'universum.studios.android:widget-adapters-module-selection:1.0.0@aar'

_depends on:_
[widget-adapters-state](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/state),
[widget-adapters-module-core](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/module/core)

**[Wrapper](https://github.com/universum-studios/android_widget_adapters/tree/master/library/src/wrapper)**

    compile 'universum.studios.android:widget-adapters-wrapper:1.0.0@aar'
