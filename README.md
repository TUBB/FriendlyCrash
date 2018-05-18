# Prefetch
[![Release](https://jitpack.io/v/TUBB/FriendlyCrash.svg)](https://jitpack.io/#TUBB/FriendlyCrash)
 ![](https://img.shields.io/badge/minSdkVersion-15-brightgreen.svg)  [![](https://img.shields.io/badge/license-Apache%202-lightgrey.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

Friendly notify user when app crashed if app moved to background.

# Download
```groovy
implementation 'com.github.TUBB:FriendlyCrash:0.0.1'
```

# Usage
Add below code in your custom [Application](https://developer.android.com/reference/android/app/Application). That's all done!
```kotlin
FriendlyCrash.build(this).enable()
```
If app crashed when app moved to background, we just kill app process, so `Force Close Dialog` will not showing.
```kotlin
android.os.Process.killProcess(myPid())
```

# Custom
We add callback for app lifecycle, like `moved to foreground` and `moved to background`. You can get the notify when app crashed, then do something.
```kotlin
FriendlyCrash.build(this) { isOnForeground ->
    if (isOnForeground) {
        Toast.makeText(this, "App moved to foreground", Toast.LENGTH_LONG).show()
    } else {
        Toast.makeText(this, "App moved to background", Toast.LENGTH_LONG).show()
    }
}.enable { _, ex ->
    Log.e(TAG, "App crashed", ex)
}
```
For more details, please see the demo project.

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