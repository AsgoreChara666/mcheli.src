//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.eval.eval.srch;

import mcheli.eval.eval.exp.*;

public class SearchAdapter implements Search
{
    protected boolean end;
    
    public SearchAdapter() {
        this.end = false;
    }
    
    public boolean end() {
        return this.end;
    }
    
    protected void setEnd() {
        this.end = true;
    }
    
    public void search(final AbstractExpression exp) {
    }
    
    public void search0(final WordExpression exp) {
    }
    
    public boolean search1_begin(final Col1Expression exp) {
        return false;
    }
    
    public void search1_end(final Col1Expression exp) {
    }
    
    public boolean search2_begin(final Col2Expression exp) {
        return false;
    }
    
    public boolean search2_2(final Col2Expression exp) {
        return false;
    }
    
    public void search2_end(final Col2Expression exp) {
    }
    
    public boolean search3_begin(final Col3Expression exp) {
        return false;
    }
    
    public boolean search3_2(final Col3Expression exp3) {
        return false;
    }
    
    public boolean search3_3(final Col3Expression exp) {
        return false;
    }
    
    public void search3_end(final Col3Expression exp) {
    }
    
    public boolean searchFunc_begin(final FunctionExpression exp) {
        return false;
    }
    
    public boolean searchFunc_2(final FunctionExpression exp) {
        return false;
    }
    
    public void searchFunc_end(final FunctionExpression exp) {
    }
}
