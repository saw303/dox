#!/bin/sh

DATABASE="dox"
read -p "Enter the MySQL root password, followed by [ENTER]" pwd
mysql -u root -p$pwd -e "DROP DATABASE $DATABASE; CREATE DATABASE $DATABASE"
mysql -u root -p$pwd $DATABASE < $1
