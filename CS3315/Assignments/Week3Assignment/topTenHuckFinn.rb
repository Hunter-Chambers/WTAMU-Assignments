#!/usr/bin/env ruby

RE = /[[:alpha:]']+/
count = open("HuckFinn.txt").read.downcase.scan(RE).tally.max_by(10, &:last)
count.each{|ar| puts ("   " + ar[1].to_s + " " + ar[0].to_s)}
