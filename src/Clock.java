/* Simulate a digital clock with hours, minutes, seconds. */

class Clock {
    private Counter hours, minutes, seconds;


    void run() {
        int[] time = {0, 0, 0};
        setup(time);
        start();
    }

    void fail() {
        System.err.println("Use: java Clock [h] [m] [s]");
        System.exit(1);
    }

    void setup(int[] time) {
        hours = new Counter(24, time[0], null);
        minutes = new Counter(60, time[1], hours);
        seconds = new Counter(60, time[2], minutes);
    }

    void start() {
        while (true) {
            String time =
                hours.display() + ":" +
                minutes.display() + ":" +
                seconds.display();
            System.out.println(time);
            try { Thread.sleep(1000); }
            catch (Exception e) { throw new Error(e); }
            seconds.tick();
        }
    }
}