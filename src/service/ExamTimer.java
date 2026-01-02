package service;

public class ExamTimer extends Thread {

    private int seconds;
    private volatile boolean timeUp = false;

    public ExamTimer(int minutes) {
        this.seconds = minutes * 60;
        setDaemon(true); // background thread
    }

    @Override
    public void run() {
        try {
            while (seconds > 0) {
                int min = seconds / 60;
                int sec = seconds % 60;

                System.out.printf("%n[Time Remaining: %02d:%02d]%n", min, sec);
                Thread.sleep(1000);
                seconds--;
            }
            timeUp = true;
            System.out.println("\n⏰ TIME IS UP! Exam auto-submitted.");
        } catch (InterruptedException ignored) {}
    }

    public boolean isTimeUp() {
        return timeUp;
    }
}
