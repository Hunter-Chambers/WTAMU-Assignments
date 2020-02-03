#!/usr/bin/env perl

open($file, '<', 'HuckFinn.txt');
@whole_file = <$file>;
close($file);
%data = ();
foreach $line (@whole_file) {
    $line = lc $line;
    foreach $word ($line =~ /[a-z']+/g) {
        if (exists($data{$word})) {
            $data{$word} = $data{$word} + 1;
        } else {
            $data{$word} = 1;
        }
    }
}
@top_ten = (reverse sort {$data{$a} <=> $data{$b}} keys %data)[0..9];
foreach $key (@top_ten) {
    print "   $data{$key} $key\n";
}
