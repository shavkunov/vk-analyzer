# VK Analyzer Application

[![Build Status](https://travis-ci.org/shavkunov/vk-analyzer.svg?branch=master)](https://travis-ci.org/shavkunov/vk-analyzer)

## Description

Application used to analyze wall of VK user or community and 
save results to internal database.

## Demo

* Check gif(27 mb) demo [here](https://www.dropbox.com/s/bt7fciw9z2dp2kp/demo.gif?dl=0).
* Or same workflow video(55 mb) [file](https://www.dropbox.com/s/8igvqb7319buvls/demo.mov?dl=0).

## Install

In command line: 


* `git clone https://github.com/shavkunov/vk-analyzer`
* `cd vk-analyzer`
* `./launch.sh`

These lines will compile resources and launches localhost.

If there are will be some errors during installation -- don't pay attention to them.

If you see something like:  Tomcat started on port(s): 8080 (http)

then installation is complete and visit: http://localhost:8080/

otherwise there is a global error and it's bug.

## Usage

Enter link to vk user/community in first form 
and amount of posts to second form(only from 10 to 80)

Examples of links: 

* "https://vk.com/username", vk.com/username", "username" 
* "https://vk.com/id1", vk.com/id1", "id1" 

They are treated as a same thing.

Click on submit button and see results.

## Close

Double ctrl+c will close app.