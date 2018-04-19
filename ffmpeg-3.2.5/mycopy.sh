#!/bin/sh
dir=myinclude
mkdir $dir
cp -r compat $dir/
cp -r fdk-aac-0.1.5 $dir/
cp -r libavcodec $dir/
cp -r libavdevice $dir/
cp -r libavfilter $dir/
cp -r libavformat $dir/
cp -r libavresample $dir/
cp -r libavutil $dir/
cp -r libswresample $dir/
cp -r libswscale $dir/
cp -r libx264 $dir/

find $dir -type f ! -name "*.h" | xargs -i rm -r {}


