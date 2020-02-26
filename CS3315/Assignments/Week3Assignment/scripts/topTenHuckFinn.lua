#!/usr/bin/env lua

-- this is a hashmap that will hold key, value pairs
-- where the word is the key and the number of times
-- that it appears is its value
local freq = {}

for line in io.lines('HuckFinn.txt') do
    -- convert each line of the text to lowercase
    local lowerline = string.lower(line)

    -- find all substrings that match the regular expression
    for word in string.gmatch(lowerline, "[%a']+") do
        -- update the number of times that the substring
        -- has appeared so far
    	if not freq[word] then
	    freq[word] = 1
	else
	    freq[word] = freq[word] + 1
	end
    end
end

local array = {}

-- fill array with the key, value pairs. I.E. array[0] => 'and, 6440'
-- this is necessary so that we can sort them correctly
for word, count in pairs(freq) do
	table.insert(array, {word, count})
end

-- sort the key, value pairs based on their value
table.sort(array, function (a, b) return a[2] > b[2] end)
 
-- display the top ten
for i = 1, 10 do
	io.write(string.format('%7d %s\n', array[i][2] , array[i][1]))
end