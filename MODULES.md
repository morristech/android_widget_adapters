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

## Available modules ##
> Following modules are available in the [latest](https://github.com/universum-studios/android_widget_adapters/releases "Latest Releases page") release.

- **[Core](https://github.com/universum-studios/android_widget_adapters/tree/master/library-core)**
- **[State](https://github.com/universum-studios/android_widget_adapters/tree/master/library-state)**
- **[@Recycler](https://github.com/universum-studios/android_widget_adapters/tree/master/library-recycler_group)**
- **[Recycler-Base](https://github.com/universum-studios/android_widget_adapters/tree/master/library-recycler-base)**
- **[Recycler-Simple](https://github.com/universum-studios/android_widget_adapters/tree/master/library-recycler-simple)**
- **[@List](https://github.com/universum-studios/android_widget_adapters/tree/master/library-list_group)**
- **[List-Base](https://github.com/universum-studios/android_widget_adapters/tree/master/library-list-base)**
- **[List-Simple](https://github.com/universum-studios/android_widget_adapters/tree/master/library-list-simple)**
- **[@Spinner](https://github.com/universum-studios/android_widget_adapters/tree/master/library-spinner_group)**
- **[Spinner-Base](https://github.com/universum-studios/android_widget_adapters/tree/master/library-spinner-base)**
- **[Spinner-Simple](https://github.com/universum-studios/android_widget_adapters/tree/master/library-spinner-simple)**
- **[@Module](https://github.com/universum-studios/android_widget_adapters/tree/master/library-module_group)**
- **[Module-Core](https://github.com/universum-studios/android_widget_adapters/tree/master/library-module-core)**
- **[Module-Header](https://github.com/universum-studios/android_widget_adapters/tree/master/library-module-header)**
- **[Module-Selection](https://github.com/universum-studios/android_widget_adapters/tree/master/library-module-selection)**
- **[Wrapper](https://github.com/universum-studios/android_widget_adapters/tree/master/library-wrapper)**
