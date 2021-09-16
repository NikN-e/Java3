import java.util.concurrent.BrokenBarrierException;

public class Road extends Stage {
    public Road(int length) {
        this.length = length;
        this.description = "Дорога " + length + " метров";
    }

    @Override
    public void go(Car c) {
        try {
            Thread.sleep(100);
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);
            App.roadStage.await();
            System.out.println(c.getName() + " закончил этап: " + description);
            if(length == 40){
                App.finishLine.countDown();
            }
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
