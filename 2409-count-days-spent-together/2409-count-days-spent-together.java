class Solution {
    public int countDaysTogether(String arriveAlice, String leaveAlice, String arriveBob, String leaveBob) {
        int arrive = Math.max(convert(arriveAlice), convert(arriveBob));
        int leave = Math.min(convert(leaveAlice), convert(leaveBob));
        return Math.max(0, leave - arrive + 1);
    }
    
    private int convert(String date) {
        int[] daysInMonths = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int month = Integer.parseInt(date.substring(0, 2));
        int day = Integer.parseInt(date.substring(3, 5));
        int totalDays = day;
        for (int i = 0; i < month - 1; i++) {
            totalDays += daysInMonths[i];
        }
        return totalDays;
    }
}