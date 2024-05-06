@echo off
title MFSYS [ Notification Service ]
set JDK="C:\Program Files\Java\jdk-17.0.8"
cd /d "D:\Development-mfsys\digital-banking-service\notification-service\target"
%JDK%\bin\java -jar notification.jar
pause
