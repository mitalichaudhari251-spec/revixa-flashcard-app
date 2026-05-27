# Add project specific ProGuard rules here.

# Keep Room entities
-keep class com.example.revixa.data.local.entity.** { *; }

# Keep domain models
-keep class com.example.revixa.domain.model.** { *; }

# MPAndroidChart
-keep class com.github.mikephil.charting.** { *; }

# Gson
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**
-keep class com.google.gson.** { *; }

# Keep ViewModels
-keep class * extends androidx.lifecycle.ViewModel { *; }

# General AndroidX keep rules
-dontwarn androidx.**
