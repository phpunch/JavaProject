package logic;

public class GameTime {
	private boolean isTimeRunning;
	public static int seconds = 0;
	private Thread gameTime;
	public GameTime() {
		isTimeRunning = true;
		gameTime = new Thread( new Runnable() {
		    
			public void run() {
				long start = System.currentTimeMillis();
				while(isTimeRunning){
				    try {
				        //System.out.println(String.valueOf(seconds));
				        seconds = (int) ((System.currentTimeMillis()-start)/1000);
				        Thread.sleep(500);
				    } catch (InterruptedException e1) {
				        e1.printStackTrace();
				    }
				}
				
			}
		},"gameTime");
		gameTime.start();
	}
	
	public void stop() {
		isTimeRunning = false;
		//System.out.println("GameTime :" + gameTime.isAlive());
	}
}
