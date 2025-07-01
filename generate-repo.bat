@echo off
setlocal EnableDelayedExpansion

set "SOURCE_DIR=.\src\main\java\com\springjpa\entity"
set "REPO_DIR=.\src\main\java\com\springjpa\repository"

mkdir "%REPO_DIR%"

for %%F in ("%SOURCE_DIR%\*.java") do (
    set "file=%%F"
    set "class_name=%%~nF"
    
    set "repo_file=%REPO_DIR%\!class_name!Repository.java"

    (
        echo package com.springjpa.repository;
        echo.
        echo import org.springframework.data.jpa.repository.JpaRepository;
        echo import org.springframework.stereotype.Repository;
        echo import com.springjpa.entity.!class_name!;
        echo.
        echo @Repository
        echo public interface !class_name!Repository extends JpaRepository^<!class_name!, Integer^> {
        echo }
    ) > "!repo_file!"

    echo Generated: !repo_file!
)

endlocal