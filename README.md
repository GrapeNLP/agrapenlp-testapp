# AGRAPENLP-TESTAPP: Android GrapeNLP test app

AGRAPENLP-TESTAPP is an open-source Android app that serves as example on how integrate GRAPENLP in an Android app.

## Dependencies

* GrapeNLP (binaries already included in folder app/src/main/jniLibs for armeabi-v7a and x86_64 ABIs)

## How to install

Create the following folder at the root of the local file system of the Android device of your choice( either a real device or an emulated one):

`grapenlpdata`

Place in that folder a Unitex fst2 grammar file named

* grammar.fst2

and the 2 following files of a DELAF dictionary in binary format:

* delaf_norm.bin
* delaf_norm.inf

Open the AGRAPENLP-TESTAPP project with Android Studio and run the app on the Android device. Note the device must be properly configured for allowing the installation of apps from unknown sources.

## License

<a href="/LICENSE"><img height="48" align="left" src="http://www.gnu.org/graphics/empowered-by-gnu.svg"></a>

This program is licensed under the GNU Lesser General Public License version 2.1. Contact javier.sastre@telefonica.net for further inquiries.
