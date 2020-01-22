public class Timer {

    private long startTime, stopTime;

    public Timer( ) {
        startTime = 0;
        stopTime  = 0;
    }

    public void start( ) {
        startTime = System.nanoTime();
    }

    public void stop( ) {
        stopTime = System.nanoTime();
    }

    public long duration( ) {
        if ( ( this.stopTime - this.startTime ) < 0 )
            throw new IllegalStateException("stop() called before start()");
        return (long)( (stopTime - startTime) / 1.0e9 );
    }

    public void run_timer( long seconds ) {
        this.start( );
        try {
            Thread.sleep(seconds*1000); 
        }
        catch (Exception e) { }
        this.stop( );
        System.out.printf("Ding . . .\n");
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.duration());
        return sb.toString();
    }
}

