//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.eval.eval.repl;

import mcheli.eval.eval.exp.*;

public interface Replace
{
    AbstractExpression replace0(final WordExpression p0);
    
    AbstractExpression replace1(final Col1Expression p0);
    
    AbstractExpression replace2(final Col2Expression p0);
    
    AbstractExpression replace2(final Col2OpeExpression p0);
    
    AbstractExpression replace3(final Col3Expression p0);
    
    AbstractExpression replaceVar0(final WordExpression p0);
    
    AbstractExpression replaceVar1(final Col1Expression p0);
    
    AbstractExpression replaceVar2(final Col2Expression p0);
    
    AbstractExpression replaceVar2(final Col2OpeExpression p0);
    
    AbstractExpression replaceVar3(final Col3Expression p0);
    
    AbstractExpression replaceFunc(final FunctionExpression p0);
    
    AbstractExpression replaceLet(final Col2Expression p0);
}
