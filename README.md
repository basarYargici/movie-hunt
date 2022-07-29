<h1 align="center">Movie Hunter</h1></br>  
<p align="center">### Status: 🚧 In progress  </p>
<p align="center">    
A demo Movie app using <a href="https://www.themoviedb.org" target="_blank"> The Movie DB </a> based on MVVM + Clean architecture.<br>  
</p>  
</br>  

## Features
* 100% Kotlin
* MVVM with Clean Architecture
* Android Architecture Components
* Kotlin Coroutines + Flow
* Single activity pattern
* Jetpack Compose Navigation.
* Dependency injection with Hilt
* Dark Mode / Light Mode
* Support Multi Language
* Testing (Upcoming)
* Compose (Upcoming-v2)

##  Tech stack & Open-source libraries

- 100%  [Kotlin](https://kotlinlang.org/)  based +  [Coroutines](https://github.com/Kotlin/kotlinx.coroutines)  +  [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/)  for asynchronous.
- [Jetpack](https://developer.android.com/jetpack)🚀
- Lifecycle - Dispose observing data when lifecycle state changes
- [Viewmodel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Manage UI related data in a lifecycle conscious way  
  and act as a channel between use cases and ui
- [View Binding](https://developer.android.com/topic/libraries/view-binding) - Support library that generates a _binding class_ for each XML layout file present in that module. An instance of a binding class contains direct references to all views that have an ID in the corresponding layout. In most cases, view binding replaces  `findViewById`.
- [Navigation Component](https://developer.android.com/guide/navigation) Navigation component is the API and the design tool in Android Studio that makes it much easier to create and edit navigation flows throughout your application.

- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Dependency injection library for Android
- [Retrofit](https://square.github.io/retrofit/) - Type safe http client  
  and supports coroutines out of the box.
- [GSON](https://github.com/square/moshi) - JSON Parser
- [okhttp-logging-interceptor](https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor/README.md) - Logs HTTP request and response data.
- [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines) - Concurrency design pattern for asynchronous programming.
- [Firebase](https://firebase.google.com/) - Backend As A Service for faster mobile development.
  - [Storage](https://firebase.google.com/docs/storage) - Store and serve user-generated content, such as photos or videos

- [Coil](https://coil-kt.github.io/coil/) - An image loading library for Android backed by Kotlin Coroutines
- [SharedPreferences](https://developer.android.com/reference/android/content/SharedPreferences) - Save key-value data

- [Youtube Android Player API](https://developers.google.com/youtube/android/player) - Enables you to incorporate video playback functionality into your Android applications
- [Download Manager](https://developer.android.com/reference/android/app/DownloadManager) - System service that handles long-running HTTP downloads
- [Youtube Extractor](https://github.com/HaarigerHarald/android-youtubeExtractor) - Stream or download youtube videos
- [Shimmer](https://github.com/facebook/shimmer-android) - Provides an easy way to add a shimmer effect to any view
- [Timber](https://github.com/JakeWharton/timber) - Logger with a small, extensible API which provides utility on top of Android's normal  `Log` class
- [Lottie](https://github.com/facebook/shimmer-android) - Parses and renders animations

## Download

1. Clone the repo
2. Generate a new API Key from [The Movie DB](https://www.themoviedb.org/settings/api). We will use this key in each request.
3. Follow the YouTube Android Player API documentation : Download jar and generate API Key
4. Go to `gradle.properties` file and create and bind your keys to IMDB API Key and Youtube API Key :
 ```kotlinAPI_KEY="0d******************v32" YOUTUBE_API_KEY="I8Y*************************8J"  
```  
6. Add keys to `gradle.properties` :
 ```kotlinbuildConfigField 'String', 'API_KEY', API_KEY buildConfigField 'String', 'YOUTUBE_API_KEY', YOUTUBE_API_KEY  
```  
7. Gradle Sync

## Screenshots

#### Status: 🚧 In progress

<!--   
<p align="center">  
<img src=".gif" width="32%"/>  
<img src=".gif" width="32%"/>  
<img src=".gif" width="32%"/>  
</p>  
 -->  


## MAD Score
#### Status: 🚧 In progress



## Find this repository useful? :heart:
Support it by joining __[stargazers](https://github.com/basarYargici/movie-hunt/stargazers)__ for this repository. :star: <br>  
And __[follow](https://github.com/basarYargici)__ me for my next creations!

<iframe src="https://giphy.com/embed/ZBVhKIDgts1eHYdT7u" width="240" height="240" frameBorder="0" class="giphy-embed" allowFullScreen></iframe><p><a href="https://giphy.com/gifs/Grittv-cowboy-salute-john-wayne-ZBVhKIDgts1eHYdT7u"></a></p>

# License
```xml  
MIT License  
  
Copyright (c) 2022 İ. Başar Yargıcı  
  
Permission is hereby granted, free of charge, to any person obtaining a copy  
of this software and associated documentation files (the "Software"), to deal  
in the Software without restriction, including without limitation the rights  
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell  
copies of the Software, and to permit persons to whom the Software is  
furnished to do so, subject to the following conditions:  
  
The above copyright notice and this permission notice shall be included in all  
copies or substantial portions of the Software.  
  
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR  
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,  
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE  
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER  
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,  
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  
SOFTWARE.  
```