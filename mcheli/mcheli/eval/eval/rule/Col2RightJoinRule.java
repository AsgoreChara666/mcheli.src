//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.eval.eval.rule;

import mcheli.eval.eval.lex.*;
import mcheli.eval.eval.exp.*;

public class Col2RightJoinRule extends AbstractRule
{
    public Col2RightJoinRule(final ShareRuleValue share) {
        super(share);
    }
    
    protected AbstractExpression parse(final Lex lex) {
        AbstractExpression x = this.nextRule.parse(lex);
        switch (lex.getType()) {
            case 2147483634: {
                final String ope = lex.getOperator();
                if (this.isMyOperator(ope)) {
                    final int pos = lex.getPos();
                    final AbstractExpression y = this.parse(lex.next());
                    x = Col2Expression.create(this.newExpression(ope, lex.getShare()), lex.getString(), pos, x, y);
                }
                return x;
            }
            default: {
                return x;
            }
        }
    }
}
