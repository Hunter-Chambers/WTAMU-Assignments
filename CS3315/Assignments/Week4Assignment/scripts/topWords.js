#!/usr/bin/env node

// used to read input files
var fs = require('fs');

fs.readFile('HuckFinn.txt', 'utf8', function(err, text) {
    // display error if one occured while trying to open the file
    if (err) {
        return console.log(err);
    }

    // this is the regular expression. the gi at the end makes
    // sure that all substrings that match this expression are
    // found, and it will not be case-sensitive.
    var re = /[a-z']+/gi;

    // find all substrings that match the regular expression
    var words = text.match(re);

    // this Map() object will hold key, value pairs where
    // the word will be the key and the number of times that
    // it appears will be the value
    var data = new Map();

    for (word of words) {
        // convert each word to lowercase
        word = word.toLowerCase();
        
        // update the number of times that it has occured so far
        if (data.has(word)) {
            data.set(word, data.get(word) + 1);
        } else {
            data.set(word, 1);
        }
    }

    // sort the Map() objects key, value pairs based on their values
    var topTen = new Map([...data.entries()].sort((a, b) => b[1] - a[1]));

    // display the top ten
    var keys = topTen.keys();
    for (var i = 0; i < 10; i++) {
        key = keys.next().value;
        console.log("  ", topTen.get(key), key);
    }
});
