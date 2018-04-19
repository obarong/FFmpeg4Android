# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

add_library( # Sets the name of the library.
             jxffmpegrun

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
              src/main/cpp/cmdutils.c
              src/main/cpp/ffmpeg.c
              src/main/cpp/ffmpeg_filter.c
              src/main/cpp/ffmpeg_opt.c
              src/main/cpp/jx_ffmpeg_cmd_run.c
             )
add_library(
            avcodec
            SHARED
            IMPORTED
            )

add_library(
            avfilter
            STATIC
            IMPORTED
             )


add_library(
            avformat
            STATIC
            IMPORTED
            )


add_library(
            avutil
            STATIC
            IMPORTED
            )

add_library(
            swresample
            STATIC
            IMPORTED
            )

add_library(
            swscale
            STATIC
            IMPORTED
            )


add_library(
            fdk-aac
            STATIC
            IMPORTED
            )

add_library(
            x264
            SHARED
            IMPORTED
            )

set_target_properties(
    avcodec
    PROPERTIES IMPORTED_LOCATION
    ${CMAKE_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}/libavcodec.a
    )

set_target_properties(
        avfilter
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}/libavfilter.a
        )

set_target_properties(
            avformat
            PROPERTIES IMPORTED_LOCATION
            ${CMAKE_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}/libavformat.a
            )

set_target_properties(
            avutil
            PROPERTIES IMPORTED_LOCATION
            ${CMAKE_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}/libavutil.a
            )

set_target_properties(
            swresample
            PROPERTIES IMPORTED_LOCATION
            ${CMAKE_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}/libswresample.a
             )

set_target_properties(
            swscale
            PROPERTIES IMPORTED_LOCATION
            ${CMAKE_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}/libswscale.a
             )

set_target_properties(
            fdk-aac
            PROPERTIES IMPORTED_LOCATION
            ${CMAKE_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}/libfdk-aac.a
             )

set_target_properties(
            x264
            PROPERTIES IMPORTED_LOCATION
            ${CMAKE_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}/libx264-148.so
             )

include_directories(
    ../ffmpeg-3.2.5/

)

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

find_library(
              z-lib
              z )

find_library(
              m-lib
              m )

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
                       jxffmpegrun
                       avfilter
                       avformat
                       avcodec
                       swresample
                       swscale
                       avutil
                       fdk-aac
                       x264
                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib}
                       ${z-lib}
                       ${m-lib} )