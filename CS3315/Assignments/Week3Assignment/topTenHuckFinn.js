#!/usr/bin/env node

var fs = require('fs');
fs.readFile('HuckFinn.txt', 'utf8', function(err, text) {
    if (err) {
        return console.log(err);
    }
    var re = /[a-z']+/gi;
    var words = text.match(re);
    var data = new Map();
    for (word of words) {
        word = word.toLowerCase();
        if (data.has(word)) {
            data.set(word, data.get(word) + 1);
        } else {
            data.set(word, 1);
        }
    }
    var topTen = new Map([...data.entries()].sort((a, b) => b[1] - a[1]));
    var keys = topTen.keys();
    for (var i = 0; i < 10; i++) {
        key = keys.next().value;
        console.log("  ", topTen.get(key), key);
    }
});
