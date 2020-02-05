#!/usr/bin/env ruby

# this is our regular expression
RE = /[[:alpha:]']+/

# open("HuckFinn.txt") => open the file
# read.downcase        => convert the contents to lowercase
# scan(RE)             => find all substrings that match the regular expression
# tally                => count how many times each substring appears
# max_by(10, &:last)   => only keep the top ten based on their tallies
count = open("HuckFinn.txt").read.downcase.scan(RE).tally.max_by(10, &:last)

# display the top ten
count.each{|ar| puts ("   " + ar[1].to_s + " " + ar[0].to_s)}
