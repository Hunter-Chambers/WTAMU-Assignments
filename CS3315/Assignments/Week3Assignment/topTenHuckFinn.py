#!/usr/bin/env python3

import re

f = open("HuckFinn.txt", errors='ignore')
lines = f.readlines()
f.close()
data = {}
for line in lines:
    line = line.lower()
    words = re.findall("[a-z']+", line)
    for word in words:
        if (word in data):
            data[word] += 1
        else:
            data[word] = 1
top_ten = sorted(data, key=data.get, reverse=True)[:10]
for key in top_ten:
    print("  ", data[key], key)
