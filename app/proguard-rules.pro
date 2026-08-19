# Regras padrão do R8/ProGuard para apps gerados pela Nine IDE

# Mantém assinaturas e anotações (necessário para Gson/Retrofit via reflexão).
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes InnerClasses
-keepattributes EnclosingMethod
-keepattributes Exceptions
-keepattributes RuntimeVisibleAnnotations
-keepattributes RuntimeInvisibleAnnotations

# Mantém campos anotados por Gson (SerializedName) e modelos serializados.
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Retrofit + OkHttp: evita remoção de interfaces usadas via proxy dinâmico.
-dontwarn retrofit2.**
-dontwarn okhttp3.**
-dontwarn javax.annotation.**
-keepattributes RuntimeVisibleAnnotations

# Mantém classes de modelo (padrão comum de pacote .model / .models).
-keep class **.model.** { *; }
-keep class **.models.** { *; }
