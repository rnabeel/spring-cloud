@echo off
title MFSYS [ API GATEWAY ]
set JDK="C:\Program Files\Java\jdk-17.0.8"
cd /d "D:\Development-mfsys\digital-banking-service\digital-banking-gateway\target"
%JDK%\bin\java -jar gateway.jar
pause
