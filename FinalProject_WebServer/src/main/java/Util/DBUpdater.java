package Util;

public class DBUpdater {
	public static void main(String[] args) {
		try {
            System.out.println("작업 스케줄러에 의해 DB 갱신 시작됨...");
            DBUtil.updateDailyBoxOfficeDB(); // 혹은 updateMoviesDB() 등 필요한 메서드
            DBUtil.updateMoviesDB();
            System.out.println("갱신 완료!");
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}

