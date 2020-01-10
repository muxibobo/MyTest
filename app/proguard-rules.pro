# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#------ Gson ----
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-keep class com.idea.fifaalarmclock.entity.***
-keep class com.google.gson.stream.** { *; }
#====== FastJson 混淆代码
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *; }
-keepattributes Signature
-keepattributes *Annotation*



#======

-keep public class **.*bean*.** { *; }
-keep public class **.*Bean*.** { *; }
-keep public class **.*model*.** { *; }
-keep public class **.*Model*.** { *; }
-keep public class **.*User*.** { *; }

-keep public class **.*bean*.**$* { *; }
-keep public class **.*Bean*.**$* { *; }
-keep public class **.*model*.**$* { *; }
-keep public class **.*Model*.**$* { *; }
-keep public class **.*User*.**$* { *; }

#-keep  class com.bo.mytest.bean.**{*;}
##
#-keep  class com.bo.mytest.bean.ChargingOrderModel{ private <fields>;
#                                                    	private <methods>;
#                                                    	public <fields>;
#                                                       public <methods>;
#                                                       *; }
#-keep  class com.bo.mytest.bean.ChargingOrderModel$ReChargeOrderDetailBean{
#    private <fields>;
# 	private <methods>;
# 	public <fields>;
#    public <methods>;
#    *; }

-keep class com.bo.mytest.MainActivity { *; }

#-keep  class com.bo.mytest.**{*;}

#-keep public class androidx.appcompat.app.**
#-keep public class * extends androidx.appcompat.app.**
#-keep public class * extends androidx.appcompat.app.AppCompatActivity
#-keep public class * extends androidx.appcompat.app.AppCompatActivity{*;}
#-keep public class * extends androidx.appcompat.app.AppCompatActivity{
#	public <fields>;
#	public <methods>;
#}