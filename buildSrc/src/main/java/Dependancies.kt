import Versions.lifecycle_version

object Apps {
    const val compileSdk = 30
    const val buildToolsVersions = "30.0.1"
    const val minSdk = 21
    const val targetSdk = 30
    const val versionCode = 1
    const val versionName = "1.0.0"
}

object Versions {
    const val gradle = "3.5.3"
    const val kotlin = "1.4.0"
    const val appcompat = "1.1.0"
    const val material = "1.2.0"
    const val coreKtxVersion = "1.2.0"
    const val constraintLayoutVersion = "1.1.3"
    const val coroutinesVersion = "1.3.7"
    const val roomVersion = "2.2.4"
    const val koinVersion = "2.0.1"
    const val retrofitVersion = "2.9.0"
    const val okHttpVersions = "4.8.1"
    const val statusBarUtilVersion = "1.5.1"
    /* test */
    const val junit = "4.12"
    const val androidExtJunit = "1.1.0"
    const val androidEspresso = "3.2.0"
    const val mockkVersion = "1.10.0"

    //liveData + ViewModel
    const val lifecycle_version = "2.2.0"

    // navigation component
    const val nav_version = "2.3.0"

    // rounded and circle imageView
    const val roundedImageViewVersion = "2.3.0"
    const val circleImageViewVersion = "3.1.0"
    const val multiDexVersion = "1.0.3"

}

object Libs {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtxVersion}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutVersion}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.appcompat}"
    const val cardView = "androidx.cardview:cardview:1.0.0"
    const val imageLibrary = "io.coil-kt:coil:0.13.0"
//    const val loadingView = "com.samigehi:loadingview:1.1"
    const val loaderView = "com.wang.avi:library:2.1.3"

    // coroutines
    const val coroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesVersion}"
    const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersion}"
    const val coroutinesViewModelExt = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.1.0-beta01"

    // ViewModel
    const val viewModel ="androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"
    // LiveData
    const val liveData ="androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"


    // Room
    const val roomRuntime = "androidx.room:room-runtime:${Versions.roomVersion}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"
    const val roomRoomKtx = "androidx.room:room-ktx:${Versions.roomVersion}"

    // Koin
    const val koinAndroid = "org.koin:koin-android:${Versions.koinVersion}"
    const val koinViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koinVersion}"
    const val koinAndroidScope = "org.koin:koin-androidx-scope:${Versions.koinVersion}"
    const val koinAndroidArc = "org.koin:koin-androidx-ext:${Versions.koinVersion}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    const val retrofitGsonConverter =
        "com.squareup.retrofit2:converter-gson:${Versions.retrofitVersion}"
    const val retrofitAdapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofitVersion}"
    const val retrofitLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpVersions}"
    const val gson = "com.google.code.gson:gson:2.4"
//    const val okhttp = "com.squareup.okhttp3:okhttp:3.9.0"
    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttpVersions}"



    // statusBar Utils
    const val statusBarUtils = "com.jaeger.statusbarutil:library:${Versions.statusBarUtilVersion}"


    // NavigationComponent
    const val navigationFragmentsKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.nav_version}"
    const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.nav_version}"

    // dimensions
    const val sdp =  "com.intuit.sdp:sdp-android:1.0.6"
    const val ssp =  "com.intuit.ssp:ssp-android:1.0.6"
    // form validations for inputs
    const val formValidator =  "com.afollestad:vvalidator:0.5.2"


    const val appLocalHelper =  "dev.b3nedikt.applocale:applocale:2.0.3"

    // Rounded & Circle ImageViews
    const val roundedImageView =  "com.makeramen:roundedimageview:${Versions.roundedImageViewVersion}"
    const val circleImageView =  "de.hdodenhof:circleimageview:${Versions.circleImageViewVersion}"


    const val multiDex =  "com.android.support:multidex:${Versions.multiDexVersion}"

    //WebView
    const val webView = "com.github.delight-im:Android-AdvancedWebView:v3.2.1"

}

object TestLibs {
    const val junit = "junit:junit:${Versions.junit}"
    const val androidExtJunit = "androidx.test.ext:junit:${Versions.androidExtJunit}"
    const val androidEspresso = "androidx.test.espresso:espresso-core:${Versions.androidEspresso}"
    const val mockk = "io.mockk:mockk:${Versions.mockkVersion}"
    const val androidArchTestCore = "android.arch.core:core-testing:2.1.0"
}

object Config {
    const val BASE_URL_KEY = "BASE_URL"
    const val BASE_URL_VALUE = "\"https://jsonplaceholder.typicode.com\""

    const val API_KEY = "KEY"
    const val API_KEY_VALUE = "\"gsa@100\""

    const val API_KEY_NAME = "API_KEY_NAME"
    const val API_KEY_NAME_VALUE = "\"key\""
}