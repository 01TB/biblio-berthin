@echo off
setlocal EnableDelayedExpansion

set "SOURCE_DIR=.\src\main\java\com\springjpa\entity"
set "REPO_DIR=.\src\main\java\com\springjpa\service"

mkdir "%REPO_DIR%"

for %%F in ("%SOURCE_DIR%\*.java") do (
    set "file=%%F"
    set "class_name=%%~nF"
    set "class_name_minus=%%~nF"
    set "class_name_minus=!class_name_minus:~0,1!"
    set "class_name_minus=!class_name_minus!!class_name:~1!"

    set "repo_file=%REPO_DIR%\!class_name!Service.java"

    (
        echo package com.springjpa.service;
        echo.
        echo import org.springframework.stereotype.Service;
        echo import java.util.List;
        echo.
        echo import org.springframework.beans.factory.annotation.Autowired;
        echo import com.springjpa.entity.!class_name!;
        echo import com.springjpa.repository.!class_name!Repository;
        echo.
        echo @Service
        echo public class !class_name!Service {
        echo     @Autowired
        echo     private !class_name!Repository !class_name_minus!Repository;
        echo.
        echo     public !class_name! findById(Integer id^) {
        echo         return !class_name_minus!Repository.findById(id^).get();
        echo     }
        echo.
        echo     public List^<!class_name!^> findAll() {
        echo         return !class_name_minus!Repository.findAll();
        echo     }
        echo.
        echo     public void save(!class_name! !class_name_minus!^) {
        echo         !class_name_minus!Repository.save(!class_name_minus!^);
        echo     }
        echo }
    ) > "!repo_file!"

    echo Generated: !repo_file!
)

endlocal