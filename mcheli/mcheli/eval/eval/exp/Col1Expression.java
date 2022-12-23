//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.eval.eval.exp;

public abstract class Col1Expression extends AbstractExpression
{
    protected AbstractExpression exp;
    
    public static AbstractExpression create(final AbstractExpression exp, final String string, final int pos, final AbstractExpression x) {
        final Col1Expression n = (Col1Expression)exp;
        n.setExpression(x);
        n.setPos(string, pos);
        return n;
    }
    
    protected Col1Expression() {
    }
    
    protected Col1Expression(final Col1Expression from, final ShareExpValue s) {
        super((AbstractExpression)from, s);
        if (from.exp != null) {
            this.exp = from.exp.dup(s);
        }
    }
    
    public void setExpression(final AbstractExpression x) {
        this.exp = x;
    }
    
    protected final int getCols() {
        return 1;
    }
    
    protected final int getFirstPos() {
        return this.exp.getFirstPos();
    }
    
    public long evalLong() {
        return this.operateLong(this.exp.evalLong());
    }
    
    public double evalDouble() {
        return this.operateDouble(this.exp.evalDouble());
    }
    
    protected abstract long operateLong(final long p0);
    
    protected abstract double operateDouble(final double p0);
    
    protected void search() {
        this.share.srch.search(this);
        if (this.share.srch.end()) {
            return;
        }
        if (this.share.srch.search1_begin(this)) {
            return;
        }
        if (this.share.srch.end()) {
            return;
        }
        this.exp.search();
        if (this.share.srch.end()) {
            return;
        }
        this.share.srch.search1_end(this);
    }
    
    protected AbstractExpression replace() {
        this.exp = this.exp.replace();
        return this.share.repl.replace1(this);
    }
    
    protected AbstractExpression replaceVar() {
        this.exp = this.exp.replaceVar();
        return this.share.repl.replaceVar1(this);
    }
    
    public boolean equals(final Object obj) {
        if (obj instanceof Col1Expression) {
            final Col1Expression e = (Col1Expression)obj;
            if (this.getClass() == e.getClass()) {
                if (this.exp == null) {
                    return e.exp == null;
                }
                return e.exp != null && this.exp.equals((Object)e.exp);
            }
        }
        return false;
    }
    
    public int hashCode() {
        return this.getClass().hashCode() ^ this.exp.hashCode();
    }
    
    public void dump(final int n) {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n; ++i) {
            sb.append(' ');
        }
        sb.append(this.getOperator());
        System.out.println(sb.toString());
        if (this.exp != null) {
            this.exp.dump(n + 1);
        }
    }
    
    public String toString() {
        if (this.exp == null) {
            return this.getOperator();
        }
        final StringBuffer sb = new StringBuffer();
        if (this.exp.getPriority() > this.prio) {
            sb.append(this.getOperator());
            sb.append(this.exp.toString());
        }
        else if (this.exp.getPriority() == this.prio) {
            sb.append(this.getOperator());
            sb.append(' ');
            sb.append(this.exp.toString());
        }
        else {
            sb.append(this.getOperator());
            sb.append(this.share.paren.getOperator());
            sb.append(this.exp.toString());
            sb.append(this.share.paren.getEndOperator());
        }
        return sb.toString();
    }
}
