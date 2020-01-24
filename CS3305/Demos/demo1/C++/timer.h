#include <iostream>
#include <iomanip>
#include <stdexcept>
#include <thread>
#include <chrono>
#include <time.h>

typedef std::chrono::high_resolution_clock::time_point TimeVar;

#define elapsed(a) std::chrono::duration_cast<std::chrono::nanoseconds>(a).count()
#define timeNow() std::chrono::high_resolution_clock::now()

class timer {

    public:
        timer( ) {
            startTime = {};
            stopTime  = {};
        }

        void start( ) {
            startTime = timeNow();
        }

        void stop( ) {
            stopTime = timeNow();
        }

        long duration( ) const {
            if ( elapsed(this->stopTime - this->startTime) < 0 )
                throw std::runtime_error("stop() called before start()");
            return (long)( elapsed(stopTime - startTime) / 1.0e9 );
        }

        void run_timer( long seconds ) {
            this->start( );
            std::this_thread::sleep_for(std::chrono::seconds(seconds));
            this->stop();
            std::cout << "Ding . . .\n";
        }

        std::string toString( ) const {
            std::stringstream outs;

            outs << this->duration( );
            return outs.str();
        }


    private:
        TimeVar startTime, stopTime;
};

std::ostream& operator <<(std::ostream& outs, const timer& t) {
    outs << t.toString( );
    return outs;
}


