# Created by jianxi on 2017/6/4
# https://github.com/mabeijianxi
# mabeijianxi@gmail.com

NDK=${ANDROID_NDK_HOME}

PLATFORM=$NDK/platforms/android-21/arch-arm64/
TOOLCHAIN=$NDK/toolchains/aarch64-linux-android-4.9/prebuilt/linux-x86_64
PREFIX=./android/arm64-v8a

function build_one
{
./configure \
--prefix=$PREFIX \
--enable-shared \
--enable-static \
--enable-pic \
--enable-strip \
--host=aarch64-linux \
--cross-prefix=$TOOLCHAIN/bin/aarch64-linux-android- \
--sysroot=$PLATFORM \
--extra-cflags="-Os -fpic" \
--extra-ldflags="" \

$ADDITIONAL_CONFIGURE_FLAG
make clean
make -j4
make install
}

build_one
