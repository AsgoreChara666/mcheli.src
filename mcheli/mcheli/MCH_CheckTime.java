//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli;

public class MCH_CheckTime
{
    private long startTime;
    public int x;
    private int y;
    public long[][] pointTimeList;
    public int MAX_Y;
    private int MAX_X;
    
    public MCH_CheckTime() {
        this.startTime = 0L;
        this.x = 0;
        this.y = 0;
        this.pointTimeList = new long[1][1];
        this.MAX_Y = 0;
        this.MAX_X = 0;
        this.MAX_Y = 100;
        this.MAX_X = 40;
        this.pointTimeList = new long[this.MAX_Y + 1][this.MAX_X];
        this.y = this.MAX_Y - 1;
    }
    
    public void start() {
        this.startTime = System.nanoTime();
        this.x = 0;
        this.y = (this.y + 1) % this.MAX_Y;
        if (this.y == 0) {
            for (int j = 0; j < this.MAX_X; ++j) {
                this.pointTimeList[this.MAX_Y][j] = 0L;
                for (int i = 0; i < this.MAX_Y; ++i) {
                    final long[] array = this.pointTimeList[this.MAX_Y];
                    final int n = j;
                    array[n] += this.pointTimeList[i][j];
                }
            }
        }
    }
    
    public void timeStamp() {
        if (this.x < this.MAX_X) {
            this.pointTimeList[this.y][this.x] = System.nanoTime() - this.startTime;
            ++this.x;
        }
    }
}
