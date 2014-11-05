LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := mp3lame
LOCAL_SRC_FILES := \
	/Users/user/AndroidStudioProjects/Android_challenge/LAME_Recorder/app/src/main/jni/Android.mk \
	/Users/user/AndroidStudioProjects/Android_challenge/LAME_Recorder/app/src/main/jni/greenlab_com_Models_SimpleLame.c \
	/Users/user/AndroidStudioProjects/Android_challenge/LAME_Recorder/app/src/main/jni/Note.txt \
	/Users/user/AndroidStudioProjects/Android_challenge/LAME_Recorder/app/src/main/jni/lame-3.98.4_libmp3lame/bitstream.c \
	/Users/user/AndroidStudioProjects/Android_challenge/LAME_Recorder/app/src/main/jni/lame-3.98.4_libmp3lame/encoder.c \
	/Users/user/AndroidStudioProjects/Android_challenge/LAME_Recorder/app/src/main/jni/lame-3.98.4_libmp3lame/fft.c \
	/Users/user/AndroidStudioProjects/Android_challenge/LAME_Recorder/app/src/main/jni/lame-3.98.4_libmp3lame/gain_analysis.c \
	/Users/user/AndroidStudioProjects/Android_challenge/LAME_Recorder/app/src/main/jni/lame-3.98.4_libmp3lame/id3tag.c \
	/Users/user/AndroidStudioProjects/Android_challenge/LAME_Recorder/app/src/main/jni/lame-3.98.4_libmp3lame/lame.c \
	/Users/user/AndroidStudioProjects/Android_challenge/LAME_Recorder/app/src/main/jni/lame-3.98.4_libmp3lame/lame.rc \
	/Users/user/AndroidStudioProjects/Android_challenge/LAME_Recorder/app/src/main/jni/lame-3.98.4_libmp3lame/mpglib_interface.c \
	/Users/user/AndroidStudioProjects/Android_challenge/LAME_Recorder/app/src/main/jni/lame-3.98.4_libmp3lame/newmdct.c \
	/Users/user/AndroidStudioProjects/Android_challenge/LAME_Recorder/app/src/main/jni/lame-3.98.4_libmp3lame/presets.c \
	/Users/user/AndroidStudioProjects/Android_challenge/LAME_Recorder/app/src/main/jni/lame-3.98.4_libmp3lame/psymodel.c \
	/Users/user/AndroidStudioProjects/Android_challenge/LAME_Recorder/app/src/main/jni/lame-3.98.4_libmp3lame/quantize.c \
	/Users/user/AndroidStudioProjects/Android_challenge/LAME_Recorder/app/src/main/jni/lame-3.98.4_libmp3lame/quantize_pvt.c \
	/Users/user/AndroidStudioProjects/Android_challenge/LAME_Recorder/app/src/main/jni/lame-3.98.4_libmp3lame/reservoir.c \
	/Users/user/AndroidStudioProjects/Android_challenge/LAME_Recorder/app/src/main/jni/lame-3.98.4_libmp3lame/set_get.c \
	/Users/user/AndroidStudioProjects/Android_challenge/LAME_Recorder/app/src/main/jni/lame-3.98.4_libmp3lame/tables.c \
	/Users/user/AndroidStudioProjects/Android_challenge/LAME_Recorder/app/src/main/jni/lame-3.98.4_libmp3lame/takehiro.c \
	/Users/user/AndroidStudioProjects/Android_challenge/LAME_Recorder/app/src/main/jni/lame-3.98.4_libmp3lame/util.c \
	/Users/user/AndroidStudioProjects/Android_challenge/LAME_Recorder/app/src/main/jni/lame-3.98.4_libmp3lame/vbrquantize.c \
	/Users/user/AndroidStudioProjects/Android_challenge/LAME_Recorder/app/src/main/jni/lame-3.98.4_libmp3lame/VbrTag.c \
	/Users/user/AndroidStudioProjects/Android_challenge/LAME_Recorder/app/src/main/jni/lame-3.98.4_libmp3lame/version.c \

LOCAL_C_INCLUDES += /Users/user/AndroidStudioProjects/Android_challenge/LAME_Recorder/app/src/main/jni
LOCAL_C_INCLUDES += /Users/user/AndroidStudioProjects/Android_challenge/LAME_Recorder/app/src/debug/jni

include $(BUILD_SHARED_LIBRARY)
