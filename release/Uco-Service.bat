@echo off
title MFSYS [ UCO Service ]
set JDK="C:\Program Files\Java\jdk-17.0.8"
cd /d "D:\Development-mfsys\digital-banking-service\UCO-BS\target"
%JDK%\bin\java -jar uco.jar
pause
