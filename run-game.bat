@echo off
cd /d "%~dp0"
java -cp "target\nfm-lit-1.0-SNAPSHOT.jar;lib\*" nfm.lit.RunApp
