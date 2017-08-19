# VK Analyzer Application

## Description

Application used to analyze wall of VK user or community and 
save results to internal database.

## Install

In command line: 


* git clone https://github.com/shavkunov/vk-analyzer
* cd vk-analyzer
* ./launch.sh

These lines will compile resources and launches localhost.

If there are will be some errors during installation -- don't pay attention to them.

If you see something like:  Tomcat started on port(s): 8080 (http)

then installation is complete and visit: http://localhost:8080/

otherwise there is a global error and it's bug.

## Usage

Enter link to vk user/community in first form 
and amount of posts to second form(only from 10 to 80)

Examples of links: "https://vk.com/username", vk.com/username", "username" 

They are treated as a same thing.

Click on submit button and see results.

## Close

Double ctrl+c will close app.

## Issues

There some problems:

* If user hid his wall application doesn't tell you about that. Just nothing.
* If user banned or deleted -- same case.