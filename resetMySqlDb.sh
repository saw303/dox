#!/bin/sh

read -p "Enter the MySQL root password, followed by [ENTER]" pwd
mysql -u root -p$pwd -e "DROP DATABASE dox; CREATE DATABASE dox;"
mysql -u root -p$pwd dox < $1
