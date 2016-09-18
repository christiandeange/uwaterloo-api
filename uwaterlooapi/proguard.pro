# Butterknife
-keep public class * implements butterknife.Unbinder { public <init>(...); }
-keep class butterknife.*
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }

# Picasso
-dontwarn com.squareup.okhttp.**

# Gson
-keep class com.deange.uwaterlooapi.sample.model.** { *; }