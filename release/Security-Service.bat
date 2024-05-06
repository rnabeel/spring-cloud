@echo off
title MFSYS [ Security Service ]
set JDK="C:\Program Files\Java\jdk-17.0.8"
cd /d "D:\Development-mfsys\digital-banking-service\digital-Security-service\target"
%JDK%\bin\java -jar security.jar
pause
