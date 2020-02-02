#!/usr/bin/env lua

local freq = {}
for line in io.lines('HuckFinn.txt') do
	local lowerline = string.lower(line)
	for word in string.gmatch(lowerline, "[%a']+") do
		if not freq[word] then
			freq[word] = 1
		else
			freq[word] = freq[word] + 1
		end
	end
end
 
local array = {}
for word, count in pairs(freq) do
	table.insert(array, {word, count})
end
 
table.sort(array, function (a, b) return a[2] > b[2] end)
 
for i = 1, arg[2] or 10 do
	io.write(string.format('%7d %s\n', array[i][2] , array[i][1]))
end
