# Prefetch
[![Release](https://jitpack.io/v/TUBB/FriendlyCrash.svg)](https://jitpack.io/#TUBB/FriendlyCrash)
 ![](https://img.shields.io/badge/minSdkVersion-15-brightgreen.svg)  [![](https://img.shields.io/badge/license-Apache%202-lightgrey.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

Friendly notify user when app crashed if app moved to background.

# Download
```groovy
implementation 'io.github.tubb:friendlycrash:1.0.2'
```

# Usage
Add below code in your custom [Application](https://developer.android.com/reference/android/app/Application). That's all done!
```kotlin
FriendlyCrash.build(this).enable()
```
If app crashed when app moved to background, we just kill app process, so `Force Close Dialog` will not showing.
```kotlin
killProcess(myPid())
```

# Custom
- We add callback for app lifecycle, like `moved to foreground` and `moved to background`.
- Also you can get the notify when app crashed, then do something.
- And enable friendly notify when app on foreground.

```kotlin
class App: Application() {

    companion object {
        private const val TAG = "FriendlyCrash"
    }

    override fun onCreate() {
        super.onCreate()
        // friendly crash when app on background
        FriendlyCrash.build(this, ::appMovedTo)
                // enable friendly notify when app on foreground
                .friendlyOnForeground(true)
                .enable(::appCrashed)
    }

    private fun appCrashed(onForeground: Boolean, thread: Thread, ex: Throwable) {
        val msg = if (onForeground) {
            "when app on foreground"
        } else {
            "when app on background"
        }
        Log.e(TAG, "App crashed $msg", ex)
    }

    private fun appMovedTo(isOnForeground: Boolean) {
        if (isOnForeground) {
            Toast.makeText(this, "App moved to foreground", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "App moved to background", Toast.LENGTH_LONG).show()
        }
    }
}
```
For more details, please see the [DEMO](https://github.com/TUBB/FriendlyCrash/tree/master/app/src/main/java/io/github/tubb/fcrash/sample) project.

# Dependencies
```groovy
implementation "android.arch.lifecycle:extensions:1.1.1"
annotationProcessor "android.arch.lifecycle:compiler:1.1.1"
```

# License

    Copyright 2018 TUBB

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.