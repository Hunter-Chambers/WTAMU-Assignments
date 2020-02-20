#!/usr/bin/env bash

(tr '[:upper:]' '[:lower:]' | grep -oE "[[:lower:]']+" | sort |
uniq -c | sort -nr | head -n 10) < HuckFinn.txt
