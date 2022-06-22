#!/bin/sh
echo "Compiling..."
javac me/second2050/raytracer/*.java > compile.log 2>&1
_compileStatus=$?
if [ $_compileStatus = 0 ]; then
    java me.second2050.raytracer.RaytracerMain
    exit $?
else
    cat compile.log
    exit $_compileStatus
fi
