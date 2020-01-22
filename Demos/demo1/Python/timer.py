#!/usr/bin/env python3
'''This file contains the Timer class'''

from time import time, sleep


class Timer:
    '''A class to model a simple timer'''

    def __init__(self):
        '''default constructor '''
        self.start_time = 0
        self.stop_time = 0
    # end __init__

    def start(self):
        '''start launches the timer at the current time'''
        self.start_time = time()
    # end start

    def stop(self):
        '''stop stops the timer at the current time'''
        self.stop_time = time()
    # end stop

    def duration(self):
        '''duration returns the number of nanoseconds
           between the start time and the stop time'''
        if self.stop_time - self.start_time < 0:
            raise RuntimeError("stop() invoked before start()")
        return int(self.stop_time - self.start_time)
    # end duration

    def run_timer(self, seconds):
        '''set_timer allows user to start a timer for some
           number of seconds'''
        self.start()
        sleep(seconds)
        self.stop()
        print("Ding . . .")
    # end run_timer

    def __str__(self):
        '''returns a string rendering of the current timer duration'''
        return str(self.duration())
    # end __str__
# end class timer
