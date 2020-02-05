#!/usr/bin/env perl

# open the file
open($file, '<', 'HuckFinn.txt');

# read contents of file into memory
@whole_file = <$file>;

# close file since its contents is now in memory
close($file);

# this is a hashmap that will hold key, value pairs
# where the key is the word and the value is the
# number of times that it appears
%data = ();

foreach $line (@whole_file) {
    # convert each line to lowercase
    $line = lc $line;
    
    # find all substrings that match the regular expression
    foreach $word ($line =~ /[a-z']+/g) {
        # update the number of times that it has appeared so far
        if (exists($data{$word})) {
            $data{$word} = $data{$word} + 1;
        } else {
            $data{$word} = 1;
        }
    }
}

# sort the key, value pairs based on their values
@top_ten = (reverse sort {$data{$a} <=> $data{$b}} keys %data)[0..9];

# display the top ten
foreach $key (@top_ten) {
    print "   $data{$key} $key\n";
}
