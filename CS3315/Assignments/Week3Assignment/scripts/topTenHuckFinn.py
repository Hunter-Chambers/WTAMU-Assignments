#!/usr/bin/env python3

# this is needed in order to use regular expressions
import re

f = open("HuckFinn.txt", errors='ignore')

# read the file into memory.
# lines is now an array full of strings
# that are each line from HuckFinn.txt
lines = f.readlines()

# close the file since its contents are now in memory
f.close()

# this dictionary will have each word as a key, and
# the number of times that it appears as the value
data = {}

for line in lines:
    # convert the line to lowercase
    line = line.lower()
    
    # find all substrings that match the regular expression
    words = re.findall("[a-z']+", line)
    
    # this for loop will count the number of times each
    # substring has occured
    for word in words:
        if (word in data):
            data[word] += 1
        else:
            data[word] = 1
            
# sort the key, value pairs based on their values
top_ten = sorted(data, key=data.get, reverse=True)[:10]

# display the top ten
for key in top_ten:
    print("  ", data[key], key)
